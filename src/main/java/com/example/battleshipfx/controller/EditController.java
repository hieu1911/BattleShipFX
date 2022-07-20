package com.example.battleshipfx.controller;

import com.example.battleshipfx.BattleShipMain;
import com.example.battleshipfx.helpz.Constants;
import com.example.battleshipfx.object.Ship;
import com.example.battleshipfx.objectManager.ShipManager;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import static com.example.battleshipfx.helpz.Constants.*;

public class EditController {
    @FXML
    private AnchorPane anchorPaneEdit;
    @FXML
    private ImageView imgShip5;
    @FXML
    private ImageView imgShip4;
    @FXML
    private ImageView imgShip3;
    @FXML
    private ImageView imgShip3_2;
    @FXML
    private ImageView imgShip2;
    @FXML
    private Text headerTextEdit;
    @FXML
    private VBox vBoxShip;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnRandom;

    private int totalShip = 5;
    private boolean canPutShip = true;
    private boolean canPutShip5 = true;
    private boolean canPutShip4 = true;
    private boolean canPutShip3 = true;
    private boolean canPutShip3_2 = true;
    private boolean canPutShip2 = true;

    private int[][] playerMap = Constants.playMap;
    private int[][] aiMap = Constants.enemyMap;

    public static int[][] playerIdMap = idPlayerMap;
    public static int[][] aiIdMap = idAIMap;

    private int[] rotation = new int[5];

    public static ShipManager playerShips = new ShipManager(0);
    public static ShipManager aiShips = new ShipManager(1);

    private EventHandler<MouseEvent> eventHandlerM;
    private EventHandler<MouseEvent> eventHandlerC;
    private EventHandler<KeyEvent> keyEventEventHandler;

    public void mouseEnteredRandom() {
        btnRandom.setTextFill(Color.BLACK);
    }

    public void mouseExitedRandom() {
        btnRandom.setTextFill(Color.WHITE);
    }

    public void mouseEnteredClear() {
        btnClear.setTextFill(Color.BLACK);
    }

    public void mouseExitedClear() {
        btnClear.setTextFill(Color.WHITE);
    }

    public void mouseEnteredPlay() {
        btnPlay.setTextFill(Color.BLACK);
    }

    public void mouseExitedPlay() {
        btnPlay.setTextFill(Color.WHITE);
    }

