package Engine;

import Board.BoardState;
import Pieces.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Engine {

    int depth = 3;
    Date date = new Date();
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

        // evaluate each initial move
        for (Piece m : moves) {
            BoardState p = new BoardState(this.position);
            // make the move
            p.makeMove(m.prevRow, m.prevCol, m.row, m.col);
            // evaluate the move
            m.evaluation = p.evaluatePosition();
        }

        // sort moves by evaluation (min to max)
        moves.sort(Comparator.comparingDouble(Piece::getEvaluation));
        // sort them max to min for white
        if (position.whiteToMove) {
            Collections.reverse(moves);
        }

        // for efficiency only consider top 5 moves
        if (moves.size() >= 5) {
            moves = new ArrayList<>(moves.subList(0, 5));
        }

        // some default move
        bestMove = moves.get(0);

        // get time before starting search
        long startTime = date.getTime();

        // compare the moves
        for (Piece m : moves) {
            BoardState p = new BoardState(this.position);
            // make the move
            p.makeMove(m.prevRow, m.prevCol, m.row, m.col);
            // check the evaluation of the move
            // fix me
            // lost in the sauce on first call
            newEval = alphabeta(depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, p);
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
            // set a search time limit
            if (date.getTime() - startTime > 5000) {
                break;
            }
        }

        // return the best move in the position
        return bestMove;

    } // calculateBestMove

    private double alphabeta (int depth, double alpha, double beta, BoardState currPosition) {

        // base cases
        // out of memory
        // depth
        // checkmate or stalemate
        if (depth==0 || currPosition.checkmate || currPosition.stalemate) {
            // max depth reach or game over
            // send back evaluation of the position
            return currPosition.evaluatePosition();
        }

        // get all moves
        ArrayList<Piece> moves = currPosition.getAllPossibleMoves();
        // evaluate initial move
        for (Piece m : moves) {
            BoardState p = new BoardState(currPosition);
            // make the move
            p.makeMove(m.prevRow, m.prevCol, m.row, m.col);
            // evaluate the move
            m.evaluation = p.evaluatePosition();
        }
        // sort moves by evaluation (min to max)
        moves.sort(Comparator.comparingDouble(Piece::getEvaluation));
        // sort them max to min for white
        if (currPosition.whiteToMove) {
            Collections.reverse(moves);
        }
        // for efficiency only consider top 5 moves
        if (currPosition.whiteToMove) {
            if (moves.size() >= 5) {
                moves = new ArrayList<>(moves.subList(moves.size() - 5, moves.size()));
            }
        }
        else {
            if (moves.size() >= 5) {
                moves = new ArrayList<>(moves.subList(0, 5));
            }
        }

        double eval;
        if (currPosition.whiteToMove) {
            double maxEval = Double.NEGATIVE_INFINITY;
            for (Piece m : moves) {
                // create a deep copy of the board state and pieces
                BoardState newPosition = new BoardState(currPosition);
                newPosition.makeMove(m.prevRow, m.prevCol, m.row, m.col);
                // continue recursive call
                eval = alphabeta(depth-1, alpha, beta, newPosition);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, maxEval);
                // pruning, disregard branch
                if (beta <= alpha) break;
            }
            return maxEval;
        }
        else {
            double minEval = Double.POSITIVE_INFINITY;
            for (Piece m : moves) {
                // create a deep copy of the board state and pieces
                BoardState newPosition = new BoardState(currPosition);
                // make the move and set the new position
                newPosition.makeMove(m.prevRow, m.prevCol, m.row, m.col);
                // continue recursive call
                eval = alphabeta(depth-1, alpha, beta, newPosition);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, minEval);
                // pruning, disregard branch
                if (beta <= alpha) break;
            }
            return minEval;
        }

    } // alphabeta

} // Engine
