package fr.carbonit.treasurehunt.util;

import fr.carbonit.treasurehunt.model.*;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AdventurerUtils {

	public static void moveAdventurerForward(TreasureMap[][] treasureMap, Adventurer adventurer) {
		Coordinates adventurerNextCoordinates = AdventurerUtils.getNextAdventurerCoordinates(adventurer);

		if (CoordinatesUtils.isCoordinatesInMapLimits(MapUtils.getMapDimensions(treasureMap), adventurerNextCoordinates)) {
			TreasureMap currentAdventurerCell = treasureMap[adventurer.getCoordinates().getHorizontalAxis()][adventurer.getCoordinates().getVerticalAxis()];
			TreasureMap nextAdventurerCell = treasureMap[adventurerNextCoordinates.getHorizontalAxis()][adventurerNextCoordinates.getVerticalAxis()];

			if (CellUtils.isAccessibleForAdventurer(nextAdventurerCell)) {
				currentAdventurerCell.setAdventurer(null);
				adventurer.setCoordinates(adventurerNextCoordinates);
				nextAdventurerCell.setAdventurer(adventurer);
				System.out.println(adventurer.getName() + " vient d'avancer d'une case.");
				if (nextAdventurerCell.getTreasures() > 0) {
					adventurer.setDiscoveredTreasures(adventurer.getDiscoveredTreasures() + 1);
					nextAdventurerCell.setTreasures(nextAdventurerCell.getTreasures() - 1);
					System.out.println(adventurer.getName() + " vient de trouver un nouveau trésor. "+adventurer.getName()+" en a maintenant " + adventurer.getDiscoveredTreasures());
				}
			} else {
				System.out.println(adventurer.getName() + " souhaite avancer sur une position non accessible. Mouvement ignoré.");
			}
		} else {
			System.out.println("Impossible de faire avancer " + adventurer.getName() + " car il risque de quitter la carte. Mouvement ignoré");
		}
	}

	public static void updateAdventurerDirectionToTheLeft(Adventurer adventurer) {
		switch (adventurer.getAdventurerOrientation()) {
			case NORTH:
				System.out.println(adventurer.getName() + " regarde le nord. Il tourne a gauche et regarde maintenant l'ouest");
				adventurer.setAdventurerDirection(Orientation.WEST);
				break;
			case SOUTH:
				System.out.println(adventurer.getName() + " regarde le sud. Il tourne a gauche et regarde maintenant l'est");
				adventurer.setAdventurerDirection(Orientation.EAST);
				break;
			case EAST:
				System.out.println(adventurer.getName() + " regarde l'est. Il tourne a gauche et regarde maintenant le nord");
				adventurer.setAdventurerDirection(Orientation.NORTH);
				break;
			case WEST:
				System.out.println(adventurer.getName() + " regarde l'ouest. Il tourne a gauche et regarde maintenant le sud");
				adventurer.setAdventurerDirection(Orientation.SOUTH);
				break;
			default:
				throw new IllegalStateException("Direction de deplacement invalide");
		}
	}

	public static void updateAdventurerDirectionToTheRight(Adventurer adventurer) {
		switch (adventurer.getAdventurerOrientation()) {
			case NORTH:
				System.out.println(adventurer.getName() + " regarde le nord. Il tourne a droite et regarde maintenant l'est");
				adventurer.setAdventurerDirection(Orientation.EAST);
				break;
			case SOUTH:
				System.out.println(adventurer.getName() + " regarde le sud. Il tourne a droite et regarde maintenant l'ouest");
				adventurer.setAdventurerDirection(Orientation.WEST);
				break;
			case EAST:
				System.out.println(adventurer.getName() + " regarde l'est. Il tourne a droite et regarde maintenant le sud");
				adventurer.setAdventurerDirection(Orientation.SOUTH);
				break;
			case WEST:
				System.out.println(adventurer.getName() + " regarde l'ouest. Il tourne a droite et regarde maintenant le nord");
				adventurer.setAdventurerDirection(Orientation.NORTH);
				break;
			default:
				throw new IllegalStateException("Direction de deplacement invalide");
		}
	}

	public static List<Mouvement> extractAdventurersMovements(List<Adventurer> adventurers) {
		return adventurers.stream()
				.flatMap(adventurer -> adventurer.getMovements().stream())
				.collect(Collectors.toList());
	}

	public static boolean isAnyMovementPlayable(List<Mouvement> movements) {
		return movements.stream().anyMatch(m -> !m.isMoveAlreadyMade());
	}

	public static Coordinates getNextAdventurerCoordinates(Adventurer adventurer) {
		Coordinates nextCoordinates = new Coordinates();
		Coordinates adventurerCoordinates = adventurer.getCoordinates();
		switch (adventurer.getAdventurerOrientation()) {
			case NORTH:
				nextCoordinates.setHorizontalAxis(adventurerCoordinates.getHorizontalAxis() - 1);
				nextCoordinates.setVerticalAxis(adventurerCoordinates.getVerticalAxis());
				break;
			case SOUTH:
				nextCoordinates.setHorizontalAxis(adventurerCoordinates.getHorizontalAxis() + 1);
				nextCoordinates.setVerticalAxis(adventurerCoordinates.getVerticalAxis());
				break;
			case EAST:
				nextCoordinates.setHorizontalAxis(adventurerCoordinates.getHorizontalAxis());
				nextCoordinates.setVerticalAxis(adventurerCoordinates.getVerticalAxis() + 1);
				break;
			case WEST:
				nextCoordinates.setHorizontalAxis(adventurerCoordinates.getHorizontalAxis());
				nextCoordinates.setVerticalAxis(adventurerCoordinates.getVerticalAxis() - 1);
				break;
			default:
				throw new IllegalStateException("Coordonnées de deplacement invalides");
		}
		return nextCoordinates;
	}

	public static List<Adventurer> getAdventurersFromLines(List<Line> lines) {
		List<Adventurer> adventurers = lines.stream().filter(LineUtils::isLineAdventurerType).map(LineUtils::getAdventurerFromLine).collect(Collectors.toList());
		if (adventurers.isEmpty()) {
			throw new IllegalStateException("Aucun aventurier n'a été trouvé dans le fichier donné. Impossible de lancer le programme !");
		} else {
			return adventurers;
		}
	}

	public static void placeAdventurersOnMap(TreasureMap[][] treasureMap, List<Adventurer> adventurers) {

		for (Adventurer a : adventurers) {
			TreasureMap cell = treasureMap[a.getCoordinates().getHorizontalAxis()][a.getCoordinates().getVerticalAxis()];
			if (CellUtils.isAccessibleForAdventurer(cell)) {
				cell.setAdventurer(a);
			} else {
				throw new IllegalStateException("Impossible de placer l'aventurier " + a.getName() + " sur la carte. Sa position de départ n'est pas accessible.");
			}
		}
	}

	public static void playAdventurersMovements(TreasureMap[][] treasureMap, List<Adventurer> adventurers, int counter) {

		List<Mouvement> allMovements = AdventurerUtils.extractAdventurersMovements(adventurers);

		if (AdventurerUtils.isAnyMovementPlayable(allMovements)) {
			adventurers.stream()
					.filter(a -> counter < a.getMovements().size() && !a.getMovements().get(counter).isMoveAlreadyMade())
					.forEach(a -> playMovement(treasureMap, counter, a));
			playAdventurersMovements(treasureMap, adventurers, counter + 1);
		}
	}

	private static void playMovement(TreasureMap[][] treasureMap, int counter, Adventurer a) {
		Mouvement movement = a.getMovements().get(counter);
		executeAdventurerMovement(treasureMap, a, movement);
		movement.setMoveAlreadyMade(true);
	}

	private static void executeAdventurerMovement(TreasureMap[][] treasureMap, Adventurer adventurer, Mouvement movement) {
		if (movement.getAction().equals(Moves.MOVE_FORWARD)) {
			moveAdventurerForward(treasureMap, adventurer);
		} else if (movement.getAction().equals(Moves.MOVE_LEFT)) {
			updateAdventurerDirectionToTheLeft(adventurer);
		} else if (movement.getAction().equals(Moves.MOVE_RIGHT)) {
			updateAdventurerDirectionToTheRight(adventurer);
		}
	}
}
