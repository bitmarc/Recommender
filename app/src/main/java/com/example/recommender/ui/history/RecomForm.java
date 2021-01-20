package com.example.recommender.ui.history;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.recommender.R;
import com.example.recommender.entities.Profile;
import com.example.recommender.entities.Recommendation;
import com.example.recommender.entities.RequestResult;
import com.example.recommender.form.Form;
import com.example.recommender.ui.recommendations.ItemForm;

public class RecomForm extends AppCompatActivity {
    private LinearLayout containerP;
    private Button btnOk;
    private TextView title;
    private String type;
    private RequestResult reqRes;
    private int idReq;

    Form userForm;
    ItemForm formBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recom_form);
        containerP = findViewById(R.id.lLparent);
        btnOk = findViewById(R.id.btnOk);
        title = findViewById(R.id.tvTitleRecomForm);
        Intent intent = getIntent();
        this.type=intent.getStringExtra("type");
        if(type.equals("view")){
            System.out.println("el tipo es vista!!");
            this.reqRes=(RequestResult) intent.getSerializableExtra("reqRes");
            userForm=reqRes.getForm();
            idReq=reqRes.getId();
            buildFormToView(userForm);
        }
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent); // You can also send result without any data using setResult(int resultCode)
                finish();
            }
        });
    }

    private void buildFormToView(Form userForm) {
        title.setText("Respuestas de la solicitud #"+idReq);
        this.formBuilder = new ItemForm(getApplicationContext(),userForm);
        formBuilder.initContainers(getApplicationContext(),containerP);
        formBuilder.setResults();
    }
}