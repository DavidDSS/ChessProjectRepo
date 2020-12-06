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

        board.makeMove(new int[]{6,3},new int[]{4,3});
        board.makeMove(new int[]{1,2},new int[]{3,2});
        board.makeMove(new int[]{4,3},new int[]{3,2});
        board.makeMove(new int[]{1,3},new int[]{3,3});
        board.makeMove(new int[]{3,2},new int[]{2,3});
        board.makeMove(new int[]{1,4},new int[]{2,3});

        /*board.makeMove(new int[]{1,2},new int[]{3,2});
        board.makeMove(new int[]{3,3},new int[]{2,2});
        board.makeMove(new int[]{1,3},new int[]{2,2});*/


        /*for(Piece p : pawnMoves){
            System.out.println("Move: "+p.position[0]+","+p.position[1]);

        }*/

    }
}
