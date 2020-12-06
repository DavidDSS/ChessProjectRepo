package Pieces;

import Board.BoardState;

import java.util.ArrayList;

public class Rook extends Piece{

    public Rook(boolean color, int r, int c, char letter){
        super(color, r, c, letter);
        moves= new ArrayList<>();
    }

    @Override
    public ArrayList<Piece> getMoves(BoardState board) {
        moves.clear();

        int pr=this.row;
        int pc=this.col;

        //Check Down Moves
        for(int i=1; i<8;i++){
            //Check if move is in bounds
            if(!inBounds(pr+i,pc)) break;
            //If no piece add move
            if (board.theBoard[pr + i][pc] == null) {
                moves.add(new Rook(this.white, pr + i, pc, this.pieceLetter));
            }
            //If piece encounter add move if opposite color
            if (board.theBoard[pr + i][pc] != null) {
                // attempt to capture piece as white
                if (board.whiteToMove) {
                    // white piece encountered
                    if (board.theBoard[pr + i][pc].white) {
                        break;
                    }
                    else {
                        moves.add(new Rook(this.white, pr + i, pc, this.pieceLetter));
                        break;
                    }
                }
                // attempt to capture piece as black
                else {
                    // black piece encountered
                    if (!board.theBoard[pr + i][pc].white) {
                        break;
                    }
                    else {
                        moves.add(new Rook(this.white, pr + i, pc, this.pieceLetter));
                        break;
                    }
                }
            }
        }

        //Check Up Moves
        for(int i=1; i<8;i++){
            //Check if move is in bounds
            if(!inBounds(pr-i,pc)) break;
            //If no piece add move
            if (board.theBoard[pr - i][pc] == null) {
                moves.add(new Rook(this.white, pr - i, pc, this.pieceLetter));
            }
            //If piece encounter add move if opposite color
            if (board.theBoard[pr - i][pc] != null) {
                // attempt to capture piece as white
                if (board.whiteToMove) {
                    // white piece encountered
                    if (board.theBoard[pr - i][pc].white) {
                        break;
                    }
                    else {
                        moves.add(new Rook(this.white, pr - i, pc, this.pieceLetter));
                        break;
                    }
                }
                // attempt to capture piece as black
                else {
                    // black piece encountered
                    if (!board.theBoard[pr - i][pc].white) {
                        break;
                    }
                    else {
                        moves.add(new Rook(this.white, pr - i, pc, this.pieceLetter));
                        break;
                    }
                }
            }
        }

        //Check Left Moves
        for(int i=1; i<8;i++){
            //Check if move is in bounds
            if(!inBounds(pr,pc-i)) break;
            //If no piece add move
            if (board.theBoard[pr][pc - i] == null) {
                moves.add(new Rook(this.white, pr, pc- i, this.pieceLetter));
            }
            //If piece encounter add move if opposite color
            if (board.theBoard[pr][pc - i] != null) {
                // attempt to capture piece as white
                if (board.whiteToMove) {
                    // white piece encountered
                    if (board.theBoard[pr][pc - i].white) {
                        break;
                    }
                    else {
                        moves.add(new Rook(this.white, pr, pc - i, this.pieceLetter));
                        break;
                    }
                }
                // attempt to capture piece as black
                else {
                    // black piece encountered
                    if (!board.theBoard[pr][pc - i].white) {
                        break;
                    }
                    else {
                        moves.add(new Rook(this.white, pr, pc - i, this.pieceLetter));
                        break;
                    }
                }
            }
        }

        //Check Right Moves
        for(int i=1; i<8;i++){
            //Check if move is in bounds
            if(!inBounds(pr,pc+i)) break;
            //If no piece add move
            if (board.theBoard[pr][pc+ i] == null) {
                moves.add(new Rook(this.white, pr, pc+ i, this.pieceLetter));
            }
            //If piece encounter add move if opposite color
            if (board.theBoard[pr][pc+ i] != null) {
                // attempt to capture piece as white
                if (board.whiteToMove) {
                    // white piece encountered
                    if (board.theBoard[pr][pc + i].white) {
                        break;
                    }
                    else {
                        moves.add(new Rook(this.white, pr, pc + i, this.pieceLetter));
                        break;
                    }
                }
                // attempt to capture piece as black
                else {
                    // black piece encountered
                    if (!board.theBoard[pr][pc + i].white) {
                        break;
                    }
                    else {
                        moves.add(new Rook(this.white, pr, pc + i, this.pieceLetter));
                        break;
                    }
                }
            }
        }

        return moves;
    }
}
