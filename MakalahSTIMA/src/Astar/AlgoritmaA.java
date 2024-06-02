package Astar;

import Astar.Util.Node;
import Astar.Util.NodeComparator;
import BestMoveFinder.BestMoveFinder;
import Game.Game;

import java.util.*;

public class AlgoritmaA {
    /*
     *  ATTRIBUTES
     */
    private final PriorityQueue<Node> pq;
    private final HashSet<Node> visited;
    private Node currentNode;

    /*
     *  METHODS
     */
    public AlgoritmaA() {
        this.pq = new PriorityQueue<>(new NodeComparator());
        this.visited = new HashSet<>();
    }

    public Node getHead() {
        return pq.poll();
    }

    public void addToPq(Node val) {
        pq.offer(val);
    }

    public void next() throws NullPointerException {
        if (pq.isEmpty()) {
            throw (new NullPointerException());
        }

        Node head = getHead();
        this.currentNode = head;

        if (this.visited.contains(head))
            return;
        this.visited.add(head);

        List<Game> nextGames = BestMoveFinder.findEveryMove(this.currentNode.game);
        for (Game g : nextGames) {
            ArrayList<Game> prevList = new ArrayList<>(head.prev);
            prevList.add(this.currentNode.game);
            Node temp = new Node(g, prevList);
            if (this.visited.contains(temp))
                continue;

            addToPq(temp);
        }
    }

    public Node start() {
        currentNode = new Node(Game.getInstance(), new ArrayList<>());
        addToPq(currentNode);

        while (!pq.isEmpty()) {
            if (currentNode.game.checkWinCondition() && currentNode.game.turn == Game.getInstance().getTurn()) {
                boolean flag = true;
                for (int i = 0; i < currentNode.prev.size(); i++)
                    if (i % 2 == 1)
                        if (Node.calcScore(currentNode.prev.get(i)) > 100) flag = false;

                if (flag)
                    return currentNode;
            }

            try { next(); }
            catch (Exception ignored) { }
        }
        return null;
    }

}
