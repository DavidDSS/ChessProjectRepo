package Board;

import Pieces.*;

import java.util.ArrayList;

public class BoardState {

    public Piece[][] theBoard = new Piece[8][8];
    public boolean whiteToMove=true;
    public boolean enPassant=false;
    public int enPassantCol;
    ArrayList<Piece> capturedPiecesWhite = new ArrayList<>();
    ArrayList<Piece> capturedPiecesBlack = new ArrayList<>();

    //Starting Position
    public BoardState(){
        for(int p=0; p<8; p++){
            theBoard[1][p]= new Pawn(false, 1, p, 'P');
            theBoard[6][p]= new Pawn(true, 6, p, 'p');
        }

        theBoard[0][0]= new Rook(false, 0,0, 'R');
        theBoard[0][7]= new Rook(false, 0,7, 'R');
        theBoard[7][0]= new Rook(true, 7,0, 'r');
        theBoard[7][7]= new Rook(true, 7,7, 'r');

        theBoard[0][1]= new Knight(false, 0,1, 'N');
        theBoard[0][6]= new Knight(false, 0,6, 'N');
        theBoard[7][1]= new Knight(true, 7,1, 'n');
        theBoard[7][6]= new Knight(true, 7,6, 'n');

        theBoard[0][2]= new Bishop(false, 0,2, 'B');
        theBoard[0][5]= new Bishop(false, 0,5, 'B');
        theBoard[7][2]= new Bishop(true, 7,2, 'b');
        theBoard[7][5]= new Bishop(true, 7,5, 'b');

        theBoard[0][3]= new Queen(false, 0,3, 'Q');
        theBoard[7][3]= new Queen(true, 7,3, 'q');

        theBoard[0][4]= new King(false, 0,4, 'K');
        theBoard[7][4]= new King(true, 0,4, 'k');
    }

    public void makeMove(int[] startPos, int[] endPos){
        ArrayList<Piece> moves= this.theBoard[startPos[0]][startPos[1]].getMoves(this);

        Piece thePiece=null;

        // check to see if the move made is possible
        for(Piece p : moves){
            if(p.row==endPos[0] && p.col==endPos[1]){
                thePiece=p;
            }
        }

        if(thePiece!=null){

            // check for enpassant attack
            if(thePiece.pieceLetter=='p' || thePiece.pieceLetter=='P'){
                // check if move is left or right
                if(Math.abs(endPos[1]-startPos[1])==1){
                    if(enPassantCol==endPos[1]){
                        if(this.whiteToMove){
                            capturedPiecesBlack.add(theBoard[endPos[0]+1][endPos[1]]);
                            theBoard[endPos[0]+1][endPos[1]]=null;
                        }
                        else{
                            capturedPiecesWhite.add(theBoard[endPos[0]-1][endPos[1]]);
                            theBoard[endPos[0]-1][endPos[1]]=null;
                        }
                    }
                }
            }
            // set en passant flag to false
            this.enPassant=false;

            // if we are capturing a piece, add the captured piece to respective list
            if (theBoard[endPos[0]][endPos[1]] != null) {
                if (this.whiteToMove) {
                    capturedPiecesBlack.add(theBoard[endPos[0]][endPos[1]]);
                }
                else {
                    capturedPiecesWhite.add(theBoard[endPos[0]][endPos[1]]);
                }
            }

            // place the piece in the end position on the board
            theBoard[endPos[0]][endPos[1]]=thePiece;

            // set the piece's previous position to null
            theBoard[startPos[0]][startPos[1]]=null;

            // set flag so we know the piece has moved
            theBoard[endPos[0]][endPos[1]].hasMoved=true;

            // check if a pawn has moved twice to set en passant flag
            if(thePiece.pieceLetter=='P' || thePiece.pieceLetter=='p'){
                if(Math.abs(startPos[0]-endPos[0])==2){
                    this.enPassant=true;
                    this.enPassantCol=endPos[1];
                }
            }

            // change turn
            this.whiteToMove = !this.whiteToMove;

            // print board
            this.printBoard();
        }

    }

    public void printBoard(){
        System.out.print("  ");
        for(int c=0; c<8;c++){
            System.out.print(c);
        }
        System.out.println();
        for(int r=0; r<8; r++){
            System.out.print(r+" ");
            for(int c=0; c<8; c++){
                if(theBoard[r][c]!=null)System.out.print(theBoard[r][c].pieceLetter);
                else System.out.print("-");
            }
            System.out.println();
        }
        System.out.println();
    }

}
