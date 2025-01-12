//package Docusign;
//
//public class CloneGraph {
//
//    public Node cloneGraph(Node node) {
//
//        Map<Node, Node> copyMap = new HashMap<>();
//
//        return dfs(copyMap, node);
//    }
//
//    public Node dfS(Map<Node, Node> copyMap, Node node){
//        if (node == null) return null;
//
//        if (copyMap.containsKey(node)) return copyMap.get(node);
//
//        Node copy = new Node(node.val);
//        copyMap.put(node, copy);
//
//        for (Node nei: node.neighbors){
//            copy.neighbors.add(dfs(copyMap, nei));
//        }
//
//        return copy;
//
//    }
//}
