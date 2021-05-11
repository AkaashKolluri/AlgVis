class BST
{
	private TreeNode root;

	public BST()
	{

	}

	public TreeNode getRoot()
	{
		return root;
	}

	public void add(int v)
	{
		TreeNode temp = root;
		TreeNode temp1 = root;
		while(temp1!=null)
		{
			temp = temp1;
			if(v<temp.getValue())
				temp1 = temp1.getLeft();
			else
				temp1 = temp1.getRight();
		}

		if(temp == null)
			root = new TreeNode(v, null, null);
		else if(v<temp.getValue())
			temp.setLeft(new TreeNode(v, null, null));
		else
			temp.setRight(new TreeNode(v, null, null));
	}

	//method for public heigh access
	public int height()
	{
		//start recrusive method
		return heightHelper(root);
	}

	//recurivse method
	private int heightHelper(TreeNode t)
	{
		if(t == null)
			return 0;
		return 1+Math.max(heightHelper(t.getRight()), heightHelper(t.getLeft()));
	}
}





class TreeNode
{
	private int value;
	private TreeNode right;
	private TreeNode left;

	public TreeNode(int v, TreeNode r, TreeNode l)
	{
		value = v;
		right = r;
		left = l;
	}

	public TreeNode getRight()
	{
		return right;
	}

	public TreeNode getLeft()
	{
		return left;
	}

	public int getValue()
	{
		return value;
	}

	public void setRight(TreeNode r)
	{
		right = r;
	}

	public void setLeft(TreeNode l)
	{
		left = l;
	}

	public void setValue(int v)
	{
		value = v;
	}
}
