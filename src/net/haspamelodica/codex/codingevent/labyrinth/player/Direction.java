package net.haspamelodica.codex.codingevent.labyrinth.player;

/**
 * Higher x coordinates are right, higher y coordinates are down.
 */
public enum Direction
{
	UP(0, -1),
	UP_RIGHT(1, -1),
	RIGHT(1, 0),
	DOWN_RIGHT(1, 1),
	DOWN(0, 1),
	DOWN_LEFT(-1, 1),
	LEFT(-1, 0),
	UP_LEFT(-1, -1);

	private final int	dx;
	private final int	dy;

	private Direction(int dx, int dy)
	{
		this.dx = dx;
		this.dy = dy;
	}

	public int getDx()
	{
		return dx;
	}
	public int getDy()
	{
		return dy;
	}
}