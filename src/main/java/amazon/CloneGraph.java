//package amazon;
//
//import java.util.*;
//
//
//public class CloneGraph {
//
//    public Node cloneGraph(Node node) {
//
//        Map<Node, Node> copyMap = new HashMap<>();
//
//        return dfS(copyMap, node);
//    }
//
//     public Node dfS(Map<Node, Node> copyMap, Node node){
//        if (node == null) return null;
//
//        if (copyMap.containsKey(node)) return copyMap.get(node);
//
//        Node copy = new Node(node.val);
//        copyMap.put(node, copy);
//
//        for (Node nei: node.neighbors){
//            copy.neighbors.add(dfS(copyMap, nei));
//        }
//
//        return copy;
//
//    }
//
//
//    class Node {
//        public int val;
//        public List<amazon.Node> neighbors;
//        public Node() {
//            val = 0;
//            neighbors = new ArrayList<amazon.Node>();
//        }
//        public Node(int _val) {        val = _val;
//            neighbors = new ArrayList<amazon.Node>();
//        }
//        public Node(int _val, ArrayList<amazon.Node> _neighbors) {
//            val = _val;
//            neighbors = _neighbors;
//        }
//    }
//
//}