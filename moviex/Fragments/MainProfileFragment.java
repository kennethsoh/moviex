package sg.edu.tp.moviex.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import sg.edu.tp.moviex.Activities.LaunchActivity;
import sg.edu.tp.moviex.R;

public class MainProfileFragment extends Fragment {

    private FirebaseAuth mAuth;

    private TextView ProfileName;
    View view;

    private static final String guest = "Guest";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_profile, container, false);

        ProfileName = view.findViewById(R.id.ProfileName);
        getCurrentUser();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);

        mAuth = FirebaseAuth.getInstance();





    }

    // show user's email if user not null
    private void getCurrentUser() {
        FirebaseUser user = mAuth.getCurrentUser();
        String emailAddress = mAuth.getCurrentUser().getEmail();

        if ((user != null)&&(emailAddress != null)) {
            ProfileName.setText(guest);
            ProfileName.setText(user.getEmail());
        } else {
            ProfileName.setText(guest);
        }


    }



}

