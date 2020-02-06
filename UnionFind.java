public class UnionFind {

    int[] array;

    /* Creates a UnionFind data structure holding N vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int N) {
        array = new int[N + 1];
        //initialise all indices to value of -1 (root of itself, 1 element in each set)
        for (int i = 0; i < array.length; i += 1) {
            array[i] = -1;
        }
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
//        return -1;
        return array[v];


    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid vertices are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
//        return -1;
        //invalid vertices
        if (v > array.length || v < 0) {
            throw new IllegalArgumentException();
        }

        int findRoot = v;
        //not the root, then use recursive to find the root using parent method
        while (parent(findRoot) > -1) {
            findRoot = parent(findRoot);
        }

        //path compression
        int newArrayValue;

        while (v != findRoot) {
            newArrayValue = parent(v);
            array[v] = findRoot;
            v = newArrayValue;
        }

        return findRoot;

//        //v is the root
//        if (array[v] < 0) {
//            return v;
//        }
//
//        int toFind = array[v];
//
//        //v is not the root. use recursive to find root
//        //value ot the index is the parent
//        return find(toFind);

    }

    /* Connects two elements V1 and V2 together. V1 and V2 can be any element,
       and a union-by-size heuristic is used. If the sizes of the sets are
       equal, tie break by choosing V1 to be the new root. Union-ing a vertex
       with itself or vertices that are already connected should not change the
       structure. */
    public void union(int v1, int v2) {
        //if elements are already connected, do nothing
//        if (connected(v1, v2) == true) {
//            return;
//        }

//        if (connected(v1, v2) == false) {
//
//            //v1 set size larger than or equals to v2 set size
//            //v1 is the root
//            if (sizeOf(v1) >= sizeOf(v2)) {
//                array[find(v1)] = array[find(v1)] - sizeOf(v2);
//                array[find(v2)] = find(v1);
//            }
//
//            else {
//                array[find(v2)] = array[find(v2)] - sizeOf(v1);
//                array[find(v1)] = find(v2);
//            }
//        }

        if (!connected(v1, v2)) {
            int sizeOfV1Set = sizeOf(v1);
            int sizeOfV2Set = sizeOf(v2);

            int rootOfV1 = find(v1);
            int rootOfV2 = find(v2);

            //v1 set size larger than or equals to v2 set size
            //v1 is the root
            if (sizeOfV1Set >= sizeOfV2Set) {
                array[rootOfV2] = rootOfV1;
                //add size of v2 to size of v1
                array[rootOfV1] = array[rootOfV1] - sizeOfV2Set;
            } else {
                array[rootOfV1] = rootOfV2;
                array[rootOfV2] = array[rootOfV2] - sizeOfV1Set;
                //v2 is root

            }
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
//        return -1;
        //find the root, which its index contains the size of the set
        int size = -(array[find(v)]);
        return size;
    }

    /* Returns true if nodes V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
//        return false;
        if (find(v1) == find(v2)) {
            return true;
        }
        return false;
    }
}
