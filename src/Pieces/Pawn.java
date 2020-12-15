package Pieces;

import Board.BoardState;

import java.util.ArrayList;

public class Pawn extends Piece {

    boolean isAttacking=false;
    int value = this.white ? 100 : -100;
    int[][] idealSquares = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {1, 5, 2, 2, 2, 2, 5, 1},
            {5, 0, 5, 7, 7, 2, 3, 5},
            {5, 0, 5, 7, 7, 2, 3, 5},
            {1, 5, 2, 2, 2, 2, 5, 1},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
    };

    public Pawn(boolean color, int r, int c, Piece p){
        super(color, r, c, p);
        if (p != null) {
            this.makeCopy(p);
        }
        else {
            this.pieceLetter=color?'p':'P';
            type=PieceType.PAWN;
        }

    }

    public Piece promote(boolean color, int r, int c, int choice){
        Piece promotedPiece = null;

        switch (choice) {
            case 0:
                promotedPiece = new Queen(color, r, c, this);
                promotedPiece.type = PieceType.QUEEN;
                promotedPiece.pieceLetter = this.white ? 'q' : 'Q';
                break;
            case 1:
                promotedPiece = new Rook(color, r, c, this);
                promotedPiece.type = PieceType.ROOK;
                promotedPiece.pieceLetter = this.white ? 'r' : 'R';
                break;
            case 2:
                promotedPiece = new Knight(color, r, c, this);
                promotedPiece.type = PieceType.KNIGHT;
                promotedPiece.pieceLetter = this.white ? 'n' : 'N';
                break;
            case 3:
                promotedPiece = new Bishop(color, r, c, this);
                promotedPiece.type = PieceType.BISHOP;
                promotedPiece.pieceLetter = this.white ? 'b' : 'B';
                break;
            default:
                promotedPiece = new Queen(color, r, c, this);
                promotedPiece.type = PieceType.QUEEN;
                promotedPiece.pieceLetter = this.white ? 'q' : 'Q';
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
                moves.add(new Pawn(this.white, pr-1, pc, this));
                if(inBounds(pr-2,pc) && this.hasMoved==false && board.theBoard[pr-2][pc]==null){
                    moves.add(new Pawn(this.white, pr-2, pc, this));
                }
            }

        }
        //Black
        else{
            if(inBounds(pr+1,pc) && board.theBoard[pr+1][pc]==null){
                moves.add(new Pawn(this.white, pr+1, pc, this));
                if(inBounds(pr+2,pc) && this.hasMoved==false && board.theBoard[pr+2][pc]==null){
                    moves.add(new Pawn(this.white, pr+2, pc, this));
                }
            }
        }

        //White Attack
        if(board.whiteToMove){
            //Diagonal Left
            if(inBounds(pr-1,pc-1)) {
                // normal attack
                if (board.theBoard[pr - 1][pc - 1] != null && !board.theBoard[pr - 1][pc - 1].white) {
                    Pawn pawn=new Pawn(this.white, pr - 1, pc - 1, this);
                    pawn.isAttacking=true;
                    moves.add(pawn);
                }
                // enPassant
                else if (board.enPassant) {
                    if (this.row == board.enPassantRow && this.col-1 == board.enPassantCol) {
                        Pawn pawn=new Pawn(this.white, pr - 1, pc - 1, this);
                        pawn.isAttacking=true;
                        moves.add(pawn);
                    }
                }
            }
            //Diagonal Right
            if(inBounds(pr-1,pc+1)) {
                // normal attack
                if (board.theBoard[pr - 1][pc + 1] != null && !board.theBoard[pr - 1][pc + 1].white) {
                    Pawn pawn=new Pawn(this.white, pr - 1, pc + 1, this);
                    pawn.isAttacking=true;
                    moves.add(pawn);
                }
                // enPassant
                else if (board.enPassant) {
                    if (this.row == board.enPassantRow && this.col+1 == board.enPassantCol) {
                        Pawn pawn=new Pawn(this.white, pr - 1, pc + 1, this);
                        pawn.isAttacking=true;
                        moves.add(pawn);
                    }
                }
            }
        }
        //Black Attack
        else{
            //Diagonal Left
            if(inBounds(pr+1,pc-1)) {
                if (board.theBoard[pr + 1][pc - 1] != null && board.theBoard[pr + 1][pc - 1].white) {
                    Pawn pawn=new Pawn(this.white, pr + 1, pc - 1, this);
                    pawn.isAttacking=true;
                    moves.add(pawn);
                }
                // enPassant
                else if (board.enPassant) {
                    if (this.row == board.enPassantRow && this.col-1 == board.enPassantCol) {
                        Pawn pawn=new Pawn(this.white, pr + 1, pc - 1, this);
                        pawn.isAttacking=true;
                        moves.add(pawn);
                    }
                }
            }
            //Diagonal Right
            if(inBounds(pr+1,pc+1)){
                if(board.theBoard[pr+1][pc+1]!=null && board.theBoard[pr + 1][pc + 1].white){
                    Pawn pawn=new Pawn(this.white, pr + 1, pc + 1, this);
                    pawn.isAttacking=true;
                    moves.add(pawn);
                }
                // enPassant
                else if (board.enPassant) {
                    if (this.row == board.enPassantRow && this.col+1 == board.enPassantCol) {
                        Pawn pawn=new Pawn(this.white, pr + 1, pc + 1, this);
                        pawn.isAttacking=true;
                        moves.add(pawn);
                    }
                }
            }

        }
        return moves;
    }

    @Override
    public int evaluatePiece(BoardState board) {

        int eval = 0;
        int minmax = this.white ? 1 : -1;
        int dir = this.white ? -1 : 1;
        int pr = this.row;
        int pc = this.col;

        // how far has the pawn advanced
        eval += minmax*(this.white ? 6 - pr : 1 - pr);

        // points for the piece being on its most effective square
        eval += minmax*idealSquares[pr][pc];

        // is this a central pawn or flank pawn
        if (pc == 3 || pc == 4) {
            eval += minmax*2;
        }
        else if (pc == 0 || pc == 7) {
            eval += minmax*1;
        }

        // is the pawn protecting another pawn/piece
        // left column
        if (inBounds(pr+dir, pc-1)) {
            if (board.theBoard[pr+dir][pc-1] != null && (this.white == board.theBoard[pr+dir][pc-1].white)) {
                eval += minmax*1;
                if (board.theBoard[pr+dir][pc-1].type == PieceType.PAWN) {
                    eval += minmax*1;
                }
            }
        }
        // check right column
        if (inBounds(pr+dir, pc+1)){
            if (board.theBoard[pr+dir][pc+1] != null && (this.white == board.theBoard[pr+dir][pc+1].white)) {
                eval += minmax*1;
                if (board.theBoard[pr+dir][pc+1].type == PieceType.PAWN) {
                    eval += minmax*1;
                }
            }
        }

        // is the pawn attacking an enemy piece
        if (this.isAttacking) {
            eval += minmax*2;
        }

        return this.value + eval;
    }

    public boolean getAttacking(){
        return isAttacking;
    }

}
