public class SumLongHex {
    static long add(String s) {
        s = s.toUpperCase();
        if (s.startsWith("0X")) {
            return Long.parseUnsignedLong(s.substring(2), 16);
        }
        return Long.parseLong(s);
    }

    public static void main(String[] args) {
        long ans = 0L;

        for (int ind = 0; ind < args.length; ind++) {
            String s = args[ind];
            int l = 0;
            for (int r = 0; l < s.length(); r = l) {
                if (!Character.isWhitespace(s.charAt(l))) {
                    while (r < s.length() && !Character.isWhitespace(s.charAt(r))) {
                        ++r;
                    }

                    ans += add(s.substring(l, r));
                }
                l = r + 1;
            }
        }
        System.out.println(ans);
    }
}