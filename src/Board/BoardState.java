package Board;

import Pieces.*;

import java.util.ArrayList;

public class BoardState {

    public Piece[][] theBoard = new Piece[8][8];
    public boolean whiteToMove=true;
    public boolean kingInCheck=false;
    public Piece pieceAttackingKing;
    public boolean enPassant=false;
    public int enPassantCol;
    public boolean gameState=true;
    ArrayList<Piece> capturedPiecesWhite = new ArrayList<>();
    ArrayList<Piece> capturedPiecesBlack = new ArrayList<>();

    //Starting Position
    public BoardState(){
        for(int p=0; p<8; p++){
            theBoard[1][p]= new Pawn(false, 1, p);
            theBoard[6][p]= new Pawn(true, 6, p);
        }

        theBoard[0][0]= new Rook(false, 0,0);
        theBoard[0][7]= new Rook(false, 0,7);
        theBoard[7][0]= new Rook(true, 7,0);
        theBoard[7][7]= new Rook(true, 7,7);

        theBoard[0][1]= new Knight(false, 0,1);
        theBoard[0][6]= new Knight(false, 0,6);
        theBoard[7][1]= new Knight(true, 7,1);
        theBoard[7][6]= new Knight(true, 7,6);

        theBoard[0][2]= new Bishop(false, 0,2);
        theBoard[0][5]= new Bishop(false, 0,5);
        theBoard[7][2]= new Bishop(true, 7,2);
        theBoard[7][5]= new Bishop(true, 7,5);

        theBoard[0][3]= new Queen(false, 0,3);
        theBoard[7][3]= new Queen(true, 7,3);

        theBoard[0][4]= new King(false, 0,4);
        theBoard[7][4]= new King(true, 7,4);
    }

    public void makeMove(int[] startPos, int[] endPos){

        // check if empty space selected for start pos
        if (theBoard[startPos[0]][startPos[1]]== null) {
            return;
        }
        //If white to move and black piece chosen return
        if(this.whiteToMove && !this.theBoard[startPos[0]][startPos[1]].white) {
            return;
        }

        //If black to move and white piece chosen return
        if(!this.whiteToMove && this.theBoard[startPos[0]][startPos[1]].white) {
            return;
        }

        ArrayList<Piece> moves= this.theBoard[startPos[0]][startPos[1]].getMoves(this);
        ArrayList<Piece> allMoves=new ArrayList<>();
        //Generate all possible moves for the A.I
        if(startPos==null) {
            for(int r=0; r<8; r++){
                for(int c=0; c<8;c++){
                    if(theBoard[r][c]!=null) {
                        theBoard[r][c].getMoves(this).forEach(move->allMoves.add(move));
                    }
                }
            }
            //Stalemate
            if(allMoves.size()==0){
                gameState=false;
                return;
            }
        }



        Piece thePiece=null;
        // check to see if the move made is possible
        for(Piece p : moves){
            if(p.row==endPos[0] && p.col==endPos[1]){
                if(theBoard[endPos[0]][endPos[1]]!=null && theBoard[endPos[0]][endPos[1]].type==PieceType.KING){
                    kingInCheck=true;
                    pieceAttackingKing=theBoard[endPos[0]][endPos[1]];
                }
                thePiece=p;
                //ADD PROMOTION SELECT FOR USER!!!
            }
        }

        if(thePiece!=null){

            /*if(kingInCheck){

                // idea
                // on previous turn, generate 8x8 array of all squares being attacked by opponent
                // importantly, we need all squares on the attacking line

                // on loop, check if king can move to square not being attacked
                // check if piece can block along attacking line or capture attacking piece

                for(Piece p : allMoves){
                    // possible ways to get out of check
                    // 1. move the king
                    // 2. block the line of attack with a piece
                    // 3. capture the attacking piece
                    if(p.type==PieceType.KING){

                    }



                }
            }*/
            // check for castling move
            if (thePiece.type==PieceType.KING) {
                // king side castles
                if ((endPos[1]-startPos[1])==2) {
                    // we account for the king moving later
                    if (whiteToMove) {
                        // only move the rook
                        theBoard[7][5] = new Rook(true, 7, 5);
                        theBoard[7][5].hasMoved = true;
                        theBoard[7][7] = null;
                    }
                    else {
                        // only move the rook
                        theBoard[0][5] = new Rook(false, 0, 5);
                        theBoard[0][5].hasMoved = true;
                        theBoard[0][7] = null;
                    }
                }

                // queen side castles
                if ((endPos[1]-startPos[1])==-2) {
                    // we account for the king moving later
                    if (whiteToMove) {
                        // only move the rook
                        theBoard[7][3] = new Rook(true, 7, 3);
                        theBoard[7][3].hasMoved = true;
                        theBoard[7][0] = null;
                    }
                    else {
                        // only move the rook
                        theBoard[0][3] = new Rook(false, 0, 3);
                        theBoard[0][3].hasMoved = true;
                        theBoard[0][0] = null;
                    }
                }
            } // castling check

            // check for enpassant attack AND check for promotion
            if(thePiece.type==PieceType.PAWN){
                // check if move is left or right (enpassant)
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
            } // en passant check

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
            if(thePiece.type==PieceType.PAWN){
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
