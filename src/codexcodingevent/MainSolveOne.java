package codexcodingevent;

import java.io.IOException;
import java.net.UnknownHostException;

import net.haspamelodica.codex.codingevent.challenges.Dimens;
import net.haspamelodica.codex.codingevent.challenges.Evaluation;
import net.haspamelodica.codex.codingevent.labyrinth.Color;
import net.haspamelodica.codex.codingevent.labyrinth.LabyrinthTile;
import net.haspamelodica.codex.codingevent.labyrinth.player.Direction;
import net.haspamelodica.codex.codingevent.labyrinth.player.LabyrinthPlayer;
import net.haspamelodica.codex.codingevent.session.ServerException;
import net.haspamelodica.codex.codingevent.session.Session;
import net.haspamelodica.codex.codingevent.session.SessionOverSocket;

public class MainSolveOne
{
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException, ServerException
	{
		runSession(MainSolveOne::solveOneChallenge);
	}

	public static void runSession(SessionHandler sessionHandler) throws ServerException, InterruptedException, IOException, UnknownHostException
	{
		try(SessionOverSocket session = new SessionOverSocket(LabyrinthSolver.SERVER_ADDR, 1337))
		{
			session.loginOrRegister(LabyrinthSolver.USERNAME, LabyrinthSolver.PASSWORD);

			LabyrinthPlayer player = new LabyrinthPlayer()
			{
				@Override
				public void move(Direction dir) throws ServerException
				{
					session.move(dir);
				}
				@Override
				public Color look(Direction dir) throws ServerException
				{
					return session.look(dir);
				}
			};

			sessionHandler.run(session, player);
		}
	}

	public static void solveOneChallenge(Session session, LabyrinthPlayer player) throws ServerException
	{
		Dimens dimens = session.startChallenge();
		LabyrinthSolver solver = new LabyrinthSolver(player, dimens.getWidth(), dimens.getHeight());
		LabyrinthTile solution = solver.solve();
		Evaluation evaluation = session.reportSolution(solution);
		System.out.println(evaluation);
	}

	public static interface SessionHandler
	{
		public void run(Session session, LabyrinthPlayer player) throws ServerException;
	}
}
