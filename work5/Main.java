package project5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.datatransfer.FlavorTable;
import java.security.Timestamp;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.sound.sampled.Line;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.omg.PortableInterceptor.Interceptor;
import org.omg.PortableServer.ServantActivator;

class MyJFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public MyJFrame(int width,int height){
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
}

class MyDrawJPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private Map<String, Point> map = new HashMap<String, Point>();
	private Map<String, Point> everMap = new HashMap<String, Point>();
	private int radius;
	private Color color;
	private MyLine topLine, downLine, leftLine, rightLine;
	
	public MyDrawJPanel(int radius ,Color color, int width, int height){
		this.radius = radius;
		this.color = color;
		this.setPreferredSize(new Dimension(width, height));
		this.topLine = new MyLine(new Point(0, 0), new Point(width, 0));
		this.downLine = new MyLine(new Point(0, height - 1)
				, new Point(width, height - 1));
		this.leftLine = new MyLine(new Point(0, 0), new Point(0, height));
		this.rightLine = new MyLine(new Point(width - 1, 0), 
				new Point(width - 1, height - 1));
	}
	
	public void paintComponent(Graphics graphics){
		super.paintComponent(graphics);
		graphics.setColor(this.color);
		graphics.drawLine(this.topLine.getStartX(), this.topLine.getStartY(),
				this.topLine.getEndX(), this.topLine.getEndY());
		graphics.drawLine(this.downLine.getStartX(), this.downLine.getStartY(),
				this.downLine.getEndX(), this.downLine.getEndY());
		graphics.drawLine(this.leftLine.getStartX(), this.leftLine.getStartY(),
				this.leftLine.getEndX(), this.leftLine.getEndY());
		graphics.drawLine(this.rightLine.getStartX(), this.rightLine.getStartY(),
				this.rightLine.getEndX(), this.rightLine.getEndY());
		Iterator<Map.Entry<String, Point>> iterator = this.map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Point> entry = iterator.next();	
			graphics.fillOval(entry.getValue().x, entry.getValue().y, this.radius, this.radius);
		}
		iterator = this.everMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Point> entry = iterator.next();	
			graphics.fillOval(entry.getValue().x, entry.getValue().y, this.radius, this.radius);
		}
	}
	
	public void pushPoint(String threadName,Point point) {
		this.map.put(threadName, point);
	}
	public void pushEverPoint(String threadName, Point point) {
		this.everMap.put(threadName, point);
		this.map.remove(threadName);
	}
}

class MyButtonJpanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JButton yesButton ,quitButtion;
	
	public MyButtonJpanel(int width, int height){
		this.setSize(width, height);
		this.yesButton = new JButton("¿ªÊ¼");
		this.quitButtion = new JButton("½áÊø");
		this.yesButton.addActionListener(Event -> this.yesAction());
		this.quitButtion.addActionListener(Event -> this.quitButton());
		
		this.add(this.yesButton);
		this.add(this.quitButtion);
	}

	public void yesAction(){
		MyThread myThread = new MyThread();
		myThread.start();
	}
	
	public void quitButton(){
		System.exit(0);
	}
	
}

class MyLine{
	private Point start ,end;
	private static float eps = (float) 0.0000001;
	public MyLine(Point start, Point end){
		this.start = start;
		this.end = end;
	}
	public int sgn(float f) {
		if (Math.abs(f) < eps) {
			 return 0;
		} else if (f > 0){
			return 1;
		} else {
			return -1;
		}
	}
	public int getStartX(){
		return this.start.x;
	}
	public int getStartY(){
		return this.start.y;
	}
	public int getEndX(){
		return this.end.x;
	}
	public int getEndY(){
		return this.end.y;
	}
	public int pointMultpiy(Point point1, Point point2){
		return point1.x * point2.y - point1.y * point2.x;
	}
	public Point pointSubract(Point point1, Point point2) {
		return new Point(point1.x - point2.x, point1.y - point2.y);
	}
	public Point pointAdd(Point point1, Point point2) {
		return new Point(point1.x + point2.x, point1.y + point2.y);
	}
	public float threePointsArea(Point point1, Point point2, Point point3) {
		return Math.abs(this.pointMultpiy(this.pointSubract(point2, point1), 
				this.pointSubract(point3, point1))) / 2; 
	}
	public Point getIntersection(MyLine line){
		if (this.sgn(this.pointMultpiy(this.pointSubract(this.start, this.end),
				this.pointSubract(line.start, line.end))) == 0) {
			return new Point(-1, -1);
		}
		if (this.sgn(this.pointMultpiy(this.pointSubract(line.start, this.start), 
				this.pointSubract(line.start, line.end))) * 
				this.sgn(this.pointMultpiy(this.pointSubract(line.start, this.end), 
						this.pointSubract(line.start, line.end))) > 0)
			return new Point(-1, -1);
		if (this.sgn(this.pointMultpiy(this.pointSubract(this.start, line.start), 
				this.pointSubract(this.start, this.end))) * 
				this.sgn(this.pointMultpiy(this.pointSubract(this.start, line.end), 
						this.pointSubract(this.start, this.end))) > 0)
			return new Point(-1, -1);
		
		float sa = this.threePointsArea(this.start, line.start, this.end);
		float sb = this.threePointsArea(this.end, this.start, line.end);
		
		Point temp = this.pointSubract(line.end , line.start);
		temp = new Point((int) (temp.x * sa / (sa + sb)),(int) (temp.y * sa / (sa + sb)));
		return this.pointAdd(line.start, temp);
	}
}

