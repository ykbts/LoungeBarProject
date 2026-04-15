package dei.unipd.it.resources;

public class Review {

    private final String waiterID;
    private final String customerID;
    private final int score;
    private final String comment;

    public Review(String waiterID, String customerID, int score, String comment) {
        this.waiterID = waiterID;
        this.customerID = customerID;
        this.score = score;
        this.comment = comment;
    }

    public String getWaiterID() {
        return waiterID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public int getScore() {
        return score;
    }

    public String getComment() {
        return comment;
    }
}
