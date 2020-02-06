import java.util.Set;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Iterator;
import java.util.ArrayDeque;
import java.util.Map;

/* A mutable and finite Graph object. Edge labels are stored via a HashMap
   where labels are mapped to a key calculated by the following. The graph is
   undirected (whenever an Edge is added, the dual Edge is also added). Vertices
   are numbered starting from 0. */
public class Graph {

    /* Maps vertices to a list of its neighboring vertices. */
    private HashMap<Integer, Set<Integer>> neighbors = new HashMap<>();
    /* Maps vertices to a list of its connected edges. */
    private HashMap<Integer, Set<Edge>> edges = new HashMap<>();
    /* A sorted set of all edges. */
    private TreeSet<Edge> allEdges = new TreeSet<>();

    /* Returns the vertices that neighbor V. */
    public TreeSet<Integer> getNeighbors(int v) {
        return new TreeSet<Integer>(neighbors.get(v));
    }

    /* Returns all edges adjacent to V. */
    public TreeSet<Edge> getEdges(int v) {
        return new TreeSet<Edge>(edges.get(v));
    }

    /* Returns a sorted list of all vertices. */
    public TreeSet<Integer> getAllVertices() {
        return new TreeSet<Integer>(neighbors.keySet());
    }

    /* Returns a sorted list of all edges. */
    public TreeSet<Edge> getAllEdges() {
        return new TreeSet<Edge>(allEdges);
    }

    /* Adds vertex V to the graph. */
    public void addVertex(Integer v) {
        if (neighbors.get(v) == null) {
            neighbors.put(v, new HashSet<Integer>());
            edges.put(v, new HashSet<Edge>());
        }
    }

    /* Adds Edge E to the graph. */
    public void addEdge(Edge e) {
        addEdgeHelper(e.getSource(), e.getDest(), e.getWeight());
    }

    /* Creates an Edge between V1 and V2 with no weight. */
    public void addEdge(int v1, int v2) {
        addEdgeHelper(v1, v2, 0);
    }

    /* Creates an Edge between V1 and V2 with weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        addEdgeHelper(v1, v2, weight);
    }

    /* Returns true if V1 and V2 are connected by an edge. */
    public boolean isNeighbor(int v1, int v2) {
        return neighbors.get(v1).contains(v2) && neighbors.get(v2).contains(v1);
    }

    /* Returns true if the graph contains V as a vertex. */
    public boolean containsVertex(int v) {
        return neighbors.get(v) != null;
    }

    /* Returns true if the graph contains the edge E. */
    public boolean containsEdge(Edge e) {
        return allEdges.contains(e);
    }

    /* Returns if this graph spans G. */
    public boolean spans(Graph g) {
        TreeSet<Integer> all = getAllVertices();
        if (all.size() != g.getAllVertices().size()) {
            return false;
        }
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> vertices = new ArrayDeque<>();
        Integer curr;

        vertices.add(all.first());
        while ((curr = vertices.poll()) != null) {
            if (!visited.contains(curr)) {
                visited.add(curr);
                for (int n : getNeighbors(curr)) {
                    vertices.add(n);
                }
            }
        }
        return visited.size() == g.getAllVertices().size();
    }

    /* Overrides objects equals method. */
    public boolean equals(Object o) {
        if (!(o instanceof Graph)) {
            return false;
        }
        Graph other = (Graph) o;
        return neighbors.equals(other.neighbors) && edges.equals(other.edges);
    }

    /* A helper function that adds a new edge from V1 to V2 with WEIGHT as the
       label. */
    private void addEdgeHelper(int v1, int v2, int weight) {
        addVertex(v1);
        addVertex(v2);

        neighbors.get(v1).add(v2);
        neighbors.get(v2).add(v1);

        Edge e1 = new Edge(v1, v2, weight);
        Edge e2 = new Edge(v2, v1, weight);
        edges.get(v1).add(e1);
        edges.get(v2).add(e2);
        allEdges.add(e1);
    }

    public Graph prims(int start) {
//        return null;
//        1. Create a new graph T, where T will be the resulting MST.
//        2. Choose an arbitrary starting vertex in G and add that vertex to T.
//        3. Repeatedly add the smallest edge of G that has one vertex inside T to T.
//        Let's call this edge e.
//        4. Continue until T has V - 1 edges.

        //cuts
        //1. vertices of T
        //2. vertices of G that aren't in T

        Graph T = new Graph();

        Map<Integer, Integer> from = new HashMap<>();
        Map<Integer, Edge> distance = new HashMap<>();

        PriorityQueue<Integer> fringe = new PriorityQueue<>((o1, o2)
            -> Integer.compare(distance.get(o1).getWeight(), distance.get(o2).getWeight()));

        //initialise
        fringe.add(start);
        from.put(start, null);


        while (!fringe.isEmpty()) {
            Integer toRemove = fringe.poll();
            if (!T.containsVertex(toRemove)) {
                //get all edges adjacent to V
                TreeSet<Edge> edgesAll = this.getEdges(toRemove);
                //iterate through the edges adjacent to V to get smallest edge
                Iterator<Edge> iterateEdge = edgesAll.iterator();
                Edge currentEdge;

                while (iterateEdge.hasNext()) {
                    //go to the next iterator
                    currentEdge = iterateEdge.next();
                    Integer nextVertex = currentEdge.getDest();

                    if (distance.containsKey(nextVertex)
                            && distance.get(nextVertex).getWeight()
                            == currentEdge.getWeight()) {
                        continue;
                    } else if (distance.containsKey(nextVertex)
                            && distance.get(nextVertex).getWeight()
                            < currentEdge.getWeight()) {
                        distance.put(nextVertex, distance.get(nextVertex));
                    } else {
                        distance.put(nextVertex, currentEdge);
                        from.put(nextVertex, toRemove);
                    }

                    //no idea why this doesnt work but the above works
//                    if (distance.containsKey(nextVertex)) {
//                        if (distance.get(nextVertex).getWeight() == currentEdge.getWeight()) {
//                            continue;
//                        }
//                        else if (distance.get(nextVertex).getWeight() < currentEdge.getWeight()) {
//                            distance.put(nextVertex, distance.get(nextVertex));
//                        }
//                    }

                    fringe.add(nextVertex);


                }

                if (toRemove == start) {
                    T.addVertex(toRemove);
                } else {
                    T.addEdge(distance.get(toRemove));
                }

            }
        }

        return T;

    }

    public Graph kruskals() {
//        return null;
//        1. Create a new graph T with the same vertices as G, but no edges (yet).
//        2. Make a list of all the edges in G.
//        3. Sort the edges from smallest weight to largest weight.
//        4. Iterate through the edges in sorted order.
//        For each edge (u, w), if u and w are not connected by a path T, add (u, w) to T.
        Graph T = new Graph();
        TreeSet<Integer> vertices = this.getAllVertices();

        for (int v : getAllVertices()) {
            T.addVertex(v);
        }

        TreeSet<Edge> edgesAll = this.getAllEdges();
        UnionFind set = new UnionFind(vertices.size());
        List<Edge> sortedEdges = new ArrayList<>(edgesAll);

        int j = 0;
        for (int i = 0; j < vertices.size() - 1; i += 1) {
            Edge currentEdge = sortedEdges.get(i);
            if (set.connected(currentEdge.getSource(), currentEdge.getDest())) {
                continue;
            } else {
                T.addEdge(currentEdge);
                set.union(currentEdge.getSource(), currentEdge.getDest());
                j += 1;
            }

        }


        return T;
    }
}
