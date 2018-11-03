package sg.edu.tp.moviex.Model;

import android.view.View;

import sg.edu.tp.moviex.Model.soundtrack;
import sg.edu.tp.moviex.R;

public class soundtrackCollection {



    private soundtrack[] soundtracks = new soundtrack[4];

    public soundtrackCollection()
    {
        prepareSongs();
    }

    public soundtrack searchById(String id)
    {
        // temporary song object called song and set to null
        soundtrack soundtrack = null;

        for (int index = 0; index <soundtracks.length; index++)
        {
            // store each song to the temporary song object.
            soundtrack = soundtracks[index];

            // compare each song Id to the Id that we want to find, and if equal return this as result
            if (soundtrack.getId().equals(id))
            {
                return soundtrack;
            }
        }

        // if song cannot be fond in array, return null
        return soundtrack;
    }

    private void prepareSongs() {
        soundtrack fallout = new soundtrack("S1001",
                "Mission Impossible Fallout",
                "Lorne Balfe",
                R.raw.mission_impossible_soundtrack,
                R.drawable.mission_impossible);

        soundtrack infinity = new soundtrack("S1002",
                "Avengers: Infinity War",
                "Alan Silvestri",
                R.raw.infinity_war_soundtrack,
                R.drawable.infinity_war);

        soundtrack dino = new soundtrack("S1003",
                "Jurassic World: Fallen Kingdom",
                "John Williams",
                R.raw.dino_soundtrack,
                R.drawable.fallen_kingdom);
        soundtrack rogue = new soundtrack("S1004",
                "Mission Impossible Rogue Nation",
                "Joe Kraemer",
                R.raw.rogue_nation_soundtrack,
                R.drawable.rogue_nation_pic);






        //097c7b735ceb410943cbd507a6e1dfda272fd8a8?cid=2afe87a64b0042dabf51f37318616965
        //watch?v=nSDgHBxUbVQ

        soundtracks[0] = fallout;
        soundtracks[1] = infinity;
        soundtracks[2] = dino;
        soundtracks[3] = rogue;
    }

    public soundtrack getNextSoundtrack(String currentSongId)
    {
        // temporary song object called song and set to null
        soundtrack soundtrack = null;
        // starting from index 0 of the song array to the last one,
        // loop through every song item. Increment the index by one after every loop
        // so that the system knows how to go to the next item until the last one
        for(int index = 0; index < soundtracks.length; index++)
        {
            String tempSongId = soundtracks[index].getId();

            if (tempSongId.equals(currentSongId) && (index < soundtracks.length -1))
            {
                soundtrack = soundtracks[index +1];

                break;

            }
        }
        return soundtrack;
    }

    public soundtrack getPrevSong(String currentSongId)
    {
        soundtrack soundtrack = null;

        for(int index = 0; index < soundtracks.length; index++)
        {
            String tempSongId = soundtracks[index].getId();

            if (tempSongId.equals("S1001")){
                    soundtrack = soundtracks[index +3];

            } else if (tempSongId.equals(currentSongId) && (index <= soundtracks.length -1))
            {
                soundtrack = soundtracks[index -1];

                break;
            }

        }
        return soundtrack;
    }

    public soundtrack goToFirst(){

        soundtrack soundtrack = soundtracks[0];

        return soundtrack;
    }

    public soundtrack goToLast(){

        soundtrack soundtrack = soundtracks[3];

        return soundtrack;
    }

}
