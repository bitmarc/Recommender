package com.example.recommender.ui.recommendations;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.recommender.MainActivity;
import com.example.recommender.connection.ConnectionManager;
import com.example.recommender.entities.User;
import com.example.recommender.form.Form;
import com.example.recommender.form.Question;
import com.example.recommender.R;

import java.io.IOException;
import java.util.List;

public class RecommendationsFragment extends Fragment {

    private RecommendationsViewModel recommendationsViewModel;
    private Form formResponse;
    private LinearLayout containerP;
    private TextView bienvenida;
    private TextView title_form;
    private Button BtnStart;
    private Button sendB;
    private ProgressBar pbLoading;
    private ItemForm form;
    private MainActivity activity;
    private User user;
    private int LAUNCH_RECOMMENDATION_RESULT_ACTIVITY = 3;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_RECOMMENDATION_RESULT_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(getActivity(), "El dato es: "+data.getStringExtra("newpass"), Toast.LENGTH_SHORT).show();
                //editTextP.setText(data.getStringExtra("newpass"));
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
                //Write your code if there's no result
            }
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        recommendationsViewModel = ViewModelProviders.of(this).get(RecommendationsViewModel.class);
        activity = (MainActivity) getActivity();
        user= activity.getUser();
        View root = inflater.inflate(R.layout.fragment_recommendations, container, false);

        title_form = root.findViewById(R.id.text_recommendations);
        BtnStart = root.findViewById(R.id.idBsttart);
        sendB = root.findViewById(R.id.sendB);
        containerP = root.findViewById(R.id.containerP);
        bienvenida = root.findViewById(R.id.title1);
        pbLoading = root.findViewById(R.id.pbLoading);

        //final ItemForm form = new ItemForm(getActivity(),preguntas);

        recommendationsViewModel.SetUI().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean s) {
                if(s){
                    containerP.removeAllViewsInLayout();
                    title_form.setVisibility(View.VISIBLE);
                    sendB.setVisibility(View.VISIBLE);
                    new getUserFormInBackground().execute();
                }
                else{
                    //containerP.removeAllViewsInLayout();
                    // agregar un restaurador de vista
                    title_form.setVisibility(View.INVISIBLE);
                    sendB.setVisibility(View.INVISIBLE);
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
                    startActivityForResult(activityIntent, LAUNCH_RECOMMENDATION_RESULT_ACTIVITY);
                }else{
                    Toast.makeText(getActivity(), "Faltan campos por llenar: ", Toast.LENGTH_LONG).show();
                }
            }
        });

        BtnStart.setOnClickListener(new View.OnClickListener() {
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
            Toast.makeText(getActivity(), "Exito", Toast.LENGTH_SHORT).show();
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
        protected Form doInBackground(Void... voids) {
            Form userForm = new Form();
            ConnectionManager cm = new ConnectionManager();
            try{
                userForm=cm.getUserForm();
            }catch (IOException e){
                e.printStackTrace();
            }
            return userForm;
        }

        @Override
        protected void onPostExecute(Form form) {
            setForm(form);
            pbLoading.setVisibility(View.GONE);
        }
    }
}

