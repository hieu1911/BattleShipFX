package com.example.battleshipfx.controller;


import com.example.battleshipfx.BattleShipMain;
import com.example.battleshipfx.helpz.Constants;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;


import java.io.IOException;


public class MenuController extends Controller{
    @FXML
    private AnchorPane anchorPaneMenu;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnExit;

    public void playGame() {
        showDialog();
    }

    public void exitGame() {
        BattleShipMain.window.close();
    }

    public void setMouseEnteredPlay() {
        btnPlay.setTextFill(Color.BLACK);
    }

    public void setMouseExitedPlay() {
        btnPlay.setTextFill(Color.WHITE);
    }

    public void setMouseEnteredExit() {
        btnExit.setTextFill(Color.BLACK);
    }

    public void setMouseExitedExit() {
        btnExit.setTextFill(Color.WHITE);
    }

    public void showDialog() {
        Scene sceneE;
        FXMLLoader fxmlLoader = new FXMLLoader(BattleShipMain.class.getResource("edit-view.fxml"));
        try {
            sceneE = new Scene(fxmlLoader.load(), 940, 660);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        initDialog();
        label.setText("Level");

        scoreLabel.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 0px;" +
                "-fx-text-fill: white;");
        btnE.setText("EASY");
        btnH.setText("HARD");
        btnC.setText("CANCEL");

        btnE.setOnAction(e -> {
            Constants.LEVEL = Constants.EASY;
            BattleShipMain.window.setScene(sceneE);
            dialog.setResult(String.valueOf(Boolean.TRUE));
            dialog.close();
        });

        btnH.setOnAction(e -> {
            Constants.LEVEL = Constants.HARD;
            BattleShipMain.window.setScene(sceneE);
            dialog.setResult(String.valueOf(Boolean.TRUE));
            dialog.close();
        });

        btnC.setOnAction(e -> {
            dialog.setResult(String.valueOf(Boolean.TRUE));
            dialog.close();
        });

        grid.add(label, 1, 0);
        grid.add(btnE, 0, 1);
        grid.add(btnH, 1, 1);
        grid.add(btnC, 2, 1);

        dialog.show();
    }

}
