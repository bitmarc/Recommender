package com.example.recommender.ui.home;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.recommender.MainActivity;
import com.example.recommender.R;
import com.example.recommender.entities.User;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private MainActivity activity;
    private User user;
    public ProgressBar loadingProgressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        this.user= activity.getUser();
        homeViewModel = ViewModelProviders.of(this, new HomeViewModelFactory(user)).get(HomeViewModel.class);
        final Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Caveat-Bold.ttf");
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView appTitle = root.findViewById(R.id.text_home);
        final TextView msgWellcome = root.findViewById(R.id.tvWellcome);
        this.loadingProgressBar = root.findViewById(R.id.pB);

        homeViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                appTitle.setTypeface(type);
                appTitle.setText(s);
            }
        });

        homeViewModel.getCurrentName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                msgWellcome.setText(s);
            }
        });

        homeViewModel.getPbarValue().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                    loadingProgressBar.setVisibility(View.VISIBLE);
                else
                    loadingProgressBar.setVisibility(View.GONE);
            }
        });



        return root;
    }
}