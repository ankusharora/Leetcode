package Docusign;

/**
 * TrieNode: A node contains an array of 26 children (for 'a' to 'z') and a flag isEndOfWord to indicate the end of a word.
 * Insert: Traverse the characters of the word, creating nodes as needed in the children array. Mark the last node as the end of the word.
 * Search: Traverse the word's characters. If a character's node is missing, return false. At the end, check if isEndOfWord is true.
 * StartsWith: Similar to search but doesn't check isEndOfWord. Return true if all prefix characters exist in the Trie.
 */
public class ImplementTrie {
    private class TrieNode {
        TrieNode[] children;
        boolean isEndOfWord;

        public TrieNode() {
            children = new TrieNode[26];
            isEndOfWord = false;
        }
    }

    private TrieNode root;

    public ImplementTrie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';

            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isEndOfWord = true;
    }

    public boolean search(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';
            if (node.children[index] == null) {
                return false;
            }
            node = node.children[index];
        }
        return node.isEndOfWord;
    }

    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            int index = ch - 'a';
            if (node.children[index] == null) {
                return false;
            }
            node = node.children[index];
        }
        return true;
    }

    public static void main(String args[]) {
        ImplementTrie implementTrie = new ImplementTrie();
        implementTrie.insert("apple");
        boolean exist = implementTrie.search("apple");
        System.out.println("apple exists : "+exist);
    }
}
