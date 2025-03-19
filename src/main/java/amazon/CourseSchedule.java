//package amazon;
//import java.util.*;
//
//
//public class CourseSchedule {
//    // COURSE SCHEDULE 1
//    public boolean canFinish(int numCourses, int[][] prerequisites) {
//
//        List<List<Integer>> graph = new ArrayList<>();
//        int[] inDegree = new int[numCourses];
//
//
//        for (int i = 0; i < numCourses; i++){
//            graph.add(new ArrayList<>());
//        }
//
//        for (int[] pre: prerequisites){
//            int course = pre[0];
//            int prereq = pre[1];
//
//            graph.get(course).add(prereq);
//            inDegree[prereq]++;
//        }
//
//        Queue<Integer> queue = new LinkedList<>();
//
//        for (int i = 0; i < numCourses; i++){
//            if (inDegree[i] == 0){
//                queue.add(i);
//            }
//        }
//
//        int processedCourse = 0;
//
//        while (!queue.isEmpty()){
//            int currentCourse = queue.poll();
//            processedCourse++;
//
//            for (int nei: graph.get(currentCourse)){
//                inDegree[nei]--;
//                if (inDegree[nei] == 0){
//                    queue.add(nei);
//                }
//            }
//        }
//
//        return processedCourse == numCourses;
//    }
//
//    // COURSE SCHEDULE 2
//    public int[] findOrder(int numCourses, int[][] prerequisites) {
//        int[] inDegree = new int[numCourses];
//
//        List<List<Integer>> adj = new ArrayList<>();
//
//        for (int i = 0; i < numCourses; i++) {
//            adj.add(new ArrayList<>());
//        }
//
//        for (int[] pre : prerequisites) {
//            inDegree[pre[1]]++;
//            adj.get(pre[0]).add(pre[1]);
//        }
//
//        Queue<Integer> queue = new LinkedList<>();
//        for (int i = 0; i < numCourses; i++) {
//            if (inDegree[i] == 0) {
//                queue.add(i);
//            }
//        }
//
//        int finish = 0;
//        int[] output = new int[numCourses];
//
//        while (!queue.isEmpty()) {
//            int currentCourse = queue.poll();
//            output[numCourses - finish - 1] = currentCourse;
//            finish++;
//
//            for (int nei : adj.get(currentCourse)) {
//                inDegree[nei]--;
//                if (inDegree[nei] == 0) {
//                    queue.add(nei);
//                }
//            }
//        }
//
//        if (finish != numCourses) {
//            return new int[0];
//        }
//
//        return output;
//    }
//}
