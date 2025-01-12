package Docusign;


import java.util.*;

class RandomizedSet {

    Map<Integer, Integer> map;
    List<Integer> list;

    Random rand = new Random();

    public RandomizedSet() {
        map = new HashMap<>();
        list = new ArrayList<>();
    }

    public boolean insert(int val) {
        if (map.containsKey(val)) return false;

        map.put(val, list.size());
        list.add(list.size(), val);
        return true;
    }

    public boolean remove(int val) {
        if (!map.containsKey(val)) return false;

        int lastElement = list.get(list.size() - 1);
        int index = map.get(val);

        list.set(index, lastElement);
        map.put(lastElement, index);

        list.remove(list.size() - 1);
        map.remove(val);
        return true;
    }

    public int getRandom() {
        return list.get(rand.nextInt(list.size()));
    }
}
