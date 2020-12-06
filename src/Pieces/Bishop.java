package Pieces;

import Board.BoardState;

import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(boolean color, int[] pos, char letter){
        super(color, pos, letter);
        moves= new ArrayList<>();
    }

    @Override
    public ArrayList<Piece> getMoves(BoardState board) {
        moves.clear();
        //White
        if(board.whiteToMove) {

        }
        //Black
        else{

        }
        return moves;
    }
}
