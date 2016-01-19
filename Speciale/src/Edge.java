import java.util.ArrayList;

public class Edge {
	private Node fromNode;
	private Node toNode;
	private int cost; 
	private int realCost;
	private int lagrange;
	private int lagrangeMultiplier;
	private int capacity;
	private int load;
	private double travelTime;
	private boolean omission;
	private boolean sail;
	private boolean dwell;
	private boolean transshipment;
	private boolean loadUnload;
	private Rotation rotation;
	private int noInRotation;
	private ArrayList<Demand> shortestPathOD;
	
	public Edge(){
	}

	/** Constructor for rotation, load/unload and transshipment edges, i.e. not omission edges. 
	 * @param fromNode
	 * @param toNode
	 * @param cost
	 * @param capacity
	 */
	public Edge(Node fromNode, Node toNode, int cost, int capacity, double travelTime, boolean rotationEdge, Rotation rotation, int noInRotation){
		super();
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.cost = cost;
		this.realCost = cost;
		this.lagrange = 0;
		this.lagrangeMultiplier = 0;
		this.capacity = capacity;
		this.travelTime = travelTime;
		this.omission = false;
		this.sail = false;	
		this.dwell = false;
		this.transshipment = false;
		this.loadUnload = false;
		this.rotation = rotation;
		this.noInRotation = noInRotation;
		if(fromNode.isDeparture() && toNode.isArrival() && rotationEdge){
			this.sail = true;	
		} else if(fromNode.isArrival() && toNode.isDeparture() && rotationEdge){
			this.dwell = true;
		} else if(fromNode.isArrival() && toNode.isDeparture() && !rotationEdge) {
			this.transshipment = true;
		} else if(fromNode.isArrival() && toNode.isCentroid() || fromNode.isCentroid() && toNode.isDeparture()){
			this.loadUnload = true;
		} else {
			throw new RuntimeException("Tried to construct an edge that does not fit "
					+ "with either sail, dwell, transshipment or load/unload definitions.");
		}
		toNode.addIngoingEdge(this);
		fromNode.addOutgoingEdge(this);
		shortestPathOD = new ArrayList<Demand>();
	}

	/** Constructor for omission edges.
	 *  Cost is set to the revenue of the OD-pair + 1000$.
	 *  Capacity is set default to Integer.MAX_VALUE.
	 * @param fromNode
	 * @param toNode
	 * @param revenue
	 */
	public Edge(Node fromNode, Node toNode, int revenue){
		super();
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.cost = 1000 + revenue;
		this.realCost = 1000;
		this.lagrange = 0;
		this.lagrangeMultiplier = 0;
		this.capacity = Integer.MAX_VALUE;
		this.travelTime = 0;
		this.omission = true;
		this.sail = false;
		this.dwell = false;
		this.transshipment = false;
		this.loadUnload = false;
		this.rotation = null;
		this.noInRotation = -1;
		toNode.addIngoingEdge(this);
		fromNode.addOutgoingEdge(this);
		shortestPathOD = new ArrayList<Demand>();
	}
	
	/**
	 * @return the fromNode
	 */
	public Node getFromNode() {
		return fromNode;
	}

	/**
	 * @return the toNode
	 */
	public Node getToNode() {
		return toNode;
	}

	/**
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}
	
	public void addLagrange(int lagrangeInput, int iteration){
		this.lagrangeMultiplier = lagrangeInput;
		this.lagrange += (int) (lagrangeInput * 1.0 / (double) iteration) + 1;
		this.cost = this.realCost+this.lagrange;
	}

	/**
	 * @return the realCost
	 */
	public int getRealCost() {
		return realCost;
	}

	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @return the omission
	 */
	public boolean isOmission() {
		return omission;
	}
	
	public boolean isSail() {
		return sail;
	}

	public boolean isDwell() {
		return dwell;
	}

	public boolean isTransshipment() {
		return transshipment;
	}

	public boolean isLoadUnload() {
		return loadUnload;
	}

	public double getTravelTime() {
		return travelTime;
	}
	
	public void resetLoad(){
		load = 0;
	}
	
	public void addLoad(int load){
		this.load += load;
	}

	public int getLoad() {
		return load;
	}
	
	public String getFromPortUNLo(){
		return fromNode.getPort().getUNLocode();
	}
	
	public String getToPortUNLo(){
		return toNode.getPort().getUNLocode();
	}
	
	public Rotation getRotation(){
		return this.rotation;
	}
	
	public int getNoInRotation(){
		return this.noInRotation;
	}
	
	public void clearShortestPathOD(){
		shortestPathOD.clear();
	}
	
	public void addShortestPathOD(Demand newOD){
		shortestPathOD.add(newOD);
	}
	
	public ArrayList<Demand> getShortestPathOD(){
		return shortestPathOD;
	}

	public int getLagrangeMultiplier() {
		return lagrangeMultiplier;
	}

	public String simplePrint(){
		String str = "Edge: ";
		if(sail || omission){
			str+= fromNode.getPort().getUNLocode() + "-" + toNode.getPort().getUNLocode();
			if(omission){
				str+= " omission";
			}
		} else {
			str+= "internal in " + fromNode.getPort().getUNLocode();
		}
		str +=  " with load: " + load + " and capacity: " + capacity;
		return str;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Edge [fromNode=" + fromNode + ", toNode=" + toNode + ", cost=" + cost + ", capacity=" + capacity
				+ ", omission=" + omission + "]";
	}
	
}
