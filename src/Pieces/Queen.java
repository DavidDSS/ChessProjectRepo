package Pieces;

import Board.BoardState;

public class Queen extends Piece {

    public Queen(boolean color, int[] pos, char letter){
        super(color, pos, letter);
    }

    @Override
    public boolean checkMove(BoardState board, int[] startPos, int[] endPos) {
        return false;
    }
}
