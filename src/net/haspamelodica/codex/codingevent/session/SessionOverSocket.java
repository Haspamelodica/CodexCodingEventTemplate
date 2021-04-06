package net.haspamelodica.codex.codingevent.session;

import static net.haspamelodica.codex.codingevent.session.ServerException.checkNotEOF;
import static net.haspamelodica.codex.codingevent.session.ServerException.forUnexpectedByte;
import static net.haspamelodica.codex.codingevent.session.ServerException.throwForResponseIfNotSuccess;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import net.haspamelodica.codex.codingevent.challenges.Dimens;
import net.haspamelodica.codex.codingevent.challenges.Evaluation;
import net.haspamelodica.codex.codingevent.labyrinth.Color;
import net.haspamelodica.codex.codingevent.labyrinth.LabyrinthTile;
import net.haspamelodica.codex.codingevent.labyrinth.player.Direction;
import net.haspamelodica.codex.codingevent.session.ServerException.Type;

public class SessionOverSocket implements Session, AutoCloseable
{
	private final Socket			sock;
	private final DataInputStream	in;
	private final DataOutputStream	out;

	public SessionOverSocket(String host, int port) throws UnknownHostException, IOException
	{
		this(new Socket(host, port));
	}
	public SessionOverSocket(Socket sock) throws IOException
	{
		this.sock = sock;
		this.in = new DataInputStream(sock.getInputStream());
		this.out = new DataOutputStream(sock.getOutputStream());

		sock.setPerformancePreferences(2, 1, 0);
		sock.setTcpNoDelay(true);
	}

	@Override
	public void loginOrRegister(String username, String password) throws ServerException
	{
		wrapIOException(() ->
		{
			out.write('R');
			out.writeUTF(username);
			out.writeUTF(password);

			throwForResponseIfNotSuccess(in.read());
		});
	}

	@Override
	public Dimens startChallenge() throws ServerException
	{
		return wrapIOException(() ->
		{
			out.write('C');

			throwForResponseIfNotSuccess(in.read());
			int width = in.readInt();
			int height = in.readInt();
			return new Dimens(width, height);
		});
	}

	@Override
	public void move(Direction dir) throws ServerException
	{
		wrapIOException(() ->
		{
			out.write('M');
			out.write(dir.ordinal());

			throwForResponseIfNotSuccess(in.read());
		});
	}

	@Override
	public Color look(Direction dir) throws ServerException
	{
		return wrapIOException(() ->
		{
			out.write('L');
			out.write(dir.ordinal());

			throwForResponseIfNotSuccess(in.read());
			//Switch expressions are sadly not supported yet in Java 10 :(
			return parseSingleByte(c ->
			{
				switch(c)
				{
					case 'B':
						return Color.BLACK;
					case 'R':
						return Color.RED;
					default:
						return null;
				}
			});
		});
	}

	@Override
	public Evaluation reportSolution(LabyrinthTile solution) throws ServerException
	{
		return wrapIOException(() ->
		{
			out.write('S');
			out.writeInt(solution.getWidth());
			out.writeInt(solution.getHeight());
			for(int x = 0; x < solution.getWidth(); x ++)
				for(int y = 0; y < solution.getHeight(); y ++)
					//Switch expressions are sadly not supported yet in Java 10 :(
					out.write(solution.getBlockAt(x, y) == Color.BLACK ? 'B' : 'R');

			throwForResponseIfNotSuccess(in.read());
			boolean correct = in.readBoolean();
			int moveOverRedCount = in.readInt();
			int moveOverBlackCount = in.readInt();
			int lookCount = in.readInt();
			long totalCost = in.readLong();
			return new Evaluation(correct, moveOverRedCount, moveOverBlackCount, lookCount, totalCost);
		});
	}

	private <R> R parseSingleByte(ThrowingCharFunction<R> parser) throws ServerException, IOException
	{
		byte read = checkNotEOF(in.read());
		R result = parser.apply(read);
		if(result == null)
			throw forUnexpectedByte(read);
		return result;
	}
	@Override
	public void close() throws IOException
	{
		sock.close();
	}

	private static void wrapIOException(ThrowingRunnable r) throws ServerException
	{

		try
		{
			r.run();
		} catch(IOException e)
		{
			throw new ServerException(Type.IO_EXCEPTION, e);
		}
	}
	private static <T> T wrapIOException(ThrowingSupplier<T> r) throws ServerException
	{

		try
		{
			return r.get();
		} catch(IOException e)
		{
			throw new ServerException(Type.IO_EXCEPTION, e);
		}
	}

	private static interface ThrowingRunnable
	{
		public void run() throws ServerException, IOException;
	}
	private static interface ThrowingSupplier<T>
	{
		public T get() throws ServerException, IOException;
	}
	private static interface ThrowingCharFunction<R>
	{
		public R apply(byte c);
	}
}
