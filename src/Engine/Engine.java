package Engine;

import Board.BoardState;
import Pieces.*;

import java.util.ArrayList;
import java.util.Comparator;

public class Engine {

    private BoardState position;
    private ArrayList<BoardState> initialMoves = new ArrayList<>();

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
            p.makeMove(new int[]{m.prevRow, m.prevCol}, new int[]{m.row, m.col});
            // evaluate the move
            m.evaluation = p.evaluatePosition();
        }
        // sort moves by evaluation (min to max)
        moves.sort(Comparator.comparingDouble(Piece::getEvaluation));

        // for efficiency only consider top 3 moves
        moves = new ArrayList<>(moves.subList(0, 3));

        // some default move
        bestMove = moves.get(0);

        // compare the moves
        for (Piece m : moves) {
            BoardState p = new BoardState(this.position);
            // make the move
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

        // get all moves
        ArrayList<Piece> moves = currPosition.getAllPossibleMoves();
        // evaluate initial move
        for (Piece m : moves) {
            BoardState p = new BoardState(currPosition);
            // make the move
            p.makeMove(new int[]{m.prevRow, m.prevCol}, new int[]{m.row, m.col});
            // evaluate the move
            m.evaluation = p.evaluatePosition();
        }
        // sort moves by evaluation (min to max)
        moves.sort(Comparator.comparingDouble(Piece::getEvaluation));
        // for efficiency only consider top 3 moves
        if (currPosition.whiteToMove) {
            moves = new ArrayList<>(moves.subList(moves.size()-3, moves.size()));
        }
        else {
            moves = new ArrayList<>(moves.subList(0, 3));
        }

        double eval;
        if (currPosition.whiteToMove) {
            eval = Double.NEGATIVE_INFINITY;
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
            eval = Double.POSITIVE_INFINITY;
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

} // Engine
