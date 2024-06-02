import Astar.AlgoritmaA;
import Astar.Util.Node;
import Game.Game;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = Game.getInstance();
        game.displayBoard();
        Scanner sc = new Scanner(System.in);
        while (game.ongoing) {
            int[] inputs = new int[2];
            game.takeInput(inputs, sc);
            try {
                game.place(inputs[0], inputs[1]);
                game.displayBoard();
                if (game.ongoing) {
                    AlgoritmaA algoritmaA = new AlgoritmaA();
                    Node solution = algoritmaA.start();
                    if (solution != null) {
                        Game firstMove;
                        if (solution.prev.size() == 1) firstMove = solution.game;
                        else firstMove = solution.prev.get(1);
                        for (int row = 0; row < 3; row++)
                            for (int col = 0; col < 3; col++)
                                if (firstMove.getData().getEl(row, col) != game.getData().getEl(row, col)) {
                                    System.out.println("Best move: (" + row + ", " + col + ")");
                                    break;
                                }
                    } else System.out.println("Tidak ada gerakan yang berakhir dengan kemenangan pemain.");
                }
            } catch (IndexOutOfBoundsException e) { System.out.println(e.getMessage()); }
        }
        if (game.checkWinCondition()) System.out.println("Permainan dimenangkan oleh " + game.turn + "!");
        else System.out.println("Permainan berakhir tanpa pemenang.");
        sc.close();
    }
}