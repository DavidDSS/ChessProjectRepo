package Pieces;

import Board.BoardState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 Jason Grightmire jg12jg (5388327)
 David Saldana Suarez ds16vx (6155964)
 COSC 3P71
 Chess Project
 */

public class King extends Piece {

    boolean[][] attackedByPiece = new boolean[8][8];
    int value = this.white ? 9000 : -9000;
    int[][] idealSquares = {
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-20, -30, -30, -40, -40, -30, -30, -20},
            {-10, -20, -20, -20, -20, -20, -20, -10},
            {20,  20,   0,   0,   0,   0,  20,  20},
            {20,  30,  10,   0,   0,  10,  30,  20}
    };

    public King(boolean color, int r, int c, Piece p){
        super(color, r, c, p);
        if (p != null) {
            this.makeCopy(p);
        }
        else {
            type=PieceType.KING;
            this.pieceLetter=color?'k':'K';
        }
    }

    public ArrayList<Piece> getMovesNotInCheck (BoardState board, ArrayList<Piece> moves) {

        // check all attacked squares by enemy pieces
        for(int r=0; r<8; r++){
            for(int c=0; c<8;c++){
                if(board.theBoard[r][c]!=null && (board.whiteToMove!=board.theBoard[r][c].white)) {

                    ArrayList<Piece> enemyMoves = board.theBoard[r][c].getMoves(board);
                    for(Piece p : enemyMoves){
                        //All Moves for the current player's pieces (white or black)
                        //Pawns only attack diagonally
                        if(p.type==PieceType.PAWN){
                            if(Math.abs(p.col-c)==1){
                                attackedByPiece[p.row][p.col]=true;
                            }
                        }
                        //All other pieces attack on end position
                        else{
                            attackedByPiece[p.row][p.col]=true;
                        }
                    }
                }
            }
        }

        // check if king has a legal move
        ArrayList<Piece> kingMoves= new ArrayList<>();
        for (Piece m : moves) {

            // king can not castle through check

            // queenside castles
            if ((m.col-this.col)==-2) {
                if(inBounds(this.row,m.col)) {
                    if (!attackedByPiece[this.row][m.col] && !attackedByPiece[this.row][m.col + 1]) {
                        kingMoves.add(m);
                    }
                }
            }
            // kingside castles
            else if ((m.col-this.col)==2) {
                if(inBounds(this.row,m.col)) {
                    if (!attackedByPiece[this.row][m.col] && !attackedByPiece[this.row][m.col - 1]) {
                        kingMoves.add(m);
                    }
                }
            }
            // add move if square not attacked by a piece
            else if (!attackedByPiece[m.row][m.col]) {
                kingMoves.add(m);
            }
        }

        return kingMoves;
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

        //For possible move check if square is empty or if attacking piece
        for(int[] s:spaces){
            if(inBounds(pr+s[0], pc+s[1])) {
                if (board.theBoard[pr + s[0]][pc + s[1]] == null) {
                    moves.add(new King(this.white, pr + s[0], pc + s[1], this));
                }
                else{
                    //If the king is trying to attack check piece color
                    if(board.whiteToMove) {
                        if (!board.theBoard[pr + s[0]][pc + s[1]].white){
                            moves.add(new King(this.white, pr + s[0], pc + s[1], this));
                        }
                    }
                    else{
                        if (board.theBoard[pr + s[0]][pc + s[1]].white){
                            moves.add(new King(this.white, pr + s[0], pc + s[1], this));
                        }
                    }
                }
            }
        }

        if(board.whiteToMove == this.white) {
            // check if castling is possible for the white or black king
            boolean kingSideCastle = true;
            boolean queenSideCastle = true;
            int row = board.whiteToMove ? 7 : 0;

            for (int i = 0; i < 8; i++) {
                // check if a file rook has moved
                if (i == 0) {
                    // if the rook is in initial position, but has moved, no castle
                    if (board.theBoard[row][i] == null) {
                        queenSideCastle = false;
                    }
                    else if (board.theBoard[row][i] != null && board.theBoard[row][i].hasMoved) {
                        queenSideCastle = false;
                    }
                } else if (i >= 1 && i <= 3) {
                    if (board.theBoard[row][i] != null) {
                        queenSideCastle = false;
                    }
                }
                // check if king has moved
                else if (i == 4) {
                    // if the king is in initial position, but has moved, no castle
                    if (board.theBoard[row][i] != null && board.theBoard[row][i].hasMoved) {
                        kingSideCastle = false;
                        queenSideCastle = false;
                    }
                } else if (i >= 5 && i <= 6) {
                    if (board.theBoard[row][i] != null) {
                        kingSideCastle = false;
                    }
                }
                // check if h file rook has moved
                else if (i == 7) {
                    // if the rook is in initial position, but has moved, no castle
                    if (board.theBoard[row][i] == null) {
                        kingSideCastle = false;
                    }
                    else if (board.theBoard[row][i] != null && board.theBoard[row][i].hasMoved) {
                        kingSideCastle = false;
                    }
                }
            }
            if (kingSideCastle) {
                moves.add(new King(this.white, pr, pc + 2, this));
            }
            if (queenSideCastle) {
                moves.add(new King(this.white, pr, pc - 2, this));
            }

            // double check moves to make sure king does not walk into check
            moves = getMovesNotInCheck(board, moves);
        }

        return moves;
    }

    @Override
    public int evaluatePiece(BoardState board) {

        // has the king castled yet
        // is the king safe in corner of the board
        // can the king attack a piece, how many pieces are on the board ?
        int eval = 0;
        int pr = this.row;
        int pc = this.col;
        int minmax = this.white ? 1 : -1;

        // how many escape squares does the king have
        if (this.moves!= null && this.moves.size() > 0) {
            eval += minmax*this.moves.size()*10;
        }

        if (!this.white) {
            Collections.reverse(Arrays.asList(idealSquares));
        }
        // points for the piece being on its most effective square
        eval += minmax*idealSquares[pr][pc];

        return eval;
    }

    @Override
    public int getPieceValue () {
        return this.value;
    }

}
