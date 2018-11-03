package sg.edu.tp.moviex.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import sg.edu.tp.moviex.Activities.MovieDetailActivity;
import sg.edu.tp.moviex.Model.Movie;
import sg.edu.tp.moviex.Model.Search;
import sg.edu.tp.moviex.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MySearchViewHolder> {

    private Context mContext;
    private List<Search> searchList;


    public SearchAdapter(Context mContext, List<Search> searchList){
        this.mContext = mContext;
        this.searchList = searchList;
    }

    // int i refers to the count/position

    // Movie.card is called here
    @Override
    public SearchAdapter.MySearchViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_result_card, viewGroup, false);

        return new MySearchViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final SearchAdapter.MySearchViewHolder viewHolder, int i){
        // Calls getOriginalTitle from movie.java and sets as movie title
        viewHolder.title.setText(searchList.get(i).getOriginalTitle());
        // Calls getVoteAverage from movie.java and sets to userrating
        String vote = Double.toString(searchList.get(i).getVoteAverage());
        viewHolder.userrating.setText(vote);
        // Calls getPosterPath from movie.java and sets poster as thumbnail
            Glide.with(mContext).load(searchList.get(i).getPosterPath())
                .placeholder(R.drawable.load).into(viewHolder.thumbnail);
    }


    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class MySearchViewHolder extends RecyclerView.ViewHolder{

        public TextView title, userrating;
        public ImageView thumbnail;

        public MySearchViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.title);
            userrating = view.findViewById(R.id.userrating);
            thumbnail = view.findViewById(R.id.thumbnail);

            view.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    Search clickedDataItem = searchList.get(position);
                    Intent intent = new Intent(mContext, MovieDetailActivity.class);
                    intent.putExtra("original_title", searchList.get(position).getOriginalTitle());
                    intent.putExtra("poster_path", searchList.get(position).getPosterPath());
                    intent.putExtra("overview", searchList.get(position).getOverview());
                    intent.putExtra("vote_average",Double.toString(searchList.get(position).getVoteAverage()));
                    intent.putExtra("release_date", searchList.get(position).getReleaseDate());
                    intent.putExtra("id", searchList.get(position).getId());

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getOriginalTitle() + clickedDataItem.getId(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
