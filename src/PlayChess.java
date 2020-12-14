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

        BoardState board=null;

        Scanner gameMode= new Scanner(System.in);
        System.out.println("Press 0 to use Classic Board \nPress 1 to use Custom Board (Testing)");
        int choice= gameMode.nextInt();

        //User chose to use classic board
        if(choice==0) {
            board= new BoardState(null);
            board.setClassicalPosition();
            board.printBoard();
        }
        //User chose to use custom board
        else{
            board= new BoardState(null);

            Scanner pieceLocation= new Scanner(System.in);
            System.out.print("Select the board position the WHITE king should be placed: ");
            String piecePosW= pieceLocation.nextLine();
            char[] inputW = new char[2];
            inputW[0]=piecePosW.charAt(0);
            inputW[1]=piecePosW.charAt(1);
            board.setPiece(true, 'k',convertNotation(inputW)[0],convertNotation(inputW)[1]);

            System.out.print("Select the board position the BLACK king should be placed: ");
            String piecePosB= pieceLocation.nextLine();
            char[] inputB = new char[2];
            inputB[0]=piecePosB.charAt(0);
            inputB[1]=piecePosB.charAt(1);
            board.setPiece(false, 'K',convertNotation(inputB)[0],convertNotation(inputB)[1]);

            int pieceChoice;

            do{
                System.out.println("Select the piece you want to add next");
                System.out.println("Press 0 for Pawn");
                System.out.println("Press 1 for Rook");
                System.out.println("Press 2 for Knight");
                System.out.println("Press 3 for Bishop");
                System.out.println("Press 4 for Queen");
                System.out.println("Press 5 to Stop Adding Pieces and PLAY");

                Scanner newPiece= new Scanner(System.in);
                pieceChoice=newPiece.nextInt();

                if(pieceChoice!=5) {
                    System.out.println("What Color is the Piece \nPress 0 for White \nPress 1 for Black");

                    int pieceColor = newPiece.nextInt();
                    boolean colorChoice = pieceColor == 0 ? true : false;

                    char pLetter = 'p';
                    switch (pieceChoice) {
                        case 0:
                            pLetter = colorChoice ? 'p' : 'P';
                            break;
                        case 1:
                            pLetter = colorChoice ? 'r' : 'R';
                            break;
                        case 2:
                            pLetter = colorChoice ? 'n' : 'n';
                            break;
                        case 3:
                            pLetter = colorChoice ? 'b' : 'B';
                            break;
                        case 4:
                            pLetter = colorChoice ? 'q' : 'Q';
                            break;
                    }


                    System.out.print("Select the board position of your piece: ");
                    String piecePos = pieceLocation.nextLine();

                    char[] inputP = new char[2];
                    inputP[0] = piecePos.charAt(0);
                    inputP[1] = piecePos.charAt(1);
                    board.setPiece(colorChoice, pLetter, convertNotation(inputP)[0], convertNotation(inputP)[1]);
                }

            }
            while(pieceChoice!=5);

            board.printBoard();

        }

        while(!board.gameOver){
            // take user input for move
            Scanner playerMove= new Scanner(System.in);
            System.out.print("Position of piece you want to move: ");
            String move= playerMove.nextLine();
            char[] input = new char[2];
            input[0]=move.charAt(0);
            input[1]=move.charAt(1);

            System.out.print("End Position of the piece: ");
            String moveEnd= playerMove.nextLine();
            System.out.println();
            char[] inputEnd = new char[2];
            inputEnd[0]=moveEnd.charAt(0);
            inputEnd[1]=moveEnd.charAt(1);

            // convert the algebraic notation into array notation for our silicon friend
            int[] theMove = convertNotation(input);
            int[] endMove = convertNotation(inputEnd);

            // check for valid move input by the user
            if (theMove[0] == -9999 || theMove[1] == -9999 || endMove[0] == -9999 || endMove[1] == -9999) {
                continue;
            }
            else {
                // make the move
                board.userMove(theMove,endMove);
                // print the board
                board.printBoard();
            }

            // let the engine make a move
            board.engineMove();
            // print the board
            board.printBoard();
        }

        System.out.println("Game over!");
    }
}
