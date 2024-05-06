import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        List<Node> nodes = new ArrayList<>();
        System.out.print("Masukkan start word: ");
        Scanner scanner = new Scanner(System.in);
        String startWord = scanner.nextLine();
        startWord = startWord.toLowerCase();
        // System.out.println(startWord);
        if (!Tools.isInputValid(startWord)){
            System.out.println("Input tidak valid");
            scanner.close();
            return;
        }

        String filePath = "src\\Dictionary\\"+startWord.length()+"char.txt";
        try { 
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            String word = null;
            List<String> children = null;
            while ((line = reader.readLine()) != null) {
                if (word == null) {
                    word = line.trim();
                } else if (children == null) {
                    int childCount = Integer.parseInt(line.trim());
                    children = new ArrayList<>();
                    for (int i = 0; i < childCount; i++) {
                        children.add(reader.readLine().trim());
                    }
                    nodes.add(new Node(word, children));
                    word = null;
                    children = null;
                }
        }
        reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Node startNode = Tools.getNode(startWord, nodes);
        if (startNode == null){
            System.out.println("Kata tidak ada di dictionary");
            scanner.close();
            return;
        }

        System.out.print("Masukkan end word: ");
        String endWord = scanner.nextLine();
        Node endNode = Tools.getNode(endWord, nodes);
        if (!Tools.isInputValid(endWord)){
            System.out.println("Input tidak valid");
            scanner.close();
            return;
        } else if (endWord.length() != startWord.length()){
            System.out.println("Panjang kata tidak sama");
            scanner.close();
            return;
        } else if (endNode == null){
            System.out.println("Kata tidak ada di dictionary");
            scanner.close();
            return;
        }
        

        boolean stop = false;
        while (!stop){
            System.out.println("Pilih algoritma: ");
            System.out.print("1. UCS \n2. GBFS\n3. A*\nMasukkan angka: ");
            int choice =0;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                       e.printStackTrace();
            }
            Map <List<String>, Integer> map = new HashMap<List<String>, Integer>();
            List<String> path = null;
            int jumlahNode = 0;
            long startTime = System.nanoTime();

            switch (choice){
                case 1:
                    System.out.println("UCS");
                    map = UCS.findPath(nodes,startNode, endNode);
                    break;
                case 2:
                    System.out.println("GBFS");
                    map = GBFS.findPath(nodes,startNode, endNode);
                    break;
                case 3:
                    System.out.println("A*");
                    map = Astar.findPath(nodes,startNode, endNode);
                    break;
                default:
                    System.out.println("Pilihan tidak valid");
                    break;
            }
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            long milliseconds =(long) elapsedTime / 1000000;
            for (Map.Entry<List<String>, Integer> entry : map.entrySet()) {
                path = entry.getKey();
                jumlahNode = entry.getValue();
            }
            
            if (path != null) {
                System.out.println("Shortest path from " + startWord + " to " + endWord + ":\n" + path);
                System.out.println("Jumlah node yang dikunjungi: " + jumlahNode);
                System.out.println("Elapsed time: " + milliseconds + " ms");
            } else {
                System.out.println("No path found from " + startWord + " to " + endWord);
            }

            System.out.print("Apakah anda ingin mengulang dengan algoritma lain? (y/n) ");
            String next = scanner.next();
            if (next.equals("n")){
                stop = true;
            }    
        }
        scanner.close();
    }
}
