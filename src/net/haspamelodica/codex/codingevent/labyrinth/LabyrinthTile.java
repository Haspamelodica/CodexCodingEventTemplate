package net.haspamelodica.codex.codingevent.labyrinth;

import java.util.ArrayList;
import java.util.List;

import net.haspamelodica.codex.codingevent.labyrinth.player.Direction;

/**
 * An immutable representation of a labyrinth tile.
 */
public class LabyrinthTile
{
	private final int				width;
	private final int				height;
	private final List<List<Color>>	blocks;

	/**
	 * Create a new {@link LabyrinthTile}.
	 * 
	 * The parameter <code>blocks</code> should be a twodimensional list of block colors, in row-major format (x axis first).
	 * As stated in {@link Direction}, higher x values are right; higher y values are down. <code>(0,0)</code> is the top left corner.<br>
	 * For example, <code>blocks.get(2).get(5)</code> should be the block at x=2 and y=5.
	 */
	public LabyrinthTile(List<List<Color>> blocks)
	{
		this.width = blocks.size();
		this.height = blocks.get(0).size();

		List<List<Color>> blocksM = new ArrayList<>();
		for(List<Color> row : blocks)
		{
			if(row.size() != height)
				throw new IllegalArgumentException("Not a rectangle");
			blocksM.add(List.copyOf(row));
		}
		this.blocks = List.copyOf(blocksM);
	}

	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	public List<List<Color>> getBlocks()
	{
		return blocks;
	}
	public Color getBlockAt(int x, int y)
	{
		return blocks.get(x).get(y);
	}
}
