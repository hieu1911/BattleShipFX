package com.example.battleshipfx.controller;

import com.example.battleshipfx.BattleShipMain;
import com.example.battleshipfx.helpz.LoadSave;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;


public class Controller {

    protected Dialog<String> dialog;
    protected Dialog<String> dialogHS;

    protected GridPane grid;
    protected Label label = new Label();
    protected Label scoreLabel = new Label();
    protected Button btnE;
    protected Button btnH;
    protected Button btnC;

    public int[] highScore = new int[3];

    protected void initDialog() {
        dialog = new Dialog<>();
        dialog.getDialogPane().setStyle("-fx-background-color: aqua");
        Stage stageD = (Stage) dialog.getDialogPane().getScene().getWindow();
        stageD.getIcons().add(new Image(Objects.requireNonNull(BattleShipMain.class.getResourceAsStream("dialog_icon.png"))));
        dialog.setHeaderText("");

        grid = new GridPane();
        grid.setVgap(20);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(30, 30, 20, 20));

        label.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 56px;" +
                "-fx-text-fill: #242355;");
        scoreLabel.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 36px;" +
                "-fx-text-fill: #242355;");

        btnE = new Button();
        btnH = new Button();
        btnC = new Button();

        btnE.setPrefWidth(150);
        btnH.setPrefWidth(150);
        btnC.setPrefWidth(150);

        btnH.setTextFill(Color.WHITE);
        btnE.setTextFill(Color.WHITE);
        btnC.setTextFill(Color.WHITE);

        btnE.setOnMouseEntered(e -> {
            btnE.setTextFill(Color.BLACK);
        });

        btnE.setOnMouseExited(e -> {
            btnE.setTextFill(Color.WHITE);
        });

        btnC.setOnMouseEntered(e -> {
            btnC.setTextFill(Color.BLACK);
        });

        btnC.setOnMouseExited(e -> {
            btnC.setTextFill(Color.WHITE);
        });

        btnH.setOnMouseEntered(e -> {
            btnH.setTextFill(Color.BLACK);
        });

        btnH.setOnMouseExited(e -> {
            btnH.setTextFill(Color.WHITE);
        });

        btnE.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-background-color: linear-gradient(#4ea3fd, #00e2fa);" +
                "-fx-border: 8;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 20px;" +
                "-fx-border-color: black;");

        btnH.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-background-color: linear-gradient(#42eb7d, #38fad7);" +
                "-fx-border: 8;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 20px;" +
                "-fx-border-color: black;");

        btnC.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-background-color: linear-gradient(#fb739a, #ffe140);" +
                "-fx-border: 8;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 20px;" +
                "-fx-border-color: black;");

        dialog.getDialogPane().setContent(grid);
    }

    protected void initDialogHS(String text, int level) {
        highScore = LoadSave.readFromFile(new File("src/main/java/com/example/battleshipfx/high_score.txt"));
        if (PlayController.score > highScore[level * 3] && text.equals("YOU WIN")) {
            highScore[level * 3] = PlayController.score;
        }
        Arrays.sort(highScore, level * 3, level * 3 + 3);
        LoadSave.writeToFile(new File("src/main/java/com/example/battleshipfx/high_score.txt"), highScore);
        dialogHS = new Dialog<>();
        dialogHS.getDialogPane().setStyle("-fx-background-color: aqua");
        Stage stageD = (Stage) dialogHS.getDialogPane().getScene().getWindow();
        stageD.getIcons().add(new Image(Objects.requireNonNull(BattleShipMain.class.getResourceAsStream("dialog_icon.png"))));

        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(10, 30, 20, 20));

        label = new Label();
        label.setStyle( "-fx-font-weight: bold;" +
                "-fx-font-size: 56px;" +
                "-fx-text-fill: black;");
        highScore = LoadSave.readFromFile(new File("src/main/java/com/example/battleshipfx/high_score.txt"));
        Label l3 = new Label("3. " + highScore[0 + level * 3]);
        Label l2 = new Label("2. " + highScore[1 + level * 3]);
        Label l1 = new Label("1. " + highScore[2 + level * 3]);

        l1.setStyle( "-fx-font-weight: bold;" +
                "-fx-font-size: 40px;" +
                "-fx-text-fill: #660066;");
        l2.setStyle( "-fx-font-weight: bold;" +
                "-fx-font-size: 40px;" +
                "-fx-text-fill: #e83d06;");
        l3.setStyle( "-fx-font-weight: bold;" +
                "-fx-font-size: 40px;" +
                "-fx-text-fill: #008d00;");

        Button btnR = new Button("RESET");
        Button btnChange = new Button(level == 0 ? "EASY" : "HARD");

        btnR.setPrefWidth(150);
        btnChange.setPrefWidth(150);

        btnR.setOnAction(e -> {
            highScore[0 + level * 3] = 0;
            highScore[1 + level * 3] = 0;
            highScore[2 + level * 3] = 0;

            l1.setText("1. 0");
            l2.setText("2. 0");
            l3.setText("3. 0");

            LoadSave.writeToFile(new File("src/main/java/com/example/battleshipfx/high_score.txt"), highScore);
        });

        btnChange.setOnAction(e -> {
            if (btnChange.getText().equals("EASY")) {
                btnChange.setText("HARD");
                l3.setText("3. " + highScore[3]);
                l2.setText("2. " + highScore[4]);
                l1.setText("1. " + highScore[5]);
            } else {
                btnChange.setText("EASY");
                l3.setText("3. " + highScore[0] + "");
                l2.setText("2. " + highScore[1] + "");
                l1.setText("1. " + highScore[2] + "");
            }
        });

        btnR.setOnMouseEntered(e -> {
            btnR.setTextFill(Color.BLACK);
        });

        btnR.setOnMouseExited(e -> {
            btnR.setTextFill(Color.WHITE);
        });

        btnChange.setOnMouseEntered(e -> {
            btnChange.setTextFill(Color.BLACK);
        });

        btnChange.setOnMouseExited(e -> {
            btnChange.setTextFill(Color.WHITE);
        });


        btnChange.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-background-color: linear-gradient(#42eb7d, #38fad7);" +
                "-fx-border: 8;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 20px;" +
                "-fx-border-color: black;");

        btnR.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-background-color: linear-gradient(#fb739a, #ffe140);" +
                "-fx-border: 8;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 20px;" +
                "-fx-border-color: black;");

        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(label);
        vBox.getChildren().add(l1);
        vBox.getChildren().add(l2);
        vBox.getChildren().add(l3);
        vBox.getChildren().add(btnChange);
        vBox.getChildren().add(btnR);
        vBox.getChildren().add(btnE);

//        grid.setAlignment(Pos.CENTER);
        dialogHS.getDialogPane().setContent(vBox);
    }

}
