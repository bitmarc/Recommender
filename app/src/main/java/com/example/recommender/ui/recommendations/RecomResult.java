package com.example.recommender.ui.recommendations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recommender.R;

public class RecomResult extends AppCompatActivity {
    private String text;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recom_result);
        Intent intent = getIntent();
        //TODO: FALTA IMPLEMENTAR EL OBJETO SERIALIZABLE
        //Proyecto  proyectoseleccionado = (Proyecto) getIntent().getSerializable("proyecto");
        // RECIBE OBJETO COMO PARAMETRO
        text=intent.getStringExtra("idResult");
        tvTitle=findViewById(R.id.idTvResult);
        tvTitle.setText("result "+this.text);
    }
}