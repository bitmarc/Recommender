package com.example.recommender.ui.recommendations;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.recommender.User;
import com.example.recommender.UserResponse;
import com.example.recommender.connection.ConnectionManager;
import com.example.recommender.form.Form;
import com.example.recommender.form.Question;
import com.example.recommender.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecommendationsFragment extends Fragment {

    private RecommendationsViewModel recommendationsViewModel;
    private List<Question> preguntas;
    private LinearLayout containerP;
    private TextView bienvenida;
    private TextView title_form;
    private Button BtnStart;
    private Button sendB;
    private ProgressBar pbLoading;
    private ItemForm form;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        recommendationsViewModel = ViewModelProviders.of(this).get(RecommendationsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_recommendations, container, false);
        title_form = root.findViewById(R.id.text_recommendations);
        BtnStart = root.findViewById(R.id.idBsttart);
        sendB = root.findViewById(R.id.sendB);
        containerP = root.findViewById(R.id.containerP);
        bienvenida = root.findViewById(R.id.title1);
        pbLoading = root.findViewById(R.id.pbLoading);

        //final ItemForm form = new ItemForm(getActivity(),preguntas);

/*        recommendationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                sendB.setVisibility(View.GONE);
                title_form.setText(s);
            }
        });*/

        sendB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(form.getStatus()){
                    preguntas=form.getQuestions();
                    Toast.makeText(getActivity(), "Lanzar recomendacion ", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(), "Faltan campos por llenar: ", Toast.LENGTH_LONG).show();
                }
            }
        });


        BtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                containerP.removeAllViewsInLayout();
                title_form.setVisibility(View.VISIBLE);
                sendB.setVisibility(View.VISIBLE);
                new getUserFormInBackground().execute();
            }
        });

        return root;
    }

    public void setForm(Form userForm){
        if(userForm.getId()!=null){
            Toast.makeText(getActivity(), "Exito", Toast.LENGTH_SHORT).show();
            this.form = new ItemForm(getActivity(),userForm);
            form.initContainers(getActivity(),containerP);
        }
        else
        System.out.println("objeto form vacio");
    }

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

