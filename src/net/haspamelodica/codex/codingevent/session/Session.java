package net.haspamelodica.codex.codingevent.session;

import net.haspamelodica.codex.codingevent.challenges.Dimens;
import net.haspamelodica.codex.codingevent.challenges.Evaluation;
import net.haspamelodica.codex.codingevent.labyrinth.Color;
import net.haspamelodica.codex.codingevent.labyrinth.LabyrinthTile;
import net.haspamelodica.codex.codingevent.labyrinth.player.Direction;

public interface Session
{
	public void loginOrRegister(String username, String password) throws ServerException;

	public Dimens startChallenge() throws ServerException;

	public void move(Direction dir) throws ServerException;
	public Color look(Direction dir) throws ServerException;

	public Evaluation reportSolution(LabyrinthTile solution) throws ServerException;
}
