package Pieces;

import Board.BoardState;

public abstract class Piece {

    //0 is black and 1 is white
    public boolean white=true;
    public boolean captured=false;
    public char[] position= new char[2];
    public char pieceLetter;

    public Piece(boolean color, char[] pos, char letter){
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

    public int[] convertNotation(char[] spot){

        int[] result= new int[2];

        if(spot[0]=='a') result[1]=0;
        else if(spot[0]=='b') result[1]=1;
        else if(spot[0]=='c') result[1]=2;
        else if(spot[0]=='d') result[1]=3;
        else if(spot[0]=='e') result[1]=4;
        else if(spot[0]=='f') result[1]=5;
        else if(spot[0]=='g') result[1]=6;
        else if(spot[0]=='h') result[1]=7;
        else result[1] = -9999;

        if(spot[1]==1) result[0]=7;
        else if(spot[1]==2) result[0]=6;
        else if(spot[1]==3) result[0]=5;
        else if(spot[1]==4) result[0]=4;
        else if(spot[1]==5) result[0]=3;
        else if(spot[1]==6) result[0]=2;
        else if(spot[1]==7) result[0]=1;
        else if(spot[1]==8) result[0]=0;
        else result[0] = -9999;

        return result;
    }

    public abstract boolean checkMove(BoardState board, char[] startPos, char[] endPos);


}
