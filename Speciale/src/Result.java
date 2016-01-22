import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Result extends Graph{
	private ArrayList<Rotation> rotations;

	public Result() throws FileNotFoundException{
		super();
	}
	
	public Result(ArrayList<Rotation> rotations) throws FileNotFoundException{
		super();
		this.rotations = rotations;
	}
	
	public void addRotation(Rotation rotation){
		this.rotations.add(rotation);
	}
	
	/**
	 * @return the rotations
	 */
	public ArrayList<Rotation> getRotations() {
		return rotations;
	}

	public int getObjectiveCost(){
		int objCost = 0;
		
		objCost = getFlowProfit();
		for(Rotation r : rotations){
			if(r.isActive()){
				VesselClass v = r.getVesselClass();
				ArrayList<Edge> rotationEdges = r.getRotationEdges();
				double sailingTime = 0;
				double idleTime = 0;
				
				for (Edge e : rotationEdges){
					if(e.isSail()){
						sailingTime += e.getTravelTime();
						if(e.isSuez()){
							objCost += v.getSuezFee();
						}
						if(e.isPanama()){
							objCost += v.getPanamaFee();
						}
					}
					if(e.isDwell()){
						idleTime += e.getTravelTime();
					}
				}
				//TODO USD per metric tons fuel = 600
				double sailingBunkerCost = (int) Math.ceil(sailingTime/24.0) * v.getFuelConsumptionDesign() * 600;
				double idleBunkerCost = (int) Math.ceil(idleTime/24.0) * v.getFuelConsumptionIdle() * 600;
						
				int rotationDays = (int) Math.ceil((sailingTime+idleTime)/24.0);
				objCost += rotationDays * v.getTCRate();
				objCost += sailingBunkerCost + idleBunkerCost;
			}
		}
		
		return objCost;
	}
	
	public int getFlowProfit(){
		int flowProfit = 0;
		
		int flowCost = 0;
		for (Edge e : super.getEdges()){		
			flowCost += e.getRealCost() * e.getLoad();
		}
		int flowRevenue = 0;
		for (Demand d : super.getData().getDemands()){
			flowRevenue += d.getDemand() * d.getRate();
		}
		flowProfit = flowRevenue - flowCost;
		
		return flowProfit;
	}
	
}
