import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

/*
 * COMP3270 
 * Chinese Chess AI mini project
 * Zhao Shixiong
 * UID: 3035028659
*/
public class GUI {
	
int[][] points=new int[11][12];

public final static Color red= new Color(255, 0, 0);
public final static Color black= new Color(0, 0, 0);
public final static Color orange= new Color(255, 200, 0);
JTextArea info=new JTextArea(30,10);
JTextField oxjt; // X of the piece choose to move
JTextField oyjt; // Y of the piece choose to move
JTextField xjt;  // X of the target position
JTextField yjt;  // Y of the target position

Board board;

int choosex=0;
int choosey=0;
int targetx=0;
int targety=0;




	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GUI chess=new GUI();
		chess.go();
	}
	
	public void go(){
		
		// 1 for JIANG/SHUAI(G)  2 for SHI(M) 3 for XIANG(E)
		//4 for MA(K)  5 for CHE(R)  6 for PAO(C)  7 for BING(P)
		for(int i=0;i<=10;i++){
			for(int j=0;j<=11;j++){
				points[i][j]=0;
			}
		}	
		points[1][1]=5;
		points[2][1]=4;
		points[3][1]=3;
		points[4][1]=2;
		points[5][1]=1;
		points[6][1]=2;
		points[7][1]=3;
		points[8][1]=4;
		points[9][1]=5;
		
		points[2][3]=6;
		points[8][3]=6;
	
		points[1][4]=7;
		points[3][4]=7;
		points[5][4]=7;
		points[7][4]=7;
		points[9][4]=7;
		
		points[1][10]=-5;
		points[2][10]=-4;
		points[3][10]=-3;
		points[4][10]=-2;
		points[5][10]=-1;
		points[6][10]=-2;
		points[7][10]=-3;
		points[8][10]=-4;
		points[9][10]=-5;
		
		points[2][8]=-6;
		points[8][8]=-6;
	
		points[1][7]=-7;
		points[3][7]=-7;
		points[5][7]=-7;
		points[7][7]=-7;
		points[9][7]=-7;
		
		JFrame ui=new JFrame("Chinese Chess");
		ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ui.setSize(700,500);
		ui.setResizable(false);
		ui.setLocationRelativeTo(null);

		board=new Board();

		JPanel input=new JPanel();
		JLabel jl1=new JLabel("Choose the piece to move");
		
		JLabel oxjl=new JLabel("X:");
		oxjt=new JTextField(10);
		JPanel opx=new JPanel();
		opx.add(oxjl);
		opx.add(oxjt);	
		
		JLabel oyjl=new JLabel("Y:");
		oyjt=new JTextField(10);
		JPanel opy=new JPanel();
		opy.add(oyjl);
		opy.add(oyjt);	
		
		JLabel jl2=new JLabel("Move to:");	
		JLabel xjl=new JLabel("X:");
		xjt=new JTextField(10);
		JPanel px=new JPanel();
		px.add(xjl);
		px.add(xjt);	
		
		JLabel yjl=new JLabel("Y:");
		yjt=new JTextField(10);
		JPanel py=new JPanel();
		py.add(yjl);
		py.add(yjt);	
		
		JButton move=new JButton("Move");
		move.addActionListener(new moveListener());
		
		info.setLineWrap(true);
		
		input.setLayout(new GridLayout(9,1,5,5));	
		input.add(jl1);
		input.add(opx);
		input.add(opy);
		input.add(jl2);
		input.add(px);
		input.add(py);
		input.add(move);
		input.add(info);
		ui.getContentPane().add(BorderLayout.CENTER,board);
		ui.getContentPane().add(BorderLayout.EAST,input);
		ui.setVisible(true);
		
		
	}
	
	public static String getN(int a){
		
		switch(Math.abs(a)){
		case 1:
		if(a>0){
		return "G";}
		else {return "G";}	
		case 2:
		return "M";
		case 3:
		return "E";		
		case 4:
		return "K";
		case 5:
		return "R";
		case 6:
		return "C";
		case 7:
		return "P";
		}
		return null;
	}
	
	
	public void copy(int[][] c1, int[][] c2){	
		for(int i=0; i<=10; i++){	
			for(int j=0; j<=11 ;j++){
				
				c1[i][j]=c2[i][j];
				
			}	
		}
	}
	
	public void print(int[][] c1){	
		for(int i=0; i<=10; i++){	
			for(int j=0; j<=11 ;j++){
				
				System.out.print(c1[i][j]);
				
				
			}	
		}
		System.out.print("\n");
	}
	
	public int[][] robotMove(){	
		ArrayList<int[][]> nextlist=new ArrayList<int[][]>();
		nextlist=generateNode(points,false);
		int[] value = new int[nextlist.size()];
		int i=0;
		for(int[][] ni: nextlist){	
			value[i]=alphabeta(-1000000,1000000,ni,3,true);
			//System.out.println(value[i]);
			//print(ni);
			i++;
		}
		int minindex=0;
		int min=value[0];
		for(int x=0;x<i;x++)
		{
			if(min>value[x]){
				min=value[x];
				minindex=x;
			}
		}
		return nextlist.get(minindex);
	}
	
 
	
	
	public int alphabeta(int avalue, int bvalue, int[][] abp, int depth, boolean maxplayer){
		if(depth==0 || checkfinish(abp)!=0){
			return Evalue.h(abp);
		}
		if(maxplayer){
			ArrayList<int[][]> max=new ArrayList<int[][]>();
			max=generateNode(abp, true);
			for(int[][] maxp:max){		
				avalue= Math.max(avalue, alphabeta(avalue,bvalue,maxp,depth-1, false));
				if (bvalue<=avalue){
					break;
				}
			}
			return avalue;
		}
		else{
			ArrayList<int[][]> min=new ArrayList<int[][]>();
			min=generateNode(abp, false);
			for(int[][] minp:min){
				bvalue= Math.min(bvalue, alphabeta(avalue,bvalue,minp,depth-1, true));
				if (bvalue<=avalue){
					break;
				}
			}
			return bvalue;
		}		
	}
	
	
	public ArrayList<int[][]> generateNode(int[][] ps, boolean red){
		
		ArrayList<int[][]> r=new ArrayList<int[][]>();
		int[][] p=new int[11][12];
		copy(p,ps);
		
		for(int i=1; i<=9 ;i++){
			for(int j=1; j<=10 ; j++){
				if(red){
				if(p[i][j]>0 ){	
					for(int h=1;h<=9;h++)
					{
						for(int v=1;v<=10;v++){
							if(checkMove(i,j,h,v,p)&& (p[h][v]==0 || p[i][j]*p[h][v]<0)){
									int[][] p1=new int[11][12];
									copy(p1,p);
									p1[h][v]=p1[i][j];
									p1[i][j]=0;
									r.add(p1);
							}
						}
					}				
				}
				}
				else{
					if(p[i][j]<0 ){	
						for(int h=1;h<=9;h++)
						{
							for(int v=1;v<=10;v++){
								if(checkMove(i,j,h,v,p)&& (p[h][v]==0 || p[i][j]*p[h][v]<0)){
										int[][] p1=new int[11][12];
										copy(p1,p);
										p1[h][v]=p1[i][j];
										p1[i][j]=0;
										r.add(p1);
								}
							}
						}				
					}				
				}
			}
		}
		
		return r;
	}
	
	
	
	//fucntion to check the move validity (just the simple move rule)
	
	public boolean checkMove(int cx,int cy,int tx,int ty, int[][] pp){
		switch( Math.abs(pp[cx][cy])){
		case 1: //king
			if((cx==tx&&ty==cy-1)||(cx==tx&&ty==cy+1)||(cx==tx+1&&cy==ty)||(cx==tx-1&&cy==ty)){
				if(tx<=6&&tx>=4&&((ty>=1&&ty<=3)||(ty>=8&&ty<=10))){return true;}
			}
			if(cx==tx&&Math.abs(ty-cy)>4){
				boolean r=false; int rl=0;
				boolean b=false; int bl=0;
				for(int go=1;go<=10;go++){
					 if(pp[cx][go]==1){r=true;rl=go;}
					 if(pp[cx][go]==-1){b=true;bl=go;}
				}
				if(r&b){
					boolean n=true;
					for(int start=rl+1;start<bl;start++){
						//System.out.println(rl+" "+bl+" "+start);
						if(pp[cx][start]!=0){n=false;}
					}
					if(n){return true;}
				}
			}
			break;
		case 2: //M
			if((cx-1==tx&&ty==cy-1)||(cx+1==tx&&ty==cy+1)||(cx+1==tx&&cy-1==ty)||(cx-1==tx&&cy+1==ty)){
				if(tx<=6&&tx>=4&&((ty>=1&&ty<=3)||(ty>=8&&ty<=10))){return true;}
			}
			break;
		case 3: // elephants
			
			if((tx==cx+2&&ty==cy+2&&pp[cx+1][cy+1]==0)||(tx==cx+2&&ty==cy-2&&pp[cx+1][cy-1]==0)||(tx==cx-2&&ty==cy+2&&pp[cx-1][cy+1]==0)||(tx==cx-2&&ty==cy-2&&pp[cx-1][cy-1]==0)){
				if(tx>=1&&tx<=9&&( (ty<=5&&ty>=1&&cy<=5) || (ty>=6&&ty<=10&&cy>=6) ))
				{return true;}
			}
			break;
		case 4: // knights
			if((tx==cx+1&&ty==cy+2&&pp[cx][cy+1]==0)||(tx==cx+2&&ty==cy+1&&pp[cx+1][cy]==0)||(tx==cx-1&&ty==cy+2&&pp[cx][cy+1]==0)||(tx==cx-2&&ty==cy+1&&pp[cx-1][cy]==0)||(tx==cx-1&&ty==cy-2&&pp[cx][cy-1]==0)||(tx==cx-2&&ty==cy-1&&pp[cx-1][cy]==0)||(tx==cx+1&&ty==cy-2&&pp[cx][cy-1]==0)||(tx==cx+2&&ty==cy-1&&pp[cx+1][cy]==0)){
				if(tx>=1&&tx<=9&&ty>=1&&ty<=10)
				{return true;}
			}
			break;
		case 5: //Rooks
			if(tx==cx&&ty!=cy){
				if(ty>cy)
					{for(int y=ty-cy-1;y>0;y--){if(pp[cx][cy+y]!=0){return false;}}}
				else
					{for(int y=cy-ty-1;y>0;y--){if(pp[cx][ty+y]!=0){return false;}}}
				return true;
			}		
			else if(tx!=cx&&ty==cy){		
				if(tx>cx)
					{for(int x=tx-cx-1;x>0;x--){if(pp[cx+x][cy]!=0){return false;}}}
				else
					{for(int x=cx-tx-1;x>0;x--){if(pp[tx+x][cy]!=0){return false;}}}
				return true;
			}
			else{
			return false;}
		case 6: //Cannons			
			if(pp[tx][ty]==0)
			{
				if(tx==cx&&ty!=cy){
					if(ty>cy)
						{for(int y=ty-cy-1;y>0;y--){if(pp[cx][cy+y]!=0){return false;}}}
					else
						{for(int y=cy-ty-1;y>0;y--){if(pp[cx][ty+y]!=0){return false;}}}
					return true;
				}
				
				else if(tx!=cx&&ty==cy){		
					if(tx>cx)
						{for(int x=tx-cx-1;x>0;x--){if(pp[cx+x][cy]!=0){return false;}}}
					else
						{for(int x=cx-tx-1;x>0;x--){if(pp[tx+x][cy]!=0){return false;}}}
					return true;
				}	
				else{
				return false;}
			}
			else if(pp[tx][ty]*pp[cx][cy]<0)
			{
				int c=0;
				if(tx==cx&&ty!=cy){
					if(ty>cy)
						{for(int y=ty-cy-1;y>0;y--){if(pp[cx][cy+y]!=0){c++;}}}
					else
						{for(int y=cy-ty-1;y>0;y--){if(pp[cx][ty+y]!=0){c++;}}}
				}
				
				if(tx!=cx&&ty==cy){		
					if(tx>cx)
						{for(int x=tx-cx-1;x>0;x--){if(pp[cx+x][cy]!=0){c++;}}}
					else
						{for(int x=cx-tx-1;x>0;x--){if(pp[tx+x][cy]!=0){c++;}}}
				}
				
				if(c==1){return true;}
			}
			else{return false;}
			break;
		
		case 7:  //Pawns
			if(tx<1||tx>9||ty<1||ty>10){return false;}
			if(pp[cx][cy]>0){		
				if(cy<=5){if(cx==tx&&ty==cy+1){return true;}}
				else{if((cx==tx&&ty==cy+1)||(cy==ty&&tx==cx-1)||(cy==ty&&tx==cx+1)){return true;}}
			}
			else{
				if(cy>5){if(cx==tx&&cy==ty+1){return true;}}
				else{if((cx==tx&&cy==ty+1)||(cy==ty&&cx==tx-1)||(cy==ty&&cx==tx+1)){return true;}}
			}
			break;
		}	
		return false;	
	}
	
	public int checkfinish(int[][] c){
		
		int win=0; //1 red win  -1 balck win
		boolean red=false;
		boolean black=false;
		for(int i=1; i<=9 ; i++){
			for(int j=1; j<=10; j++)
			{if(c[i][j]==1){red=true;}if(c[i][j]==-1){black=true;}}
		}
		if(red&&black){
			win=0;
		}
		else if(red){
			win=1;
		}
		else{
			win=-1;
		}
		return win;
	}
	
	class moveListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try{
			choosex=Integer.parseInt(oxjt.getText());
			choosey=Integer.parseInt(oyjt.getText());
			targetx=Integer.parseInt(xjt.getText());
			targety=Integer.parseInt(yjt.getText());
			}
			catch(Exception e){
				info.setText("Please input non-empty X,Y");
			}
			if(checkMove(choosex,choosey,targetx,targety,points)&&points[choosex][choosey]!=0&& (points[targetx][targety]==0 || points[targetx][targety]*points[choosex][choosey]<0)){
				//if(points[targetx][targety]!=0&&points[targetx][targety]*points[choosex][choosey]<0){points[targetx][targety].p.status=false;}
				points[targetx][targety]=points[choosex][choosey];
				points[choosex][choosey]=0;
				board.repaint();
				if(checkfinish(points)==0){
				points=robotMove();}
				board.repaint();
				int check=checkfinish(points);
				if(check==0){}
				else if(check==1){info.setText("You win!");}
				else{info.setText("Robot win!");}
			}
			else{info.setText("Illegal Move");}
			
		}
		
		
	}
	
	@SuppressWarnings("serial")
	//Graphical Part
	class Board extends JPanel{

		protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D)g;
		int width=this.getWidth()-50;
		int height=this.getHeight()-50;
		int hGap=height/9;
		int wGap=width/8;
		
		info.setText("You are red player and is to move");
		
		g2d.setColor(black);
		
		// vertical line
		for(int i=0;i<9;i++){
			g2d.setFont(new Font("Tahoma", Font.ITALIC, 10));
			g2d.drawString(""+(i+1),i*wGap+20,height+40);
			g2d.drawLine(i*wGap+20, 20, i*wGap+20, 4*hGap+20);  
			g2d.drawLine(i*wGap+20, 5*hGap+20, i*wGap+20,9*hGap+20);
		}
		
		// horizontal line
		for(int i=0;i<10;i++){
			g2d.drawString(""+(10-i),2,i*hGap+20);
			g2d.drawLine(20, i*hGap+20, 8*wGap+20, i*hGap+20);
		}
		
		g2d.setFont(new Font("Tahoma", Font.BOLD, 20));
		g2d.drawString("CHUHE             HANJIE", (width-180)/2, height/2+25);
		
		//special line
		g2d.drawLine(3*wGap+20, 20, 5*wGap+20, 2*hGap+20);
		g2d.drawLine(5*wGap+20, 20, 3*wGap+20, 2*hGap+20);
		g2d.drawLine(3*wGap+20, height+13, 5*wGap+20, height-2*hGap+13);
		g2d.drawLine(3*wGap+20, height-2*hGap+13, 5*wGap+20, height+13);
		
		for(int xl=0;xl<9;xl++){
			for(int yl=0;yl<10;yl++){
				if(points[xl+1][yl+1]!=0){
					g2d.setFont(new Font("Tahoma", Font.BOLD, 20));
					g2d.setColor(orange);
					g2d.fillOval(xl*wGap-15+20, (9-yl)*hGap-15+20, 30, 30);
					if(points[xl+1][yl+1]>0){
					g2d.setColor(red);}
					else{g2d.setColor(black);}
					g2d.drawString(getN(points[xl+1][yl+1]),xl*wGap-7+20, (9-yl)*hGap+7+20);
					}
			}
		}
		g2d.dispose();
		}
	}

}



