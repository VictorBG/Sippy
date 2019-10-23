import java.util.*;

public class LZW {
    /** Compress a string to a list of output symbols. */
    public static List<Integer> compress(String uncompressed) {
        int size = 256;
        Map<String,Integer> dictionary = new HashMap<String,Integer>();
        for (int i = 0; i < size; i++){
            dictionary.put("" + (char)i, i);
        }

        String w = "";
        List<Integer> result = new ArrayList<Integer>();
        for (char c : uncompressed.toCharArray()) {
            String wc = w + c;
            if (dictionary.containsKey(wc)) w = wc;
            else {
                result.add(dictionary.get(w));
                dictionary.put(wc, size++);
                w = "" + c;
            }
        }

        if (!w.equals("")) result.add(dictionary.get(w));

        return result;
    }

    public static String decompress(List<Integer> compressed) {
        int size = 256;
        Map<Integer,String> dictionary = new HashMap<Integer,String>();
        for (int i = 0; i < size; i++){
            dictionary.put(i, "" + (char)i);
        }
        String w = "" + (char)(int)compressed.remove(0);
        StringBuffer result = new StringBuffer(w);
        for (int k : compressed) {
            String entry;
            if (dictionary.containsKey(k))
                entry = dictionary.get(k);
            else if (k == size){
                entry = w + w.charAt(0);
            }
            else{
                throw new IllegalArgumentException("Bad compressed k: " + k);
            }

            result.append(entry);

            // Add w+entry[0] to the dictionary.
            dictionary.put(size++, w + entry.charAt(0));

            w = entry;
        }
        return result.toString();
    }

//    public static void main(String[] args) {
//        List<Integer> compressed = compress("HOLA QUE TAL");
//        System.out.println(compressed);
//        String decompressed = decompress(compressed);
//        System.out.println(decompressed);
//    }
}