public class Order {
    public int id;
    public int burger;
    public int fries;
    public int coke;
    public int time;
    public boolean isFinished;


    public Order(int id, int burger, int fries, int coke, int time) {
        this.id = id;
        this.burger = burger;
        this.fries = fries;
        this.coke = coke;
        this.time = time;
        this.isFinished = false;
    }


}
