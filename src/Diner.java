import javax.print.DocFlavor;
import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Diner extends Thread {

    public int dinerId;
    public int arrivalTime;
    private int burgers;
    private int fries;
    private int coke;
    private Table tableSitted = null;
    private Order order = null;
    private TableManager tableManager = null;
    public int sitTime = 0;
    public OrderManager om;
    public int eatTime = 0;
    public int leaveTime = 0;




    public Diner(int id, int arrivalTime, int burgers, int fries, int coke, TableManager tableManager, OrderManager om) {
        this.dinerId = id;
        this.arrivalTime = arrivalTime;
        this.burgers = burgers;
        this.fries = fries;
        this.coke = coke;
        this.tableManager = tableManager;
        this.om = om;
    }

    public synchronized void print() {
        System.out.format("%d - Diner %d arrives %n", arrivalTime, dinerId);
        System.out.format("%d - Diner %d is seated at table %d %n", sitTime, dinerId,tableSitted.tableId);
        System.out.format("%d - Diner %d's order is ready, diner %d starts eating %n", eatTime, dinerId,dinerId);
        System.out.format("%d - Diner %d's finishes, diner %d leaves %n", leaveTime, dinerId,dinerId);
//        if(om.isFinish()) {
////            System.out.format("%d - The last diner leaves the restaurant %n", leaveTime);
////            System.exit(-1);
////        }
    }
    @Override
    public void run() {
//        TableManager tableManager = Restaurant.getTableManager()
        try {
            tableSitted = tableManager.assignTable(this);       //might block here;
        //set sit time
            if (tableSitted.time >= arrivalTime) {
                sitTime = tableSitted.time;   //the time this diner sit is equal to the edited time of the table
            } else {
                sitTime = arrivalTime;
            }
//            System.out.format("%d - Diner %d is seated at table %d %n", sitTime, dinerId,tableSitted.tableId);

            Order order = new Order(dinerId, burgers, fries, coke, sitTime);
            om.putOrder(order);

            om.checkStatus(order.id);


            eatTime = order.time;
//            System.out.format("%d - Diner %d's order is ready, diner %d starts eating %n", eatTime, dinerId,dinerId);

            leaveTime = eatTime + 30;
//            System.out.format("%d - Diner %d's finishes, diner %d leaves %n", leaveTime, dinerId,dinerId);

            tableSitted.time = leaveTime;
            tableManager.returnTable(tableSitted);
//            if(om.isFinish()) {
//                System.out.format("%d - The last diner leaves the restaurant %n", leaveTime);
//                System.exit(-1);
//            }
//            print();
        } catch (Exception e) {
        }







    }
}
