package sg.edu.tp.moviex.Model;

import com.google.gson.annotations.SerializedName;

public class Trailer {

    @SerializedName("key")
    private String key;


    public Trailer(String key){
        this.key = key; }

        public String getKey(){
        return key; }

        public void setKey(String key){
        this.key = key; }


    }

