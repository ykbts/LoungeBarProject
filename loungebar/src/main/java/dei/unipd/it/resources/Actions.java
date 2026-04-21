package dei.unipd.it.resources;

public final class Actions {

    public static final String CREATE_RESERVATION = "CREATE_RESERVATION";
    public static final String LIST_REVIEWS = "LIST_REVIEWS";
    public static final String CREATE_REVIEW = "CREATE_REVIEW";


    private Actions() {
        throw new AssertionError(String.format("No instances of %s allowed.", Actions.class.getName()));
    }

}
