package sg.edu.tp.moviex.movieAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import sg.edu.tp.moviex.Model.MovieResponse;
import sg.edu.tp.moviex.Model.SearchResponse;
import sg.edu.tp.moviex.Model.TrailerResponse;

public interface Service {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey, @Query("language") String lang);

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET("search/movie")
    Call<SearchResponse> getSearch(@Query("api_key") String apiKey, @Query("query") String query);

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getTrailers(@Path("movie_id") int id, @Query("api_key") String apiKey, @Query("language") String lang);





}
