package sg.edu.tp.moviex.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import sg.edu.tp.moviex.Adapter.MoviesAdapter;
import sg.edu.tp.moviex.BuildConfig;
import sg.edu.tp.moviex.Model.Movie;
import sg.edu.tp.moviex.Model.MovieResponse;
import sg.edu.tp.moviex.R;
import sg.edu.tp.moviex.movieAPI.Client;
import sg.edu.tp.moviex.movieAPI.Service;

public class TopRatedFragment extends Fragment {

    View view;
    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private List<Movie> movieList;
    private SwipeRefreshLayout swipeContainer;
    public static final String LOG_TAG = MoviesAdapter.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_top_rated, container, false);
        initViews();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);

        movieList = new ArrayList<>();
    }

    private void loadJSON(){
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(getActivity(), "No API key found", Toast.LENGTH_SHORT).show();
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
                    recyclerView.setAdapter(new MoviesAdapter(getActivity(), movies));
                    recyclerView.smoothScrollToPosition(0);
                    if (swipeContainer.isRefreshing()){
                        swipeContainer.setRefreshing(false);

                    }

                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(getActivity(), "Error Fetching Data from TMDb", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    private void initViews(){

        recyclerView = view.findViewById(R.id.recycler_view);
        movieList = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(getActivity(), movieList);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(moviesAdapter);
        moviesAdapter.notifyDataSetChanged();

        loadJSON();

        //TODO Look at SwipeRefreshLayout and Lambda Expressions

        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViews();
            }
        });

    }



}