    public void playGame() {
        Scene sceneP;
        FXMLLoader fxmlLoader = new FXMLLoader(BattleShipMain.class.getResource("play-view.fxml"));
        try {
            sceneP = new Scene(fxmlLoader.load(), 940, 660);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        placeAllShipsOfAI();
        System.out.println("vi tri tau cua Player");
        printGrid(playerMap);

//        System.out.println("id ai map");
//        printGrid(aiIdMap);
        if (totalShip == 0) {
            for (Ship ship : playerShips.getShips()) {
                ship.setShipPart();
            }
            BattleShipMain.window.setScene(sceneP);
            new PlayController();
        } else {
            headerTextEdit.setStyle(" -fx-font-smoothing-type: lcd;" + "-fx-font-weight: bold;" + "-fx-fill: red;" + "-fx-font-size: 28pt;");
            headerTextEdit.setText("Please put all ship into your map");
        }
    }

    public void removeEventHandler() {
        if (eventHandlerM != null) {
            anchorPaneEdit.removeEventHandler(MouseEvent.MOUSE_MOVED, eventHandlerM);
        }
        if (eventHandlerC != null) {
            anchorPaneEdit.removeEventHandler(MouseEvent.MOUSE_RELEASED, eventHandlerC);
        }
        if (keyEventEventHandler != null) {
            anchorPaneEdit.removeEventHandler(KeyEvent.KEY_PRESSED, keyEventEventHandler);
        }
        BattleShipMain.window.getScene().setCursor(Cursor.DEFAULT);
    }

    public void putShip5() {
        if (canPutShip && canPutShip5) {
            removeEventHandler();
            putShip(imgShip5, new Ship(0, 5, RIGHT));
            canPutShip = false;
            canPutShip5 = false;
        }
    }

    public void putShip4() {
        if (canPutShip && canPutShip4) {
            removeEventHandler();
            putShip(imgShip4, new Ship(1, 4, RIGHT));
            canPutShip = false;
            canPutShip4 = false;
        }
    }

    public void putShip3() {
        if (canPutShip && canPutShip3) {
            removeEventHandler();
            putShip(imgShip3, new Ship(2, 3, RIGHT));
            canPutShip = false;
            canPutShip3 = false;
        }
    }

    public void putShip3_2() {
        if (canPutShip && canPutShip3_2) {
            removeEventHandler();
            putShip(imgShip3_2, new Ship(3, 3, RIGHT));
            canPutShip = false;
            canPutShip3_2 = false;
        }
    }

    public void putShip2() {
        if (canPutShip && canPutShip2) {
            removeEventHandler();
            putShip(imgShip2, new Ship(4, 2, RIGHT));
            canPutShip = false;
            canPutShip2 = false;
        }
    }

//    put ship into the map
    public void putShip(ImageView imgView, Ship ship) {
        imgView.setOpacity(0.5);

        eventHandlerM = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getX() >= 10 && e.getX() <= 560 && e.getY() >= 100 && e.getY() <= 650) {
                    int mouseX = (int) ((e.getX() - 10) / 50);
                    int mouseY = (int) ((e.getY() - 100) / 50);

                    if (check(ship.getLength(), mouseX - 1, mouseY - 1 , ship.getDirection(), playerMap)) {
                        BattleShipMain.window.getScene().setCursor(Cursor.HAND);
                        anchorPaneEdit.getChildren().remove(imgView);
                        imgView.setLayoutX(mouseX * 50 + 10);
                        imgView.setLayoutY(mouseY * 50 + 100);
                        headerTextEdit.setStyle(" -fx-font-smoothing-type: lcd;" + "-fx-font-weight: bold;" + "-fx-fill: white;" + "-fx-font-size: 28pt;");
                        headerTextEdit.setText("Put the ships into your map");

                        if (!playerShips.getShips().contains(ship)) {
                            playerShips.getShips().add(ship);
                        }

                        ship.setCol(mouseX - 1);
                        ship.setRow(mouseY - 1);

                        anchorPaneEdit.getChildren().add(anchorPaneEdit.getChildren().size(), imgView);
                    } else {
                        BattleShipMain.window.getScene().setCursor(Cursor.DEFAULT);
                        headerTextEdit.setStyle(" -fx-font-smoothing-type: lcd;" + "-fx-font-weight: bold;" + "-fx-fill: yellow;" + "-fx-font-size: 28pt;");
                        headerTextEdit.setText("Can't move your ship");
                    }

                }
            }
        };

        eventHandlerC = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getX() >= 10 && e.getX() <= 560 && e.getY() >= 100 && e.getY() <= 650) {
                    int mouseX = (int) ((e.getX() - 10) / 50);
                    int mouseY = (int) ((e.getY() - 100) / 50);

                    if (check(ship.getLength(), mouseX - 1, mouseY - 1, ship.getDirection(), playerMap)) {
                        headerTextEdit.setStyle(" -fx-font-smoothing-type: lcd;" + "-fx-font-weight: bold;" + "-fx-fill: white;" + "-fx-font-size: 28pt;");
                        headerTextEdit.setText("Put the ships into your map");
                        anchorPaneEdit.getChildren().remove(imgView);
                        imgView.setOpacity(1);
                        anchorPaneEdit.getChildren().add(anchorPaneEdit.getChildren().size(), imgView);
                        placeAllShipsOfPlayer();
                        --totalShip;
                        canPutShip = true;
                        removeEventHandler();
                        }
                    }
            }

        };

        keyEventEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                Rotate rotate = new Rotate();
                rotate.setPivotX(25);
                rotate.setPivotY(25);
                if (keyEvent.getCode() == KeyCode.R) {
                    if (canRotate(ship.getRow(), ship.getCol(), ship.getLength(), ship.getDirection())) {
                        rotate.setAngle(90);
                        imgView.getTransforms().add(rotate);
                        ship.setDirection(ship.getDirection() + 1);
                        if (ship.getDirection() >= 4) {
                            ship.setDirection(0);
                        }
                        rotation[ship.getId()] = ship.getDirection() * 90;
                    } else {
                        headerTextEdit.setStyle(" -fx-font-smoothing-type: lcd;" + "-fx-font-weight: bold;" + "-fx-fill: red;" + "-fx-font-size: 28pt;");
                        headerTextEdit.setText("Can't rotate");
                    }
                }
            }
        };

        anchorPaneEdit.addEventHandler(MouseEvent.MOUSE_MOVED, eventHandlerM);
        anchorPaneEdit.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandlerC);
        anchorPaneEdit.addEventHandler(KeyEvent.KEY_PRESSED, keyEventEventHandler);

    }

    public boolean canRotate(int row, int col, int length, int dir) {
        if (dir == RIGHT) {
            return row + length <= NUM_ROWS;
        } else if (dir == DOWN) {
            return col - length >= -1;
        } else if (dir == LEFT) {
            return row - length >= -1;
        } else {
            return col + length <= NUM_COLS;
        }
    }

