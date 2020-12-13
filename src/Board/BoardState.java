package Board;

import Engine.Engine;
import Pieces.*;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class BoardState {

    public Piece[][] theBoard = new Piece[8][8];
    public boolean[][] attackedByPiece= new boolean[8][8];
    public double evaluation=0;
    public boolean whiteToMove=true;
    public boolean kingInCheck=false;
    public ArrayList<Piece> piecesAttackingKing = new ArrayList<>();
    public boolean enPassant=false;
    public int enPassantCol;
    public boolean gameOver=false;

    ArrayList<Piece> capturedPiecesWhite = new ArrayList<>();
    ArrayList<Piece> capturedPiecesBlack = new ArrayList<>();

    //Starting Position
    public BoardState(Piece[][] position){
        if (position != null) {
            theBoard = position;
        }
    }

    public void engineMove () {

        Engine e = new Engine(this);
        Piece move = e.calculateBestMove();
        makeMove(new int[]{move.prevRow, move.prevCol}, new int[]{move.row, move.col});

    }

    public double evaluatePosition () {


        return 0;
    }

    public void userMove(int[] startPos, int[] endPos){

        boolean legalMove=false;

        // user chose empty square
        if(theBoard[startPos[0]][startPos[1]]==null){
            System.out.println("That is not a legal move!");
            return;
        }

        // the chosen piece
        ArrayList<Piece> chosenPieceMoves = theBoard[startPos[0]][startPos[1]].getMoves(this);

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
                if(p.row==endPos[0] && p.col==endPos[1]){
                    legalMove=true;
                }
            }
        }

        // if the user move is legal, actually make the move
        if(legalMove) {
            makeMove(startPos, endPos);
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
                gameOver = true;
                System.out.println("Stalemate: 1/2 - 1/2");
            }
        }
    }
    public void checkCheckmate () {

        if (kingInCheck) {
            ArrayList<Piece> moves = legalMovesKingInCheck();
            if (moves.size() == 0) {
                gameOver = true;
                String winner = whiteToMove ? "Black" : "White";
                System.out.println(winner + " is victorious!");
            }
        }
    }

    public ArrayList<Piece> getLineOfAttack () {

        if (piecesAttackingKing == null || piecesAttackingKing.size() < 1) return null;
        Piece attackingPiece = piecesAttackingKing.get(0);
        ArrayList<Piece> lineOfAttack = new ArrayList<>();
        Piece king = null;

        // get the position of the black king
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
                for (int i = attackingPiece.row; i < king.row; i += inc) {
                    if (attackingPiece.type == PieceType.ROOK) {
                        lineOfAttack.add(new Rook(attackingPiece.white, i, attackingPiece.col));
                    } else {
                        lineOfAttack.add(new Queen(attackingPiece.white, i, attackingPiece.col));
                    }
                }
            }
            else if (attackingPiece.row == king.row){
                int inc = colDistance > 0 ? -1 : 1;
                for (int i = attackingPiece.col; i < king.col; i += inc) {
                    if (attackingPiece.type == PieceType.ROOK) {
                        lineOfAttack.add(new Rook(attackingPiece.white, attackingPiece.row, i));
                    } else {
                        lineOfAttack.add(new Queen(attackingPiece.white, attackingPiece.row, i));
                    }
                }
            }
        }
        // if the attacking piece is a bishop or a queen
        if (attackingPiece.type == PieceType.BISHOP || attackingPiece.type == PieceType.QUEEN) {
            int incRow = rowDistance > 0 ? -1 : 1;
            int incCol = colDistance > 0 ? -1 : 1;
            int destination = attackingPiece.row;
            int i = 1;
            while (destination != king.row) {
                if (attackingPiece.type == PieceType.BISHOP) {
                    lineOfAttack.add(new Bishop(attackingPiece.white, attackingPiece.row+incRow*i, attackingPiece.col+incCol*i));
                }
                else {
                    lineOfAttack.add(new Queen(attackingPiece.white, attackingPiece.row+incRow*i, attackingPiece.col+incCol*i));
                }
                destination += incRow;
                i++;
            }
        }

        return lineOfAttack;
    }

    public ArrayList<Piece> legalMovesKingInCheck () {

        ArrayList<Piece> lineOfAttack= getLineOfAttack();
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

            // 2. block the line of attack with a piece
            if (piecesAttackingKing.size() == 1) {
                for(Piece l:lineOfAttack){
                    if(p.row==l.row && p.col==l.col){
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

    public void makeMove(int[] startPos, int[] endPos){

        piecesAttackingKing.clear();
        kingInCheck = false;

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
                        // update the objects location on the board
                        theBoard[7][5].row = 7;
                        theBoard[7][5].col = 5;
                    }
                    else {
                        // only move the rook
                        theBoard[0][5] = new Rook(false, 0, 5);
                        theBoard[0][5].hasMoved = true;
                        theBoard[0][7] = null;
                        // update the objects location on the board
                        theBoard[0][5].row = 0;
                        theBoard[0][5].col = 5;
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
                        // update the objects location on the board
                        theBoard[7][3].row = 7;
                        theBoard[7][3].col = 3;
                    }
                    else {
                        // only move the rook
                        theBoard[0][3] = new Rook(false, 0, 3);
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
                    piecesAttackingKing.add(theBoard[endPos[0]][endPos[1]]);
                }
            }
            // check for a double check, other pieces attacking the king
            for (int r=0; r<8; r++) {
                for (int c=0; c<8; c++) {
                    // not the same piece
                    if(theBoard[r][c]!=null && theBoard[r][c].white==whiteToMove && r!=endPos[0] && c!=endPos[1] && r!=startPos[0] && c!=startPos[1]) {
                        moves = theBoard[r][c].getMoves(this);
                        for (Piece p : moves) {
                            if (theBoard[p.row][p.col]!=null && theBoard[p.row][p.col].type==PieceType.KING) {
                                kingInCheck = true;
                                piecesAttackingKing.add(p);
                            }
                        }
                    }
                }
            }

            // set the piece's previous position to null
            theBoard[startPos[0]][startPos[1]]=null;

            // set flag so we know the piece has moved
            theBoard[endPos[0]][endPos[1]].hasMoved=true;

            // change turn
            this.whiteToMove = !this.whiteToMove;

            // check for checkmate
            checkCheckmate();

            // check for stalemate
            checkStalemate();

            // check to see if the game is over
            this.printBoard();

        } // if the piece is not null i.e. legal move was attempted

    }

    public void setClassicalPosition () {

        for (int p = 0; p < 8; p++) {
            theBoard[1][p] = new Pawn(false, 1, p);
            theBoard[6][p] = new Pawn(true, 6, p);
        }

        theBoard[0][0] = new Rook(false, 0, 0);
        theBoard[0][7] = new Rook(false, 0, 7);
        theBoard[7][0] = new Rook(true, 7, 0);
        theBoard[7][7] = new Rook(true, 7, 7);

        theBoard[0][1] = new Knight(false, 0, 1);
        theBoard[0][6] = new Knight(false, 0, 6);
        theBoard[7][1] = new Knight(true, 7, 1);
        theBoard[7][6] = new Knight(true, 7, 6);

        theBoard[0][2] = new Bishop(false, 0, 2);
        theBoard[0][5] = new Bishop(false, 0, 5);
        theBoard[7][2] = new Bishop(true, 7, 2);
        theBoard[7][5] = new Bishop(true, 7, 5);

        theBoard[0][3] = new Queen(false, 0, 3);
        theBoard[7][3] = new Queen(true, 7, 3);

        theBoard[0][4] = new King(false, 0, 4);
        theBoard[7][4] = new King(true, 7, 4);

    } // setClassicalPosition

    public void setPiece(boolean colour, char pieceLetter, int r, int c) {
        if(pieceLetter=='k' || pieceLetter=='K') {
            theBoard[r][c] = new King(colour, r, c);
        }
        else if(pieceLetter=='p' || pieceLetter=='P') {
            theBoard[r][c] = new Pawn(colour, r, c);
        }
        else if(pieceLetter=='r' || pieceLetter=='R') {
            theBoard[r][c] = new Rook(colour, r, c);
        }
        else if(pieceLetter=='n' || pieceLetter=='N') {
            theBoard[r][c] = new Knight(colour, r, c);
        }
        else if(pieceLetter=='b' || pieceLetter=='B') {
            theBoard[r][c] = new Bishop(colour, r, c);
        }
        else if(pieceLetter=='q' || pieceLetter=='Q') {
            theBoard[r][c] = new Queen(colour, r, c);
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

}
