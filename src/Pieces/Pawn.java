package Pieces;

import Board.BoardState;

public class Pawn extends Piece {

    public boolean hasMoved=false;

    public Pawn(boolean color, char[] pos, char letter){
        super(color, pos, letter);
    }

    @Override
    public boolean checkMove(BoardState board, char[] startPos, char[] endPos) {

        int[] startSpot= convertNotation(startPos);
        int[] endSpot= convertNotation(endPos);

        if (startSpot[0]==-9999||startSpot[1]==-9999||endSpot[0]==-9999||endSpot[1]==-9999) return false;

        if(Math.abs(endPos[1]-startPos[1])>2) return false;

        //Pawn is in Initial State
        if(Math.abs(startPos[1]-endPos[1])==2 && !hasMoved){
            if(board.whiteToMove) {
                //No backwards moves
                if(startSpot[0] < endSpot[0]) return false;
                if(board.theBoard[startSpot[0]-1][startSpot[1]]==null) {
                    if(board.theBoard[startSpot[0]-2][startSpot[1]]==null){
                        board.enPassant=true;
                        return true;
                    }
                }
            }
            else{
                //No backwards moves
                if(startSpot[0] > endSpot[0]) return false;
                if(board.theBoard[startSpot[0]+1][startSpot[1]]==null) {
                    if(board.theBoard[startSpot[0]+2][startSpot[1]]==null){
                        board.enPassant=true;
                        return true;
                    }
                }
            }
        }

        //Pawn isnt in Initial State
        if(Math.abs(startPos[1]-endPos[1])==1){
            if(board.whiteToMove) {
                //No backwards moves
                if(startSpot[0] < endSpot[0]) return false;
                //Attacking
                if (Math.abs(startSpot[1]-endSpot[1])==1) {
                    int colAdjustment = startSpot[1]-endSpot[1];
                    if((board.theBoard[startSpot[0]-1][startSpot[1]-colAdjustment]!=null)||board.enPassant) return true;
                }
                //Move Forward
                else {
                    if(board.theBoard[startSpot[0]-1][startSpot[1]]==null) return true;
                }

            }
            else{
                //No backwards moves
                if(startSpot[0] > endSpot[0]) return false;
                //Attacking
                if (Math.abs(startSpot[1]-endSpot[1])==1) {
                    int colAdjustment = startSpot[1]-endSpot[1];
                    if(board.theBoard[startSpot[0]+1][startSpot[1]-colAdjustment]!=null||board.enPassant) return true;
                }
                //Move Forward
                else {
                    if(board.theBoard[startSpot[0]+1][startSpot[1]]==null) return true;
                }
            }
        }

        return false;
    }


}
