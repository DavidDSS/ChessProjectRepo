import Board.BoardState;
import Pieces.Pawn;
import Pieces.Piece;

import java.util.ArrayList;
import java.util.Scanner;

/**
 Jason Grightmire (5388327)
 David Saldana Suarez (6155964)
 COSC 3P71
 Chess Project
 */

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

        BoardState board;

        Scanner userInput = new Scanner(System.in);

        int choice;
        do {
            System.out.println("Press 0 to use Classic Board \nPress 1 to use Custom Board (Testing)");
            while (!userInput.hasNextInt()) {
                System.out.println("Not a Valid Input");
                System.out.println("Press 0 to use Classic Board \nPress 1 to use Custom Board (Testing)");
                userInput.next();
            }
            choice= userInput.nextInt();
        } while (choice != 0 && choice !=1);


        int depth;

        do {
            System.out.println("Please set a depth for the engine search (3-5 recommended): ");
            while (!userInput.hasNextInt()) {
                System.out.println("Not a Valid Input");
                System.out.println("Please set a depth for the engine search (3-5 recommended): ");
                userInput.next();
            }
            depth = userInput.nextInt();
        } while (depth<=0);

        //User chose to use classic board
        if(choice==0) {
            board= new BoardState(null);
            board.setClassicalPosition();
            board.printBoard();
        }
        //User chose to use custom board
        else{
            Scanner customBoardInput = new Scanner(System.in);
            char[] inputW;
            do {
                board = new BoardState(null);
                System.out.print("Select the board position the WHITE king should be placed: ");
                String piecePosW = customBoardInput.nextLine();
                inputW= new char[2];
                inputW[0] = piecePosW.charAt(0);
                inputW[1] = piecePosW.charAt(1);
            } while(convertNotation(inputW)[0]==-9999 || convertNotation(inputW)[1]==-9999);
            board.setPiece(true, 'k',convertNotation(inputW)[0],convertNotation(inputW)[1]);

            char[] inputB;
            do {
                System.out.print("Select the board position the BLACK king should be placed: ");
                String piecePosB= customBoardInput.nextLine();
                inputB = new char[2];
                inputB[0]=piecePosB.charAt(0);
                inputB[1]=piecePosB.charAt(1);
            } while(convertNotation(inputB)[0]==-9999 || convertNotation(inputB)[1]==-9999);
            board.setPiece(false, 'K',convertNotation(inputB)[0],convertNotation(inputB)[1]);

            int pieceChoice;

            Scanner newPiece= new Scanner(System.in);
            do{
                do {
                    System.out.println("Select the piece you want to add next:");
                    System.out.println("Press 0 for Pawn");
                    System.out.println("Press 1 for Rook");
                    System.out.println("Press 2 for Knight");
                    System.out.println("Press 3 for Bishop");
                    System.out.println("Press 4 for Queen");
                    System.out.println("Press 5 to Stop Adding Pieces and PLAY");
                    while (!newPiece.hasNextInt()) {
                        System.out.println("Not a Valid Input!\nEnter a number from 0 to 5");
                        newPiece.next();
                    }
                    pieceChoice= newPiece.nextInt();
                    if(pieceChoice>5) System.out.println("Not a Valid Input!\nEnter a number from 0 to 5");
                } while (pieceChoice>5 || pieceChoice<0);


                if(pieceChoice!=5) {
                    int pieceColor;
                    do {
                        System.out.println("What Color is the Piece \nPress 0 for White \nPress 1 for Black");
                        while (!newPiece.hasNextInt()) {
                            System.out.println("Not a Valid Input!\nEnter 0 for White or 1 for Black\n");
                            newPiece.next();
                        }
                        pieceColor= newPiece.nextInt();
                        if(pieceColor<0 || pieceColor>1) System.out.println("Not a Valid Input!\nEnter 0 for White or 1 for Black\n");
                    } while (pieceColor>1 || pieceColor<0);


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


                    char[] inputP;
                    do {
                        System.out.print("Select the board position the piece should be placed: ");
                        String piecePos = customBoardInput.nextLine();
                        inputP = new char[2];
                        inputP[0] = piecePos.charAt(0);
                        inputP[1] = piecePos.charAt(1);
                    } while(convertNotation(inputP)[0]==-9999 || convertNotation(inputP)[1]==-9999);
                    board.setPiece(colorChoice, pLetter, convertNotation(inputP)[0], convertNotation(inputP)[1]);

                }

            }
            while(pieceChoice!=5);

            board.printBoard();

        }

        while(!board.checkmate && !board.stalemate){
            // take user input for move
            Scanner playerMove= new Scanner(System.in);
            System.out.print("Algebraic coordinates of piece you want to move i.e d2: ");
            String move= playerMove.nextLine();
            char[] input = new char[2];
            input[0]=move.charAt(0);
            input[1]=move.charAt(1);

            System.out.print("Algebraic coordinates of where to move the piece i.e d4: ");
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
                System.out.println("That is not a legal move!");
                continue;
            }
            else {
                // make the move
                board.userMove(theMove[0], theMove[1], endMove[0], endMove[1]);
                // print the board
                board.printBoard();

                // AI move
                if (!board.whiteToMove) {
                    System.out.println("Calculating...");
                    // break out of loop if game over
                    if (board.checkmate || board.stalemate) break;
                    // let the engine make a move
                    board.engineMove(depth);
                    // print the board
                    board.printBoard();
                }
            }
        }

        System.out.println("Game over!");
        if (board.checkmate) System.out.println("Checkmate");
        if (board.stalemate) System.out.println("Stalemate");
    }
}
