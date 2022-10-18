package mailBoxProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JInternalFrame;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;

import javax.swing.JTable;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.GridLayout;
import javax.swing.SpringLayout;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.EmptyBorder;

public class TitleWindow {

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TitleWindow window = new TitleWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TitleWindow() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 153, 51));
		frame.getContentPane().setForeground(new Color(60, 179, 113));
		frame.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		frame.setBounds(100, 100, 500, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel title = new JLabel("歡迎進入台灣信箱查詢系統");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(0, 251, 484, 70);
		title.setFont(new Font("標楷體", Font.BOLD, 30));
		title.setForeground(new Color(255, 255, 255));
		
		JPanel grayPart = new JPanel();
		grayPart.setBackground(new Color(204, 204, 204));
		grayPart.setBounds(20, 63, 210, 62);
		grayPart.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		grayPart.setForeground(new Color(153, 102, 204));
		
		JPanel grayPart2 = new JPanel();
		grayPart2.setBackground(new Color(204, 204, 204));
		grayPart2.setBounds(254, 63, 210, 62);
		grayPart2.setForeground(new Color(153, 102, 204));
		grayPart2.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		
		JLabel blackPart2 = new JLabel("");
		blackPart2.setBounds(10, 10, 190, 35);
		blackPart2.setPreferredSize(new Dimension(210, 80));
		blackPart2.setOpaque(true);
		blackPart2.setForeground(Color.RED);
		blackPart2.setBackground(new Color(0, 0, 0));
		blackPart2.setBorder(new LineBorder(new Color(153, 153, 153), 5));
		
		JLabel blackPart = new JLabel("");
		blackPart.setBounds(10, 10, 190, 35);
		blackPart.setPreferredSize(new Dimension(210, 80));
		blackPart.setForeground(new Color(255, 0, 0));
		blackPart.setBackground(new Color(0, 0, 0));
		blackPart.setOpaque(true);
		blackPart.setBorder(new LineBorder(new Color(153, 153, 153), 5));
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(grayPart2);
		grayPart2.setLayout(null);
		grayPart2.add(blackPart2);
		frame.getContentPane().add(title);
		frame.getContentPane().add(grayPart);
		grayPart.setLayout(null);
		grayPart.add(blackPart);
		
		JLabel icon = new JLabel("");

		icon.setBounds(202, 147, 80, 80);
		icon.setIcon(new ImageIcon("C:\\reportingData\\icon.png"));
		frame.getContentPane().add(icon);
		
		JPanel enterPanel = new JPanel();
		enterPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		enterPanel.setBackground(new Color(0, 153, 51));
		enterPanel.setBounds(30, 362, 409, 199);
		frame.getContentPane().add(enterPanel);
		enterPanel.setLayout(null);
		
		JLabel iconEnter_1 = new JLabel();
		iconEnter_1.setHorizontalAlignment(SwingConstants.CENTER);
		iconEnter_1.setIcon(new ImageIcon("C:\\reportingData\\lock.jpg"));
		iconEnter_1.setBounds(192, 6, 37, 43);
		enterPanel.add(iconEnter_1);
		
		JLabel name = new JLabel("陳韋翰");
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setForeground(Color.WHITE);
		name.setFont(new Font("標楷體", Font.BOLD, 18));
		name.setBounds(33, 49, 348, 30);
		enterPanel.add(name);
		
		JLabel classAndNumber = new JLabel("EEIT49_14號");
		classAndNumber.setHorizontalAlignment(SwingConstants.CENTER);
		classAndNumber.setForeground(Color.WHITE);
		classAndNumber.setFont(new Font("標楷體", Font.BOLD, 18));
		classAndNumber.setBounds(33, 74, 348, 30);
		enterPanel.add(classAndNumber);
		
		JPanel enter = new JPanel();
		enter.setLayout(null);
		enter.setForeground(new Color(153, 102, 204));
		enter.setBorder(new LineBorder(new Color(153, 204, 0), 3, true));
		enter.setBackground(new Color(102, 153, 51));
		enter.setBounds(135, 104, 145, 62);
		enter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					frame.setVisible(false);
					MainWindow searchAndUpdate = new MainWindow();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		enterPanel.add(enter);
		
		JLabel lblNewLabel_2 = new JLabel("點我進入系統");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("標楷體", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(23, 24, 103, 18);
		enter.add(lblNewLabel_2);
		
		JPanel decorateBlock = new JPanel();
		decorateBlock.setBounds(30, 349, 409, 14);
		frame.getContentPane().add(decorateBlock);
		decorateBlock.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		decorateBlock.setBackground(new Color(0, 153, 51));
	}
}
