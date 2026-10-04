package LibraryManager;

public class Member {
    private final String name;
    private final int id;
    private int activeLoans;
    private final int maxActiveLoans;

    public Member(String name, int id) {
        this.name = name;
        this.id = id;
        this.activeLoans = 0;
        this.maxActiveLoans = 3;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getActiveLoans() {
        return activeLoans;
    }

    public void increaseActiveLoan() {
        this.activeLoans++;
    }

    public void decreaseActiveLoan() {
        this.activeLoans--;
    }

    public String toString() {
        return "Name: " + name + ", ID: " + id + ", Active Loans: " + activeLoans;
    }

    public boolean maxActiveLoans() {
        return activeLoans >= maxActiveLoans;
    }

}
