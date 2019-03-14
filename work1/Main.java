import java.awt.Frame;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

class TickTackToe{
	private char[] charArr = {'1','2','3','4','5','6','7','8','9'};
	private boolean result = true;//true means user win
	private ArrayList<Integer> operatList = new ArrayList<>();
	private Frame frame = new Frame();
	private char userChar = '*';
	private char aiChar = 'o';
	
	public String getString(){
		String string = "-----------------------------------\n"+"|      " + charArr[0]
				+ "      |      " + charArr[1] + "      |      " + charArr[2]
				+ "      |\n" + "-----------------------------------\n" + "|      "
				+ charArr[3]+ "      |      " + charArr[4] + "      |      " + charArr[5]
				+ "      |\n" + "-----------------------------------\n" + "|      " 
				+ charArr[6]+ "      |      " + charArr[7] + "      |      " + charArr[8]
				+ "      |\n" + "-----------------------------------\n";
		return string;
	}
	
	public void startGame(){
		this.runGame();
	}
	
	private void runGame(){
		while (true){
			String string = this.showGame();
			boolean flag = false;
			if(string == null){
				if (this.operatList.size() == 0){
					JOptionPane.showMessageDialog(this.frame, "游戏退出");
					break;
				}else{
					this.charArr[this.operatList.get(this.operatList.size() - 1)] 
							= (char)(this.operatList.
									get(this.operatList.size() - 1).intValue() + 48 + 1);
					this.operatList.remove(this.operatList.size() - 1);
					this.charArr[this.operatList.get(this.operatList.size() - 1)] 
							= (char)(this.operatList.
									get(this.operatList.size() - 1).intValue() + 48 + 1);
					this.operatList.remove(this.operatList.size() - 1);
					continue;
				}
				
			}
			else{
				flag = this.checkInputAndChange(string);
			}
			if (flag){
				boolean getWin = this.checkResult();
				if (getWin){
					this.result = true;
					this.showResult();
					break;
				}
				this.aiStep();
				getWin = this.checkResult();
				if (getWin){
					this.result = false;
					this.showResult();
					break;
				}
			}else{
				this.showError();
			}
		}
	}
	
	private String showGame(){
		String string = (String)JOptionPane.showInputDialog(
				this.frame,this.getString(),"输入",JOptionPane.PLAIN_MESSAGE);
		return string;	
	}
	
	//true代表输入合法，false代表输入非法
	private boolean checkInputAndChange(String string){
		boolean flag = false;
		if (string.length() > 1 || (string.length() == 1 &&
				(string.compareTo("1") < 0 || string.compareTo("9") > 0) )){
			flag =false;
		}else if(string.length() == 1){
			int number = Integer.parseInt(string) - 1;
			if (this.charArr[number] == this.userChar || this.charArr[number] == this.aiChar){
				flag = false;
			}else{
				flag = true;
				this.operatList.add(number);
				this.charArr[number] = this.userChar;
			}
		}
		return flag;
		
	}
	
	//ai随机选择一处地方放入棋子
	private void aiStep(){
		ArrayList<Integer> List = new ArrayList<>();
		for(int i = 0 ;i < 9;i ++){
			if (this.charArr[i] != this.userChar &&
					this.charArr[i] !=this.aiChar)
				List.add(i);
		}
		Random random = new Random();
		int id = random.nextInt(List.size());
		this.operatList.add(List.get(id));
		this.charArr[List.get(id)] = this.aiChar;
	}
	
	private void showError(){
		JOptionPane.showMessageDialog(this.frame, "请输入合法位置");
	}
	
	//检查这三个点是否相同
	private boolean checkThreePoints(int x,int y,int z) {
		boolean flag = false;
		if (this.charArr[x] == this.charArr[y] && this.charArr[x] == this.charArr[z])
			flag = true;
		return flag;
	}
	
	//检查一方是否胜利,true为存在一方胜利
	private boolean checkResult(){
		boolean flag = false;
		if (this.checkThreePoints(0, 1, 2) ||
				this.checkThreePoints(3, 4, 5) || 
				this.checkThreePoints(6, 7, 8) ||
				this.checkThreePoints(0, 3, 6) || 
				this.checkThreePoints(1, 4, 7) ||
				this.checkThreePoints(2, 5, 8) ||
				this.checkThreePoints(0, 4, 8) ||
				this.checkThreePoints(2, 4, 6))
			flag = true;
		return flag;
	}
	
	public void showResult(){
		String string = (this.result == true ? "恭喜你赢了" : "可惜你输了");
		JOptionPane.showMessageDialog(this.frame, this.getString() + string);
	}
}

public class Main {
	
	public static void main(String[] args){
		TickTackToe tickTackToe = new TickTackToe();
		tickTackToe.startGame();
	}
}
