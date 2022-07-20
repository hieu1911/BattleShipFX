package com.example.battleshipfx.controller;

import com.example.battleshipfx.BattleShipMain;
import com.example.battleshipfx.helpz.ImageFix;
import com.example.battleshipfx.helpz.Music;
import com.example.battleshipfx.object.Ship;
import com.example.battleshipfx.objectManager.ShipManager;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import static com.example.battleshipfx.controller.EditController.*;
import static com.example.battleshipfx.helpz.Constants.*;
import static com.example.battleshipfx.helpz.LoadSave.delay;

public class PlayController extends Controller {
    @FXML
    private AnchorPane anchorPanePlay;
    @FXML
    private ImageView turnoff_on;
    @FXML
    private Button btnPlay;
    @FXML
    private Text headerTextP;
    @FXML
    private Text headerTextA;
    @FXML
    private Text numberRemainingP;
    @FXML
    private Text numberRemainingA;

    private boolean render = true;
    private int idShip;
    private boolean attackA = true;
    public static int score;

    private final Image atlas = new Image(Objects.requireNonNull(BattleShipMain.class.getResourceAsStream("Tokens.png")));
    private final ImageView rada = new ImageView(ImageFix.cutImg(atlas, 2 * 32, 0, 32, 32));

    private final int[][] playerMap = playMap;
    private final int[][] aiMap = enemyMap;
    private final int[][] checkAIMap = new int[NUM_ROWS][NUM_COLS];
    private final int[][] checkPlayerMap = new int[NUM_ROWS][NUM_COLS];

    private final ShipManager playerShipsP = EditController.playerShips;
    private final ShipManager aiShipsP = EditController.aiShips;


    public void playGame() {
        if (render) {
            addMouseEvent();
            for (Ship ship : playerShipsP.getShips()) {
                renderShip(60, ship);
            }
        }
        render = false;
        btnPlay.setText(String.valueOf(score));
    }

//    draw ship
    private void renderShip(int x, Ship ship) {
            ImageView imageView = new ImageView();
            Image image = null;
            switch (ship.getId()) {
                case 0 -> image = new Image(Objects.requireNonNull(BattleShipMain.class.getResourceAsStream("ship5.png")));
                case 1 -> image = new Image(Objects.requireNonNull(BattleShipMain.class.getResourceAsStream("ship4.png")));
                case 2 -> image = new Image(Objects.requireNonNull(BattleShipMain.class.getResourceAsStream("ship3.png")));
                case 3 -> image = new Image(Objects.requireNonNull(BattleShipMain.class.getResourceAsStream("ship3_2.png")));
                case 4 -> image = new Image(Objects.requireNonNull(BattleShipMain.class.getResourceAsStream("ship2.png")));
            }

            imageView.setImage(image);
            imageView.setFitWidth(ship.getLength() * 40);
            imageView.setFitHeight(40);
            imageView.setLayoutX(x + ship.getCol() * 40);
            imageView.setLayoutY(170 + ship.getRow() * 40);

            Rotate rotate = new Rotate();
            rotate.setPivotX(20);
            rotate.setPivotY(20);
            rotate.setAngle(ship.getDirection() * 90);

            imageView.getTransforms().add(rotate);
            anchorPanePlay.getChildren().add(imageView);
    }

//    mouse input
    private void addMouseEvent() {
        EventHandler<MouseEvent> mouseEventMoved = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getX() >= 520 && e.getX() <= 920 && e.getY() >= 170 && e.getY() <= 570) {
                    int mouseX = (int)((e.getX() - 522) / 40);
                    int mouseY = (int)((e.getY() - 172) / 40);
                    if (checkAIMap[mouseY][mouseX] == 0) {
                        anchorPanePlay.getChildren().remove(rada);
                        rada.setFitHeight(40);
                        rada.setFitWidth(40);
                        rada.setLayoutX(mouseX * 40 + 520);
                        rada.setLayoutY(mouseY * 40 + 170);
                        anchorPanePlay.getChildren().add(rada);
                    }
                }
            }
        };

        EventHandler<MouseEvent> mouseEventClick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (attackA) {
                    anchorPanePlay.getChildren().remove(rada);
                    int col = (int) ((e.getX() - 520) / 40);
                    int row = (int) ((e.getY() - 170) / 40);
                    if (e.getX() >= 520 && e.getX() <= 920 && e.getY() >= 170 && e.getY() <= 570 && checkAIMap[row][col] == 0) {
//                        btnPlay.setText(String.valueOf(++score));
                        attackA = false;
                        checkAIMap[row][col] = 1;
                        drawRocket(row, col, 1);
                    }
                }
            }
        };

        anchorPanePlay.addEventHandler(MouseEvent.MOUSE_MOVED, mouseEventMoved);
        anchorPanePlay.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventClick);
    }

    public void attackAIShip(int row, int col) {
        ImageView imgViewD;
        if (isShip(aiMap,row, col)) {
            imgViewD = new ImageView(ImageFix.cutImg(atlas, 32, 0, 32, 32));
            for (Ship ship : aiShipsP.getShips()) {
                if (ship.getId() == aiIdMap[row][col]) {
                    ship.setAttackShip(row, col);
                    btnPlay.setText(increaseScore(ship.getLength()) + "");
                }
            }
            checkShipDestroyed(aiShipsP);
            setAttackA();
        } else {
            imgViewD = new ImageView(ImageFix.cutImg(atlas, 0, 0, 32, 32));
            btnPlay.setText(increaseScore(-1) + "");
            attackPlayerShip();
        }
        imgViewD.setFitHeight(40);
        imgViewD.setFitWidth(40);
        imgViewD.setLayoutX(col * 40 + 520);
        imgViewD.setLayoutY(row * 40 + 170);
        anchorPanePlay.getChildren().add(imgViewD);
        checkShipDestroyed(aiShipsP);
        numberRemainingA.setText("Number of ships remaining: " + aiShipsP.getShipsRemaining());
        checkEndGame();
    }

    public void attackPlayerShip() {
        setColorTextP();
        Random rand = new Random();
        int r;
        int c;
        do {
            r = rand.nextInt(NUM_ROWS);
            c = rand.nextInt(NUM_COLS);
        } while (checkPlayerMap[r][c] == 1);

        drawRocket(r, c, 0);
    }

