package Board;

import Engine.Engine;
import Pieces.*;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 Jason Grightmire (5388327)
 David Saldana Suarez (6155964)
 COSC 3P71
 Chess Project
 */

public class BoardState {

    public Piece[][] theBoard = new Piece[8][8];
    public boolean[][] attackedByPiece= new boolean[8][8];

    public double evaluation = 0;
    public int enPassantRow = 0;
    public int enPassantCol = 0;
    public int promoteChoice = -999;

    public boolean whiteToMove = true;
    public boolean kingInCheck = false;
    public boolean enPassant = false;
    public boolean checkmate = false;
    public boolean stalemate = false;

    public ArrayList<Piece> piecesAttackingKing = new ArrayList<>();
    public ArrayList<Piece> capturedPiecesWhite = new ArrayList<>();
    public ArrayList<Piece> capturedPiecesBlack = new ArrayList<>();

    public BoardState(BoardState b){

        if (b != null) {
            this.evaluation = b.evaluation;
            this.whiteToMove = b.whiteToMove;
            this.kingInCheck = b.kingInCheck;
            this.enPassant = b.enPassant;
            this.enPassantRow = b.enPassantRow;
            this.enPassantCol = b.enPassantCol;
            this.promoteChoice = b.promoteChoice;
            this.checkmate = b.checkmate;
            this.stalemate = b.stalemate;
            this.piecesAttackingKing = b.piecesAttackingKing;
            this.capturedPiecesBlack = b.capturedPiecesBlack;
            this.capturedPiecesWhite = b.capturedPiecesWhite;

            // deep copy of the board and pieces
            this.theBoard = b.theBoard.clone();
            this.theBoard = this.createDeepCopy(b.theBoard);

        }
    } // copy constructor

    public Piece[][] createDeepCopy (Piece[][] board) {

        Piece[][] copy = new Piece[8][8];
        // make deep copy of pieces
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {

                if (board[r][c] != null) {
                    if (board[r][c].type == PieceType.PAWN) {
                        copy[r][c] = new Pawn(board[r][c].white, board[r][c].row, board[r][c].col, board[r][c]);
                    }
                    else if (board[r][c].type == PieceType.KING) {
                        copy[r][c] = new King(board[r][c].white, board[r][c].row, board[r][c].col, board[r][c]);
                    }
                    else if (board[r][c].type == PieceType.QUEEN) {
                        copy[r][c] = new Queen(board[r][c].white, board[r][c].row, board[r][c].col, board[r][c]);
                    }
                    else if (board[r][c].type == PieceType.KNIGHT) {
                        copy[r][c] = new Knight(board[r][c].white, board[r][c].row, board[r][c].col, board[r][c]);
                    }
                    else if (board[r][c].type == PieceType.BISHOP) {
                        copy[r][c] = new Bishop(board[r][c].white, board[r][c].row, board[r][c].col, board[r][c]);
                    }
                    else if (board[r][c].type == PieceType.ROOK) {
                        copy[r][c] = new Rook(board[r][c].white, board[r][c].row, board[r][c].col, board[r][c]);
                    }
                }
            }
        }

