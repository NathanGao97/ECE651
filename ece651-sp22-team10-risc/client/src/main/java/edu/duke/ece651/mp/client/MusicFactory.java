package edu.duke.ece651.mp.client;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class MusicFactory {

    public static MediaPlayer createMediaPlayer(String s) {
        if (s.equals("chopper")) {
            return createChopperPlayer();
        }
        if (s.equals("franky")) {
            return createFrankyPlayer();
        }
        if (s.equals("kaidou")) {
            return createKaidouPlayer();
        }
        if (s.equals("spy")) {
            return createLucciPlayer();
        }
        if (s.equals("luffy")) {
            return createLuffyPlayer();
        }
        if (s.equals("nami")) {
            return createNamiPlayer();
        }
        if (s.equals("sanji")) {
            return createSanjiPlayer();
        }
        if (s.equals("usopp")) {
            return createUsoppPlayer();
        }
        return createZoroPlayer();

    }
    public static MediaPlayer createBGMPlayer() {
        String s = "music/bgm.mp4";
        Media m = new Media(Paths.get(s).toUri().toString());
        MediaPlayer mP = new MediaPlayer(m);
        return mP;
    }
    public static MediaPlayer createChopperPlayer() {
        String s = "music/chopper-voice.m4a";
        Media m = new Media(Paths.get(s).toUri().toString());
        MediaPlayer mP = new MediaPlayer(m);
        return mP;
    }

    public static MediaPlayer createFrankyPlayer() {
        String s = "music/franky-voice.m4a";
        Media m = new Media(Paths.get(s).toUri().toString());
        MediaPlayer mP = new MediaPlayer(m);
        return mP;
    }

    public static MediaPlayer createKaidouPlayer() {
        String s = "music/kaidou-voice.m4a";
        Media m = new Media(Paths.get(s).toUri().toString());
        MediaPlayer mP = new MediaPlayer(m);
        return mP;
    }
    public static MediaPlayer createLucciPlayer() {
        String s = "music/lucci-voice.m4a";
        Media m = new Media(Paths.get(s).toUri().toString());
        MediaPlayer mP = new MediaPlayer(m);
        return mP;
    }
    public static MediaPlayer createLuffyPlayer() {
        String s = "music/luffy-voice.m4a";
        Media m = new Media(Paths.get(s).toUri().toString());
        MediaPlayer mP = new MediaPlayer(m);
        return mP;
    }
    public static MediaPlayer createNamiPlayer() {
        String s = "music/nami-voice.m4a";
        Media m = new Media(Paths.get(s).toUri().toString());
        MediaPlayer mP = new MediaPlayer(m);
        return mP;
    }
    public static MediaPlayer createSanjiPlayer() {
        String s = "music/sanji-voice.m4a";
        Media m = new Media(Paths.get(s).toUri().toString());
        MediaPlayer mP = new MediaPlayer(m);
        return mP;
    }
    public static MediaPlayer createUsoppPlayer() {
        String s = "music/usopp-voice.m4a";
        Media m = new Media(Paths.get(s).toUri().toString());
        MediaPlayer mP = new MediaPlayer(m);
        return mP;
    }
    public static MediaPlayer createZoroPlayer() {
        String s = "music/zoro-voice.m4a";
        Media m = new Media(Paths.get(s).toUri().toString());
        MediaPlayer mP = new MediaPlayer(m);
        return mP;
    }


}
