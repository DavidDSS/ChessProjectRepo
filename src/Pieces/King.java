package Pieces;

import Board.BoardState;

import java.util.ArrayList;

public class King extends Piece {

    public King(boolean color, int r, int c){
        super(color, r, c);
        type=PieceType.KING;
        this.pieceLetter=color?'k':'K';
        moves= new ArrayList<>();
    }

    @Override
    public ArrayList<Piece> getMoves(BoardState board) {
        moves.clear();

        int pr=this.row;
        int pc=this.col;

        int[][] spaces={
                {1,0},
                {-1,0},
                {1,1},
                {1,-1},
                {-1,1},
                {0,1},
                {0,-1},
                {-1,-1},
        };

        //For possible jump check if square is empty or if attacking piece
        for(int[] s:spaces){
            if(inBounds(pr+s[0], pc+s[1])) {
                if (board.theBoard[pr + s[0]][pc + s[1]] == null) {
                    moves.add(new King(this.white, pr + s[0], pc + s[1]));
                }
                else{
                    //If the knight is trying to attack check piece color
                    if(board.whiteToMove) {
                        if (!board.theBoard[pr + s[0]][pc + s[1]].white){
                            moves.add(new King(this.white, pr + s[0], pc + s[1]));
                        }
                    }
                    else{
                        if (board.theBoard[pr + s[0]][pc + s[1]].white){
                            moves.add(new King(this.white, pr + s[0], pc + s[1]));
                        }
                    }
                }
            }
        }

        // check if castling is possible for the white king
        boolean kingSideCastle = true;
        boolean queenSideCastle = true;
        int row = board.whiteToMove ? 7 : 0;

        for (int i=0; i < 8; i++) {
            // check if a file rook has moved
            if (i==0) {
                // if the rook is in initial position, but has moved, no castle
                if (board.theBoard[row][i]!=null & board.theBoard[row][i].hasMoved) {
                    queenSideCastle = false;
                }
            }
            else if (i >= 1 && i <= 3) {
                if (board.theBoard[row][i]!=null) {
                    queenSideCastle = false;
                }
            }
            // check if king has moved
            else if (i==4) {
                // if the king is in initial position, but has moved, no castle
                if (board.theBoard[row][i]!=null & board.theBoard[row][i].hasMoved) {
                    kingSideCastle = false;
                    queenSideCastle = false;
                }
            }
            else if (i >= 5 && i <= 6) {
                if (board.theBoard[row][i]!=null) {
                    kingSideCastle = false;
                }
            }
            // check if h file rook has moved
            else if (i==7){
                // if the rook is in initial position, but has moved, no castle
                if (board.theBoard[row][i]!=null & board.theBoard[row][i].hasMoved) {
                    kingSideCastle = false;
                }
            }
        }
        if (kingSideCastle) {
            moves.add(new King(this.white, pr, pc + 2));
        }
        if (queenSideCastle) {
            moves.add(new King(this.white, pr, pc - 2));
        }

        return moves;
    }
}
