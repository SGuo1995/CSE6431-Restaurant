import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class Cook extends Thread {

    private  int id;
    private OrderManager orderManager;
    private Machine machine;
    public  Order order;
    public Restaurant restaurant;
    public int beginProcessTime = 0;
    public int time = 0;    //available time of this cook
    public int burgers = 0;
    public int fries = 0;
    public int coke = 0;
    public LinkedHashSet<String> logs;

    public Cook(int id, OrderManager orderManager, Machine machine, Restaurant restaurant) {
        this.id = id;
        this.orderManager = orderManager;
        this.machine = machine;
        this.logs = new LinkedHashSet<>();
        this.restaurant = restaurant;
    }


    public synchronized void print() {
        for (String log : logs) {
            System.out.println(log);
        }
    }
    @Override
    public void run() {
        while (true) {
            try{
                order = orderManager.popOrder();
//                System.out.println(" %n ");
                if(order.time > time) {
                    beginProcessTime = order.time;
//                    logs.add(order.time + " - " + "Cook " + id + " begin process diner " + order.id + "'s order");
//                    System.out.format("%d - Cook %d begin process diner %d's order %n", order.time, id, order.id);
                } else {
                    beginProcessTime = time;
//                    logs.add(time + " - " + "Cook " + id + " begin process diner " + order.id + "'s order");
//                    System.out.format("%d - Cook %d begin process diner %d's order %n", time, id, order.id);

                }

                logs.add(beginProcessTime + " - " + "Cook " + id + " begin process diner " + order.id + "'s order");
                burgers = order.burger;
                fries = order.fries;
                coke = order.coke;
                while(burgers!= 0 || fries != 0 || coke != 0) {
                    if (burgers > 0 && machine.checkBurgerM()) {
                        burgers--;
                        order.time = machine.burgerFinishTime(order.time);
                        logs.add((order.time - 5) + " - " + "Cook " + id + " use burger machine");
//                        System.out.format("%d - Cook %d use burger machine %n", order.time - 5, id);
                        machine.releaseBurgerM();

                    } else if (fries > 0 && machine.checkFriesM()) {
                        fries--;
                        order.time = machine.friesFinishTime(order.time);
                        logs.add((order.time - 3) + " - " + "Cook " + id + " use fries machine");
//                        System.out.format("%d - Cook %d use fries machine %n", order.time - 3, id);
                        machine.releaseFriesM();
                    } else if (coke > 0 && machine.checkCokeM()) {
                        coke--;
                        order.time = machine.cokeFinishTime(order.time);
                        logs.add((order.time - 1) + " - " + "Cook " + id + " use coke machine");
//                        System.out.format("%d - Cook %d use coke machine %n", order.time - 1, id);
                        machine.releaseCokeM();
                    }
                }
                time = order.time;
                order.isFinished = true;

//                Restaurant.addCookInfo(order.id, new LinkedHashSet<>(logs));
//                System.out.println(" %n ");
//                logs.clear();
                orderManager.putFinish(order);

            } catch (Exception e) {}



        }
    }
}




