package fr.carbonit.treasurehunt.util;

import fr.carbonit.treasurehunt.model.TreasureMap;

public abstract class CellUtils {

    public static boolean isAccessibleForAdventurer(TreasureMap cell) {
        return (!cell.isMountain() && cell.getAdventurer() == null);
    }
}
