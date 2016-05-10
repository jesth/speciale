package Data;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class Data {
	private static HashMap<String, PortData> portsMap;
	private static PortData[] ports;
	private static Distance[][] distances;
	private static ArrayList<VesselClass> vesselClasses;
	private static double[][] randomNumbers;
	private static int portStay = 24;
	private static int fuelPrice = 600;

	public static void initialize(String vesselNoFileName, String randomNumbersFileName) throws FileNotFoundException{
		portsMap = ReadData.readPorts();
		convertPortsMap();
		distances = ReadData.readDistances(portsMap);
		vesselClasses = ReadData.readVesselClass(vesselNoFileName);
		randomNumbers = ReadData.readRandomNumbers(randomNumbersFileName);
	}

	private static void convertPortsMap(){
		ports = new PortData[portsMap.size()];
		for(PortData p : portsMap.values()){
			ports[p.getPortId()] = p;
		}
	}

	public static HashMap<String, PortData> getPortsMap() {
		return portsMap;
	}

	public static PortData[] getPorts(){
		return ports;
	}

	public static PortData getPort(int portId){
		return ports[portId];
	}

	public static Distance[][] getDistances() {
		return distances;
	}

	public static Distance getDistance(String port1UNLo, String port2UNLo){
		int portId1 = portsMap.get(port1UNLo).getPortId();
		int portId2 = portsMap.get(port2UNLo).getPortId();
		return getDistance(portId1, portId2);
	}

	public static Distance getDistance(int portId1, int portId2){
		if(portId1 == portId2){
			throw new RuntimeException("Trying to get dwell distance at port " + ports[portId1].getUNLocode());
		}
		return distances[portId1][portId2];
	}

	public static DistanceElement getBestDistanceElement(String port1UNLo, String port2UNLo, VesselClass vesselClass){
		Distance distance = getDistance(port1UNLo, port2UNLo);
		return distance.getBestDistanceElement(vesselClass);
	}

	public static DistanceElement getBestDistanceElement(int portId1, int portId2, VesselClass vesselClass){
		Distance distance = getDistance(portId1, portId2);
		return distance.getBestDistanceElement(vesselClass);
	}

	public static DistanceElement getBestDistanceElement(Port port1, Port port2, VesselClass vesselClass){
		Distance distance = getDistance(port1.getPortId(), port2.getPortId());
		return distance.getBestDistanceElement(vesselClass);
	}

	public static DistanceElement getBestDistanceElement(PortData port1, PortData port2, VesselClass vesselClass){
		Distance distance = getDistance(port1.getPortId(), port2.getPortId());
		return distance.getBestDistanceElement(vesselClass);
	}

	public static DistanceElement getDistanceElement(Port port1, Port port2, boolean suez, boolean panama){
		Distance distance = getDistance(port1.getPortId(), port2.getPortId());
		return distance.getDistanceElement(suez, panama);
	}

	public static ArrayList<VesselClass> getVesselClasses() {
		return vesselClasses;
	}
	
	public static VesselClass getVesselClassId(int id){
		for(VesselClass i : vesselClasses){
			if(i.getId() == id){
				return i;
			}
		}
		return null;
	}

	public static int getPortStay(){
		return portStay;
	}
	
	public static int getFuelPrice(){
		return fuelPrice;
	}
	
	public static double getRandomNumber(int row, int column){
		return randomNumbers[column][row];
	}
}
