package Pieces;

import Board.BoardState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 Jason Grightmire (5388327)
 David Saldana Suarez (6155964)
 COSC 3P71
 Chess Project
 */

public class Rook extends Piece{

    int value = this.white ? 500 : -500;
    int[][] directions = {
            {1, 0},
            {-1, 0},
            {0, -1},
            {0, 1},
    };
    int[][] idealSquares = {
            {  0,  0,  0,  0,  0,  0,  0,  0},
            { 20, 20, 20, 20, 20, 20, 20, 20},
            {  0,  0,  0,  0,  0,  0,  0,  0},
            {  5,  0,  5,  5,  5,  5,  0,  5},
            {  5,  0,  5,  5,  5,  5,  0,  5},
            {  5,  0,  0,  0,  0,  0,  0,  5},
            {  0,  0,  0,  0,  0,  0,  0,  0},
            {  0, 10, 0,  0,  0,  0,  10,  0}
    };

    public Rook(boolean color, int r, int c, Piece p){
        super(color, r, c, p);
        if (p != null) {
            this.makeCopy(p);
        }
        else {
            type=PieceType.ROOK;
            this.pieceLetter=color?'r':'R';
        }
    }

    @Override
    public ArrayList<Piece> getMoves(BoardState board) {
        moves.clear();

        int pr = this.row;
        int pc = this.col;

        //check possible moves for each direction up, down, left, right
        for (int[] dir : directions) {
            for(int i=1; i<8;i++){
                //Check if move is in bounds
                if(!inBounds(pr + i*dir[0],pc + i*dir[1])) break;
                //If no piece add move
                if (board.theBoard[pr + i*dir[0]][pc + i*dir[1]] == null) {
                    moves.add(new Rook(this.white, pr + i*dir[0], pc + i*dir[1], this));
                }
                //If piece encounter add move if opposite color
                if (board.theBoard[pr + i*dir[0]][pc + i*dir[1]] != null) {
                    // attempt to capture piece as white
                    if (board.whiteToMove) {
                        // white piece encountered
                        if (board.theBoard[pr + i*dir[0]][pc + i*dir[1]].white) {
                            break;
                        }
                        else {
                            moves.add(new Rook(this.white, pr + i*dir[0], pc + i*dir[1], this));
                            break;
                        }
                    }
                    // attempt to capture piece as black
                    else {
                        // black piece encountered
                        if (!board.theBoard[pr + i*dir[0]][pc + i*dir[1]].white) {
                            break;
                        }
                        else {
                            moves.add(new Rook(this.white, pr + i*dir[0], pc + i*dir[1], this));
                            break;
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

        // how many squares does the rook control
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
