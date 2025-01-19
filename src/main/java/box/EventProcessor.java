package box;

import java.util.*;

class Event {
    int id;
    int timestamp;
    String payload;
    int checksum;

    public Event(int id, int timestamp, String payload, int checksum) {
        this.id = id;
        this.timestamp = timestamp;
        this.payload = payload;
        this.checksum = checksum;
    }
}

class EventProcessor {
    private final Deque<Event> eventQueue = new LinkedList<>();
    // TREEMAP // Ideal Data Strcture
    Map<Integer, String> eventMap = new TreeMap<>(); // timestamp and payload
    Set<Event> eventSet = new TreeSet<>();


    /*
    Insertion logn,
    Deletion log n
    searching log n

    iterate over the treeset 0(n)
     */
    private final int TIME_WINDOW = 60;
    private int startTime = 0;

    public void processEvent(Event event) {

//        if (startTime != 0){
//            startTime = event.timestamp;
//        }

        if (!isValidChecksum(event)) {
            System.out.println("Event " + event.id + ": Invalid checksum");
            return;
        }

//        if (!isEventWithinWindow(event)) {
//            System.out.println("Event " + event.id + ": Ignored, too late received, window starts " +
//                    (eventQueue.isEmpty() ? event.timestamp : eventQueue.peekLast().timestamp - TIME_WINDOW));
//            return;
//        }


        eventQueue.offer(event);
        removeOldEvents();
        calculateAndPrintAverage(event);
    }

    private boolean isValidChecksum(Event event) {
        int sum = 0;
        for (char c : event.payload.toCharArray()) {
            sum += c;
        }
        return sum % 10 == event.checksum;
    }

    private boolean isEventWithinWindow(box.Event event) {
        if (eventQueue.isEmpty()) {
            return true;
        }
        int diff = Math.abs(eventQueue.peekLast().timestamp - startTime);
        if (diff >= 60) {
            return false;
        } else {
            return true;
        }
//        return event.timestamp <= eventQueue.peekLast().timestamp + TIME_WINDOW;
    }

    private void removeOldEvents() {
        while (!eventQueue.isEmpty() && eventQueue.peek().timestamp < (eventQueue.peekLast().timestamp - TIME_WINDOW)) {
            eventQueue.poll();
        }
    }

    private void calculateAndPrintAverage(Event event) {
        if (eventQueue.isEmpty()) {
            return;
        }

        int totalLength = 0;
        for (Event e : eventQueue) {
            totalLength += e.payload.length();
        }

        double average = (double) totalLength / eventQueue.size();
        System.out.println("Event " + event.id + ": average " + String.format("%.2f", average)
                + ", window ends " + event.timestamp);
    }
    public static void main(String[] args) {

        String input1 = "4,456,Box,7\n5,466,AAA,1\n6,506,xyz,3";
        String input2 = "10,456,abcd,4\n11,466,abcde,5\n12,506,abcdef,7\n13,520,a,7\n14,570,abcde,5";
        String input3 = "21,450,abcd,4\n22,460,abcde,5\n23,530,abcdef,7\n24,460,a,7\n25,570,abcd,4";
        String input4 = "31,10,abcd,4\n32,50,abcde,5\n33,30,ab,5\n34,20,abcdef,7\n35,90,ab,5\n36,120,a,7";

        String input5 = "1,10,abc,4\n3,30,def,5\n2,20,ghi,6\n4,70,jkl,7";
        String input6 = "1,10,abc,4\n2,20,def,5\n3,30,ghi,1";
        String input9 = "1,10,abc,4\n2,10,def,5\n3,10,ghi,6";
        String input8 = "1,10,abc,4";

        List<String> inputStrings = new ArrayList<>();

        inputStrings.add(input1);
        inputStrings.add(input2);
        inputStrings.add(input3);
        inputStrings.add(input4);
        inputStrings.add(input5);
        inputStrings.add(input6);
        inputStrings.add(input8);

        inputStrings.add(input9);

        for (String input: inputStrings){
            EventProcessor processor = new EventProcessor();

            String[] events = input.split("\\n");

        for (String eventString : events) {
            String[] parts = eventString.split(",");
            int id = Integer.parseInt(parts[0]);
            int timestamp = Integer.parseInt(parts[1]);
            String payload = parts[2];
            int checksum = Integer.parseInt(parts[3]);

            Event event = new Event(id, timestamp, payload, checksum);
            processor.processEvent(event);

        }

                        System.out.println("----------------------------------------------");

        }
    }
}