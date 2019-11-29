import java.util.concurrent.LinkedBlockingQueue;

public class OrderManager {

    public LinkedBlockingQueue<Order> orderList;
    public LinkedBlockingQueue<Order> finishedList;

    public int numDiners;
    public volatile  int numFinished = 0;

    public OrderManager(int numDiners){
        this.orderList = new LinkedBlockingQueue<Order>();
        this.numDiners = numDiners;
        this.finishedList = new LinkedBlockingQueue<>();
    }

    public synchronized void putOrder(Order order) throws InterruptedException{
        orderList.put(order);
        notifyAll();
    }

    public synchronized Order popOrder() throws InterruptedException{
        while(orderList.isEmpty()) {
            wait();
        }
        Order order = orderList.poll();
        return order;
    }
    public synchronized boolean isFinish() {
        numFinished += 1;
        if (numFinished == numDiners) {
            return true;
        } else {
            return false;
        }
    }

    public synchronized void putFinish(Order order) throws InterruptedException{
        finishedList.put(order);
        notifyAll();
    }

    public synchronized void checkStatus(int id) throws InterruptedException{
            while(notFinished(id)) {
                wait();
            }
    }
    public synchronized boolean notFinished(int id) {
        for (Order order : finishedList) {
            if (order.id == id) {
                return false;
            }
        }
        return true;
    }

}
