package pilsner.art2;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.math.linear.AbstractRealVector;
import org.apache.commons.math.linear.ArrayRealVector;

//import org.apache.commons.math.linear.

public class Art2Net
{
	private static final double DEFAULT_VIGILANCE = 0.7;
	
	private Neuron[] compareLayer;
	
	private Neuron[] recognitionLayer;
	
	private Synapsis[] forwardSynapsis;
	
	private Synapsis[] backwardSynapsis;
	
	private double alpha;
	
	private double theta;
	
	private double vigilance;
	
	private double a;
	
	private double b;
	
	private double c;
	
	private double d;
	
	private double e;
	
	private AbstractRealVector u;
	
	private AbstractRealVector w;
	
	private AbstractRealVector p;
	
	private AbstractRealVector x;
	
	private AbstractRealVector q;
	
	private AbstractRealVector v;
	
	private AbstractRealVector r;
	
	public Art2Net(int compareLayerSize, int recognitionLayerSize,
			double vigilance, double alpha, double theta, 
			double a, double b, double c, double d, double e)
	{
		this.vigilance = (0 <= vigilance && vigilance <= 1) ? vigilance : DEFAULT_VIGILANCE;
		this.alpha = alpha;
		this.theta = theta;
		
		compareLayer = new Neuron[compareLayerSize];
		recognitionLayer = new Neuron[recognitionLayerSize];
		forwardSynapsis = new Synapsis[compareLayer.length * recognitionLayer.length];
		backwardSynapsis = new Synapsis[forwardSynapsis.length];
		
		for (int i = 0; i < compareLayer.length; i++)
		{
			compareLayer[i] = new Neuron();
		}
		
		for (int i = 0; i < recognitionLayer.length; i++)
		{
			recognitionLayer[i] = new Neuron();
		}
		
		for (int i = 0; i < forwardSynapsis.length; i++)
		{
			forwardSynapsis[i] = new Synapsis();
		}
		
		for (int i = 0; i < backwardSynapsis.length; i++)
		{
			backwardSynapsis[i] = new Synapsis();
		}
		
		u = new ArrayRealVector(compareLayer.length);
		w = new ArrayRealVector(compareLayer.length);
		p = new ArrayRealVector(compareLayer.length);
		x = new ArrayRealVector(compareLayer.length);
		q = new ArrayRealVector(compareLayer.length);
		v = new ArrayRealVector(compareLayer.length);
		r = new ArrayRealVector(compareLayer.length);
		
		initParameters(a, b, c, d, e);
		
		initSynapsis();
		connect();
		
		/*for (Neuron n: recognitionLayer)
		{
			System.out.println(n.getOutputSynapsis());
		}*/
		//System.out.println("++++++++++++++++++++++++++");
	}
	
	private void initParameters(double a, double b, double c, double d, double e)
	{
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
	}
	
	private void initSynapsis()
	{
		for (int i = 0; i < forwardSynapsis.length; i++)
		{
			forwardSynapsis[i] = new Synapsis();
			backwardSynapsis[i] = new Synapsis();
		}
	}
	
	private void connect()
	{
		int k = 0; //synapsis counter
		
		for (int i = 0; i < compareLayer.length; i++)
		{
			for (int j = 0; j < recognitionLayer.length; j++)
			{
				compareLayer[i].addOutputSynapsis(forwardSynapsis[k]);
				recognitionLayer[j].addInputSynapsis(forwardSynapsis[k]);
				//forwardSynapsis[k].setWeight(0.05); //WHY?
				forwardSynapsis[k].setWeight(7d);//0d);
				
				compareLayer[i].addInputSynapsis(backwardSynapsis[k]);
				recognitionLayer[j].addOutputSynapsis(backwardSynapsis[k]);
				//backwardSynapsis[k].setWeight(0.01d);//Synapsis.computeWeightBottomUp(0, 1));
				backwardSynapsis[k].setWeight(0d);
				
				forwardSynapsis[k].setFrom(compareLayer[i]);
				forwardSynapsis[k].setTo(recognitionLayer[j]);
				
				backwardSynapsis[k].setFrom(recognitionLayer[j]);
				backwardSynapsis[k].setTo(compareLayer[i]);
				
				k++;
			}
		}
	}
	
	private Neuron findWinner()
	{
		/*Neuron winner = recognitionLayer[0];
		
		for (Neuron n: recognitionLayer)
		{
			System.out.println("neuron response: " + n.computeResponse());
			if (!n.isBlocked() && n.computeResponse() > winner.computeResponse())
				winner = n;
		}
		return winner;*/
		
		Neuron winner = null;
		double maxResponse = -Double.MAX_VALUE;
		double currentResponse;
		for (int i = 0; i < recognitionLayer.length; i++)
		{
			if (recognitionLayer[i].isBlocked())
			{
				continue;
			}
			else
			{
				currentResponse = recognitionLayer[i].computeResponse();
				System.out.println("i: "+ i + ", value: " + currentResponse);
				if (currentResponse > maxResponse)
				{
					winner = recognitionLayer[i];
					maxResponse = currentResponse;
				}
			}
		}
		
		return winner;
		
	}
	
	private void initInput(AbstractRealVector input)
	{
		for (int i = 0; i < compareLayer.length && i < input.getDimension(); i++)
		{
			compareLayer[i].setResponse(input.getEntry(i));
		}
	}
	
	private void blockRecognitionLayerNeurons(boolean blocked)
	{
		for (Neuron n: recognitionLayer)
		{
			n.setBlocked(blocked);
		}
	}
	
