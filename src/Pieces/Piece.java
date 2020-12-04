package Pieces;

import Board.BoardState;

public abstract class Piece {

    //0 is black and 1 is white
    public boolean white=true;
    public boolean captured=false;
    public int[] position= new int[2];
    public char pieceLetter;
    public boolean hasMoved=false;

    public Piece(boolean color, int[] pos, char letter){
        this.setColor(color);
        this.position[0]=pos[0];
        this.position[1]=pos[1];
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

    public abstract boolean checkMove(BoardState board, int[] startPos, int[] endPos);


}
