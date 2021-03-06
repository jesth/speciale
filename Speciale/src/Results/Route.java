package Results;
import java.io.Serializable;
import java.util.ArrayList;

import Data.Demand;
import Graph.Edge;
import Methods.BellmanFord;

public class Route implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<Edge> route;
	private Demand demand;
	private int FFE;
	private int FFErep;
	private int FFEforRemoval;
	private boolean repair;
	private boolean omission;
	private int lagrangeProfit;
	private int realProfit;

	public Route(Demand demand, boolean repair){
		this.route = new ArrayList<Edge>();
		this.demand = demand;
		this.repair = repair;
		this.lagrangeProfit = 0;
		this.realProfit = 0;
		this.FFE = 0;
		this.FFErep = 0;
		this.FFEforRemoval = 0;
	}

	/** Determines the shortest route using Bellman-Ford and respecting the prohibited edges defined.
	 * 
	 */
//	public void findRoute(){
//		if(!route.isEmpty()){
//			throw new RuntimeException("Tried to find a route for a non-empty Route.");
//		}
//		BellmanFord.runSingleRoute(this);
//	}

	/** Updates the route and profit of this Route element.
	 * @param route - the edges used in this Route element.
	 */
	public void update(ArrayList<Edge> route){
		if(route.size() == 1 && route.get(0).isOmission()){
			this.omission = true;
		}
		//TODO: Add 1000 to lagrangeProfit???
		int lagrangeProfit = demand.getRate();
		int realProfit = demand.getRate();
		for(Edge e : route){
			e.addRoute(this);
			lagrangeProfit -= e.getCost();
			realProfit -= e.getRealCost();
		}
		this.lagrangeProfit = lagrangeProfit;
		this.realProfit = realProfit;
		this.route = route;

	}

	/**
	 * @return The number of FFE using this route when the created repair routes are <i>not</i> considered. 
	 */
	public int getFFE() {
		return FFE;
	}

	/**
	 * @param FFE - the number of FFE using this route when the created repair routes are <i>not</i> considered.
	 */
	public void setFFE(int FFE) {
		this.FFE = FFE;
	}

	/**
	 * @return The number of FFE using this route when the created repair routes are considered.
	 */
	public int getFFErep() {
		return FFErep;
	}

	/**
	 * @param FFErep - the number of FFE using this route when the created repair routes are considered.
	 */
	public void setFFErep(int FFErep) {
		this.FFErep = FFErep;
	}

	/**
	 * @param adjustFFErep - the number of FFE to adjust FFErep with. <b>Notice:</b> Use positive number to increase FFErep and negative number to decrease FFErep.
	 */
	public void adjustFFErep(int adjustFFErep){
		this.FFErep += adjustFFErep;
		if(FFErep == 0 && repair){
			deleteRoute();
		}
	}
	
	public int getFFEforRemoval(){
		return FFEforRemoval;
	}
	
	public void addFFEforRemoval(int FFEforRemoval){
		this.FFEforRemoval += FFEforRemoval;
	}
	
	public void implementFFEforRemoval(){
		adjustFFErep(-FFEforRemoval);
		this.FFEforRemoval = 0;
	}

	/** Removes all references to this route element from the associated edges and demand. Route element will then be garbage collected.
	 * 
	 */
	public void deleteRoute(){
		for(Edge e : route){
			e.removeRoute(this);
		}
		demand.removeRoute(this);
	}

	/**
	 * @param route - the edges used in the shortest path of this route element.
	 */
	public void setRoute(ArrayList<Edge> route){
		this.route = route;
	}

	/**
	 * @return The route used in the shortest path of this route element.
	 */
	public ArrayList<Edge> getRoute() {
		return route;
	}

	/**
	 * @return The demand that this route element is associated to.
	 */
	public Demand getDemand() {
		return demand;
	}

	/**
	 * @return Whether this is a repair route.
	 */
	public boolean isRepair() {
		return repair;
	}
	
	public boolean isOmission(){
		return omission;
	}

//	TODO: Lagrange profit is not updated with changing Lagrange values?
	/**
	 * @return The profit including Lagrange values, i.e. a value which is lower than the real profit.
	 */
	public int getLagrangeProfit() {
		return lagrangeProfit;
	}

	/**
	 * @param lagrangeProfit - the profit including Lagrange values.
	 */
	public void setLagrangeProfit(int lagrangeProfit) {
		this.lagrangeProfit = lagrangeProfit;
	}

	public void updateLagrangeProfit() {
		this.lagrangeProfit = realProfit;
		for(Edge e : route){
			realProfit -= e.getLagrange();
		}
	}

	/**
	 * @return The profit excluding Lagrange values.
	 */
	public int getRealProfit() {
		return realProfit;
	}

	public int getCost() {
		int cost = 0;
		for(Edge e : route){
			cost += e.getRealCost();
		}
		return cost;
	}

	public int getLagrangeCost() {
		int cost = 0;
		for(Edge e : route){
			cost += e.getCost();
		}
		return cost;
	}

	/**
	 * @param realProfit - the profit excluding Lagrange values.
	 */
	public void setRealProfit(int realProfit) {
		this.realProfit = realProfit;
	}
	
	public int findMaxUnderflow() {
		int maxUnderflow = Integer.MAX_VALUE;
		for(Edge e : route){
			int underflow = e.getCapacity() - e.getRepLoad();
			if(underflow < maxUnderflow){
				maxUnderflow = underflow;
			}
		}
		return maxUnderflow;
	}

	public String simplePrint(){
		String str = "Demand of " + FFErep + " from " + demand.getOrigin().getUNLocode() + " to " + 
				demand.getDestination().getUNLocode() + " uses route: \n";
		int counter = 1;
		boolean wasDwell = false;
		str += "Leg " + counter + ": ";
		for(Edge e : route){
			if(e.isSail() && !wasDwell){
				str += e.getFromPortUNLo() + "-" + e.getToPortUNLo();
			} else if(e.isSail() && wasDwell){
				str += "-" + e.getToPortUNLo();
				wasDwell = false;
			} else if(e.isDwell()){
				wasDwell = true;
			} else if(e.isTransshipment()){
				wasDwell = false;
				counter++;
				str += "\nLeg " + counter + ": ";
			} else if(e.isOmission()){
				str += e.getFromPortUNLo() + "-" + e.getToPortUNLo() + " via omission edge";
			} 
			counter++;
		}
		str += "\n";
		return str;
	}



}
