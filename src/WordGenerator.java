import java.io.*;
import java.util.*;

public class WordGenerator {

    public static void main(String[] args) {
        // Change this file path as needed
        String inputFilePath = "src\\Dictionary\\words.txt";

        try {
            // Read words from input file
            List<String> words = readWordsFromFile(inputFilePath);

            // Organize words by length
            Map<Integer, List<String>> wordsByLength = organizeWordsByLength(words);

            // Generate and write child words for each length
            for (Map.Entry<Integer, List<String>> entry : wordsByLength.entrySet()) {
                int length = entry.getKey();
                List<String> wordsOfLength = entry.getValue();
                generateAndWriteChildWords(wordsOfLength, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read words from file
    private static List<String> readWordsFromFile(String filePath) throws IOException {
        List<String> words = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");
            for (String word : parts) {
                if (word.matches("[a-zA-Z]+") && !word.matches(".*\\d+.*")) { // Ignore words with numbers
                    words.add(word.toLowerCase());
                }
            }
        }
        reader.close();
        return words;
    }

    // Menyusun kata berdasarkan panjangnya
    private static Map<Integer, List<String>> organizeWordsByLength(List<String> words) {
        Map<Integer, List<String>> wordsByLength = new HashMap<>();
        for (String word : words) {
            int length = word.length();
            List<String> wordsOfLength = wordsByLength.getOrDefault(length, new ArrayList<>());
            wordsOfLength.add(word);
            wordsByLength.put(length, wordsOfLength);
        }
        return wordsByLength;
    }

    // Buat file txt sesuai panjang kata
    private static void generateAndWriteChildWords(List<String> words, int length) throws IOException {
        String outputFilePath = "src\\Dictionary\\" + length + "char.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
        for (String word : words) {
            List<String> children = generateChildWords(word, words);
            writer.write(word + "\n");
            writer.write(children.size() + "\n");
            for (String child : children) {
                writer.write(child + "\n");
            }
        }
        writer.close();
    }

    // Buat child dari kata di list yang sama yang hanya berbeda satu huruf dengan parent
    private static List<String> generateChildWords(String word, List<String> words) {
        List<String> children = new ArrayList<>();
        for (String otherWord : words) {
            if (word.equals(otherWord)) continue;
            if (isOneCharDifferent(word, otherWord)) {
                children.add(otherWord);
            }
        }
        return children;
    }

    // Mengecek apakah dua kata hanya beda 1 huruf
    private static boolean isOneCharDifferent(String word1, String word2) {
        if (word1.length() != word2.length()) return false;
        int diffCount = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                diffCount++;
                if (diffCount > 1) return false;
            }
        }
        return diffCount == 1;
    }
}
