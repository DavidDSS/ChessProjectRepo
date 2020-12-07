package Pieces;

import Board.BoardState;
import javafx.beans.binding.ObjectExpression;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(boolean color, int r, int c){
        super(color, r, c);
        this.pieceLetter=color?'p':'P';
        type=PieceType.PAWN;
        moves= new ArrayList<>();

    }

    public ArrayList<Piece> promote(boolean color, int r, int c){
        ArrayList<Piece> promotions= new ArrayList<>();

        promotions.add(new Rook(color, r, c));
        promotions.add(new Knight(color, r, c));
        promotions.add(new Bishop(color, r, c));
        promotions.add(new Queen(color, r, c));

        return promotions;
    }

    @Override
    public ArrayList<Piece> getMoves(BoardState board) {
        moves.clear();

        int pr=this.row;
        int pc=this.col;

        //White
        if(board.whiteToMove){
            if(inBounds(pr-1,pc) && board.theBoard[pr-1][pc]==null){
                //Pawn reached end of board
                if((pr-1)==0){
                    promote(this.white, pr-1, pc).forEach(piece -> moves.add(piece));
                }
                else{
                    moves.add(new Pawn(this.white, pr-1, pc));
                }
                if(this.hasMoved==false && board.theBoard[pr-2][pc]==null){
                    moves.add(new Pawn(this.white, pr-2, pc));
                }
            }

        }
        //Black
        else{
            if(inBounds(pr+1,pc) && board.theBoard[pr+1][pc]==null){
                //Pawn reached end of board
                if((pr+1)==7){
                    promote(this.white, pr+1, pc).forEach(piece -> moves.add(piece));
                }
                else{
                    moves.add(new Pawn(this.white, pr+1, pc));
                }
                if(this.hasMoved==false && board.theBoard[pr+2][pc]==null){
                    moves.add(new Pawn(this.white, pr+2, pc));
                }
            }
        }

        //White Attack
        if(board.whiteToMove){
            //Diagonal Left
            if(inBounds(pr-1,pc-1)) {
                if (board.theBoard[pr - 1][pc - 1] != null && !board.theBoard[pr - 1][pc - 1].white || board.enPassant) {
                    //Pawn reached end of board
                    if((pr-1)==0){
                        promote(this.white, pr-1, pc-1).forEach(piece -> moves.add(piece));
                    }
                    else{
                        moves.add(new Pawn(this.white, pr - 1, pc - 1));
                    }
                }
            }
            //Diagonal Right
            if(inBounds(pr-1,pc+1)) {
                if (board.theBoard[pr - 1][pc + 1] != null && !board.theBoard[pr - 1][pc + 1].white || board.enPassant) {
                    //Pawn reached end of board
                    if((pr-1)==0){
                        promote(this.white, pr-1, pc+1).forEach(piece -> moves.add(piece));
                    }
                    else{
                        moves.add(new Pawn(this.white, pr - 1, pc + 1));
                    }
                }
            }
        }
        //Black Attack
        else{
            //Diagonal Left
            if(inBounds(pr+1,pc-1)) {
                if (board.theBoard[pr + 1][pc - 1] != null && board.theBoard[pr + 1][pc - 1].white || board.enPassant) {
                    if((pr+1)==7){
                        promote(this.white, pr+1, pc-1).forEach(piece -> moves.add(piece));
                    }
                    else{
                        moves.add(new Pawn(this.white, pr + 1, pc - 1));
                    }
                }
            }
            //Diagonal Right
            if(inBounds(pr+1,pc+1)){
                if(board.theBoard[pr+1][pc+1]!=null && board.theBoard[pr + 1][pc + 1].white || board.enPassant){
                    if((pr+1)==7){
                        promote(this.white, pr+1, pc+1).forEach(piece -> moves.add(piece));
                    }
                    else{
                        moves.add(new Pawn(this.white, pr+1,pc+1));
                    }
                }
            }

        }
        return moves;
    }


}
