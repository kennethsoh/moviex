package sg.edu.tp.moviex.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {
    @SerializedName("results")
    private List<Search> results;


    public List<Search> getResults() {
        return results;
    }

    public void setResults(List<Search> results) {
        this.results = results;
    }
}
