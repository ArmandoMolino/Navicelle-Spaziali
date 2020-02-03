package Obstacle;

import java.awt.*;

public interface Obstacle {

    Rectangle getHitBox();
    void move();

    static int[] getPolyXArray(int startXPos, int[] polyXArray) {
        int[] tempPolyXArray = polyXArray.clone();
        for(int i = 0; i < tempPolyXArray.length; i++) {
            tempPolyXArray[i] += startXPos;
        }
        return tempPolyXArray;
    }

    static int[] getPolyYArray(int startYPos, int[] polyYArray) {
        int[] tempPolyYArray = polyYArray.clone();
        for(int i = 0; i < tempPolyYArray.length; i++) {
            tempPolyYArray[i] += startYPos;
        }
        return tempPolyYArray;
    }
}
