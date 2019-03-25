package project7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;



class MyJFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MyJFrame(int width, int height) {
		this.setSize(width, height);
		this.setLocation(500, 300);
		this.setResizable(false);
		this.setVisible(true);
	}
}

class MyDocumentJPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton jButton;
	private JTextField jTextField;
	private MyDrawJPanel myDrawJPanel;
	public MyDocumentJPanel(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.LIGHT_GRAY);
		
		this.jButton = new JButton("文件");
		this.jTextField = new JTextField(66);
		this.jButton.addActionListener(Event -> this.buttonAction());
		this.jTextField.setEditable(false);;
		this.jTextField.setBackground(Color.LIGHT_GRAY);
		this.jTextField.setBorder(null);
		
		this.add(this.jButton,BorderLayout.WEST);
		this.add(this.jTextField, BorderLayout.CENTER);
	}
	public void connectMyDrawJPanel(MyDrawJPanel myDrawJPanel) {
		this.myDrawJPanel = myDrawJPanel;
	}
	public void buttonAction() {
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.showDialog(new JLabel(), "保存");
		jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		File file = jFileChooser.getSelectedFile();
		System.out.println(file.getAbsolutePath());	
		try {
			ImageIO.write(this.myDrawJPanel.getImage(), "jpg", 
					new java.io.File(file.toString()+".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

class MyButtonJPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton lineButton, cycleButton,
				ovalButton, rectangleButton, colorButton,quitButton;
	private JRadioButton notFillButton, fillButton;
	private ButtonGroup buttonGroup;
	private MyDrawJPanel myDrawJPanel;
	private int[] array = new int[5];
	
	public MyButtonJPanel(int width, int height) {
		for (int i : array) {
			i = 0;
		}
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.LIGHT_GRAY);
		
		this.lineButton = new JButton("直线");
		this.cycleButton = new JButton("  圆  ");
		this.ovalButton = new JButton("椭圆");
		this.rectangleButton = new JButton("矩形");
		this.colorButton = new JButton("颜色");
		this.quitButton = new JButton("撤销");
		this.notFillButton = new JRadioButton("不填充");
		this.fillButton = new JRadioButton("  填充  ");
		this.buttonGroup = new ButtonGroup();
		
		this.buttonGroup.add(this.notFillButton);
		this.buttonGroup.add(this.fillButton);
		
		this.lineButton.addActionListener(Event -> this.lineButtonAction());
		this.cycleButton.addActionListener(Event -> this.cycleButtonAction());
		this.ovalButton.addActionListener(Event -> this.ovalButtonAction());
		this.rectangleButton.addActionListener(Event -> this.rectangleButtonAction());
		this.colorButton.addActionListener(Event -> this.colorButtonAction());
		this.notFillButton.addActionListener(Event -> this.notFillButtonAction());
		this.fillButton.addActionListener(Event -> this.fillButtonAction());
		this.quitButton.addActionListener(Event -> this.quitButtonAction());
		
		this.add(this.lineButton);
		this.add(this.cycleButton);
		this.add(this.ovalButton);
		this.add(this.rectangleButton);
		this.add(this.colorButton);
		this.add(this.notFillButton);
		this.add(this.fillButton);
		this.add(this.quitButton);
	}
	public void clear(){
		this.array[0] = this.array[1] = this.array[2] = this.array[3] = this.array[4] = 0;
	}
	public void newButtonColor(){
		if(this.array[0] == 1){
			this.lineButton.setBackground(Color.LIGHT_GRAY);
		}else{
			this.lineButton.setBackground(Color.WHITE);
		}
		if(this.array[1] == 1){
			this.cycleButton.setBackground(Color.LIGHT_GRAY);
		}else{
			this.cycleButton.setBackground(Color.WHITE);
		}
		if(this.array[2] == 1){
			this.ovalButton.setBackground(Color.LIGHT_GRAY);
		}else{
			this.ovalButton.setBackground(Color.WHITE);
		}
		if(this.array[3] == 1){
			this.rectangleButton.setBackground(Color.LIGHT_GRAY);
		}else{
			this.rectangleButton.setBackground(Color.WHITE);
		}
	}
	public void connectMyDrawJPanel(MyDrawJPanel myDrawJPanel){
		this.myDrawJPanel = myDrawJPanel;
	}
	public void lineButtonAction(){
		this.myDrawJPanel.setShape(1);
		this.clear();
		array[0] = 1;
		this.newButtonColor();
	}
	public void cycleButtonAction(){
		this.myDrawJPanel.setShape(2);
		this.clear();
		array[1] = 1;
		this.newButtonColor();
	}
	public void ovalButtonAction(){
		this.myDrawJPanel.setShape(3);
		this.clear();
		array[2] = 1;
		this.newButtonColor();
	}
	public void rectangleButtonAction(){
		this.myDrawJPanel.setShape(4);
		this.clear();
		array[3] = 1;
		this.newButtonColor();
	}
	public void colorButtonAction(){
		Color color = this.myDrawJPanel.setColor();
		if(color == null){
			return;
		}
		this.colorButton.setBackground(color);
	}
	public void notFillButtonAction(){
		this.myDrawJPanel.setIfFill(false);
	}
	public void fillButtonAction(){
		this.myDrawJPanel.setIfFill(true);
	}
	public void quitButtonAction(){
		this.myDrawJPanel.quitArray();
	}
}

class MyPoint{
	public int shape,a,b,c,d;
	public boolean ifFill;
	public Color color;
	public MyPoint(int shape,int a,int b,int c,int d,boolean ifFill,Color color){
		this.shape = shape;
		this.a = a;this.b = b;this.c = c;this.d = d;
		this.ifFill = ifFill;
		this.color = color;
	}
}

class MyDrawJPanel extends JPanel implements MouseListener,MouseMotionListener {
	private static final long serialVersionUID = 1L;
	private int shape = -1;
	private Color color = Color.BLACK;
	private boolean ifFill;
	private ArrayList<MyPoint> arrayList = new ArrayList<>();
	private int a,b,c,d;
	
	public MyDrawJPanel(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
	}
	public void draw(Graphics graphics){
		for (MyPoint myPoint : arrayList) {
			graphics.setColor(myPoint.color);
			if (myPoint.shape == 1){
				graphics.drawLine(myPoint.a, myPoint.b, myPoint.c, myPoint.d);
			}else if(myPoint.shape == 2){
				if(myPoint.ifFill){
					graphics.fillOval(myPoint.a, myPoint.b, myPoint.c, myPoint.c);
				}else{
					graphics.drawOval(myPoint.a, myPoint.b, myPoint.c, myPoint.c);
				}
			}else if(myPoint.shape == 3){
				if(myPoint.ifFill){
					graphics.fillOval(myPoint.a, myPoint.b, myPoint.c, myPoint.d);
				}else{
					graphics.drawOval(myPoint.a, myPoint.b, myPoint.c, myPoint.d);
				}
			}else if(myPoint.shape == 4){
				if(myPoint.ifFill){
					graphics.fillRect(myPoint.a, myPoint.b, myPoint.c, myPoint.d);
				}else{
					graphics.drawRect(myPoint.a, myPoint.b, myPoint.c, myPoint.d);
				}
			}
		}
	}
	public void paintComponent(Graphics graphics){
		super.paintComponent(graphics);
		this.draw(graphics);
	}
	public void quitArray(){
		if(this.arrayList.size() > 0){
			this.arrayList.remove(this.arrayList.size() - 1);
			this.repaint();
		}
	}
	public void setIfFill(boolean ifFill){
		this.ifFill = ifFill;
	}
	public void setShape(int shape){
		this.shape = shape;
	}
	public Color setColor(){
		JFrame jFrame = new JFrame();
		Color color = JColorChooser.showDialog(jFrame, "选择颜色", null);
		if (color == null) {
			return null;
		}
		this.color = color;
		return color;
	}
	public BufferedImage getImage() {
		BufferedImage bufferedImage = new BufferedImage(this.getWidth(), 
				this.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = (Graphics2D)bufferedImage.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
		this.draw(graphics);
		return bufferedImage;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(this.shape == -1){
			return;
		}
		this.a = arg0.getX() - 65;
		this.b = arg0.getY() - 65;
		this.c = this.a;
		this.d = this.b;
		arrayList.add(new MyPoint(this.shape, 
				this.a, this.b, this.c, this.d,this.ifFill,this.color));
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override	
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if(this.shape == -1){
			return ;
		}
		this.c = e.getX() - 65;
		this.d = e.getY() - 65;
		if(this.shape != 1){
			this.c = Math.abs(this.a - this.c);
			this.d = Math.abs(this.b - this.d);
		}
		MyPoint temp = this.arrayList.remove(this.arrayList.size() - 1);
		temp.c = this.c;
		temp.d = this.d;
		this.arrayList.add(temp);
		this.repaint();
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

public class Main {
	public static void main(String[] args) {
		int width = 800, height = 480;
		MyJFrame myJFrame = new MyJFrame(width, height);
		MyDocumentJPanel myDocumentJPanel = new MyDocumentJPanel(width, 40);
		MyButtonJPanel myButtonJPanel = new MyButtonJPanel(65, height - 40);
		MyDrawJPanel myDrawJPanel = new MyDrawJPanel(width - 65, height - 40);
		
		myDocumentJPanel.connectMyDrawJPanel(myDrawJPanel);
		myButtonJPanel.connectMyDrawJPanel(myDrawJPanel);
		myJFrame.addMouseListener(myDrawJPanel);
		myJFrame.addMouseMotionListener(myDrawJPanel);
		
		myJFrame.add(myDocumentJPanel,BorderLayout.NORTH);
		myJFrame.add(myButtonJPanel,BorderLayout.WEST);
		myJFrame.add(myDrawJPanel,BorderLayout.CENTER);
		
		myJFrame.pack();
	}
}
