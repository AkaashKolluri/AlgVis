import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JToggleButton;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.*;



public class PathScreen extends JPanel implements MouseListener, ActionListener, ChangeListener
{
	private Rectangle r[][];
	private int rCoords[][][]; //x,y,two coords (top left)
	private int rColor[][]; //x,y,if start/end- 0 for nothing 1 for start 2 for end
	private int rClicked[]; //x,y coords of recently clicekd square
	private boolean changeSE=true; //Change Start/End of squares or not
	private boolean firstTime=true; //Special case if just starting out

	private Rectangle rh[][]; //Horizontal Walls
	private Rectangle rv[][]; //Vertical Walls
	private int ch[][]; //Color of rh, 0 red 1 green
	private int cv[][]; //Color of rv
	private int playLevel;
	private int level; //CHANGE THIS LATER
	private int thickness=10; //Thickness of Walls

	private JButton reset;
	private JButton startBFS;
	private JButton createRandomMaze;
	private JButton startDFS;

	private JSlider speed;
	private boolean selected = false;
	private int begin[]=new int[2];
	private int end[]=new int[2];
	private int[][] nums;
	private HashMap<Pair, Integer> nums2;
	private boolean done = false;
	
	//Upon DFSing/BFSing...
	private boolean start;
	private boolean checked[][];
	private boolean visited[][];
	private JToggleButton send;
	
	private Queue<Integer[]> visitedPoints=new LinkedList<Integer[]>();
	
