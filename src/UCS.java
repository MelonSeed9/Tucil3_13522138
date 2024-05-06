import java.util.*;

public class UCS {
        public static Map<List<String>, Integer> findPath(List<Node> nodes, Node startNode, Node endNode) {
        String startWord = startNode.getWord();
        PriorityQueue<Node> prioQue = new PriorityQueue<>((n1, n2) -> Integer.compare(n1.UCScost(startWord), n2.UCScost(startWord)));
        Map<String, Integer> explored = new HashMap<>();
        Map<String, String> parentMap = new HashMap<>();
        prioQue.offer(startNode);
        while (!prioQue.isEmpty()) {
            Node current = prioQue.poll();
            String currentWord = current.getWord();
            int currentCost = current.UCScost(startWord);

            if (currentWord.equals(endNode.getWord())) {
                Map<List<String>, Integer> ret = new HashMap<List<String>, Integer>();
                ret.put(constructPath(parentMap,startNode, endNode), explored.size());
                return ret;
            }

            if (!explored.containsKey(currentWord) || currentCost > explored.get(currentWord)){ {
                
                explored.put(currentWord, currentCost);

                for (String child : current.getChildren()) {
                    Node currentChild = Tools.getNode(child, nodes);
                    int newCost = currentChild.UCScost(startWord);
                    if (!explored.containsKey(child) || newCost > explored.get(child)) {
                        prioQue.offer(currentChild);
                        if(!parentMap.containsKey(currentChild.getWord()))    {
                        parentMap.put(child, currentWord);
                        }
                    }
                }
            }
        }
    }
    return new HashMap<>(); 
}

    private static List<String> constructPath(Map<String, String> parentMap,Node startNode, Node endNode) {
        List<String> path = new ArrayList<>();  
        String currentWord = endNode.getWord();
        while (currentWord != null) {
            path.add(0, currentWord);
            currentWord = parentMap.get(currentWord);
        }
        return path;
    }

    
}