//    translate rocket
    public void drawRocket(int row, int col, int type) {
        ImageView imgRoc = new ImageView();
        Image imgRocket = new Image(Objects.requireNonNull(BattleShipMain.class.getResourceAsStream("rocket.png")));
        int fromX = type == 0 ? col * 40 + 60 : col * 40 + 520;
        imgRoc.setImage(imgRocket);
        imgRoc.setLayoutX(Math.max(fromX, 0));
        imgRoc.setLayoutY(row *  40 + 170 - 40 * 3);
        imgRoc.setFitWidth(36);
        imgRoc.setFitHeight(16);

        Rotate rotate = new Rotate();
        rotate.setPivotX(18);
        rotate.setPivotY(10);
        rotate.setAngle(90);

        anchorPanePlay.getChildren().add(imgRoc);

        TranslateTransition translateTransition = new TranslateTransition(new Duration(800), imgRoc);
        translateTransition.setToY(40 * 3);
        imgRoc.getTransforms().add(rotate);
        translateTransition.play();

        delay(800, () -> {
            anchorPanePlay.getChildren().remove(imgRoc);
            if (playMusic) {
                Music.playBoomMusic();
            }
        });

        delay(900, () -> drawBoom(row, col, type));
    }

    public int increaseScore(float length) {
        score += length * 20;
        score  = Math.max(score, 0);
        return score;
    }

//    render boom in (row, col)
    public void drawBoom(int row, int col, int type) {
        ImageView imgBoom = new ImageView(ImageFix.cutImg(atlas, 3 * 32, 0, 32, 32));
        drawToken(imgBoom, row, col, type);
        delay(250, () -> {
            anchorPanePlay.getChildren().remove(imgBoom);
            if (type == 1) {
                attackAIShip(row, col);
            } else {
                drawAttackPlayerShip(row, col);
            }
        });
    }

    public void drawAttackPlayerShip(int r, int c) {
        ImageView imgViewD;
        checkPlayerMap[r][c] = 1;
        checkShipDestroyed(playerShipsP);
        numberRemainingP.setText("Number of ships remaining: " + playerShipsP.getShipsRemaining());
        if (isShip(playerMap, r, c)) {
            idShip = playerIdMap[r][c];
            imgViewD = new ImageView(ImageFix.cutImg(atlas, 32, 0, 32, 32));
            drawToken(imgViewD, r, c, 0);
            for (Ship ship : playerShipsP.getShips()) {
                if (ship.getId() == playerIdMap[r][c]) {
                    ship.setAttackShip(r, c);
                }
            }

            btnPlay.setText(increaseScore((float) -0.5) + "");

            if (!checkEndGame()) {
                if (LEVEL == HARD && !playerShipsP.checkShipDestroyById(idShip)) {
                    outerLoop:
                    for (int i = 0; i < NUM_ROWS; ++i) {
                        for (int j = 0; j < NUM_COLS; ++j) {
                            if (playerIdMap[i][j] == idShip && checkPlayerMap[i][j] == 0 ) {
                                checkShipDestroyed(playerShipsP);
                                drawRocket(i, j, 0);
                                break outerLoop;
                            }
                        }
                    }
                } else{
                    checkShipDestroyed(playerShipsP);
                    attackPlayerShip();
                }
            }

        } else {
            imgViewD = new ImageView(ImageFix.cutImg(atlas, 0, 0, 32, 32));
            drawToken(imgViewD, r, c, 0);
            checkShipDestroyed(playerShipsP);
            setAttackA();
        }
        checkShipDestroyed(playerShipsP);
    }

