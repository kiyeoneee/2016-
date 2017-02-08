package CategoryClassification;

import java.awt.EventQueue;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class ClassificationFrame {
	JFrame frame;
	private JTable table;
	private JList itemNameList, keyWordList;
	private JTextPane selectedItem;
	private JButton addKeywordButton, deleteButton, saveEdidtedInfoButton, editButton, cancelButton;
	private JSeparator partition;
	private DefaultTableModel model;

	private JList categoryListView1, categoryListView2, categoryListView3;

	private String[][][] categoryList, categoryName;
	private int[][][] categoryCode;
	private String[] itemList, category1Data, category2Data, category3Data;
	private Object nullObj[][] = {};

	int itemListSelectedIndex;

	public HashMap<String, HashMap<String, int[]>> Item = new HashMap();

	public ClassificationFrame() {
		try {
			Document documentCategory = getCategoryData();
			NodeList nodeCategory = documentCategory.getChildNodes();
			Document documentItem = getItemData();
			NodeList nodeItem = documentItem.getChildNodes();

			setItemArray(nodeItem.item(0).getChildNodes());
			setCategoryArray(nodeCategory.item(0).getChildNodes());

		} catch (Exception e) {
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(50, 50, 1150, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		itemList = new String[Item.size()];
		Item.keySet().toArray(itemList);
		itemNameList = new JList(itemList);
		frame.getContentPane().add(new JScrollPane(itemNameList)).setBounds(6, 6, 280, 666);

		keyWordList = new JList();
		keyWordList.setBounds(952, 6, 192, 666);
		frame.getContentPane().add(keyWordList);

		selectedItem = new JTextPane();
		frame.getContentPane().add(new JScrollPane(selectedItem)).setBounds(292, 6, 654, 55);

		model = new DefaultTableModel(nullObj, new Object[] { "핵심단어", "카테고리1", "카테고리2", "카테고리3", "코드" });
		table = new JTable(model);
		frame.getContentPane().add(new JScrollPane(table)).setBounds(292, 67, 593, 220);

		JButton btnNewButton = new JButton(">>");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(889, 145, 59, 60);
		frame.getContentPane().add(btnNewButton);

		editButton = new JButton("수정");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		editButton.setBounds(287, 290, 220, 30);
		frame.getContentPane().add(editButton);

		addKeywordButton = new JButton("추가");
		addKeywordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		addKeywordButton.setBounds(509, 290, 220, 30);
		frame.getContentPane().add(addKeywordButton);

		deleteButton = new JButton("삭제");
		deleteButton.setBounds(731, 290, 220, 30);
		frame.getContentPane().add(deleteButton);

		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(291, 344, 655, 40);
		frame.getContentPane().add(textPane_1);

		categoryListView1 = new JList();
		category1Data = getString4List1();
		categoryListView1.setListData(category1Data);
		frame.getContentPane().add(new JScrollPane(categoryListView1)).setBounds(292, 390, 214, 250);

		categoryListView2 = new JList();
		frame.getContentPane().add(new JScrollPane(categoryListView2)).setBounds(512, 390, 214, 250);

		categoryListView3 = new JList();
		frame.getContentPane().add(new JScrollPane(categoryListView3)).setBounds(732, 390, 214, 250);

		saveEdidtedInfoButton = new JButton("수정정보 저장");
		saveEdidtedInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		saveEdidtedInfoButton.setBounds(287, 643, 335, 30);
		frame.getContentPane().add(saveEdidtedInfoButton);

		partition = new JSeparator();
		partition.setBounds(290, 323, 658, 14);
		frame.getContentPane().add(partition);

		cancelButton = new JButton("취소");
		cancelButton.setBounds(616, 643, 335, 30);
		frame.getContentPane().add(cancelButton);

		// eventListenerSetting
		ListSelectionListener itemListSelectionListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent itemListSelectionEvent) {
				boolean adjust = itemListSelectionEvent.getValueIsAdjusting();
				if(!adjust) {
					JList list = (JList) itemListSelectionEvent.getSource();
					int selectedIndex = list.getSelectedIndex();
					if (selectedIndex >= 0) {
						String itemName = itemList[selectedIndex];
						HashMap getChild = Item.get(itemName);
						String itemKeyWord = getChild.keySet().toString();
						selectedItem.setText(itemName.concat("\n\n핵심단어 => ").concat(itemKeyWord));
					}
				}	
			}
		};
		itemNameList.addListSelectionListener(itemListSelectionListener);
	}

	public String[] getString4List1() {
		String[] makeListData = new String[categoryName.length];
		for (int i = 0; i < categoryName.length; i++)
			makeListData[i] = categoryName[i][0][0].toString();
		System.out.println(makeListData);
		return makeListData;
	}

	public String[] getString4List2(int x) {
		String[] makeListData = new String[categoryName.length];
		for (int i = 0; i < categoryName.length; i++)
			makeListData[i] = categoryName[i][0][0].toString();
		System.out.println(makeListData);
		return makeListData;
	}

	public String[] getString4List3(int x, int y) {
		String[] makeListData = new String[categoryName.length];
		for (int i = 0; i < categoryName.length; i++)
			makeListData[i] = categoryName[i][0][0].toString();
		System.out.println(makeListData);
		return makeListData;
	}

	public static Document getItemData() {
		try {
			File xmlFile = new File("result.xml");

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			factory.setIgnoringElementContentWhitespace(true);
			Document document = builder.parse(xmlFile);
			document.getDocumentElement().normalize();

			return document;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Document getCategoryData() {
		try {
			File xmlFile = new File("korea_category.xml");

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			factory.setIgnoringElementContentWhitespace(true);
			Document document = builder.parse(xmlFile);
			document.getDocumentElement().normalize();

			return document;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setItemArray(NodeList node) {
		if (node.getLength() == 1 && node.item(0).getNodeName() == "#text")
			return;

		for (int i = 0; i < node.getLength(); i++) {
			if (node.item(i) instanceof Element == false)
				continue;
			HashMap<String, int[]> valueData = new HashMap();
			NodeList keyWordNodes = node.item(i).getChildNodes();
			// System.out.println(keyWordNodes.getLength());
			for (int j = 0; j < keyWordNodes.getLength(); j++) {

				NodeList recommandCategoryCode = keyWordNodes.item(j).getChildNodes();
				int[] categoryCode = new int[recommandCategoryCode.getLength()];
				// System.out.println("\t" + recommandCategoryCode.getLength());
				for (int k = 0; k < recommandCategoryCode.getLength(); k++) {
					Element elemC = (Element) recommandCategoryCode.item(k);
					Node n = elemC.getFirstChild();
					categoryCode[k] = Integer.parseInt(n.getNodeValue());
				}
				Element elemN = (Element) keyWordNodes.item(j);
				if (recommandCategoryCode.getLength() != 0)
					valueData.put(elemN.getAttribute("name").trim(), categoryCode);
				else
					valueData.put(elemN.getAttribute("name").trim(), null);
			}

			Element elemH = (Element) node.item(i);
			Item.put(elemH.getAttribute("name").trim(), valueData);
		}
	}

	public void setCategoryArray(NodeList node) {
		categoryList = new String[node.getLength() / 2][][];
		categoryName = new String[node.getLength() / 2][][];
		categoryCode = new int[node.getLength() / 2][][];

		for (int i = 0; i < node.getLength(); i++) {
			if (node.item(i).getNodeName() == "#text")
				continue;
			NodeList depth1 = node.item(i).getChildNodes();

			int indexI = i / 2;
			categoryList[indexI] = new String[depth1.getLength() / 2 + 1][];
			categoryList[indexI][0] = new String[1];
			categoryName[indexI] = new String[depth1.getLength() / 2 + 1][];
			categoryName[indexI][0] = new String[1];
			categoryCode[indexI] = new int[depth1.getLength() / 2 + 1][];
			categoryCode[indexI][0] = new int[1];

			for (int j = 1; j < depth1.getLength(); j++) {
				if (depth1.item(j).getNodeName() == "#text")
					continue;
				int indexJ = j / 2;
				NodeList depth2 = depth1.item(j).getChildNodes();

				categoryList[indexI][indexJ + 1] = new String[depth2.getLength() / 2 + 1];
				categoryName[indexI][indexJ + 1] = new String[depth2.getLength() / 2 + 1];
				categoryCode[indexI][indexJ + 1] = new int[depth2.getLength() / 2 + 1];

				for (int k = 1; k < depth2.getLength(); k++) {
					if (depth2.item(k).getNodeName() == "#text")
						continue;
					int indexK = k / 2;
					// 가장 하단 카테고리
					if (depth2.item(k).getNodeType() == Node.ELEMENT_NODE) {
						Element elemH2 = (Element) depth2.item(k);
						String str = elemH2.getAttribute("name");
						categoryList[indexI][indexJ + 1][indexK + 1] = str;
						categoryName[indexI][indexJ + 1][indexK + 1] = str.substring(4);
						categoryCode[indexI][indexJ + 1][indexK + 1] = Integer.parseInt(str.substring(0, 3));
					}
				}

				// 중간 카테고리
				if (depth1.item(j).getNodeType() == Node.ELEMENT_NODE) {
					Element elemH1 = (Element) depth1.item(j);
					String str = elemH1.getAttribute("name");
					categoryList[indexI][indexJ + 1][0] = str;
					categoryName[indexI][indexJ + 1][0] = str.substring(4);
					categoryCode[indexI][indexJ + 1][0] = Integer.parseInt(str.substring(0, 3));
				}
			}
			// 상단 카테고리
			if (node.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element elemH = (Element) node.item(i);
				String str = elemH.getAttribute("name");
				categoryList[indexI][0][0] = str;
				categoryName[indexI][0][0] = str.substring(4);
				categoryCode[indexI][0][0] = Integer.parseInt(str.substring(0, 3));
			}
		}
	}
}
