import java.util.*;

public class Astar {
    public static Map<List<String>, Integer> findPath(List<Node> nodes, Node startNode, Node endNode) {
        String startWord = startNode.getWord();
        String endWord = endNode.getWord();
        PriorityQueue<Node> prioQue = new PriorityQueue<>((n1, n2) -> Integer.compare(n1.Astarcost(startWord,endWord), n2.Astarcost(startWord,endWord)));
        Map<String, Integer> explored = new HashMap<>();
        Map<String, String> parentMap = new HashMap<>();
        prioQue.offer(startNode);
        
        while (!prioQue.isEmpty()) {
            Node current = prioQue.poll();
            String currentWord = current.getWord();
            int currentCost = current.Astarcost(startWord, endWord);

            if (currentWord.equals(endWord)) {
                Map<List<String>, Integer> ret = new HashMap<>();
                ret.put(constructPath(parentMap, startWord, endWord), explored.size());
                return ret;
            }
            try{
            if (!explored.containsKey(currentWord) || currentCost > explored.get(currentWord)) {
                explored.put(currentWord, currentCost);

                for (String child : current.getChildren()) {
                    Node currentChild = Tools.getNode(child, nodes);
                    int newCost = currentChild.Astarcost(startWord, endWord); // cost so far
                    if (!explored.containsKey(child) || newCost > explored.get(child)) {
                        prioQue.offer(currentChild);
                        if (!parentMap.containsKey(currentChild.getWord())) {
                            parentMap.put(child, currentWord);
                        }
                    }
                }
            }} catch(Exception e){}
        }
        return new HashMap<>();
    }

    private static List<String> constructPath(Map<String, String> parentMap, String startWord, String endWord) {
        List<String> path = new ArrayList<>();
        String currentWord = endWord;
        while (currentWord != null && !currentWord.equals(startWord)) {
            path.add(0, currentWord);
            currentWord = parentMap.get(currentWord);
        }
        if (currentWord != null && currentWord.equals(startWord)) {
            path.add(0, currentWord);
        }
        return path;
    }
}
