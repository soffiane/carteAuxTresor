package fr.carbonit.treasurehunt.util;

import fr.carbonit.treasurehunt.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static fr.carbonit.treasurehunt.Constantes.IGNORE_LINE;
import static fr.carbonit.treasurehunt.Constantes.SEPARATOR;


public abstract class LineUtils {



	public static List<Line> getAllLinesTypes(List<String> sLines) {
		return sLines.stream().map(LineUtils::getLineTypeFromString).collect(Collectors.toList());
	}

	public static Line getLineTypeFromString(String line) {
		Line lineType = new Line();
		lineType.setLine(line);
		if (line.startsWith(LineType.MAP.getValue())) {
			lineType.setLineType(LineType.MAP);
		} else if (line.startsWith(LineType.MOUNTAIN.getValue())) {
			lineType.setLineType(LineType.MOUNTAIN);
		} else if (line.startsWith(LineType.TREASURE.getValue())) {
			lineType.setLineType(LineType.TREASURE);
		} else if (line.startsWith(LineType.ADVENTURER.getValue())) {
			lineType.setLineType(LineType.ADVENTURER);
		} else if (line.startsWith(IGNORE_LINE)) {
			return null;
		} else {
			throw new IllegalArgumentException("Impossible de définir le type de la ligne fournie : " + line);
		}
		return lineType;
	}

	public static boolean isLineMapType(Line line) {
		return line.getLineType().equals(LineType.MAP);
	}

	public static boolean isLineMountainType(Line line) {
		return line.getLineType().equals(LineType.MOUNTAIN);
	}

	public static boolean isLineTreasureType(Line line) {
		return line.getLineType().equals(LineType.TREASURE);
	}

	public static boolean isLineAdventurerType(Line line) {
		return line.getLineType().equals(LineType.ADVENTURER);
	}

	public static Coordinates getTreasureMapSize(List<Line> lines) {
		return lines.stream()
				.filter(LineUtils::isLineMapType)
				.map(Coordinates::new)
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("La taille de la carte n'est pas renseignée"));
	}

	public static Adventurer getAdventurerFromLine(Line line) {
		String[] split = line.getLine().split(SEPARATOR);

		List<Mouvement> mouvements = new ArrayList<>();

		for (char c : split[5].toCharArray()) {
			final Moves mouvement = Arrays.stream(Moves.values())
					.filter(moves -> moves.getValue().equals(Character.toString(c)))
					.findFirst()
					.orElseThrow(() -> new IllegalArgumentException("valeur de deplacement invalide"));
			mouvements.add(new Mouvement(mouvement, false));
		}

		Coordinates adventurerCoordinates = LineUtils.getAdventurerCoordinatesFromLine(line.getLine());

		final Orientation orientation = Arrays.stream(Orientation.values())
				.filter(orientations -> orientations.getValue().equals(split[4]))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("valeur de direction invalide"));

		return new Adventurer(
				split[1],
				adventurerCoordinates,
				orientation,
				mouvements,
				0
		);
	}

	public static Coordinates getCoordinatesFromLine(String line) {
		String[] split = line.split(SEPARATOR);
		return new Coordinates(Integer.parseInt(split[2]), Integer.parseInt(split[1]));
	}

	public static Coordinates getAdventurerCoordinatesFromLine(String line) {
		String[] split = line.split(SEPARATOR);
		return new Coordinates(Integer.parseInt(split[3]), Integer.parseInt(split[2]));
	}

	public static int getTreasureNumberFromLine(String line) {
		return Integer.parseInt(line.split(SEPARATOR)[3]);
	}

	public static Optional<List<Line>> getMountainsFromInputFile(List<Line> lines) {
		List<Line> mountains = lines.stream()
				.filter(LineUtils::isLineMountainType)
				.collect(Collectors.toList());

		return mountains.isEmpty() ? Optional.empty() : Optional.of(mountains);
	}

	public static Optional<List<Line>> getTreasuresFromInputFile(List<Line> lines) {
		List<Line> treasures = lines.stream()
				.filter(LineUtils::isLineTreasureType)
				.collect(Collectors.toList());

		return treasures.isEmpty() ? Optional.empty() : Optional.of(treasures);
	}
}