//    draw token when destroy ship
    public void checkShipDestroyed(ShipManager shipManager) {
        for (Ship ship : shipManager.getShips()) {
            if (ship.isDestroy()) {
                if (shipManager.getType() == 1) {
                    if (!ship.isRender()) {
                        btnPlay.setText(increaseScore(10) + "");
                        renderShip(520, ship);
                        ship.setRender(true);
                    }
                }
                int[][] arr = ship.getBorderShip();
                int r, c;
                for (int i = 0; i < arr.length; ++i) {
                    for (int j = 1; j < arr[0].length; ++j) {
                        if (ship.getDirection() == RIGHT || ship.getDirection() == LEFT) {
                            c = arr[i][0];
                            r = arr[i][j];
                        } else {
                            r = arr[i][0];
                            c = arr[i][j];
                        }
                        if (shipManager.getType() == 0 && r >=0 && r <= 9 && c >= 0 && c <= 9) {
                            if (playerMap[r][c] == 0 && checkPlayerMap[r][c] == 0) {
                                checkPlayerMap[r][c] = 1;
                                ImageView imageView = new ImageView(ImageFix.cutImg(atlas, 0, 0, 32, 32));
                                drawToken(imageView, r, c, 0);
                            }
                        } else if (shipManager.getType() == 1 && r >=0 && r <= 9 && c >= 0 && c <= 9) {
                            if (aiMap[r][c] == 0 && checkAIMap[r][c] == 0) {
                                checkAIMap[r][c] = 1;
                                ImageView imageView = new ImageView(ImageFix.cutImg(atlas, 0, 0, 32, 32));
                                drawToken(imageView, r, c, 1);
                            } else if (aiMap[r][c] == 1 && checkAIMap[r][c] == 1) {
                                ImageView imageView = new ImageView(ImageFix.cutImg(atlas, 32, 0, 32, 32));
                                drawToken(imageView, r, c, 1);
                            }
                        }
                    }
                }
            }
        }
    }

//    mark on the map
    public void drawToken(ImageView imgView, int row, int col, int type) {
        anchorPanePlay.getChildren().remove(imgView);
        imgView.setFitHeight(40);
        imgView.setFitWidth(40);
        if (type == 0) {
            imgView.setLayoutX(col * 40 + 60);
        } else {
            imgView.setLayoutX(col * 40 + 520);
        }
        imgView.setLayoutY(row * 40 + 170);
        anchorPanePlay.getChildren().add(imgView);
    }

    public boolean checkEndGame() {
        if (playerShipsP.isAddDestroyed()) {
            for (int i = 0; i < checkPlayerMap.length; ++i) {
                for (int j = 0; j < checkPlayerMap[0].length; ++j) {
                    if (checkPlayerMap[i][j] == 0) {
                        ImageView imageView = new ImageView(ImageFix.cutImg(atlas, 0, 0, 32, 32));
                        drawToken(imageView, i, j, 0);
                    }
                }
            }
            showDialog("YOU LOSE");
            return true;
        } else if (aiShipsP.isAddDestroyed()) {
            for (int i = 0; i < checkAIMap.length; ++i) {
                for (int j = 0; j < checkAIMap[0].length; ++j) {
                    if (checkAIMap[i][j] == 0) {
                        ImageView imageView = new ImageView(ImageFix.cutImg(atlas, 0, 0, 32, 32));
                        drawToken(imageView, i, j, 1);
                    }
                }
            }
            scoreLabel.setText(String.valueOf("    SCORE: " + score));
            showDialog("YOU WIN");
            return true;
        }
        return false;
    }

