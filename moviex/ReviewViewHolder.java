package sg.edu.tp.moviex;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sg.edu.tp.moviex.Model.PostReview;

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    public TextView reviewMovieTitleTextView;
    public TextView reviewTextView;


    public ReviewViewHolder(View itemView) {
        super(itemView);

        reviewMovieTitleTextView = itemView.findViewById(R.id.reviewMovieTitleTextView);
        reviewTextView = itemView.findViewById(R.id.reviewTextView);


    }


    public void bindToPost(PostReview postReview, View.OnClickListener starClickListener) {

        reviewMovieTitleTextView.setText(postReview.MovieTitle);

        reviewTextView.setText(postReview.ReviewText);

    }

}
