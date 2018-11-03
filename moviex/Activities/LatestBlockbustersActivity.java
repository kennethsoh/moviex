package sg.edu.tp.moviex.Activities;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import sg.edu.tp.moviex.Adapter.MoviesAdapter;
import sg.edu.tp.moviex.BuildConfig;
import sg.edu.tp.moviex.Fragments.ExploreFragment;
import sg.edu.tp.moviex.Model.Movie;
import sg.edu.tp.moviex.Model.MovieResponse;
import sg.edu.tp.moviex.R;
import sg.edu.tp.moviex.movieAPI.Client;
import sg.edu.tp.moviex.movieAPI.Service;

public class LatestBlockbustersActivity extends AppCompatActivity {

    View view1;
    private RecyclerView recyclerView1;
    private MoviesAdapter moviesAdapter;
    private List<Movie> movieList;
    private SwipeRefreshLayout swipeContainer1;
    public static final String LOG_TAG = MoviesAdapter.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_blockbusters);

        initViews();

        swipeContainer1 = findViewById(R.id.swipeContainer1);
        swipeContainer1.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer1.setOnRefreshListener(this::initViews);


        movieList = new ArrayList<>();
    }

    // Back Arrow button to redirect back to Explore Fragment
    public void openExploreFragment(View view){
        Intent intentOpenExploreFrag = new Intent(this, ExploreFragment.class);
        startActivity(intentOpenExploreFrag);
    }

    private void loadJSON(){
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(LatestBlockbustersActivity.this, "Please obtain API key from themoviedb.org", Toast.LENGTH_SHORT).show();
                return;
            }
            Client Client = new Client();
            Service apiService = sg.edu.tp.moviex.movieAPI.Client.getClient().create(Service.class);

            // API service: getLatestMovies ; change to other forms to get other types
            Call<MovieResponse> call = apiService.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN, "en-US");
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    List<Movie> movies = response.body().getResults();

                    // MoviesAdapter is set to recyclerView1, context is this activity
                    recyclerView1.setAdapter(new MoviesAdapter(LatestBlockbustersActivity.this, movies));
                    recyclerView1.smoothScrollToPosition(0);
                    if (swipeContainer1.isRefreshing()){
                        swipeContainer1.setRefreshing(false);
                    }

                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(LatestBlockbustersActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews() {

        recyclerView1 = findViewById(R.id.recycler_view1);
        movieList = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(this, movieList);

        recyclerView1.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(moviesAdapter);
        moviesAdapter.notifyDataSetChanged();

        loadJSON();

        //TODO Look at SwipeRefreshLayout and Lambda Expressions


    }
}
