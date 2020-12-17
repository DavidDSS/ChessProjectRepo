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

public class Bishop extends Piece {

    // bishops are generally better than knights, hence the extra 5 points
    int value = this.white ? 350 : -350;
    int[][] directions = {
            {1, 1},
            {-1, 1},
            {1, -1},
            {-1, -1},
    };
    int[][] idealSquares = {
            {-20,-10,-10,-10,-10,-10,-10,-20},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-10,  0,  5, 10, 10,  5,  0,-10},
            {-10,  5,  5, 10, 10,  5,  5,-10},
            {-10,  0, 10, 10, 10, 10,  0,-10},
            {-10, 10, 10, 10, 10, 10, 10,-10},
            {-10,  5,  0,  0,  0,  0,  5,-10},
            {-20,-10,-40,-10,-10,-40,-10,-20},
    };

    public Bishop(boolean color, int r, int c, Piece p){
        super(color, r, c, p);
        if (p != null) {
            this.makeCopy(p);
        }
        else {
            type=PieceType.BISHOP;
            this.pieceLetter=color?'b':'B';
        }
    }

    @Override
    public ArrayList<Piece> getMoves(BoardState board) {
        moves.clear();

        int pr=this.row;
        int pc=this.col;

        for (int[] dir : directions) {
            for(int i=1; i<8;i++){
                //Check if move is in bounds
                if(!inBounds(pr + i*dir[0],pc + i*dir[1])) break;
                //If no piece add move
                if (board.theBoard[pr + i*dir[0]][pc + i*dir[1]] == null) {
                    moves.add(new Bishop(this.white, pr + i*dir[0], pc + i*dir[1], this));
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
                            moves.add(new Bishop(this.white, pr + i*dir[0], pc + i*dir[1], this));
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
                            moves.add(new Bishop(this.white, pr + i*dir[0], pc + i*dir[1], this));
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

        // how many squares does the bishop control
        if (this.moves!= null && this.moves.size() > 0) {
            eval += minmax*this.moves.size();
        }

        // do we have the bishop pair
        boolean bishopPair = true;
        if (this.white && board.capturedPiecesWhite!=null) {
            for (Piece p : board.capturedPiecesWhite) {
                if (p != null && p.type == PieceType.BISHOP) {
                    bishopPair = false;
                }
            }
        }
        else if (!this.white && board.capturedPiecesBlack!=null){
            for (Piece p : board.capturedPiecesBlack) {
                if (p != null && p.type == PieceType.BISHOP) {
                    bishopPair = false;
                }
            }
        }

        if (bishopPair) {
            eval += minmax*2;
        }

        if (!this.white) {
            Collections.reverse(Arrays.asList(idealSquares));
        }
        // points for the piece being on its most effective square
        eval += minmax*idealSquares[pr][pc];

        return eval;
    }
}
