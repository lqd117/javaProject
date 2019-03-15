package project4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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
class MyNorthJpanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JTextField sumText;
	public MyNorthJpanel(){
		this.sumText = new JTextField("你已经猜了0次");
		this.sumText.setEditable(false);
		this.sumText.setBorder(new EmptyBorder(0,0,0,0));
		this.add(this.sumText);
	}
	public void setSum(int sum){
		this.sumText.setText("你已经猜了" + sum + "次");
	}
	public void setBackgroundColor(Color color){
		this.sumText.setBackground(color);
		this.setBackground(color);
	}
}
class MyCenterJPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JTextField tipText,inputText,resultText;
	
	public MyCenterJPanel(){
		this.tipText = new JTextField("输入猜测的数");
		this.tipText.setEditable(false);
		this.tipText.setBorder(new EmptyBorder(0,0,0,0));
		
		this.inputText = new JTextField(10);
		
		this.resultText = new JTextField(6);
		this.resultText.setEditable(false);
		this.resultText.setBorder(new EmptyBorder(0,0,0,0));
		
		this.add(this.tipText);
		this.add(this.inputText);
		this.add(this.resultText);
	}
	public String getText(){
		String string = this.inputText.getText();
		this.inputText.setText("");
		return string;
	}
	public void setResultText(String string){
		this.resultText.setText(string);
	}
	public void setBackgroundColor(Color color){
		this.tipText.setBackground(color);
		this.resultText.setBackground(color);
		this.setBackground(color);
	}
}

class MySouthJPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private JButton yesButton,againButton,quitButton;
	private Game game;
	
	public MySouthJPanel(){
		this.yesButton = new JButton("确认");
		this.againButton = new JButton("重新开始");
		this.quitButton = new JButton("退出");
		
		this.yesButton.addActionListener(event -> this.yesAction());
		this.againButton.addActionListener(event -> this.againAction());
		this.quitButton.addActionListener(event -> this.quitAction());
		
		this.add(this.yesButton);
		this.add(this.againButton);
		this.add(quitButton);
	}
	
	public void setBackgroundColor(Color color){
		this.setBackground(color);
	}
	
	public void setYesButtonIfEnable(boolean flag){
		this.yesButton.setEnabled(flag);
	}
	
	public void connectWithGame(Game game){
		this.game = game;
	}
	
	public void yesAction(){
		this.game.yesLogic();
	}
	
	public void againAction(){
		this.game.againLogic();
	}
	
	public void quitAction(){
		this.game.quitLogic();
	}
	
}

class Game{
	private int result;
	private int sum;
	private Random random;
	private final int MAX = 1000;
	private MyJFrame myJFrame;
	private MyNorthJpanel myNorthJpanel;
	private MyCenterJPanel myCenterJPanel;
	private MySouthJPanel mySouthJPanel;

	public Game(MyJFrame myJFrame, MyNorthJpanel myNorthJpanel,
			MyCenterJPanel myCenterJPanel,MySouthJPanel mySouthJPanel){
		this.sum = 0;
		this.random = new Random();
		this.result = random.nextInt(this.MAX) + 1;
		this.myJFrame = myJFrame;
		this.myNorthJpanel = myNorthJpanel;
		this.myCenterJPanel = myCenterJPanel;
		this.mySouthJPanel = mySouthJPanel;
	}
	public boolean isNumeric(String string){
        for (int i = 0; i < string.length(); i++) {
        	if (!Character.isDigit(string.charAt(i)))
        		return false;
        }
        return true;
	}
	public void yesLogic(){
		String input = this.myCenterJPanel.getText();
		if (input.length() > 0 && this.isNumeric(input)){
			this.sum ++;
			this.myNorthJpanel.setSum(this.sum);
			int number = Integer.parseInt(input);
			if (number > this.result){
				this.myCenterJPanel.setResultText("太大了");
				this.myNorthJpanel.setBackgroundColor(Color.RED);
				this.myCenterJPanel.setBackgroundColor(Color.RED);
				this.mySouthJPanel.setBackgroundColor(Color.RED);
				
			}else if(number < this.result){
				this.myCenterJPanel.setResultText("太小了");
				this.myNorthJpanel.setBackgroundColor(Color.BLUE);
				this.myCenterJPanel.setBackgroundColor(Color.BLUE);
				this.mySouthJPanel.setBackgroundColor(Color.BLUE);
			}
			else{
				this.myCenterJPanel.setResultText("猜对了");
				this.mySouthJPanel.setYesButtonIfEnable(false);
				this.myNorthJpanel.setBackgroundColor(Color.WHITE);
				this.myCenterJPanel.setBackgroundColor(Color.WHITE);
				this.mySouthJPanel.setBackgroundColor(Color.WHITE);
			}
		}else{
			JOptionPane.showMessageDialog(this.myJFrame, "请输入合法数字");
		}
	}
	
	public void againLogic(){
		this.result = random.nextInt(this.MAX) + 1;
		this.sum = 0;
		this.myNorthJpanel.setSum(this.sum);
		this.myCenterJPanel.setResultText("");
		this.mySouthJPanel.setYesButtonIfEnable(true);
	}
	
	public void quitLogic(){
		System.exit(0);
	}
}
public class Main {
	public static void main(String[] args){
		MyJFrame myJFrame = new MyJFrame(500, 300);
		MyNorthJpanel myNorthJpanel = new MyNorthJpanel();
		MyCenterJPanel myCenterJPanel = new MyCenterJPanel();
		MySouthJPanel mySouthJPanel = new MySouthJPanel();
		Game game = new Game(myJFrame,myNorthJpanel, myCenterJPanel, mySouthJPanel);
		mySouthJPanel.connectWithGame(game);
		
		myJFrame.add(myNorthJpanel,BorderLayout.NORTH);
		myJFrame.add(myCenterJPanel,BorderLayout.CENTER);
		myJFrame.add(mySouthJPanel,BorderLayout.SOUTH);
		
		myJFrame.pack();
	}
}
