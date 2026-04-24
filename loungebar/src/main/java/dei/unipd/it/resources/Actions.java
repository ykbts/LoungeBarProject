package dei.unipd.it.resources;

/**
 * Contains constants for the actions performed by the application.
 */
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
    public static final String CREATE_ORDER = "CREATE_ORDER";
    public static final String SEARCH_ORDER = "SEARCH_ORDER";
    public static final String SHOW_LOGIN = "SHOW_LOGIN";
    public static final String SHOW_SIGNUP = "SHOW_SIGNUP";
    public static final String LOGOUT = "LOGOUT";
    public static final String UPDATE_ORDER_STATUS = "UPDATE_ORDER_STATUS";
    public static final String SEARCH_MY_ORDER = "SEARCH_MY_ORDER";


    private Actions() {
        throw new AssertionError(String.format("No instances of %s allowed.", Actions.class.getName()));
    }

}
