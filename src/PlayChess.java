import Board.BoardState;
import Pieces.Pawn;
import Pieces.Piece;

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

        /*board.makeMove(new int[]{6,1},new int[]{4,1});
        board.makeMove(new int[]{4,1},new int[]{3,1});
        board.whiteToMove=false;
        board.makeMove(new int[]{1,2},new int[]{3,2});
        board.printBoard();
        board.whiteToMove=true;
        board.makeMove(new int[]{3,1},new int[]{2,2});
        board.printBoard();*/




        // 97 -> a
        // 98 -> b
        // 99 -> c
        // 100 -> d
        // 101 -> e
        // 102 -> f
        // 103 -> g
        // 104 -> h

        // a 2
        /*Pawn p=new Pawn(true, new char[]{(char)(97),6}, 'P');
        System.out.println("PAWN");
        System.out.print(p.position[0]);
        System.out.println((int)p.position[1]);
        System.out.print(p.convertNotation(p.position)[0]);
        System.out.println(p.convertNotation(p.position)[1]);
        System.out.println(p.checkMove(board, p.position, new char[]{(char)(98),7}));*/
    }
}