//    put player's ship
    private void placeAllShipsOfPlayer() {
        for(int index = 0; index < playerShips.getShips().size(); index++) {
            int numberRow = playerShips.getShips().get(index).getRow();
            int numberCol = playerShips.getShips().get(index).getCol();
            int direction = playerShips.getShips().get(index).getDirection();
            int i = playerShips.getShips().get(index).getLength();
            int id = playerShips.getShips().get(index).getId();

            if (direction == RIGHT) {
                for (int j = numberCol; j < numberCol + i; j++) {
                    playerMap[numberRow][j] = 1;
                    playerIdMap[numberRow][j] = id;
                }
            } else if (direction == DOWN) {
                for (int j = numberRow; j < numberRow + i; j++) {
                    playerMap[j][numberCol] = 1;
                    playerIdMap[j][numberCol] = id;
                }
            } else if (direction == LEFT) {
                for (int j = numberCol; j > numberCol - i; j--) {
                    playerMap[numberRow][j] = 1;
                    playerIdMap[numberRow][j] = id;
                }
            } else {
                for (int j = numberRow; j > numberRow - i; j--) {
                    playerMap[j][numberCol] = 1;
                    playerIdMap[j][numberCol] = id;
                }
            }
        }
//        System.out.println("vi tri tau cua nguoi choi");
//        printGrid(playerMap);
    }

    private void placeAllShipsOfAI() {
        for (int i = 0; i < aiMap.length; ++i) {
            for (int j = 0; j < aiMap[0].length; ++j) {
                aiMap[i][j] = 0;
            }
        }

        aiShips.getShips().clear();

        Random rnd = new Random();
        int randomNumberRow;
        int randomNumberCol;
        int randomDirection;
        int x = 0;
        for (int i : Constants.SHIP_LENGTHS) {
            do {
                randomNumberRow = rnd.nextInt(NUM_ROWS);
                randomNumberCol = rnd.nextInt(NUM_COLS);
                randomDirection = rnd.nextInt(4);
            } while (!check(i, randomNumberCol, randomNumberRow, randomDirection, aiMap));

            Ship aiShip = new Ship(x, i, randomDirection);
            aiShip.setRow(randomNumberRow);
            aiShip.setCol(randomNumberCol);
            aiShips.getShips().add(aiShip);

            if (randomDirection == RIGHT) {
                for (int j = randomNumberCol; j < randomNumberCol + i; j++) {
                    aiMap[randomNumberRow][j] = 1;
                    aiIdMap[randomNumberRow][j] = x;
                }
            } else if (randomDirection == DOWN) {
                for (int j = randomNumberRow; j < randomNumberRow + i; j++) {
                    aiMap[j][randomNumberCol] = 1;
                    aiIdMap[j][randomNumberCol] = x;
                }
            } else if (randomDirection == LEFT) {
                for (int j = randomNumberCol; j > randomNumberCol - i; j--) {
                    aiMap[randomNumberRow][j] = 1;
                    aiIdMap[randomNumberRow][j] = x;
                }
            } else {
                for (int j = randomNumberRow; j > randomNumberRow - i; j--) {
                    aiMap[j][randomNumberCol] = 1;
                    aiIdMap[j][randomNumberCol] = x;
                }
            }
            ++x;
            aiShip.setShipPart();
        }
        System.out.println("vi tri tau cua AI");
        printGrid(aiMap);
    }

