package Pieces;

import Board.BoardState;

import java.util.ArrayList;

public class Queen extends Piece {

    public Queen(boolean color, int r, int c, char letter){
        super(color, r, c, letter);
        moves= new ArrayList<>();
    }

    @Override
    public ArrayList<Piece> getMoves(BoardState board) {
        moves.clear();

        Piece rook= new Rook(this.white, this.row, this.col, this.pieceLetter);
        ArrayList<Piece> rookMoves=rook.getMoves(board);
        for(Piece p : rookMoves){
            char letter= board.whiteToMove?'q':'Q';
            moves.add(new Queen(p.white, p.row, p.col, letter));

        }

        Piece bishop= new Bishop(this.white, this.row, this.col, this.pieceLetter);
        ArrayList<Piece> bishopMoves=bishop.getMoves(board);
        for(Piece p : bishopMoves){
            char letter= board.whiteToMove?'q':'Q';
            moves.add(new Queen(p.white, p.row, p.col, letter));

        }

        return moves;
    }
}
