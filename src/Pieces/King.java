package Pieces;

import Board.BoardState;

public class King extends Piece {

    public boolean hasMoved=false;

    public King(boolean color, char[] pos, char letter){
        super(color, pos, letter);
    }

    @Override
    public boolean checkMove(BoardState board, char[] startPos, char[] endPos) {
        return false;
    }
}
