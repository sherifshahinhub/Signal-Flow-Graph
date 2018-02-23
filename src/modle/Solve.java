package modle;

import java.util.ArrayList;

public class Solve {
	
	double[][] data;
	public ArrayList<String> forwardPaths = new ArrayList<String>();
	public ArrayList<String> individualLoops = new ArrayList<String>();
	public ArrayList<Double> forwardPathsGains = new ArrayList<Double>();
	public ArrayList<Double> individualLoopsGains = new ArrayList<Double>();
	public ArrayList<String> loops = new ArrayList<String>();
	public ArrayList<ArrayList<String>> nonTouchingLoops = new ArrayList<ArrayList<String>>();
	public ArrayList<ArrayList<Double>> nonTouchingLoopsGains = new ArrayList<ArrayList<Double>>();
	public ArrayList<Double> forwardPathsDelta = new ArrayList<Double>();
	int numOfNodes;
	
	/*********************************************************************************************/
	
	/**
	 * take number of nodes.
	 * @param numOfNodes.
	 */
	public void setData(double[][] data2) {
		this.data = data2;
		this.numOfNodes = data2.length;
	}
	
	/**
	 * set gains between the nodes. 
	 * @param firstNode.
	 * @param secondNode.
	 * @param gain.
	 */
	public void setGains(int firstNode , int secondNode , int gain) {
		data[firstNode - 1][secondNode - 1] = gain;
	}
	
	/**
	 * get the forward paths from node to the output node.
	 * @param node.
	 * @param path.
	 */
	public void getForwardPaths(int node,String path) {
		if (path.contains("" + node)) {
			return;
		}
		path += node;
		if(node == numOfNodes) {
			forwardPaths.add(path);
			forwardPathsGains.add(culculateGain(path));
			return;
		}
		for(int i = 0 ; i < numOfNodes ; i++) {
			if(data[node - 1][i] != 0) {
				getForwardPaths(i + 1,path);
			}
		}
	}
	
	/**
	 * get all loops in the flow graph.
	 */
	public void getIndividualLoops() {
		String loop = "";
		for (int i = 0; i < data.length; i++) {
			if (data[i][i] != 0) {
				loop += (i + 1);
				loop += (i + 1);
				individualLoops.add(loop);
				individualLoopsGains.add(culculateGain(loop));
				loop = "";
			}
		}
		for(int i = 0 ; i < data.length ; i++) {
			for (int j = i + 1; j < data.length; j++) {
				if(data[j][i] != 0) {
					getForwardPaths(i + 1 , j + 1 , "");
					for (int k = 0 ; k < loops.size() ; k++) {
						individualLoops.add(loops.get(k) + (i + 1));
						individualLoopsGains.add(culculateGain(loops.get(k) + (i + 1)));
					}
					loops.clear();
				}
			}
		}
	}
	
	/**
	 * get forward paths between two nodes.
	 * @param firstNode.
	 * @param lastNode.
	 * @param path.
	 */
	public void getForwardPaths(int firstNode,int lastNode,String path) {
		if (path.contains("" + firstNode)) {
			return;
		}
		path += firstNode;
		if(firstNode == lastNode) {
			loops.add(path);
			return;
		}
		for(int i = 0 ; i < numOfNodes ; i++) {
			if(data[firstNode - 1][i] != 0) {
				getForwardPaths(i + 1,lastNode,path);
			}
		}
	}
	
	/**
	 * get from 2 non-touching loops to the nth non-touching loops.
	 */
	public void getNonTouchingLoops() {
		int numOfLoops = individualLoops.size();
		for (int i = 0; i < numOfLoops; i++) {
			nonTouchingLoops.add(new ArrayList<String>());
			nonTouchingLoopsGains.add(new ArrayList<Double>());
		}
		for (int i = 3; i < Math.pow(2,numOfLoops); i++) {
			ArrayList<Integer> positions = getPositions(i);
			ArrayList<String> loops = new ArrayList<String>();
			if(positions.size() > 1) {
				for (int j = 0; j < positions.size(); j++) {
					loops.add(individualLoops.get(positions.get(j)));
				}
				if (checkNonTouchingLoops(loops)) {
					String str = loops.get(0);
					for (int j = 1; j < loops.size(); j++) {
						str += " , " + loops.get(j); 
					}
					nonTouchingLoops.get(positions.size() - 2).add(str);
					double gain = 1;
					for (int j = 0; j < positions.size(); j++) {
						gain *= culculateGain((individualLoops.get(positions.get(j))));
					}
					nonTouchingLoopsGains.get(positions.size() - 2).add(gain);
				}
			}
		}
	}
	
