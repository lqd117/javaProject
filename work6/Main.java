package project6;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.xml.soap.Text;

class HandleEnglishText{
	private Map<String, Integer> map = new HashMap<String, Integer>();
	private int charNumber,wordNumber;
	private String string;
	public int getCharNumber() {
		return this.charNumber;
	}
	public int getWordNumber() {
		return this.wordNumber;
	}
	public HandleEnglishText(InputStream inputStream) {
		try {
			byte[] b = new byte[inputStream.available()];
			int temp;
			int id = 0;
			while ((temp = inputStream.read()) != -1) {
				b[id ++] = (byte) temp;
			}
			inputStream.close();
			this.string = new String(b);
			this.charNumber = this.string.length();
			String tempString = "";
			for (int i = 0; i < this.string.length(); i++) {
				if (('a' <= this.string.charAt(i) && this.string.charAt(i) <= 'z') ||
				('A' <= this.string.charAt(i) && this.string.charAt(i) <= 'Z')) {
					tempString += this.string.charAt(i);
				} else {
					if (tempString != "") {
						if (this.map.containsKey(tempString)) {
							this.map.put(tempString, this.map.get(tempString) + 1);
						} else {
							this.map.put(tempString, 1);
							this.wordNumber ++;
						}
					}
				}	
			}
			if (tempString != "") {
				if (this.map.containsKey(tempString)) {
					this.map.put(tempString, this.map.get(tempString) + 1);
				} else {
					this.map.put(tempString, 1);
					this.wordNumber ++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String string = scanner.nextLine();
		
		try {
			HandleEnglishText handleEnglishText = 
					new HandleEnglishText(new FileInputStream(string));
			System.out.println("charNumber " + handleEnglishText.getCharNumber());
			System.out.println("wordNumber " + handleEnglishText.getWordNumber());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
				
	}
}
