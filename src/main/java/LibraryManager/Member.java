package LibraryManager;

public class Member {
    private String name;
    private final int id;
    private int activeLoans;

    public Member(String name, int id) {
        this.name = name;
        this.id = id;
        this.activeLoans = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getActiveLoans() {
        return activeLoans;
    }

    public void setActiveLoans(int activeLoans) {
        this.activeLoans = activeLoans;
    }

    public void increaseActiveLoan() {
        this.activeLoans++;
    }

    public void decreaseActiveLoan() {
        this.activeLoans--;
    }

    public boolean hasActiveLoan() {
        return activeLoans > 0;
    }

    public String toString() {
        return "Name: " + name + ", ID: " + id + ", Active Loans: " + activeLoans;
    }

    public boolean maxActiveLoans() {
        return activeLoans >= 3;
    }

}
