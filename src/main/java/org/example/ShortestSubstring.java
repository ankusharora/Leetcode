class Solution {
    public int findKthLargest(int[] nums, int k) {
        List<Integer> unique = new ArrayList<>();

        for (int num: nums){
            unique.add(num);
        }

        quickselect( unique, 0, nums.length -1, k-1);
        return unique.get(k-1);
    }

    private List<Integer> swap(List<Integer> unique, int a, int b){
        int temp = unique.get(a);
        unique.set(a, unique.get(b));
        unique.set(b, temp);
        return unique;
    }

    private void quickselect(List<Integer> unique, int start, int end, int k){
        if (start >= end) return;

        int pivot = start + (int) (Math.random() * (end - start + 1));

        pivot = partition(unique, start, end, pivot);

        if (pivot == k){
            return;
        } else if (pivot < k){
            quickselect(unique, pivot + 1, end, k);
        } else {
            quickselect(unique, start, pivot - 1, k);
        }


    }


    private int partition(List<Integer> unique, int start, int end, int pivot){

        int pivotValue = unique.get(pivot);

        swap(unique, pivot, end);
        int storeIndex = start;
        for (int i = start; i < end; i++){
            if (unique.get(i) > pivotValue){
                swap(unique, storeIndex, i);
                storeIndex++;
            }
        }

        swap(unique, storeIndex, end);
        return storeIndex;

    }
}