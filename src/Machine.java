import javax.crypto.Mac;

public class Machine {

    public boolean burgerM = true;
    public boolean friesM = true;
    public boolean cokeM = true;
    public int burgerTime = 0;
    public int friesTime = 0;
    public int cokeTime = 0;

    public Machine () {

    }

    public synchronized boolean checkBurgerM() {
        if (burgerM) {
            return true;
        } else {
            return false;
        }
    }
    public synchronized boolean checkFriesM() {
        if (friesM) {
            return true;
        } else {
            return false;
        }
    }
    public synchronized boolean checkCokeM() {
        if (cokeM) {
            return true;
        } else {
            return false;
        }
    }

    public synchronized int burgerFinishTime(int orderTime) throws InterruptedException {

        while(!burgerM) {
            wait();
        }
        burgerM = false;
        if (orderTime > burgerTime) {
            burgerTime = orderTime + 5;

        } else {
            burgerTime += 5;

        }
        return burgerTime;
    }

    public synchronized int friesFinishTime(int orderTime) throws InterruptedException{

        while(!friesM) {
            wait();
        }
        friesM = false;
        if (orderTime > friesTime) {
            friesTime = orderTime + 3;

        } else {
            friesTime += 3;

        }
        return friesTime;
    }

    public synchronized int cokeFinishTime(int orderTime) throws InterruptedException{
        while(!cokeM) {
            wait();
        }
        cokeM = false;
        if (orderTime > cokeTime) {
            cokeTime = orderTime + 1;

        } else {
            cokeTime += 1;

        }

        return cokeTime;
    }

    public synchronized void releaseBurgerM() {
        burgerM = true;
        notifyAll();
    }
    public synchronized void releaseFriesM() {
        friesM = true;
        notifyAll();
    }
    public synchronized void releaseCokeM() {
        cokeM = true;
        notifyAll();
    }


}
