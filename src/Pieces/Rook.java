package Pieces;

import Board.BoardState;

public class Rook extends Piece{

    public boolean hasMoved=false;

    public Rook(boolean color, char[] pos, char letter){
        super(color, pos, letter);
    }

    @Override
    public boolean checkMove(BoardState board, char[] startPos, char[] endPos) {
        return false;
    }
}
