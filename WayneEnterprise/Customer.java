package WayneEnterprise;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Customer implements Runnable {
    private final LinkedBlockingQueue<Order> orderQueue;
    private final Random random = new Random();
    public Customer(LinkedBlockingQueue<Order> orderQueue) {
        this.orderQueue = orderQueue;
    }
    @Override
    public void run() {
        while (true) {
            try {
                int cargoWeight = random.nextInt(41) + 10; // Random cargo between 10 and 50 tonnes
                String destination = random.nextBoolean() ? "Gotham" : "Atlanta";
                Order order = new Order(cargoWeight, destination);
                orderQueue.put(order);
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
