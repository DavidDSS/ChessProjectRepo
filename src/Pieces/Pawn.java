package Pieces;

import Board.BoardState;
import javafx.beans.binding.ObjectExpression;

import java.util.ArrayList;

public class Pawn extends Piece {

    boolean isAttacking=false;

    public Pawn(boolean color, int r, int c){
        super(color, r, c);
        this.pieceLetter=color?'p':'P';
        type=PieceType.PAWN;
        moves= new ArrayList<>();

    }

    public Piece promote(boolean color, int r, int c, int choice){
        Piece promotedPiece = null;

        switch (choice) {
            case 0:
                promotedPiece = new Queen(color, r, c);
                break;
            case 1:
                promotedPiece = new Rook(color, r, c);
                break;
            case 2:
                promotedPiece = new Knight(color, r, c);
                break;
            case 3:
                promotedPiece = new Bishop(color, r, c);
                break;
            default:
                promotedPiece = new Queen(color, r, c);
                break;
        }

        return promotedPiece;
    }

    @Override
    public ArrayList<Piece> getMoves(BoardState board) {
        moves.clear();

        int pr=this.row;
        int pc=this.col;

        //White
        if(board.whiteToMove){
            if(inBounds(pr-1,pc) && board.theBoard[pr-1][pc]==null){
                moves.add(new Pawn(this.white, pr-1, pc));
                if(this.hasMoved==false && board.theBoard[pr-2][pc]==null){
                    moves.add(new Pawn(this.white, pr-2, pc));
                }
            }

        }
        //Black
        else{
            if(inBounds(pr+1,pc) && board.theBoard[pr+1][pc]==null){
                moves.add(new Pawn(this.white, pr+1, pc));
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
                    Pawn pawn=new Pawn(this.white, pr - 1, pc - 1);
                    pawn.isAttacking=true;
                    moves.add(pawn);
                }
            }
            //Diagonal Right
            if(inBounds(pr-1,pc+1)) {
                if (board.theBoard[pr - 1][pc + 1] != null && !board.theBoard[pr - 1][pc + 1].white || board.enPassant) {
                    Pawn pawn=new Pawn(this.white, pr - 1, pc + 1);
                    pawn.isAttacking=true;
                    moves.add(pawn);
                }
            }
        }
        //Black Attack
        else{
            //Diagonal Left
            if(inBounds(pr+1,pc-1)) {
                if (board.theBoard[pr + 1][pc - 1] != null && board.theBoard[pr + 1][pc - 1].white || board.enPassant) {
                    Pawn pawn=new Pawn(this.white, pr + 1, pc - 1);
                    pawn.isAttacking=true;
                    moves.add(pawn);
                }
            }
            //Diagonal Right
            if(inBounds(pr+1,pc+1)){
                if(board.theBoard[pr+1][pc+1]!=null && board.theBoard[pr + 1][pc + 1].white || board.enPassant){
                    Pawn pawn=new Pawn(this.white, pr + 1, pc + 1);
                    pawn.isAttacking=true;
                    moves.add(pawn);
                }
            }

        }
        return moves;
    }

    public boolean getAttacking(){
        return isAttacking;
    }


}
