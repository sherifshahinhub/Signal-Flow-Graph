package modle;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ResultPage extends JPanel{

	private int xForwardPaths = 50;
	private int xForwardPathsGains = 200;
	private int xForwardPathsDelta = 350;
	private int xIndividualLoops = 500;
	private int xIndividualLoopsGains = 650;
	private int xNonTouchingLoops = 900;
	private int xNonTouchingLoopsGain = 1100;
	private Solve solve;
	private int y = 100;
	public ResultPage() {
		setup();
	}
	
	private void setup() {
		this.setBackground(Color.LIGHT_GRAY);
		this.setBounds(0, 0, 1300, 700);
		this.setLayout(null);
		this.setVisible(false);
	}
	
	public void draw(Solve solve) {
		this.solve = solve;
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}
	
	public void doDrawing(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(new Font("Serif", Font.PLAIN, 24));
		g.drawString("Fprward Paths:", xForwardPathsGains, 25);
		g.drawString("Paths:", xForwardPaths, 65);
		g.drawString("Paths Gains:", xForwardPathsGains, 65);
		g.drawString("Paths Delta:", xForwardPathsDelta, 65);
		
		g.drawString("Individual Loops:", xIndividualLoops + 75, 25);
		g.drawString("Loops:", xIndividualLoops, 65);
		g.drawString("Loops Gains:", xIndividualLoopsGains, 65);
		
		g.drawString("Non-Touching Loops:", xNonTouchingLoops + 75, 25);
		g.drawString("Loops:", xNonTouchingLoops, 65);
		g.drawString("Loops Gains:", xNonTouchingLoopsGain, 65);
		
		drawForwadPaths(g2);
		drawIndivdualLoops(g2);
		drawNonTouchingLoops(g2);
		g.drawString("Delta =  " + solve.clculateDelta(), 500, 600);
		g.drawString("OverAll T.F =  " + solve.culculateOverallTF(), 500, 650);
	}
	
	public void drawForwadPaths(Graphics2D g) {
		for (int i = 0; i < solve.forwardPaths.size(); i++) {
			g.drawString(solve.forwardPaths.get(i), xForwardPaths, y + i * 30);
			g.drawString("" + solve.forwardPathsGains.get(i), xForwardPathsGains, y + i * 30);
			g.drawString("" + solve.forwardPathsDelta.get(i), xForwardPathsDelta, y + i * 30);
		}
	}
	
	public void drawIndivdualLoops(Graphics2D g) {
		for (int i = 0; i < solve.individualLoops.size(); i++) {
			g.drawString(solve.individualLoops.get(i), xIndividualLoops, y + i * 30);
			g.drawString("" + solve.individualLoopsGains.get(i), xIndividualLoopsGains, y + i * 30);
		}
	}
	
	public void drawNonTouchingLoops(Graphics2D g) {
		int height = y;
		for (int i = 0; i < solve.nonTouchingLoops.size(); i++) {
			if(solve.nonTouchingLoops.get(i).size() > 0) {
				g.drawString((i + 2) + " non-touching loops:", xNonTouchingLoops, height);
				height += 30;
				for (int j = 0; j < solve.nonTouchingLoops.get(i).size(); j++) {
					g.drawString(solve.nonTouchingLoops.get(i).get(j), xNonTouchingLoops, height);
					g.drawString("" + solve.nonTouchingLoopsGains.get(i).get(j), xNonTouchingLoopsGain, height);
					height += 30;
				}
			}
		}
	}
}
