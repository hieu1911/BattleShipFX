package com.example.battleshipfx.objectManager;

import com.example.battleshipfx.object.Ship;

import java.util.ArrayList;

public class ShipManager {

    private ArrayList<Ship> ships = new ArrayList<>();
    private int type;

    public ShipManager(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    public boolean isAddDestroyed() {
        for (Ship ship : ships) {
            if (!ship.isDestroy()) {
                return false;
            }
        }
        return true;
    }

    public int getShipsRemaining() {
        int shipsDestroyed = 0;
        for (Ship ship : ships) {
            if (ship.isDestroy()) {
                ++shipsDestroyed;
            }
        }
        return ships.size() - shipsDestroyed;
    }

    public boolean checkShipDestroyById(int id) {
        for (Ship ship : ships) {
            if (ship.getId() == id) {
                return ship.isDestroy();
            }
        }
        return false;
    }

}
