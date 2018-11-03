package sg.edu.tp.moviex.Model;

import android.net.Uri;
import android.provider.MediaStore;

public class soundtrack {

    private String Id;
    private String title;
    private String artist;
    private int fileLink;
    private int coverArt;


    public soundtrack(String Id, String title, String artist, int fileLink, int coverArt)
    {
        this.Id = Id;
        this.title = title;
        this.artist = artist;
        this.fileLink = fileLink;
        this.coverArt = coverArt;
    }



    public String getId(){return Id; }
    public void SetId(String Id) {this.Id = Id; }

    public String getTitle() {return title; }
    public void SetTitle(String title) {this.title = title; }

    public String getArtist() {return artist; }
    public void SetArtist(String artist) {this.artist = artist; }

    public int getFileLink() {return fileLink; }
    public void SetFileLink(int fileLink) {this.fileLink = fileLink; }

    public int getCoverArt() {return coverArt; }
    public void SetCoverArt(int coverArt) {this.coverArt = coverArt; }
}

