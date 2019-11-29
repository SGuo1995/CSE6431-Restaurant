import javafx.scene.control.Tab;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class TableManager {

    private LinkedBlockingQueue<Table> tableList;


    public TableManager() {
        this.tableList = new LinkedBlockingQueue<>();
    }

    public LinkedBlockingQueue<Table> getTableList() {
        return tableList;
    }

    public synchronized Table assignTable(Diner diner) throws InterruptedException {
        Table table = null;
//        System.out.format("%d - Diner %d arrives %n", diner.arrivalTime, diner.dinerId);

        while (tableList.isEmpty()) {
            wait();
        }
        table = tableList.poll();
        return table;
    }

    public synchronized void returnTable(Table table) throws InterruptedException {
        tableList.put(table);
        notifyAll();
    }
}
