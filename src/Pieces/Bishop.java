package Pieces;

import Board.BoardState;

import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(boolean color, int r, int c, char letter){
        super(color, r, c, letter);
        moves= new ArrayList<>();
    }

    @Override
    public ArrayList<Piece> getMoves(BoardState board) {
        moves.clear();

        int pr=this.row;
        int pc=this.col;

        //Check Upward Right Diagonal
        for(int i=1; i<8;i++){
            //Check if move is in bounds
            if(!inBounds(pr-i,pc+i)) break;
            //If no piece add move
            if (board.theBoard[pr - i][pc + i] == null) {
                moves.add(new Bishop(this.white, pr - i, pc+i, this.pieceLetter));
            }
            //If piece encounter add move if opposite color
            if (board.theBoard[pr - i][pc+i] != null) {
                // attempt to capture piece as white
                if (board.whiteToMove) {
                    // white piece encountered
                    if (board.theBoard[pr - i][pc+i].white) {
                        break;
                    }
                    else {
                        moves.add(new Bishop(this.white, pr - i, pc+i, this.pieceLetter));
                        break;
                    }
                }
                // attempt to capture piece as black
                else {
                    // black piece encountered
                    if (!board.theBoard[pr - i][pc+i].white) {
                        break;
                    }
                    else {
                        moves.add(new Bishop(this.white, pr - i, pc+i, this.pieceLetter));
                        break;
                    }
                }
            }
        }

        //Check Upward Left Diagonal
        for(int i=1; i<8;i++){
            //Check if move is in bounds
            if(!inBounds(pr-i,pc-i)) break;
            //If no piece add move
            if (board.theBoard[pr - i][pc-i] == null) {
                moves.add(new Bishop(this.white, pr - i, pc-i, this.pieceLetter));
            }
            //If piece encounter add move if opposite color
            if (board.theBoard[pr - i][pc-i] != null) {
                // attempt to capture piece as white
                if (board.whiteToMove) {
                    // white piece encountered
                    if (board.theBoard[pr - i][pc-i].white) {
                        break;
                    }
                    else {
                        moves.add(new Bishop(this.white, pr - i, pc-i, this.pieceLetter));
                        break;
                    }
                }
                // attempt to capture piece as black
                else {
                    // black piece encountered
                    if (!board.theBoard[pr - i][pc-i].white) {
                        break;
                    }
                    else {
                        moves.add(new Bishop(this.white, pr - i, pc-i, this.pieceLetter));
                        break;
                    }
                }
            }
        }

        //Check Downward Right Diagonal
        for(int i=1; i<8;i++){
            //Check if move is in bounds
            if(!inBounds(pr+i,pc+i)) break;
            //If no piece add move
            if (board.theBoard[pr+i][pc + i] == null) {
                moves.add(new Bishop(this.white, pr+i, pc+i, this.pieceLetter));
            }
            //If piece encounter add move if opposite color
            if (board.theBoard[pr+i][pc+i] != null) {
                // attempt to capture piece as white
                if (board.whiteToMove) {
                    // white piece encountered
                    if (board.theBoard[pr+i][pc+i].white) {
                        break;
                    }
                    else {
                        moves.add(new Bishop(this.white, pr+i, pc+i, this.pieceLetter));
                        break;
                    }
                }
                // attempt to capture piece as black
                else {
                    // black piece encountered
                    if (!board.theBoard[pr+i][pc+i].white) {
                        break;
                    }
                    else {
                        moves.add(new Bishop(this.white, pr+i, pc+i, this.pieceLetter));
                        break;
                    }
                }
            }
        }

        //Check Downward Left Diagonal
        for(int i=1; i<8;i++){
            //Check if move is in bounds
            if(!inBounds(pr+i,pc-i)) break;
            //If no piece add move
            if (board.theBoard[pr+i][pc-i] == null) {
                moves.add(new Bishop(this.white, pr+i, pc-i, this.pieceLetter));
            }
            //If piece encounter add move if opposite color
            if (board.theBoard[pr+i][pc-i] != null) {
                // attempt to capture piece as white
                if (board.whiteToMove) {
                    // white piece encountered
                    if (board.theBoard[pr+i][pc- i].white) {
                        break;
                    }
                    else {
                        moves.add(new Bishop(this.white, pr+i, pc-i, this.pieceLetter));
                        break;
                    }
                }
                // attempt to capture piece as black
                else {
                    // black piece encountered
                    if (!board.theBoard[pr+i][pc-i].white) {
                        break;
                    }
                    else {
                        moves.add(new Bishop(this.white, pr+i, pc-i, this.pieceLetter));
                        break;
                    }
                }
            }
        }

        return moves;
    }
}
