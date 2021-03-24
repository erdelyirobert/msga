package ThemePark;

/**
 * The type General equipment.
 */
public class GeneralEquipment extends Building {
    /**
     * Instantiates a new General equipment.
     *
     * @param type             the type
     * @param inConstruction   the in construction
     * @param constructionTime the construction time
     * @param buildPrice       the build price
     * @param location_X       the location x
     * @param location_Y       the location y
     * @param range            the range
     */
    public GeneralEquipment(Enum type, boolean inConstruction, int constructionTime, int buildPrice, int location_X, int location_Y, int range) {
        super(type, inConstruction, constructionTime, buildPrice, location_X, location_Y, range);
    }
}
