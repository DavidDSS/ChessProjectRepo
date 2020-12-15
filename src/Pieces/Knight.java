package Pieces;

import Board.BoardState;

import java.util.ArrayList;

public class Knight extends Piece {

    int value = this.white ? 30 : -30;
    int[][] idealSquares = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 5, 0, 0},
            {0, 5, 0, 5, 5, 0, 5, 0},
            {0, 5, 0, 5, 5, 0, 5, 0},
            {0, 0, 0, 0, 0, 5, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
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

        // how many potential moves does the knight have
        // is the knight in a good central square
        // does the knight attack multiple pieces, if so which ones ?
        // can we develop this piece ?
        int eval = 0;
        int pr = this.row;
        int pc = this.col;
        int minmax = this.white ? 1 : -1;

        // points for piece development
        if (this.hasMoved) {
            eval += minmax*5;
        }

        // points for the piece being on its most effective square
        eval += minmax*idealSquares[this.row][this.col];

        return this.value + eval;
    }
}
