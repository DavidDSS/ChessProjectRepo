package Pieces;

import Board.BoardState;

public class Knight extends Piece {

    public Knight(boolean color, char[] pos, char letter){
        super(color, pos, letter);
    }

    @Override
    public boolean checkMove(BoardState board, char[] startPos, char[] endPos) {
        return false;
    }
}
