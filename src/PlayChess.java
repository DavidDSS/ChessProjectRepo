import Board.BoardState;
import Pieces.Pawn;
import Pieces.Piece;

import java.util.ArrayList;

public class PlayChess {

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

    public static void main(String[] args){
        BoardState board= new BoardState();
        board.printBoard();

        // nf3 nf6
        board.makeMove(new int[]{7,1},new int[]{5,2});
        board.makeMove(new int[]{0,1},new int[]{2,2});

        board.makeMove(new int[]{6,1},new int[]{5,1});
        board.makeMove(new int[]{1,1},new int[]{2,1});

        board.makeMove(new int[]{7,2},new int[]{6,1});
        board.makeMove(new int[]{0,2},new int[]{1,1});

        board.makeMove(new int[]{6,3},new int[]{5,3});
        board.makeMove(new int[]{1,3},new int[]{2,3});

        board.makeMove(new int[]{7,3},new int[]{6,3});
        board.makeMove(new int[]{0,3},new int[]{1,3});

        board.makeMove(new int[]{7,4},new int[]{7,2});
        board.makeMove(new int[]{0,4},new int[]{0,2});

    }
}
