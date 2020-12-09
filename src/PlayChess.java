import Board.BoardState;
import Pieces.Pawn;
import Pieces.Piece;

import java.util.ArrayList;
import java.util.Scanner;

public class PlayChess {

    public static int[] convertNotation(char[] spot){

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

        if(spot[1]=='1') result[0]=7;
        else if(spot[1]=='2') result[0]=6;
        else if(spot[1]=='3') result[0]=5;
        else if(spot[1]=='4') result[0]=4;
        else if(spot[1]=='5') result[0]=3;
        else if(spot[1]=='6') result[0]=2;
        else if(spot[1]=='7') result[0]=1;
        else if(spot[1]=='8') result[0]=0;
        else result[0] = -9999;

        return result;
    }

    public static void main(String[] args){
        BoardState board= new BoardState();
        board.printBoard();
        /*
        while(!board.gameOver){
            Scanner playerMove= new Scanner(System.in); //System.in is a standard input stream.
            System.out.print("Piece to Move: ");
            String move= playerMove.nextLine();
            char[] input = new char[2];
            input[0]=move.charAt(0);
            input[1]=move.charAt(1);
            System.out.print("End Position: ");
            String moveEnd= playerMove.nextLine();
            char[] inputEnd = new char[2];
            inputEnd[0]=moveEnd.charAt(0);
            inputEnd[1]=moveEnd.charAt(1);
            int[] theMove = convertNotation(input);
            int[] endMove = convertNotation(inputEnd);
            if (theMove[0] == -9999 || theMove[1] == -9999 || endMove[0] == -9999 || endMove[1] == -9999) {
                continue;
            }
            else {
                board.userMove(theMove,endMove);
            }


        }

        */
        // stalemate check
        board.userMove(convertNotation(new char[]{'e','2'}),convertNotation(new char[]{'e','3'}));
        board.userMove(convertNotation(new char[]{'a','7'}),convertNotation(new char[]{'a','5'}));
        board.userMove(convertNotation(new char[]{'d','1'}),convertNotation(new char[]{'h','5'}));
        board.userMove(convertNotation(new char[]{'a','8'}),convertNotation(new char[]{'a','6'}));
        board.userMove(convertNotation(new char[]{'h','5'}),convertNotation(new char[]{'a','5'}));
        board.userMove(convertNotation(new char[]{'h','7'}),convertNotation(new char[]{'h','5'}));
        board.userMove(convertNotation(new char[]{'h','2'}),convertNotation(new char[]{'h','4'}));
        board.userMove(convertNotation(new char[]{'a','6'}),convertNotation(new char[]{'h','6'}));
        board.userMove(convertNotation(new char[]{'a','5'}),convertNotation(new char[]{'c','7'}));
        board.userMove(convertNotation(new char[]{'f','7'}),convertNotation(new char[]{'f','6'}));
        board.userMove(convertNotation(new char[]{'c','7'}),convertNotation(new char[]{'d','7'}));
        board.userMove(convertNotation(new char[]{'e','8'}),convertNotation(new char[]{'f','7'}));
        board.userMove(convertNotation(new char[]{'d','7'}),convertNotation(new char[]{'b','7'}));
        board.userMove(convertNotation(new char[]{'d','8'}),convertNotation(new char[]{'d','3'}));
        board.userMove(convertNotation(new char[]{'b','7'}),convertNotation(new char[]{'b','8'}));
        board.userMove(convertNotation(new char[]{'d','3'}),convertNotation(new char[]{'h','7'}));
        board.userMove(convertNotation(new char[]{'b','8'}),convertNotation(new char[]{'c','8'}));
        board.userMove(convertNotation(new char[]{'f','7'}),convertNotation(new char[]{'g','6'}));
        board.userMove(convertNotation(new char[]{'c','8'}),convertNotation(new char[]{'e','6'}));

        System.out.println(board.gameOver);
    }
}
