package Pieces;

import Board.BoardState;

import java.util.ArrayList;

public class Rook extends Piece{

    public Rook(boolean color, int[] pos, char letter){
        super(color, pos, letter);
        moves= new ArrayList<>();
    }

    @Override
    public ArrayList<Piece> getMoves(BoardState board) {
        moves.clear();

        int pr=this.position[0];
        int pc=this.position[1];

        //Check Down Moves
        for(int i=1; i<8;i++){
            //Check if move is in bounds
            if(!inBounds(pr+i,pc)) break;
            //If no piece add move
            if (board.theBoard[pr + i][pc] == null) {
                moves.add(new Rook(this.white, new int[]{pr + i, pc}, this.pieceLetter));
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
                        moves.add(new Rook(this.white, new int[]{pr + i, pc}, this.pieceLetter));
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
                        moves.add(new Rook(this.white, new int[]{pr + i, pc}, this.pieceLetter));
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
                moves.add(new Rook(this.white, new int[]{pr - i, pc}, this.pieceLetter));
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
                        moves.add(new Rook(this.white, new int[]{pr - i, pc}, this.pieceLetter));
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
                        moves.add(new Rook(this.white, new int[]{pr - i, pc}, this.pieceLetter));
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
                moves.add(new Rook(this.white, new int[]{pr, pc- i}, this.pieceLetter));
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
                        moves.add(new Rook(this.white, new int[]{pr, pc - i}, this.pieceLetter));
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
                        moves.add(new Rook(this.white, new int[]{pr, pc - i}, this.pieceLetter));
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
                moves.add(new Rook(this.white, new int[]{pr, pc+ i}, this.pieceLetter));
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
                        moves.add(new Rook(this.white, new int[]{pr, pc + i}, this.pieceLetter));
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
                        moves.add(new Rook(this.white, new int[]{pr, pc + i}, this.pieceLetter));
                        break;
                    }
                }
            }
        }

        return moves;
    }
}