	private double evaluateActivation(double value)
	{
		return value >= theta ? value : 0;
	}
	
	public int compute(AbstractRealVector vector)
	{
		for (int i = 0; i < vector.getDimension(); i++)
		{
			u.setEntry(i, 0d);
			w.setEntry(i, vector.getEntry(i));
			p.setEntry(i, 0d);
			x.setEntry(i, w.getEntry(i) / (e + vector.getNorm()));
			q.setEntry(i, 0d);
			v.setEntry(i, evaluateActivation(x.getEntry(i)));
		}
		System.out.println("1st");
		System.out.println(u);
		System.out.println(w);
		System.out.println(p);
		System.out.println(x);
		System.out.println(q);
		System.out.println(v);
		
		for (int i = 0; i < vector.getDimension(); i++)
		{
			u.setEntry(i, v.getEntry(i) / (e + v.getNorm()));
			w.setEntry(i, vector.getEntry(i) + a * u.getEntry(i));
			p.setEntry(i, u.getEntry(i));
			x.setEntry(i, w.getEntry(i) / (e + w.getNorm()));
			q.setEntry(i, p.getEntry(i) / (e + p.getNorm()));
			v.setEntry(i, evaluateActivation(x.getEntry(i)) + b * evaluateActivation(q.getEntry(i)));
		}
		System.out.println("2nd");
		System.out.println(u);
		System.out.println(w);
		System.out.println(p);
		System.out.println(x);
		System.out.println(q);
		System.out.println(v);
		
		initInput(p);
		
		Neuron winner;
		
		do
		{
			winner = findWinner();
			
			for (int i = 0; i < compareLayer.length; i++)
			{
				u.setEntry(i, v.getEntry(i) / (e + v.getNorm()));
				p.setEntry(i, u.getEntry(i) + d * winner.getOutputSynapsis().get(i).getWeight());
				r.setEntry(i, (u.getEntry(i) + c * p.getEntry(i)) / (e + u.getNorm() + c * p.getNorm()));
			
			}
			
			System.out.println("3rd");
			System.out.println(u);
			System.out.println(p);
			System.out.println(r);
			
			System.out.println("if: " + r.getNorm());
			if (r.getNorm() < vigilance - e)
			{
				winner.setBlocked(true);
			}
			else
			{
				for (int i = 0; i < compareLayer.length; i++)
				{
					w.setEntry(i, vector.getEntry(i) + a * u.getEntry(i));
					x.setEntry(i, w.getEntry(i) / (e + w.getNorm()));
					q.setEntry(i, p.getEntry(i) / (e + p.getNorm()));
					v.setEntry(i, evaluateActivation(x.getEntry(i) + b * evaluateActivation(q.getEntry(i))));
				}
				System.out.println("4th");
				System.out.println(w);
				System.out.println(x);
				System.out.println(q);
				System.out.println(v);
				break;
			}
			 
		} while (true);
		
		blockRecognitionLayerNeurons(false);
		
		//any reason to skip weight modification?
		
		//weights modification
				
		for (int i = 0; i < 2; i++)
		{
			
			for (int j = 0; j < compareLayer.length; j++)
			{
				winner.getOutputSynapsis().get(j).setWeight(
						alpha * d * u.getEntry(j) + (1 + alpha * d * (d - 1)) * winner.getOutputSynapsis().get(j).getWeight());
				//System.out.println(winner.getOutputSynapsis().get(j).getWeight());
				winner.getInputSynapsis().get(j).setWeight(
						alpha * d * u.getEntry(j) + (1 + alpha * d * (d - 1)) * winner.getInputSynapsis().get(j).getWeight());
				//System.out.println(winner.getInputSynapsis().get(j).getWeight());
			}
			System.out.println("///////////////////////////////");
			for (int j = 0; j < compareLayer.length; j++)
			{
				System.out.println(winner.getOutputSynapsis().get(j).getWeight());
				System.out.println(winner.getInputSynapsis().get(j).getWeight());
			}
			
			System.out.println("///////////////////////////////");
			for (int j = 0; j < vector.getDimension(); j++)
			{
				u.setEntry(j, v.getEntry(j) / (e + v.getNorm()));
				w.setEntry(j, vector.getEntry(j) + a * u.getEntry(j));
				p.setEntry(j, u.getEntry(j) + d * winner.getOutputSynapsis().get(j).getWeight());
				x.setEntry(j, w.getEntry(j) / (e + w.getNorm()));
				q.setEntry(j, p.getEntry(j) / (e + p.getNorm()));
				v.setEntry(j, evaluateActivation(x.getEntry(j)) + b * evaluateActivation(q.getEntry(j)));
			}
			
			System.out.println("learn");
			System.out.println(u);
			System.out.println(w);
			System.out.println(p);
			System.out.println(x);
			System.out.println(q);
			System.out.println(v);
		}
		
		return getRecognitionIndex(winner);
	}
	
	public int[] compute(List<AbstractRealVector> inputs)
	{
		int[] clusterIndex = new int[inputs.size()];
		
		for (int i = 0; i < inputs.size(); i++)
		{
			clusterIndex[i] = compute(inputs.get(i));
		}
		
		return clusterIndex;
	}
	
	private int getRecognitionIndex(Neuron n)
	{
		for (int i = 0; i < recognitionLayer.length; i++)
		{
			if (recognitionLayer[i].equals(n))
				return i;
		}
		
		return -1;
	}
}
