package com.example.battleshipfx.helpz;

import com.example.battleshipfx.BattleShipMain;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Music {

    public static MediaPlayer player;
    public static MediaPlayer boomMusic;

    public static void playMusic() {
        try {
            String fileName = BattleShipMain.class.getResource("music.mp3").toURI().toString();
            Media media = new Media(fileName);
            player = new MediaPlayer(media);
            player.setOnEndOfMedia(new Runnable() {
                public void run() {
                    player.seek(Duration.ZERO);
                }
            });
            player.autoPlayProperty();
            player.setVolume(2);
            player.play();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playBoomMusic() {
        try {
            String fileName = BattleShipMain.class.getResource("boom3.mp3").toURI().toString();
            Media media = new Media(fileName);
            boomMusic = new MediaPlayer(media);
            boomMusic.play();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
