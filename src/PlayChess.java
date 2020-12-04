import Board.BoardState;
import Pieces.Pawn;
import Pieces.Piece;

public class PlayChess {

    public static void main(String[] args){
        BoardState board= new BoardState();
        board.printBoard();

        // 97 -> a
        // 98 -> b
        // 99 -> c
        // 100 -> d
        // 101 -> e
        // 102 -> f
        // 103 -> g
        // 104 -> h

        // a 2
        Pawn p=new Pawn(false, new char[]{(char)(98),6}, 'P');
        System.out.println("PAWN");
        System.out.print(p.position[0]);
        System.out.println((int)p.position[1]);
        System.out.print(p.convertNotation(p.position)[0]);
        System.out.println(p.convertNotation(p.position)[1]);
        System.out.println(p.checkMove(board, p.position, new char[]{(char)(97),5}));
    }
}
