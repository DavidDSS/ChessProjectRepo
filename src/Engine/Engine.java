package Engine;

import Board.BoardState;
import Pieces.Piece;
import java.util.ArrayList;

public class Engine {

    private BoardState position;

    public Engine (BoardState p) {
        position = p;
    }

    /*******************************************************************************
     * takes a board position and calculates the best move
     * idea, create all moves and choose the move that produces the best max/min
     *
     */
    public Piece calculateBestMove () {

        BoardState p = this.getPosition();
        double currEval = p.evaluation;
        double newEval;
        Piece bestMove = null;

        // get all possible moves for the player
        // loop through all the moves setting chosen position based on max/minx
        // return best position
        ArrayList<Piece> moves = position.getAllPossibleMoves();

        for (Piece m : moves) {
            // check the evaluation of the move
            newEval = alphabeta(3, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, p);

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
                // make the move and set the new position
                BoardState newPosition = new BoardState(currPosition.theBoard);
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
                // make the move and set the new position
                BoardState newPosition = new BoardState(currPosition.theBoard);
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

    private BoardState getPosition () {
        return this.position;
    }
}
