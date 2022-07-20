package com.example.battleshipfx;

import com.example.battleshipfx.helpz.Music;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class BattleShipMain extends Application {

    public static Stage window;

    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(BattleShipMain.class.getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 940, 660);
        stage.setTitle("Battle Ship");
        stage.getIcons().add(new Image(Objects.requireNonNull(BattleShipMain.class.getResourceAsStream("game_icon.png"))));

        stage.setResizable(false);

        Music.playMusic();

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}