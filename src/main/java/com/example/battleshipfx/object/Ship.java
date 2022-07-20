package com.example.battleshipfx.object;

import static com.example.battleshipfx.helpz.Constants.*;

public class Ship {

    private int id;
    private int length;
    private int row;
    private int col;
    private int direction;
    private boolean isDestroy;
    private int[][] shipPart;
    private int[][] borderShip;
    private boolean isRender;

    public Ship(int id, int length, int direction) {
        this.id = id;
        this.length = length;
        this.direction = direction;
        shipPart = new int[length][2];
        borderShip = new int[length + 2][4];
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDestroy() {
        for (int i = 0 ; i < length; ++i) {
            if (shipPart[i][1] != 1) {
                return false;
            }
        }
        return true;
    }


    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setShipPart() {
        int x = 0;
        for (int i = 0; i < length; ++i) {
            switch (direction) {
                case RIGHT -> {
                    shipPart[i][0] = getCol() + i;
                    x = getRow();
                }
                case DOWN -> {
                    shipPart[i][0] = getRow() + i;
                    x = getCol();
                }
                case LEFT -> {
                    shipPart[i][0] = getCol() - i;
                    x = getRow();
                }
                case UP -> {
                    shipPart[i][0] = getRow() - i;
                    x = getCol();
                }
            }
        }
//        for (int[] ints : shipPart) {
//            System.out.print(ints[0] + " ");
//        }
//        System.out.println("\n");
        setBorderShip(x);
    }

    public void setBorderShip(int x) {
        if (getDirection() == RIGHT || getDirection() == DOWN) {
            borderShip[0][0] = shipPart[0][0] - 1;
            borderShip[borderShip.length - 1][0] = shipPart[shipPart.length - 1][0] + 1;
        } else {
            borderShip[0][0] = shipPart[0][0] + 1;
            borderShip[borderShip.length - 1][0] = shipPart[shipPart.length - 1][0] - 1;
        }

        for (int j = 1; j < borderShip.length - 1; ++j) {
            borderShip[j][0] = shipPart[j - 1][0];
        }
        for (int k = 0; k < borderShip.length; ++k) {
            borderShip[k][1] = x - 1;
            borderShip[k][2] = x;
            borderShip[k][3] = x + 1;
        }
//        for (int u = 0; u < borderShip.length; ++u) {
//            for (int v = 0; v < borderShip[0].length; ++v) {
//                System.out.print(borderShip[u][v] + " ");
//            }
//            System.out.println("");
//        }
//        System.out.println("");
    }

    public void setAttackShip(int row, int col) {
        if (getDirection() == RIGHT || getDirection() == LEFT) {
            for (int i = 0; i < length; ++i) {
                if (shipPart[i][0] == col) {
                    shipPart[i][1] = 1;
                }
            }
        } else {
            for (int i = 0; i < length; ++i) {
                if (shipPart[i][0] == row) {
                    shipPart[i][1] = 1;
                }
            }
        }
    }

    public int[][] getBorderShip() {
        return borderShip;
    }


    public boolean isRender() {
        return isRender;
    }

    public void setRender(boolean render) {
        isRender = render;
    }


}
