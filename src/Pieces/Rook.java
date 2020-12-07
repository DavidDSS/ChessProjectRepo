package Pieces;

import Board.BoardState;

import java.util.ArrayList;

public class Rook extends Piece{

    public Rook(boolean color, int r, int c){
        super(color, r, c);
        type=PieceType.ROOK;
        this.pieceLetter=color?'r':'R';
        moves= new ArrayList<>();

    }

    @Override
    public ArrayList<Piece> getMoves(BoardState board) {
        moves.clear();

        int pr=this.row;
        int pc=this.col;

        int[][] directions = {
                {1, 0},
                {-1, 0},
                {0, -1},
                {0, 1},
        };

        //check possible moves for each direction up, down, left, right
        for (int[] dir : directions) {
            for(int i=1; i<8;i++){
                //Check if move is in bounds
                if(!inBounds(pr + i*dir[0],pc + i*dir[1])) break;
                //If no piece add move
                if (board.theBoard[pr + i*dir[0]][pc + i*dir[1]] == null) {
                    moves.add(new Rook(this.white, pr + i*dir[0], pc + i*dir[1]));
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
                            moves.add(new Rook(this.white, pr + i*dir[0], pc + i*dir[1]));
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
                            moves.add(new Rook(this.white, pr + i*dir[0], pc + i*dir[1]));
                            break;
                        }
                    }
                }
            }
        }

        return moves;
    }
}
