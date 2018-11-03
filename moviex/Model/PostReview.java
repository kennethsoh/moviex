package sg.edu.tp.moviex.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class PostReview {
    public String uid;
    public String MovieTitle;
    public String ReviewText;


    // Mental note: Constructors must have same name as class
    public PostReview() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }



    public PostReview(String uid, String MovieTitle, String ReviewText) {
        this.uid = uid;
        this.MovieTitle = MovieTitle;
        this.ReviewText = ReviewText;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("MovieTitle", MovieTitle);
        result.put("ReviewText", ReviewText);
        return result;
    }


}
