package swingTest;

import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Frame extends JFrame {
	JFrame frame;
	JTable table;
	
	Object userRow[][] = {
			{"바지", "의류"}, 
			{"마우스", "가전"},
			{"노트북", "가전"},
			{"모자", "의류"},
			{"떡볶이", "식품"}};
	String userColumn[] = {"item", "category"};
	
	public Frame(){
		setTitle("Frame1");
		
		Container contentPane = getContentPane();
		table = new JTable(userRow, userColumn);
		
		table.setBounds(20, 30, 250, 200);
		contentPane.add(new JScrollPane(table));

	}
	
	public static void main(String[] args) throws IOException{
		Frame frame = new Frame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 500, 400);
		frame.setVisible(true);
	}

}
