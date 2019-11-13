package domain.algorithms.lzss;
/**
 * Author: Miguel Angel Cabrera
 *
 * Class representing the String line far away of lookAhead
 *
 */
public class InputString {
    char[] input;

    public InputString(String s) {
        input = new char[10];
        for (int i = 0; i<10 && i<s.length(); i++) {
            input[i] = s.charAt(i);
        }
    }

    public char nextChar() {
        //mark <EOF>
        if (input[0] == '.') {
            input = null;
            return '.';
        }
        else {
            this.shiftLeft();
            return input[0];
        }
    }

    public void shiftLeft() {

        for (int i = 1; i<input.length; i++) {
            char temp = input[i-1];
            input[i-1] = input[i];
        }
    }
}
