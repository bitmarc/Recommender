package com.example.recommender.ui.history;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.recommender.MainActivity;
import com.example.recommender.R;
import com.example.recommender.connection.ConnectionManager;
import com.example.recommender.entities.User;
import com.example.recommender.retrofit.models.History;
import com.example.recommender.ui.recommendations.RecomResult;

import java.io.IOException;

public class HistoryFragment extends Fragment implements View.OnClickListener{
    private MainActivity activity;
    private User user;
    private HistoryViewModel historyViewModel;
    private ResultContainer rc;
    private int LAUNCH_RECOMMENDATION_RESULT_ACTIVITY_FOR_VIEW = 4;
    private ProgressBar pbLoading;
    private LinearLayout lparent;
    private TextView title;
    private History history;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        this.user= activity.getUser();
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        title = root.findViewById(R.id.text_history);
        lparent = root.findViewById(R.id.idLinearLRows);
        pbLoading = root.findViewById(R.id.pBarHistory);
        historyViewModel.getHistory().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    new HistoryFragment.getHistoryInBackground().execute(user.getUsername(),user.getPassword());
                }
            }
        });

        historyViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                title.setText(s);
                title.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_RECOMMENDATION_RESULT_ACTIVITY_FOR_VIEW) {
            if (resultCode == Activity.RESULT_OK) {
                //Toast.makeText(getActivity(), "ACTUALIZA DATOS", Toast.LENGTH_SHORT).show();
                System.out.println("Actualiza datos");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
                //Write your code if there's no result
            }
        }
    }

    private void loadForms(int idReq, int index){
        Intent activityIntent = new Intent(getActivity(), RecomForm.class);
        activityIntent.putExtra("reqRes",history.getArrRequest().get(index)); //solo paso el requestReult necesario
        activityIntent.putExtra("type", "view");
        startActivityForResult(activityIntent, LAUNCH_RECOMMENDATION_RESULT_ACTIVITY_FOR_VIEW);
    }

    private void loadRecom(int idReq,int index){
        Intent activityIntent = new Intent(getActivity(), RecomResult.class);
        activityIntent.putExtra("reqRes",history.getArrRequest().get(index)); //solo paso el requestReult necesario
        activityIntent.putExtra("type", "view");
        startActivityForResult(activityIntent, LAUNCH_RECOMMENDATION_RESULT_ACTIVITY_FOR_VIEW);
    }

    public void setHistory(History history){
        this.history=history;
        if(history.getRequests()==0){
            title.setText(R.string.no_history);
        }else{
        historyViewModel.ChangeTitle(history.getRequests());
        ItemResult itres = new ItemResult(getContext(),history);
        this.rc=itres.initContainers(lparent);
        for(int x = 0; x<rc.getButtonForm().size(); x++){
            rc.getButtonForm().get(x).setOnClickListener(this);
            rc.getButtonRecom().get(x).setOnClickListener(this);
        }
        }
    }

    @Override
    public void onClick(View view) {
        for(int x = 0; x<rc.getButtonForm().size(); x++){
            if(view.getId()==rc.getButtonForm().get(x).getId()){
                loadForms(history.getArrRequest().get(x).getId(),x);
                break;
            }else if(view.getId()==rc.getButtonRecom().get(x).getId()){
                loadRecom(history.getArrRequest().get(x).getId(),x);
                break;
            }
        }
    }

    class getHistoryInBackground extends AsyncTask<String, Void, History> {
        @Override
        protected void onPreExecute() {
            pbLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected History doInBackground(String... strings) {
            History reqHistory = new History();
            ConnectionManager cm = new ConnectionManager(strings[0],strings[1]);
            try{
                reqHistory=cm.getHistory();
            }catch (IOException e){
                e.printStackTrace();
            }
            return reqHistory;
        }

        @Override
        protected void onPostExecute(History reqHistory) {
            setHistory(reqHistory);
            pbLoading.setVisibility(View.GONE);
        }
    }

}