import java.util.*;

public class OptimalResourceAllocation {
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        List<Map.Entry<String, Integer>> top5 = new ArrayList<>();
    }

    private TrieNode root;
    private Map<String, Integer> frequencyMap;

    public AutoCompleteSystem() {
        root = new TrieNode();
        frequencyMap = new HashMap<>();
    }

    public void insertOrUpdate(String word, int freq) {
        frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + freq);
        int currentFreq = frequencyMap.get(word);

        TrieNode curr = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (curr.children[idx] == null) {
                curr.children[idx] = new TrieNode();
            }
            curr = curr.children[idx];
            updateTop5(curr, word, currentFreq);
        }
    }

    private void updateTop5(TrieNode node, String word, int freq) {
        node.top5.removeIf(entry -> entry.getKey().equals(word));
        node.top5.add(new AbstractMap.SimpleEntry<>(word, freq));

        node.top5.sort((a, b) -> {
            if (!a.getValue().equals(b.getValue())) {
                return b.getValue() - a.getValue();
            }
            return a.getKey().compareTo(b.getKey());
        });

        if (node.top5.size() > 5) {
            node.top5.remove(5);
        }
    }

    public List<String> getTop5(String prefix) {
        TrieNode curr = root;
        List<String> result = new ArrayList<>();

        for (char c : prefix.toCharArray()) {
            int idx = c - 'a';
            if (curr.children[idx] == null) {
                return result;
            }
            curr = curr.children[idx];
        }

        for (Map.Entry<String, Integer> entry : curr.top5) {
            result.add(entry.getKey());
        }
        return result;
    }
}