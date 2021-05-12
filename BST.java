class BST
{
	private MyNode root;

	public BST()
	{
        root = null;
	}

	public MyNode getRoot()
	{
		return root;
	}

	public void add(int v)
	{
		MyNode temp = root;
		MyNode temp1 = root;
		while(temp1!=null)
		{
			temp = temp1;
			if(v<temp.getValue())
				temp1 = temp1.getLeft();
			else
				temp1 = temp1.getRight();
		}

		if(temp == null)
			root = new MyNode(v, null, null);
		else if(v<temp.getValue())
			temp.setLeft(new MyNode(v, null, null));
		else
			temp.setRight(new MyNode(v, null, null));
	}

	//method for public heigh access
	public int height()
	{
		//start recrusive method
		return heightHelper(root);
	}

	//recurivse method
	private int heightHelper(MyNode t)
	{
		if(t == null)
			return 0;
		return 1+Math.max(heightHelper(t.getRight()), heightHelper(t.getLeft()));
	}
}




