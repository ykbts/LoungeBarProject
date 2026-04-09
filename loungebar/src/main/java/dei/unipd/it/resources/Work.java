package dei.unipd.it.resources;

public class Work {

    private final int waiterID;
    private final int shiftID;

    public Work(int waiterID, int shiftID) {
        this.waiterID = waiterID;
        this.shiftID = shiftID;
    }
    public int getWaiterID() {
        return waiterID;
    }
    public int getShiftID() {
        return shiftID;
    }
}
