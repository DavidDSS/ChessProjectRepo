package Board;

import Pieces.*;

public class BoardState {

    public Piece[][] theBoard = new Piece[8][8];

    //Starting Position
    public BoardState(){
        for(int p=0; p<8; p++){
            theBoard[1][p]= new Pawn(false, new char[]{(char)(97+p),8}, 'P');
            theBoard[6][p]= new Pawn(true, new char[]{(char)(97+p),1}, 'P');
        }

        theBoard[0][0]= new Rook(false, new char[]{'a',8}, 'R');
        theBoard[0][7]= new Rook(false, new char[]{'h',8}, 'R');
        theBoard[7][0]= new Rook(true, new char[]{'a',1}, 'R');
        theBoard[7][7]= new Rook(true, new char[]{'h',1}, 'R');

        theBoard[0][1]= new Knight(false, new char[]{'b',8}, 'N');
        theBoard[0][6]= new Knight(false, new char[]{'g',8}, 'N');
        theBoard[7][1]= new Knight(true, new char[]{'b',1}, 'N');
        theBoard[7][6]= new Knight(true, new char[]{'g',1}, 'N');

        theBoard[0][2]= new Bishop(false, new char[]{'c',8}, 'B');
        theBoard[0][5]= new Bishop(false, new char[]{'f',8}, 'B');
        theBoard[7][2]= new Bishop(true, new char[]{'c',1}, 'B');
        theBoard[7][5]= new Bishop(true, new char[]{'f',1}, 'B');

        theBoard[0][3]= new Queen(false, new char[]{'d',8}, 'Q');
        theBoard[7][3]= new Queen(true, new char[]{'d',1}, 'Q');

        theBoard[0][4]= new King(false, new char[]{'e',8}, 'K');
        theBoard[7][4]= new King(true, new char[]{'e',1}, 'K');
    }

    public void printBoard(){
        for(int r=0; r<8; r++){
            for(int c=0; c<8; c++){
                if(theBoard[r][c]!=null)System.out.print(theBoard[r][c].pieceLetter);
                else System.out.print(" ");
            }
            System.out.println();
        }
    }

}
