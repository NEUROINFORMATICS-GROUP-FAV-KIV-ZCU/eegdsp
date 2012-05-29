package pilsner.art2;

import java.util.ArrayList;
import java.util.List;

class Neuron
{
	private static int idCounter = 0;
	
	private int id;
	
	private boolean blocked;
	
	private double response;
	
	private List<Synapsis> inputSynapsis;
	
	private List<Synapsis> outputSynapsis;

	Neuron()
	{
		id = idCounter++;
		blocked = false;
		response = 0d;
		// http://stackoverflow.com/questions/322715/when-to-use-linkedlist-over-arraylist
		inputSynapsis = new ArrayList<Synapsis>(); 
		outputSynapsis = new ArrayList<Synapsis>();
	}
	
	public int getId()
	{
		return id;
	}

	public void addInputSynapsis(Synapsis s)
	{
		inputSynapsis.add(s);
	}
	
	public void addOutputSynapsis(Synapsis s)
	{
		outputSynapsis.add(s);
	}
	
	public void removeInputSynapsis(Synapsis s)
	{
		
	}
	
	
	
	public List<Synapsis> getInputSynapsis()
	{
		return inputSynapsis;
	}

	public List<Synapsis> getOutputSynapsis()
	{
		return outputSynapsis;
	}

	public boolean isBlocked()
	{
		return blocked;
	}

	public void setBlocked(boolean blocked)
	{
		this.blocked = blocked;
		
		setResponse(blocked ? -1d : 0d);
	}
	public double computeResponse()
	{
		double currentResponse = 0;
		
		for (Synapsis s: inputSynapsis)
		{
			currentResponse += s.getWeight() + s.getFrom().getResponse();
		}
		
		return currentResponse;
	}
	
	public double getResponse()
	{
		return response;
	}

	public void setResponse(double response)
	{
		this.response = response;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Neuron)
		{
			Neuron n = (Neuron) o;
			
			if (n.inputSynapsis.size() == this.inputSynapsis.size()
					&& n.outputSynapsis.size() == this.outputSynapsis.size())
			{
				for (int i = 0; i < this.inputSynapsis.size(); i++)
					if (!n.inputSynapsis.get(i).equals(this.inputSynapsis.get(i)))
						return false;
				
				for (int i = 0; i < this.outputSynapsis.size(); i++)
					if (!n.outputSynapsis.get(i).equals(this.outputSynapsis.get(i)))
						return false;
				
				return true;
			}
		}
		
		return false;
	}
	
	/*
	@Override
	public int hashCode()
	{
		
	}
	*/
	
	@Override
	public String toString()
	{
		return "Neuron id: " + id + "/n"
				+ "Input synapsis from neurons: " + inputSynapsis.toString() + "/n"
				+ "Output synapsis to neurons: " + outputSynapsis.toString() + "/n";
	}
}
