package mailBoxProject;

import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;

import org.xml.sax.SAXException;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class MainWindow {

	private JFrame frame;
	private String selectType = "id";
	private String keyword ;
	private static JTable table;
	JScrollPane searchScroll;
	JScrollPane addDataScroll;
	public Vector<String> header = new Vector<String>();
	private JTextField keywordEnter;
	private JButton insertButton;
	private JTable addDataTable;
	private Vector<Vector<String>> VectorBig = new Vector<Vector<String>>();
	private Vector<String> vectorSmall = new Vector<String>();
	MainTableModel model;
	private JTextPane userGuide;
	private String path;
	private JButton exportButton;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainWindow() throws SQLException {
		Dao dao = new Dao();
		header.add("id");
		header.add("類別編號");
		header.add("類別");
		header.add("信筒樣式代號");
		header.add("信筒樣式");
		header.add("地址");
		header.add("地址描述");
		header.add("服務單位");
		header.add("聯絡電話");
		header.add("備註");
		header.add("X座標");
		header.add("Y座標");
		initialize(dao);		
	}
	
	private void initialize(Dao dao) throws SQLException {
		frame = new JFrame();
		frame.setTitle("搜尋信箱");
		frame.setBounds(100, 100, 1000, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		//預設表格畫面
		searchScroll = new JScrollPane();
		table = new JTable();
		
		//使用引導與狀況說明欄
		userGuide = new JTextPane();
		userGuide.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		userGuide.setText("透過上方欄位輸入查詢資料，欄位留空展示全部資料，下方表格輸入完成後點擊左方進行新增");
		userGuide.setEditable(false);
		
		
		//可選擇以何種資料進行查詢的combobox		
		JComboBox<String> typeCombo = new JComboBox<String>();
		typeCombo.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		typeCombo.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		typeCombo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					switch ((String)e.getItem()) {
					case "ID":
						selectType = "id";
						break;
					case "類別編號":
						selectType = "classNumber";
						break;
					case "類別":
						selectType = "className";
						break;
					case "信筒樣式代號":
						selectType = "classOfMailbox";
						break;
					case "信筒樣式":
						selectType = "mailboxName";
						break;
					case "地址":
						selectType = "address";
						break;
					case "地址描述":
						selectType = "descripAddress";
						break;
					case "服務單位":
						selectType = "mechanism";
						break;
					case "聯絡電話":
						selectType = "phoneNumber";
						break;
					case "備註":
						selectType = "remark";
						break;
					case "X座標":
						selectType = "longitude";
						break;
					case "Y座標":
						selectType = "latitude";
						break;
					default:
						selectType = "?";
						break;
					}
					
				}
			}
		});
		typeCombo.setModel(new DefaultComboBoxModel(new String[] {"ID", "類別編號",
				"類別", "信筒樣式代號", "信筒樣式", "地址", "地址描述", "服務單位",
				"連絡電話", "備註", "X座標", "Y座標"}));
		
		//依輸入內容查詢資料
		keywordEnter = new JTextField();
		keywordEnter.setColumns(10);
		
		JButton searchButton = new JButton("查詢");
		searchButton.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		searchButton.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent clicked) {
					keyword = "'%" + keywordEnter.getText() + "%'";
					try {
						model = new MainTableModel(dao.searchByKey(selectType, keyword),header); 
						table = new JTable(model);
						searchScroll.setViewportView(table);
						table.setPreferredScrollableViewportSize(frame.getSize());
						userGuide.setText("可直接點擊表格內容進行修改，或選取欄位後點擊左方按鈕刪除整行檔案");
					} catch (SQLException e) {
						e.printStackTrace();
					}
					table.getColumnModel().getColumn(0).setPreferredWidth(50);
					table.getColumnModel().getColumn(1).setPreferredWidth(20);
					table.getColumnModel().getColumn(3).setPreferredWidth(20);
					table.getColumnModel().getColumn(4).setPreferredWidth(30);
					table.getColumnModel().getColumn(5).setPreferredWidth(200);
					table.getColumnModel().getColumn(8).setPreferredWidth(130);
					table.getColumnModel().getColumn(9).setPreferredWidth(110); 
			}
		});
		
		
		//點擊一行資料後進行刪除
		JButton deleteByRowButton = new JButton("整行刪除");
		deleteByRowButton.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		deleteByRowButton.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		deleteByRowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Object rowID = model.getValueAt(table.getSelectedRow(), 0);
					int deleteId = Integer.parseInt((String)rowID);
					dao.deleteById(deleteId);
					model = new MainTableModel(dao.searchByKey(selectType, keyword),header);
					table = new JTable(model);
					searchScroll.setViewportView(table);
					table.getColumnModel().getColumn(0).setPreferredWidth(50);
					table.getColumnModel().getColumn(1).setPreferredWidth(20);
					table.getColumnModel().getColumn(3).setPreferredWidth(20);
					table.getColumnModel().getColumn(4).setPreferredWidth(30);
					table.getColumnModel().getColumn(5).setPreferredWidth(200);
					table.getColumnModel().getColumn(8).setPreferredWidth(130);
					table.getColumnModel().getColumn(9).setPreferredWidth(110); 
					userGuide.setText("刪除ID: " + deleteId);					
				} catch (SQLException e1) {
					userGuide.setText("資料庫連接失敗!!");
					e1.printStackTrace();
				} catch(ArrayIndexOutOfBoundsException e1){
					userGuide.setText("選取資料錯誤");
					e1.printStackTrace();
				}
			}
		});
		
		//新增資料用的表格
		addDataScroll = new JScrollPane();
		addDataScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		addDataScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		addDataTable = new JTable();
		for(int i = 0 ; i <= 11 ; i++) {
			vectorSmall.add(null);
		}
		VectorBig.add(vectorSmall);

		addDataTable.setModel(new InsertTableModel(VectorBig, header));
		addDataScroll.setViewportView(addDataTable);
		
		//新增單筆資料
		insertButton = new JButton("新增資料");
		insertButton.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		insertButton.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dao.insertDataBy1(vectorSmall);
					VectorBig.clear();
					for(int i = 1 ; i <= 11 ; i++) {
						vectorSmall.set(i, null);
					}
					VectorBig.add(vectorSmall);
					
					addDataTable.setModel(new InsertTableModel(VectorBig,header));;
					addDataScroll.setViewportView(addDataTable);
					addDataTable.setPreferredScrollableViewportSize(frame.getSize());
					
					model = new MainTableModel(dao.searchByKey(selectType, keyword),header);
					table = new JTable(model);
					searchScroll.setViewportView(table);
					table.getColumnModel().getColumn(0).setPreferredWidth(50);
					table.getColumnModel().getColumn(1).setPreferredWidth(20);
					table.getColumnModel().getColumn(3).setPreferredWidth(20);
					table.getColumnModel().getColumn(4).setPreferredWidth(30);
					table.getColumnModel().getColumn(5).setPreferredWidth(200);
					table.getColumnModel().getColumn(8).setPreferredWidth(130);
					table.getColumnModel().getColumn(9).setPreferredWidth(110); 
					userGuide.setText("新增資料成功，新增ID:" + vectorSmall.get(0));
					vectorSmall.set(0, null);
				} catch (SQLException e1) {
					userGuide.setText("失敗，輸入了錯誤的ID");
					e1.printStackTrace();
				} 
				catch (IOException e1) {
					userGuide.setText("發生未知錯誤");
					e1.printStackTrace();
				}
			}
		});
		
		
		//透過選擇檔案匯入資料
		JButton importButton = new JButton("上傳檔案");
		importButton.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		importButton.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		importButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent clicked) {
				int result = 0;
				JFileChooser fileChooser = new JFileChooser();
				FileSystemView fsv = FileSystemView.getFileSystemView();
				System.out.println(fsv.getHomeDirectory());
				fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
				fileChooser.setDialogTitle("選擇檔案");
				fileChooser.setApproveButtonText("確定");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				result = fileChooser.showOpenDialog(null);
				if(JFileChooser.APPROVE_OPTION == result) {
					path = fileChooser.getSelectedFile().getPath();
					System.out.println(path);
					userGuide.setText(path);
					int index = path.lastIndexOf(".");
					String filter = path.substring(index+1);
					switch(filter) {
						case "csv":
						try {
							int rows = dao.insertDataByCSV(new File(path));
							String text = "完成，總共新增 " + rows + " 筆資料，請重新點擊查詢";
							userGuide.setText(text);
						} catch (SQLException | IOException e) {
							e.printStackTrace();
						}
							break;
						case "json":
						try {
							int rows = dao.insertDataByJson(new File(path));
							String text = "完成，總共新增 " + rows + " 筆資料，請重新點擊查詢";
							userGuide.setText(text);
						} catch (SQLException | IOException e) {
							e.printStackTrace();
						}
							break;
						case "xml":
						try {
							int rows = dao.insertDataByXML(new File(path));
							String text = "完成，總共新增 " + rows + " 筆資料，請重新點擊查詢";
							userGuide.setText(text);
							
						} catch (SAXException | IOException | SQLException e) {
							e.printStackTrace();
						}
							break;
						default:
							userGuide.setText("檔案類型錯誤!!");
							break;
					}
				}

			}
		});
		


		//選擇匯出模式並執行匯出的combobox
		JComboBox<String> exportCombo = new JComboBox<String>();
		exportCombo.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		exportCombo.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		exportCombo.setModel(new DefaultComboBoxModel(new String[] {"CSV", "JSON" }));
		exportCombo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					switch ((String)e.getItem()) {
					case "CSV":
						userGuide.setText("將以CSV進行匯出");;
						break;
					case "JSON":
						userGuide.setText("將以JSON進行匯出");;
						break;
					default:
						userGuide.setText("選項錯誤");;
						break;
					}
					
				}
			}
		});


		
		//以選擇匯出模式執行匯出
		exportButton = new JButton("結果匯出");
		exportButton.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		exportButton.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = 0;
				JFileChooser fileChooser = new JFileChooser();
				FileSystemView fsv = FileSystemView.getFileSystemView();
				fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
				fileChooser.setDialogTitle("選擇匯出位置");
				fileChooser.setApproveButtonText("確定");
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				result = fileChooser.showOpenDialog(null);
				if(JFileChooser.APPROVE_OPTION == result) {
					path = fileChooser.getSelectedFile().getPath();
			        path = path + "\\export"  + new Date(System.currentTimeMillis());
			        userGuide.setText("匯出中...");
			        System.out.println(exportCombo.getSelectedItem());
			        try {
				        switch((String)exportCombo.getSelectedItem()) {
							case "CSV":
								path += ".csv";
								dao.exportDataCSV(path,selectType,keyword);
								userGuide.setText("匯出完成，檔案名稱為: " + path);
								break;
							case "JSON":
								path += ".json";
								dao.exportDataJson(path, selectType, keyword);
								userGuide.setText("匯出完成，檔案名稱為: " + path);
								break;
							default:
								userGuide.setText("檔案類型錯誤!!");
								break;
				        }
			        }catch (SQLException e1) {
			        	e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		

		
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(typeCombo, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(keywordEnter, GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(searchButton, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
						.addComponent(addDataScroll, GroupLayout.DEFAULT_SIZE, 964, Short.MAX_VALUE)
						.addComponent(searchScroll, GroupLayout.DEFAULT_SIZE, 964, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(insertButton, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(deleteByRowButton, GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(userGuide, GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(importButton, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(exportCombo, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(exportButton, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(11)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(keywordEnter, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(typeCombo, GroupLayout.PREFERRED_SIZE, 18, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(searchButton, 0, 0, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(searchScroll, GroupLayout.PREFERRED_SIZE, 327, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(2)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(importButton, GroupLayout.PREFERRED_SIZE, 23, Short.MAX_VALUE)
										.addComponent(exportCombo, GroupLayout.PREFERRED_SIZE, 22, Short.MAX_VALUE)
										.addComponent(exportButton, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)))
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(insertButton, GroupLayout.PREFERRED_SIZE, 23, Short.MAX_VALUE)
									.addComponent(deleteByRowButton, GroupLayout.PREFERRED_SIZE, 23, Short.MAX_VALUE)))
							.addGap(10))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(userGuide, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addComponent(addDataScroll, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addGap(14))
		);

		frame.getContentPane().setLayout(groupLayout);
		
		
	}
}
