package Game;

import BestMoveFinder.Util.Pair;
import Game.Matrix.Matrix;

import java.util.Scanner;

public class Game {
    /*
     *  ATTRIBUTES
     */
    public final Matrix<Character> data;
    public char turn;
    public boolean ongoing;
    public int emptySlots;
    public int xTurns;
    public int oTurns;

    public static final Game INSTANCE = new Game();

    /*
     *  METHODS
     */
    private Game() {
        this.data = new Matrix<>(3, 3);
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                this.data.setEl(row, col, ' ');

        this.turn = 'X';
        this.ongoing = true;
        this.emptySlots = 9;
        this.xTurns = 0;
        this.oTurns = 0;
    }

    public Game(Game oth) {
        this.data = new Matrix<>(3, 3);
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                this.data.setEl(row, col, oth.data.getEl(row, col));
        this.turn = oth.turn;
        this.ongoing = oth.ongoing;
        this.xTurns = oth.xTurns;
        this.oTurns = oth.oTurns;
    }

    // Getter & Setter
    public static Game getInstance() { return INSTANCE; }
    public char getTurn() { return this.turn; }
    public Matrix<Character> getData() { return this.data; }
    public boolean getOngoing() { return this.ongoing; }

    // Lain-lain
    public void nextTurn() { this.turn = this.turn == 'X' ? 'O' : 'X'; }

    public boolean checkWinCondition() {
        // Cek baris dan kolom
        for (int idx = 0; idx < 3; idx++)
            if ((this.data.getEl(idx, 0) == this.turn &&
                 this.data.getEl(idx, 1) == this.turn &&
                 this.data.getEl(idx, 2) == this.turn) ||
                (this.data.getEl(0, idx) == this.turn &&
                 this.data.getEl(1, idx) == this.turn &&
                 this.data.getEl(2, idx) == this.turn)) {
                this.ongoing = false;
                return true;
            }

        // Cek diagonal
        if ((this.data.getEl(0, 0) == this.turn &&
             this.data.getEl(1, 1) == this.turn &&
             this.data.getEl(2, 2) == this.turn) ||
            (this.data.getEl(0, 2) == this.turn &&
             this.data.getEl(1, 1) == this.turn &&
             this.data.getEl(2, 0) == this.turn)) {
            this.ongoing = false;
            return true;
        }
        return false;
    }

    public void place(int row, int col) throws IndexOutOfBoundsException {
        // Validasi input
        if (row < 0 || row >= 3 || col < 0 || col >= 3)
            throw new IndexOutOfBoundsException("Index yang dimasukkan tidak valid!");

        if (this.data.getEl(row, col) != ' ')
            throw new IndexOutOfBoundsException("Hanya bisa meletakkan di kotak kosong!");

        this.data.setEl(row, col, this.turn);
        this.emptySlots--;

        if (this.turn == 'X') xTurns++;
        else oTurns++;

        checkWinCondition();

        if (this.ongoing)
            nextTurn();

        if (this.emptySlots == 0)
            this.ongoing = false;
    }

    public void takeInput(int[] inputs, Scanner sc) {
        while (true) {
            String row, col;
            System.out.println("Giliran [" + this.turn + "]");

            System.out.print("Baris: ");
            row = sc.nextLine();

            System.out.print("Kolom: ");
            col = sc.nextLine();

            try {
                inputs[0] = Integer.parseInt(row);
                inputs[1] = Integer.parseInt(col);
                break;
            } catch (NumberFormatException ignored) {
                System.out.println("Input tidak valid!");
            }
        }
    }

    public void displayBoard() {
        System.out.println("    0   1   2");
        System.out.println("  ┌───┬───┬───┐");
        for (int row = 0; row < 3; row++) {
            System.out.print(row + " | ");
            for (int col = 0; col < 3; col++) {
                char currentChar = this.data.getEl(row, col);

                System.out.print(currentChar + " | ");
            }
            if (row != 2)
                System.out.println("\n  ├───┼───┼───┤");
        }
        System.out.println("\n  └───┴───┴───┘");
    }


    public String convToString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                char currentChar = this.data.getEl(row, col);
                sb.append(currentChar);
            }
        }
        return sb.toString();
    }
}
