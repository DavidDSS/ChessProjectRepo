package Pieces;

import Board.BoardState;

import java.util.ArrayList;

public class Knight extends Piece {

    int value = this.white ? 30 : -30;

    public Knight(boolean color, int r, int c){
        super(color, r, c);
        type=PieceType.KNIGHT;
        this.pieceLetter=color?'n':'N';
        moves= new ArrayList<>();
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
                    moves.add(new Knight(this.white, pr + j[0], pc + j[1]));
                }
                else{
                    //If the knight is trying to attack check piece color
                    if(board.whiteToMove) {
                        if (!board.theBoard[pr + j[0]][pc + j[1]].white){
                            moves.add(new Knight(this.white, pr + j[0], pc + j[1]));
                        }
                    }
                    else{
                        if (board.theBoard[pr + j[0]][pc + j[1]].white){
                            moves.add(new Knight(this.white, pr + j[0], pc + j[1]));
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

        return 0;
    }
}
