package control;

import java.awt.Color;

import javax.swing.JFrame;

import modle.ResultPage;
import modle.Solve;

public class Solver {

	private JFrame frame = new JFrame("Result");
	private ResultPage resultPage = new ResultPage();
	public Solve solve = new Solve();
	
	public Solver() {
		setup();
	}
	
	private void setup() {
		frame.setBounds(100, 5, 1300, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(false);
		frame.setLocationRelativeTo(null);
	}
	
	public void setData(double[][] data) {
		solve.setData(data);
	}
	
	public void solve() {
		solve.getForwardPaths(1,"");
		solve.getIndividualLoops();
		solve.getNonTouchingLoops();
		solve.clculateDelta();
		solve.culculateForwardPathesDelta();
		resultPage.setVisible(true);
		frame.add(resultPage);
		frame.setVisible(true);
		resultPage.draw(solve);
	}
}
