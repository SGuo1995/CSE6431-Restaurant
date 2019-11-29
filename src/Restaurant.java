import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.management.BufferPoolMXBean;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private static int numCook;
    private static int numTable;
    private static int numDiner;
    private static TableManager tableManager;
    private static OrderManager orderManager;
    private static Machine machine;
    public static TreeMap<Integer,LinkedHashSet<String>> cookInfo;
    public static int leaveTimeOfLastDiner = 0;

//    public static LinkedBlockingQueue<Order> orderList;
    /*
        Input:
        number of diners
        number of tables
        number of cooks
        arrival time , burgers (1 or higher), fires(0 or higher), coke (0 or 1)
        .....
    */
    public static void addCookInfo(int oid, LinkedHashSet<String> info) {
        cookInfo.put(oid, info);
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(args[0]));

        numDiner = Integer.valueOf(br.readLine().trim());
        numTable = Integer.valueOf(br.readLine().trim());
        numCook = Integer.valueOf(br.readLine().trim());
        tableManager = new TableManager();
        orderManager = new OrderManager(numDiner);
        machine = new Machine();
        Diner[] diners = new Diner[numDiner];
        cookInfo = new TreeMap<>();
//        orderList = new LinkedBlockingQueue<>();

        for(int i = 0; i < numDiner; i++) {
//            Scanner sc = new Scanner(br.readLine());
            String[] str = br.readLine().split(",");
            int arrival_time = Integer.valueOf(str[0]);
            int burgers = Integer.valueOf(str[1]);
            int fries = Integer.valueOf(str[2]);
            int coke = Integer.valueOf(str[3]);
            Diner newDiner = new Diner(i + 1, arrival_time, burgers, fries, coke, tableManager, orderManager);
            diners[i] = newDiner;
        }
        int tableId = 1;
        LinkedBlockingQueue<Table> availableTables = tableManager.getTableList();
        for (int j = 0; j < numTable; j++) {
            availableTables.add(new Table(tableId++, -1));
        }
        int cookId = 1;
        Restaurant restaurant = new Restaurant();
        Cook[] cooks = new Cook[numCook];
        for (int i = 0; i < numCook; i++) {
            cooks[i] = new Cook(cookId++, orderManager, machine,restaurant);
            cooks[i].start();
        }

        for (int i = 0; i < diners.length; i++) {
                diners[i].start();
                Thread.sleep(10);
//            diners[i].getTable();
////            Thread dinerThread = new Thread(diners[i]);
//            diners[i].run();
//            Thread.sleep(3);
        }

        /// wait for all diners finish
        for(int i=0;i<diners.length;i++){
            try {
                diners[i].join();
            }catch(Exception e){
                System.out.println(e);
            }
        }

////        Print all cook activities
//        for (Map.Entry<Integer, LinkedHashSet<String>> entry: cookInfo.entrySet()) {
//            for(String str : entry.getValue()) {
//                System.out.println(str);
//            }
//        }
        //All diners finish, start to print out results
        System.out.println("Cooks Activities: ");
        for(int i = 0; i < numCook; i++) {
            cooks[i].print();
        }
        //Print all diners activities
        System.out.println("Diners Activities: ");
        for(int i = 0; i < numDiner; i++) {
            diners[i].print();
            leaveTimeOfLastDiner = Math.max(diners[i].leaveTime, leaveTimeOfLastDiner);
        }
        System.out.format("%d - The last diner leaves the restaurant %n", leaveTimeOfLastDiner);
        System.exit(-1);






    }
}