//    Give the player a choice when the game is over
    public void showDialog(String text) {
        initDialog();
        Scene sceneE;
        FXMLLoader fxmlLoader = new FXMLLoader(BattleShipMain.class.getResource("menu-view.fxml"));
        try {
            sceneE = new Scene(fxmlLoader.load(), 940, 660);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        label.setText(text);

        btnE.setText("MENU");
        btnH.setText("HIGH SCORE");
        btnC.setText("EXIT");

        btnH.setPrefWidth(250);

        btnE.setOnAction(e -> {
            dialog.setResult(String.valueOf(Boolean.TRUE));
            dialog.close();
            if (dialogHS != null) {
                dialogHS.setResult(String.valueOf(Boolean.TRUE));
                dialogHS.close();
            }
            BattleShipMain.window.setScene(sceneE);
            score = 0;
            for (int i = 0; i < playMap.length; i++) {
                for (int j = 0; j < playMap[0].length; j++) {
                    playMap[i][j] = 0;
                    enemyMap[i][j] = 0;
                    idPlayerMap[i][j] = -1;
                    idAIMap[i][j] = -1;
                    checkPlayerMap[i][j] = 0;
                    checkAIMap[i][j] = 0;
                }
            }
            playerShips.getShips().clear();
            aiShips.getShips().clear();
            Music.playMusic();
        });

        btnH.setOnAction(e -> {
            dialog.setResult(String.valueOf(Boolean.TRUE));
            dialog.close();
            initDialogHS(text, LEVEL);
            label.setText("HIGH SCORE");
            dialogHS.show();
        });

        btnC.setOnAction(e -> {
            dialog.setResult(String.valueOf(Boolean.TRUE));
            dialog.close();
            BattleShipMain.window.close();
        });

        grid.add(label, 1, 0);
        grid.add(scoreLabel, 1, 1);
        grid.add(btnE, 0, 2);
        grid.add(btnH, 1, 2);
        grid.add(btnC, 2, 2);

        dialog.show();
    }

    public void setAttackA() {
        attackA = true;
        setColorTextA();
    }

    public boolean isShip(int[][] map, int row, int col) {
        return map[row][col] == 1;
    }

//    turn on/off music
    public void turnoff_on() {
        if(playMusic) {
            turnoff_on.setImage(new Image(Objects.requireNonNull(BattleShipMain.class.getResourceAsStream("mute.jpg"))));
            Music.player.pause();
        } else {
            turnoff_on.setImage(new Image(Objects.requireNonNull(BattleShipMain.class.getResourceAsStream("unmute.jpg"))));
            Music.player.play();
        }
        playMusic = !playMusic;
    }

//    css headerText
    public void setColorTextA() {
        headerTextA.setStyle(" -fx-font-smoothing-type: lcd;" + "-fx-font-weight: bold;" + "-fx-fill: #fe2702 ;" + "-fx-font-size: 20pt;");
        numberRemainingA.setStyle("-fx-font-smoothing-type: lcd;" + "-fx-fill: #fe2702 ;" + "-fx-font-size: 16pt;");
        headerTextP.setStyle(" -fx-font-smoothing-type: lcd;" + "-fx-font-weight: bold;" + "-fx-fill: white ;" + "-fx-font-size: 20pt;");
        numberRemainingP.setStyle("-fx-font-smoothing-type: lcd;" + "-fx-fill: white ;" + "-fx-font-size: 16pt;");
    }

//    css headerText
    public void setColorTextP() {
        headerTextP.setStyle(" -fx-font-smoothing-type: lcd;" + "-fx-font-weight: bold;" + "-fx-fill: #fe2702 ;" + "-fx-font-size: 20pt;");
        numberRemainingP.setStyle("-fx-font-smoothing-type: lcd;" + "-fx-fill: #fe2702 ;" + "-fx-font-size: 16pt;");
        headerTextA.setStyle(" -fx-font-smoothing-type: lcd;" + "-fx-font-weight: bold;" + "-fx-fill: white ;" + "-fx-font-size: 20pt;");
        numberRemainingA.setStyle("-fx-font-smoothing-type: lcd;" + "-fx-fill: white ;" + "-fx-font-size: 16pt;");
    }

}