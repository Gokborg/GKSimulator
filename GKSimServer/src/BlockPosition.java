public class BlockPosition
{
    public int x;
    public int y;

    public BlockPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object obj)
    {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final BlockPosition obj2 = (BlockPosition) obj;
        return this.x == obj2.x && this.y == obj2.y;
    }
}
