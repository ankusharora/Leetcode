import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class Delivery {
    int dasherId;
    int deliveryId;
    LocalDateTime timestamp;
    Status status;

    public Delivery(int dasherId, int deliveryId, LocalDateTime timestamp, Status status) {
        this.dasherId = dasherId;
        this.deliveryId = deliveryId;
        this.timestamp = timestamp;
        this.status = status;
    }
}

enum Status {
    ACCEPTED, FULFILLED, CANCELLED
}

class JsonParser {
    public static List<Delivery> parseDeliveries(String json) {
        List<Delivery> deliveries = new ArrayList<>();
        json = json.trim();

        // Remove outer brackets
        if (json.startsWith("[") && json.endsWith("]")) {
            json = json.substring(1, json.length() - 1).trim();
        }

        if (json.isEmpty()) {
            return deliveries;
        }

        // Split by "}," to separate each delivery object
        String[] objects = json.split("\\},\\s*");

        for (int i = 0; i < objects.length; i++) {
            String obj = objects[i];
            // Add back curly braces if removed by split
            if (!obj.startsWith("{")) {
                obj = "{" + obj;
            }
            if (i < objects.length - 1 && !obj.endsWith("}")) {
                obj = obj + "}";
            }

            // Parse the individual object
            Delivery delivery = parseSingleDelivery(obj);
            if (delivery != null) {
                deliveries.add(delivery);
            }
        }
        return deliveries;
    }

    private static Delivery parseSingleDelivery(String jsonObj) {
        jsonObj = jsonObj.replace("{", "").replace("}", "").replace("\"", "").trim();
        String[] fields = jsonObj.split(",\\s*");

        int dasherId = 0, deliveryId = 0;
        LocalDateTime timestamp = null;
        Status status = null;

        for (String field : fields) {
            String[] keyValue = field.split(":\\s*", 2); // Split only on first colon
            if (keyValue.length < 2) continue;

            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            switch (key) {
                case "dasher_id":
                    dasherId = Integer.parseInt(value);
                    break;
                case "delivery_id":
                    deliveryId = Integer.parseInt(value);
                    break;
                case "timestamp":
                    timestamp = LocalDateTime.parse(value);
                    break;
                case "status":
                    status = Status.valueOf(value);
                    break;
            }
        }

        if (timestamp != null && status != null) {
            return new Delivery(dasherId, deliveryId, timestamp, status);
        }
        return null;
    }
}

class DasherPayoutCalculator {
    private static final double BASE_PAY_PER_MINUTE = 0.3;

    public static double calculatePayout(List<Delivery> deliveries) {
        double totalPayout = 0.0;
        int activeDeliveries = 0;
        LocalDateTime previousTimestamp = null;

        for (Delivery delivery : deliveries) {
            if (previousTimestamp != null) {
                long minutesElapsed = Duration.between(previousTimestamp, delivery.timestamp).toMinutes();
                if (minutesElapsed > 0 && activeDeliveries > 0) {
                    totalPayout += minutesElapsed * BASE_PAY_PER_MINUTE * activeDeliveries;
                }
            }
            previousTimestamp = delivery.timestamp;

            if (delivery.status == Status.ACCEPTED) {
                activeDeliveries++;
            } else if (delivery.status == Status.FULFILLED || delivery.status == Status.CANCELLED) {
                activeDeliveries = Math.max(0, activeDeliveries - 1); // Prevent negative count
            }
        }

        return totalPayout;
    }
}

public class SolutionWithParser {
    public static void main(String[] args) {
        String json = """
        [
            {"dasher_id":1, "delivery_id":1, "timestamp":"2025-03-04T18:01:00", "status":"ACCEPTED"},
            {"dasher_id":1, "delivery_id":2, "timestamp":"2025-03-04T18:04:00", "status":"ACCEPTED"},
            {"dasher_id":1, "delivery_id":3, "timestamp":"2025-03-04T18:13:00", "status":"FULFILLED"},
            {"dasher_id":1, "delivery_id":4, "timestamp":"2025-03-04T18:23:00", "status":"CANCELLED"}
        ]
        """;

        List<Delivery> deliveries = JsonParser.parseDeliveries(json);
        double payout = DasherPayoutCalculator.calculatePayout(deliveries);
        System.out.printf("Total payout: $%.2f%n", payout);
    }
}