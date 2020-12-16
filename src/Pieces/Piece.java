package Pieces;

import Board.BoardState;

/**
 Jason Grightmire (5388327)
 David Saldana Suarez (6155964)
 COSC 3P71
 Chess Project
 */

import java.util.ArrayList;

public abstract class Piece {

    //0 is black and 1 is white
    public boolean white=true;
    public boolean captured=false;
    public int prevRow;
    public int prevCol;
    public int row;
    public int col;
    public char pieceLetter;
    public PieceType type;
    public boolean hasMoved=false;
    public boolean hasDeveloped=false;
    public boolean isPinned = false;
    public double evaluation = 0;
    public int value;
    ArrayList<Piece> moves = new ArrayList<>();

    public Piece(boolean color, int r, int c, Piece p){
        this.white = color;
        this.row = r;
        this.col = c;
    }

    public boolean inBounds(int r, int c){
        if(r<0 || c<0 || r>7 || c>7){
            return false;
        }
        return true;
    }

    public abstract ArrayList<Piece> getMoves(BoardState board);

    public abstract int evaluatePiece(BoardState board);

    public void makeCopy (Piece p) {
        // not sure if we need this since colour, row, and column are set automatically
        //this.row = p.row;
        //this.col= p.col;
        //this.white = p.white;
        this.prevRow = p.prevRow;
        this.prevCol = p.prevCol;
        this.type = p.type;
        this.pieceLetter = p.pieceLetter;
        this.hasMoved = p.hasMoved;
        this.isPinned = p.isPinned;
        this.moves = p.moves;
        this.captured = p.captured;
        this.hasDeveloped = p.hasDeveloped;
        this.evaluation = p.evaluation;
        this.value = p.value;
    }
    public double getEvaluation () {
        return this.evaluation;
    }
}
