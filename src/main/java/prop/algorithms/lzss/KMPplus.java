package prop.algorithms.lzss;

/**
 *  The {@code KMP} class finds the first occurrence of a pattern string
 *  in a text string.
 *  <p>
 *  This implementation uses a version of the Knuth-Morris-Pratt substring search
 *  algorithm. The version takes time proportional to <em>n</em> + <em>m R</em>
 *  in the worst case, where <em>n</em> is the length of the text string,
 *  <em>m</em> is the length of the pattern, and <em>R</em> is the alphabet size.
 *  It uses extra space proportional to <em>m R</em>.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/53substring">Section 5.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class KMPplus {
    private String pattern;
    private int[] next;

    /**
     * Preprocesses the pattern string.
     *
     * @param pattern the pattern string
     */
    public KMPplus(String pattern) {
        this.pattern = pattern;
        int m = pattern.length();
        next = new int[m];
        int j = -1;
        for (int i = 0; i < m; i++) {
            if (i == 0)                                      next[i] = -1;
            else if (pattern.charAt(i) != pattern.charAt(j)) next[i] = j;
            else                                             next[i] = next[j];
            while (j >= 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j];
            }
            j++;
        }
    }

    /**
     * Returns the index of the first occurrrence of the pattern string
     * in the text string.
     *
     * @param  text the text string
     * @return the index of the first occurrence of the pattern string
     *         in the text string; N if no such match
     */
    public int search(String text) {
        int m = pattern.length();
        int n = text.length();
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            while (j >= 0 && text.charAt(i) != pattern.charAt(j))
                j = next[j];
            j++;
        }
        if (j == m) return i - m;
        return n;
    }


    /**
     * Takes a pattern string and an input string as command-line arguments;
     * searches for the pattern string in the text string; and prints
     * the first occurrence of the pattern string in the text string.
     *
     */
    public static EncodedString searchKMPplus(String pattern, String text) {

        int m = pattern.length();
        int n = text.length();

        // substring search
        KMPplus kmp = new KMPplus(pattern);
        int offset = kmp.search(text);

        int indexMatch = 0;
        int indexMatchAnt = 0;
        int i = 1;
        String patSub = pattern.substring(0,i);
        boolean match = false;
        do {
            indexMatchAnt = indexMatch;
            patSub = pattern.substring(0,i);
            kmp = new KMPplus(patSub);
            indexMatch = kmp.search(text);
            i++;
        }
        while (indexMatch >= indexMatchAnt && i<=pattern.length() && indexMatch != text.length());
        i = i-2;
        EncodedString es = new EncodedString();
        es.setLength( (short)i);
        es.setOffset( (short)indexMatchAnt);
        return es;

    }

}
