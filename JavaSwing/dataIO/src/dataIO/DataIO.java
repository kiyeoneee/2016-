package dataIO;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;


public class DataIO  extends JFrame implements ActionListener, TableModelListener{
	private JFrame jFrame;
	private JTable jTable;
	private TextField txt;
	private JButton btn1, btn2;
	private DefaultTableModel jModel;
	
	private Vector<String> category = new Vector<String>();
	private Vector<Vector<String>> itemData = new Vector<Vector<String>>();
	private Object[][] itemRow;
	private String[] itemCategory;
	
	private boolean tableEvent = true;
	private String filename = "data.txt";
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataIO d = new DataIO();
					d.jFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	public DataIO(){
		// 프로그램이 시작될 때 기존의 데이터를 먼저 받고 데이터 입출력 및 수정을 시작함 
		getData();
		
		setTitle("Java Swing Aryuwoyo");
		
		// jTable에 넣어주기 
		itemRow = itemData.stream().map(List::toArray).toArray(Object[][]::new);
		itemCategory = category.toArray(new String[category.size()]);
		
		txt = new TextField("데이터 사이에 띄어쓰기를 해주세요.", 38);
		Panel jPanel = new Panel(new FlowLayout());
		jPanel.add(txt);
		btn1 = new JButton("추가");
		btn1.addActionListener(this);
		btn2 = new JButton("삭제");
		btn2.addActionListener(this);
		jPanel.add(btn1);
		jPanel.add(btn2);
		
		jModel = new DefaultTableModel(itemRow, itemCategory);
		jModel.addTableModelListener(this);
		
		jTable = new JTable(jModel);
		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		jFrame = new JFrame();
		jFrame.getContentPane().add(new JScrollPane(jTable));
		jFrame.getContentPane().add(jPanel, BorderLayout.PAGE_END);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setBounds(100, 100, 500, 400);
	}
	
	public void getData(){
		try {
			BufferedReader file = new BufferedReader(new FileReader(filename));
			String data = file.readLine();
	
			if (data != null) {
				String dataSplit[] = data.split("\t");
				
				for (String cat: dataSplit) category.addElement(cat);
				data = file.readLine();
				
	 			while(data != null){
	 				// Data가 tab에 의해 구분되어 있으므로 tab문자열을 기준으로 나누어줌 
		 			// 나뉘어진 단어들을 String 배열에 넣어주어 하나의 Row를 하나의 string 배열로 봐줌
	 				Vector<String> str = new Vector<String>();
					String getData[] = data.split("\t");  
					for(String sp: getData) str.add(sp);
					itemData.addElement(str);
					data = file.readLine();
				}
	 			
	 			file.close();
	 		}
		} catch (IOException e) {
			System.out.println("File Not Found\n");
			e.printStackTrace();
		}
	}

	//String 배열을 toString함수를 통해서 변경하면 
	//[index1, index2, index3, ....]
	//위의 형태로 나오므로 기존 input값과 같이 tab으로 구분되는 문자열로 맞춰주기 위한 함수 
	public String changeString(String stringData){
		String s = stringData;
		
		s = s.replace("[", "");
        s = s.replace("]", "");
        s = s.replaceAll(" ", "");
        s = s.replaceAll(",", "\t");
        
        return s;
	}
	
	public void updateData(){
		try {
			PrintWriter pw = new PrintWriter("data.txt");
			pw.println(changeString(Arrays.asList(category).toString()));
			
	        for(int i = 0; i < itemData.size(); i++) 
	            pw.println(changeString(itemData.elementAt(i).toString()));
	        
            pw.close();
	 		
		} catch (IOException e) {
			System.out.println("Fail to write data on file.\n");
			e.printStackTrace();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();

		if((JButton)obj == btn1){
			String s = txt.getText().toString();
			// textFile에 들어오는 값들을 띄어쓰기에 따라서 각 index에 넣기위해 
			String getData[] = s.split(" ");
			Vector<String> st = new Vector<String>();
			for(String sp: getData) st.add(sp);
			itemData.addElement(st);
			
			//printData();
			tableEvent = false;
			jModel.addRow(getData);
			updateData();
		}
		
		if((JButton)obj == btn2){
			if(jTable.getSelectedRowCount() != 0){
				int n[] = jTable.getSelectedRows();
				for(int i = n.length; i > 0; i--){
					itemData.remove(n[i - 1]);
					
					tableEvent = false;
					jModel.removeRow(n[i - 1]);
					updateData();
				}
				//printData();
			}
		}
		// data가 변경되는 이벤트 발생시 data파일에 변경사항을 업데이트
		
	}

	// tableEvent 변수 : 데이터가 추가/삭제될 때 table에도 데이터가 추가/삭제 되므로 tableChange event가 발생
	// 이때 tableChanged함수는 cell의 데이터를 수정하기 위한 함수이므로 함수가 실행되면 안됨
	// 함수의 실행을 방지하기 위해서 button actionEvent로 인한 이벤트 발생인지를 확인하기 위한 함수
	// worning 방지
	@Override
	public void tableChanged(TableModelEvent e) {
		if(tableEvent == false) {
			tableEvent = true;
			return;
		}
		
		DefaultTableModel model = (DefaultTableModel)e.getSource();
		int row = e.getFirstRow();
		int column = e.getColumn();
	
		String cellValue = String.valueOf(jTable.getValueAt(row, column));
		String itemS = itemData.elementAt(row).elementAt(column);
			
		if(cellValue.compareTo(itemS) == 0) return;
		itemData.elementAt(row).set(column, cellValue);
		updateData();
	
		JOptionPane.showMessageDialog(this,
			"Value at \'" + itemS + "\' changed to " + "\'" + cellValue + "\'");
		
	}
	
	/*
	// 디버깅 용 print함수 
	public void printData(){
		for(int i = 0; i < itemData.size(); i++) System.out.println(itemData.elementAt(i));
		System.out.println("\n");
	}
	*/
}
