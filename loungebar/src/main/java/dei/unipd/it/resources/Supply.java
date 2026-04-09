package dei.unipd.it.resources;

public class Supply extends AbstractResource {
    private final String name;
    private final int unitsAvailable;

    public Supply(final String name, final int unitsAvailable) {
        this.name = name;
        this.unitsAvailable = unitsAvailable;
    }

    public final String getName() {
        return name;
    }

    public final int getUnitsAvailable() {
        return unitsAvailable;
    }

}