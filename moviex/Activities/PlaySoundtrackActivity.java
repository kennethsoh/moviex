package sg.edu.tp.moviex.Activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import sg.edu.tp.moviex.R;
import sg.edu.tp.moviex.Model.soundtrack;
import sg.edu.tp.moviex.Model.soundtrackCollection;

public class PlaySoundtrackActivity extends AppCompatActivity {

    View view;

    // website to stream music
    private static final String BASE_URL = "https://p.scdn.co/mp3-preview/";

    //https://p.scdn.co/mp3-preview/
    //https://www.youtube.com/watch?v=nSDgHBxUbVQ

    // variables are song information that will be used
    private String songId = "";
    private String title = "";
    private String artist = "";
    private Integer fileLink;
    private Integer coverArt;
    private ToggleButton switchLoop;


    private MediaPlayer player = null;

    // position of song in playback
    private int musicPosition = 0;

    // this button acts as both play and pause button
    private Button btnPlayPause = null;

    private soundtrackCollection soundtrackCollection = new soundtrackCollection();

    private void retrieveData() {
        Bundle songData = this.getIntent().getExtras();

        songId = songData.getString("id");
        title = songData.getString("title");
        artist = songData.getString("artist");
        fileLink = songData.getInt("fileLink");
        coverArt = songData.getInt("coverArt");


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_soundtrack);

        btnPlayPause = findViewById(R.id.btnPlayPause);
        switchLoop = findViewById(R.id.toggleButton);

        retrieveData();

        displaySong(title, artist, coverArt);

        switchLoop.setVisibility(View.INVISIBLE);

        switchLoop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ((isChecked)) {
                    // auto play next song if auto play button is checked
                    autoPlayNextSong();

                }


            }
        });


    }





    private void displaySong(String title, String artist, Integer coverArt) {
        // Retrieve song title from UI and set it as title
        TextView txtTitle = findViewById(R.id.txtSongTitle);
        txtTitle.setText(title);

        // Retrieve song Artist for UI and set as artist name
        TextView txtArtist = findViewById(R.id.txtArtist);
        txtArtist.setText(artist);

        // get ID of cover art from drawable folder.

        // retrieve cover art ImageView from the UI screen
        ImageView ivCoverArt = findViewById(R.id.imgCoverArt);

        // Set the selected cover art image to the ImageView in the screen
        ivCoverArt.setImageResource(coverArt);


    }

    private void preparePlayer(int fileLink) {
        player = MediaPlayer.create(PlaySoundtrackActivity.this, fileLink);

       /* try {
            // audio stream type = music streaming
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);

            // source of the music
            player.create(this, fileLink);


            // prepare player for playback
            player.prepare();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    } */

   /* public void onPrepared(){
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                player.start();
            }
        });
    } */
    }

    public void playOrPauseMusic(View view)
    {

        // if player not created, prepare (create) player
        if (player == null)
            preparePlayer(fileLink);

        // once created, start regardless
        //player.start();

        // if player is not playing
        if (!player.isPlaying())
        {

            if (musicPosition > 0)
            {
                player.seekTo(musicPosition);
            }

            // Start the player
            player.start();


            switchLoop.setVisibility(View.VISIBLE);






            // Set the text of the play button to "pause"
            btnPlayPause.setText("Pause");

            setTitle("Now Playing " + title + "-" + artist);

        }
        else
        {

            // pause the music
            pauseMusic();
        }
    }

    private void pauseMusic()

    {
        // pause player
        player.pause();


        // get current position of music that is playing
        musicPosition = player.getCurrentPosition();

        btnPlayPause.setText("Play");

        gracefullyStopWhenMusicEnds();

    }

    private void gracefullyStopWhenMusicEnds()
    {
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopActivities();
            }
        });

    }
    private void stopActivities()
    {

        if (player != null)
        {
            btnPlayPause.setText("Play");
            musicPosition = 0;
            setTitle(" ");
            player.stop();
            player.release();
            player = null;


        }
    }


    public void playNext(View view) {
        soundtrack nextSong = soundtrackCollection.getNextSoundtrack(songId);

        if (nextSong != null) {
            songId = nextSong.getId();
            title = nextSong.getTitle();
            artist = nextSong.getArtist();
            fileLink = nextSong.getFileLink();
            coverArt = nextSong.getCoverArt();



            // display next song info on screen
            displaySong(title, artist, coverArt);

            // call stopActivities() method to stop current playing song
            stopActivities();

            // call playOrPause() method to play the song
            playOrPauseMusic(view);


        }

        // Go to first song when at the end of the list
        if (nextSong == null){
            nextSong = soundtrackCollection.goToFirst();
            songId = nextSong.getId();
            title = nextSong.getTitle();
            artist = nextSong.getArtist();
            fileLink = nextSong.getFileLink();
            coverArt = nextSong.getCoverArt();

            // display next song info on screen
            displaySong(title, artist, coverArt);

            // call stopActivities() method to stop current playing song
            stopActivities();

            // call playOrPause() method to play the song
            playOrPauseMusic(view);

        }
    }
    public void playPrev(View view) {
        soundtrack prevSong = soundtrackCollection.getPrevSong(songId);

        if (prevSong != null) {
            songId = prevSong.getId();
            title = prevSong.getTitle();
            artist = prevSong.getArtist();
            fileLink = prevSong.getFileLink();
            coverArt = prevSong.getCoverArt();

            //url = BASE_URL + fileLink;

            displaySong(title, artist, coverArt);

            stopActivities();

            playOrPauseMusic(view);

        } else if(songId.equals("S1001")) {
            prevSong = soundtrackCollection.goToLast();
            songId = prevSong.getId();
            title = prevSong.getTitle();
            artist = prevSong.getArtist();
            fileLink = prevSong.getFileLink();
            coverArt = prevSong.getCoverArt();

            // display next song info on screen
            displaySong(title, artist, coverArt);

            // call stopActivities() method to stop current playing song
            stopActivities();

            // call playOrPause() method to play the song
            playOrPauseMusic(view);

        } else {
            Toast.makeText(this, "Cannot play soundtrack, return to soundtrack list", Toast.LENGTH_SHORT).show();
        }

    }

    // auto play next song when current song completes
    public void autoPlayNextSong () {
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playNext(view);
            }
        });

    }

    public void openSoundtrackList (View view){
        Intent intent = new Intent(PlaySoundtrackActivity.this, SoundtrackListActivity.class);
        startActivity(intent);
    }

}
