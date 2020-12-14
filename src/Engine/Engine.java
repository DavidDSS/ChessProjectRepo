package Engine;

import Board.BoardState;
import Pieces.*;

import java.util.ArrayList;

public class Engine {

    private BoardState position;

    public Engine (BoardState p) {

        // deep copy of board state object
        position = new BoardState(p);

    }

    /*******************************************************************************
     * takes a board position and calculates the best move
     * idea, create all moves and choose the move that produces the best max/min
     *
     */
    public Piece calculateBestMove () {

        double currEval = position.evaluatePosition();
        double newEval;
        Piece bestMove = null;
        ArrayList<Piece> moves;

        // get all possible moves for the player
        if (position.kingInCheck) {
            moves = position.legalMovesKingInCheck();
        }
        else {
            moves = position.getAllPossibleMoves();
        }

        // checkmate or stalemate
        if (moves.size() == 0) {
            return null;
        }
        // some default move
        else {
            bestMove = moves.get(0);
        }

        for (Piece m : moves) {
            BoardState p = new BoardState(this.position);
            p.makeMove(new int[]{m.prevRow, m.prevCol}, new int[]{m.row, m.col});

            // check the evaluation of the move
            newEval = alphabeta(2, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, p);

            // for white we want the maximum value
            if (position.whiteToMove) {
                if (currEval <= newEval) {
                    bestMove = m;
                }
            }
            // for black we want the minimum value
            else {
                if (currEval >= newEval) {
                    bestMove = m;
                }
            }
        }

        // return the best move in the position
        return bestMove;

    } // calculateBestMove

    private double alphabeta (int depth, double alpha, double beta, BoardState currPosition) {

        if (depth==0 || currPosition.gameOver) {
            // max depth reach or game over
            // send back evaluation of the position
            return currPosition.evaluatePosition();
        }

        if (currPosition.whiteToMove) {
            double eval = Double.NEGATIVE_INFINITY;
            ArrayList<Piece> moves = currPosition.getAllPossibleMoves();
            for (Piece m : moves) {
                // create a deep copy of the board state and pieces
                BoardState newPosition = new BoardState(currPosition);
                newPosition.makeMove(new int[]{m.prevRow, m.prevCol}, new int[]{m.row, m.col});
                // continue recursive call
                eval = alphabeta(depth-1, alpha, beta, newPosition);
                alpha = Math.max(alpha, eval);
                // pruning, disregard branch
                if (beta <= alpha) break;
            }
            return eval;
        }
        else {
            double eval = Double.POSITIVE_INFINITY;
            ArrayList<Piece> moves = currPosition.getAllPossibleMoves();
            for (Piece m : moves) {
                // create a deep copy of the board state and pieces
                BoardState newPosition = new BoardState(currPosition);
                // make the move and set the new position
                newPosition.makeMove(new int[]{m.prevRow, m.prevCol}, new int[]{m.row, m.col});
                // continue recursive call
                eval = alphabeta(depth-1, alpha, beta, newPosition);
                beta = Math.min(beta, eval);
                // pruning, disregard branch
                if (beta <= alpha) break;
            }
            return eval;
        }

    } // alphabeta

    public Piece[][] createDeepCopy (Piece[][] board) {

        Piece[][] copy = new Piece[8][8];
        // make deep copy of pieces
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {

                if (board[r][c] != null) {
                    if (board[r][c].type == PieceType.PAWN) {
                        copy[r][c] = new Pawn(board[r][c].white, board[r][c].row, board[r][c].col);
                    }
                    else if (board[r][c].type == PieceType.KING) {
                        copy[r][c] = new King(board[r][c].white, board[r][c].row, board[r][c].col);
                    }
                    else if (board[r][c].type == PieceType.QUEEN) {
                        copy[r][c] = new Queen(board[r][c].white, board[r][c].row, board[r][c].col);
                    }
                    else if (board[r][c].type == PieceType.KNIGHT) {
                        copy[r][c] = new Knight(board[r][c].white, board[r][c].row, board[r][c].col);
                    }
                    else if (board[r][c].type == PieceType.BISHOP) {
                        copy[r][c] = new Bishop(board[r][c].white, board[r][c].row, board[r][c].col);
                    }
                    else if (board[r][c].type == PieceType.ROOK) {
                        copy[r][c] = new Rook(board[r][c].white, board[r][c].row, board[r][c].col);
                    }
                }
            }
        }

        return copy;
    } // createDeepCopy

} // Engine
