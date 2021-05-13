public class MyNode
{
	private int value;
	private MyNode right;
	private MyNode left;
    private boolean isLit;

	public MyNode(int v, MyNode r, MyNode l)
	{
		value = v;
		right = r;
		left = l;
        isLit = false;
	}

	public MyNode getRight()
	{
		return right;
	}

	public MyNode getLeft()
	{
		return left;
	}

	public int getValue()
	{
		return value;
	}

	public void setRight(MyNode r)
	{
		right = r;
	}

	public void setLeft(MyNode l)
	{
		left = l;
	}

	public void setValue(int v)
	{
		value = v;
	}

    public void makeLit()
    {
        isLit = true;
    }

    public void turnOff()
    {
        isLit = false;
    }
}
