package sg.edu.tp.moviex.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import sg.edu.tp.moviex.R;
import sg.edu.tp.moviex.Model.soundtrack;
import sg.edu.tp.moviex.Model.soundtrackCollection;
import sg.edu.tp.moviex.util.AppUtil;

public class SoundtrackListActivity extends AppCompatActivity {
    private soundtrackCollection soundtrackCollection = new soundtrackCollection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soundtrack_list);
    }
    public void handlerSelection(View view) {
        // Get ID of selected song
        String resourceId = AppUtil.getResourceId(this, view);

        // Search for the selected song based on the ID so
        //that all information/data of the song can be retrieved from a song list.
        soundtrack selectedSong = soundtrackCollection.searchById(resourceId);

        //Popup a message on the screen to show the title of the song.
        AppUtil.popMessage(this, "Streaming song: " + selectedSong.getTitle());


        //Send the song data to the player screen to be played.
        sendDataToActivity(selectedSong);
    }

    public void sendDataToActivity(soundtrack soundtrack)
    {
        Intent intent = new Intent(this, PlaySoundtrackActivity.class);

        intent.putExtra("id", soundtrack.getId());
        intent.putExtra("title", soundtrack.getTitle());
        intent.putExtra("artist", soundtrack.getArtist());
        intent.putExtra("fileLink", soundtrack.getFileLink());
        intent.putExtra("coverArt", soundtrack.getCoverArt());

        startActivity(intent);
    }

    public void openHomeActivity(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}
