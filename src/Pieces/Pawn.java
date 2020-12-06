package Pieces;

import Board.BoardState;
import javafx.beans.binding.ObjectExpression;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(boolean color, int[] pos, char letter){
        super(color, pos, letter);
        moves= new ArrayList<>();
    }

    @Override
    public ArrayList<Piece> getMoves(BoardState board) {
        moves.clear();

        int pr=this.position[0];
        int pc=this.position[1];

        //White
        if(board.whiteToMove){
            if(inBounds(pr-1,pc) && board.theBoard[pr-1][pc]==null){
                moves.add(new Pawn(this.white, new int[]{pr-1,pc},this.pieceLetter));
                if(this.hasMoved==false && board.theBoard[pr-2][pc]==null){
                    moves.add(new Pawn(this.white, new int[]{pr-2,pc},this.pieceLetter));
                }
            }

        }
        //Black
        else{
            if(inBounds(pr+1,pc) && board.theBoard[pr+1][pc]==null){
                moves.add(new Pawn(this.white, new int[]{pr+1,pc},this.pieceLetter));
                if(this.hasMoved==false && board.theBoard[pr+2][pc]==null){
                    moves.add(new Pawn(this.white, new int[]{pr+2,pc},this.pieceLetter));
                }
            }
        }

        //White Attack
        if(board.whiteToMove){
            //Diagonal Left
            if(inBounds(pr-1,pc-1)) {
                if (board.theBoard[pr - 1][pc - 1] != null && !board.theBoard[pr - 1][pc - 1].white || board.enPassant) {
                    moves.add(new Pawn(this.white, new int[]{pr - 1, pc - 1}, this.pieceLetter));
                }
            }
            //Diagonal Right
            if(inBounds(pr-1,pc+1)) {
                if (board.theBoard[pr - 1][pc + 1] != null && !board.theBoard[pr - 1][pc + 1].white || board.enPassant) {
                    moves.add(new Pawn(this.white, new int[]{pr - 1, pc + 1}, this.pieceLetter));
                }
            }
        }
        //Black Attack
        else{
            //Diagonal Left
            if(inBounds(pr+1,pc-1)) {
                if (board.theBoard[pr + 1][pc - 1] != null && board.theBoard[pr + 1][pc - 1].white || board.enPassant) {
                    moves.add(new Pawn(this.white, new int[]{pr + 1, pc - 1}, this.pieceLetter));
                }
            }
            //Diagonal Right
            if(inBounds(pr+1,pc+1)){
                if(board.theBoard[pr+1][pc+1]!=null && board.theBoard[pr + 1][pc + 1].white || board.enPassant){
                    moves.add(new Pawn(this.white, new int[]{pr+1,pc+1},this.pieceLetter));
                }
            }

        }
        return moves;
    }


}
