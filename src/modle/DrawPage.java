package modle;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Path2D;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.Solver;

public class DrawPage extends JPanel {

	private int numOfNodes = 0;
	private int distance = 0;
	private JTextField firstNodeTextField;
	private JTextField secondNodeTextField;
	private JTextField gainTextField;
	private double[][] data;
	private Solver solver = new Solver();

	public DrawPage() {
		this.setBounds(0, 0, 1300, 700);
		this.setLayout(null);
		this.setVisible(true);
		setButtons();
		setTextfiled();
		setNumOfNodes(10);
	}

	void setRandomColor(Graphics g) {
		Random rand = new Random();
		float red = rand.nextFloat();
		float green = rand.nextFloat();
		float blue = rand.nextFloat();
		g.setColor(new Color(red, green, blue));
	}

	public void setNumOfNodes(int n) {
		numOfNodes = n;
		distance = 1100 / (n - 1);
		data = new double[n][n];
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

	public void doDrawing(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		for (int i = 1; i <= numOfNodes; i++) {
			drawCircle(g2, i);
		}
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data.length; j++) {
				if (data[i][j] != 0) {
					if (i == j) {
						setRandomColor(g);
						drawArc2(g2, i + 1, j + 1, data[i][j]);
					} else if (Math.abs(i - j) > 1) {
						setRandomColor(g);
						drawArc(g2, i + 1, j + 1, data[i][j]);
					} else if (i - j == -1) {
						setRandomColor(g);
						drawLine(g2, i + 1, j + 1, data[i][j]);
					} else {
						setRandomColor(g);
						drawArc(g2, i + 1, j + 1, data[i][j]);
					}
				}
			}
		}
	}

	/********************************************************/
	public void drawCircle(Graphics2D g, int node) {
		int x = 100 + (node - 1) * distance;
		g.setFont(new Font("Serif", Font.PLAIN, 24));
		g.drawOval(x, 250, 30, 30);
		g.drawString("" + node, x + 5, 270);
	}

	/********************************************************/
	public void drawLine(Graphics2D g, int first, int second, double data2) {
		int x1 = 100 + (first - 1) * distance + 30;
		int x2 = 100 + (second - 1) * distance;
		Path2D.Double path = new Path2D.Double();
		if (first < second) {
			path.moveTo(x1 + (x2 - x1) / 2, 258);
			path.lineTo(x1 + (x2 - x1) / 2, 272);
			path.lineTo(x1 + (x2 - x1) / 2 + 20, 265);
		} else {
			path.moveTo(x1 + (x2 - x1) / 2, 258);
			path.lineTo(x1 + (x2 - x1) / 2, 272);
			path.lineTo(x1 + (x2 - x1) / 2 - 20, 265);
			x1 = 100 + (second - 1) * distance + 30;
			x2 = 100 + (first - 1) * distance;
		}
		g.fill(path);
		g.drawLine(x1, 265, x2, 265);
		g.drawString("" + data2, x1 + (x2 - x1) / 2, 250);
	}

	/********************************************************/
	public void drawArc(Graphics2D g, int first, int second, double gain) {
		int x1 = 100 + (first - 1) * distance + 15;
		int x2 = 100 + (second - 1) * distance + 15;
		int y = Math.abs(second - first) * distance;
		Path2D.Double path = new Path2D.Double();
		path.moveTo(x1, 250);
		path.quadTo(x1 + (x2 - x1) / 2, 265 - y / 2, x2, 250);
		g.draw(path);
		path = new Path2D.Double();
		if (first < second) {
			path.moveTo(x1 + (x2 - x1) / 2, 260 - y / 4 - 7);
			path.lineTo(x1 + (x2 - x1) / 2, 260 - y / 4 + 7);
			path.lineTo(x1 + (x2 - x1) / 2 + 20, 260 - y / 4 - 2);
		} else {
			path.moveTo(x1 + (x2 - x1) / 2, 260 - y / 4 - 7);
			path.lineTo(x1 + (x2 - x1) / 2, 260 - y / 4 + 7);
			path.lineTo(x1 + (x2 - x1) / 2 - 20, 260 - y / 4 - 2);
			x1 = 100 + (second - 1) * distance + 15;
			x2 = 100 + (first - 1) * distance + 15;
		}
		g.fill(path);
		g.drawString("" + gain, x1 + (x2 - x1) / 2, 265 - y / 4 - 10);
	}

	/******************************************************************/
	public void drawArc2(Graphics2D g, int first, int second, double gain) {
		int x1 = 100 + (first - 1) * distance + 15;
		Path2D.Double path = new Path2D.Double();
		path.moveTo(x1 - 15, 265);
		path.quadTo(x1, 160, x1 + 15, 265);
		g.draw(path);
		// path = new Path2D.Double();
		// path.moveTo(x1 , 220);
		// path.lineTo(x1 , 220 + 7);
		// path.lineTo(x1 + 20, 220 - 2);
		// g.fill(path);
		g.drawString("" + gain, x1, 220 - 10);
	}

	/**********************************************************/
	public void setButtons() {
		JButton enter = new JButton("Enter");
		enter.setBounds(750, 25, 75, 30);
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enter();
			}
		});
		JButton solve = new JButton("Solve");
		solve.setBounds(850, 25, 75, 30);
		solve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				solve();
			}
		});
		this.add(solve);
		this.add(enter);
	}

	public void setTextfiled() {
		firstNodeTextField = new JTextField();
		firstNodeTextField.setBounds(360, 25, 100, 30);
		secondNodeTextField = new JTextField();
		secondNodeTextField.setBounds(490, 25, 100, 30);
		gainTextField = new JTextField();
		gainTextField.setBounds(620, 25, 100, 30);
		this.add(firstNodeTextField);
		this.add(secondNodeTextField);
		this.add(gainTextField);
	}

	public void enter() {
			int firstNode=0;
			int secondNode = 0;
			Double gain=0.0;
		try {
			 firstNode = Integer.parseInt(firstNodeTextField.getText());
			 secondNode = Integer.parseInt(secondNodeTextField.getText());
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Nodes must be integers");
		}
		
		try {
			gain = Double.parseDouble(gainTextField.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Gain must be RealNumber");
		}
		handle(firstNode, secondNode);
		
		try {
			data[firstNode - 1][secondNode - 1] = gain;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "please Enter Nodes in range 1 to "+data.length);
		}
		repaint();
	}

	public void handle(int first, int second) {
		if (second == 1) {
			JOptionPane.showMessageDialog(null,"Input node can't get inputs");
			throw new RuntimeException();
		}
	}

	public void solve() {
		solver.setData(data);
		solver.solve();
	}
}
