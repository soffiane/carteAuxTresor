package fr.carbonit.treasurehunt.util;

import fr.carbonit.treasurehunt.model.Coordinates;

public abstract class CoordinatesUtils {

    public static boolean isCoordinatesInMapLimits(Coordinates mapLimits, Coordinates adventurerNextCoordinates) {
        return (
                mapLimits.getHorizontalAxis() > adventurerNextCoordinates.getHorizontalAxis() &&
                mapLimits.getVerticalAxis() > adventurerNextCoordinates.getVerticalAxis() &&
                adventurerNextCoordinates.getHorizontalAxis() >= 0 &&
                adventurerNextCoordinates.getVerticalAxis() >= 0
        );
    }
}
