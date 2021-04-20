package fr.carbonit.treasurehunt.model;

import java.util.List;

public class Adventurer {

	private final String name;
	private Coordinates coordinates;
	private Orientation orientation;
	private final List<Mouvement> movements;
	private int discoveredTreasures;

	public Adventurer(String name, Coordinates coordinates, Orientation orientation, List<Mouvement> movements, int discoveredTreasures) {
		this.name = name;
		this.coordinates = coordinates;
		this.orientation = orientation;
		this.movements = movements;
		this.discoveredTreasures = discoveredTreasures;
	}

	public String getName() {
		return name;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public Orientation getAdventurerOrientation() {
		return orientation;
	}

	public void setAdventurerDirection(Orientation orientation) {
		this.orientation = orientation;
	}

	public List<Mouvement> getMovements() {
		return movements;
	}

	public int getDiscoveredTreasures() {
		return discoveredTreasures;
	}

	public void setDiscoveredTreasures(int discoveredTreasures) {
		this.discoveredTreasures = discoveredTreasures;
	}
}
