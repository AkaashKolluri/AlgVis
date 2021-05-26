public class Pair
{
    int x;
    int y;

    public Pair(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object y)
    {
        Pair temp = (Pair)y;
        return this.x == temp.x && this.y==temp.y;
    }
}