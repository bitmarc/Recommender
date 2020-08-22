package com.example.recommender.ui.recommendations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.recommender.Question;
import com.example.recommender.R;

import java.util.ArrayList;

public class RecommendationsFragment extends Fragment {

    private RecommendationsViewModel recommendationsViewModel;
    private ArrayList<Question> preguntas;
    private LinearLayout containerP;
    private TextView bienvenida;
    private TextView title_form;
    private Button BtnStart;
    private Button sendB;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        recommendationsViewModel = ViewModelProviders.of(this).get(RecommendationsViewModel.class);

        Question q1 = new Question(1,"Cual es tu color favorito?");
        q1.setOption("Selecciona");
        q1.setOption("el azul");
        q1.setOption("El verde");
        q1.setOption("El amarillo oo oo oo oo o o o o o o o");
        Question q2 = new Question(2,"Cual es tu nuemro favorito?");
        q2.setOption("Selecciona");
        q2.setOption("el uno");
        q2.setOption("El dos");
        q2.setOption("El tres");
        Question q3 = new Question(3,"Cual es tu dia favorito?");
        q3.setOption("Selecciona");
        q3.setOption("el lunes");
        q3.setOption("El martes");
        q3.setOption("El el miercoles");
        Question q4 = new Question(4,"Cual es tu amigo preferido?");
        q4.setOption("Selecciona");
        q4.setOption("el bunny");
        q4.setOption("El pope");
        q4.setOption("El el marciano");
        Question q5 = new Question(5,"Cual es tu vdeojuego favorito?");
        q5.setOption("Selecciona");
        q5.setOption("el eish");
        q5.setOption("El fortnite");
        q5.setOption("El overwacho");
        Question q6 = new Question(6,"Cual es tu crush favorita?");
        q5.setOption("Selecciona");
        q5.setOption(" aki san");
        q5.setOption(" serenity");
        q5.setOption("Ari");
        preguntas = new ArrayList<Question>();
        preguntas.add(q1);
        preguntas.add(q2);
        preguntas.add(q3);
        preguntas.add(q4);
        preguntas.add(q5);
        preguntas.add(q6);


        View root = inflater.inflate(R.layout.fragment_recommendations, container, false);
        title_form = root.findViewById(R.id.text_recommendations);
        BtnStart = root.findViewById(R.id.idBsttart);
        sendB = root.findViewById(R.id.sendB);
        containerP = root.findViewById(R.id.containerP);
        bienvenida = root.findViewById(R.id.title1);

        final ItemForm form = new ItemForm(getActivity(),preguntas);

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
                ItemForm form = new ItemForm(getActivity(),preguntas);
                form.initContainers(getActivity(),containerP);
            }
        });

        return root;
    }
}