class Game{
	private static int radius;
	private static int width, height;
	private static int maxSpeed;
	private static int maxNumberOfCollisions;
	private static MyDrawJPanel myDrawJPanel;
	private Point point;
	private Point direction;
	private Random random;
	private long time;
	private int numberOfCollisions;
	private MyLine topLine, downLine, leftLine, rightLine;
	private int speed;
	
	
	public static void getConnectionWithDrawJPanel(MyDrawJPanel myDrawJPanel){
		Game.myDrawJPanel = myDrawJPanel;
	}
	
	public static void initGame(Color color, int radius, int width, int height
			, int maxspeed, int maxNumberOfCollisions){
		Game.radius = radius;
		Game.width = width;
		Game.height = height;
		Game.maxSpeed = maxspeed;
		Game.maxNumberOfCollisions = maxNumberOfCollisions;
	}

	public Game(){
		this.random = new Random();
		this.point = new Point(Game.width / 2, Game.height - Game.radius);
		this.direction = new Point();
		double angle = this.random.nextDouble() * Math.PI/6 + Math.PI/6;
		this.direction.x = (int) (Math.cos(angle) * Game.maxSpeed);
		this.direction.y = - (int) (Math.sin(angle) * Game.maxSpeed);
		this.time = System.currentTimeMillis();
		this.numberOfCollisions = Game.maxNumberOfCollisions;
		this.topLine = new MyLine(new Point(0, 0), new Point(Game.width, 0));
		this.downLine = new MyLine(new Point(0, Game.height)
				, new Point(Game.width, Game.height));
		this.leftLine = new MyLine(new Point(0, 0), new Point(0, Game.height));
		this.rightLine = new MyLine(new Point(Game.width, 0), 
				new Point(Game.width, Game.height));
		this.speed = Game.maxSpeed;
	}
	
	public int getNumberOfCollisions() {
		return this.numberOfCollisions;
	}
	
	public void getNextPoint(float tempTime){
		
		MyLine myLine;
		float costTime;
		tempTime = tempTime / 1000;
		while (this.numberOfCollisions > 0 && tempTime > 0.0) {
			myLine = new MyLine(this.point, new Point(this.point.x + 
					(int) (tempTime * this.direction.x)
					,(int) (this.point.y + tempTime * this.direction.y)));
			Point tempPoint = new Point(-1, -1);
			if (tempPoint.x == -1 && this.point.y != 0) {
				tempPoint = myLine.getIntersection(this.topLine);
				if (tempPoint.x != -1) {
					this.direction.y = - this.direction.y;
				}
			}
			if (tempPoint.x == -1 && this.point.y != Game.height) {
				tempPoint = myLine.getIntersection(this.downLine);
				if (tempPoint.x != -1) {
					this.direction.y = - this.direction.y;
				}
			}
			if (tempPoint.x == -1 && this.point.x != 0) {
				tempPoint = myLine.getIntersection(this.leftLine);
				if (tempPoint.x != -1) {
					this.direction.x = - this.direction.x;
				}
			}
			if (tempPoint.x == -1 && this.point.x != Game.width) {
				tempPoint = myLine.getIntersection(this.rightLine);
				if (tempPoint.x != -1) {
					this.direction.x = - this.direction.x;
				}
			}
			if (tempPoint.x != -1) {
				costTime = Math.abs(tempPoint.x - this.point.x) / this.speed;
				this.point = tempPoint;
				tempTime = tempTime - costTime;
				this.numberOfCollisions --;
			} else {
				this.point.x = (int) (this.point.x + this.direction.x * tempTime);
				this.point.y = (int) (this.point.y + this.direction.y * tempTime);
				tempTime = (float) 0.0;
				if (this.point.x == 0 || this.point.x == Game.width) {
					this.direction.x = -this.direction.x;
					this.numberOfCollisions --;
				}
				if (this.point.y == 0 || this.point.y == Game.height) {
					this.direction.y = -this.direction.y;
					this.numberOfCollisions --;
				}
			}
		}
	}
	
	public void run(String threadName){
		float tempTime = System.currentTimeMillis() - this.time;
		if (tempTime < 1000/30){
			return ;
		}
		this.getNextPoint(tempTime);
		if (this.numberOfCollisions != 0) {
			Game.myDrawJPanel.pushPoint(threadName,this.point);
		} else {
			Game.myDrawJPanel.pushEverPoint(threadName,this.point);
		}
		
		this.time = System.currentTimeMillis();
	}
	
	
}

class MyThread extends Thread{

	public void run(){
		Game game = new Game();
		
		while (true) {
			game.run(this.getName());
			if (game.getNumberOfCollisions() == 0) {
				break;
			}
		}
	}
}

public class Main {
	public static void main(String[] args){
		Color color = Color.BLACK;
		int radius = 15, width = 300, gameHeight = 200,buttonHeight = 100,
				speed = 200, maxNumberOfCollisions = 10;
		Game.initGame(color, radius, width, gameHeight, speed, maxNumberOfCollisions);
		MyJFrame myJFrame = new MyJFrame(width, gameHeight + buttonHeight);
		MyDrawJPanel myDrawJPanel = new MyDrawJPanel(radius, color, width, gameHeight);
		MyButtonJpanel myButtonJpanel = new MyButtonJpanel(width, buttonHeight);
		Game.getConnectionWithDrawJPanel(myDrawJPanel);
		
		myJFrame.add(myDrawJPanel,BorderLayout.CENTER);
		myJFrame.add(myButtonJpanel,BorderLayout.SOUTH);
		
		myJFrame.pack();
		
		long time = System.currentTimeMillis();
		while (true) {
			if (System.currentTimeMillis() - time < 1000 / 30) { 
				continue;
			} else {
				time = System.currentTimeMillis();
				myDrawJPanel.repaint();
			}
			
		}
	}
}
