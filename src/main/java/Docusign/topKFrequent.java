package Docusign;

import java.util.*;

class topKFrequent {
    public int[] topKFrequent(int[] nums, int k) {
        // Get frequency

        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        List<Map.Entry<Integer, Integer>> counts = new ArrayList<>(freqMap.entrySet());
        int n = counts.size();
        quickselect(0, n - 1, n - k, counts);

        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = counts.get(n - k + i).getKey();
        }
        return result;
    }

    public void quickselect(int left, int right, int k_target_index, List<Map.Entry<Integer, Integer>> counts) {
        if (left == right) {
            return;
        }

        int pivot_index = partition(left, right, counts);

        if (k_target_index == pivot_index) {
            return;
        } else if (k_target_index < pivot_index) {
            quickselect(left, pivot_index - 1, k_target_index, counts);
        } else {
            quickselect(pivot_index + 1, right, k_target_index, counts);
        }
    }

    public int partition(int left, int right, List<Map.Entry<Integer, Integer>> counts) {
        Random rand = new Random();
        int pivot_index = rand.nextInt(right - left + 1) + left;
        int pivot_value = counts.get(pivot_index).getValue();

        Collections.swap(counts, pivot_index, right);

        int i = left;
        for (int j = left; j < right; j++) {
            if (counts.get(j).getValue() < pivot_value) {
                Collections.swap(counts, i, j);
                i++;
            }
        }

        Collections.swap(counts, i, right);
        return i;
    }


// Easy way to do it
    public int[] minHeap(int[] nums, int k) {
        if (k == nums.length){
            return nums;
        }

        Map<Integer, Integer> count = new HashMap();

        for (int n: nums){
            count.put(n, count.getOrDefault(n, 0) + 1);
        }

        Queue<Integer> heap = new PriorityQueue<>(
                (a,b) -> count.get(a) - count.get(b)
        );

        for (int n: count.keySet()){
            heap.add(n);
            if (heap.size() > k){
                heap.poll();
            }
        }

        int[] top = new int[k];
        for(int i = k - 1; i >= 0; --i) {
            top[i] = heap.poll();
        }
        return top;
    }

}