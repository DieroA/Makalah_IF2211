package BestMoveFinder;

import BestMoveFinder.Util.Pair;
import Game.Game;
import Game.Matrix.Matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BestMoveFinder {
    public static Pair findBestMove(Game gameState) {
        List<Game> possibleMoves = findEveryMove(gameState);
        Game bestMove = possibleMoves.getFirst();
        int bestMoveScore = evaluatePosition(bestMove);
        for (Game possibleMove : possibleMoves) {
            int score = evaluatePosition(possibleMove);
            if (score < bestMoveScore) {
                bestMove = possibleMove;
                bestMoveScore = score;
            }
        }

        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                if (gameState.getData().getEl(row, col) != bestMove.getData().getEl(row, col))
                    return new Pair(row, col);
        return new Pair(-99, -99);
    }

    public static List<Game> findEveryMove(Game gameState) {
        List<Game> possibleMoves = new ArrayList<>();

        List<Pair> emptySpaces = getEmptySpaces(gameState);
        for (Pair pair : emptySpaces) {
            Game newGame = new Game(gameState);
            newGame.place(pair.first, pair.second);
            possibleMoves.add(newGame);
        }

        return possibleMoves;
    }

    public static List<Pair> getEmptySpaces(Game gameState) {
        List<Pair> emptySpaces = new ArrayList<>();
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                if (gameState.getData().getEl(row, col) == ' ')
                    emptySpaces.add(new Pair(row, col));
        return emptySpaces;
    }

    public static int evaluatePosition(Game gameState) {
        char currentTurn = gameState.getTurn();

        int MAX_SCORE = 100;
        int score = 0;

        HashMap<String, Pair> summary = summarizeState(gameState);
        for (Pair pair : summary.values()) {
            int val = currentTurn == 'X' ? pair.second : pair.first;
            int valEnemy = currentTurn == 'X' ? pair.first : pair.second;

            if (val == 3 || valEnemy == 3) {
                if (val == 3) score = MAX_SCORE;            // KALAH
                else score = 0;                             // MENANG
                break;
            }

            if (valEnemy == 2 && val == 0) {
                score = MAX_SCORE;                          // KALAH
                break;
            }

            if (valEnemy == 0) {
                if (val == 1) score -= 10;
                else if (val == 2) score -= 50;
            }

            if (val == 0 && valEnemy == 1)
                score += 10;
        }
        return score;
    }

    public static HashMap<String, Pair> summarizeState(Game gameState) {
        HashMap<String, Pair> ret = new HashMap<>();
        Matrix<Character> board = gameState.getData();

        // Eval rows
        for (int row = 0; row < 3; row++) {
            String rowName = "row" + row;

            ret.put(rowName, new Pair(0, 0));
            for (int col = 0; col < 3; col++) {
                if (board.getEl(row, col) == ' ')
                    continue;

                Pair pair = ret.get(rowName);
                if (board.getEl(row, col) == 'X')
                    pair.first++;
                else pair.second++;
                ret.put(rowName, pair);
            }
        }

        // Eval cols
        for (int col = 0; col < 3; col++) {
            String colName = "col" + col;

            ret.put(colName, new Pair(0, 0));
            for (int row = 0; row < 3; row++) {
                if (board.getEl(row, col) == ' ')
                    continue;

                Pair pair = ret.get(colName);
                if (board.getEl(row, col) == 'X')
                    pair.first++;
                else pair.second++;
                ret.put(colName, pair);
            }
        }

        // Eval diags
        ret.put("diag1", new Pair(0, 0));
        ret.put("diag2", new Pair(0, 0));
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board.getEl(row, col) == ' ')
                    continue;

                if (row == col) {
                    Pair pair = ret.get("diag1");
                    if (board.getEl(row, col) == 'X')
                        pair.first++;
                    else pair.second++;
                    ret.put("diag1", pair);
                }

                if (row + col == 2) {
                    Pair pair = ret.get("diag2");
                    if (board.getEl(row, col) == 'X')
                        pair.first++;
                    else pair.second++;
                    ret.put("diag2", pair);
                }
            }
        }
        return ret;
    }
}