        return copy;
    } // createDeepCopy

    public void engineMove (int d) {

        Engine e = new Engine(this, d);
        Piece move = e.calculateBestMove();

        // if no move returned
        if (move == null) {
            checkStalemate();
            checkCheckmate();
        }
        else {
            System.out.println("AI Move: "+convertAImove(move.prevRow, move.prevCol)+" -> "+convertAImove(move.row, move.col));
            if (getLineOfAttack()!=null) {
                System.out.println(getLineOfAttack().size());
            }
            makeMove(move.prevRow, move.prevCol, move.row, move.col);
        }
    }

    public double evaluatePosition () {
        double activityEval = 0;
        double pieceEval = 0;
        double checkmateEval = 0;

        // evaluate all pieces
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if(theBoard[r][c]!=null) {
                    // get the evaluation of the piece and add it to the total
                    pieceEval += theBoard[r][c].getPieceValue();
                    activityEval += theBoard[r][c].evaluatePiece(this);
                }
            }
        }

        if (this.checkmate) {
            if (this.whiteToMove) {
                checkmateEval -= 9000;
            }
            else {
                checkmateEval += 9000;
            }
        }

        // set and return the evaluation
        this.evaluation = 0.7*activityEval + pieceEval + checkmateEval;
        return 0.7*activityEval + pieceEval + checkmateEval;
    }

    public void userMove(int startR, int startC, int endR, int endC){

        boolean legalMove=false;

        // user chose empty square
        if(theBoard[startR][startC]==null){
            System.out.println("That is an illegal move!");
            return;
        }

        // the chosen piece
        ArrayList<Piece> chosenPieceMoves = theBoard[startR][startC].getMoves(this);

        // check if the king is in check, user has to react
        if (kingInCheck) {
            // get the legal moves
            ArrayList<Piece> moves = legalMovesKingInCheck();

            // check if the user chose a legal move to evade check
            for (Piece a : moves) {
                for (Piece p : chosenPieceMoves) {
                    if (p.row == a.row && p.col == a.col && p.white == a.white && p.type == a.type) {
                        legalMove = true;
                    }
                }
            }

        }
        // check if the user entered valid end position
        else {
            for(Piece p : chosenPieceMoves){
                if(p.row==endR && p.col==endC){
                    legalMove=true;
                }
            }
        }

        // if the user move is legal, actually make the move
        if(legalMove) {
            if(theBoard[startR][startC].type==PieceType.PAWN){
                if(endR==0){
                    System.out.println("Please choose what the pawn should promote to:");
                    System.out.println("Enter 0 for Queen");
                    System.out.println("Enter 1 for Rook");
                    System.out.println("Enter 2 for Knight");
                    System.out.println("Enter 3 for Bishop");

                    Scanner promoteInput = new Scanner(System.in);
                    promoteChoice= promoteInput.nextInt();

                }
            }
            makeMove(startR, startC, endR, endC);
        }
        else{
            System.out.println("That is not a legal move!");
        }

    }

    public ArrayList<Piece> getAllPossibleMoves() {

        ArrayList<Piece> allMoves = new ArrayList<>();

        // loop through each square on the board
        for(int r=0; r<8; r++){
            for(int c=0; c<8;c++){
                // only get the correct colour moves
                if(theBoard[r][c]!=null && (whiteToMove==theBoard[r][c].white)) {
                    ArrayList<Piece> pieceMoves = theBoard[r][c].getMoves(this);
                    for(Piece p : pieceMoves){
                        // set where the piece is coming from
                        p.prevRow = r;
                        p.prevCol = c;
                        //All Moves for the current player's pieces (white or black)
                        allMoves.add(p);
                    }
                }
            }
        }

        return allMoves;

    } // getAllPossibleMoves

    public void checkStalemate () {
        ArrayList<Piece> moves = getAllPossibleMoves();
        boolean otherThanKing=false;
        for(Piece p : moves){
            if(p.type!=PieceType.KING) otherThanKing=true;
        }
        if(!otherThanKing) {
            moves = legalMovesKingInCheck();
            if (moves.size() == 0) {
                stalemate = true;
            }
        }
    }

    public void checkCheckmate () {

        if (kingInCheck) {
            ArrayList<Piece> moves = legalMovesKingInCheck();
            if (moves.size() == 0) {
                checkmate = true;
            }
        }
    }

    public ArrayList<Piece> getLineOfAttack () {

        if (piecesAttackingKing == null || piecesAttackingKing.size() < 1) return null;
        Piece attackingPiece = piecesAttackingKing.get(0);
        ArrayList<Piece> lineOfAttack = new ArrayList<>();
        Piece king = null;

        // get the position of the king
        for(int r=0; r<8; r++) {
            for (int c = 0; c < 8; c++) {
                if(theBoard[r][c]!=null && theBoard[r][c].type==PieceType.KING && theBoard[r][c].white==whiteToMove){
                    king = theBoard[r][c];
                    break;
                }
            }
            if (king!=null) break;
        }

        int rowDistance, colDistance;
        rowDistance = attackingPiece.row - king.row;
        colDistance = attackingPiece.col - king.col;

        // if the attacking piece is a knight
        if (attackingPiece.type == PieceType.KNIGHT) {
            return lineOfAttack;
        }
        // if the attacking piece is a pawn
        else if (attackingPiece.type == PieceType.PAWN) {
            return lineOfAttack;
        }
        // if the attacking piece is a rook or a queen
        if (attackingPiece.type == PieceType.ROOK || attackingPiece.type == PieceType.QUEEN) {
            if (attackingPiece.col == king.col) {
                int inc = rowDistance > 0 ? -1 : 1;
                int kingDistance = Math.abs(attackingPiece.row - king.row);
                for (int i = 1; i < kingDistance; i ++) {
                    if (attackingPiece.type == PieceType.ROOK) {
                        lineOfAttack.add(new Rook(attackingPiece.white, attackingPiece.row+i*inc, attackingPiece.col, attackingPiece));
                    } else {
                        lineOfAttack.add(new Queen(attackingPiece.white, attackingPiece.row+i*inc, attackingPiece.col, attackingPiece));
                    }
                }
            }
            else if (attackingPiece.row == king.row){
                int inc = colDistance > 0 ? -1 : 1;
                int kingDistance = Math.abs(attackingPiece.row - king.row);
                for (int i = 1; i < kingDistance; i ++) {
                    if (attackingPiece.type == PieceType.ROOK) {
                        lineOfAttack.add(new Rook(attackingPiece.white, attackingPiece.row, attackingPiece.col+i*inc, attackingPiece));
                    } else {
                        lineOfAttack.add(new Queen(attackingPiece.white, attackingPiece.row, attackingPiece.col+i*inc, attackingPiece));
                    }
                }
            }
        }
        // if the attacking piece is a bishop or a queen
        if (attackingPiece.type == PieceType.BISHOP || attackingPiece.type == PieceType.QUEEN) {
            if (attackingPiece.col != king.col && attackingPiece.row != king.row) {
                int incRow = rowDistance > 0 ? -1 : 1;
                int incCol = colDistance > 0 ? -1 : 1;
                int kingDist = Math.abs(king.row-attackingPiece.row);

                // add squares between the piece and the king
                for (int i = 1; i < kingDist; i++) {
                    if (attackingPiece.type == PieceType.BISHOP) {
                        lineOfAttack.add(new Bishop(attackingPiece.white, attackingPiece.row+i*incRow, attackingPiece.col+i*incCol, attackingPiece));
                    } else {
                        lineOfAttack.add(new Queen(attackingPiece.white, attackingPiece.row+i*incRow, attackingPiece.col+i*incCol, attackingPiece));
                    }
                }
            }
        }

        return lineOfAttack;
    }

    public ArrayList<Piece> legalMovesKingInCheck () {

        ArrayList<Piece> lineOfAttack= getLineOfAttack();
        ArrayList<Piece> legalMoves= new ArrayList<>();
        attackedByPiece= new boolean[8][8];

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

        // for the attacking piece just add all theoretical attack moves
        for (Piece p : piecesAttackingKing) {
            if (p.type == PieceType.QUEEN || p.type == PieceType.ROOK) {
                // check off every square on the column
                for (int r=0; r < 8; r++) {
                    attackedByPiece[r][p.col] = true;
                }
                // check off every square on the row
                for (int c=0; c < 8; c++) {
                    attackedByPiece[p.row][c] = true;
                }
            }
            if (p.type == PieceType.QUEEN || p.type == PieceType.BISHOP) {
                // check off every square on one diagonal
                int[][] directions = {
                        {1, 1},
                        {-1, 1},
                        {1, -1},
                        {-1, -1},
                };
                for (int[] dir : directions) {
                    for(int i=1; i<8;i++){
                        //Check if move is in bounds
                        if(p.row + i*dir[0]<0 || p.col + i*dir[1]<0 || p.row + i*dir[0]>7 || p.col + i*dir[1]>7) break;
                        attackedByPiece[p.row + i*dir[0]][p.col + i*dir[1]] = true;
                    }
                }
            }
        }

        ArrayList<Piece> moves = getAllPossibleMoves();
        // all possible moves loop
        for(Piece p : moves){
            // possible ways to get out of check
            // 1. move the king
            if(p.type==PieceType.KING){
                if(!attackedByPiece[p.row][p.col]){
                    legalMoves.add(p);
                }
            }

            // 2. block the line of attack with a piece other than the king
            if (piecesAttackingKing.size() == 1) {
                for(Piece l:lineOfAttack){
                    if(p.type!=PieceType.KING && p.row==l.row && p.col==l.col){
                        legalMoves.add(p);
                    }
                }
            }


            // 3. capture the attacking piece
            if (piecesAttackingKing.size() == 1) {
                if (p.row == piecesAttackingKing.get(0).row && p.col == piecesAttackingKing.get(0).col) {
                    if (p.type == PieceType.PAWN) {
                        Pawn pawn = (Pawn) p;
                        if (pawn.getAttacking()) {
                            legalMoves.add(p);
                        }
                    } else if (p.type!=PieceType.KING){
                        legalMoves.add(p);
                    }
                }
            }
        } // all possible moves

        return legalMoves;
    }

    public void makeMove(int startR, int startC, int endR, int endC){

        piecesAttackingKing.clear();
        kingInCheck = false;

        //If white to move and black piece chosen return
        if(this.whiteToMove && !this.theBoard[startR][startC].white) {
            return;
        }
        //If black to move and white piece chosen return
        if(!this.whiteToMove && this.theBoard[startR][startC].white) {
            return;
        }

        // get the piece we are trying to move
        Piece thePiece = theBoard[startR][startC];

        if(thePiece!=null){
            // check for castling move
            if (thePiece.type==PieceType.KING) {
                // king side castles
                if ((endC-startC)==2) {
                    // we account for the king moving later
                    if (whiteToMove) {
                        // only move the rook
                        theBoard[7][5] = new Rook(true, 7, 5, theBoard[7][7]);
                        theBoard[7][5].hasMoved = true;
                        theBoard[7][7] = null;
                        // update the objects location on the board
                        theBoard[7][5].row = 7;
                        theBoard[7][5].col = 5;
                    }
                    else {
                        // only move the rook
                        theBoard[0][5] = new Rook(false, 0, 5, theBoard[0][7]);
                        theBoard[0][5].hasMoved = true;
                        theBoard[0][7] = null;
                        // update the objects location on the board
                        theBoard[0][5].row = 0;
                        theBoard[0][5].col = 5;
                    }
                }

                // queen side castles
                if ((endC-startC)==-2) {
                    // we account for the king moving later
                    if (whiteToMove) {
                        // only move the rook
                        theBoard[7][3] = new Rook(true, 7, 3, theBoard[7][0]);
                        theBoard[7][3].hasMoved = true;
                        theBoard[7][0] = null;
                        // update the objects location on the board
                        theBoard[7][3].row = 7;
                        theBoard[7][3].col = 3;
                    }
                    else {
                        // only move the rook
                        theBoard[0][3] = new Rook(false, 0, 3, theBoard[0][0]);
                        theBoard[0][3].hasMoved = true;
                        theBoard[0][0] = null;
                        // update the objects location on the board
                        theBoard[0][3].row = 0;
                        theBoard[0][3].col = 3;
                    }
                }
            } // castling

            // check for enpassant attack and promotion
            if(thePiece.type==PieceType.PAWN){
                // check if move is left or right (enpassant)
                if(Math.abs(endC-startC)==1){
                    if(enPassantCol==endC){
                        // white en passant
                        if(this.whiteToMove){
                            if (theBoard[endR+1][endC]!= null && !theBoard[endR+1][endC].white) {
                                capturedPiecesBlack.add(theBoard[endR+1][endC]);
                                theBoard[endR+1][endC]=null;
                            }
                        }
                        // black en passant
                        else{
                            if (theBoard[endR-1][endC]!= null && theBoard[endR-1][endC].white) {
                                capturedPiecesWhite.add(theBoard[endR-1][endC]);
                                theBoard[endR-1][endC]=null;
                            }
                        }
                    }
                }

                // promote the pawn at the end of the board
                if (endR==0 && whiteToMove) {
                    Pawn pawn = (Pawn) thePiece;
                    if(promoteChoice!=-999){
                        thePiece = pawn.promote(whiteToMove, endR, endC, promoteChoice);
                    }
                    else{
                        thePiece = pawn.promote(whiteToMove, endR, endC, 0);
                    }
                }
                else if (endR==7 && !whiteToMove) {
                    Pawn pawn = (Pawn) thePiece;
                    thePiece = pawn.promote(whiteToMove, endR, endC, 0);
                }
            } // en passant check

            // set en passant flag back to false
            this.enPassant=false;

            // check if a pawn has moved twice to set en passant flag
            if(thePiece.type==PieceType.PAWN){
                if(Math.abs(startR-endR)==2){
                    this.enPassant=true;
                    this.enPassantRow=endR;
                    this.enPassantCol=endC;
                }
            }

            // if we are capturing a piece, add the captured piece to respective list
            if (theBoard[endR][endC] != null) {
                if (this.whiteToMove) {
                    capturedPiecesBlack.add(theBoard[endR][endC]);
                }
                else {
                    capturedPiecesWhite.add(theBoard[endR][endC]);
                }
            }

            // place the piece in the end position on the board
            theBoard[endR][endC]=thePiece;
            // update the objects location on the board
            thePiece.row = endR;
            thePiece.col = endC;

            // get possible moves of the piece we moved
            ArrayList<Piece> moves = theBoard[endR][endC].getMoves(this);
            // check if the piece we moved has put the king in check
            for(Piece p : moves){
                // opposite colour king
                if(theBoard[p.row][p.col]!=null && theBoard[p.row][p.col].type==PieceType.KING){
                    if (theBoard[p.row][p.col].white != p.white) {
                        kingInCheck = true;
                        piecesAttackingKing.add(theBoard[endR][endC]);
                    }

                }
            }
            // check for a double check, other pieces attacking the king
            for (int r=0; r<8; r++) {
                for (int c=0; c<8; c++) {
                    // not the same piece
                    if(theBoard[r][c]!=null && theBoard[r][c].white==whiteToMove && !(r==endR && c==endC)) {
                        moves = theBoard[r][c].getMoves(this);
                        for (Piece p : moves) {
                            // opposite colour king
                            if (theBoard[p.row][p.col]!=null && theBoard[p.row][p.col].type==PieceType.KING) {
                                if (theBoard[p.row][p.col].white != p.white) {
                                    kingInCheck = true;
                                    piecesAttackingKing.add(p);
                                }
                            }
                        }
                    }
                }
            }

            // set the piece's previous position to null
            theBoard[startR][startC]=null;

            // set flag so we know the piece has moved
            theBoard[endR][endC].hasMoved=true;

            // change turn
            this.whiteToMove = !this.whiteToMove;

            // check for checkmate
            checkCheckmate();

            // check for stalemate
            checkStalemate();

        } // if the piece is not null i.e. legal move was attempted

    }

    public void setClassicalPosition () {

        for (int p = 0; p < 8; p++) {
            theBoard[1][p] = new Pawn(false, 1, p, null);
            theBoard[6][p] = new Pawn(true, 6, p, null);
        }

        theBoard[0][0] = new Rook(false, 0, 0, null);
        theBoard[0][7] = new Rook(false, 0, 7, null);
        theBoard[7][0] = new Rook(true, 7, 0, null);
        theBoard[7][7] = new Rook(true, 7, 7, null);

        theBoard[0][1] = new Knight(false, 0, 1, null);
        theBoard[0][6] = new Knight(false, 0, 6, null);
        theBoard[7][1] = new Knight(true, 7, 1, null);
        theBoard[7][6] = new Knight(true, 7, 6, null);

        theBoard[0][2] = new Bishop(false, 0, 2, null);
        theBoard[0][5] = new Bishop(false, 0, 5, null);
        theBoard[7][2] = new Bishop(true, 7, 2, null);
        theBoard[7][5] = new Bishop(true, 7, 5, null);

        theBoard[0][3] = new Queen(false, 0, 3, null);
        theBoard[7][3] = new Queen(true, 7, 3, null);

        theBoard[0][4] = new King(false, 0, 4, null);
        theBoard[7][4] = new King(true, 7, 4, null);

    } // setClassicalPosition

    public void setPiece(boolean colour, char pieceLetter, int r, int c) {
        if(pieceLetter=='k' || pieceLetter=='K') {
            theBoard[r][c] = new King(colour, r, c, null);
        }
        else if(pieceLetter=='p' || pieceLetter=='P') {
            theBoard[r][c] = new Pawn(colour, r, c, null);
        }
        else if(pieceLetter=='r' || pieceLetter=='R') {
            theBoard[r][c] = new Rook(colour, r, c, null);
        }
        else if(pieceLetter=='n' || pieceLetter=='N') {
            theBoard[r][c] = new Knight(colour, r, c, null);
        }
        else if(pieceLetter=='b' || pieceLetter=='B') {
            theBoard[r][c] = new Bishop(colour, r, c, null);
        }
        else if(pieceLetter=='q' || pieceLetter=='Q') {
            theBoard[r][c] = new Queen(colour, r, c, null);
        }
    }

    public void printBoard(){
        System.out.print("  ");
        for(int c=0; c<8;c++){
            System.out.print((char)(97+c)+" ");
        }
        System.out.println();
        for(int r=0; r<8; r++){
            System.out.print(8-r+" ");
            for(int c=0; c<8; c++){
                if(theBoard[r][c]!=null)System.out.print(theBoard[r][c].pieceLetter+" ");
                else System.out.print("- ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public String convertAImove (int r, int c) {

        String move = "";

        if (c==0) move+="a";
        else if (c==1) move+="b";
        else if (c==2) move+="c";
        else if (c==3) move+="d";
        else if (c==4) move+="e";
        else if (c==5) move+="f";
        else if (c==6) move+="g";
        else if (c==7) move+="h";

        if (r==0) move+="8";
        else if (r==1) move+="7";
        else if (r==2) move+="6";
        else if (r==3) move+="5";
        else if (r==4) move+="4";
        else if (r==5) move+="3";
        else if (r==6) move+="2";
        else if (r==7) move+="1";

        return move;
    }

}
