package net.haspamelodica.codex.codingevent.labyrinth.player;

import net.haspamelodica.codex.codingevent.labyrinth.Color;
import net.haspamelodica.codex.codingevent.session.ServerException;

public interface LabyrinthPlayer
{
	/** Move in the given direction according to the rules in README.md */
	public void move(Direction dir) throws ServerException;
	/** Look in the given direction according to the rules in README.md and return the seen color */
	public Color look(Direction dir) throws ServerException;
}
