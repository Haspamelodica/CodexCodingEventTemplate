package net.haspamelodica.codex.codingevent.challenges;

public class Evaluation
{
	private final boolean	correct;
	private final int		moveOverRedCount;
	private final int		moveOverBlackCount;
	private final int		lookCount;
	private final long		totalCost;

	public Evaluation(boolean correct, int moveOverRedCount, int moveOverBlackCount, int lookCount, long totalCost)
	{
		this.correct = correct;
		this.moveOverRedCount = moveOverRedCount;
		this.moveOverBlackCount = moveOverBlackCount;
		this.lookCount = lookCount;
		this.totalCost = totalCost;
	}

	public boolean isCorrect()
	{
		return correct;
	}
	public int getMoveOverRedCount()
	{
		return moveOverRedCount;
	}
	public int getMoveOverBlackCount()
	{
		return moveOverBlackCount;
	}
	public int getLookCount()
	{
		return lookCount;
	}
	public long getTotalCost()
	{
		return totalCost;
	}

	@Override
	public String toString()
	{
		return "Solution is " + (correct ? "" : "in") + "correct. Total cost: " + totalCost +
				", moves over red: " + moveOverRedCount + ", moves over black: " + moveOverBlackCount +
				", looks: " + lookCount;
	}
}
