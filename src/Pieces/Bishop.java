package Pieces;

import Board.BoardState;

import java.util.ArrayList;

public class Bishop extends Piece {

    // bishops are generally better than knights, hence the extra 5 points
    int value = this.white ? 35 : -35;

    public Bishop(boolean color, int r, int c){
        super(color, r, c);
        type=PieceType.BISHOP;
        this.pieceLetter=color?'b':'B';
        moves= new ArrayList<>();

    }

    @Override
    public ArrayList<Piece> getMoves(BoardState board) {
        moves.clear();

        int pr=this.row;
        int pc=this.col;

        int[][] directions = {
                {1, 1},
                {-1, 1},
                {1, -1},
                {-1, -1},
        };

        for (int[] dir : directions) {
            //Check Upward Right Diagonal
            for(int i=1; i<8;i++){
                //Check if move is in bounds
                if(!inBounds(pr + i*dir[0],pc + i*dir[1])) break;
                //If no piece add move
                if (board.theBoard[pr + i*dir[0]][pc + i*dir[1]] == null) {
                    moves.add(new Bishop(this.white, pr + i*dir[0], pc + i*dir[1]));
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
                            moves.add(new Bishop(this.white, pr + i*dir[0], pc + i*dir[1]));
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
                            moves.add(new Bishop(this.white, pr + i*dir[0], pc + i*dir[1]));
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

        // is the bishop on a long diagonal
        // how many squares is the bishop controlling
        // is the bishop pointing towards the enemy king
        // do we have the bishop pair

        return 0;
    }
}
