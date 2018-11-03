package sg.edu.tp.moviex.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import sg.edu.tp.moviex.Model.PostReview;
import sg.edu.tp.moviex.R;

public class PostReviewActivity extends AppCompatActivity  {

    private static final String TAG ="PostReviewActivity";

    private FirebaseAuth mAuth;

    //view objects
    private EditText reviewMovieTitleInput;
    private EditText reviewTextInput;
    private Button postReviewButton;

    public String uid;
    public String MovieTitle;
    public String ReviewText;


    private DatabaseReference mReviews;
    private DatabaseReference mReviewMovieTitleReference;
    private DatabaseReference mReviewTextReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_review);

        //Initialize Database Reference
        mReviews = FirebaseDatabase.getInstance().getReference();
        //mReviewMovieTitleReference = FirebaseDatabase.getInstance().getReference().child("Movie Title");
        //mReviewTextReference = FirebaseDatabase.getInstance().getReference().child("Review Text");

        //Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();


        // Initialise objects; getting views
        postReviewButton = findViewById(R.id.postReviewButton);
        reviewMovieTitleInput = findViewById(R.id.reviewMovieTitle);
        reviewTextInput = findViewById(R.id.reviewText);

        this.uid = uid;
        this.MovieTitle = MovieTitle;
        this.ReviewText = ReviewText;

        postReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }


    });
    }

    // Open Home Activity for back arrow button on topbar
    public void openHomeActivity(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


    private void submit() {
        final String MovieTitle = reviewMovieTitleInput.getText().toString();
        final String ReviewText = reviewTextInput.getText().toString();

        // Movie Title is required
        if (TextUtils.isEmpty(MovieTitle)) {
            reviewMovieTitleInput.setError("Movie Title Required");
            return;
        }
        // Review Text is required
        if (TextUtils.isEmpty(ReviewText)) {
            reviewTextInput.setError("Review Text Required");
            return;
        }
        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        final String userId = mAuth.getUid();
        mReviews.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (userId.equals(null)) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");

                            Toast.makeText(PostReviewActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPost(userId, MovieTitle, ReviewText);
                            //mReviews.child(userId).child(MovieTitle);
                           // mReviews.child(userId).child(ReviewText);
                            //confirmSubmission();

                        }
                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        setEditingEnabled(true);
                    }
                });

    }

    private void setEditingEnabled(boolean enabled) {
        reviewMovieTitleInput.setEnabled(enabled);
        reviewTextInput.setEnabled(enabled);
        if (enabled) {
            postReviewButton.setVisibility(View.VISIBLE);
        } else {
            postReviewButton.setVisibility(View.GONE);
        }
    }

    private void writeNewPost(String userId, String MovieTitle, String ReviewText) {

        String key = mReviews.child("Reviews").push().getKey();
        PostReview post = new PostReview(userId, MovieTitle, ReviewText);
        Map<String, Object> postValues = post.toMap();

        //update HashMap
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Reviews/" , postValues);
        childUpdates.put("/user-review/" + userId + "/" + key, postValues);
        childUpdates.put("/MovieTitle/" + MovieTitle + "/" + key, postValues);
        childUpdates.put("/ReviewText/" + ReviewText + "/"+ key, postValues);

        mReviews.child(userId).child(MovieTitle);
        mReviews.child(userId).child(ReviewText);

        mReviews.updateChildren(childUpdates);

        //Toast to acknowledge review submission
        Toast.makeText(this, "Review submitted successfully", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"Review submitted successfully");

        // Go back to home activity after submitting review
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }

    public void confirmSubmission(){
        //Toast to acknowledge review submission
        Toast.makeText(this, "Review submitted successfully", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"Review submitted successfully");

        // Go back to home activity after submitting review
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }






}
