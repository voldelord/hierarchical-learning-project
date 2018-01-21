package Neural;

import java.io.Serializable;
import java.util.*;

public class Neuron implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4253495208116671925L;
	static int counter = 0;
	final public int id; // auto increment, starts at 0
	Connection biasConnection;
	final double bias = -1;
	double output;
	double error;
	ArrayList<Connection> Inconnections = new ArrayList<Connection>();
	HashMap<Integer, Connection> connectionLookup = new HashMap<Integer, Connection>();

	public Neuron() {
		id = counter;
		counter++;
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	/**
	 * Compute Sj = Wij*Aij + w0j*bias
	 */
	public void calculateOutput(boolean outputLayer) {
		double s = 0;
		for (Connection con : Inconnections) {
			Neuron leftNeuron = con.getFromNeuron();
			double weight = con.getWeight();
			double a = leftNeuron.getOutput(); // output from previous layer

			s = s + (weight * a);
		}
		s = s + (biasConnection.getWeight() * bias);

		if (outputLayer) {
			output = g(s);
		}else{
			output = s;
		}
		//System.out.print(output+", ");
	}

	double g(double x) {
		return sigmoid(x);
	}

	double sigmoid(double x) {
		return 1.0 / (1.0 + (Math.exp(-x)));
	}

	public double dsigmoid(double x) {
		double t = sigmoid(x);
		return t * (1 - t);
	}

	public void addInConnectionsS(ArrayList<Neuron> inNeurons) {
		for (Neuron n : inNeurons) {
			Connection con = new Connection(n, this);
			Inconnections.add(con);
			connectionLookup.put(n.id, con);
		}
	}

	public Connection getConnection(int neuronIndex) {
		return connectionLookup.get(neuronIndex);
	}

	public void addInConnection(Connection con) {
		Inconnections.add(con);
	}

	public void addBiasConnection(Neuron n) {
		Connection con = new Connection(n, this);
		biasConnection = con;
		Inconnections.add(con);
	}

	public ArrayList<Connection> getAllInConnections() {
		return Inconnections;
	}

	public double getBias() {
		return bias;
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(double o) {
		output = o;
	}
}