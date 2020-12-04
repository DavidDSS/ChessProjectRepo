package Pieces;

import Board.BoardState;

public class Bishop extends Piece {

    public Bishop(boolean color, char[] pos, char letter){
        super(color, pos, letter);
    }

    @Override
    public boolean checkMove(BoardState board, char[] startPos, char[] endPos) {
        return false;
    }
}
