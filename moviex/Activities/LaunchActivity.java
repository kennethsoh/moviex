package sg.edu.tp.moviex.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import sg.edu.tp.moviex.R;


public class LaunchActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "Anonymous login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        mAuth = FirebaseAuth.getInstance();

        android.support.v7.widget.Toolbar topToolbar = findViewById(R.id.topbar_launch_activity);
        setSupportActionBar(topToolbar);
        getSupportActionBar().setTitle("");


        }

        @Override
        public void onStart() {
        super.onStart();
            FirebaseUser currentUser = mAuth.getCurrentUser();
        }

        public void openLoginActivity (View view){

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        public void signInAnonymously(View view){
        mAuth.signInAnonymously().addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "signInAnonymously: Success" );
                FirebaseUser user = mAuth.getCurrentUser();
                startActivity(new Intent(LaunchActivity.this, HomeActivity.class));
            } else {
                Log.e(TAG, "signInAnonymously: Failure");
                Toast.makeText(LaunchActivity.this, "Authentication Failed, Try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

