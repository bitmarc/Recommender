package com.example.recommender.ui.recommendations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recommender.R;
import com.example.recommender.connection.ConnectionManager;
import com.example.recommender.entities.Automobile;
import com.example.recommender.entities.Profile;
import com.example.recommender.entities.Recommendation;
import com.example.recommender.entities.RequestResult;
import com.example.recommender.entities.ScoreSheet;
import com.example.recommender.entities.User;
import com.example.recommender.form.Form;
import com.example.recommender.retrofit.models.History;
import com.example.recommender.retrofit.models.RecommendationRequest;

import java.io.IOException;
import java.util.ArrayList;

public class RecomResult extends AppCompatActivity implements View.OnClickListener{
    private TextView tvnResults, tvProfile, tvProfileDescription;
    private ProgressBar pbar;
    private LinearLayout containerP;
    private ConstraintLayout contenedor; //reutilizada
    private Button seeMore, bAceptar;
    private TextView tRecom, nRecom, tbrand, brand, tmodel, model, tyear, year, tversion, version; // reutilizada
    private ArrayList<Integer> idsButtons, idsAutos;
    private ConstraintLayout.LayoutParams layoutParamsContainer = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    private LinearLayout.LayoutParams layoutParamsP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private Form form;
    private User user;
    private int idBAceptar;
    private Typeface franklin;
    private String type;
    private RequestResult reqRes;
    private ArrayList<Automobile> autos;
    private int LAUNCH_RECOMMENDATION_RESULT_ACTIVITY = 5; //random
    private LinearLayout boxContainer;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_RECOMMENDATION_RESULT_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                //Toast.makeText(getActivity(), "Limpiar pantalla ", Toast.LENGTH_SHORT).show();
                System.out.println("regresa a pantalla con boton aceptar");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
                System.out.println("regresa a pantalla con retroceso");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recom_result);
        tvnResults=findViewById(R.id.idTvResult);
        tvProfile=findViewById(R.id.idTvProfile);
        tvProfileDescription = findViewById(R.id.idTvProfileDescription);
        bAceptar = findViewById(R.id.bAceptar);
        pbar=findViewById(R.id.idProgressBar);
        containerP=findViewById(R.id.containerP);
        bAceptar.setOnClickListener(this);
        idBAceptar=bAceptar.getId();
        idsButtons = new ArrayList<>();
        idsAutos = new ArrayList<>();
        this.franklin= ResourcesCompat.getFont(this,R.font.franklin_gothic_demi_cond);

        Intent intent = getIntent();
        this.type=intent.getStringExtra("type");
        if(type.equals("view")){
            this.reqRes=(RequestResult) intent.getSerializableExtra("reqRes");
            Recommendation recom=new Recommendation();
            recom.setIdRecommendation(reqRes.getId());
            recom.setResults(reqRes.getAutos());
            recom.setProfile(reqRes.getProfile());
            setRecom(recom);
            this.autos=reqRes.getAutos();
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
            System.out.println("Entre al for priincipal");
            parent.addView(createItem(context, recomendaation.getResults().get(i),i),layoutParamsP);
            //parent.addView(createBoxItem(context,recomendaation,i),layoutParamsP);
        }
    }

    // Definición de item
    private ConstraintLayout createItem(Context context, Automobile auto, int count) {
        float scale = context.getResources().getDisplayMetrics().density;
        contenedor = new ConstraintLayout(context);
        contenedor.setId(View.generateViewId());
        //ConstraintLayout.LayoutParams paramsItemContainer = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        contenedor.setLayoutParams(layoutParamsContainer);
        idsAutos.add(auto.getId());


        nRecom = new TextView(context);
        nRecom.setId(View.generateViewId());
        String sourceString = "<b>" + "Recomendación " + (count+1)+ "</b> ";
        nRecom.setText(""+(count+1));
        nRecom.setTypeface(franklin);
        nRecom.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        nRecom.setTextSize(getResources().getDimension(R.dimen.LargeFranklin));
        nRecom.setTextColor(getResources().getColor(R.color.colorPrimary));
        ConstraintLayout.LayoutParams paramsTvInd=new ConstraintLayout.LayoutParams(toPixels(130,scale), toPixels(90,scale));
        nRecom.setLayoutParams(paramsTvInd);
        contenedor.addView(nRecom);

        tRecom = new TextView(context);
        tRecom.setId(View.generateViewId());
        tRecom.setText("OPCIÓN");
        tRecom.setTypeface(franklin);
        tRecom.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        ConstraintLayout.LayoutParams paramsTvTit=new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        tRecom.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tRecom.setLayoutParams(paramsTvTit);
        contenedor.addView(tRecom);

        ConstraintLayout.LayoutParams paramsTvInf=new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        tbrand = new TextView(context);
        tbrand.setId(View.generateViewId());
        tbrand.setText("MARCA");
        tbrand.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        tbrand.setTextSize(getResources().getDimension(R.dimen.normal_text_titles));
        tbrand.setLayoutParams(paramsTvInf);
        contenedor.addView(tbrand);

        brand = new TextView(context);
        brand.setId(View.generateViewId());
        brand.setText(auto.getBrand());
        brand.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        brand.setTextSize(getResources().getDimension(R.dimen.normal_text_values));
        brand.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        contenedor.addView(brand);

        tmodel = new TextView(context);
        tmodel.setId(View.generateViewId());
        tmodel.setText("MODELO");
        tmodel.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        tmodel.setTextSize(getResources().getDimension(R.dimen.normal_text_titles));
        tmodel.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        contenedor.addView(tmodel);

        model = new TextView(context);
        model.setId(View.generateViewId());
        model.setText(auto.getModel());
        model.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        model.setTextSize(getResources().getDimension(R.dimen.normal_text_values));
        model.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        contenedor.addView(model);

        tyear = new TextView(context);
        tyear.setId(View.generateViewId());
        tyear.setText("AÑO");
        tyear.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        tyear.setTextSize(getResources().getDimension(R.dimen.normal_text_titles));
        tyear.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        contenedor.addView(tyear);

        year = new TextView(context);
        year.setId(View.generateViewId());
        year.setText(auto.getYear());
        year.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        year.setTextSize(getResources().getDimension(R.dimen.normal_text_values));
        year.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        contenedor.addView(year);

        tversion = new TextView(context);
        tversion.setId(View.generateViewId());
        tversion.setText("VERSION");
        tversion.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        tversion.setTextSize(getResources().getDimension(R.dimen.normal_text_titles));
        tversion.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        contenedor.addView(tversion);

        version = new TextView(context);
        version.setId(View.generateViewId());
        version.setText(auto.getVersion());
        version.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        version.setTextSize(getResources().getDimension(R.dimen.normal_text_values));
        version.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        contenedor.addView(version);

        seeMore = new Button(context);
        seeMore.setId(View.generateViewId());
        seeMore.setText("VER DETALLES");
        seeMore.setTypeface(franklin);
        seeMore.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        seeMore.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        seeMore.setTextSize(getResources().getDimension(R.dimen.normal_text_questions));
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,toPixels(25,scale));
        params.setMargins(0,toPixels(10,scale),0,toPixels(10,scale));
        seeMore.setLayoutParams(params);
        seeMore.setPadding(toPixels(2,scale),1,toPixels(2,scale),1);
        contenedor.addView(seeMore);
        seeMore.setOnClickListener(this);
        idsButtons.add(seeMore.getId());

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(contenedor);
        constraintSet.connect(nRecom.getId(), constraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(nRecom.getId(), constraintSet.TOP, contenedor.getId(), constraintSet.TOP);
        constraintSet.connect(nRecom.getId(), constraintSet.BOTTOM, contenedor.getId(), constraintSet.BOTTOM);
        constraintSet.connect(tRecom.getId(), constraintSet.START, nRecom.getId(), constraintSet.START);
        constraintSet.connect(tRecom.getId(), constraintSet.TOP, nRecom.getId(), constraintSet.BOTTOM);
        constraintSet.connect(tRecom.getId(), constraintSet.END, nRecom.getId(), constraintSet.END);

        constraintSet.connect(tbrand.getId(), ConstraintSet.TOP, contenedor.getId(), constraintSet.TOP);
        constraintSet.connect(tbrand.getId(), ConstraintSet.START, nRecom.getId(), constraintSet.END);
        constraintSet.connect(tbrand.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);
        constraintSet.connect(brand.getId(), ConstraintSet.TOP, tbrand.getId(), constraintSet.BOTTOM);
        constraintSet.connect(brand.getId(), ConstraintSet.START, nRecom.getId(), constraintSet.END);
        constraintSet.connect(brand.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);

        constraintSet.connect(tmodel.getId(), ConstraintSet.TOP, brand.getId(), constraintSet.BOTTOM);
        constraintSet.connect(tmodel.getId(), ConstraintSet.START, nRecom.getId(), constraintSet.END);
        constraintSet.connect(tmodel.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);
        constraintSet.connect(model.getId(), ConstraintSet.TOP, tmodel.getId(), constraintSet.BOTTOM);
        constraintSet.connect(model.getId(), ConstraintSet.START, nRecom.getId(), constraintSet.END);
        constraintSet.connect(model.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);

        constraintSet.connect(tyear.getId(), ConstraintSet.TOP, model.getId(), constraintSet.BOTTOM);
        constraintSet.connect(tyear.getId(), ConstraintSet.START, nRecom.getId(), constraintSet.END);
        constraintSet.connect(tyear.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);
        constraintSet.connect(year.getId(), ConstraintSet.TOP, tyear.getId(), constraintSet.BOTTOM);
        constraintSet.connect(year.getId(), ConstraintSet.START, nRecom.getId(), constraintSet.END);
        constraintSet.connect(year.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);

        constraintSet.connect(tversion.getId(), ConstraintSet.TOP, year.getId(), constraintSet.BOTTOM);
        constraintSet.connect(tversion.getId(), ConstraintSet.START, nRecom.getId(), constraintSet.END);
        constraintSet.connect(tversion.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);
        constraintSet.connect(version.getId(), ConstraintSet.TOP, tversion.getId(), constraintSet.BOTTOM);
        constraintSet.connect(version.getId(), ConstraintSet.START, nRecom.getId(), constraintSet.END);
        constraintSet.connect(version.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);

        constraintSet.connect(seeMore.getId(), ConstraintSet.TOP, version.getId(), constraintSet.BOTTOM);
        constraintSet.connect(seeMore.getId(), ConstraintSet.START, nRecom.getId(), constraintSet.END);
        constraintSet.connect(seeMore.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);
        constraintSet.connect(seeMore.getId(), ConstraintSet.BOTTOM, contenedor.getId(), constraintSet.BOTTOM);
        constraintSet.setHorizontalBias(seeMore.getId(),0);

        constraintSet.applyTo(contenedor);
        contenedor.setBackgroundColor(getResources().getColor(R.color.colorBackgroundContainer));
        return contenedor;
    }

    // Funcion de conversion a pixeles
    private int toPixels(int dp, float scale) {
        int pixels = (int) (dp * scale + 0.5f);
        return pixels;
    }

    // publicacion de resultados
    public void setRecom(Recommendation recom){
        this.tvnResults.setText("ID de resultado: "+recom.getIdRecommendation());
        this.tvProfile.setText("Perfil: "+recom.getProfile().getName());
        this.tvProfileDescription.setText("Descripción: "+recom.getProfile().getDescription());
        this.autos=recom.getResults();
        initContainers(getApplication(), containerP, recom);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==this.idBAceptar){
            regresar();
        }else{
            for(int i =0;i<idsButtons.size();i++) {
                if (view.getId() == idsButtons.get(i)) {
                    Intent activityIntent = new Intent(getApplicationContext(), CardetailActivity.class);
                    activityIntent.putExtra("auto",autos.get(i)); // AQUI EL VALOR ERA idRes
                    startActivityForResult(activityIntent, LAUNCH_RECOMMENDATION_RESULT_ACTIVITY);
                    //Toast.makeText(this, "id: "+idsAutos.get(i)+scores.get(i).getSeeMore(), Toast.LENGTH_SHORT).show();
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
            System.out.println("On post execute");
            setRecom(recom);
            pbar.setVisibility(View.GONE);

        }
    }
}

