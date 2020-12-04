package Board;

import Pieces.*;

public class BoardState {

    public Piece[][] theBoard = new Piece[8][8];
    public boolean whiteToMove=true;
    public boolean enPassant=false;

    //Starting Position
    public BoardState(){
        for(int p=0; p<8; p++){
            theBoard[1][p]= new Pawn(false, new int[]{1,p}, 'P');
            theBoard[6][p]= new Pawn(true, new int[]{6,p}, 'p');
        }

        theBoard[0][0]= new Rook(false, new int[]{0,0}, 'R');
        theBoard[0][7]= new Rook(false, new int[]{0,7}, 'R');
        theBoard[7][0]= new Rook(true, new int[]{7,0}, 'r');
        theBoard[7][7]= new Rook(true, new int[]{7,7}, 'r');

        theBoard[0][1]= new Knight(false, new int[]{0,1}, 'N');
        theBoard[0][6]= new Knight(false, new int[]{0,6}, 'N');
        theBoard[7][1]= new Knight(true, new int[]{7,1}, 'n');
        theBoard[7][6]= new Knight(true, new int[]{7,6}, 'n');

        theBoard[0][2]= new Bishop(false, new int[]{0,2}, 'B');
        theBoard[0][5]= new Bishop(false, new int[]{0,5}, 'B');
        theBoard[7][2]= new Bishop(true, new int[]{7,2}, 'b');
        theBoard[7][5]= new Bishop(true, new int[]{7,5}, 'b');

        theBoard[0][3]= new Queen(false, new int[]{0,3}, 'Q');
        theBoard[7][3]= new Queen(true, new int[]{7,3}, 'q');

        theBoard[0][4]= new King(false, new int[]{0,4}, 'K');
        theBoard[7][4]= new King(true, new int[]{0,4}, 'k');
    }

    public void makeMove(int[] startPos, int[] endPos){
        if(theBoard[startPos[0]][startPos[1]].checkMove(this,startPos,endPos)){
            theBoard[startPos[0]][startPos[1]].position[0]=endPos[0];
            theBoard[startPos[0]][startPos[1]].position[1]=endPos[1];

            theBoard[endPos[0]][endPos[1]]=theBoard[startPos[0]][startPos[1]];
            theBoard[startPos[0]][startPos[1]]=null;

            theBoard[endPos[0]][endPos[1]].hasMoved=true;
        }

    }

    public void printBoard(){
        for(int r=0; r<8; r++){
            for(int c=0; c<8; c++){
                if(theBoard[r][c]!=null)System.out.print(theBoard[r][c].pieceLetter);
                else System.out.print("-");
            }
            System.out.println();
        }
        System.out.println();
    }

}
