package Pieces;

import Board.BoardState;

import java.util.ArrayList;

public class Rook extends Piece{

    int value = this.white ? 50 : -50;
    int[][] directions = {
            {1, 0},
            {-1, 0},
            {0, -1},
            {0, 1},
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

        // how many available squares the rook has to move
        // is the rook attacking/defending a piece
        // points for check
//        for (int[] dir : directions) {
//            for (int i = 1; i < 8; i++) {
//                //Check if move is in bounds
//                if(!inBounds(pr + i*dir[0],pc + i*dir[1])) break;
//                // add point for possible move
//                if (board.theBoard[pr + i*dir[0]][pc + i*dir[1]] == null) {
//                    eval += minmax*1;
//                }
//                //If piece encounter add point
//                if (board.theBoard[pr + i*dir[0]][pc + i*dir[1]] != null) {
//                    eval += minmax*1;
//                    // attack puts king in check
//                    if (board.theBoard[pr + i*dir[0]][pc + i*dir[1]].type == PieceType.KING) {
//                        eval += minmax*2;
//                    }
//                    break;
//                }
//            }
//        }
        // how many squares does the rook control
        if (this.moves!= null && this.moves.size() > 0) {
            eval += minmax*this.moves.size();
        }

        return this.value + eval;
    }
}
