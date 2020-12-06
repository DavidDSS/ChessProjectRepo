package Pieces;

import Board.BoardState;

import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(boolean color, int r, int c, char letter){
        super(color, r, c, letter);
        moves= new ArrayList<>();

    }

    @Override
    public ArrayList<Piece> getMoves(BoardState board) {
        return null;
    }
}
