package hw4;

import java.awt.*;
import java.awt.event.*;

class WindowDestroyer extends WindowAdapter
{
    public void windowClosing(WindowEvent e) 
    {
        System.exit(0);
    }
}

public class s20161566hw4 implements ActionListener {
	private static TextField tf;
	private static TextField tf2;
	private Frame f;
	private int s_zero=0;
	private String temp;
	private int end_flag=0;
	private int error=0;
	
	public s20161566hw4(String str) {

		f= new Frame(str);
		f.setSize(400,250);
		Panel pan = new Panel();
		Button b[] = new Button[20];
		
		pan.setBackground(Color.black);
		pan.setSize(700,700);
		
		tf = new TextField("0");
		tf2 = new TextField("");
		
		f.add(tf,BorderLayout.NORTH);
		f.add(tf2,BorderLayout.CENTER);
		f.add(pan,BorderLayout.SOUTH);

		pan.setLayout(new GridLayout(4,5,10,10));
			
		b[0]= new Button("7");		
		b[1]= new Button("8");
		b[2]= new Button("9");
		b[3]= new Button("/");
		b[4]= new Button("C");
		b[5]= new Button("4");
		b[6]= new Button("5");
		b[7]= new Button("6");
		b[8]= new Button("*");
		b[9]= new Button("<-");
		b[10]= new Button("1");
		b[11]= new Button("2");
		b[12]= new Button("3");
		b[13] =new Button("-");
		b[14]= new Button("(");
		b[15]= new Button("0");
		b[16]= new Button(".");
		b[17]= new Button("=");
		b[18]= new Button("+");
		b[19]= new Button(")");
		
		b[0].setForeground(Color.blue);
		b[1].setForeground(Color.blue);
		b[2].setForeground(Color.blue);
		b[5].setForeground(Color.blue);
		b[6].setForeground(Color.blue);
		b[7].setForeground(Color.blue);
		b[10].setForeground(Color.blue);
		b[11].setForeground(Color.blue);
		b[12].setForeground(Color.blue);
		b[15].setForeground(Color.blue);
		b[3].setForeground(Color.red);
		b[4].setForeground(Color.red);
		b[8].setForeground(Color.red);
		b[9].setForeground(Color.red);
		b[13].setForeground(Color.red);
		b[14].setForeground(Color.red);
		b[17].setForeground(Color.red);
		b[18].setForeground(Color.red);
		b[19].setForeground(Color.red);
		
		for(int i=0;i<20;i++) {
			pan.add(b[i]);		
			b[i].addActionListener(this);
		}
		

		WindowDestroyer listener = new WindowDestroyer();  
      	f.addWindowListener(listener);
 
	    f.setVisible(true);
	}
	public double find_result(int [] numbers,int [] non_numbers,int order[], int start, int end) {
		double result=0;
		double save=0;
		int flag=0;
		
		for(int i=start;i<end;i++) {
			if(order[i] < 0) {
				if(non_numbers[-1*order[i]-1]==5) {
					if(i+1<=end) {
						save=find_result(numbers,non_numbers,order,i+1,end);
						flag=1;
						if(i==start)
							result=save;
					}
					else {
						error=1;
						return 0;
					}
				}
				else if(non_numbers[-1*order[i]-1] == 6) {}
				else if(order[i+1] <0) {
					if(non_numbers[-1*order[i+1]-1] == 5){
						if(i+2<end) {
							save = find_result(numbers,non_numbers,order,i+2,end);
							flag=1;
						}
						else {
							error=1;
							return 0;
						}						
					}
					else{
						error=4;
						return 0;				
					}
				}
				else 
					save = numbers[order[i+1]-1];
				
				if(error!=0)
					return 0;
			
				if(non_numbers[-1*order[i]-1] == 1) {
					result += save;
					save=0;
				}
				else if(non_numbers[-1*order[i]-1] == 2) {
					result -= save;
					save=0;
				}
				else if(non_numbers[-1*order[i]-1] == 3) {
					if(i!=start) {
					result *= save;
					save=0;
					}
					else {
						error=3;
						return 0;
					}
				}
				else if(non_numbers[-1*order[i]-1] == 4) {
					if(save==0) {
						error=2;
						return 0;
					}
					else{
						if(i!=start) {
						result /=save;
						save=0;
						}
						else {
							error=3;
							return 0;
						}
					}
				}
				else if(non_numbers[-1*order[i]-1]==6) {
					end_flag=i;
					return result;
				}
				else {}
				if(flag==1) {
					i=end_flag;
					flag=0;
				}
			}
			if(error!=0)
				return 0;
			if(order[i]>0 && i==start)
				result=numbers[order[i]-1];
		}
		if(order[end]<0) {
			if(non_numbers[-1*order[end]-1]==6) {
				end_flag=end;
				return result;
			}
			else {
				error=3;
				return 0;				
			}
		}
		if(start==end)
			return numbers[order[start]-1]; 
		
		return result;		
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("C")) {
			tf.setText("0");
			tf2.setText("");
			s_zero=0;
		}
		else if(e.getActionCommand().equals("<-"))
		{
			if(s_zero==0) {
				tf.setText("0");
				tf2.setText("");
			}
			else {
				temp = tf.getText();
				if(temp.equals("0") || temp.length()==1) {
					tf.setText("0");
					s_zero=0;
				}						
				else {
					temp = temp.substring(0,temp.length()-1);
					tf.setText(temp);
				}
			}
		}
		else if(e.getActionCommand().equals("=")){
			temp = tf.getText();
			int[] numbers = new int[temp.length()+1];
			int[] non_numbers = new int [temp.length()+1];
			int[] order = new int [temp.length()+1];
			int numbers_num=0;
			int non_numbers_num=0;
			int order_num=0;
			int store=0;
			int check_store=0;
			int bracket=0;
			
			for(int i=0;i<temp.length();i++) {
				if(temp.substring(i,i+1).equals("+")) {
					non_numbers[non_numbers_num]=1;
					non_numbers_num++;
					if(i!=0 && temp.substring(i-1,i).equals(")")){}
					else {
						if(check_store == 1) {
							numbers[numbers_num]=store;
							numbers_num++;
							store=0;
							check_store=0;
							order[order_num]=numbers_num;
							order_num++;
						}
					}
					order[order_num]=-1*non_numbers_num;
					order_num++;
					
				}
				else if(temp.substring(i,i+1).equals("-")) {
					non_numbers[non_numbers_num]=2;
					non_numbers_num++;
					if( i != 0 && temp.substring(i-1,i).equals(")")){}
					else {
						if(check_store == 1) {
							numbers[numbers_num]=store;
							numbers_num++;
							store=0;
							check_store=0;
							order[order_num]=numbers_num;
							order_num++;
						}
					}
					order[order_num]=-1*non_numbers_num;
					order_num++;
				}
				else if(temp.substring(i,i+1).equals("*")) {
					non_numbers[non_numbers_num]=3;
					non_numbers_num++;
					if( i != 0 && temp.substring(i-1,i).equals(")")){}
					else {
						if(check_store == 1) {
							numbers[numbers_num]=store;
							numbers_num++;
							store=0;
							check_store=0;
							order[order_num]=numbers_num;
							order_num++;
						}
					}
					order[order_num]=-1*non_numbers_num;
					order_num++;
				}
				else if(temp.substring(i,i+1).equals("/")) {
					non_numbers[non_numbers_num]=4;
					non_numbers_num++;
					if( i != 0 && temp.substring(i-1,i).equals(")") ){}
					else {
						if(check_store == 1) {
							numbers[numbers_num]=store;
							numbers_num++;
							store=0;
							check_store=0;
							order[order_num]=numbers_num;
							order_num++;
						}
					}
					order[order_num]=-1*non_numbers_num;
					order_num++;
				}
				else if(temp.substring(i,i+1).equals("(")) {
					bracket++;
					non_numbers[non_numbers_num]=5;
					non_numbers_num++;
					order[order_num]=-1*non_numbers_num;
					order_num++;
				}
				else if(temp.substring(i,i+1).equals(")")) {
					bracket--;
					if(bracket<0)
						error=1;
					non_numbers[non_numbers_num]=6;
					non_numbers_num++;
					if(check_store == 1) {
						numbers[numbers_num]=store;
						numbers_num++;
						store=0;
						check_store=0;
						order[order_num]=numbers_num;
						order_num++;
					}
					order[order_num]=-1*non_numbers_num;
					order_num++;
				}
				else if(temp.substring(i,i+1).equals(".")) {
					error=5;
				}
				else {
						store = store*10 + Integer.parseInt(temp.substring(i,i+1));
						check_store=1;
				}
			}
			if(check_store==1) {
					numbers[numbers_num]=store;
					numbers_num++;
					order[order_num]=numbers_num;
					order_num++;
			}
			end_flag=0;
			
			if(error==1) {
				tf2.setText("ERROR: BRACKET");
				s_zero=0;
				error=0;
			}
			else if(error==5) {
				tf2.setText("DON'T USE THE DOT!!");
				s_zero=0;
				error=0;
			}
			else {
				double result = find_result(numbers,non_numbers,order,0,order_num-1);
			
				if(error==1) {
					tf2.setText("ERROR");
					s_zero=0;
					error=0;
				}
				else if(error==2) {
					tf2.setText("ERROR: DIVIDED BY ZERO");
					s_zero=0;
					error=0;
				}
				else if(error==3) {
					tf2.setText("ERROR: WRONG USE OF SIMBOLS");
					s_zero=0;
					error=0;
				}
				else if(error==4) {
					tf2.setText("ERROR: TWO CONSECUTIVE SIMBOLS");
					s_zero=0;
					error=0;
				}
			
				else {
					if((int)result == result)
						tf2.setText(Integer.toString((int)result));
					else
						tf2.setText(Double.toString(result));
					s_zero=0;
				}
			}		
		}
		else {
			if(s_zero==0) {
				tf.setText(e.getActionCommand());
				tf2.setText("");
				s_zero=1;
			}
			else {
				tf2.setText("");
				tf.setText(tf.getText()+e.getActionCommand());
			}
		}
	}		
	

	public static void main(String[] args) {
		s20161566hw4 f = new s20161566hw4("calculator");
	 
	}

}