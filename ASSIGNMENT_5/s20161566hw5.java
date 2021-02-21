package hw5;
import java.awt.*;
import java.awt.event.*;

class WindowDestroyer extends WindowAdapter
{
    public void windowClosing(WindowEvent e) 
    {
        System.exit(0);
    }
}

public class s20161566hw5 extends Frame implements ActionListener
{  	 private Canvas canvas; 
	public s20161566hw5()
   	{  	canvas = new Canvas();
      	add("Center", canvas);
      	Panel p = new Panel();
		Button s = new Button("Start");
		Button c = new Button("Close");
		p.add(s);	p.add(c);
      	s.addActionListener(this);
		c.addActionListener(this);
      	add("South", p);
   	}
	public void actionPerformed(ActionEvent evt)
   	{  	if (evt.getActionCommand() == "Start")
      	{  	
   		Ball b = new Ball(canvas);
         		b.start();
      	}
      	else if (evt.getActionCommand() == "Close")
         	System.exit(0);
   	}
	public static void main(String[] args)
   	{  	
		Frame f = new s20161566hw5();
      	f.setSize(400,300);
      	WindowDestroyer listener = new WindowDestroyer();  
      		f.addWindowListener(listener);
      	f.setVisible(true);  
   }
	private Canvas box;   
class Ball extends Thread
{  	
	
	private int x[] = new int[100];
	private int y[] = new int[100];
	private int dx[] = new int[100];
	private int dy[] = new int[100];
	private int check[] = new int[100];

	private int ball_size[] = new int [100];
	private int total_ball=5;
	private int total_move=0;
	public Ball(Canvas c) 	{ box = c; }
	public void draw()
		{ 
		for(int i=0;i<total_ball;i++) {
			x[i]=200;
			y[i]=150;
			dx[i]=1;
			dy[i]=1;
			ball_size[i]=20;
		}
		dx[0] = 2; dy[0] = 1;
		dx[1] = 2; dy[1] = -1;
		dx[2] = -2; dy[2] = 1;
		dx[3] = -2; dy[3] = -1;
		dx[4] = -1; dy[4] = -2;
		Graphics g = box.getGraphics();
		for(int  i=0;i<total_ball;i++)
		g.fillOval(x[i], y[i],ball_size[i] ,ball_size[i]);
  		g.dispose();	
  		}
	
