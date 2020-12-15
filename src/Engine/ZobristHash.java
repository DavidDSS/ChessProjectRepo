package Engine;

public class ZobristHash {

    long arr[][][] = new long[2][6][64];

//    constant indices
//    white_pawn := 1
//    white_rook := 2
//            # etc.
//            black_king := 12
//
//    function init_zobrist():
//            # fill a table of random numbers/bitstrings
//            table := a 2-d array of size 64×12
//            for i from 1 to 64:  # loop over the board, represented as a linear array
//              for j from 1 to 12:      # loop over the pieces
//                  table[i][j] := random_bitstring()
//
//    function hash(board):
//    h := 0
//            for i from 1 to 64:      # loop over the board positions
//        if board[i] ≠ empty:
//    j := the piece at board[i], as listed in the constant indices, above
//    h := h XOR table[i][j]
//            return h


}
