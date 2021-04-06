package codexcodingevent;

import static net.haspamelodica.codex.codingevent.labyrinth.player.Direction.*;

import net.haspamelodica.codex.codingevent.labyrinth.Color;
import net.haspamelodica.codex.codingevent.labyrinth.LabyrinthTile;
import net.haspamelodica.codex.codingevent.labyrinth.player.LabyrinthPlayer;
import net.haspamelodica.codex.codingevent.session.ServerException;

public class LabyrinthSolver
{
	public static final String	USERNAME	= "User";			//TODO put your username here
	/** <b>Do not reuse a password you use elsewhere!</b> Passwords are stored and transmitted as unencrypted plaintext. */
	public static final String	PASSWORD	= "Password";		//TODO put your password here
	public static final String	SERVER_ADDR	= "139.59.215.82";

	private final LabyrinthPlayer labyrinthPlayer;

	private final int	width;
	private final int	height;

	public LabyrinthSolver(LabyrinthPlayer labyrinthPlayer, int width, int height)
	{
		this.labyrinthPlayer = labyrinthPlayer;
		this.width = width;
		this.height = height;
	}

	public LabyrinthTile solve() throws ServerException
	{
		//TODO Implement your algorithm here!

		//Example how to interact with the labyrinth to solve:
		System.out.println("One tile has dimensions " + width + " x " + height);
		Color blockRightOfMe = labyrinthPlayer.look(RIGHT);
		System.out.println("The block right of me has color " + blockRightOfMe);
		labyrinthPlayer.move(UP);

		return null;
	}
}
