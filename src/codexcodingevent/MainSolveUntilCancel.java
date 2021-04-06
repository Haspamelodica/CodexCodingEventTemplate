package codexcodingevent;

import java.io.IOException;
import java.net.UnknownHostException;

import net.haspamelodica.codex.codingevent.labyrinth.player.LabyrinthPlayer;
import net.haspamelodica.codex.codingevent.session.ServerException;
import net.haspamelodica.codex.codingevent.session.Session;

public class MainSolveUntilCancel
{
	public static void main(String[] args) throws UnknownHostException, ServerException, InterruptedException, IOException
	{
		MainSolveOne.runSession(MainSolveUntilCancel::solveChallengesUntilCancel);
	}

	public static void solveChallengesUntilCancel(Session session, LabyrinthPlayer player) throws ServerException
	{
		Thread cancelThread = new Thread(() ->
		{
			try
			{
				System.out.println("Press Enter to cancel");
				System.in.read();
			} catch(IOException e)
			{}
		});
		cancelThread.setDaemon(true);
		cancelThread.start();
		while(cancelThread.isAlive())
			MainSolveOne.solveOneChallenge(session, player);
	}
}
