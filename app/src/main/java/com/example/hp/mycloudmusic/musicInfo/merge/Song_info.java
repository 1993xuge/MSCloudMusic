package com.example.hp.mycloudmusic.musicInfo.merge;

import java.util.List;

public class Song_info {
    public int total;
    public List<Song> song_list;

    public int getTotal() {
        return total;
    }

    public List<Song> getSong_list() {
        return song_list;
    }

    public void setSong_list(List<Song> song_list) {
        this.song_list = song_list;
    }
}
