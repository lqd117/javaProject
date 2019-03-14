package project3;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

abstract class MyShape{
	private int a,b,c,d;
	
	public MyShape(){
		this(0, 0, 0, 0);
	}
	public MyShape(int a,int b,int c,int d){
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	abstract void draw(Graphics g);
	public void setA(int a){
		this.a = a;
	}
	public void setB(int b){
		this.b = b;
	}
	public void setC(int c){
		this.c = c;
	}
	public void setD(int d){
		this.d = d;
	}
	public int getA(){
		return this.a;
	}
	public int getB(){
		return this.b;
	}
	public int getC(){
		return this.c;
	}
	public int getD(){
		return this.d;
	}
}
class MyLine extends MyShape{
	public MyLine(int a,int b,int c,int d){
		super(a,b,c,d);
	}
	public void draw(Graphics g){
		g.drawLine(getA(), getB(), getC(), getD());
	}
}
class MyRectangle extends MyShape{
	public MyRectangle(int a,int b,int c,int d){
		super(a,b,c,d);
	}
	public void draw(Graphics g){
		g.drawRect(getA(), getB(), getC(), getD());
	}
}
class MyOval extends MyShape{
	public MyOval(int a,int b,int c,int d){
		super(a,b,c,d);
	}
	public void draw(Graphics g){
		g.drawOval(getA(), getB(), getC(), getD());
	}
}
class MyJPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private ArrayList<MyShape> arrayList = new ArrayList<>();
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for (MyShape myShape : arrayList) {
			myShape.draw(g);
		}
	}
	public void addMyShape(MyShape myShape){
		this.arrayList.add(myShape);
	}
	
}
class MyJFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	public MyJFrame(){
		this.setSize(1000, 1000);
		this.setVisible(true);
	}
}
public class Main{
	public static int MAX = 500;
	public static void main(String[] args){
		MyJFrame myJFrame = new MyJFrame();
		MyJPanel myJPanel = new MyJPanel();
		Random random = new Random();
		for (int i = 1;i <= 7;i++){
			myJPanel.addMyShape(new MyLine(random.nextInt(MAX), random.nextInt(MAX), 
					random.nextInt(MAX), random.nextInt(MAX)) {});
			myJPanel.addMyShape(new MyRectangle(random.nextInt(MAX), random.nextInt(MAX), 
					random.nextInt(MAX), random.nextInt(MAX)) {});
			if (i == 7){
				break;
			}
			myJPanel.addMyShape(new MyOval(random.nextInt(MAX), random.nextInt(MAX), 
					random.nextInt(MAX), random.nextInt(MAX)) {});
		}
		
		myJFrame.add(myJPanel);
	}
}