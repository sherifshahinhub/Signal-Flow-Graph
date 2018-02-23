package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import modle.DrawPage;

public class Frame {

	private static JFrame frame = new JFrame("signal-flow-graph");
	private static DrawPage drawPage = new DrawPage();
	private static JTextField numOfNodesTextFiled ;
	
	public static void main(String[] args) {
		setup();
		frame.add(drawPage);
		drawPage.setVisible(false);
		enterButton();
	}
	
	private static void setup() {
		frame.getContentPane().setBackground(Color.CYAN);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 5, 1300, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	public static void enterButton() {
		numOfNodesTextFiled = new JTextField();
		numOfNodesTextFiled.setBounds(600, 50, 100, 30);
		frame.add(numOfNodesTextFiled);
		final JButton enter = new JButton("Enter");
		enter.setBounds(750, 50, 75, 30);
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int n = Integer.parseInt(numOfNodesTextFiled.getText());
					drawPage.setNumOfNodes(n);
					drawPage.setVisible(true);
					enter.setVisible(false);
					numOfNodesTextFiled.setVisible(false);
				} catch (Exception e) {
					
				}
			}
		});
		frame.add(enter);
	}
	
}
