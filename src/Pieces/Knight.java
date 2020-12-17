package Pieces;

import Board.BoardState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 Jason Grightmire jg12jg (5388327)
 David Saldana Suarez ds16vx (6155964)
 COSC 3P71
 Chess Project
 */

public class Knight extends Piece {

    int value = this.white ? 300 : -300;
    int[][] idealSquares = {
            {-50,-40,-30,-30,-30,-30,-40,-50},
            {-40,-20,  0,  0,  0,  0,-20,-40},
            {-30,  0, 10, 15, 15, 10,  0,-30},
            {-30,  5, 15, 20, 20, 15,  5,-30},
            {-30,  0, 15, 20, 20, 15,  0,-30},
            {-30,  5, 10, 15, 15, 10,  5,-30},
            {-40,-20,  0,  5,  5,  0,-20,-40},
            {-50,-40,-20,-30,-30,-20,-40,-50},
    };

    public Knight(boolean color, int r, int c, Piece p){
        super(color, r, c, p);
        if (p != null) {
            this.makeCopy(p);
        }
        else {
            type = PieceType.KNIGHT;
            this.pieceLetter = color ? 'n' : 'N';
        }
    }

    @Override
    public ArrayList<Piece> getMoves(BoardState board) {
        moves.clear();

        int pr=this.row;
        int pc=this.col;

        int[][] jumps={
                {-2,1},
                {2,1},
                {2,-1},
                {-2,-1},
                {-1,2},
                {1,2},
                {1,-2},
                {-1,-2},
        };

        //For possible jump check if square is empty or if attacking piece
        for(int[] j:jumps){
            if(inBounds(pr+j[0], pc+j[1])) {
                if (board.theBoard[pr + j[0]][pc + j[1]] == null) {
                    moves.add(new Knight(this.white, pr + j[0], pc + j[1], this));
                }
                else{
                    //If the knight is trying to attack check piece color
                    if(board.whiteToMove) {
                        if (!board.theBoard[pr + j[0]][pc + j[1]].white){
                            moves.add(new Knight(this.white, pr + j[0], pc + j[1], this));
                        }
                    }
                    else{
                        if (board.theBoard[pr + j[0]][pc + j[1]].white){
                            moves.add(new Knight(this.white, pr + j[0], pc + j[1], this));
                        }
                    }
                }
            }
        }

        return moves;
    }

    @Override
    public int evaluatePiece(BoardState board) {
        int eval = 0;
        int pr = this.row;
        int pc = this.col;
        int minmax = this.white ? 1 : -1;

        // how many squares does the knight control
        if (this.moves!= null && this.moves.size() > 0) {
            eval += minmax*this.moves.size();
        }

        if (!this.white) {
            Collections.reverse(Arrays.asList(idealSquares));
        }
        // points for the piece being on its most effective square
        eval += minmax*idealSquares[pr][pc];

        return eval;
    }
}
