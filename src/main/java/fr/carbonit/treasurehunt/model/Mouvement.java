package fr.carbonit.treasurehunt.model;

public class Mouvement {

	private final Moves action;
	private boolean isMoveAlreadyMade;

	public Mouvement(Moves action, boolean isMoveAlreadyMade) {
		this.action = action;
		this.isMoveAlreadyMade = isMoveAlreadyMade;
	}

	public Moves getAction() {
		return action;
	}

	public Boolean isMoveAlreadyMade() {
		return isMoveAlreadyMade;
	}

	public void setMoveAlreadyMade(Boolean moveAlreadyMade) {
		isMoveAlreadyMade = moveAlreadyMade;
	}
}
