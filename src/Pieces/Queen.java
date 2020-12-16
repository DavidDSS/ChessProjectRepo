package Pieces;

import Board.BoardState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Queen extends Piece {

    int value = this.white ? 900 : -900;
    int[][] idealSquares = {
            {-20,-10,-10,  0,  0,-10,-10,-20},
            {-10,  0,  0,  0,  0,  5,  0,-10},
            {-10,  0,  5,  5,  5,  5,  0,-10},
            {  5,  0,  5,  5,  5,  5,  0,  5},
            {  5,  0,  5,  5,  5,  5,  0,  5},
            {-10,  5,  5,  5,  5,  5,  0,-10},
            {-10,  0,  5,  0,  0,  0,  0,-10},
            {-20,-10,-10,  0,  0,-10,-10,-20}
    };

    public Queen(boolean color, int r, int c, Piece p){
        super(color, r, c, p);
        if (p != null) {
            this.makeCopy(p);
        }
        else {
            type=PieceType.QUEEN;
            this.pieceLetter=color?'q':'Q';
        }
    }

    @Override
    public ArrayList<Piece> getMoves(BoardState board) {
        moves.clear();

        Piece rook = new Rook(this.white, this.row, this.col, null);
        ArrayList<Piece> rookMoves = rook.getMoves(board);
        for(Piece p : rookMoves){
            moves.add(new Queen(p.white, p.row, p.col, this));
        }

        Piece bishop= new Bishop(this.white, this.row, this.col, null);
        ArrayList<Piece> bishopMoves = bishop.getMoves(board);
        for(Piece p : bishopMoves){
            moves.add(new Queen(p.white, p.row, p.col, this));
        }

        return moves;
    }

    @Override
    public int evaluatePiece(BoardState board) {

        int eval = 0;
        int pr = this.row;
        int pc = this.col;
        int minmax = this.white ? 1 : -1;

        // points for piece development
        if (this.hasMoved && !this.hasDeveloped) {
            eval += minmax*5;
            this.hasDeveloped = true;
        }

        // how many squares does the queen control
        if (this.moves!= null && this.moves.size() > 0) {
            eval += minmax*this.moves.size();
        }

        if (!this.white) {
            Collections.reverse(Arrays.asList(idealSquares));
        }
        // points for the piece being on its most effective square
        eval += minmax*idealSquares[pr][pc];

        return eval;
    }
}
