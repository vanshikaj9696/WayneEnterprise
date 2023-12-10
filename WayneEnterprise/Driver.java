package WayneEnterprise;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Driver {
    public static void main(String[] args) {
        LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();
        ExecutorService executorService = Executors.newFixedThreadPool(12); //5 ships+ 7 customers
        // Create customer threads
        for (int i = 0; i < 7; i++) {
            executorService.submit(new Customer(orderQueue));
        }
        // Create ship threads
        for (int i = 0; i < 5; i++) {
            executorService.submit(new Ship(orderQueue));
        }
        executorService.shutdown();
    }
}
