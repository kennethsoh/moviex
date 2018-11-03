package sg.edu.tp.moviex.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import sg.edu.tp.moviex.Activities.MovieDetailActivity;
import sg.edu.tp.moviex.Model.Movie;
import sg.edu.tp.moviex.R;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> movieList;


    public MoviesAdapter(Context mContext, List<Movie> movieList){
        this.mContext = mContext;
        this.movieList = movieList;
    }

    // int i refers to the count/position


    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_card, viewGroup, false);

        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MoviesAdapter.MyViewHolder viewHolder, int i){
        // Calls getOriginalTitle from movie.java and sets as movie title
        viewHolder.title.setText(movieList.get(i).getOriginalTitle());
        // Calls getVoteAverage from movie.java and sets to userrating
        String vote = Double.toString(movieList.get(i).getVoteAverage());
        viewHolder.userrating.setText(vote);
        // Calls getPosterPath from movie.java and sets poster as thumbnail
        Glide.with(mContext).load(movieList.get(i).getPosterPath())
                .placeholder(R.drawable.load).into(viewHolder.thumbnail);
    }


    @Override
    public int getItemCount() {
            return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView title, userrating;
        public ImageView thumbnail;

        public MyViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.title);
            userrating = view.findViewById(R.id.userrating);
            thumbnail = view.findViewById(R.id.thumbnail);

            view.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    Movie clickedDataItem = movieList.get(position);
                    Intent intent = new Intent(mContext, MovieDetailActivity.class);
                    intent.putExtra("original_title", movieList.get(position).getOriginalTitle());
                    intent.putExtra("poster_path", movieList.get(position).getPosterPath());
                    intent.putExtra("overview", movieList.get(position).getOverview());
                    intent.putExtra("vote_average",Double.toString(movieList.get(position).getVoteAverage()));
                    intent.putExtra("release_date", movieList.get(position).getReleaseDate());
                    intent.putExtra("id", movieList.get(position).getId());

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getOriginalTitle() + clickedDataItem.getId(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
