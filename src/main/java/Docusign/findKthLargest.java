package Docusign;

import java.util.Random;

class findKthLargest {
    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        return quickSelect(nums, 0, n -1, n - k);

    }

    public int quickSelect(int[] nums, int left, int right, int k) {
        if (left == right) {
            return nums[left];
        }

        Random rand = new Random();

        int pivotIndex = left + rand.nextInt(right - left + 1);

        pivotIndex = partition(nums, left, right, pivotIndex);

        if (k == pivotIndex) {
            return nums[k];
        } else if (k < pivotIndex) {
            return quickSelect(nums, left, pivotIndex - 1, k);
        } else {
            return quickSelect(nums, pivotIndex + 1, right, k);
        }
    }

    public static int partition(int[] nums, int left, int right, int pivotIndex) {
        int pivotValue = nums[pivotIndex];

        swap(nums, pivotIndex, right);

        int storeIndex = left;

        for (int i = left; i < right; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, storeIndex, i);
                storeIndex++;
            }
        }

        // move pivot to final place

        swap(nums, storeIndex, right);
        return storeIndex;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}