package com.example.recommender.ui.recommendations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.recommender.R;
import com.example.recommender.connection.ConnectionManager;
import com.example.recommender.entities.Recommendation;
import com.example.recommender.form.Form;

import java.io.IOException;

public class RecomResult extends AppCompatActivity {
    TextView tvTitle;
    Form form;
    ProgressBar pbar;
    Recommendation recom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recom_result);
        Intent intent = getIntent();
        this.form=(Form)intent.getSerializableExtra("Form");
        tvTitle=findViewById(R.id.idTvResult);
        pbar=findViewById(R.id.idProgressBar);
        tvTitle.setText("result ");
        new getUserRecomInBackground().execute(this.form);
    }

    class getUserRecomInBackground extends AsyncTask<Form, Void, Recommendation> {
        @Override
        protected void onPreExecute() {
            pbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Recommendation doInBackground(Form... forms) {
            Recommendation userRecom = new Recommendation();
            ConnectionManager cm = new ConnectionManager();
            try{
                userRecom=cm.getRecommendation(forms[0]);
            }catch (IOException e){
                e.printStackTrace();
            }
            return userRecom;
        }

        @Override
        protected void onPostExecute(Recommendation recom) {
            setRecom(recom);
            pbar.setVisibility(View.GONE);
        }
    }

    public void setRecom(Recommendation recom){
        this.recom=recom;
        this.tvTitle.setText("Recibido ok ok ok "+recom.getIdRecommendation());
    }
}

