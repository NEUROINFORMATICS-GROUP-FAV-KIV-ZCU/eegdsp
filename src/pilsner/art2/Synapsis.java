package pilsner.art2;

class Synapsis
{
	static double computeWeightTopDown()
	{
		return 0d;
	}
	
	static double computeWeightBottomUp(int numberOfInputUnits, int activationOfWinningUnit)
	{
		return Math.random() * (1 / ((1 - activationOfWinningUnit) * Math.sqrt(numberOfInputUnits)));
	}
	
	private static final double DEFAULT_WEIGHT = 0d;
	
	private Neuron from;
	
	private Neuron to;
	
	private double weight;
	
	Synapsis()
	{
		weight = DEFAULT_WEIGHT;
	}
	
	Synapsis(Neuron from, Neuron to)
	{
		weight = DEFAULT_WEIGHT;
		this.from = from;
		this.to = to;
	}
	
	Synapsis(Neuron from, Neuron to, double weight)
	{
		this.weight = weight;
		this.from = from;
		this.to = to;
	}

	public Neuron getFrom()
	{
		return from;
	}

	public void setFrom(Neuron from)
	{
		this.from = from;
	}

	public Neuron getTo()
	{
		return to;
	}

	public void setTo(Neuron to)
	{
		this.to = to;
	}

	public double getWeight()
	{
		return weight;
	}

	public void setWeight(double weight)
	{
		this.weight = weight;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Synapsis)
		{
			Synapsis s = (Synapsis) o;
			
			return s.from.equals(this.from) && s.to.equals(this.to);
			
		}
		else
			return false;
	}
	
	@Override
	public String toString()
	{
		return "Synapsis from " + from.getId() + " to " + to.getId() + ", weight: " + weight;
	}
	
}
