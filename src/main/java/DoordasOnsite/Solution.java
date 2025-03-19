package DoordasOnsite;

import org.json.JSONArray;
import org.json.JSONObject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

class ExternalService {
    public static String fetchDeliveriesJson() {
        return """
        [
            {"dasher_id": 1, "delivery_id": 1, "timestamp": "2025-03-04T18:01:00", "status": "ACCEPTED"},
            {"dasher_id": 1, "delivery_id": 2, "timestamp": "2025-03-04T18:04:00", "status": "ACCEPTED"},
            {"dasher_id": 1, "delivery_id": 3, "timestamp": "2025-03-04T18:13:00", "status": "FULFILLED"},
            {"dasher_id": 1, "delivery_id": 4, "timestamp": "2025-03-04T18:23:00", "status": "CANCELLED"}
        ]
        """;
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

                /**
                 * For prorated minutes:
                 *
                 * double secondsElapsed = Duration.between(previousTimestamp, delivery.timestamp).toSeconds();
                 * double minutesElapsed = secondsElapsed / 60.0; // Fractional minutes
                 * if (minutesElapsed > 0 && activeDeliveries > 0) {
                 *     totalPayout += minutesElapsed * BASE_PAY_PER_MINUTE * activeDeliveries;
                 * }
                 */

                long minutesElapsed = Duration.between(previousTimestamp, delivery.timestamp).toMinutes();
                if (minutesElapsed > 0 && activeDeliveries > 0) {
                    totalPayout += minutesElapsed * BASE_PAY_PER_MINUTE * activeDeliveries;
                }
            }
            previousTimestamp = delivery.timestamp;

            if (delivery.status == Status.ACCEPTED) {
                activeDeliveries++;
            } else if (delivery.status == Status.FULFILLED || delivery.status == Status.CANCELLED) {
                activeDeliveries--;
            }
        }

        return totalPayout;
    }
}

class DeliveryParser {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static List<Delivery> parseDeliveries(String json) {
        List<Delivery> deliveries = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            int dasherId = obj.getInt("dasher_id");
            int deliveryId = obj.getInt("delivery_id");
            LocalDateTime timestamp = LocalDateTime.parse(obj.getString("timestamp"), FORMATTER);
            Status status = Status.valueOf(obj.getString("status"));

            deliveries.add(new Delivery(dasherId, deliveryId, timestamp, status));
        }

        return deliveries;
    }
}

public class Solution {
    public static void main(String[] args) {
        try {
            String json = ExternalService.fetchDeliveriesJson();

            // Parse the JSON into a list of deliveries
            List<Delivery> deliveries = DeliveryParser.parseDeliveries(json);

            // Calculate payout
            double payout = DasherPayoutCalculator.calculatePayout(deliveries);

            // Print result
            System.out.printf("Total payout: $%.2f%n", payout);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

