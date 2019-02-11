//     =-=-=-=-=-=-=-= SPACE INVADERS: DEMASTERED =-=-=-=-=-=-=-=
/*
 __    __     __  __     ______     __     ______        ______   __         ______     __  __     ______     ______
/\ "-./  \   /\ \/\ \   /\  ___\   /\ \   /\  ___\      /\  == \ /\ \       /\  __ \   /\ \_\ \   /\  ___\   /\  == \
\ \ \-./\ \  \ \ \_\ \  \ \___  \  \ \ \  \ \ \____     \ \  _-/ \ \ \____  \ \  __ \  \ \____ \  \ \  __\   \ \  __<
 \ \_\ \ \_\  \ \_____\  \/\_____\  \ \_\  \ \_____\     \ \_\    \ \_____\  \ \_\ \_\  \/\_____\  \ \_____\  \ \_\ \_\
  \/_/  \/_/   \/_____/   \/_____/   \/_/   \/_____/      \/_/     \/_____/   \/_/\/_/   \/_____/   \/_____/   \/_/ /_/
Players music, what else?
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

    public class MusicPlayer {
        public static final String music_title = "title_music.mp3";
        public static final String music_battle = "battle_music.mp3";
        private static Boolean muteToggle = false;
        private static String track = "null";

        private static Music music;

        public MusicPlayer() {
            //MusicPlayer.track = null;
        }
        public static void setSong(String track) { //switches sound tracks
            if (MusicPlayer.track.equals(track)) { return; } //if the desired song is the current song, do nothing
            else {
                if (music != null) { //destroy the previous music object
                    MusicPlayer.music.stop();
                    MusicPlayer.music.dispose();
                    MusicPlayer.music = null;
                }
                //create new music object and set the current song
                MusicPlayer.music = Gdx.audio.newMusic(Gdx.files.internal(track));
                MusicPlayer.track = track;
                music.play();
                MusicPlayer.music.setLooping(true); //loop music
            }
        }

        public static void toggleMute() {
            MusicPlayer.muteToggle = MusicPlayer.muteToggle == false ? true : false;
            MusicPlayer.music.setVolume(MusicPlayer.muteToggle == false ? 1 : 0);
        }

}
