package Pieces;

import Board.BoardState;

public class Knight extends Piece {

    public Knight(boolean color, int[] pos, char letter){
        super(color, pos, letter);
    }

    @Override
    public boolean checkMove(BoardState board, int[] startPos, int[] endPos) {
        return false;
    }
}
