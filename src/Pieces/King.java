package Pieces;

import Board.BoardState;

public class King extends Piece {

    public boolean hasMoved=false;

    public King(boolean color, int[] pos, char letter){
        super(color, pos, letter);
    }

    @Override
    public boolean checkMove(BoardState board, int[] startPos, int[] endPos) {
        return false;
    }
}