	//Timer
	private Timer time;
	private boolean timer = false;
	

	
    public PathScreen()
	{
		nums = new int[10][10];

		nums2 = new HashMap<Pair,Integer>();
		nums2.put(new Pair(0,0),1);
		JButton ex = new JButton("Exit to main");
		ex.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent event) 
			{
				
				Main.switchScreen("HomeScreen");
				
            }
          });
        add(ex);
    	time = new Timer(250, this);
        //setting size to whatever the window size is, and making it visible
		setSize(Main.width, Main.height);
		setVisible(true);
		
		
		String level1 = "10";

        level=Integer.parseInt(level1);
		
        //Boring Stuff
		setSize(this.getSize());
        addMouseListener(this);
		setVisible(true);
		
	
	
		this.playLevel=level;
		setSize(2000, 1500);
		

        r=new Rectangle[level][level];
		rCoords=new int[level][level][2];
		rColor=new int[level][level];
		rClicked=new int[2]; rClicked[0]=0; rClicked[1]=0;
		
		for(int i=0; i<level; i++) {
			for(int j=0; j<level; j++) {
				rCoords[i][j][0]=110+600*i/(level);
				rCoords[i][j][1]=110+600*j/(level);
				r[i][j]=new Rectangle(rCoords[i][j][0],rCoords[i][j][1],480/(level),480/level);
			}
		}
		
		rColor[0][0]=1;
		rColor[level-1][level-1]=2;
		
		rh=new Rectangle[level-1][level];
		rv=new Rectangle[level][level-1];
		
		ch=new int[level-1][level];
		cv=new int[level][level-1];
		
		int k=(600)/(playLevel);
		
		for(int i=0; i<level-1; i++) {
			for(int j=0; j<level; j++) {
				rh[i][j]=new Rectangle(100-(thickness/2)+j*(600/playLevel)+120/playLevel, 100+k*(i+1), 
						600/playLevel-120/playLevel, thickness);
				rv[j][i]=new Rectangle(100+k*(i+1), 100-(thickness/2)+j*(600/playLevel)+120/playLevel, 
						thickness,600/playLevel-120/playLevel);
			}
		}
		
		checked=new boolean[level][level];
		visited=new boolean[level][level];
		
		
		setVisible(true); 
		
		start=false;
		
		startDFS = new JButton("Start DFS Algorithm");
		startDFS.setEnabled(true);
	    startDFS.addActionListener(new ActionListener() 
		{
	        public void actionPerformed(ActionEvent event) 
			{
				reset.setEnabled(false);
				startBFS.setEnabled(false);
				createRandomMaze.setEnabled(false);
				start = true;
				for(int i=0; i<level; i++) {
					for(int j=0; j<level; j++) {
						if(rColor[i][j]==1) {
							begin[0]=i;
							begin[1]=j;
						}
						else if(rColor[i][j]==2) {
							end[0]=i;
							end[1]=j;
						}
					}
				}
				
			
				dfs(begin[0],begin[1],0);
				start = true;
				timer=true;
				time.start();
	        	
	        }
	      });
	    add(startDFS);



		startBFS = new JButton("Start BFS Algorithm");
		startBFS.setEnabled(true);
	    startBFS.addActionListener(new ActionListener() 
		{
	        public void actionPerformed(ActionEvent event) 
			{
				reset.setEnabled(false);
				startDFS.setEnabled(false);
				createRandomMaze.setEnabled(false);
				start = true;
				for(int i=0; i<level; i++) {
					for(int j=0; j<level; j++) {
						if(rColor[i][j]==1) {
							begin[0]=i;
							begin[1]=j;
						}
						else if(rColor[i][j]==2) {
							end[0]=i;
							end[1]=j;
						}
					}
				}
				
		
				bfs(begin[0],begin[1]);
				start = true;
				timer=true;
				time.start();
	        	
	        }
	      });
	    add(startBFS);


		createRandomMaze = new JButton("Create a random maze");
		createRandomMaze.setEnabled(true);
	    createRandomMaze.addActionListener(new ActionListener() 
		{
	        public void actionPerformed(ActionEvent event) 
			{
				createRandomMaze();
	        	
	        }
	      });
	    add(createRandomMaze);

		send = new JToggleButton("Press me to select the maze end. Unpress me to select the start.");
		ItemListener il = new ItemListener() {
            public void itemStateChanged(ItemEvent il)
            {
                int state = il.getStateChange();
                selected = (state == ItemEvent.SELECTED);
                
            }
        };
  

      	send.addItemListener(il);
		add(send);




	    reset = new JButton("Reset the maze");
		reset.setEnabled(true);
	    reset.addActionListener(new ActionListener() 
		{
	        public void actionPerformed(ActionEvent event) 
			{
				reset();
	        	
	        }
	      });
	    add(reset);

		speed = new JSlider(0,1000,250);
		
		speed.setMajorTickSpacing(200);
        speed.setMinorTickSpacing(200);
		speed.setPaintTrack(true);
        speed.setPaintTicks(true);
        speed.setPaintLabels(true);
 
        speed.addChangeListener(this);
		
	
		add(speed);
	}

	public void stateChanged(ChangeEvent e)
    {
		time.stop();
		time = new Timer(speed.getValue(), this);
		time.start();
    }
	
    public void reset() {
		done = false;
		start=false;
		time.stop();
		nums = new int[10][10];

		nums2 = new HashMap<Pair,Integer>();
    	r=new Rectangle[level][level];
		rCoords=new int[level][level][2];
		rColor=new int[level][level];
		rClicked=new int[2]; rClicked[0]=0; rClicked[1]=0;
		
		for(int i=0; i<level; i++) {
			for(int j=0; j<level; j++) {
				rCoords[i][j][0]=110+600*i/(level);
				rCoords[i][j][1]=110+600*j/(level);
				r[i][j]=new Rectangle(rCoords[i][j][0],rCoords[i][j][1],480/(level),480/level);
			}
		}
		
		rColor[0][0]=1;
		rColor[level-1][level-1]=2;
		
		rh=new Rectangle[level-1][level];
		rv=new Rectangle[level][level-1];
		
		ch=new int[level-1][level];
		cv=new int[level][level-1];
		
		int k=(600)/(playLevel);
		
		for(int i=0; i<level-1; i++) {
			for(int j=0; j<level; j++) {
				rh[i][j]=new Rectangle(100-(thickness/2)+j*(600/playLevel)+120/playLevel, 100+k*(i+1), 
						600/playLevel-120/playLevel, thickness);
				rv[j][i]=new Rectangle(100+k*(i+1), 100-(thickness/2)+j*(600/playLevel)+120/playLevel, 
						thickness,600/playLevel-120/playLevel);
			}
		}
		
		checked=new boolean[level][level];
		visited=new boolean[level][level];
		
		
		setVisible(true); 
		
		start=false;
		timer=false;
		time.stop();
		createRandomMaze.setEnabled(true);
		startDFS.setEnabled(true);
		startBFS.setEnabled(true);
		changeSE=true;
		repaint();
    }
    
	public void paintComponent(Graphics g)
	{
		// g.setColor(Color.WHITE);
		// g.fillRect(106,106,594,594);
		// g.setColor(Color.BLACK);
		if(done == false && start && visitedPoints.size()==0)
		{
			Font f = new Font ("Helvetica", 0, 24);
						g.setFont(f);
			g.setColor(Color.black);
			g.drawString("No path found", 930, 410);
		}
		else if(done == true && visitedPoints.size() == 0)
		{
			Font f = new Font ("Helvetica", 0, 24);
						g.setFont(f);
			g.setColor(Color.black);
			g.drawString("Path found in " +nums[end[0]][end[1]]+ " steps", 930, 410);
		}
	    drawGrid(g);
	    
	    

	    	for(int i=0; i<playLevel; i++) {
				for(int j=0; j<playLevel; j++) {
					
					if(rColor[i][j]==1)
						g.setColor(Color.green);
					else if(rColor[i][j]==2)
						g.setColor(Color.red);
					else if(checked[i][j])
						g.setColor(Color.cyan);
					else
						g.setColor(Color.white);	
						
					
					g.fillRect(rCoords[i][j][0]+1,rCoords[i][j][1],480/(playLevel)-2,480/(playLevel)-4);
					
					if(nums[i][j] != 0 && ( checked[i][j] || (visitedPoints.size()==0 && rColor[i][j] ==2)))
					{
						Font f = new Font ("Helvetica", 0, 12);
						g.setFont(f);
							g.setColor(Color.black);
						if(nums[i][j]<10)
							g.drawString(nums[i][j] + "", rCoords[i][j][0]+20,rCoords[i][j][1]+26 );
						else
						g.drawString(nums[i][j] + "", rCoords[i][j][0]+16,rCoords[i][j][1]+26 );
	
					}
				}
			}

	    
	    
		    
		    for(int i=0; i<playLevel; i++) {
				for(int j=0; j<playLevel; j++) {
					drawSquare(g,i,j);
				}
			}	
			
		    
		    changeSE=false;
		    firstTime=false;
	    
	
	    
	}
	
	public void drawGrid(Graphics g)
	{
		
		
		g.drawRect(106,106,594,594);
		
		int k=(600)/(playLevel);
		
		for(int i=0; i<playLevel-1; i++) {
			for(int j=0; j<playLevel; j++) {
				if(ch[i][j]==0) {
					g.setColor(Color.BLACK);
					g.drawRect(100-(thickness/2)+j*(600/playLevel)+120/playLevel+3, 100+k*(i+1)-2, 
						600/playLevel-120/playLevel, thickness);
				}
				else if(ch[i][j]==1) {
					g.setColor(Color.BLACK);
					g.fillRect(100-(thickness/2)+j*(600/playLevel)+120/playLevel+3, 100+k*(i+1)-2, 
						600/playLevel-120/playLevel, thickness);
				}
				
				
				if(cv[j][i]==0) {
					g.setColor(Color.BLACK);
					g.drawRect(100+k*(i+1)-1, 100-(thickness/2)+j*(600/playLevel)+120/playLevel+1, 
						thickness-1,600/playLevel-120/playLevel);
				}
				else if(cv[j][i]==1) {
					g.setColor(Color.BLACK);
					g.fillRect(100+k*(i+1)-1, 100-(thickness/2)+j*(600/playLevel)+120/playLevel+1, 
						thickness-1,600/playLevel-120/playLevel);
				}
				
			}
		}
	}
	
	
	public void drawSquare(Graphics g, int i, int j)
	{
		
		if(!changeSE) {
			return;
		}
		
		if(firstTime) {
			
		}
		else if(selected) {
			if(i==rClicked[0]&&j==rClicked[1]) {
				rColor[i][j]=2;
			}
			else if(rColor[i][j]==2) {
				rColor[i][j]=0;
			}
		}
		else {
			if(i==rClicked[0]&&j==rClicked[1]) {
				rColor[i][j]=1;
			}
			else if(rColor[i][j]==1) {
				rColor[i][j]=0;
			}
		}
		if(rColor[i][j]==0)
			g.setColor(Color.white);
		else if(rColor[i][j]==1)
			g.setColor(Color.green);
		else if(rColor[i][j]==2)
			g.setColor(Color.red);
		
		
		g.fillRect(rCoords[i][j][0]+1,rCoords[i][j][1],480/(playLevel)-2,480/(playLevel)-4);
	}
	

	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	
	public void mouseClicked(MouseEvent e)
	{
		if(start || done )
		{
			return;
		}

		int x = e.getX();
		int y = e.getY();
		
		boolean done=false;
		
		for(int i=0; i<playLevel-1; i++) {
			for(int j=0; j<playLevel; j++) {
				if(rh[i][j].inside(x, y)) {
					changerh(i,j);
					done=true;
					repaint();
					break;
				}
				else if(rv[j][i].inside(x,y)) {
					changerv(j,i);
					done=true;
					repaint();
					break;
				}
			}
			
			if(done) {
				break;
			}
		}
		
		for(int i=0; i<playLevel; i++) {
			for(int j=0; j<playLevel; j++) {
				if(r[i][j].inside(x, y)) {
					rClicked[0]=i; rClicked[1]=j;
					changeSE=true;
					repaint();
					return;
				}
			}
		}

		repaint();
	}
	
	
	public void changerh(int i, int j)
	{
		if(ch[i][j]==0) {
			ch[i][j]=1;
		}
		else if(ch[i][j]==1) {
			ch[i][j]=0;
		}
	
	}
	public void changerv(int i, int j)
	{
		if(cv[i][j]==0) {
			cv[i][j]=1;
		}
		else if(cv[i][j]==1) {
			cv[i][j]=0;
		}
		
	}
	
			
	public void update(Graphics g)
	{
		paint(g);
	}
	
	public void dfs(int i, int j, int x) {
		
		
		if(!start) {
			return;
		}
		
		if(i==end[0]&&j==end[1]) {
			visitedPoints.add(new Integer[] {0});
			nums[i][j] = x;
			done = true;
			start = false;
	
			return;
		}
		
		if(i==-1||j==-1||i==playLevel||j==playLevel) {
			return;
		}
		
		if(visited[i][j]) {
			return;
		}
		
		nums[i][j] = x;
		visited[i][j]=true;
		
		Integer adder[]=new Integer[2];
		adder[0]=i; adder[1]=j;
		
		visitedPoints.add(adder);
		
		if(i>0&&cv[j][i-1]==0)
			dfs(i-1,j, x+1);
		if(j>0&&ch[j-1][i]==0)
			dfs(i,j-1, x+1);
		if(i<playLevel-1&&cv[j][i]==0)	
			dfs(i+1,j, x+1);
		if(j<playLevel-1&&ch[j][i]==0)
			dfs(i,j+1, x+1);
		
		
	}

	public void bfs(int i, int j) {
		Queue<Integer[]> qq= new LinkedList<Integer[]>();
		

		
		Integer adder[]=new Integer[3];


		adder[0]=i; adder[1]=j;
		adder[2] = 0;
		
		
		qq.add(adder);
		while(qq.isEmpty() == false)
		{
			Integer[] temp  = qq.remove();

			
			int ii = temp[0];
			int jj = temp[1];
			int xx = temp[2];
			

			if(ii==-1||jj==-1||ii==playLevel||jj==playLevel) {
				continue;
			}

			if(ii==end[0]&&jj==end[1]) {
				visitedPoints.add(new Integer[] {0});
				done = true;
				nums[ii][jj] = xx;
				start = false;
				return;
			}
			
			
			if(visited[ii][jj]) {
				continue;
			}
			nums[ii][jj] = xx;
			visitedPoints.add(temp);
			visited[ii][jj]=true;
			if(ii>0&&cv[jj][ii-1]==0)
				qq.add(new Integer[]{ii-1,jj,xx+1 });
			if(jj>0&&ch[jj-1][ii]==0)
				qq.add(new Integer[]{ii,jj-1, xx+1});
			if(ii<playLevel-1&&cv[jj][ii]==0)	
				qq.add(new Integer[]{ii+1,jj, xx+1});
			if(jj<playLevel-1&&ch[jj][ii]==0)
				qq.add(new Integer[]{ii,jj+1,xx+1});
		}
		
	}
	
	public void createRandomMaze()
	{
		for(int i =0; i<15; i++)
		{
			int x = (int) (Math.random()*playLevel);
			int y = (int) (Math.random()*(playLevel-1));
			changerh(y,x);
			x = (int) (Math.random()*playLevel-1);
			y = (int) (Math.random()*(playLevel));	
			changerv(y, x);
		}
		repaint();
	}

	public void actionPerformed(ActionEvent e)
	{
		
		if(timer)
		{
			
			if(visitedPoints.size()>1) {
				Integer[] yes1=visitedPoints.remove();
				checked[yes1[0]][yes1[1]]=true;
			}
			else if(visitedPoints.size() == 1)
			{
				visitedPoints.remove();
			}
			else
			{
				
				reset.setEnabled(true);
				startBFS.setEnabled(false);
				startDFS.setEnabled(false);
				
			}
			repaint();
		}
		
		
	}
}