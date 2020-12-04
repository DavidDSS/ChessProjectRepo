package Pieces;

import Board.BoardState;

public class Bishop extends Piece {

    public Bishop(boolean color, int[] pos, char letter){
        super(color, pos, letter);
    }

    @Override
    public boolean checkMove(BoardState board, int[] startPos, int[] endPos) {
        return false;
    }
}
