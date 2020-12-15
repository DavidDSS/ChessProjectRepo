package Pieces;

import Board.BoardState;

import java.util.ArrayList;

public class Queen extends Piece {

    int value = this.white ? 90 : -90;

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


        // how many squares does the queen control
        // is the queen putting the king in check
        // is the queen on a good central square
        // is the queen pinning a piece
        int eval = 0;
        return this.value + eval;
    }
}