//    print matrix
    public void printGrid(int[][] lvl) {
        for (int[] ints : lvl) {
            for (int j = 0; j < lvl[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }

        System.out.println("\n\n");
    }

//    check ship's position
    public boolean check (int i, int randomNumberCol, int randomNumberRow, int randomDirection, int[][] lvl ) {
        if (randomDirection == RIGHT) {
            for (int j = randomNumberCol; j < randomNumberCol + i; j++) {
                if (!inBounds(randomNumberRow, j) || lvl[randomNumberRow][j] != 0) {
                    return false;
                }
            }
        } else if (randomDirection == DOWN){
            for (int j = randomNumberRow; j < randomNumberRow + i; j++) {
                if (!inBounds(j, randomNumberCol) || lvl[j][randomNumberCol] != 0) {
                    return false;
                }
            }
        } else if (randomDirection == LEFT) {
            for (int j = randomNumberCol; j > randomNumberCol - i; j--) {
                if (!inBounds(randomNumberRow, j) || lvl[randomNumberRow][j] != 0) {
                    return false;
                }
            }
        } else {
            for (int j = randomNumberRow; j > randomNumberRow - i; j--) {
                if (!inBounds(j, randomNumberCol) || lvl[j][randomNumberCol] != 0) {
                    return false;
                }
            }
        }
        return true;

    }

    private boolean inBounds(int row, int col)
    {
        return ((row >= 0) && (row < Constants.NUM_ROWS) && (col >= 0) && (col < Constants.NUM_COLS));
    }

//    check clear button
    public void clearMap() {
        for (int i = 0; i < playerMap.length; i++) {
            for (int j = 0; j < playerMap[0].length; j++) {
                playerMap[i][j] = 0;
                aiMap[i][j] = 0;
                playerIdMap[i][j] = -1;
                aiIdMap[i][j] = -1;
            }
        }

        canPutShip5 = true;
        canPutShip4 = true;
        canPutShip3 = true;
        canPutShip3_2 = true;
        canPutShip2 = true;

        playerShips.getShips().clear();

        for (int i = 0; i < 5 - totalShip; i++) {
            anchorPaneEdit.getChildren().remove(anchorPaneEdit.getChildren().size() - 1);
        }

        totalShip = 5;
        renderShip(imgShip5, 0);
        renderShip(imgShip4, 1);
        renderShip(imgShip3, 2);
        renderShip(imgShip3_2, 3);
        renderShip(imgShip2, 4);
    }

    //	random player map
    public void randomMap () {
        clearMap();

        Random rnd = new Random();
        int randomNumberRow;
        int randomNumberCol;
        int randomDirection;
        int x = 0;
        for (int i : Constants.SHIP_LENGTHS) {
            do {
                randomNumberRow = rnd.nextInt(NUM_ROWS);
                randomNumberCol = rnd.nextInt(NUM_COLS);
                randomDirection = rnd.nextInt(4);
            } while (!check(i, randomNumberCol, randomNumberRow, randomDirection, playerMap));

            Ship playerShip = new Ship(x, i, randomDirection);
            playerShip.setRow(randomNumberRow);
            playerShip.setCol(randomNumberCol);
            aiShips.getShips().add(playerShip);

            if (randomDirection == RIGHT) {
                for (int j = randomNumberCol; j < randomNumberCol + i; j++) {
                    playerMap[randomNumberRow][j] = 1;
                    playerIdMap[randomNumberRow][j] = x;
                }
            } else if (randomDirection == DOWN) {
                for (int j = randomNumberRow; j < randomNumberRow + i; j++) {
                    playerMap[j][randomNumberCol] = 1;
                    playerIdMap[j][randomNumberCol] = x;
                }
            } else if (randomDirection == LEFT) {
                for (int j = randomNumberCol; j > randomNumberCol - i; j--) {
                    playerMap[randomNumberRow][j] = 1;
                    playerIdMap[randomNumberRow][j] = x;
                }
            } else {
                for (int j = randomNumberRow; j > randomNumberRow - i; j--) {
                    playerMap[j][randomNumberCol] = 1;
                    playerIdMap[j][randomNumberCol] = x;
                }
            }
            ++x;
            playerShip.setShipPart();
            playerShips.getShips().add(playerShip);
        }


        //anchorPaneEdit.getChildren().remove(vBoxShip);
        vBoxShip.getChildren().remove(imgShip5);
        vBoxShip.getChildren().remove(imgShip4);
        vBoxShip.getChildren().remove(imgShip3_2);
        vBoxShip.getChildren().remove(imgShip3);
        vBoxShip.getChildren().remove(imgShip2);

        for (Ship ship : playerShips.getShips()) {
            renderShip(60, ship);
        }

        totalShip = 0;

//        System.out.println("vi tri tau cua player");
//        printGrid(playerMap);
//        System.out.println("id player");
//        printGrid(playerIdMap);
    }

    //  draw ship
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
        imageView.setFitWidth(ship.getLength() * 50);
        imageView.setFitHeight(50);
        imageView.setLayoutX(x + ship.getCol() * 50);
        imageView.setLayoutY(150 + ship.getRow() * 50);

        Rotate rotate = new Rotate();
        rotate.setPivotX(25);
        rotate.setPivotY(25);
        rotate.setAngle(ship.getDirection() * 90);

        imageView.getTransforms().add(rotate);
        anchorPaneEdit.getChildren().add(imageView);
    }

//    draw ship
    public void renderShip(ImageView imgView, int id) {
        if (!vBoxShip.getChildren().contains(imgView)) {
            Rotate rotate = new Rotate();
            rotate.setPivotX(25);
            rotate.setPivotY(25);
            rotate.setAngle(-rotation[id]);

            imgView.getTransforms().add(rotate);
            vBoxShip.getChildren().add(imgView);
        }
    }

}
