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
                    currEval = newEval;
                    bestMove = m;
                }
            }
            // for black we want the minimum value
            else {
                if (currEval >= newEval) {
                    currEval = newEval;
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

        double eval;
        if (currPosition.whiteToMove) {
            double maxEval = Double.NEGATIVE_INFINITY;
            ArrayList<Piece> moves = currPosition.getAllPossibleMoves();
            for (Piece m : moves) {
                // create a deep copy of the board state and pieces
                BoardState newPosition = new BoardState(currPosition);
                newPosition.makeMove(new int[]{m.prevRow, m.prevCol}, new int[]{m.row, m.col});
                // continue recursive call
                eval = alphabeta(depth-1, alpha, beta, newPosition);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                // pruning, disregard branch
                if (beta <= alpha) break;
            }
            return maxEval;
        }
        else {
            double minEval = Double.POSITIVE_INFINITY;
            ArrayList<Piece> moves = currPosition.getAllPossibleMoves();
            for (Piece m : moves) {
                // create a deep copy of the board state and pieces
                BoardState newPosition = new BoardState(currPosition);
                // make the move and set the new position
                newPosition.makeMove(new int[]{m.prevRow, m.prevCol}, new int[]{m.row, m.col});
                // continue recursive call
                eval = alphabeta(depth-1, alpha, beta, newPosition);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                // pruning, disregard branch
                if (beta <= alpha) break;
            }
            return minEval;
        }

    } // alphabeta

} // Engine
