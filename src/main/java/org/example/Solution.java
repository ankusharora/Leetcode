//class Solution {
//    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
//
//        List<int[]> result = new ArrayList<>();
//
//        int l1= 0;
//        int l2 = 0;
//
//
//        while (l1 < firstList.length && l2 < secondList.length){
//
//            int[] list1 = firstList[l1];
//            int[] list2 = secondList[l2];
//
//            int low = Math.max(list1[0], list2[0]);
//            int high = Math.min(list1[1], list2[1]);
//
//            if (low <= high){
//                result.add(new int[] {low, high});
//            }
//
//            if (list1[1] < list2[1]){
//                l1++;
//            } else {
//                l2++;
//            }
//        }
//
//
//        return result.toArray(new int[result.size()][]);
//    }
//}