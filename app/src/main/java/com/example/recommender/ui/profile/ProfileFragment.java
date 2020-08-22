package com.example.recommender.ui.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.recommender.MainActivity;
import com.example.recommender.PreStartActivity;
import com.example.recommender.R;
import com.example.recommender.SessionManagement;
import com.example.recommender.User;
import com.example.recommender.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    public MainActivity main;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        main = (MainActivity) getActivity(); //para poder recuperar variables desde el main
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);//instanciar la clase ProfileViewModel

        View root = inflater.inflate(R.layout.fragment_profile, container, false);  // referencia a la actividad raiz
        final EditText editTextPn = root.findViewById(R.id.editTextPersonName2);                // definir mi variable edittext
        final FloatingActionButton fabEdit = root.findViewById(R.id.floatingActionButton);
        final FloatingActionButton fabDone = root.findViewById(R.id.floatingActionButtonDone);
        final EditText editTextUn = root.findViewById(R.id.editTextTextUserName);
        final EditText editTextEa = root.findViewById(R.id.editTextTextEmailAddress);
        final EditText editTextP = root.findViewById(R.id.editTextTextPassword);
        final Button buttonlogout = root.findViewById(R.id.logout);
        fabDone.hide();

        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<User>() {    // metodo que observa y actualiza la UI
            @Override
            public void onChanged(@Nullable User s) {
                editTextPn.setText(s.getPersonname());
                editTextUn.setText(s.getUsername());
                editTextEa.setText(s.getEmail());
                editTextP.setText(s.getPassword());
            }
        });

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setState(fabEdit,fabDone,editTextPn,editTextUn,editTextEa,editTextP,buttonlogout);
            }
        });
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileViewModel.refresh();
                setState(fabEdit,fabDone,editTextPn,editTextUn,editTextEa,editTextP,buttonlogout);
            }
        });

        return root;
    }

    public void setState(FloatingActionButton fabEdit, FloatingActionButton fabDone, EditText editTextPn, EditText editTextUn, EditText editTextEa,
                         EditText editTextP, Button buttonlogout){
        if(editTextPn.isEnabled()){
            fabEdit.setImageResource(R.drawable.ic_baseline_edit_24);
            fabEdit.setSupportBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.colorAccent));
            fabDone.hide();
            buttonlogout.setEnabled(true);

            editTextPn.setEnabled(false);
            editTextPn.setClickable(false);
            editTextPn.setCursorVisible(false);
            editTextPn.setBackground(null);

            editTextUn.setEnabled(false);
            editTextUn.setClickable(false);
            editTextUn.setCursorVisible(false);
            editTextUn.setBackground(null);

            editTextEa.setEnabled(false);
            editTextEa.setClickable(false);
            editTextEa.setCursorVisible(false);
            editTextEa.setBackground(null);

            editTextP.setEnabled(false);
            editTextP.setClickable(false);
            editTextP.setCursorVisible(false);
            editTextP.setBackground(null);


        }else{
            fabEdit.setImageResource(R.drawable.ic_baseline_cancel_24);
            fabEdit.setSupportBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.colorButtonX));
            fabDone.show();
            buttonlogout.setEnabled(false);

            editTextPn.setEnabled(true);
            editTextPn.setClickable(true);
            editTextPn.setCursorVisible(true);
            editTextPn.setBackgroundResource(android.R.drawable.edit_text);

            editTextUn.setEnabled(true);
            editTextUn.setClickable(true);
            editTextUn.setCursorVisible(true);
            editTextUn.setBackgroundResource(android.R.drawable.edit_text);

            editTextEa.setEnabled(true);
            editTextEa.setClickable(true);
            editTextEa.setCursorVisible(true);
            editTextEa.setBackgroundResource(android.R.drawable.edit_text);

            editTextP.setEnabled(true);
            editTextP.setClickable(true);
            editTextP.setCursorVisible(true);
            editTextP.setBackgroundResource(android.R.drawable.edit_text);

        }

    }

}