	/**
	 * .
	 * @return gain of delta.
	 */
	public double clculateDelta() {
		double gain = 1;
		for (int i = 0; i < individualLoopsGains.size(); i++) {
			gain -= individualLoopsGains.get(i);
		}
		for (int i = 0; i < nonTouchingLoopsGains.size(); i++) {
			for (int j = 0; j < nonTouchingLoopsGains.get(i).size(); j++) {
				gain += Math.pow(-1, i + 2) * nonTouchingLoopsGains.get(i).get(j);
			}
		}
		return gain;
	}
	
	/**
	 * culculate Delta1,Delta2,....,Delta(n) where n is the number of forward paths.
	 */
	public void culculateForwardPathesDelta() {
		for (int i = 0; i < forwardPaths.size(); i++) {
			double gain = 1;
			for (int j = 0; j < individualLoops.size(); j++) {
				if(!checkTouching(forwardPaths.get(i), individualLoops.get(j))) {
					gain -= individualLoopsGains.get(j);
				}
			}
			for (int k = 0; k < nonTouchingLoopsGains.size(); k++) {
				for (int j = 0; j < nonTouchingLoopsGains.get(k).size(); j++) {
					String[] loops = nonTouchingLoops.get(k).get(j).split(",");
					boolean flag = true;
					for (int l = 0; l < loops.length; l++) {
						if(checkTouching(forwardPaths.get(i), loops[l])) {
							flag = false;
						}
					}
					if (flag) {
						gain -= Math.pow(-1, k) * nonTouchingLoopsGains.get(k).get(j);
					}
				}
			}
			forwardPathsDelta.add(gain);
		}
	}
	
	/**
	 * culculate overall transfer function.
	 * @return
	 */
	public double culculateOverallTF() {
		int sum = 0;
		for (int i = 0; i < forwardPathsGains.size(); i++) {
			sum += (forwardPathsGains.get(i) * forwardPathsDelta.get(i));
		}
		return (sum / clculateDelta());
	}
	
	/**
	 * check if two or more loops touching or not.
	 * @param list.
	 * @return true if not touching , false if at least two touching.
	 */
	private boolean checkNonTouchingLoops(ArrayList<String> list) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if(checkTouching(list.get(i), list.get(j))) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * check if two loops touching or not.
	 * @param str1.
	 * @param str2.
	 * @return false if not touching , true if touching.
	 */
	private boolean checkTouching(String str1 , String str2) {
		str1 = str1.trim();
		str2 = str2.trim();
		for (int i = 0; i < str1.length(); i++) {
			if(str2.contains(""+str1.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * culculate the gain of loop or forward path.
	 * @param str.
	 * @return the gain.
	 */
	private double culculateGain(String str) {
		double gain = 1;
		for (int i = 0; i < str.length() - 1; i++) {
			gain *= data[Integer.parseInt(""+str.charAt(i)) - 1][Integer.parseInt(""+str.charAt(i + 1)) - 1];
		}
		return gain;	
	}
	
	/**
	 * get positions of the ones in the binary representation of the number.  
	 * @param number.
	 * @return array list contains the positions.
	 */
	private ArrayList<Integer> getPositions(int number) {
		ArrayList<Integer> positions = new ArrayList<Integer>();
		int position = 0;
		while (number != 0) {
			if ((number & 1) != 0) {
				positions.add(position);
			}
			position++;
			number = number >>> 1;
		}
		return positions;
	}
}

