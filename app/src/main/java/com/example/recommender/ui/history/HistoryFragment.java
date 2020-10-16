package com.example.recommender.ui.history;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.recommender.R;
import com.example.recommender.ui.recommendations.RecomResult;

public class HistoryFragment extends Fragment implements View.OnClickListener{

    private HistoryViewModel historyViewModel;
    ResultContainer rc;
    private int LAUNCH_RECOMMENDATION_RESULT_ACTIVITY = 3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                ViewModelProviders.of(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        final TextView textView = root.findViewById(R.id.text_history);
        LinearLayout lparent = root.findViewById(R.id.idLinearLRows);
        ItemResult itres = new ItemResult(getContext(),8);
        this.rc=itres.initContainers(lparent);
        for(int x = 0; x<rc.getButtonForm().size(); x++){
            rc.getButtonForm().get(x).setOnClickListener(this);
            rc.getButtonRecom().get(x).setOnClickListener(this);
        }

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_RECOMMENDATION_RESULT_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(getActivity(), "El dato es: "+data.getStringExtra("newpass"), Toast.LENGTH_SHORT).show();
                //editTextP.setText(data.getStringExtra("newpass"));
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
                //Write your code if there's no result
            }
        }
    }
    private void loadForms(int idButton){
        Toast.makeText(getActivity(),"id Form "+idButton,Toast.LENGTH_SHORT).show();

    }
    private void loadRecom(int idButton,String idRes){
        Toast.makeText(getActivity(),"id Recom "+idButton,Toast.LENGTH_SHORT).show();
        Intent activityIntent = new Intent(getActivity(), RecomResult.class);
        activityIntent.putExtra("idResult", "Resultado R-231020-0123"); // AQUI EL VALOR ERA idRes
        startActivityForResult(activityIntent, LAUNCH_RECOMMENDATION_RESULT_ACTIVITY);
    }

    @Override
    public void onClick(View view) {
        for(int x = 0; x<rc.getButtonForm().size(); x++){
            if(view.getId()==rc.getButtonForm().get(x).getId()){
                loadForms(view.getId());
                break;
            }else if(view.getId()==rc.getButtonRecom().get(x).getId()){
                loadRecom(view.getId(),rc.getIdsRes().get(x));  // CAMBIO
                break;
            }
        }
    }
}