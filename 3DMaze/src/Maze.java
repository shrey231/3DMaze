//Shreyas Vaderiyattil
//Addons - Music, 2D maze hint, key to unlock finish, and ability to restart maze when finished
import java.util.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.awt.Polygon;
import java.awt.Image;
import java.io.*;
import java.io.File;
import javax.sound.sampled.*;



public class Maze extends JPanel implements KeyListener,MouseListener
{
	JFrame frame;
	//declare an array to store the maze
	String[][] MazeArr = new String[13][37];
	int x=1,y=1;
	int count = 0;
	int direction = 0;
	int moves = 0;
	boolean won = false;
	boolean key = false;
	boolean showmap = false;
	boolean restart = false;


	boolean l1  = true;
	boolean l2 = true;
	boolean l3 = true;
	boolean r1 = true;
	boolean r2 = true;
	boolean r3 = true;
	boolean space1 = true;
	boolean space2 = true;
	boolean space3 = true;

	int left1R = -1, left1C = 1, left2R = -1, left2C = 2, left3R = -1, left3C = 3;
	int mid1R = 0, mid1C = 1, mid2R = 0, mid2C = 2, mid3R = 0, mid3C = 3;
	int right1R = 1, right1C = 1, right2C = 2, right2R = 1, right3R = 1, right3C = 3;



	public Maze()
	{
		setBoard();



				try{
					InputStream is= new FileInputStream(new File("E:\\CMSC131\\The-Morning.wav"));
					 AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("E:\\CMSC131\\The-Morning.wav"));
			        Clip clip = AudioSystem.getClip();
			        clip.open(inputStream);
       		 		clip.loop(Clip.LOOP_CONTINUOUSLY);
				}
					catch (IOException ex){

			            ex.printStackTrace();
			        }

			        catch (UnsupportedAudioFileException ex1){

			            ex1.printStackTrace();
			        }

			        catch (LineUnavailableException ex2){

			            ex2.printStackTrace();
       				 }



