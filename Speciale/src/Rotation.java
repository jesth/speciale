import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Rotation {
	private int id;
	private VesselClass vesselClass;
	private ArrayList<Node> rotationNodes;
	private ArrayList<Edge> rotationEdges;
	private double speed;
	private int noOfVessels;
	private int distance;
	private static AtomicInteger idCounter = new AtomicInteger();
	private boolean active;

	public Rotation(){
	}

	public Rotation(VesselClass vesselClass) {
		super();
		this.id = idCounter.getAndIncrement();
		this.vesselClass = vesselClass;
		this.rotationNodes = new ArrayList<Node>();
		this.rotationEdges = new ArrayList<Edge>();
		this.active = true;
		this.speed = 0;
		this.noOfVessels = 0;
//		calculateSpeed();
	}

	public void calcOptimalSpeed(){
		double bestSpeed = 0;
		int bestNoOfVessels = 0;
		int lowestCost = Integer.MAX_VALUE;
		int lbNoVessels = calculateMinNoVessels();
		int ubNoVessels = calculateMaxNoVessels();
		if(lbNoVessels > ubNoVessels){
			//TODO: Function to distribute extra time when sailing with min speed to port stays.
		}
		for(int i = lbNoVessels; i <= ubNoVessels; i++){
			double speed = calculateSpeed(i);
			int bunkerCost = calcSailingBunkerCost(speed, i);
			int TCRate = i * vesselClass.getTCRate();
			int cost = bunkerCost + TCRate;
			if(cost < lowestCost){
				bestSpeed = speed;
				lowestCost = cost;
				bestNoOfVessels = i;
			}
		}
		this.speed = bestSpeed;
		this.noOfVessels = bestNoOfVessels;
	}
	
	public double calculateSpeed(int noOfVessels){
		double availableTime = 168 * noOfVessels - 24 * getNoOfPortStays();
		return distance / availableTime;
	}

	public int calculateMinNoVessels(){
		double rotationTime = 24 * getNoOfPortStays() + (distance / vesselClass.getMaxSpeed()) / 168.0;
		int noVessels = (int) Math.ceil(rotationTime);
		return noVessels;
	}

	public int calculateMaxNoVessels(){
		double rotationTime = 24 * getNoOfPortStays() + (distance / vesselClass.getMinSpeed()) / 168.0;
		int noVessels = (int) Math.floor(rotationTime);
		return noVessels;
	}

	public int getDistance(){
		return distance;
	}

	/*
	public int calculateNoVessels(){
		//TODO hardcoded 168 hours per week.
		// also runs calculateRotationTime() every time number of vessels are needed. smart? 
		calculateRotationTime();
		this.noVessels = (int) Math.ceil(rotationTime/168.0);
		return this.noVessels;
	}
	 */

	//	public int calculateNoVessels(){
	//		//168 hours per week.
	//		return 1 + (int) rotationTime / 168;
	//	}

	public VesselClass getVesselClass() {
		return vesselClass;
	}

	public void addRotationNode(Node node){
		rotationNodes.add(node);
	}

	public void addRotationEdge(Edge edge){
		rotationEdges.add(edge);
		distance += edge.getDistance().getDistance();
	}

	public ArrayList<Node> getRotationNodes() {
		return rotationNodes;
	}

	public ArrayList<Edge> getRotationEdges() {
		return rotationEdges;
	}

	public int calcCost(){
		int obj = 0;
		VesselClass v = this.getVesselClass();
		ArrayList<Edge> rotationEdges = this.getRotationEdges();
		double sailingTime = 0;
		double idleTime = 0;
		int portCost = 0;
		int suezCost = 0;
		int panamaCost = 0;
		for (Edge e : rotationEdges){
			if(e.isSail()){
				sailingTime += e.getTravelTime();
				Port p = e.getToNode().getPort();
				portCost += p.getFixedCallCost() + p.getVarCallCost() * v.getCapacity();
				if(e.isSuez()){
					suezCost += v.getSuezFee();
				}
				if(e.isPanama()){
					panamaCost += v.getPanamaFee();
				}
			}
			if(e.isDwell()){
				idleTime += e.getTravelTime();
			}
		}
		//TODO USD per metric tons fuel = 600
		int sailingBunkerCost = calcSailingBunkerCost(speed, noOfVessels);
		double idleBunkerCost = (int) Math.ceil(idleTime/24.0) * v.getFuelConsumptionIdle() * 600;

		int rotationDays = (int) Math.ceil((sailingTime+idleTime)/24.0);
		int TCCost = rotationDays * v.getTCRate();

		System.out.println("Rotation number "+ this.id);
		System.out.println("Voyage duration in nautical miles " + distance);
		System.out.println(this.noOfVessels + " ships needed sailing with speed " + speed);
		System.out.println("Port call cost " + portCost);
		System.out.println("Bunker idle burn in Ton " + idleBunkerCost/600.0);
		System.out.println("Bunker fuel burn in Ton " + sailingBunkerCost/600.0);
		System.out.println("Total TC cost " + TCCost);
		System.out.println();

		obj -= sailingBunkerCost + idleBunkerCost + portCost + suezCost + panamaCost + TCCost;

		return obj;
	}
	
	public int calcSailingBunkerCost(double speed, int noOfVessels){
		double fuelConsumption = vesselClass.getFuelConsumption(speed);
		double sailTimeDays = (distance / speed) / 24.0;
		double bunkerConsumption = sailTimeDays * fuelConsumption;
		return (int) (bunkerConsumption * 600.0);
	}

	public int getNoOfVessels() {
		return noOfVessels;
	}

	public int getId(){
		return id;
	}

	public double getSailTime(){
		double sailTime = 0;
		for(Edge e : rotationEdges){
			if(e.isSail()){
				sailTime += e.getTravelTime();
			}
		}

		return sailTime;
	}

	public ArrayList<Port> getPorts(){
		ArrayList<Port> ports = new ArrayList<Port>();
		for(Edge e : rotationEdges){
			if(e.getToNode().isArrival()){
				ports.add(e.getToNode().getPort());
			}
		}
		return ports;
	}
	
	public int getNoOfPortStays(){
		int counter = 0;
		for(Edge e : rotationEdges){
			if(e.getToNode().isArrival()){
				counter++;
			}
		}
		return counter;
	}

	/**
	 * @return active or not
	 */
	public boolean isActive() {
		return active;
	}


	/**
	 * Set rotation to active
	 */
	public void setActive() {
		this.active = true;
	}


	/**
	 * Set rotation to inactive
	 */
	public void setInactive(){
		this.active = false;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		String print = "Rotation [vesselClass=" + vesselClass.getName() + ", noOfVessels=" + noOfVessels + "]\n";
		int counter = 0;
		for(Node i : rotationNodes){
			if(i.isDeparture()){
				print += "Port no. " + counter + ": " + i.getPort() + "\n";
				counter++;
			}
		}
		return print;
	}

}
