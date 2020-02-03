package Enum;

/**
 * Quest'enum viene utilizzata per indicare le direzioni
 * degli elementi che compiono movimenti nel gioco.
 */
public enum Direction {
    RIGHT(1,0),
    LEFT(-1,0),
    UP(0,-1),
    DOWN(0,1),
    STOP(0,0);

    private int xDirection;
    private int yDirection;


    /**
     * Costruttore dell'enum
     * @param x cordinata x
     * @param y cordinata y
     */
    Direction(int x, int y){
        this.xDirection = x;
        this.yDirection = y;
    }

    // getter
    public int getXDirection() {
        return xDirection;
    }
    public int getYDirection() {
        return yDirection;
    }


    /**
     * Data una direzione questa funzione ci restituische la
     * direzione opposta.
     * @param direction direzione attuale
     * @return direzione opposta
     */
    public static Direction reverseDirection(Direction direction){
        switch (direction){
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
            case UP: return DOWN;
            case DOWN: return UP;
            default: return STOP;
        }
    }
}
