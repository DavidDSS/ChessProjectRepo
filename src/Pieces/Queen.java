package Pieces;

import Board.BoardState;

import java.util.ArrayList;

public class Queen extends Piece {

    public Queen(boolean color, int r, int c){
        super(color, r, c);
        type=PieceType.QUEEN;
        this.pieceLetter=color?'q':'Q';
        moves= new ArrayList<>();
    }

    @Override
    public ArrayList<Piece> getMoves(BoardState board) {
        moves.clear();

        Piece rook= new Rook(this.white, this.row, this.col);
        ArrayList<Piece> rookMoves=rook.getMoves(board);
        for(Piece p : rookMoves){
            char letter= board.whiteToMove?'q':'Q';
            moves.add(new Queen(p.white, p.row, p.col));

        }

        Piece bishop= new Bishop(this.white, this.row, this.col);
        ArrayList<Piece> bishopMoves=bishop.getMoves(board);
        for(Piece p : bishopMoves){
            char letter= board.whiteToMove?'q':'Q';
            moves.add(new Queen(p.white, p.row, p.col));

        }

        return moves;
    }
}
