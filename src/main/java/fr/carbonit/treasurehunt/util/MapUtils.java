package fr.carbonit.treasurehunt.util;

import fr.carbonit.treasurehunt.model.Coordinates;
import fr.carbonit.treasurehunt.model.Line;
import fr.carbonit.treasurehunt.model.TreasureMap;

import java.util.List;
import java.util.Optional;

public class MapUtils {

	public static Coordinates getMapDimensions(TreasureMap[][] treasureMap) {
		Coordinates coordinates = new Coordinates();
		coordinates.setHorizontalAxis(treasureMap.length);
		coordinates.setVerticalAxis(treasureMap[0].length);
		return coordinates;
	}

	public static Optional<TreasureMap> getMapCellFromCoordinates(TreasureMap[][] treasureMap, Coordinates coordinates) {
		return CoordinatesUtils.isCoordinatesInMapLimits(getMapDimensions(treasureMap), coordinates) ?
				Optional.of(treasureMap[coordinates.getHorizontalAxis()][coordinates.getVerticalAxis()]) :
				Optional.empty();
	}

	public static void setupMountains(TreasureMap[][] treasureMap, List<Line> lines) {
		LineUtils.getMountainsFromInputFile(lines).ifPresent(mountainsLines -> placeMountains(treasureMap, mountainsLines));
	}

	public static void placeMountains(TreasureMap[][] treasureMap, List<Line> mountainsLines) {
		mountainsLines
				.stream()
				.map(mountain -> LineUtils.getCoordinatesFromLine(mountain.getLine()))
				.forEach(mountainCoordinates -> treasureMap[mountainCoordinates.getHorizontalAxis()][mountainCoordinates.getVerticalAxis()].setMountain(true));
	}

	public static void setUpTreasures(TreasureMap[][] treasureMap, List<Line> lines) {
		LineUtils.getTreasuresFromInputFile(lines).ifPresent(o -> placeTreasures(treasureMap, o));
	}

	public static void placeTreasures(TreasureMap[][] treasureMap, List<Line> treasuresLines) {
		treasuresLines.forEach(line -> {
			Coordinates treasureCoordinates = LineUtils.getCoordinatesFromLine(line.getLine());
			TreasureMap cell = treasureMap[treasureCoordinates.getHorizontalAxis()][treasureCoordinates.getVerticalAxis()];
			if (!cell.isMountain()) {
				cell.setTreasures(LineUtils.getTreasureNumberFromLine(line.getLine()));
			} else {
				System.out.println("Impossible de placer un trésor à l'emplacement ["
						+ treasureCoordinates.getHorizontalAxis() + ", " + treasureCoordinates.getVerticalAxis() + "] " +
						"car la position pointe sur une montagne");
			}
		});
	}

	public static TreasureMap[][] build(List<Line> lines) {
		Coordinates mapDimensions = LineUtils.getTreasureMapSize(lines);
		TreasureMap[][] treasureMap = new TreasureMap[mapDimensions.getHorizontalAxis()][mapDimensions.getVerticalAxis()];
		fillMap(treasureMap, mapDimensions);
		MapUtils.setupMountains(treasureMap, lines);
		MapUtils.setUpTreasures(treasureMap, lines);
		return treasureMap;
	}

	public static void fillMap(TreasureMap[][] treasureMap, Coordinates dimensions) {
		for (int i = 0; i < dimensions.getHorizontalAxis(); i++) {
			for (int j = 0; j < dimensions.getVerticalAxis(); j++) {
				treasureMap[i][j] = new TreasureMap(i, j);
			}
		}
	}
}
