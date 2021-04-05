public class BottleDispenser {
    private int bottles;
    private int money;

    public BottleDispenser() {
        bottles = 5;
        money = 0;
    }

    public void addMoney() {
        money += 1;
        System.out.println("Klink! Money was added into the machine!");
    }

    public void buyBottle() {
        bottles -= 1;
        System.out.println("KACHUNK! Bottle appeared from the machine!");
    }

    public void returnMoney() {
        money = 0;
        System.out.println("Klink klink!! All money gone!");
    }
}
