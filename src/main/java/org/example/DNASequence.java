//package org.example;
//
//import java.util.Stack;
//
///**
// *
// * Input - TTTTTGGTCCTTTA
// * Output -
// * T5
// * G2
// * T1
// * C2
// * T3
// * A1
// *
// * AB --> A1B1
// *
// * Stack<Char, count>
// *
// *
// * T -
// *
// * G - 2
// *  ArrayDeque
// */
//
//public class DNASequence {
//
//    public String compression(String input){
//        Stack<Pair<Character, Integer>> stack = new Stack<>();
//        for (char c: input.toCharArray()){
//            if (stack.isEmpty()){
//                stack.push(new Pair(c, 1));
//            }
//
//            Pair currentPair = stack.peek();
//            char currChar = currentPair.getKey();
//            if (currChar == c) {
//                Integer currentCount = currentPair.getValue();
//
//                stack.pop();
//                stack.push(new pair(currChar, currentCount + 1));
//            } else {
//                stack.push(new Pair(c, 1));
//            }
//        }
//
//        StringBuilder sb = new StringBuilder();
//
//        while(!stack.isEmpty()){
//
//        }
//
//        return sb.toString();
//    }
//}
