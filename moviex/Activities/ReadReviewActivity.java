package sg.edu.tp.moviex.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sg.edu.tp.moviex.Model.PostReview;
import sg.edu.tp.moviex.R;

public class ReadReviewActivity extends AppCompatActivity {

    private static final String TAG = "ReadReviewActivity";

    private String userId;
    private String review;
    private String key;

    private TextView reviewMovieTitleText;
    private TextView reviewText;

    private FirebaseAuth mAuth;
    private DatabaseReference mReviews;
    private DatabaseReference mReviewMovieTitleReference;
    private DatabaseReference mReviewTextReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_review);

        /* Get post key from intent
        mPostKey = getIntent().getStringExtra("post_key");
        if (mPostKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        } */

        // Initialize Database
        mReviews = FirebaseDatabase.getInstance().getReference();
        mReviewMovieTitleReference = FirebaseDatabase.getInstance().getReference();
        mReviewTextReference = FirebaseDatabase.getInstance().getReference();

        //Initialize views
        reviewMovieTitleText = findViewById(R.id.reviewMovieTitleTextView);
        reviewText= findViewById(R.id.reviewTextView);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();




    }

    public void openHomeActivity(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();



        // Add value event listener to the post
        ValueEventListener TitleListener= new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String MovieTxt = dataSnapshot.getValue().toString();
                    reviewMovieTitleText.setText(MovieTxt);



                } else {
                    Toast.makeText(ReadReviewActivity.this, "no data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message

                Log.w("READ REVIEW ACTIVITY", "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(ReadReviewActivity.this, "Failed to load reviews.", Toast.LENGTH_SHORT).show();

            }
        };
        mReviews.child("Reviews").child("MovieTitle").addValueEventListener(TitleListener);


        // Add value event listener to the post
        ValueEventListener ReviewListener= new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String ReviewTxt = dataSnapshot.getValue().toString();
                    reviewText.setText(ReviewTxt);


                } else {
                    Toast.makeText(ReadReviewActivity.this, "no data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message

                Log.w("READ REVIEW ACTIVITY", "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(ReadReviewActivity.this, "Failed to load reviews.", Toast.LENGTH_SHORT).show();

            }
        };
        mReviews.child("Reviews").child("ReviewText").addValueEventListener(ReviewListener);


    }


}



