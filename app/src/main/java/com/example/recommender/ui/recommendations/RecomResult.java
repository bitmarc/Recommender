package com.example.recommender.ui.recommendations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
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
    private TextView tvnResults, tvProfile;
    private ProgressBar pbar;
    private LinearLayout containerP;
    private ConstraintLayout contenedor; //reutilizada
    private Button seeMore, bAceptar;
    private TextView description;
    private TextView nRecom, brand, model, year, version; // reutilizada
    private ArrayList<Integer> idsButtons, idsAutos;
    private ConstraintLayout.LayoutParams layoutParamsContainer = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    private LinearLayout.LayoutParams layoutParamsP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private Form form;
    private User user;
    private int idBAceptar;
    private String type;
    private RequestResult reqRes;
    private ArrayList<ScoreSheet> scores;

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
            this.reqRes=(RequestResult) intent.getSerializableExtra("reqRes");
            System.out.println("AQUI LANZA NUEVA FORMA");
            Recommendation recom=new Recommendation();
            recom.setIdRecommendation(reqRes.getId());
            recom.setResults(reqRes.getAutos());
            Profile profile = new Profile(0,reqRes.getProfile(),"z"); // El perfil aun no contiene parametros
            recom.setProfile(profile);
            recom.setScores(reqRes.getScores());
            setRecom(recom);
            this.scores=reqRes.getScores();
        }else{
            this.form=(Form)intent.getSerializableExtra("Form");
            this.user=(User)intent.getSerializableExtra("User");
            System.out.println("Entro a solicitar la recomendacion");
            new getUserRecomInBackground().execute(new RecommendationRequest(form,user));
            System.out.println("Espero la recomendacion");
        }
    }

    // Construccion de contenedores
    public void initContainers(Context context, LinearLayout parent, Recommendation recomendaation){
        float scale = context.getResources().getDisplayMetrics().density;
        layoutParamsP.setMargins(0, toPixels(20,scale), 0, 0);
        for (int i = 0; i < recomendaation.getResults().size(); i++) {
            parent.addView(createItem(context, recomendaation.getResults().get(i),i, recomendaation.getScores().get(i)),layoutParamsP);
        }
    }

    // Definición de item
    private ConstraintLayout createItem(Context context, Automobile auto, int count, ScoreSheet score) {
        float scale = context.getResources().getDisplayMetrics().density;
        contenedor = new ConstraintLayout(context);
        contenedor.setId(View.generateViewId());
        contenedor.setLayoutParams(layoutParamsContainer);

        idsAutos.add(auto.getId());

        nRecom = new TextView(context);
        nRecom.setId(View.generateViewId());
        String sourceString = "<b>" + "Recomendación " + (count+1)+ "</b> ";
        nRecom.setText(Html.fromHtml(sourceString));
        nRecom.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        nRecom.setPadding(5,0,0,0);
        //nRecom.setText("Recomendación No. "+(count+1));
        nRecom.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        contenedor.addView(nRecom);

        brand = new TextView(context);
        brand.setId(View.generateViewId());
        sourceString = "<b>" + "MARCA: " + "</b> " + auto.getBrand();
        brand.setText(Html.fromHtml(sourceString));
        //brand.setText("MARCA: "+auto.getBrand());
        brand.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        contenedor.addView(brand);

        model = new TextView(context);
        model.setId(View.generateViewId());
        sourceString = "<b>" + "MODELO: " + "</b> " + auto.getModel();
        model.setText(Html.fromHtml(sourceString));
        //model.setText("MODELO: "+auto.getModel());
        model.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        contenedor.addView(model);

        year = new TextView(context);
        year.setId(View.generateViewId());
        sourceString = "<b>" + "AÑO: " + "</b> " + auto.getYear();
        year.setText(Html.fromHtml(sourceString));
        //year.setText("AÑO: "+auto.getYear());
        year.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        contenedor.addView(year);

        version = new TextView(context);
        version.setId(View.generateViewId());
        sourceString = "<b>" + "VERSION: " + "</b> " + auto.getVersion();
        version.setText(Html.fromHtml(sourceString));
        //version.setText("VERSION: "+auto.getVersion());
        version.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        contenedor.addView(version);

        description = new TextView(context);
        description.setId(View.generateViewId());
        description.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        description.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        description.setPadding(5,0,0,0);
        description.setElegantTextHeight(true);
        description.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        description.setSingleLine(false);
        sourceString = "<b>" + "A FAVOR: " + "</b> <br> " + score.getPositive() + "<br>"+"<b>" + "En contra: " + "</b> <br> "+score.getNegative();
        description.setText(Html.fromHtml(sourceString));
        description.setBackgroundResource(R.drawable.tvback);//*********************
        //description.setText(score.getPositive());
        contenedor.addView(description);

        seeMore = new Button(context);
        seeMore.setId(View.generateViewId());
        seeMore.setText("Más informacion");
        seeMore.setBackgroundColor(getResources().getColor(R.color.recomBox1));
        //seeMore.setLayoutParams(new LinearLayout.LayoutParams(toPixels(150,scale),toPixels(35,scale)));
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(toPixels(150,scale),toPixels(35,scale));
        params.setMargins(0,toPixels(10,scale),0,toPixels(10,scale));
        seeMore.setLayoutParams(params);
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

        constraintSet.connect(description.getId(), ConstraintSet.TOP, version.getId(), constraintSet.BOTTOM);
        constraintSet.connect(description.getId(), ConstraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(description.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);

        constraintSet.connect(seeMore.getId(), ConstraintSet.TOP, description.getId(), constraintSet.BOTTOM);
        constraintSet.connect(seeMore.getId(), ConstraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(seeMore.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);
        constraintSet.connect(seeMore.getId(), ConstraintSet.BOTTOM, contenedor.getId(), constraintSet.BOTTOM);
        constraintSet.applyTo(contenedor);
        //contenedor.setBackgroundColor(getResources().getColor(R.color.recomBox2));
        contenedor.setBackgroundResource(R.drawable.containerback);
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
        this.scores=recom.getScores();
        System.out.println("Empieza Init containers ok");
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
                    Uri uri = Uri.parse(scores.get(i).getSeeMore()); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
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

