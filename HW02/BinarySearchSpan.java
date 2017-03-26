public class BinarySearchSpan {
    // Pre: a[i] >= a[i + 1] for all i even if empty
    static int binIter(int[] a, int key) {
        // Pre
        int l = -1, r = a.length;
        // Inv: (r' == a.size || (r' < a.size && a[r'] <= key)) && (l' == -1 || (0 <= l' < a.size && a[l'] > key)) &&
        //       && (r'' - l'' <= (r' - l' + 1) div 2) && (r' - l' >= 1)
        while (r - l > 1) {
            // Pre && Inv && r' > l' + 1
            int m = (l + r) / 2;
            // Pre && Inv && r' > l' + 1 && m == (r' + l') div 2 && l' < m < r'
            if (a[m] <= key) {
                // Pre && Inv && r' > l' + 1 && m = (r' + l') div 2 && a[m] <= key && l' < m < r'
                r = m;

                // r'' == m && l'' = l'  ->  m - l'' == (l' + r') div 2 - l' == (r' - l' + 1) div 2
                // l'' = l'  ->  (l'' == -1 || (0 <= l'' < a.size && a[l''] > key))
                // Pre && a[r'' == m] <= key && l' < m < r'  -> (r'' < a.size && a[r''] <= key)
                // r' > l' + 1 && r'' == m && l' < m < r'  ->   r'' - l'' >= 1

                // Pre && Inv
            } else {
                // Pre && Inv && r' > l' + 1 && m = (r' + l') div 2 && a[m] > key
                l = m;

                // l'' == m && r'' = r'  -> r'' - m == r' - (r' + l') div 2 == (r' - l' + 1) div 2
                // r'' == r'  ->  ((r'' == a.size) || (r'' < a.size && a[r''] <= key))
                // Pre && a[l'' == m] > key && l' < m < r'  ->  (0 <= l'' < a.size && a[l''] > key)
                // r' > l' + 1 && l'' = m && l' < m < r'  ->  r'' - l'' >= 1

                // Pre && Inv
            }
        }
        // !((r' - l') > 1) && Inv(r' - l' >= 1) -> l' + 1 == r'
        // Pre && Inv && (l' + 1 == r')
        // 1) a.size == 0 -> never visited while ->  r' == 0 && l' == -1
        // 2) a.last > x -> never visited if() in while()  ->  r' == a.size() && l' = a.size() - 1
        // 3) r' < a.size && Inv ->  a[r'] <= key
        // 3a) r' == 0
        // 3b) r' > 0 && Inv && (l' + 1 == r') -> a[r' - 1] > key
        return r;
    }
    // Post: (a[i]' == a[i]) && (a.size == 0 && ans == 0) || (a.last > x && ans == a.size) || (ans < a.size && a[ans] <= key && (ans == 0 || a[ans - 1] > key))

    // Pre: (a[i]' == a[i]) && (a[i] >= a[i + 1] for all i) && (r == a.size || (r < a.size && a[r] < key)) &&
    // && (l == -1 || (0 <= l < a.size && a[l] >= key)) && (r' - l' <= (r - l + 1) div 2) && (r - l >= 1)
    static int binRec(int[] a, int key, int l, int r) {
        if (r - l == 1) {
            // Pre && l + 1 == r
            // 1) a.size == 0 -> it is first level of recursion ->  r == 0 && l == -1
            // 2) a[0] < key -> never visited else() in previous recursions  -> r == 0 && l == -1
            // 3) l != -1  ->  a[l] >= key
            // 3a) l == n - 1
            // 3b) l < n - 1 && Inv && (l + 1 == r) -> a[l + 1] < key
            return l;
        } else {

            // Pre && r > l + 1
            int m = (l + r) / 2;
            // Pre && r > l + 1 && m == (l + r) div 2 && l < m < r
            if (a[m] < key) {
                // Pre && r > l + 1 && m == (l + r) div 2 && l < m < r && a[m] < key
                // a[i]' == a[i] && a[i] >= a[i + 1] for all i && key' == key
                // l' == l  ->  (l == -1 || (0 <= l < a.size && a[l] >= key))
                // Pre && a[m] < key && l < m < r  ->  (r < a.size && a[r] < key)
                // r' == m && l' = l  ->  m - l' == (l + r) div 2 - l == (r - l + 1) div 2
                // r > l + 1 && r' == m && l < m < r  ->   r' - l' >= 1
                // Pre for l' and r'
                return BinRec(a, key, l, m);
                // Post was OK before and a[i] hasn't changed for all i
            } else {
                // Pre && r > l + 1 && m = (r + l) div 2 && a[m] >= key
                // a[i]' == a[i] && a[i] >= a[i + 1] for all i && key' = key
                // l' == m && r' = r  -> r' - m == r - (r + l) div 2 == (r - l + 1) div 2
                // r' == r  -> ((r == a.size) || (r' < a.size && a[r'] < key))
                // Pre && a[l' == m] >= key && l < m < r  ->  (0 <= l' < a.size && a[l'] >= key)
                // r > l + 1 && l' = m && l < m < r  ->  r' - l' >= 1
                // Pre for l' and r'
                return BinRec(a, key, m, r);
                // Post was OK before and a[i] hasn't changed for all i
            }
        }
    }
    // Post: (key' == key) && (a[i]' == a[i]) && (a.size == 0 || (a[0] < key)) && ans == -1) && (a[ans] >= key && (ans == a.size() - 1 || a[ans + 1] < key)

    public static void main(String[] args) {
        int n = args.length - 1;
        int key = Integer.parseInt(args[0]);
        int[] a = new int[n];
        for (int i = 1; i < n + 1; i++) {
            a[i - 1] = Integer.parseInt(args[i]);
        }
        int r = BinRec(a, key, -1, n);
        int l = BinIter(a, key);
        if (l == n || a[l] != key) {
            System.out.println(l + " 0");
        } else {
            System.out.println(l + " " + (r - l + 1));
        }
    }
}