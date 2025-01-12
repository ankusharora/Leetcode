package box;

import java.util.*;

public class TopKFrequentWords {

    public List<String> topKFrequent (String[] words, int k){
        Map<String, Integer> freMap = new HashMap<>();

        for (String word: words){
            freMap.put(word, freMap.getOrDefault(word, 0) + 1);
        }

        PriorityQueue<String> minHeap = new PriorityQueue<>(
                (a,b) -> freMap.get(a).equals(freMap.get(b)) ? b.compareTo(a) : freMap.get(a) - freMap.get(b)
        );

        for (String word: freMap.keySet()){
            minHeap.offer(word);
            if (minHeap.size() > k){
                minHeap.poll();
            }
        }
        List<String> result = new ArrayList<>();

        while (!minHeap.isEmpty()){
            result.add(minHeap.poll());
        }

        Collections.reverse(result);
        return result;

    }
}
