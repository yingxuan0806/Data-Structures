import java.util.ArrayList;
import java.util.NoSuchElementException;

/* A MinHeap class of Comparable elements backed by an ArrayList. */
public class MinHeap<E extends Comparable<E>> {

    /* An ArrayList that stores the elements in this MinHeap. */
    private ArrayList<E> contents;
    private int size;

    /* Initializes an empty MinHeap. */
    public MinHeap() {
        contents = new ArrayList<>();
        contents.add(null);
    }

    /* Returns the element at index INDEX, and null if it is out of bounds. */
    private E getElement(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the element at index INDEX to ELEMENT. If the ArrayList is not big
       enough, add elements until it is the right size. */
    private void setElement(int index, E element) {
        while (index >= contents.size()) {
            contents.add(null);
        }
        contents.set(index, element);
    }

    /* Swaps the elements at the two indices. */
    private void swap(int index1, int index2) {
        E element1 = getElement(index1);
        E element2 = getElement(index2);
        setElement(index2, element1);
        setElement(index1, element2);
    }

    /* Prints out the underlying heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getElement(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getElement(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getElement(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getElement(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* Returns the index of the left child of the element at index INDEX. */
    private int getLeftOf(int index) {
        return 2 * index;

    }

    /* Returns the index of the right child of the element at index INDEX. */
    private int getRightOf(int index) {
        return (2 * index) + 1;
    }

    /* Returns the index of the parent of the element at index INDEX. */
    private int getParentOf(int index) {
        return (index / 2);
    }

    /* Returns the index of the smaller element. At least one index has a
       non-null element. If the elements are equal, return either index. */
    private int min(int index1, int index2) {
        if (getElement(index1) == null) {
            return index2;
        } else if (getElement(index2) == null) {
            return index1;
        } else if (getElement(index1).compareTo(getElement(index2)) < 0) {
            return index1;
        }
        return index2;
    }

    /* Returns but does not remove the smallest element in the MinHeap. */
    public E findMin() {
//        return null;
        return getElement(1);
    }

    /* Bubbles up the element currently at index INDEX. */
    // swap with its parent as long as it is smaller than parent or until it is the new root
    // if equal to parent, either swap or not.

    private void bubbleUp(int index) {
        if (getParentOf(index) < 1) {
            return;
        }

        if (getElement(index).compareTo(getElement(getParentOf(index))) < 0) {
            swap(index, getParentOf(index));
        }
    }

    /* Bubbles down the element currently at index INDEX. */
    private void bubbleDown(int index) {
        if (getElement(getLeftOf(index)) == null && getElement(getRightOf(index)) == null) {
            return;
        }

        int selectedChild = min(getLeftOf(index), getRightOf(index));
        if (getElement(index).compareTo(getElement(selectedChild)) > 0) {
            swap(index, selectedChild);
        }
    }

    /* Returns the number of elements in the MinHeap. */
    public int size() {
        return size;
    }

    /* Inserts ELEMENT into the MinHeap. If ELEMENT is already in the MinHeap,
       throw an IllegalArgumentException.*/
    public void insert(E element) {
        if (contains(element)) {
            throw new IllegalArgumentException();
        }

        size += 1;
        int added = size;
        setElement(added, element);

        if (getParentOf(added) >= 1) {
            while (getElement(added).compareTo(getElement(getParentOf(added))) < 0) {
                bubbleUp(added);
                added = getParentOf(added);
                if (getParentOf(added) < 1) {
                    break;
                }
            }
        }

    }

    /* Returns and removes the smallest element in the MinHeap. */
    public E removeMin() {
//        return null;
        E smallestElement = getElement(1);
        setElement(1, getElement(size));
        setElement(size, null);
        size -= 1;
        int index = 1;

        int smallerI;

        if (getElement(getLeftOf(index)) == null && getElement(getRightOf(index)) == null) {
            smallerI = 1;
        } else {
            smallerI = min(getLeftOf(index), getRightOf(index));
        }

        if (getElement(smallerI) != null) {
            while (getElement(index).compareTo(getElement(smallerI)) > 0) {
                bubbleDown(index);

                index = smallerI;

                if (getElement(getLeftOf(index)) == null && getElement(getRightOf(index)) == null) {
                    smallerI = 1;
                } else {
                    smallerI = min(getLeftOf(index), getRightOf(index));
                }
            }
        }
        return smallestElement;
    }

    /* Replaces and updates the position of ELEMENT inside the MinHeap, which
       may have been mutated since the initial insert. If a copy of ELEMENT does
       not exist in the MinHeap, throw a NoSuchElementException. Item equality
       should be checked using .equals(), not ==. */
    public void update(E element) {
        if (contains(element)) {
            for (int i = 1; i <= size; i += 1) {
                if (getElement(i).equals(element)) {
                    setElement(i, element);

                    if (getParentOf(i) >= 1) {
                        while (getElement(i).compareTo(getElement(getParentOf(i))) < 0) {
                            bubbleUp(i);
                            i = getParentOf(i);
                            if (getParentOf(i) < 1) {
                                break;
                            }
                        }
                    }


                    int smallerI;
                    if (getElement(getLeftOf(i)) == null && getElement(getRightOf(i)) == null) {
                        smallerI = 1;
                    } else {
                        smallerI = min(getLeftOf(i), getRightOf(i));
                    }

                    if (getElement(smallerI) != null) {
                        while (getElement(i).compareTo(getElement(smallerI)) > 0) {
                            bubbleDown(i);
                            i = smallerI;
                            if (getElement(getLeftOf(i)) == null
                                    && getElement(getRightOf(i)) == null) {
                                smallerI = 1;
                            } else {
                                smallerI = min(getLeftOf(i), getRightOf(i));
                            }
                        }
                        break;
                    }
                }
            }
        } else {
            throw new NoSuchElementException();
        }
    }

    /* Returns true if ELEMENT is contained in the MinHeap. Item equality should
       be checked using .equals(), not ==. */
    public boolean contains(E element) {
//        return false;

        for (int i = 1; i <= size; i += 1) {
            if (getElement(i).equals(element)) {
                return true;
            }
        }
        return false;
    }
}
