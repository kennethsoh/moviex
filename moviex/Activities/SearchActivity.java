package sg.edu.tp.moviex.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import sg.edu.tp.moviex.Adapter.SearchAdapter;
import sg.edu.tp.moviex.BuildConfig;
import sg.edu.tp.moviex.Model.Movie;
import sg.edu.tp.moviex.Model.MovieResponse;
import sg.edu.tp.moviex.Model.Search;
import sg.edu.tp.moviex.Model.SearchResponse;
import sg.edu.tp.moviex.R;
import sg.edu.tp.moviex.movieAPI.Client;
import sg.edu.tp.moviex.movieAPI.Service;



public class SearchActivity extends AppCompatActivity {

    //TODO Reconsider moving Search back to Fragment

    private RecyclerView searchRecyclerView;
    private List<Search> searchList;
    private SearchAdapter SearchAdapter;
    private EditText searchView;
    private ImageButton search_button;
    private static final String TAG = "Search Activity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.searchView);
        search_button = findViewById(R.id.search_button);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSearchView();
            }
        });



        searchList = new ArrayList<>();
    }



    private void searchMovies(){

        String query = searchView.getText().toString();
        Log.d(TAG, query);

        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(SearchActivity.this, "Please obtain API key from themoviedb.org", Toast.LENGTH_SHORT).show();
                return;
            }
            Client Client = new Client();

            Service apiService = sg.edu.tp.moviex.movieAPI.Client.getClient().create(Service.class);

            // API service: getSearch ; change to other forms to get other types
            Call<SearchResponse> call = apiService.getSearch(BuildConfig.THE_MOVIE_DB_API_TOKEN, query);
            Log.d(TAG, "query successful");
            call.enqueue(new Callback<SearchResponse>() {
                @Override
                public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response){
                    Log.d(TAG, "query successful again");
                    List<Search> searches = response.body().getResults();
                    searchRecyclerView.setAdapter(new SearchAdapter(SearchActivity.this, searches));
                    searchRecyclerView.smoothScrollToPosition(0);


                }

                @Override
                public void onFailure(Call<SearchResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(SearchActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initSearchView() {



        searchRecyclerView = findViewById(R.id.searchRecyclerView);
        searchList = new ArrayList<>();
        SearchAdapter = new SearchAdapter(this, searchList);
        searchRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        searchRecyclerView.setItemAnimator(new DefaultItemAnimator());
        searchRecyclerView.setAdapter(SearchAdapter);


        searchMovies();

        SearchAdapter.notifyDataSetChanged();

    }

    // Open Home Activity from back button at top LH corner of Search Activity on Topbar
    public void openHomeActivity(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }



}
