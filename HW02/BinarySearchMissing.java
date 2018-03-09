package search;

public class BinarySearchMissing {

    //before:
    //array - integer array in reverse order, key - integer
    //&& right == array length || (array[right] <= key && 0 <= right < array length)
    //&& left == -1 ||  (array[left] >= key && array length > left >=0)
    //&& left' - right' <= (right - left + 1) / 2
    //&& right - left >= 1
    private static int recursiveBinarySearch(int[] array, int left, int right, int key) {
        //before
        if (left >= right - 1) {
            //before && left >= right - 1
            //array[right] <= key && key - array[right + z] >= key - array[right] for all z > 0
            //&& array[right - z] > key for all z > 0
            //result == right
            if (right != array.length && right != -1 && array[right] == key) {
                //array[right] == key
                return right;
            } else {
                //array[right] most close over key || key > any array element
                // || key < any array element
                return (-right - 1);
            }
        } else {
            //before && left < right - 1
            int middle = (left + right) / 2;
            //left < middle < right && middle == (left + right) / 2
            if (array[middle] > key)
                //left' == middle
                //array[left'] > key && middle <= result <= right
                return recursiveBinarySearch(array, middle, right, key);
            else
                //right' == middle
                //array[right'] <= key && left <= result <= middle
                return recursiveBinarySearch(array, left, middle, key);
        }
    }
    // Post: (key' == key) && (array[i]' == array[i]) && (array length == 0 || (array[0] < key)) && result == -(-1) - 1) && (array[result] >= key && (result == -(array len - 1)-1 || array[result + 1] < key)

    //before: array - integer array in reverse order, key - integer value
    private static int iterativeBinarySearch(int[] array, int key) {
        //before
        int left = -1;
        int right = array.length;
        //invariant:
        //(right' == array length || (right' < array length && array[right'] <= key)) &&
        //(left' == -1 || ( left' >= 0 && array[left'] >= key)) &&
        //(right'' - left'' <= (right' - left' + 1) / 2)  && (left' <= right' - 1)
        while (left < right - 1) {
            //invariant && before && right' > left' + 1
            int middle = (left + right) / 2;
            //before && invariant && right' <= middle <= left' && right' > left' + 1
            if (array[middle] > key)
                //invariant && before && right' > left' + 1 && array[middle] > key
                left = middle;
                //left'' == middle && right'' = right
                //array[left''] > key &&  left'' <= result <= right''
                //invariant && before
            else
                //invariant && before && right' > left' + 1 && array[middle] <=key
                //array[middle] <= key && left <= result <= middle
                right = middle;
                //right'' == middle && left'' == left
                //array[right''] <= key & left'' <= result <= right''
                //invariant and before
        }
        //before && invariant && (left' + 1 == right')
        //result == right' &&  ((array[right'] - most close over key || array[right'] == key) || (right == -1 && array[0] < key) || (right == array length && array[len] > key))
        if (right != array.length && array[right] == key) {
            //array[right'] == key && result == right'
            return right;
        } else {
            //array[right'] most close over key || array[0] < key || array[len] > key
            return (-right - 1);
        }
    }
    //after: (array[i]' == array[i]) && (result == 0 && array length == 0 ) || (array[len] > key && result == -(array length - 1)-1) || (result < array len && array[result] <= key && (result == 0  || array[result - 1] > key))

    public static void main(String[] args) {
        int[] ints;
        int key;
        try {
            key = Integer.parseInt(args[0]);
            ints = new int[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                ints[i - 1] = Integer.parseInt(args[i]);
            }
            System.out.println(recursiveBinarySearch(ints, -1, ints.length, key));
            //System.out.println(iterativeBinarySearch(ints, key));
        } catch (NumberFormatException e) {
            System.err.println("Illegal arguments, not numbers");
        } catch (NegativeArraySizeException e) {
            System.err.println("Not enough arguments");
        }
    }
}
