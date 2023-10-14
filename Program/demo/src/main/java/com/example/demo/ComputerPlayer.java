package com.example.demo;

import java.util.Random;

public class ComputerPlayer extends PlayerParent {

    ComputerPlayer(){}

    //The computer randomly picks whther to put an S or an O in the box.  If it is 0, then it is S.
    //If it is 1, then it is O.
    public void checkToDrawSOrO(PlayerBox player, Box box) {
        Random rand = new Random();
        int temp = rand.nextInt(2);

        if(temp == 0) {
            drawS(box);
        }
        else {
            drawO(box);
        }
    }
}