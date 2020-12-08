package Board;

import Pieces.*;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class BoardState {

    public Piece[][] theBoard = new Piece[8][8];
    public boolean[][] attackedByPiece= new boolean[8][8];

    ArrayList<Piece> allMoves=new ArrayList<>();
    public boolean whiteToMove=true;
    public boolean kingInCheck=false;
    public Piece pieceAttackingKing;
    public boolean enPassant=false;
    public int enPassantCol;
    public boolean gameOver=false;

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

    public void userMove(int[] startPos, int[] endPos){
        getAllPossibleMoves();

        ArrayList<Piece> chosenPieceMoves= theBoard[startPos[0]][startPos[1]].getMoves(this);
        boolean legalMove=false;
        for(Piece p: chosenPieceMoves){
            if(p.row==endPos[0] && p.col==endPos[1]){
                legalMove=true;
            }
        }
        if(legalMove) {
            makeMove(startPos, endPos);
        }
        else{
            System.out.println("That is not a legal move!");
        }

    }

    public void getAllPossibleMoves() {
        // loop through each square on the board
        for(int r=0; r<8; r++){
            for(int c=0; c<8;c++){
                if(theBoard[r][c]!=null && (whiteToMove==theBoard[r][c].white)) {
                    ArrayList<Piece> pieceMoves=theBoard[r][c].getMoves(this);
                    for(Piece p:pieceMoves){
                        //All Moves for the current player's pieces (white or black)
                        allMoves.add(p);
                    }
                }
            }
        }
        // check if king is in check
        if(kingInCheck){
            allMoves = legalMovesKingInCheck();
            if (allMoves.size() == 0) {
                gameOver = true;
                String winner = whiteToMove ? "Black" : "White";
                System.out.println(winner + " is victorious.");
            }
        }
        // check if no more moves, stalemate
        else if (allMoves.size()==0) {
            gameOver = true;
            System.out.println("Stalemate: 1/2 - 1/2");
        }

    } // getAllPossibleMoves

    public ArrayList<Piece> legalMovesKingInCheck () {

        ArrayList<Piece> lineOfAttack= pieceAttackingKing.getMoves(this);
        ArrayList<Piece> legalMoves= new ArrayList<>();

        //Checks all the possible attacks for the enemy pieces
        for(int r=0; r<8; r++){
            for(int c=0; c<8;c++){
                if(theBoard[r][c]!=null && (whiteToMove!=theBoard[r][c].white)) {
                    ArrayList<Piece> enemyMoves=theBoard[r][c].getMoves(this);
                    for(Piece p:enemyMoves){
                        //All Moves for the current player's pieces (white or black)
                        //Pawns only attack diagonally
                        if(p.type==PieceType.PAWN){
                            if(Math.abs(p.col-c)==1){
                                attackedByPiece[p.row][p.col]=true;
                            }
                        }
                        //All other pieces attack on end position
                        else{
                            attackedByPiece[p.row][p.col]=true;
                        }
                    }
                }
            }
        }

        // all possible moves loop
        for(Piece p : allMoves){

            // possible ways to get out of check

            // 1. move the king
            if(p.type==PieceType.KING){
                if(!attackedByPiece[p.row][p.col]){
                    legalMoves.add(p);
                }
            }

            // 2. block the line of attack with a piece
            for(Piece l:lineOfAttack){
                if(p.row==l.row && p.col==l.col){
                    legalMoves.add(p);
                }
            }

            // 3. capture the attacking piece
            if(p.row==pieceAttackingKing.row && p.col==pieceAttackingKing.col){
                if(p.type==PieceType.PAWN){
                    Pawn pawn= (Pawn) p;
                    if(pawn.getAttacking()){
                        legalMoves.add(p);
                    }
                }
                else{
                    legalMoves.add(p);
                }
            }
        } // all possible moves

        //Checkmate
        if(legalMoves.size()==0){
            gameOver=true;
            return null;
        }

        return legalMoves;
    }

    public void makeMove(int[] startPos, int[] endPos){

        //If white to move and black piece chosen return
        if(this.whiteToMove && !this.theBoard[startPos[0]][startPos[1]].white) {
            return;
        }
        //If black to move and white piece chosen return
        if(!this.whiteToMove && this.theBoard[startPos[0]][startPos[1]].white) {
            return;
        }

        // get the piece we are trying to move
        Piece thePiece = theBoard[startPos[0]][startPos[1]];

        if(thePiece!=null){
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
            } // castling

            // check for enpassant attack and promotion
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

                // promote the pawn at the end of the board
                if (endPos[0]==0 && whiteToMove) {
                    Pawn pawn = (Pawn) thePiece;
                    thePiece = pawn.promote(whiteToMove, endPos[0], endPos[1], 0);
                }
                else if (endPos[0]==7 && !whiteToMove) {
                    Pawn pawn = (Pawn) thePiece;
                    thePiece = pawn.promote(whiteToMove, endPos[0], endPos[1], 0);
                }
            } // en passant check

            // set en passant flag back to false
            this.enPassant=false;

            // check if a pawn has moved twice to set en passant flag
            if(thePiece.type==PieceType.PAWN){
                if(Math.abs(startPos[0]-endPos[0])==2){
                    this.enPassant=true;
                    this.enPassantCol=endPos[1];
                }
            }

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
            // update the objects location on the board
            thePiece.row = endPos[0];
            thePiece.col = endPos[1];

            // get possible moves of the piece we moved
            ArrayList<Piece> moves = theBoard[endPos[0]][endPos[1]].getMoves(this);

            // check if the piece we moved has put the king in check
            for(Piece p : moves){
                if(theBoard[p.row][p.col]!=null && theBoard[p.row][p.col].type==PieceType.KING){
                    kingInCheck=true;
                    pieceAttackingKing=theBoard[endPos[0]][endPos[1]];
                }
            }

            // set the piece's previous position to null
            theBoard[startPos[0]][startPos[1]]=null;

            // set flag so we know the piece has moved
            theBoard[endPos[0]][endPos[1]].hasMoved=true;

            // change turn
            this.whiteToMove = !this.whiteToMove;

            // print board
            this.printBoard();

        } // if the piece is not null i.e. legal move was attempted

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