		frame=new JFrame();
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,800);
		frame.setVisible(true);
		frame.addKeyListener(this);


		//this.addMouseListener(this);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.ORANGE);	//this will set the background color
		g.fillRect(0,0,1000,800);
		g.setColor(Color.BLUE);
		g.drawRect(0,0,1000,800);

		if(restart==true){
			won = false;
			key = false;
			showmap = false;
	 		restart = false;
	 		direction = 0;
	 		x =1;
	 		y=1;
		}


		//drawBoard here!

		int xval = 50;
		int yval=  50;
		for(int row = 0; row < 12; row++)
		{
			for(int col = 0; col < 36; col++)
			{
				if(MazeArr[row][col].equals("*"))
				{

					if(row == 0 || row == 11
						|| col == 0 || col == 35)
					{
						g.setColor(Color.BLUE);
						g.fillRect(col*10+50,row*10+50,10,10);
						g.fillRect(col*10+50, row*10+50, 10, 10);

					}


						g.setColor(Color.GREEN);
						g.drawRect(col*10+50, row*10+50,10,10);


				}
				if(row ==10 &&col==33){
					g.setColor(Color.YELLOW);
					g.fillRect(col*10+50,row*10+50,10,10);
					g.fillRect(col*10+50, row*10+50, 10, 10);
					g.setColor(Color.BLUE);
					}
			}
		}


		g.setColor(Color.YELLOW);
		g.drawOval(y*10+50,x*10+50,10,10);
		g.fillOval(y*10+50,x*10+50,10,10);


		//x & y would be used to located your
		//playable character
		//values would be set below




		//other commands that might come in handy
		//g.setFont("Times New Roman",Font.PLAIN,18);
		g.setColor(Color.ORANGE);
		Font font = new Font("Monospaced", Font.PLAIN, 30);
		 g.setFont(font);
		 g.drawString("Moves: "+moves,50,200);
		 if(x == 10&&y ==33 && key ==true){
			 g.drawString("Congrats you finished!",50,250);
			 won = true;
		 }
		check3D();
		turn();
		Polygon top = new Polygon(new int[]{0,999,999,0},new int[]{0,0,240,240},4);
		Polygon bottom = new Polygon(new int[] {0,999,999,0},new int[]{570,570,800,800},4);

		//Smallest
		Polygon center1 = new Polygon(new int[] {350,650,650,350},new int[]{240,240,570,570},4);
		Polygon center2 = new Polygon(new int[] {275,725,725,275},new int[]{185,185,615,615},4);
		Polygon center3 = new Polygon(new int[] {150,850,850,150},new int[]{100,100,700,700},4);
		//Biggest
		Polygon center4 = new Polygon(new int[] {0,999,999,0},new int[]{50,50,750,750},4);

		Polygon left1 = new Polygon(new int[] {0,150,150,0},new int[]{0,100,700,800},4);
		Polygon left2 = new Polygon(new int[] {150,275,275,150}, new int[] {100,185,615,700},4);
		Polygon left3 = new Polygon(new int[] {275,350,350,275}, new int[] {185,240,570,615},4);

		Polygon leftentrance1 = new Polygon(new int[] {0,150,150,0},new int[]{100,100,700,700},4);
		Polygon leftentrance2 = new Polygon(new int[] {150,275,275,150}, new int[] {185,185,615,615},4);
		Polygon leftentrance3 = new Polygon(new int[] {275,350,350,275}, new int[] {240,240,570,570},4);

		Polygon right1 = new Polygon(new int[] {1000,850,850,1000},new int[]{0,100,700,800},4);
		Polygon right2 = new Polygon(new int[] {850,725,725,850}, new int[] {100,185,615,700},4);
		Polygon right3 = new Polygon(new int[] {725,650,650,725}, new int[] {185,240,570,615},4);

		Polygon rightentrance1 = new Polygon(new int[] {1000,850,850,1000},new int[]{100,100,700,700},4);
		Polygon rightentrance2 = new Polygon(new int[] {850,725,725,850}, new int[] {185,185,615,615},4);
		Polygon rightentrance3 = new Polygon(new int[] {725,650,650,725}, new int[] {240,240,570,570},4);

		Polygon key1 = new Polygon(new int[] {450,500,550,500}, new int[] {430,410,430,450},4);
		Polygon key2 = new Polygon(new int[] {400,500,600,500}, new int[] {430,390,430,470},4);

		g.setColor(Color.CYAN);
		g.drawPolygon(top);
		g.fillPolygon(top);
		g.drawPolygon(bottom);
		g.fillPolygon(bottom);



		if(l1 == true){
			g.setColor(Color.ORANGE);
			g.fillPolygon(left1);
			g.setColor(Color.BLUE);
			g.drawPolygon(left1);

		}else if(l1 == false){
			g.setColor(Color.ORANGE);
			g.fillPolygon(leftentrance1);
			g.setColor(Color.BLUE);
			g.drawPolygon(leftentrance1);
		}

		if(l2 ==true){
			g.setColor(Color.ORANGE);
			g.fillPolygon(left2);
			g.setColor(Color.BLUE);
			g.drawPolygon(left2);
		}else if(l2 ==false){
			g.setColor(Color.ORANGE);
			g.fillPolygon(leftentrance2);
			g.setColor(Color.BLUE);
			g.drawPolygon(leftentrance2);
		}

		if(l3 ==true){
			g.setColor(Color.ORANGE);
			g.fillPolygon(left3);
			g.setColor(Color.BLUE);
			g.drawPolygon(left3);
		}else if(l3 ==false){
			g.setColor(Color.ORANGE);
			g.fillPolygon(leftentrance3);
			g.setColor(Color.BLUE);
			g.drawPolygon(leftentrance3);
		}


		if(r1 == true){
			g.setColor(Color.ORANGE);
			g.fillPolygon(right1);
			g.setColor(Color.BLUE);
			g.drawPolygon(right1);

		}else if(r1 == false){
			g.setColor(Color.ORANGE);
			g.fillPolygon(rightentrance1);
			g.setColor(Color.BLUE);
			g.drawPolygon(rightentrance1);
		}

		if(r2 ==true){
			g.setColor(Color.ORANGE);
			g.fillPolygon(right2);
			g.setColor(Color.BLUE);
			g.drawPolygon(right2);
		}else if(r2 ==false){
			g.setColor(Color.ORANGE);
			g.fillPolygon(rightentrance2);
			g.setColor(Color.BLUE);
			g.drawPolygon(rightentrance2);
		}

		if(r3 ==true){
			g.setColor(Color.ORANGE);
			g.fillPolygon(right3);
			g.setColor(Color.BLUE);
			g.drawPolygon(right3);
		}else if(r3 ==false){
			g.setColor(Color.ORANGE);
			g.fillPolygon(rightentrance3);
			g.setColor(Color.BLUE);
			g.drawPolygon(rightentrance3);
		}

		if(key == true || (key !=true && (x!=10 && y!=30) && (x!=10 && y!=31 &&(x!=10 && y!=32)&&(x!=10 && y!=33)))){
			if(!space1 == true){
			g.setColor(Color.ORANGE);
			g.fillPolygon(center4);
			g.setColor(Color.BLUE);
			g.drawPolygon(center4);

			}
			if(!space2 == true){

			g.setColor(Color.ORANGE);
			g.fillPolygon(center3);
			g.setColor(Color.BLUE);
			g.drawPolygon(center3);

			}
			if(!space3 == true){
			g.setColor(Color.ORANGE);
			g.fillPolygon(center2);
			g.setColor(Color.BLUE);
			g.drawPolygon(center2);
			}
			else if(space1 == true && space2 ==true && space3 ==true ){
			g.setColor(Color.ORANGE);
			g.fillPolygon(center1);
			g.setColor(Color.BLUE);
			g.drawPolygon(center1);
			}
		}
			else if(direction==0 && ((x==9 &&y==30)||(x==9 && y==31)||(x==9 && y==32)||(x==9&&y==33))) {

					if(!space1 == true){
						g.setColor(Color.BLACK);
						g.fillPolygon(center4);
						g.setColor(Color.BLUE);
						g.drawPolygon(center4);
						g.setColor(Color.WHITE);
						g.drawString("You cannot enter past this point yet...",120,400);
						}
						if(!space2 == true){

						g.setColor(Color.BLACK);
						g.fillPolygon(center3);
						g.setColor(Color.BLUE);
						g.drawPolygon(center3);

						}
						if(!space3 == true){
						g.setColor(Color.BLACK);
						g.fillPolygon(center2);
						g.setColor(Color.BLUE);
						g.drawPolygon(center2);
						}
						else if(space1 == true && space2 ==true && space3 ==true ){
						g.setColor(Color.BLACK);
						g.fillPolygon(center1);
						g.setColor(Color.BLUE);
						g.drawPolygon(center1);
					}

			}else{
				if(!space1 == true){
							g.setColor(Color.ORANGE);
							g.fillPolygon(center4);
							g.setColor(Color.BLUE);
							g.drawPolygon(center4);

							}
							if(!space2 == true){

							g.setColor(Color.ORANGE);
							g.fillPolygon(center3);
							g.setColor(Color.BLUE);
							g.drawPolygon(center3);
							}
							if(!space3 == true){
							g.setColor(Color.ORANGE);
							g.fillPolygon(center2);
							g.setColor(Color.BLUE);
							g.drawPolygon(center2);
							}
							else if(space1 == true && space2 ==true && space3 ==true ){
							g.setColor(Color.ORANGE);
							g.fillPolygon(center1);
							g.setColor(Color.BLUE);
							g.drawPolygon(center1);
				}
			}

			if(x==8&&y==9&& direction ==3&& key==false){
				g.setColor(Color.RED);
				g.fillPolygon(key1);
				g.setColor(Color.WHITE);
				g.fillOval(495,425,10,10);
			}
			if(x==7&&y==9&& direction ==3&&key ==false){
					g.setColor(Color.RED);
					g.fillPolygon(key2);
					g.setColor(Color.WHITE);
					g.fillOval(493,422,15,15);
					g.setFont(new Font("Serif", Font.ITALIC, 30));
					g.setColor(Color.BLACK);
					g.drawString("Press r to open entrance!",350,100);

			}
			if(x==2 && y==18 && showmap==false&& direction==0){
					g.setColor(Color.WHITE);
					g.setFont(new Font("Serif", Font.ITALIC, 40));
					g.drawString("Press s for a hint",350,400);
			}

			if(showmap==true){
				for(int row = 0; row < 12; row++)
						{
						for(int col = 0; col < 36; col++)
						{
							if(MazeArr[row][col].equals("*"))
							{

								if(row == 0 || row == 11
									|| col == 0 || col == 35)
								{
									g.setColor(Color.BLUE);
									g.fillRect(col*10+300,row*10+350,10,10);
									g.fillRect(col*10+300, row*10+350, 10, 10);

								}


									g.setColor(Color.BLACK);
									g.drawRect(col*10+300, row*10+350,10,10);


							}
							if(row ==10 &&col==33){
								g.setColor(Color.RED);
								g.fillRect(col*10+300,row*10+350,10,10);
								g.fillRect(col*10+300, row*10+350, 10, 10);
								g.setColor(Color.BLUE);
								}
							if(row ==1 && col==1){
								g.setColor(Color.RED);
								g.fillRect(col*10+300,row*10+350,10,10);
								g.fillRect(col*10+300, row*10+350, 10, 10);
								g.setColor(Color.BLUE);
							}
							if(row ==7 && col==9){
								g.setColor(Color.MAGENTA);
								g.fillRect(col*10+300,row*10+350,10,10);
								g.fillRect(col*10+300, row*10+350, 10, 10);
								g.setColor(Color.BLUE);
							}
						}

						}
						g.setColor(Color.WHITE);
						g.setFont(new Font("Serif", Font.ITALIC, 40));
						g.drawString("Here is a birds eye view of the maze!",175,600);
						g.drawString("The colors mark \"key\" locations",200,700);

			}

			if(x==1 && y==1&& direction==0){
				g.setColor(Color.WHITE);
				g.setFont(new Font("Serif", Font.ITALIC, 40));
				g.drawString("Throughout the maze there are hidden",200,320);
				g.drawString("hints and objects, good luck!",275,390);
			}

			if(direction==1 && key==true&& x==9&&y==33){
				g.setColor(Color.GREEN);
				g.fillPolygon(center3);
				g.setColor(Color.BLUE);
				g.drawPolygon(center3);

			}
				if(direction==1 && key==true&& x==10&&y==33){
					g.setColor(Color.GREEN);
					g.fillPolygon(center4);
					g.setColor(Color.BLUE);
					g.drawPolygon(center4);
					g.setFont(new Font("Serif", Font.ITALIC, 40));
					g.drawString("Congrats you finished!",270,350);
					g.drawString("Press p to restart!",310,500);
				}


			repaint();


				//you can also use Font.BOLD, Font.ITALIC, Font.BOLD|Font.Italic
		//g.drawOval(x,y,10,10);
		//g.fillRect(x,y,100,100);
		//g.fillOval(x,y,10,10);
	}
	public void setBoard()
	{
		//choose your maze design

		//pre-fill maze array here


		File name = new File("Maze.txt");
		int r=0;
		int row = 0;
		try
			{
				BufferedReader input = new BufferedReader(new FileReader(name));
				String text;
				while( (text=input.readLine())!= null)
				{

					//your code goes in here to chop up the maze design
					//fill maze array with actual maze stored in text file

					String[] c = text.split("");

					for(int col = 0; col < c.length; col++) {
						if(row < 13)
						MazeArr[row][col] = c[col];

					}
					row++;

				}
				for(int rw = 0; rw < 11; rw++) {
					for(int col = 0; col < 36; col++) {
						System.out.print(MazeArr[rw][col]);
					}
					System.out.println();
				}


			}
			catch (IOException io)
			{
				System.err.println("File error");
			}

			//setWalls();
	}

	public void setWalls()
	{
		//when you're ready for the 3D part
		int[] c1X={150,550,450,250};
		int[] c1Y={50,50,100,100};
		//Ceiling ceiling1=new Polygon(c1X,c1Y,4);  //needs to be set as a global!

	}
	public void check3D(){
		if(MazeArr[x+mid1R][y+mid1C].equals("*")){
			space1 = false;
			space2 = true;
			space3 = true;
			l1 = false;
			r1 = false;
		}
		else{
			space1 = true;
			if(MazeArr[x+left1R][y+left1C].equals("*")){
				l1 = true;
			}else{
				l1 = false;
			}
			if(MazeArr[x+right1R][y+right1C].equals("*")){
				r1 = true;
			}else{
				r1 = false;
			}

			if(MazeArr[x+mid2R][y+mid2C].equals("*")){
				space2 = false;
				space1 = true;
				space3 = true;
			}else{
				space2 = true;
				if(MazeArr[x+left2R][y+left2C].equals("*")){
					l2 = true;
				}else{
					l2 = false;
				}
				if(MazeArr[x+right2R][y+right2C].equals("*")){
					r2 = true;
				}else{
					r2 = false;
				}

					if(MazeArr[x+mid3R][y+mid3C].equals("*")){
						space3 = false;
						space2 = true;
						space1 = true;
					}else{
						space3 = true;
						if(MazeArr[x+left3R][y+left3C].equals("*")){
							l3 = true;
						}else{
							l3 = false;
						}
						if(MazeArr[x+right3R][y+right3C].equals("*")){
							r3 = true;
						}else{
							r3 = false;
						}
				}

			}


		}

	}

	public void turn(){
		//East
		if(direction ==0){
				left1R = -1;
				left1C = 1;
				left2R = -1;
				left2C = 2;
				left3R = -1;
				left3C = 3;
				mid1R = 0;
				mid1C = 1;
				mid2R = 0;
				mid2C = 2;
				mid3R = 0;
				mid3C = 3;
				right1R = 1;
				right1C = 1;
				right2R = 1;
				right2C = 2;
				right3R = 1;
				right3C = 3;
		 //South
		}else if(direction ==1){
				left1R = 1;
				left1C = 1;
				left2R = 2;
				left2C = 1;
				left3R = 3;
				left3C = 1;
				mid1R = 1;
				mid1C = 0;
				mid2R = 2;
				mid2C = 0;
				mid3R = 3;
				mid3C = 0;
				right1R = 1;
				right1C = -1;
				right2R = 2;
				right2C = -1;
				right3R = 3;
				right3C = -1;
		 //West
		}else if(direction ==2){
				left1R = 1;
				left1C = -1;
				left2R = 1;
				left2C = -2;
				left3R = 1;
				left3C = -3;
				mid1R = 0;
				mid1C = -1;
				mid2R = 0;
				mid2C = -2;
				mid3R = 0;
				mid3C = -3;
				right1R = -1;
				right1C = -1;
				right2R = -1;
				right2C = -2;
				right3R = -1;
				right3C = -3;
		 //North
		}else if(direction ==3){
				left1R = -1;
				left1C = -1;
				left2R = -2;
				left2C = -1;
				left3R = -3;
				left3C = -1;
				mid1R = -1;
				mid1C = 0;
				mid2R = -2;
				mid2C = 0;
				mid3R = -3;
				mid3C = 0;
				right1R = -1;
				right1C = 1;
				right2R = -2;
				right2C = 1;
				right3R = -3;
				right3C = 1;
		}
		repaint();
	}



	public void check(int orient,String [][] maze){
		int x = 1;
		int y = 1;
		if(direction ==0){
			if(maze[x-1][y].equals("*")){

			}
		}

	}


	public void keyPressed(KeyEvent e)
	{
		int xAdd = 0;
		int yAdd = 0;

		//direction - 0=East, 1=South, 2=West, 3=North
		//y*10+50,x*10+50
	if(won ==false){
		if(key==false){
			MazeArr[10][33] = "*";
		}else{
			MazeArr[10][33] = "-";
		}
		if((x==7 && y==9)&&e.getKeyCode()==82){
			key = true;
			repaint();
		}
		if((x==2 && y==18)&&e.getKeyCode()==83){
			showmap =true;
		}else{
			showmap = false;
		}
		if(e.getKeyCode()==38){

			if(direction == 0){
				xAdd = 0;
				yAdd = 1;
				if(!(MazeArr[x+xAdd][y+yAdd].equals("*")) && MazeArr[xAdd+x][y+yAdd].equals("-")){
					y++;
					repaint();
					moves++;

				}

			}
			if(direction ==1){
				xAdd = 1;
				yAdd = 0;
				if(!(MazeArr[x+xAdd][y+yAdd].equals("*")) && MazeArr[xAdd+x][y+yAdd].equals("-")){
					x++;
					repaint();
					moves++;

				}

			}
			if(direction ==2){
				xAdd = 0;
				yAdd = -1;
				if(!(MazeArr[x+xAdd][y+yAdd].equals("*")) && MazeArr[xAdd+x][y+yAdd].equals("-")){
					y--;
					repaint();
					moves++;

				}

			}
			if(direction ==3){
				xAdd = -1;
				yAdd = 0;
				if(!(MazeArr[x+xAdd][y+yAdd].equals("*")) && MazeArr[xAdd+x][y+yAdd].equals("-")){
					x--;
					repaint();
					moves++;

				}
			}
		}
		if(e.getKeyCode()==39){

			if(direction==0){
				direction = 1;
			}else if(direction ==1){
				direction = 2;
			}else if(direction ==2){
				direction = 3;
			}else if(direction ==3){
				direction = 0;
			}
		}
		if(e.getKeyCode()==37){

			if(direction==0){
				direction = 3;
			}else if(direction ==1){
				direction = 0;
			}else if(direction ==2){
				direction = 1;
			}else if(direction ==3){
				direction = 2;
			}
		}
		System.out.println(direction);
		//System.out.println(moves);
		System.out.println(x+" "+y);

		repaint();
		}
		if(won==true&&e.getKeyCode()==80){
			restart =true;
			repaint();
		}

	}
	public void keyReleased(KeyEvent e)
	{
	}
	public void keyTyped(KeyEvent e)
	{
	}
	public void mouseClicked(MouseEvent e)
	{
	}
	public void mousePressed(MouseEvent e)
	{
	}
	public void mouseReleased(MouseEvent e)
	{
	}
	public void mouseEntered(MouseEvent e)
	{
	}
	public void mouseExited(MouseEvent e)
	{
	}
	public static void main(String args[])
	{

		Maze app=new Maze();
	}
}
