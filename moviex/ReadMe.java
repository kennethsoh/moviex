package sg.edu.tp.moviex;

public class ReadMe {

    // This class file exists solely to provide explanation to the api process

    /*
    1. The TMDb abpi key is specified in the gradle.properties and will be referred to as BuildConfig.THE_MOVIE_DB_API_TOKEN
    2.In each fragment's onCreateView, initview() is called
    3. initview() serves to initialise the recycler view, the movieAdapter and movieList, and to call loadjson().
    4. loadjson() checks if the api token is present, then calls the client(retrofit) and the service interface.

        where call<T> is an invocation of a Retrofit method that sends a request to a webserver and returns a response,
        we state call<movieResponse> to be call<service>

        An adapter is responsible for retrieving data from the data set and for generating View objects based on that data.

    When there is a response from the api server, the views are set and getResults() is called.
    getResults() fills the movieList through movieResponse and the movieAdapter uses the list as data to show as a view.

     */


    /*
    Similar to using TMDb api, we set the YTapi_key in YoutubeConfiguration to prevent conflict within the gradle properties
    (although is it perfectly fine to do so, I don't confuse myself)

     */


}
