import java.util.*;

public class Node {
    String word;
    List<String> children;

    public Node(String word, List<String> children) {
        this.word = word;
        this.children = children;
    }

    public String getWord() {
        return word;
    }

    public List<String> getChildren() {
        return children;
    }
    public int UCScost(String startWord){
        return diffCount(startWord);
    }
    public int diffCount(String word) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != this.getWord().charAt(i)) {
                count++;
            }
        }
        return count;
    }
    public int sameCount(String word) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != this.getWord().charAt(i)) {
                count++;
            }
        }
        return count;
    }
    public int GBFScost(String endword){
        return sameCount(endword);
    }

    public int Astarcost(String startWord, String endword){
        return diffCount(startWord)+sameCount(endword);
    }
}
