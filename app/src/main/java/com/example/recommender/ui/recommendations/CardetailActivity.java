package com.example.recommender.ui.recommendations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.recommender.R;
import com.example.recommender.connection.ConnectionManager;
import com.example.recommender.entities.Attribute;
import com.example.recommender.entities.Automobile;
import com.example.recommender.entities.Datasheet;
import com.example.recommender.entities.ScoreSheet;

import java.io.IOException;
import java.util.ArrayList;

public class CardetailActivity extends AppCompatActivity implements View.OnClickListener {
    public Automobile auto;
    private ProgressBar pbar;
    private Datasheet datasheet;
    private ConstraintLayout contenedor; //reutilizada
    private LinearLayout contenedorA;
    private TextView descriptionFavor, descriptionContra, attrib, automovil;
    private Button seeMore, bAceptar;
    private int idButton;
    private LinearLayout parent;
    private LinearLayout.LayoutParams layoutParamsContainerL = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
    private ConstraintLayout.LayoutParams layoutParamsContainer = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    private LinearLayout.LayoutParams layoutParamsP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardetail);
        this.pbar=findViewById(R.id.detailPbar);
        this.parent=findViewById(R.id.detailContainerP);
        this.automovil = findViewById(R.id.detailAutoName);
        this.bAceptar = findViewById(R.id.detailsidButtonBack);
        Intent intent = getIntent();
        this.auto= (Automobile) intent.getSerializableExtra("auto");
        new getAutoDetailInBackground().execute(auto);

        bAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }

    public void initContainers(Context context, Datasheet datasheet){
        float scale = context.getResources().getDisplayMetrics().density;
        layoutParamsP.setMargins(0, toPixels(20,scale), 0, 0);
        this.parent.addView(createItemFavorContra(context, datasheet.getScoreSheet(), datasheet.getAttributes()),layoutParamsP);
    }

    private ConstraintLayout createItemFavorContra(Context context, ScoreSheet score, ArrayList<Attribute> atributos) {
        float scale = context.getResources().getDisplayMetrics().density;
        contenedor = new ConstraintLayout(context);
        contenedor.setId(View.generateViewId());
        contenedor.setLayoutParams(layoutParamsContainer);

        descriptionFavor = new TextView(context);
        descriptionFavor.setId(View.generateViewId());
        descriptionFavor.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        descriptionFavor.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        descriptionFavor.setPadding(5,0,0,0);
        descriptionFavor.setElegantTextHeight(true);
        descriptionFavor.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        descriptionFavor.setSingleLine(false);
        String sourceString = "<b>" + "Puntos a favor: " + "</b> <br> " + score.getPositive() + "<br>";
        descriptionFavor.setText(Html.fromHtml(sourceString));
        descriptionFavor.setBackgroundResource(R.drawable.tvback);//*********************
        contenedor.addView(descriptionFavor);

        descriptionContra = new TextView(context);
        descriptionContra.setId(View.generateViewId());
        descriptionContra.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        descriptionContra.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        descriptionContra.setPadding(5,0,0,0);
        descriptionContra.setElegantTextHeight(true);
        descriptionContra.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        descriptionContra.setSingleLine(false);
        sourceString = "<b>" + "Puntos en contra: " + "</b> <br> " + score.getNegative() + "<br>";
        descriptionContra.setText(Html.fromHtml(sourceString));
        descriptionContra.setBackgroundResource(R.drawable.tvback);//*********************
        contenedor.addView(descriptionContra);

        createItemAttribs(context,atributos);
        contenedorA.setBackgroundResource(R.drawable.tvback);//*********************
        contenedor.addView(contenedorA);

        seeMore = new Button(context);
        seeMore.setId(View.generateViewId());
        seeMore.setText(R.string.see_more_btn);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,toPixels(10,scale),0,toPixels(10,scale));
        seeMore.setLayoutParams(params);
        contenedor.addView(seeMore);
        seeMore.setOnClickListener(this);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(contenedor);
        constraintSet.connect(descriptionFavor.getId(), ConstraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(descriptionFavor.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);
        constraintSet.connect(descriptionFavor.getId(), ConstraintSet.TOP, contenedor.getId(), constraintSet.TOP);

        constraintSet.connect(descriptionContra.getId(), ConstraintSet.TOP, descriptionFavor.getId(), constraintSet.BOTTOM);
        constraintSet.connect(descriptionContra.getId(), ConstraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(descriptionContra.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);

        constraintSet.connect(contenedorA.getId(), ConstraintSet.TOP, descriptionContra.getId(), constraintSet.BOTTOM);
        constraintSet.connect(contenedorA.getId(), ConstraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(contenedorA.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);

        constraintSet.connect(seeMore.getId(), ConstraintSet.TOP, contenedorA.getId(), constraintSet.BOTTOM);
        constraintSet.connect(seeMore.getId(), ConstraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(seeMore.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);
        constraintSet.connect(seeMore.getId(), ConstraintSet.BOTTOM, contenedor.getId(), constraintSet.BOTTOM);
        
        constraintSet.applyTo(contenedor);
        //contenedor.setBackgroundColor(getResources().getColor(R.color.recomBox2));
        //contenedor.setBackgroundResource(R.drawable.containerback);
        return contenedor;
    }

    private void createItemAttribs(Context context, ArrayList<Attribute> atributos) {
        float scale = context.getResources().getDisplayMetrics().density;
        contenedorA = new LinearLayout(context);
        contenedorA.setId(View.generateViewId());
        contenedorA.setLayoutParams(layoutParamsContainerL);//**************
        contenedorA.setOrientation(LinearLayout.VERTICAL);
        layoutParamsContainerL.setMargins(0, toPixels(0,scale), 0, 0);

        for(int i=0;i<atributos.size();i++){
            attrib = new TextView(context);
            attrib.setId(View.generateViewId());
            attrib.setPadding(5,0,0,0);
            attrib.setElegantTextHeight(true);
            String sourceString = "<b>" + atributos.get(i).getName() + "</b> :" + atributos.get(i).getDescription() + "<br>";
            attrib.setText(Html.fromHtml(sourceString));
            contenedorA.addView(attrib,layoutParamsContainerL);
        }
    }


    private int toPixels(int dp, float scale) {
        int pixels = (int) (dp * scale + 0.5f);
        return pixels;
    }

    public void setDatasheet(Datasheet datasheet){
        this.datasheet=datasheet;
        this.automovil.setText(this.auto.getBrand()+" "+this.auto.getModel()+" "+this.auto.getYear()+" "+this.auto.getVersion());
        initContainers(getApplication(),datasheet);
    }

    @Override
    public void onClick(View view) {
        // para abrir enlace web
        Uri uri = Uri.parse(this.datasheet.getScoreSheet().getSeeMore()); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    class getAutoDetailInBackground extends AsyncTask<Automobile, Void, Datasheet> {
    @Override
    protected void onPreExecute() {
        pbar.setVisibility(View.VISIBLE);
    }
    @Override
    protected Datasheet doInBackground (Automobile...voids){
        Datasheet datasheet= new Datasheet();
        ConnectionManager cm = new ConnectionManager();
        try {
            datasheet = cm.getDatasheet(voids[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datasheet;
    }
    @Override
    protected void onPostExecute (Datasheet datasheet){
        setDatasheet(datasheet);
        pbar.setVisibility(View.GONE);
    }
    }

}