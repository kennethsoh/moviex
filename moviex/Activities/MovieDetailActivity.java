package sg.edu.tp.moviex.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sg.edu.tp.moviex.BuildConfig;
import sg.edu.tp.moviex.Model.Movie;
import sg.edu.tp.moviex.Model.Trailer;
import sg.edu.tp.moviex.Model.TrailerResponse;
import sg.edu.tp.moviex.R;
import sg.edu.tp.moviex.movieAPI.YoutubeConfiguration;
import sg.edu.tp.moviex.movieAPI.Client;
import sg.edu.tp.moviex.movieAPI.Service;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;


public class MovieDetailActivity extends YouTubeBaseActivity {


    String videoid;
    public static String globalVidID;

    Movie movie;

    Trailer trailer1;



    // private static final String BASE_URL = "https://www.youtube.com/watch?v=";

    TextView nameOfMovie, plotSynopsis, userRating, releaseDate;
    YouTubePlayerView movieTrailer;


    YouTubePlayer.OnInitializedListener mOnInitializedListener;

    private static final String TAG = "MovieDetailActivity";


    int movie_id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        nameOfMovie = findViewById(R.id.movietitle);
        plotSynopsis = findViewById(R.id.plotsynopsis);
        userRating = findViewById(R.id.userrating);
        releaseDate = findViewById(R.id.releasedate);


        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("original_title")) {

            String thumbnail = getIntent().getExtras().getString("poster_path");
            String movieName = getIntent().getExtras().getString("original_title");
            String synopsis = getIntent().getExtras().getString("overview");
            String rating = getIntent().getExtras().getString("vote_average");
            String dateOfRelease = getIntent().getExtras().getString("release_date");
            Integer movieid = getIntent().getExtras().getInt("id");


            nameOfMovie.setText(movieName);
            plotSynopsis.setText(synopsis);
            userRating.setText(rating);
            releaseDate.setText(dateOfRelease);


            movie_id = movieid;
            Log.d(TAG, "movie id : " + movie_id);


        } else {
            Toast.makeText(this, "No API Data", Toast.LENGTH_SHORT).show();
        }

        movieTrailer = findViewById(R.id.YtView);

        getTrailerResource();

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG, "Initialize success");

                Log.d(TAG, "the only videoid is " + globalVidID);

                //TODO set onCompleteListener to release resources , also check if can be more responsive by code cleanup of whatever...

                youTubePlayer.loadVideo(globalVidID);

                //youTubePlayer.play();
                //youTubePlayer.loadVideo("ZJDMWVZta3M");

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "Initialize failure");
            }
        };
        movieTrailer.initialize(YoutubeConfiguration.getYtapiKey(), mOnInitializedListener);


    }

    public void openReviewPage(View view) {
        Intent intentOpenReviews = new Intent(this, PostReviewActivity.class);
        startActivity(intentOpenReviews);
    }

    public void readReviews (View view){
        Intent intentReadReviews = new Intent(this, ReadReviewActivity.class);
        startActivity(intentReadReviews);
    }

    public void getTrailerResource() {

        Client Client = new Client();
        Service apiService = sg.edu.tp.moviex.movieAPI.Client.getClient().create(Service.class);

        // API service: getTrailers ; change to other forms to get other types
        Call<TrailerResponse> call = apiService.getTrailers(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN, "en-US");
        {
            call.enqueue(new Callback<TrailerResponse>() {
                @Override
                public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                        TrailerResponse trailerResponse = response.body();
                            List<Trailer> trailers = response.body().getTrailers();
                            for (final Trailer trailer : trailers) {
                                trailer1 = trailers.get(0);
                                videoid = trailer1.getKey();
                                Log.d(TAG, "videoid " + videoid);
                                Log.d(TAG, "call trailerResponse: success");
                                globalVidID = videoid;
                                Log.d(TAG, "globalVidID" + globalVidID + " " + videoid);

                            }
                }
                @Override
                public void onFailure(Call<TrailerResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MovieDetailActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                }
            });

        }
        //TODO Value of globalVidID was lost from here

        Log.d(TAG, "the globalVidID is " + globalVidID);

    }
}













