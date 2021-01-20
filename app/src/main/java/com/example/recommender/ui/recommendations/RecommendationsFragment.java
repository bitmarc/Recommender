package com.example.recommender.ui.recommendations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.recommender.MainActivity;
import com.example.recommender.connection.ConnectionManager;
import com.example.recommender.entities.User;
import com.example.recommender.form.Form;
import com.example.recommender.R;

import java.io.IOException;

public class RecommendationsFragment extends Fragment {

    private RecommendationsViewModel recommendationsViewModel;
    private Form formResponse;
    private LinearLayout containerP;
    private TextView bienvenida;
    private TextView title_form;
    private ImageView ivHand;
    private Button btnStart;
    private Button sendB;
    private ProgressBar pbLoading;
    private ItemForm form;
    private MainActivity activity;
    private User user;
    private int LAUNCH_RECOMMENDATION_RESULT_ACTIVITY = 3;
    private Typeface containerValuesFont;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_RECOMMENDATION_RESULT_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                //Toast.makeText(getActivity(), "Limpiar pantalla ", Toast.LENGTH_SHORT).show();
                recommendationsViewModel.changeUI(false);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void restoreScreen(Context context, LinearLayout parent) {
        float scale = context.getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams layoutParamsTv = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParamsBtn = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, toPixels(25,scale));
        layoutParamsTv.setMargins(toPixels(20,scale), toPixels(70,scale), toPixels(20,scale), 0);
        layoutParamsBtn.setMargins(0, toPixels(20,scale), 0, 0);
        bienvenida = new TextView(context);
        bienvenida.setLayoutParams(layoutParamsTv);
        bienvenida.setId(View.generateViewId());
        String sourceString = getResources().getString(R.string.message_start_recommender)+"<br><br><i>" + getResources().getString(R.string.objective)+"</i>" ;
        bienvenida.setText(Html.fromHtml(sourceString));
        bienvenida.setTextSize(toPixels(5,scale));
        bienvenida.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        parent.addView(bienvenida);
        btnStart = new Button(context);
        btnStart.setId(View.generateViewId());
        layoutParamsBtn.gravity = Gravity.CENTER;
        btnStart.setLayoutParams(layoutParamsBtn);
        btnStart.setText("Empezar");
        btnStart.setPadding(toPixels(2,scale),0,toPixels(2,scale),0);
        btnStart.setTypeface(containerValuesFont);
        btnStart.setTextSize(getResources().getDimension(R.dimen.normal_text_questions));
        btnStart.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        btnStart.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recommendationsViewModel.changeUI(true);
            }
        });
        parent.addView(btnStart);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        recommendationsViewModel = ViewModelProviders.of(this).get(RecommendationsViewModel.class);
        activity = (MainActivity) getActivity();
        user= activity.getUser();
        View root = inflater.inflate(R.layout.fragment_recommendations, container, false);
        this.containerValuesFont= ResourcesCompat.getFont(getContext(),R.font.franklin_gothic_demi_cond);
        containerP = root.findViewById(R.id.containerP);
        title_form = root.findViewById(R.id.text_recommendations);
        title_form.setVisibility(View.GONE);
        btnStart = root.findViewById(R.id.idBsttart);
        ivHand = root.findViewById(R.id.ivHand);
        ivHand.setVisibility(View.GONE);
        sendB = root.findViewById(R.id.sendB);
        sendB.setVisibility(View.GONE);
        pbLoading = root.findViewById(R.id.pbLoading);
        pbLoading.setVisibility(View.GONE);


        recommendationsViewModel.SetUI().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean s) {
                if(s){
                    containerP.removeAllViewsInLayout();
                    title_form.setVisibility(View.VISIBLE);
                    sendB.setVisibility(View.VISIBLE);
                    ivHand.setVisibility(View.VISIBLE);
                    new getUserFormInBackground().execute();
                }
                else{
                    containerP.removeAllViewsInLayout();
                    restoreScreen(getActivity(),containerP);
                    title_form.setVisibility(View.INVISIBLE);
                    sendB.setVisibility(View.INVISIBLE);
                    ivHand.setVisibility(View.INVISIBLE);
                }
            }
        });

        sendB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(form.getStatus()){
                    formResponse=form.getForm();
                    Intent activityIntent = new Intent(getActivity(), RecomResult.class);
                    activityIntent.putExtra("Form",formResponse); // AQUI EL VALOR ERA idRes
                    activityIntent.putExtra("User",user);
                    activityIntent.putExtra("type", "request");
                    startActivityForResult(activityIntent, LAUNCH_RECOMMENDATION_RESULT_ACTIVITY);
                }else{
                    Toast.makeText(getActivity(), "Faltan campos por llenar: ", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recommendationsViewModel.changeUI(true);
            }
        });
        return root;
    }
    // ********************************************************************************** METHOD SETFORM
    public void setForm(Form userForm){
        if(userForm.getId()!=null){
            this.form = new ItemForm(getActivity(),userForm);
            form.initContainers(getActivity(),containerP);
        }
        else
        System.out.println("objeto form vacio");
    }
    // ********************************************************************************** CLASS GET FORM INBACKGROUND
    class getUserFormInBackground extends AsyncTask<Void, Void, Form> {
        @Override
        protected void onPreExecute() {
            pbLoading.setVisibility(View.VISIBLE);
        }
            @Override
            protected Form doInBackground (Void...voids){
                Form userForm = new Form();
                ConnectionManager cm = new ConnectionManager();
                try {
                    userForm = cm.getUserForm();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return userForm;
            }
            @Override
            protected void onPostExecute (Form form){
                setForm(form);
                pbLoading.setVisibility(View.GONE);
            }
        }
        private int toPixels(int dp, float scale) {
            int pixels = (int) (dp * scale + 0.5f);
            return pixels;
        }

    }


