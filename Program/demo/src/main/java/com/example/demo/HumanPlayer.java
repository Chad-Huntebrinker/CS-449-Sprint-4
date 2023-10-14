package com.example.demo;

public class HumanPlayer extends PlayerParent {

    HumanPlayer(){}

    //The human player checks to see if the user has selected to place an S or an O in the box.
    //Once it has checked, it then calls the draw method for S or O.
    public void checkToDrawSOrO(PlayerBox player, Box box) {
        if(player.getS()) {
            drawS(box);
        }
        else {
            drawO(box);
        }
    }

}