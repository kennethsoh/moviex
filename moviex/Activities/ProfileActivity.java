package sg.edu.tp.moviex.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import sg.edu.tp.moviex.Fragments.MainProfileFragment;
import sg.edu.tp.moviex.R;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView ProfileName;
    private String emailAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ProfileName = findViewById(R.id.ProfileName);


        mAuth = FirebaseAuth.getInstance();



        getSupportFragmentManager().beginTransaction().replace(R.id.profile_fragment_container, new MainProfileFragment()).commit();


    }


    // User account sign out, returns to launch activity.
    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(ProfileActivity.this, LaunchActivity.class));
    }

    // exits profile activity(MainProfileFragment), returns to Home Activity(HomeFragment)
    public void openHomeActivity(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }


    public void openChangePasswordActivity(View view) {

            Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
            startActivity(intent);

    }







        //public void openLatestBlockbustersFrag(View view) {

        //  Fragment chosenFragment = new PrivacyPolicyFragment();

        // getSupportFragmentManager().beginTransaction().replace(R.id.profile_fragment_container, chosenFragment).commit();


    }











