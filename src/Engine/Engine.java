package Engine;

import Board.BoardState;
import Pieces.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Engine {

    int depth;
    Date date = new Date();
    private BoardState position;

    public Engine (BoardState p, int d) {

        depth = d;
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

        // for efficiency only consider top 10 moves
        if (moves.size() >= 10) {
            moves = new ArrayList<>(moves.subList(0, 10));
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
            newEval = alphabeta(p, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
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
            // set a search time limit, 10 seconds
            if (date.getTime() - startTime > 10000) {
                break;
            }
        }

        // return the best move in the position
        return bestMove;

    } // calculateBestMove

    private double alphabeta (BoardState currPosition, int depth, double alpha, double beta) {

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
        // for efficiency only consider top 10 moves
        if (moves.size() >= 10) {
            moves = new ArrayList<>(moves.subList(0, 10));
        }

        double eval;
        if (currPosition.whiteToMove) {
            eval = Double.NEGATIVE_INFINITY;
            for (Piece m : moves) {
                // create a deep copy of the board state and pieces
                BoardState newPosition = new BoardState(currPosition);
                newPosition.makeMove(m.prevRow, m.prevCol, m.row, m.col);
                // continue recursive call
                eval = alphabeta(newPosition,depth-1, alpha, beta);
                alpha = Math.max(alpha, eval);
                // pruning, disregard branch
                if (beta <= alpha) break;
            }
            return eval;
        }
        else {
            eval = Double.POSITIVE_INFINITY;
            for (Piece m : moves) {
                // create a deep copy of the board state and pieces
                BoardState newPosition = new BoardState(currPosition);
                // make the move and set the new position
                newPosition.makeMove(m.prevRow, m.prevCol, m.row, m.col);
                // continue recursive call
                eval = alphabeta(newPosition,depth-1, alpha, beta);
                beta = Math.min(beta, eval);
                // pruning, disregard branch
                if (beta <= alpha) break;
            }
            return eval;
        }

    } // alphabeta

} // Engine
