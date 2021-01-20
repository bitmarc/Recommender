package com.example.recommender.ui.recommendations;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;

import com.example.recommender.form.Form;
import com.example.recommender.form.Question;
import com.example.recommender.R;

import java.util.ArrayList;
import java.util.List;

public class ItemForm extends ConstraintLayout implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private ArrayList<Integer> idsContenedores, idsSpiners, idsHints;
    private ArrayList<Spinner> arrSpiners;
    private Form userForm;
    private TextView title;
    private Spinner spiner;
    private Button imageHint;
    private ConstraintLayout contenedor;
    private ConstraintLayout.LayoutParams layoutParamsContainer = new LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    private LinearLayout.LayoutParams layoutParamsP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private int counter;
    private Typeface containerValuesFont;
    List<List<Integer>> dynamic2D = new ArrayList<List<Integer>>(); //almaceno los id's de cada respuesta para recuperarlos mediante su posicion


    public ItemForm(Context context, Form userForm) {
        super(context);
        this.userForm=userForm;
        idsContenedores = new ArrayList<>();
        idsSpiners = new ArrayList<>();
        arrSpiners = new ArrayList<>();
        idsHints = new ArrayList<>();
        this.counter=0;
        this.containerValuesFont= ResourcesCompat.getFont(context,R.font.franklin_gothic_demi_cond);
    }

    public void initContainers(Context context, LinearLayout parent) {
        float scale = context.getResources().getDisplayMetrics().density;
        layoutParamsP.setMargins(0, toPixels(20,scale), 0, 0);
        for (int i = 0; i < userForm.getArrQuestions().size(); i++) {
            System.out.println("for . "+userForm.getArrQuestions().size());
            dynamic2D.add(new ArrayList<Integer>());
            parent.addView(createItem(context, userForm.getArrQuestions().get(i),i),layoutParamsP);
        }
    }

    private ConstraintLayout createItem(Context context, Question question,int count) {
        contenedor = new ConstraintLayout(context);
        contenedor.setId(View.generateViewId());
        contenedor.setLayoutParams(layoutParamsContainer);

        final float scale = context.getResources().getDisplayMetrics().density;
        title = new TextView(context);
        title.setId(View.generateViewId());
        title.setText(idsContenedores.size()+1+".- "+question.getTitle());
        title.setTypeface(containerValuesFont);
        title.setTextSize(getResources().getDimension(R.dimen.normal_text_questions));//***************
        title.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        LayoutParams tvLp= new LayoutParams(LayoutParams.MATCH_CONSTRAINT, LayoutParams.WRAP_CONTENT);
        //ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(LayoutParams.MATCH_CONSTRAINT, LayoutParams.WRAP_CONTENT);
        title.setPadding(toPixels(10,scale),toPixels(5,scale),toPixels(5,scale),toPixels(5,scale));
        title.setLayoutParams(tvLp);
        //title.setBackgroundResource(R.drawable.containerback);
        contenedor.addView(title);

        LayoutParams spinLp= new LayoutParams(LayoutParams.MATCH_CONSTRAINT, LayoutParams.WRAP_CONTENT);
        spinLp.setMargins(0,toPixels(3,scale),0,toPixels(3,scale));
        spiner = new Spinner(context);
        spiner.setId(View.generateViewId());
        String[] optionsArray = new String[question.getOptions().size()+1];// inicializo un string de los elementos
        for(int x=0;x<question.getOptions().size()+1;x++){

            if(x==0)
                optionsArray[x]="Selecciona";
            else{
                optionsArray[x]=question.getOptions().get(x-1).getTitle();
                dynamic2D.get(count).add(question.getOptions().get(x-1).getId());}

        }
        ArrayAdapter ad = new ArrayAdapter(context, R.layout.dropdownlayout, optionsArray);
        ad.setDropDownViewResource(R.layout.custom_dropdown_spinner_item);
        spiner.setAdapter(ad);
        spiner.setLayoutParams(spinLp);
        contenedor.addView(spiner);
        idsSpiners.add(spiner.getId());
        arrSpiners.add(spiner);
        spiner.setOnItemSelectedListener(this);

        imageHint = new Button(context);
        imageHint.setId(View.generateViewId());
        imageHint.setBackground(context.getDrawable(R.drawable.ic_baseline_help_outline_24));
        imageHint.setLayoutParams(new LinearLayout.LayoutParams(toPixels(25, scale), toPixels(25, scale)));
        imageHint.setOnClickListener(this);
        idsHints.add(imageHint.getId());
        contenedor.addView(imageHint);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(contenedor);

        constraintSet.connect(title.getId(), constraintSet.TOP, contenedor.getId(), constraintSet.TOP);
        constraintSet.connect(title.getId(), constraintSet.START, contenedor.getId(), constraintSet.START);
        constraintSet.connect(imageHint.getId(), ConstraintSet.TOP, contenedor.getId(), constraintSet.TOP);
        constraintSet.connect(imageHint.getId(), ConstraintSet.END, contenedor.getId(), constraintSet.END);
        constraintSet.connect(title.getId(), constraintSet.END, imageHint.getId(), constraintSet.START);
        constraintSet.connect(spiner.getId(), ConstraintSet.TOP, title.getId(), constraintSet.BOTTOM);

        constraintSet.applyTo(contenedor);
        idsContenedores.add(contenedor.getId());
        contenedor.setBackgroundColor(getResources().getColor(R.color.colorBackgroundContainer));
        return contenedor;
    }

    public boolean getStatus(){
        boolean status= true;
        for (int j=0;j<userForm.getArrQuestions().size();j++){
            if(userForm.getArrQuestions().get(j).getAnswer()!=0){
                System.out.println("correcto "+userForm.getArrQuestions().size()+" j:"+j);
            }else{
                status=false;
                break;
            }
        }
        return status;
    }

    public void setResults(){
        for (int k=0;k<idsSpiners.size();k++){
            for (int l=0;l<userForm.getArrQuestions().get(k).getOptions().size();l++){
                if(userForm.getArrQuestions().get(k).getOptions().get(l).getId()==userForm.getArrQuestions().get(k).getAnswer()){
                    arrSpiners.get(k).setSelection(l+1);
                    break;
                }
            }
            arrSpiners.get(k).setEnabled(false);
        }
    }

    private int toPixels(int dp, float scale) {
        int pixels = (int) (dp * scale + 0.5f);
        return pixels;
    }

    public List<Question> getQuestions() {
        return userForm.getArrQuestions();
    }

    public Form getForm() {
        return userForm;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getItemAtPosition(position).equals("Selecciona")){
            //do nothing
        }else{
            for(int i=0;i<idsSpiners.size();i++)
            if(parent.getId()==idsSpiners.get(i)){
                userForm.getArrQuestions().get(i).setAnswer(dynamic2D.get(i).get(position-1));//antes solo guardaba la posicion 1-3
                //Toast.makeText(parent.getContext(), "Selected: " + userForm.getArrQuestions().get(i).getTitle()+" id_POS: "+dynamic2D.get(i).get(position-1), Toast.LENGTH_LONG).show();
                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }


    @Override
    public void onClick(View view) {
        int[] location= new int[2];
        view.getLocationOnScreen(location);
        for(int x = 0; x<idsHints.size(); x++){
            if(view.getId()==idsHints.get(x)){
                Toast toast= Toast.makeText(getContext(), userForm.getArrQuestions().get(x).getHint(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP|Gravity.LEFT, location[0],location[1]);
                toast.show();
                break;
            }
        }
    }
}