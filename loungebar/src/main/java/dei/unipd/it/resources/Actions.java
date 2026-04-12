package dei.unipd.it.resources;

public final class Actions {

    public static final String CREATE_RESERVATION = "CREATE_RESERVATION";


    private Actions() {
        throw new AssertionError(String.format("No instances of %s allowed.", Actions.class.getName()));
    }

}
