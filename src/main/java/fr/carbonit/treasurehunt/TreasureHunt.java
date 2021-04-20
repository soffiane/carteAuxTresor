package fr.carbonit.treasurehunt;

import fr.carbonit.treasurehunt.model.Adventurer;
import fr.carbonit.treasurehunt.model.Line;
import fr.carbonit.treasurehunt.model.TreasureMap;
import fr.carbonit.treasurehunt.util.AdventurerUtils;
import fr.carbonit.treasurehunt.util.FileUtils;
import fr.carbonit.treasurehunt.util.LineUtils;
import fr.carbonit.treasurehunt.util.MapUtils;

import java.util.List;

public class TreasureHunt {

	public static void main(String[] args) throws Exception {
		List<Line> linesTypes = LineUtils.getAllLinesTypes(FileUtils.getFileLines("/home/soffiane/IdeaProjects/carteAuxTresor/src/main/resources/input.txt"));
		TreasureMap[][] treasureMap = MapUtils.build(linesTypes);
		List<Adventurer> adventurers = AdventurerUtils.getAdventurersFromLines(linesTypes);
		AdventurerUtils.placeAdventurersOnMap(treasureMap, adventurers);
		AdventurerUtils.playAdventurersMovements(treasureMap, adventurers, 0);
		FileUtils.writeProgramOutputFile(treasureMap, adventurers, linesTypes
		);
	}
}