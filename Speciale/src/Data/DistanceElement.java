package Data;

import java.io.Serializable;

public class DistanceElement implements Serializable{
	private static final long serialVersionUID = 1L;
	private Distance parent;
	private int distance;
	private double draft;
	private boolean suez;
	private boolean panama;
	
	public DistanceElement(Distance parent, boolean suez, boolean panama) {
		super();
		this.distance = Integer.MAX_VALUE;
		this.draft = -1;
		this.parent = parent;
		this.suez = suez;
		this.panama = panama;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void setDraft(double draft) {
		this.draft = draft;
	}

	public void setSuez(boolean suez) {
		this.suez = suez;
	}

	public void setPanama(boolean panama) {
		this.panama = panama;
	}

	public Distance getParent(){
		return parent;
	}
	
	public int getDistance(){
		return distance;
	}
	
	public double getDraft(){
		return draft;
	}
	
	public boolean isSuez(){
		return suez;
	}
	
	public boolean isPanama(){
		return panama;
	}
	
	public PortData getOrigin(){
		return parent.getOrigin();
	}
	
	public PortData getDestination(){
		return parent.getDestination();
	}
	
	public int getDesignSpeedCost(VesselClass vesselClass){
		double travelTimeDays = (distance / vesselClass.getDesignSpeed()) / 24.0;
		double TCCost = travelTimeDays * vesselClass.getTCRate();
		double fuelConsumptionCost = vesselClass.getFuelConsumptionDesign() * travelTimeDays * Data.getFuelPrice();
		double canalCost = 0;
		if(suez){
			canalCost += vesselClass.getSuezFee();
		}
		if(panama){
			canalCost += vesselClass.getPanamaFee();
		}
		int cost = (int) (TCCost + fuelConsumptionCost + canalCost);
		return cost;
	}
}
