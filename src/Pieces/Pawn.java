package Pieces;

import Board.BoardState;

public class Pawn extends Piece {

    public Pawn(boolean color, int[] pos, char letter){
        super(color, pos, letter);
    }

    @Override
    public boolean checkMove(BoardState board, int[] startPos, int[] endPos) {

        if (startPos[0]==-9999||startPos[1]==-9999||endPos[0]==-9999||endPos[1]==-9999) return false;

        if(Math.abs(endPos[0]-startPos[0])>2) return false;

        //Pawn is in Initial State
        if(Math.abs(startPos[0]-endPos[0])==2 && !hasMoved){
            if(board.whiteToMove) {
                //No backwards moves
                if(startPos[0] < endPos[0]) return false;
                if(board.theBoard[startPos[0]-1][startPos[1]]==null) {
                    if(board.theBoard[startPos[0]-2][startPos[1]]==null){
                        try {
                            if (board.theBoard[endPos[0]][endPos[1] + 1].pieceLetter == 'P'){
                                board.enPassant=true;
                            }
                        }
                        catch(Exception e){}
                        try {
                            if(board.theBoard[endPos[0]][endPos[1]-1].pieceLetter=='P'){
                                board.enPassant=true;
                            }
                        }
                        catch(Exception e){}

                        return true;
                    }
                }
            }
            else{
                //No backwards moves
                if(startPos[0] > endPos[0]) return false;
                if(board.theBoard[startPos[0]+1][startPos[1]]==null) {
                    if(board.theBoard[startPos[0]+2][startPos[1]]==null){
                        try {
                            if (board.theBoard[endPos[0]][endPos[1] + 1].pieceLetter == 'p'){
                                board.enPassant=true;
                            }
                        }
                        catch(Exception e){}
                        try {
                            if(board.theBoard[endPos[0]][endPos[1]-1].pieceLetter=='p'){
                                board.enPassant=true;
                            }
                        }
                        catch(Exception e){}

                        return true;
                    }
                }
            }
        }

        //Pawn isnt in Initial State
        if(Math.abs(startPos[0]-endPos[0])==1){
            if(board.whiteToMove) {
                //No backwards moves
                if(startPos[0] < endPos[0]) return false;
                //Attacking
                if (Math.abs(startPos[1]-endPos[1])==1) {
                    int colAdjustment = startPos[1]-endPos[1];
                    if(board.theBoard[startPos[0]-1][startPos[1]-colAdjustment]!=null &&
                            !board.theBoard[startPos[0]-1][startPos[1]-colAdjustment].white||board.enPassant) return true;
                }
                //Move Forward
                else {
                    if(board.theBoard[startPos[0]-1][startPos[1]]==null) return true;
                }

            }
            else{
                //No backwards moves
                if(startPos[0] > endPos[0]) return false;
                //Attacking
                if (Math.abs(startPos[1]-endPos[1])==1) {
                    int colAdjustment = startPos[1]-endPos[1];
                    if(board.theBoard[startPos[0]+1][startPos[1]-colAdjustment]!=null &&
                            board.theBoard[startPos[0]+1][startPos[1]-colAdjustment].white||board.enPassant) return true;
                }
                //Move Forward
                else {
                    if(board.theBoard[startPos[0]+1][startPos[1]]==null) return true;
                }
            }
        }

        return false;
    }


}
