package com.example.demo;

//Parent class for the human and computer players.  Since both will need to draw an S and an O, we'll have
//ready to go in this class.  Also, we'll have a function called checkToDrawSOrO for our different players
//to see if they want to draw an S or an O; but, we won't implement it in the parent class.  We'll leave
//that up to the children.
public class PlayerParent {

    public void checkToDrawSOrO(){};

    //Functions to draw an S or an O in the box.
    public void drawS(Box box) {
        box.drawS();
    }
    public void drawO(Box box) {
        box.drawO();
    }
}