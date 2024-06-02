package Astar.Util;

import BestMoveFinder.BestMoveFinder;
import Game.Game;

import java.util.List;

public class Node {
    /*
     *  ATTRIBUTES
     */
    public Game game;
    public int score;
    public List<Game> prev;

    /*
     *  METHODS
     */
    public Node(Game game, List<Game> prev) {
        this.game = game;
        this.score = calcScore(game);
        this.prev = prev;
    }

    public static int calcScore(Game game) {
        return BestMoveFinder.evaluatePosition(game) + (game.turn == 'X' ? game.xTurns : game.oTurns);
    }

    public int getScore() { return this.score; }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof Node o)) return false;

        return (game.convToString().equals(o.game.convToString()));
    }

    @Override
    public int hashCode() {
        return game.convToString().hashCode();
    }
}
