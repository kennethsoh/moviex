package sg.edu.tp.moviex.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TrailerResponse {

    // similar to MovieResponse, Serialized name prevents use of any other name to reference these
    @SerializedName("id")
    private int trailer_id;
    @SerializedName("results")
    private List<Trailer> trailers;

    public int getTrailer_id() {
        return trailer_id;
    }

    public void setTrailer_id(int trailer_id) {
        this.trailer_id = trailer_id;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }
}
