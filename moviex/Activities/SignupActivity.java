package sg.edu.tp.moviex.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import sg.edu.tp.moviex.R;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "Sign Up";
    EditText mail, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        mail = findViewById(R.id.editsignupEmailField);
        pass = findViewById(R.id.editsignupPasswordField);
    }

    public void createUserWithEmailAndPassword(View view) {
        String email = mail.getText().toString();
        String password = pass.getText().toString();

        if ((email.length() > 0) && (password.length() >= 6)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign up success, update UI with the signed-in user's information
                            Log.e(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(SignupActivity.this, HomeActivity.class));


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();


                        }


                    });

         } else if (password.length() <= 6) {
            // Sign up fail, password length not equal or more than 6 characters
            Toast.makeText(SignupActivity.this, "Password length too short, enter at least 6 characters", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(SignupActivity.this, "Enter Email and password", Toast.LENGTH_SHORT).show();

        }
    }

    public void openPrivacyPolicy(View view) {
        Intent openPrivacyPolicyIntent = new Intent(this, PrivacyPolicyActivity.class);
        startActivity(openPrivacyPolicyIntent);
    }

    public void openLoginActivity (View view){

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}


//TODO Fix android:onclick for createUserWithEmailAndPassword.