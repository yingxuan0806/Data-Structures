import java.util.List;

public class KDTree implements PointSet {

    // TODO: Instance variables here!
    private KDTreeNode root;
    private static final boolean horizontal = false;
    private static final boolean vertical = true;


    /* Constructs a KDTree using POINTS. You can assume POINTS contains at least one
       Point object. */
    public KDTree(List<Point> points) {
        // TODO: YOUR CODE HERE
        for (Point p : points) {
            root = add(p, root, horizontal);
        }
    }

    private KDTreeNode add(Point p, KDTreeNode node, boolean orientation) {
        if (node == null) {
            return new KDTreeNode(p, orientation);
        }

        if (p.equals(node.p)) {
            return node;
        }

        int compare = comparePoints(p, node.p, orientation);
        if (compare < 0) {
            node.left = add(p, node.left, !orientation);
        } else {
            node.right = add(p, node.right, !orientation);
        }
        return node;
    }

    private int comparePoints(Point a, Point b, boolean orientation) {
        if (orientation == horizontal) {
            return Double.compare(a.getX(), b.getY());
        } else {
            return Double.compare(a.getY(), b.getY());
        }
    }



    /*

    You might find this insert helper method useful when constructing your KDTree!
    Think of what arguments you might want insert to take in. If you need
    inspiration, take a look at how we do BST insertion!

    private KDTreeNode insert(...) {
        ...
    }

    */

//    private KDTreeNode insert(List<Point> points, int index, KDTreeNode currentNode, KDTreeNode previousNode, int compare) {
//
//    }

    /* Returns the closest Point to the inputted X and Y coordinates. This method
       should run in O(log N) time on average, where N is the number of POINTS. */
    public Point nearest(double x, double y) {
        // TODO: YOUR CODE HERE
//        return null;
        return nearest(root, new Point(x, y), root.p);
    }

    private Point nearest(KDTreeNode node, Point target, Point best) {
        if (node == null) {
            return best;
        }
        if (Point.distance(node.p, target) < Point.distance(best, target)) {
            best = node.p;
        }

        int compare = comparePoints(target, node.p, node.orientation);
        KDTreeNode good;
        KDTreeNode bad;

        if (compare < 0) {
            good = node.left;
            bad = node.right;
        } else {
            good = node.right;
            bad = node.left;
        }
        best = nearest(good, target, best);
        if (isWorthLooking(node, target, best)) {
            best = nearest(bad, target, best);
        }
        return best;
    }


    private boolean isWorthLooking(KDTreeNode node, Point target, Point best) {
        double distanceBest = Point.distance(best, target);
        double distanceWorst;
        if (node.orientation == horizontal) {
            distanceWorst = Point.distance(new Point(target.getX(), node.p.getY()), target);
        } else {
            distanceWorst = Point.distance(new Point(node.getX(), target.getY()), target);
        }
        return distanceWorst < distanceBest;
    }

    private class KDTreeNode {

        private Point p;
        private KDTreeNode left;
        private KDTreeNode right;
        private boolean orientation;

        // If you want to add any more instance variables, put them here!

        KDTreeNode(Point p, boolean orientation) {
            this.p = p;
            this.orientation = orientation;
        }

        KDTreeNode(Point p, KDTreeNode left, KDTreeNode right) {
            this.p = p;
            this.left = left;
            this.right = right;
        }

        Point p() {
            return p;
        }

        KDTreeNode left() {
            return left;
        }

        KDTreeNode right() {
            return right;
        }

        // If you want to add any more methods, put them here!

    }
}
