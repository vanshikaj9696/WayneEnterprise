package WayneEnterprise;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Ship implements Runnable {
    private final LinkedBlockingQueue<Order> orderQueue;
    private int totalTrips = 0;
    private static int totalRevenue = 0;
    private int consecutiveCanceledOrders = 0;
    private static final int MAX_CARGO = 300;
    private static final int MIN_CARGO = 50;
    public Ship(LinkedBlockingQueue<Order> orderQueue) {
        this.orderQueue = orderQueue;
    }
    @Override
    public void run() {
        try {
            while (totalRevenue < 1000000) {
                Order order = orderQueue.poll(1, TimeUnit.MINUTES);
                if (order != null) {
                    int cargoWeight = Math.min(order.getCargoWeight(), MAX_CARGO);
                    cargoWeight = Math.max(cargoWeight, MIN_CARGO);
                    if (order.getDestination().equals("Gotham")) {
                        // Process the order (calculate revenue, etc.)
                        int revenue = cargoWeight * 1000;
                        totalRevenue += revenue;
                        totalTrips++;
                        // Simulate ship going back
                        orderQueue.put(new Order(cargoWeight, "Atlanta"));
                    } else {
                        // Process the return order
                        orderQueue.put(new Order(cargoWeight, "Gotham"));
                    }
                    consecutiveCanceledOrders = 0; // Reset consecutive canceled orders
                } else {
                    consecutiveCanceledOrders++;
                    if (consecutiveCanceledOrders == 3) {
                        // Customer leaves if 3 orders are canceled continuously
                        System.out.println("Customer leaving due to continuous cancellations");
                        break;
                    }
                }
                System.out.println("Shipped Revenue = "+totalRevenue);

                //Maintenance
                if (totalTrips % 5 == 0) {
                    System.out.println("Ship going for maintenance...");
                    Thread.sleep(60000);
                }
            }
            System.out.println("Reached target.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
