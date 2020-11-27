package com.example.recommender.ui.recommendations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recommender.R;
import com.example.recommender.connection.ConnectionManager;
import com.example.recommender.entities.Automobile;
import com.example.recommender.entities.Recommendation;
import com.example.recommender.entities.User;
import com.example.recommender.form.Form;
import com.example.recommender.retrofit.models.History;
import com.example.recommender.retrofit.models.RecommendationRequest;

import java.io.IOException;
import java.util.ArrayList;

public class RecomResult extends AppCompatActivity implements View.OnClickListener{
    private TextView tvnResults, tvProfile;
    private ProgressBar pbar;
    private LinearLayout containerP;
    private ConstraintLayout contenedor; //reutilizada
    private Button seeMore, bAceptar;
    private TextView nRecom, brand, model, year, version; // reutilizada
    private ArrayList<Integer> idsButtons, idsAutos;
    private ConstraintLayout.LayoutParams layoutParamsContainer = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    private LinearLayout.LayoutParams layoutParamsP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private Form form;
    private User user;
    private int idBAceptar;
    private String type;
    private History history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recom_result);
        tvnResults=findViewById(R.id.idTvResult);
        tvProfile=findViewById(R.id.idTvProfile);
        bAceptar = findViewById(R.id.bAceptar);
        pbar=findViewById(R.id.idProgressBar);
        containerP=findViewById(R.id.containerP);
        bAceptar.setOnClickListener(this);
        idBAceptar=bAceptar.getId();
        idsButtons = new ArrayList<>();
        idsAutos = new ArrayList<>();

        Intent intent = getIntent();
        this.type=intent.getStringExtra("type");
        if(type.equals("view")){
            this.history=(History)intent.getSerializableExtra("History");
            System.out.println("AQUI LANZA NUEVA FORMA");

        }else{
            this.form=(Form)intent.getSerializableExtra("Form");
            this.user=(User)intent.getSerializableExtra("User");
            new getUserRecomInBackground().execute(new RecommendationRequest(form,user));
        }
    }

    // Construccion de contenedores
    public void initContainers(Context context, LinearLayout parent, Recommendation recomendaation){
        float scale = context.getResources().getDisplayMetrics().density;
        layoutParamsP.setMargins(0, toPixels(20,scale), 0, 0);
        for (int i = 0; i < recomendaation.getResults().size(); i++) {
            parent.addView(createItem(context, recomendaation.getResults().get(i),i),layoutParamsP);
        }
    }

    // Definición de item
    private ConstraintLayout createItem(Context context, Automobile auto, int count) {
        contenedor = new ConstraintLayout(context);
        contenedor.setId(View.generateViewId());
        contenedor.setLayoutParams(layoutParamsContainer);

        idsAutos.add(auto.getId());

        nRecom = new TextView(context);
        nRecom.setId(View.generateViewId());
        nRecom.setText("Recomendación No. "+(count+1));
        nRecom.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        contenedor.addView(nRecom);

        brand = new TextView(context);
        brand.setId(View.generateViewId());
        brand.setText("MARCA: "+auto.getBrand());
        brand.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        contenedor.addView(brand);

        model = new TextView(context);
        model.setId(View.generateViewId());
        model.setText("MODELO: "+auto.getModel());
        model.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        contenedor.addView(model);

        year = new TextView(context);
        year.setId(View.generateViewId());
        year.setText("AÑO: "+auto.getYear());
        year.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        contenedor.addView(year);

        version = new TextView(context);
        version.setId(View.generateViewId());
        version.setText("VERSION: "+auto.getVersion());
        version.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        contenedor.addView(version);

        seeMore = new Button(context);
        seeMore.setId(View.generateViewId());
        seeMore.setText("VER FICHA TÉCNICA");
        //final float scale = context.getResources().getDisplayMetrics().density;
        //seeMore.setLayoutParams(new LinearLayout.LayoutParams(toPixels(50, scale), toPixels(25, scale)));
        contenedor.addView(seeMore);
        seeMore.setOnClickListener(this);
        idsButtons.add(seeMore.getId());

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(contenedor);
        constraintSet.connect(nRecom.getId(), constraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(nRecom.getId(), constraintSet.END, contenedor.getId(), constraintSet.END);
        constraintSet.connect(nRecom.getId(), constraintSet.TOP, contenedor.getId(), constraintSet.TOP);

        constraintSet.connect(brand.getId(), ConstraintSet.TOP, nRecom.getId(), constraintSet.BOTTOM);
        constraintSet.connect(brand.getId(), ConstraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(brand.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);

        constraintSet.connect(model.getId(), ConstraintSet.TOP, brand.getId(), constraintSet.BOTTOM);
        constraintSet.connect(model.getId(), ConstraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(model.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);

        constraintSet.connect(year.getId(), ConstraintSet.TOP, model.getId(), constraintSet.BOTTOM);
        constraintSet.connect(year.getId(), ConstraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(year.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);

        constraintSet.connect(version.getId(), ConstraintSet.TOP, year.getId(), constraintSet.BOTTOM);
        constraintSet.connect(version.getId(), ConstraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(version.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);

        constraintSet.connect(seeMore.getId(), ConstraintSet.TOP, version.getId(), constraintSet.BOTTOM);
        constraintSet.connect(seeMore.getId(), ConstraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(seeMore.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);
        constraintSet.applyTo(contenedor);
        return contenedor;
    }

    // Funcion de conversion a pixeles
    private int toPixels(int dp, float scale) {
        int pixels = (int) (dp * scale + 0.5f);
        return pixels;
    }

    // publicacion de resultados
    public void setRecom(Recommendation recom){
        this.tvnResults.setText("Resultado No. "+recom.getIdRecommendation());
        this.tvProfile.setText("Perfil: "+recom.getProfile().getName());
        initContainers(getApplication(), containerP, recom);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==this.idBAceptar){
            regresar();
        }else{
            for(int i =0;i<idsButtons.size();i++) {
                System.out.println("for");
                if (view.getId() == idsButtons.get(i)) {
                    Toast.makeText(this, "id: "+idsAutos.get(i), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void regresar(){
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent); // You can also send result without any data using setResult(int resultCode)
        finish();
    }

    // Peticion de recomendacion
    class getUserRecomInBackground extends AsyncTask<RecommendationRequest, Void, Recommendation> {
        @Override
        protected void onPreExecute() {
            pbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Recommendation doInBackground(RecommendationRequest... Rreq) {
            Recommendation userRecom = new Recommendation();
            ConnectionManager cm = new ConnectionManager();
            try{
                userRecom=cm.getRecommendation(Rreq[0]);
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
}

