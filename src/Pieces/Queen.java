package Pieces;

import Board.BoardState;

public class Queen extends Piece {

    public Queen(boolean color, char[] pos, char letter){
        super(color, pos, letter);
    }

    @Override
    public boolean checkMove(BoardState board, char[] startPos, char[] endPos) {
        return false;
    }
}
