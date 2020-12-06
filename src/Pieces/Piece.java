package Pieces;

import Board.BoardState;

import java.util.ArrayList;

public abstract class Piece {

    //0 is black and 1 is white
    public boolean white=true;
    public boolean captured=false;
    //public int[] position= new int[2];
    public int row;
    public int col;
    public char pieceLetter;
    public boolean hasMoved=false;
    ArrayList<Piece> moves;

    public Piece(boolean color, int r, int c, char letter){
        this.setColor(color);
        this.row= r;
        this.col= c;
        this.pieceLetter=letter;
    }

    public boolean isCaptured(){
        return this.captured;
    }

    public boolean isWhite(){
        return white;
    }

    public void setColor(boolean color){
        this.white=color;
    }

    public void setCaptured(boolean captured){
        this.captured=captured;
    }

    public boolean inBounds(int r, int c){
        if(r<0 || c<0 || r>7 || c>7){
            return false;
        }
        return true;
    }

    public abstract ArrayList<Piece> getMoves(BoardState board);


}
