package Astar.Util;

import BestMoveFinder.BestMoveFinder;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return Integer.compare(o1.score, o2.score);
    }
}
