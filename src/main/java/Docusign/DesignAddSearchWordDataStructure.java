//package Docusign;
//
//import java.util.*;
//
//public class DesignAddSearchWordDataStructure {
//    class TrieNode {
//        Map<Character, TrieNode> children = new HashMap<>();
//        boolean isWord = false;
//    }
//
//    TrieNode trieNode;
//
//    public DesignAddSearchWordDataStructure() {
//        trieNode = new TrieNode();
//    }
//
//    public void addWord(String word) {
//        TrieNode node = trieNode;
//
//        for( char ch : word.toCharArray()) {
//            if(!node.children.containsKey(ch)) {
//                node.children.put(ch, new TrieNode());
//            }
//            node = node.children.get(ch);
//        }
//        node.isWord = true;
//    }
//    public boolean search(String word) {
//        return searchInNode(word, trieNode);
//    }
//
//
//    public boolean searchInNode(String word, TrieNode node) {
//        for(int i=0; i<word.length(); i++) {
//            char ch = word.charAt(i);
//
//            if(!node.children.containsKey(ch)) {
//                if(ch == '.') {
//                    for( char x : node.children.keySet()) {
//                        TrieNode child = node.children.get(x);
//                        if(searchInNode(word.substring(i+1), child))){
//                            return true;
//                        }
//                    }
//                }
//                return false;
//            } else {
//                node = node.children.get(ch);
//            }
//        }
//        return node.isWord;
//    }
//}
