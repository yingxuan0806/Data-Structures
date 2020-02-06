import java.util.Random;
import java.util.List;
import static java.util.Collections.swap;
import java.util.ArrayList;

public class Sorts {

    // Ensures you get the same sequence of random numbers every single time.
    private static Random generator;
    private static final int RANDOM_SEED = 400;

    /* Returns the result of sorting the values in LIST using insertion sort. */
    public static void insertionSort(List<Integer> list) {
        for (int i = 0; i < list.size(); i += 1) {
            for (int j = i; j > 0 && list.get(j - 1) > list.get(j); j -= 1) {
                swap(list, j, j - 1);
            }
        }


    }

    /* Returns the result of sorting the values in LIST using selection sort. */
    public static void selectionSort(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            int lowestIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j) < list.get(lowestIndex)) {
                    lowestIndex = j;
                }
            }
            list.add(i, list.remove(lowestIndex));
        }
    }

    /* Returns the result of sorting the values in this list using quicksort. */
    public static void quickSort(List<Integer> list) {
        generator = new Random(RANDOM_SEED);
//        quickSort(null, -1, -1); // FIXME
        quickSort(list, 0, list.size());
    }

    private static void quickSort(List<Integer> list, int start, int end) {
        // Below are example of how to use the random number generator. You will
        // need to do this in your code somehow!
        generator.nextDouble();
        generator.nextInt(30);
        if (list.size() == 0) {
            return;
        }

        if (list.size() == 1) {
            return;
        }

        int pivotIndex = generator.nextInt(list.size());
        Integer pivot = list.get(pivotIndex);
        List<Integer> tempLeft = new ArrayList<>();
        List<Integer> tempMiddle = new ArrayList<>();
        List<Integer> tempRight = new ArrayList<>();

        for (int i = 0; i < list.size(); i += 1) {
            if (list.get(i) < pivot) {
                tempLeft.add(list.get(i));
            } else if (list.get(i) == pivot) {
                tempMiddle.add(list.get(i));
            } else {
                tempRight.add(list.get(i));
            }
        }

        quickSort(tempLeft, 0, tempLeft.size());
        quickSort(tempRight, pivotIndex + tempMiddle.size(), list.size());
        list.clear();
        list.addAll(tempLeft);
        list.addAll(tempMiddle);
        list.addAll(tempRight);

    }

    /* Returns the result of sorting the values in this list using merge
       sort. */
    public static void mergeSort(List<Integer> list) {
        mergeSortHelper(list);

    }


    private static void mergeSortHelper(List<Integer> list) {
        int pivot;
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();

        if (list.size() > 1) {
            pivot = list.size() / 2;

            //copy left half into left array
            for (int i = 0; i < pivot; i += 1) {
                left.add(list.get(i));
            }

            //copy right half into right array
            for (int i = pivot; i < list.size(); i += 1) {
                right.add(list.get(i));
            }

            mergeSortHelper(left);
            mergeSortHelper(right);

            merge(list, left, right);
        }
    }

    private static void merge(List<Integer> list, List<Integer> left, List<Integer> right) {
        //temp list to build merge list
        ArrayList<Integer> temp = new ArrayList<>();

        int listIndex = 0;
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex) < right.get(rightIndex)) {
                list.set(listIndex, left.get(leftIndex));
                leftIndex += 1;
            } else {
                list.set(listIndex, right.get(rightIndex));
                rightIndex += 1;
            }
            listIndex += 1;
        }

        int tempIndex = 0;

        if (leftIndex >= left.size()) {
            temp = (ArrayList<Integer>) right;
            tempIndex = rightIndex;
        } else {
            temp = (ArrayList<Integer>) left;
            tempIndex = leftIndex;
        }

        for (int i = tempIndex; i < temp.size(); i += 1) {
            list.set(listIndex, temp.get(i));
            listIndex += 1;
        }
    }


}
