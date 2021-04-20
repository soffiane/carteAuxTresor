package fr.carbonit.treasurehunt.util;

import fr.carbonit.treasurehunt.model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static fr.carbonit.treasurehunt.Constantes.RESULT_FILE;
import static fr.carbonit.treasurehunt.Constantes.SEPARATOR;

public abstract class FileUtils {

	private static final Logger LOGGER = Logger.getLogger(FileUtils.class.getName());

	private FileUtils(){
	}

	public static List<String> getFileLines(String filePath) {
		List<String> fileLine = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
			stream.forEach(fileLine::add);
		} catch (IOException ioe) {
			LOGGER.log(Level.SEVERE,"fichier introuvable");
		}
		return fileLine;
	}

	public static void writeProgramOutputFile(TreasureMap[][] treasureMap, List<Adventurer> adventurers, List<Line> lines) throws IOException {
		FileWriter fileWriter = new FileWriter(RESULT_FILE);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		lines.forEach(l -> {
			if (LineUtils.isLineMapType(l)) {
				writeMapLine(printWriter, l);
			} else if (LineUtils.isLineMountainType(l)) {
				writeMountainLine(printWriter, l);
			} else if (LineUtils.isLineTreasureType(l)) {
				MapUtils.getMapCellFromCoordinates(treasureMap, LineUtils.getCoordinatesFromLine(l.getLine()))
						.ifPresent(map -> writeTreasureLine(printWriter, map));
			}
		});
		adventurers.forEach(a -> writeAdventurerLine(printWriter, a));
		printWriter.close();
	}

	private static void writeMapLine(PrintWriter writer, Line line) {
		Coordinates dimensions = new Coordinates(line);
		writer.println(
				LineType.MAP.getValue() + SEPARATOR +
						dimensions.getVerticalAxis() + SEPARATOR + dimensions.getHorizontalAxis()
		);
	}

	private static void writeMountainLine(PrintWriter writer, Line l) {
		Coordinates mountainCoordinates = LineUtils.getCoordinatesFromLine(l.getLine());
		writer.println(
				LineType.MOUNTAIN.getValue() + SEPARATOR +
						mountainCoordinates.getVerticalAxis() + SEPARATOR + mountainCoordinates.getHorizontalAxis()
		);
	}

	private static void writeTreasureLine(PrintWriter writer, TreasureMap cell) {
		if (cell.getTreasures() > 0) {
			writer.println(
					LineType.TREASURE.getValue() + SEPARATOR + cell.getMapHeight() + SEPARATOR + cell.getMapWidth() + SEPARATOR + cell.getTreasures()
			);
		}
	}

	private static void writeAdventurerLine(PrintWriter writer, Adventurer a) {
		writer.println(
				LineType.ADVENTURER.getValue() + SEPARATOR + a.getName() + SEPARATOR + a.getCoordinates().getVerticalAxis() + SEPARATOR + a.getCoordinates().getHorizontalAxis() + SEPARATOR +
						a.getAdventurerOrientation().getValue() + SEPARATOR + a.getDiscoveredTreasures()
		);
	}
}
