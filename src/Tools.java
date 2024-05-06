
import java.util.*;
public class Tools {
    public static boolean isSameLength(String s1, String s2) {
        return s1.length() == s2.length();
    }
    
    public static boolean isInputValid(String s){
        return s.matches("^[a-zA-Z]+$");
    }

    public static Node getNode(String s, List<Node> nodes){
        for (Node node : nodes) {
            if (node.word.equals(s)) {
                return node;
            }
        }
        return null;
    }
}
