package dei.unipd.it.resources;

public final class Actions {

    public static final String LIST_RESERVATIONS = "LIST_RESERVATIONS";
    public static final String CREATE_RESERVATION = "CREATE_RESERVATION";
    public static final String LIST_REVIEWS = "LIST_REVIEWS";
    public static final String CREATE_REVIEW = "CREATE_REVIEW";
    public static final String SEARCH_WAITER_SHIFT = "SEARCH_WAITER_SHIFT";
    public static final String CREATE_SUPPLY = "SUPPLY_CREATE";
    public static final String CREATE_SHIFT = "SHIFT_CREATE";
    public static final String SEARCH_SUPPLY = "SEARCH_SUPPLY";
    public static final String SEARCH_WAITER_REVIEWS = "SEARCH_WAITER_REVIEWS";


    private Actions() {
        throw new AssertionError(String.format("No instances of %s allowed.", Actions.class.getName()));
    }

}
