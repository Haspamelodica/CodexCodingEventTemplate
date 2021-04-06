package net.haspamelodica.codex.codingevent.challenges;

public final class Dimens
{
	private final int	width;
	private final int	height;

	public Dimens(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}

	@Override
	public String toString()
	{
		return "(" + width + "|" + height + ")";
	}
}
