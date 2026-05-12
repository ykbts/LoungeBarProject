package dei.unipd.it.resources;

public enum SupplyType {

    DISCRETE(0),             // discrete units -> no conversion; set to 0 for behavior identification
    BOTTLE_0_7L(700),    // 700 ml
    BOTTLE_1L(1000);      // 1000 ml

    /**
     * Measure unit adopted in the conversion system. Every unit available for the Supplies corresponds to {@value CONVENTIONAL_UNIT} ml.
     */
    private static final int CONVENTIONAL_UNIT = 15;
    private final int unitsPerContainer;

    /**
     *
     * @param mlPerUnit     volume per unit of the selected type; set to {@code 0} if the type is DISCRETE
     */
    SupplyType(int mlPerUnit) {
        this.unitsPerContainer = mlPerUnit == 0 ? 1 : (mlPerUnit / CONVENTIONAL_UNIT);
    }

    /**
     * Converts units submitted by the user in the conventional units adopted to store in the database.
     *
     * @param userInput units inserted by the user (cans or bottles)
     * @return conventional unit to store
     * @throws IllegalArgumentException if {@code userInput} is zero or lower
     */
    public int toStoredUnits(int userInput) {

        if (userInput <= 0)
            throw new IllegalArgumentException("Supply's UNITS value must be greater than 0.");

        return userInput * unitsPerContainer;
    }


}
