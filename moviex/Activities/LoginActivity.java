package sg.edu.tp.moviex.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import sg.edu.tp.moviex.R;

public class LoginActivity extends AppCompatActivity {


    private Button continueLoginButton;


    private FirebaseAuth mAuth;
    private static final String TAG = "Login";
    EditText mail, pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();


        mail = findViewById(R.id.editLoginEmailField);
        pass = findViewById(R.id.editLoginPasswordField);


    }

    public void openSignupActivity(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed-in (non null)
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    public void signInWithEmailAndPassword(View view) {
        String email = mail.getText().toString();
        String password = pass.getText().toString();

        if ((email.length()>0) && (password.length()>0)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    });

        } else {
            Toast.makeText(LoginActivity.this, "Enter email and password", Toast.LENGTH_SHORT).show();
        }


    }


}