		public void move()
		{  	
			
			Graphics g = box.getGraphics();
	  		g.setXORMode(box.getBackground());
	  		for(int  i=0;i<total_ball;i++) //출력
	  			g.fillOval(x[i], y[i],ball_size[i] ,ball_size[i]);
	  		
	  		
	  		for(int i=0; i< total_ball; i++) {
	  			x[i] += dx[i];
	  			y[i] += dy[i];
	  		}
	  		for(int i=0;i<100;i++)
				check[i]=0;
	  		int temp=total_ball;
	  		for(int i=0;i<temp-1;i++) {
	  			for(int j=i+1;j<temp;j++) {
	  				if(Math.sqrt((x[i]-x[j])*(x[i]-x[j])+(y[i]-y[j])*(y[i]-y[j]))< (ball_size[i]/2+ball_size[j]/2)  && check[i]==0 && check[j]==0 && total_move>30) {
	  					if(ball_size[i] > 1 || ball_size[j] > 1) {
	  						meet_move(i,j);
	  						check[i]=1;
	  						check[j]=1;
	  					}
	  				}
	  			}
	  		}
	  		
	  		
	
	  		Dimension d = box.getSize();
	  		for(int  i=0;i<total_ball;i++) { // 벽에 부딪히는 경우 다음 이동
		  		if (x[i] < 0) { x[i] = 0; dx[i] = -dx[i]; }
		  		if (x[i] + ball_size[i] >= d.width) { x[i] = d.width - ball_size[i]; dx[i] = -dx[i]; }
		  		if (y[i] < 0) { y[i] = 0; dy[i] = -dy[i]; }
		  		if (y[i] + ball_size[i] >= d.height) { y[i] = d.height - ball_size[i]; dy[i] = -dy[i]; }
	  		}
	  		
	  		for(int  i=0;i<total_ball;i++) // 출력
	  			g.fillOval(x[i], y[i],ball_size[i] ,ball_size[i]);
	  		g.dispose();	
  		}
		public void meet_move(int i, int j) {
			System.out.print("dot: "+i+ "\tdot: " +j+"\tsize: "+ball_size[i]+", "+ball_size[j]+"\t"+total_ball);
			
			int temp_dx_i, temp_dx_j;
			int temp_dy_i, temp_dy_j;
			int save_non_1=101;
			int save_1=101;
					
			if(ball_size[i] == 1) {
				save_non_1=j;
				save_1 =i;
			}
			if(ball_size[j] == 1) {
				save_non_1=i;
				save_1=j;
			}
			if(save_non_1 == 101) {
				temp_dx_i=dx[i]; temp_dx_j=dx[j];
				temp_dy_i=dy[i]; temp_dy_j=dy[j];
		
				
				x[total_ball]=x[i]+ball_size[i];
				y[total_ball]=y[i]+ball_size[i];
				x[total_ball+1]=x[i]+ball_size[i];
				y[total_ball+1]=y[i]-ball_size[i];
				x[i]=x[j]+ball_size[j];
				y[i]=y[j]+ball_size[j];
				x[j]=x[j]+ball_size[j];;
				y[j]=y[j]-ball_size[j];
				
				ball_size[total_ball]=ball_size[i]/2;
				ball_size[total_ball+1]=ball_size[i]/2;
				
				ball_size[i]=ball_size[j]/2;
				ball_size[j] = ball_size[j]/2;				
				
				if((float)(temp_dy_i+3)/(temp_dx_i+2) != (float)temp_dy_j/temp_dx_j) {
					dx[i]=temp_dx_i+2;
					dy[i]=temp_dy_i+3;
				}
				else {
					dx[i]=temp_dx_i+2;
					dy[i]=temp_dy_i+5;
				}
				
				if((float)(temp_dy_i-3)/(temp_dx_i-2) != (float)temp_dy_j/temp_dx_j) {
					dx[j]=temp_dx_i-2;
					dy[j]=temp_dy_i-3;
				}
				else {
					dx[j]=temp_dx_i-2;
					dy[j]=temp_dy_i-5;
				}
				
				if((float)(temp_dy_j+3)/(temp_dx_j+2) != (float)temp_dy_i/temp_dx_i) {
					dx[total_ball]=temp_dx_j+2;
					dy[total_ball]=temp_dy_j+5;
				}
				else {
					dx[total_ball]=temp_dx_j+2;
					dy[total_ball]=temp_dy_j+5;
				}
				
				if((float)(temp_dy_j-3)/(temp_dx_j-2) != (float)temp_dy_i/temp_dx_i) {
					dx[total_ball+1]=temp_dx_j-2;
					dy[total_ball+1]=temp_dy_j-3;
				}
				else {
					dx[total_ball+1]=temp_dx_j-2;
					dy[total_ball+1]=temp_dy_j-5;
				}
				total_ball += 2;
			}
			else {
				x[total_ball]=x[i];
				y[total_ball]=y[i];
				ball_size[total_ball]=ball_size[save_non_1]/2;
				ball_size[save_non_1]=ball_size[save_non_1]/2;
				
				temp_dx_i=dx[save_non_1];
				temp_dy_i=dy[save_non_1];
				temp_dx_j=dx[save_1];
				temp_dy_j=dy[save_1];
				
				if((float)(temp_dy_j+3)/(temp_dx_j+2) != (float)temp_dy_i/temp_dx_i) {
					dx[save_non_1]=temp_dx_j+2;
					dy[save_non_1]=temp_dy_j+3;
				}
				else {
					dx[save_non_1]=temp_dx_j+2;
					dy[save_non_1]=temp_dy_j+5;
				}
				
				if((float)(temp_dy_j-3)/(temp_dx_j-2) != (float)temp_dy_i/temp_dx_i) {
					dx[total_ball]=temp_dx_j-2;
					dy[total_ball]=temp_dy_j-3;
				}
				else {
					dx[total_ball]=temp_dx_j-2;
					dy[total_ball]=temp_dy_j-5;
				}
				
				dx[save_1]=-temp_dx_j;
				dy[save_1]=-temp_dy_j;
				total_ball++;				
			}
			System.out.println("-->\t"+ total_ball+" balls bouncing");
	
				
		}
	public void start()
		{  	
			draw();
			while(true)
			{  	
				int flag=0;
				for(int i=0;i<total_ball;i++) {
					if(ball_size[i]==1)
						flag=1;
				}
				if(flag==1) {
					System.out.println("---------------------result----------------------------");
					for(int i =0; i<total_ball;i++)
						System.out.println("ball number: "+(i+1)+"\tx:"+x[i]+"\ty:"+y[i]+"\tsize:"+ball_size[i]);
					break;
				}
				else {
				move();
				total_move++;
					try { Thread.sleep(5); } catch(InterruptedException e) {}
				} 
			}
  		}
}	
}
