package test;


import java.awt.EventQueue;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class main {

	private JFrame frame;
	private JTable table;
	private JScrollPane jScrollPane;
	private DefaultTableModel tableModel;
	
	private Object userRow[][] = {
			{"바지", "의류"}, 
			{"마우스", "가전"},
			{"노트북", "가전"},
			{"모자", "의류"},
			{"떡볶이", "식품"}};
	private String userColumn[] = {"item", "category"};
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main window = new main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		tableModel = new DefaultTableModel(userRow, userColumn);
		tableModel.setColumnIdentifiers(userColumn);
		jScrollPane = new JScrollPane(table);
		table = new JTable(tableModel);
		table.add(jScrollPane);
		
		JButton btnEXIT = new JButton("Exit");
		btnEXIT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnEXIT.setBounds(333, 243, 111, 29);
		frame.getContentPane().add(btnEXIT);
		
		//setBounds(크기 가로, 크기 세로, 위치 가로, 위치 세로)
		//배치관리자 없이 컴포넌트를 삽입할 때 컴포넌트의 크기와 위치 설정하는 함(setSize, setLocation)
		table.setBounds(20, 30, 250, 200);
		frame.getContentPane().add(table);
		
	}
}
