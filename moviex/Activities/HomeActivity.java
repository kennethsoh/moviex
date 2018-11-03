package sg.edu.tp.moviex.Activities;


import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import sg.edu.tp.moviex.Fragments.ExploreFragment;
import sg.edu.tp.moviex.Fragments.HomeFragment;
import sg.edu.tp.moviex.Fragments.ReviewMasterFragment;
import sg.edu.tp.moviex.Fragments.TopRatedFragment;

import sg.edu.tp.moviex.Fragments.UpcomingMoviesFragment;
import sg.edu.tp.moviex.R;


public class HomeActivity extends AppCompatActivity {

    String movieTitle;
    String reviewTxt;

    public ImageButton search_button;
    FirebaseUser user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If there is no user, go to LoginActivity (It should never happen
        // because, there's no way to enter app without some form of login (emailPass/guest))
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        setContentView(R.layout.activity_home);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottombar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();



    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.menu_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.menu_explore:
                        selectedFragment = new ExploreFragment();
                        break;
                    case R.id.menu_reviews:
                        selectedFragment = new ReviewMasterFragment();
                        break;


                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            };


    // Open profile Activity from profile button at top LH corner of home fragment on Topbar
    public void openProfileActivity(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    // Open Search Activity from search button at top RH corner of home fragment on topbar
    public void openSearchActivity(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }


    // Open Top Rated Fragment with TMDb Api from explore Fragment
    public void openTopRatedFrag(View view){

        Fragment chosenFragment = new TopRatedFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, chosenFragment).commit();

    }

    // Open Upcoming Fragment with TMDb Api from explore Fragment
    public void openUpcomingFrag(View view){

        Fragment chosenFragment = new UpcomingMoviesFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, chosenFragment).commit();

    }


    // Back Arrow button to redirect back to Explore Fragment
    public void openExploreFragment(View view){
        Fragment chosenFragment = new ExploreFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, chosenFragment).commit();
    }

    // Open Soundtrack List Activity on Explore Fragment
    public void openSoundtrackList(View view){
        Intent intent = new Intent(this, SoundtrackListActivity.class);
        startActivity(intent);
    }

    // Open Home Activity from back button on topbar
    public void openHomeActivity(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


    //TODO Reviews
    public void openReviewPage(View view) {
        Intent intentOpenReviews = new Intent(this, PostReviewActivity.class);
        startActivity(intentOpenReviews);
    }

    //TODO Reviews
    public void readReviews (View view){
        Intent intentReadReviews = new Intent(this, ReadReviewActivity.class);
      //  intentReadReviews.putExtra("movieTitle", movieTitle);
       // intentReadReviews.putExtra("reviewTxt", reviewTxt);
        startActivity(intentReadReviews);
    }

    public void buyTicketsFromGV (View view) {

        Toast.makeText(this, "Exiting Moviex", Toast.LENGTH_SHORT).show();

        String url = "https://www.gv.com.sg/GVBuyTickets#/";
        Intent intentBuyTickets = new Intent(Intent.ACTION_VIEW);
        intentBuyTickets.setData(Uri.parse(url));
        startActivity(intentBuyTickets);
    }

    public void buyTicketsFromShaw (View view) {

        Toast.makeText(this, "Exiting Moviex", Toast.LENGTH_SHORT).show();

        String url = "https://www.shaw.sg/sw_buytickets.aspx";
        Intent intentBuyTickets = new Intent(Intent.ACTION_VIEW);
        intentBuyTickets.setData(Uri.parse(url));
        startActivity(intentBuyTickets);
    }

}


