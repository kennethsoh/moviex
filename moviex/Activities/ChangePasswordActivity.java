package sg.edu.tp.moviex.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import sg.edu.tp.moviex.R;

public class ChangePasswordActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText changePasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        changePasswordField = findViewById(R.id.changePasswordField);
        mAuth = FirebaseAuth.getInstance();
    }

    public void changePassword(View view) {
        FirebaseUser user = mAuth.getCurrentUser();

        String newPassword = changePasswordField.getText().toString();

        String emailAddress = mAuth.getCurrentUser().getEmail();

        // check for email address, guest accounts cannot change password otherwise app will crash.
        if (emailAddress != null) {
            //AuthCredential for re-authentication, see firebase docs regarding change password
            if(newPassword.length() >= 6) {

                AuthCredential credential = EmailAuthProvider.getCredential("user@example.com", "password");
                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("change password:", "user re-authenticated");
                        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ChangePasswordActivity.this, "Password Change: success", Toast.LENGTH_SHORT).show();
                                    Log.d("change password: ", "success");
                                    Log.d("new password: ", newPassword);
                                } else {
                                    Toast.makeText(ChangePasswordActivity.this, "Password Change: failure", Toast.LENGTH_SHORT).show();
                                    Log.d("change password: ", "failure");
                                }
                            }
                        });

                    }
                });
            } else {
                // Sign up fail, password length not equal or more than 6 characters
                Toast.makeText(this, "New password length too short, enter at least 6 characters", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "A guest account cannot change password", Toast.LENGTH_LONG).show();
        }
    }

    // for back arrow button on topbar
    public void openProfileActivity(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);

    }
}
