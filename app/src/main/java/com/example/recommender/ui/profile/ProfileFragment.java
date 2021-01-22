package com.example.recommender.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.recommender.MainActivity;
import com.example.recommender.R;
import com.example.recommender.entities.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private MainActivity activity;
    private User user;
    public ProgressBar loadingProgressBar;
    private int LAUNCH_CHANGE_PASSWORD_ACTIVITY = 2;
    private EditText editTextP;
    private Typeface franklinfont;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        user= activity.getUser();
        profileViewModel = ViewModelProviders.of(this, new ProfileViewModelFactory(user)).get(ProfileViewModel.class);//instanciar la clase ProfileViewModel

        View root = inflater.inflate(R.layout.fragment_profile, container, false);  // referencia a la actividad raiz
        final EditText editTextPn = root.findViewById(R.id.editTextPersonName2);
        final FloatingActionButton fabEdit = root.findViewById(R.id.floatingActionButton);
        final FloatingActionButton fabDone = root.findViewById(R.id.floatingActionButtonDone);
        final EditText editTextUn = root.findViewById(R.id.editTextTextUserName);
        final EditText editTextEa = root.findViewById(R.id.editTextTextEmailAddress);
        editTextP = root.findViewById(R.id.editTextTextPassword);
        final Button buttonlogout = root.findViewById(R.id.logout);
        final ImageView ivEditPass = root.findViewById(R.id.ivWaring);
        this.loadingProgressBar = root.findViewById(R.id.pBarHistory);
        this.franklinfont= ResourcesCompat.getFont(getContext(),R.font.franklin_gothic_demi_cond);
        fabDone.hide();

        profileViewModel.getCurrentUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User s) {
                editTextPn.setText(s.getPersonname());
                editTextUn.setText(s.getUsername());
                editTextEa.setText(s.getEmail());
                editTextP.setText(s.getPassword());// modificado 4.0.7.1
            }
        });


        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setState(fabEdit,fabDone,editTextPn,editTextUn,editTextEa,buttonlogout,ivEditPass);// modificado 4.0.7.1
                profileViewModel.refresh();
            }
        });

        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User newUser = new User(editTextUn.getText().toString(), editTextP.getText().toString(),
                        editTextPn.getText().toString(), editTextEa.getText().toString());
                newUser.setId(user.getId());
                profileViewModel.updateUser(newUser);
                activity.setUser(newUser);
                user=newUser;
                //profileViewModel.refresh();
                setState(fabEdit,fabDone,editTextPn,editTextUn,editTextEa,buttonlogout, ivEditPass);
            }
        });

        ivEditPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityIntent = new Intent(getActivity(), PasswordChange.class);
                startActivityForResult(activityIntent, LAUNCH_CHANGE_PASSWORD_ACTIVITY);
            }
        });

        profileViewModel.getPbarValue().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_CHANGE_PASSWORD_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(getActivity(), "El dato es: "+data.getStringExtra("newpass"), Toast.LENGTH_SHORT).show();
                editTextP.setText(data.getStringExtra("newpass"));
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
                //Write your code if there's no result
            }
        }
    }

    public void setState(FloatingActionButton fabEdit, FloatingActionButton fabDone, EditText editTextPn, EditText editTextUn,
                         EditText editTextEa, Button buttonlogout, ImageView ivEditP){
        if(editTextPn.isEnabled()){
            fabEdit.setImageResource(R.drawable.ic_baseline_edit_24);
            fabEdit.setSupportBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.colorPrimary));
            fabDone.hide();
            buttonlogout.setEnabled(true);

            editTextPn.setEnabled(false);
            editTextPn.setClickable(false);
            editTextPn.setCursorVisible(false);
            editTextPn.setBackground(null);
            editTextPn.setTextColor(getResources().getColor(R.color.colorPrimary));

            editTextUn.setEnabled(false);
            editTextUn.setClickable(false);
            editTextUn.setCursorVisible(false);
            editTextUn.setBackground(null);
            editTextUn.setTextColor(getResources().getColor(R.color.colorTextPrimary));

            editTextEa.setEnabled(false);
            editTextEa.setClickable(false);
            editTextEa.setCursorVisible(false);
            editTextEa.setBackground(null);
            editTextEa.setTextColor(getResources().getColor(R.color.colorTextPrimary));

            ivEditP.setClickable(false);
            ivEditP.setVisibility(View.GONE);


        }else{
            fabEdit.setImageResource(R.drawable.ic_baseline_cancel_24);
            fabEdit.setSupportBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.colorPrimary));
            fabDone.show();
            buttonlogout.setEnabled(false);

            editTextPn.setEnabled(true);
            editTextPn.setClickable(true);
            editTextPn.setCursorVisible(true);
            editTextPn.setTextColor(getResources().getColor(R.color.colorBackgroundNav));
            editTextPn.setBackgroundResource(android.R.drawable.edit_text);

            editTextUn.setEnabled(true);
            editTextUn.setClickable(true);
            editTextUn.setCursorVisible(true);
            editTextUn.setTextColor(getResources().getColor(R.color.colorBackgroundNav));
            editTextUn.setBackgroundResource(android.R.drawable.edit_text);

            editTextEa.setEnabled(true);
            editTextEa.setClickable(true);
            editTextEa.setCursorVisible(true);
            editTextEa.setTextColor(getResources().getColor(R.color.colorBackgroundNav));
            editTextEa.setBackgroundResource(android.R.drawable.edit_text);

            ivEditP.setClickable(true);
            ivEditP.setColorFilter(getResources().getColor(R.color.colorTextPrimary));
            ivEditP.setVisibility(View.VISIBLE);

        }

    }

}