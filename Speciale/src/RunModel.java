import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import AuxFlow.AuxDijkstra;
import AuxFlow.AuxEdge;
import AuxFlow.AuxGraph;
import AuxFlow.AuxRun;
import Data.Data;
import Data.DistanceElement;
import Data.Port;
import Data.PortData;
import Data.VesselClass;
import Graph.Edge;
import Graph.Graph;
import Graph.Node;
import Methods.ComputeRotations;
import Methods.LNS;
//import Methods.MulticommodityFlow;
import Methods.MulticommodityFlowThreads;
import Results.Result;
import Results.Rotation;
import RotationFlow.RotationGraph;

public class RunModel {
	public static void main(String[] args) throws InterruptedException, IOException {
//		testMed();
//		saveAux();
//		testAux();

//		testMedManual2();
//		testWorldLargeAuto();

		testLNS("Baltic");
		
		
//		testLNS("Pacific");
		
//		testCode("WorldSmall", 3);

//		testBrouerCases();
		
		
//		Data.initialize("fleet_WAF.csv", "randomNumbers.csv", 1, 1);
//		Graph network = new Graph("Demand_WAF.csv");
//		network.copyRotations("Network.csv", "NetworkCost.csv");
//		network.runMcf();
//		network.printNetworkFacts("sailArcs.csv", "dwellArcs.csv");
//		System.out.println(network.getResult().getFlowProfit(false));
		
//		network.getResult().saveLoads("testLoads", 100);
//		network.getResult().saveLagranges("testLagranges", 100);
//		network.getResult().saveRotationSol("test.csv");
		
//		network.getResult().saveAllEdgesSol("AllEdgesSol.csv");
//		network.getResult().saveODSol("ODSol.csv");
//		network.getResult().saveRotationSol("RotationSol.csv");
//		network.getResult().saveTransferSol("TransferSol.csv");
//		network.getResult().saveFlowCost("FlowCost.csv");
//		network.getResult().saveRotationCost("RotationCost.csv");
//		network.getResult().saveOPLData("OPLData.dat");
//		network.saveJson("bestSolWS.GeoJSON", 10);
//		System.out.println(network.getResult().getObjective());

	}
	
	public static void testLNS(String caseName) throws InterruptedException, IOException{
		String fleetFileName = getFleetFileName(caseName);
		String demandFileName = getDemandFileName(caseName);
		Data.initialize(fleetFileName, "randomNumbers.csv", 1, 1);
		LNS lns = new LNS();
		for(int i=1; i<4; i++){
			lns.VNSrun(3600, 20, caseName, i);	
		}
//		lns.testInsertion(150, 20, fleetFileName, demandFileName);
	}
	
	public static void testCode(String caseName, int runs) throws InterruptedException, IOException{
		String fleetFileName = getFleetFileName(caseName);
		String demandFileName = getDemandFileName(caseName);
		for(int i = 0; i < runs; i++){
			LNS lns = new LNS();
			lns.run(600, 20, fleetFileName, demandFileName, i);
		}
	}
	
	private static String getFleetFileName(String caseName){
		if(caseName.equals("WorldSmall")){
			return "fleet_WorldSmall.csv";
		} else if(caseName.equals("WorldLarge")){
			return "fleet_WorldLarge.csv";
		} else if(caseName.equals("Mediterranean")){
			return "fleet_Mediterranean.csv";
		} else if(caseName.equals("Baltic")){
			return "fleet_Baltic.csv";
		} else if(caseName.equals("EuropeAsia")){
			return "fleet_EuropeAsia.csv";
		} else if(caseName.equals("Pacific")){
			return "fleet_Pacific.csv";
		} else if(caseName.equals("WAF")){
			return "fleet_WAF.csv";
		} else if(true){
			throw new RuntimeException("Wrong case name given.");
		}
		return "";
	}
	
	private static String getDemandFileName(String caseName){
		if(caseName.equals("WorldSmall")){
			return "Demand_WorldSmall.csv";
		} else if(caseName.equals("WorldLarge")){
			return "Demand_WorldLarge.csv";
		} else if(caseName.equals("Mediterranean")){
			return "Demand_Mediterranean.csv";
		} else if(caseName.equals("Baltic")){
			return "Demand_Baltic.csv";
		} else if(caseName.equals("EuropeAsia")){
			return "Demand_EuropeAsia.csv";
		} else if(caseName.equals("Pacific")){
			return "Demand_Pacific.csv";
		} else if(caseName.equals("WAF")){
			return "Demand_WAF.csv";
		} else if(true){
			throw new RuntimeException("Wrong case name given.");
		}
		return "";
	}
	
	public static void saveAux() throws FileNotFoundException{
		Data.initialize("fleet_WorldLarge.csv", "RandomNumbers.csv", 1, 1);
		Graph testGraph = new Graph("Demand_WorldLarge.csv");
		AuxRun auxRun = new AuxRun(testGraph, 1, 0);
		auxRun.run();
		System.out.println("SaveAux is done");
	}
	
	private static void testBrouerCases() throws FileNotFoundException, InterruptedException {

//		testBrouerNetwork("WorldSmall_Best_Base.log", "fleet_WorldSmall.csv",
//				"RandomNumbers.csv", "Demand_WorldSmall.csv", 1.5, 1.5);
//		testBrouerNetwork("WorldSmall_high_best_12848_1.log", "fleet_WorldSmall.csv",
//				"RandomNumbers.csv", "Demand_WorldSmall.csv", 1.5, 1.5);
//		testBrouerNetwork("WorldSmall_pid_4953_15_best_low.log", "fleet_WorldSmall.csv",
//				"RandomNumbers.csv", "Demand_WorldSmall.csv", 1.5, 1.5);
		
//		testBrouerNetwork("Pacific_base_best.log", "fleet_Pacific.csv",
//				"RandomNumbers.csv", "Demand_Pacific.csv", 1.5, 1.5);
//		testBrouerNetwork("Pacific_high_best_30600_4.log", "fleet_Pacific.csv",
//				"RandomNumbers.csv", "Demand_Pacific.csv", 1.5, 1.5);
//		testBrouerNetwork("Pacific_28043_5_low_best.log", "fleet_Pacific.csv",
//				"RandomNumbers.csv", "Demand_Pacific.csv", 1.5, 1.5);
		
		
//		testBrouerNetwork("Med_base_best.log", "fleet_Mediterranean.csv",
//				"RandomNumbers.csv", "Demand_Mediterranean.csv", 1.5, 1.5);
//		testBrouerNetwork("Med_high_best_5840_1.log", "fleet_Mediterranean.csv",
//				"RandomNumbers.csv", "Demand_Mediterranean.csv", 1.5, 1.5);
//		testBrouerNetwork("Med_low_best_12808_11.log", "fleet_Mediterranean.csv",
//				"RandomNumbers.csv", "Demand_Mediterranean.csv", 1.5, 1.5);
	}
	
	public static void testBrouerNetwork(String resultFile, String fleetFile, String randomFile, String demandFile,
			double factorTC, double factorCapacity) throws FileNotFoundException, InterruptedException{
		Data.initialize(fleetFile, randomFile, factorTC, factorCapacity);
		Graph network = new Graph(demandFile);
		ArrayList<Integer> rotation = new ArrayList<Integer>();
		HashMap<String, PortData> ports = Data.getPortsMap();
		
		File solInput = new File("BrouerResultCases\\" + resultFile);
		Scanner scanner = new Scanner(solInput);
		scanner.useDelimiter("\t|\n|;| ");
		scanner.nextLine();
		
		String vesselCapStr = "";
		String vesselNoStr = "";
		VesselClass v = null;
		int vesselNo = -1;
		String portStr = "";
		while(scanner.hasNextLine()){
			String textIn = scanner.next();
			if(textIn.equals("capacity")){
				vesselCapStr = scanner.next();
				v = Data.getVesselClassFromCap(Integer.parseInt(vesselCapStr));
				scanner.nextLine();
				scanner.next();
				scanner.next();
				vesselNoStr = scanner.next();
				vesselNo = Integer.parseInt(vesselNoStr);
				scanner.nextLine();
				if(!scanner.hasNextInt()){
					scanner.nextLine();
				}
				while(scanner.hasNextInt()){
					scanner.next();
					portStr = scanner.next();
					rotation.add(ports.get(portStr).getPortId());
					scanner.nextLine();
				}
//				System.out.println("vesselCapStr: " + vesselCapStr);
//				System.out.println("Brouer #vessels: " + vesselNo);
				Rotation r = network.createRotationFromPortsWithSetNoVessels(rotation, v, vesselNo);
//				System.out.println("# vessels: " + r.getNoOfVessels());
//				for(int i : rotation){
//					System.out.println(Data.getPort(i).getUNLocode());
//				}
//				System.out.println();
				rotation.clear();
				v = null;
			}
			scanner.nextLine();
		}
		scanner.close();
		
		network.runMcf();
		System.out.println(network.getResult().getFlowProfit(false));
	}
	
	
	/* Out-of-date method.
	public static void testAux() throws FileNotFoundException, InterruptedException{
		Data.initialize("fleet_Mediterranean.csv", "LinerLib_Data\\RandomNumbers.csv");
		Graph graph = new Graph("Demand_Mediterranean.csv");
		initialize(graph);
		ArrayList<AuxEdge> sortedEdges = AuxGraph.getSortedAuxEdges();
		VesselClass feeder450 = Data.getVesselClasses().get(0);
		VesselClass feeder800 = Data.getVesselClasses().get(1);
		VesselClass panamax1200 = Data.getVesselClasses().get(2);
		Rotation r = ComputeRotations.createAuxFlowRotation(4, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(3, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder450);
		ComputeRotations.createAuxFlowRotation(3, sortedEdges, feeder450);
		
//		ArrayList<String> rotationPorts = new ArrayList<String>(r.getPorts().size());
//		for(Port p : r.getPorts()){
//			rotationPorts.add(p.getName());
//		}
//		RuneVisualization.makeVisualization(rotationPorts, "beforeOptimization");
		Rotation optRotation = graph.getResult().getRotations().get(0);
		Edge remove = optRotation.getRotationEdges().get(14);
		graph.removePort(optRotation, remove);
//		ComputeRotations.addPorts();
		graph.getMcf().run();
//		ComputeRotations.removePorts();
//		MulticommodityFlow.run();
//		ComputeRotations.removePorts();
		graph.getMcf().run();	
//		ComputeRotations.addPorts();
		
//		rotationPorts = new ArrayList<String>(r.getPorts().size());
//		for(Port p : r.getPorts()){
//			rotationPorts.add(p.getName());
//		}
//		RuneVisualization.makeVisualization(rotationPorts, "afterOptimization");
		
		graph.getMcf().run();
		
		graph.getResult().saveODSol("ODSol.csv");
		graph.getResult().saveRotationSol("RotationSol.csv");
		System.out.println();
		System.out.println("Objective " + graph.getResult().getObjective());
		System.out.println("Flow profit " + graph.getResult().getFlowProfit(false));
		
		graph.getResult().saveOPLData("OPLData.dat");
	}
	*/
	
	/*
	public static void testAutomatic() throws FileNotFoundException{
		Graph testGraph = new Graph("Demand_Mediterranean.csv", "fleet_Mediterranean.csv");
		initialize(testGraph);
		MulticommodityFlow.run();
		Rotation r1 = ComputeRotations.createLargestLossRotation();
		System.out.println("Rotation created");
		long time = System.currentTimeMillis();
		MulticommodityFlow.run();
		int counter = 0;
		while(r1.getNoOfVessels() < 6){
			ComputeRotations.insertBestPort(r1);
			MulticommodityFlow.run();
			counter++;
		}
		System.out.println("Did " + counter + " port inserts.");
		Rotation r2 = ComputeRotations.createLargestLossRotation();
//		MulticommodityFlow.run();
//		ComputeRotations.insertBestPort(r1);
//		MulticommodityFlow.run();
//		ComputeRotations.insertBestPort(r1);
//		MulticommodityFlow.run();
		long timeUse = System.currentTimeMillis() - time;
		System.out.println("Running for " + timeUse + " ms");
		MulticommodityFlow.saveODSol("test.csv", Data.getDemands());
		System.out.println(r1);
		System.out.println(r2);
	}
	*/
	
	public static void testBalticManual() throws FileNotFoundException, InterruptedException{
		Data.initialize("fleet_Baltic.csv", "LinerLib_Data\\RandomNumbers.csv", 1, 1);
		Graph testGraph = new Graph("Demand_Baltic.csv");
		VesselClass vesselClass = Data.getVesselClasses().get(1);
		ArrayList<DistanceElement> distances = new ArrayList<DistanceElement>();
		DistanceElement leg1 = Data.getBestDistanceElement("DEBRV", "RULED", vesselClass);
		DistanceElement leg2 = Data.getBestDistanceElement("RULED", "DEBRV", vesselClass);
		DistanceElement leg3 = Data.getBestDistanceElement("DEBRV", "NOSVG", vesselClass);
		DistanceElement leg4 = Data.getBestDistanceElement("NOSVG", "SEGOT", vesselClass);
		DistanceElement leg5 = Data.getBestDistanceElement("SEGOT", "DEBRV", vesselClass);
		distances.add(leg1);
		distances.add(leg2);
		distances.add(leg3);
		distances.add(leg4);
		distances.add(leg5);
		Rotation r = testGraph.createRotation(distances, vesselClass);
		
		vesselClass = Data.getVesselClasses().get(0);
		ArrayList<DistanceElement> distances2 = new ArrayList<DistanceElement>();
		DistanceElement leg10 = Data.getBestDistanceElement("DEBRV", "DKAAR", vesselClass);
		DistanceElement leg11 = Data.getBestDistanceElement("DKAAR", "DEBRV", vesselClass);
		DistanceElement leg12 = Data.getBestDistanceElement("DEBRV", "RUKGD", vesselClass);
		DistanceElement leg13 = Data.getBestDistanceElement("RUKGD", "FIKTK", vesselClass);
		DistanceElement leg14 = Data.getBestDistanceElement("FIKTK", "DEBRV", vesselClass);
		DistanceElement leg15 = Data.getBestDistanceElement("DEBRV", "RULED", vesselClass);
		DistanceElement leg16 = Data.getBestDistanceElement("RULED", "PLGDY", vesselClass);
		DistanceElement leg17 = Data.getBestDistanceElement("PLGDY", "DEBRV", vesselClass);
		distances2.add(leg10);
		distances2.add(leg11);
		distances2.add(leg12);
		distances2.add(leg13);
		distances2.add(leg14);
		distances2.add(leg15);
		distances2.add(leg16);
		distances2.add(leg17);
		Rotation r2 = testGraph.createRotation(distances2, vesselClass);
		
//		vesselClass = Data.getVesselClasses().get(0);
//		ArrayList<DistanceElement> distances3 = new ArrayList<DistanceElement>();
//		DistanceElement leg20 = Data.getDistanceElement("DEBRV", "DKAAR", vesselClass);
//		DistanceElement leg21 = Data.getDistanceElement("DKAAR", "DEBRV", vesselClass);
//		distances3.add(leg20);
//		distances3.add(leg21);
//		Rotation r3 = testGraph.createRotation(distances3, vesselClass);
		
//		Result.addRotation(r3);
		long time = System.currentTimeMillis();
		testGraph.runMcf();
		long timeUse = System.currentTimeMillis() - time;
		System.out.println("Running for " + timeUse + " ms");
		testGraph.getResult().saveODSol("ODSol.csv");
		testGraph.getResult().saveRotationSol("RotationSol.csv");
		System.out.println();
		System.out.println("Objective " + testGraph.getResult().getObjective());
		System.out.println("Flow profit " + testGraph.getResult().getFlowProfit(false));
		
		testGraph.getResult().saveOPLData("OPLData.dat");
	}
	
	public static void testBaltic() throws FileNotFoundException, InterruptedException{
		Data.initialize("fleet_Baltic.csv", "LinerLib_Data\\RandomNumbers.csv", 1, 1);
		Graph testGraph = new Graph("Demand_Baltic.csv");
		VesselClass vesselClass = Data.getVesselClasses().get(0);
		ArrayList<DistanceElement> distances = new ArrayList<DistanceElement>();
		DistanceElement leg1 = Data.getBestDistanceElement("RULED", "FIKTK", vesselClass);
		DistanceElement leg2 = Data.getBestDistanceElement("FIKTK", "DEBRV", vesselClass);
		DistanceElement leg3 = Data.getBestDistanceElement("DEBRV", "RUKGD", vesselClass);
		DistanceElement leg4 = Data.getBestDistanceElement("RUKGD", "PLGDY", vesselClass);
		DistanceElement leg5 = Data.getBestDistanceElement("PLGDY", "DEBRV", vesselClass);
		DistanceElement leg6 = Data.getBestDistanceElement("DEBRV", "RULED", vesselClass);
		
		distances.add(leg1);
		distances.add(leg2);
		distances.add(leg3);
		distances.add(leg4);
		distances.add(leg5);
		distances.add(leg6);
		Rotation r = testGraph.createRotation(distances, vesselClass);
		
		vesselClass = Data.getVesselClasses().get(1);
		ArrayList<DistanceElement> distances2 = new ArrayList<DistanceElement>();
		DistanceElement leg10 = Data.getBestDistanceElement("RULED", "DEBRV", vesselClass);
		DistanceElement leg11 = Data.getBestDistanceElement("DEBRV", "NOSVG", vesselClass);
		DistanceElement leg12 = Data.getBestDistanceElement("NOSVG", "SEGOT", vesselClass);
		DistanceElement leg13 = Data.getBestDistanceElement("SEGOT", "DEBRV", vesselClass);
		DistanceElement leg14 = Data.getBestDistanceElement("DEBRV", "RULED", vesselClass);
		distances2.add(leg10);
		distances2.add(leg11);
		distances2.add(leg12);
		distances2.add(leg13);
		distances2.add(leg14);
		Rotation r2 = testGraph.createRotation(distances2, vesselClass);
		
		vesselClass = Data.getVesselClasses().get(0);
		ArrayList<DistanceElement> distances3 = new ArrayList<DistanceElement>();
		DistanceElement leg20 = Data.getBestDistanceElement("DEBRV", "DKAAR", vesselClass);
		DistanceElement leg21 = Data.getBestDistanceElement("DKAAR", "DEBRV", vesselClass);
		distances3.add(leg20);
		distances3.add(leg21);
		Rotation r3 = testGraph.createRotation(distances3, vesselClass);
		
		long time = System.currentTimeMillis();
		testGraph.runMcf();
		long timeUse = System.currentTimeMillis() - time;
		System.out.println("Running for " + timeUse + " ms");
		testGraph.getResult().saveODSol("ODSol.csv");
		testGraph.getResult().saveRotationSol("RotationSol.csv");
		System.out.println();
		System.out.println("Objective " + testGraph.getResult().getObjective());
		System.out.println("Flow profit " + testGraph.getResult().getFlowProfit(false));
		
		testGraph.getResult().saveOPLData("OPLData.dat");
	}
	
	public static void testMedManual() throws FileNotFoundException, InterruptedException{
		Data.initialize("fleet_Mediterranean.csv", "LinerLib_Data\\RandomNumbers.csv", 1, 1);
		Graph testGraph = new Graph("Demand_Mediterranean.csv");
		VesselClass vesselClass = Data.getVesselClasses().get(2);
		ArrayList<DistanceElement> distances = new ArrayList<DistanceElement>();
		DistanceElement leg1 = Data.getBestDistanceElement("TRAMB", "ESALG", vesselClass);
		DistanceElement leg2 = Data.getBestDistanceElement("ESALG", "MAPTM", vesselClass);
		DistanceElement leg4 = Data.getBestDistanceElement("MAPTM", "EGPSD", vesselClass);
		DistanceElement leg5 = Data.getBestDistanceElement("EGPSD", "ITGIT", vesselClass);
		DistanceElement leg6 = Data.getBestDistanceElement("ITGIT", "EGALY", vesselClass);
		DistanceElement leg7 = Data.getBestDistanceElement("EGALY", "TRAMB", vesselClass);
		distances.add(leg1);
		distances.add(leg2);
		distances.add(leg4);
		distances.add(leg5);
		distances.add(leg6);
		distances.add(leg7);
		Rotation r = testGraph.createRotation(distances, vesselClass);
		
		vesselClass = Data.getVesselClasses().get(1);
		ArrayList<DistanceElement> distances2 = new ArrayList<DistanceElement>();
		DistanceElement leg10 = Data.getBestDistanceElement("EGPSD", "ILASH", vesselClass);
		DistanceElement leg11 = Data.getBestDistanceElement("ILASH", "LBBEY", vesselClass);
		DistanceElement leg12 = Data.getBestDistanceElement("LBBEY", "TRMER", vesselClass);
		DistanceElement leg52 = Data.getBestDistanceElement("TRMER", "ITGIT", vesselClass);
		DistanceElement leg53 = Data.getBestDistanceElement("ITGIT", "ITSAL", vesselClass);
		DistanceElement leg54 = Data.getBestDistanceElement("ITSAL", "ESBCN", vesselClass);
		DistanceElement leg55 = Data.getBestDistanceElement("ESBCN", "ESAGP", vesselClass);
		DistanceElement leg56 = Data.getBestDistanceElement("ESAGP", "ESALG", vesselClass);
		DistanceElement leg57 = Data.getBestDistanceElement("ESALG", "GRSKG", vesselClass);
		DistanceElement leg58 = Data.getBestDistanceElement("GRSKG", "TRAMB", vesselClass);
		DistanceElement leg59 = Data.getBestDistanceElement("TRAMB", "TRMER", vesselClass);
		DistanceElement leg13 = Data.getBestDistanceElement("TRMER", "ILHFA", vesselClass);
//		DistanceElement leg14 = Data.getDistanceElement("SYLTK", "ILHFA", vesselClass);
		DistanceElement leg15 = Data.getBestDistanceElement("ILHFA", "EGPSD", vesselClass);
		distances2.add(leg10);
		distances2.add(leg11);
		distances2.add(leg12);
		distances2.add(leg52);
		distances2.add(leg53);
		distances2.add(leg54);
		distances2.add(leg55);
		distances2.add(leg56);
		distances2.add(leg57);
		distances2.add(leg58);
		distances2.add(leg59);
		distances2.add(leg13);
//		distances2.add(leg14);
		distances2.add(leg15);
		Rotation r2 = testGraph.createRotation(distances2, vesselClass);
		
		vesselClass = Data.getVesselClasses().get(0);
		ArrayList<DistanceElement> distances3 = new ArrayList<DistanceElement>();
		DistanceElement leg20 = Data.getBestDistanceElement("TRAMB", "GEPTI", vesselClass);
		DistanceElement leg21 = Data.getBestDistanceElement("GEPTI", "UAODS", vesselClass);
		DistanceElement leg22 = Data.getBestDistanceElement("UAODS", "BGVAR", vesselClass);
		DistanceElement leg23 = Data.getBestDistanceElement("BGVAR", "TRAMB", vesselClass);
		DistanceElement leg24 = Data.getBestDistanceElement("TRAMB", "TRIZM", vesselClass);
		DistanceElement leg25 = Data.getBestDistanceElement("TRIZM", "TRAMB", vesselClass);
		distances3.add(leg20);
		distances3.add(leg21);
		distances3.add(leg22);
		distances3.add(leg23);
		distances3.add(leg24);
		distances3.add(leg25);
		Rotation r3 = testGraph.createRotation(distances3, vesselClass);
		
		vesselClass = Data.getVesselClasses().get(1);
		ArrayList<DistanceElement> distances4 = new ArrayList<DistanceElement>();
		DistanceElement leg30 = Data.getBestDistanceElement("ESALG", "MACAS", vesselClass);
		DistanceElement leg32 = Data.getBestDistanceElement("MACAS", "MAPTM", vesselClass);
		DistanceElement leg33 = Data.getBestDistanceElement("MAPTM", "ESALG", vesselClass);
		DistanceElement leg34 = Data.getBestDistanceElement("ESALG", "ITGOA", vesselClass);
		DistanceElement leg35 = Data.getBestDistanceElement("ITGOA", "ITGIT", vesselClass);
		DistanceElement leg36 = Data.getBestDistanceElement("ITGIT", "ESALG", vesselClass);
		distances4.add(leg30);
		distances4.add(leg32);
		distances4.add(leg33);
		distances4.add(leg34);
		distances4.add(leg35);
		distances4.add(leg36);
		Rotation r4 = testGraph.createRotation(distances4, vesselClass);
		
		vesselClass = Data.getVesselClasses().get(0);
				ArrayList<DistanceElement> distances5 = new ArrayList<DistanceElement>();
		DistanceElement leg40 = Data.getBestDistanceElement("MAPTM", "MAAGA", vesselClass);
		DistanceElement leg41 = Data.getBestDistanceElement("MAAGA", "PTLEI", vesselClass);
		DistanceElement leg42 = Data.getBestDistanceElement("PTLEI", "ESALG", vesselClass);
		DistanceElement leg43 = Data.getBestDistanceElement("ESALG", "TNTUN", vesselClass);
		DistanceElement leg44 = Data.getBestDistanceElement("TNTUN", "ITGIT", vesselClass);
		DistanceElement leg45 = Data.getBestDistanceElement("ITGIT", "EGPSD", vesselClass);
		DistanceElement leg46 = Data.getBestDistanceElement("EGPSD", "DZALG", vesselClass);
		DistanceElement leg47 = Data.getBestDistanceElement("DZALG", "MAPTM", vesselClass);
		distances5.add(leg40);
		distances5.add(leg41);
		distances5.add(leg42);
		distances5.add(leg43);
		distances5.add(leg44);
		distances5.add(leg45);
		distances5.add(leg46);
		distances5.add(leg47);
		Rotation r5 = testGraph.createRotation(distances5, vesselClass);

		
		long time = System.currentTimeMillis();
		testGraph.runMcf();
		long timeUse = System.currentTimeMillis() - time;
		System.out.println("Running for " + timeUse + " ms");
		testGraph.getResult().saveODSol("ODSol.csv");
		testGraph.getResult().saveRotationSol("RotationSol.csv");
		System.out.println();
		System.out.println("Objective " + testGraph.getResult().getObjective());
		System.out.println("Flow profit " + testGraph.getResult().getFlowProfit(false));
		
		testGraph.getResult().saveOPLData("OPLData.dat");
	}
	
	public static void testMedManual2() throws FileNotFoundException, InterruptedException{
		Data.initialize("fleet_Mediterranean.csv", "LinerLib_Data\\RandomNumbers.csv", 1, 1);
		Graph testGraph = new Graph("Demand_Mediterranean.csv");
		VesselClass vesselClass = Data.getVesselClasses().get(2);
		ArrayList<DistanceElement> distances = new ArrayList<DistanceElement>();
		DistanceElement leg1 = Data.getBestDistanceElement("EGPSD", "ESALG", vesselClass);
		DistanceElement leg8 = Data.getBestDistanceElement("ESALG", "MACAS", vesselClass);
		DistanceElement leg2 = Data.getBestDistanceElement("MACAS", "MAAGA", vesselClass);
		DistanceElement leg7 = Data.getBestDistanceElement("MAAGA", "PTLEI", vesselClass);
		DistanceElement leg3 = Data.getBestDistanceElement("PTLEI", "MAPTM", vesselClass);
		DistanceElement leg4 = Data.getBestDistanceElement("MAPTM", "ESALG", vesselClass);
		DistanceElement leg5 = Data.getBestDistanceElement("ESALG", "EGALY", vesselClass);
		DistanceElement leg6 = Data.getBestDistanceElement("EGALY", "EGPSD", vesselClass);
		distances.add(leg1);
		distances.add(leg8);
		distances.add(leg2);
		distances.add(leg7);
		distances.add(leg3);
		distances.add(leg4);
		distances.add(leg5);
		distances.add(leg6);
		Rotation r = testGraph.createRotation(distances, vesselClass);
		
		vesselClass = Data.getVesselClasses().get(1);
		ArrayList<DistanceElement> distances2 = new ArrayList<DistanceElement>();
		DistanceElement leg10 = Data.getBestDistanceElement("ESALG", "MAPTM", vesselClass);
		DistanceElement leg19 = Data.getBestDistanceElement("MAPTM", "DZALG", vesselClass);
		DistanceElement leg11 = Data.getBestDistanceElement("DZALG", "TNTUN", vesselClass);
		DistanceElement leg12 = Data.getBestDistanceElement("TNTUN", "ITGIT", vesselClass);
		DistanceElement leg13 = Data.getBestDistanceElement("ITGIT", "EGPSD", vesselClass);
		DistanceElement leg14 = Data.getBestDistanceElement("EGPSD", "EGALY", vesselClass);
		DistanceElement leg15 = Data.getBestDistanceElement("EGALY", "GRPIR", vesselClass);
		DistanceElement leg16 = Data.getBestDistanceElement("GRPIR", "ITGIT", vesselClass);
//		DistanceElement leg17 = Data.getDistanceElement("ITGIT", "ITGOA", vesselClass);
		DistanceElement leg18 = Data.getBestDistanceElement("ITGIT", "ESBCN", vesselClass);
		DistanceElement leg20 = Data.getBestDistanceElement("ESBCN", "ESALG", vesselClass);
		distances2.add(leg10);
		distances2.add(leg19);
		distances2.add(leg11);
		distances2.add(leg12);
		distances2.add(leg13);
		distances2.add(leg14);
		distances2.add(leg15);
		distances2.add(leg16);
//		distances2.add(leg17);
		distances2.add(leg18);
		distances2.add(leg20);
		Rotation r2 = testGraph.createRotation(distances2, vesselClass);
		
		
		vesselClass = Data.getVesselClasses().get(1);
		ArrayList<DistanceElement> distances3 = new ArrayList<DistanceElement>();
		DistanceElement leg30 = Data.getBestDistanceElement("TRAMB", "EGPSD", vesselClass);
		DistanceElement leg32 = Data.getBestDistanceElement("EGPSD", "ILASH", vesselClass);
		DistanceElement leg33 = Data.getBestDistanceElement("ILASH", "ILHFA", vesselClass);
		DistanceElement leg34 = Data.getBestDistanceElement("ILHFA", "EGPSD", vesselClass);
		DistanceElement leg35 = Data.getBestDistanceElement("EGPSD", "TRAMB", vesselClass);
		distances3.add(leg30);
		distances3.add(leg32);
		distances3.add(leg33);
		distances3.add(leg34);
		distances3.add(leg35);
		Rotation r3 = testGraph.createRotation(distances3, vesselClass);
		
		vesselClass = Data.getVesselClasses().get(0);
		ArrayList<DistanceElement> distances4 = new ArrayList<DistanceElement>();
		DistanceElement leg40 = Data.getBestDistanceElement("ESALG", "TNTUN", vesselClass);
		DistanceElement leg41 = Data.getBestDistanceElement("TNTUN", "ITGIT", vesselClass);
		DistanceElement leg42 = Data.getBestDistanceElement("ITGIT", "ITSAL", vesselClass);
		DistanceElement leg45 = Data.getBestDistanceElement("ITSAL", "ESVLC", vesselClass);
		DistanceElement leg43 = Data.getBestDistanceElement("ESVLC", "ESAGP", vesselClass);
		DistanceElement leg44 = Data.getBestDistanceElement("ESAGP", "ESALG", vesselClass);
		distances4.add(leg40);
		distances4.add(leg41);
		distances4.add(leg42);
		distances4.add(leg45);
		distances4.add(leg43);
		distances4.add(leg44);
		Rotation r4 = testGraph.createRotation(distances4, vesselClass);
		
		vesselClass = Data.getVesselClasses().get(0);
				ArrayList<DistanceElement> distances5 = new ArrayList<DistanceElement>();
		DistanceElement leg50 = Data.getBestDistanceElement("ESALG", "ESAGP", vesselClass);
		DistanceElement leg51 = Data.getBestDistanceElement("ESAGP", "DZORN", vesselClass);
		DistanceElement leg53 = Data.getBestDistanceElement("DZORN", "ITGIT", vesselClass);
		DistanceElement leg54 = Data.getBestDistanceElement("ITGIT", "EGPSD", vesselClass);
		DistanceElement leg55 = Data.getBestDistanceElement("EGPSD", "LBBEY", vesselClass);
		DistanceElement leg56 = Data.getBestDistanceElement("LBBEY", "TRMER", vesselClass);
		DistanceElement leg57 = Data.getBestDistanceElement("TRMER", "EGPSD", vesselClass);
		DistanceElement leg59 = Data.getBestDistanceElement("EGPSD", "ESALG", vesselClass);
		distances5.add(leg50);
		distances5.add(leg51);
		distances5.add(leg53);
		distances5.add(leg54);
		distances5.add(leg55);
		distances5.add(leg56);
		distances5.add(leg57);
		distances5.add(leg59);
		Rotation r5 = testGraph.createRotation(distances5, vesselClass);
		
		
		vesselClass = Data.getVesselClasses().get(0);
		ArrayList<DistanceElement> distances6 = new ArrayList<DistanceElement>();
		DistanceElement leg60 = Data.getBestDistanceElement("TRAMB", "GEPTI", vesselClass);
		DistanceElement leg61 = Data.getBestDistanceElement("GEPTI", "UAODS", vesselClass);
		DistanceElement leg62 = Data.getBestDistanceElement("UAODS", "BGVAR", vesselClass);
		DistanceElement leg63 = Data.getBestDistanceElement("BGVAR", "TRAMB", vesselClass);
		DistanceElement leg64 = Data.getBestDistanceElement("TRAMB", "TRIZM", vesselClass);
		DistanceElement leg65 = Data.getBestDistanceElement("TRIZM", "TRAMB", vesselClass);
		distances6.add(leg60);
		distances6.add(leg61);
		distances6.add(leg62);
		distances6.add(leg63);
		distances6.add(leg64);
		distances6.add(leg65);
		Rotation r6 = testGraph.createRotation(distances6, vesselClass);
		
		
		vesselClass = Data.getVesselClasses().get(1);
		ArrayList<DistanceElement> distances7 = new ArrayList<DistanceElement>();
		DistanceElement leg70 = Data.getBestDistanceElement("ESALG", "ESTAR", vesselClass);
		DistanceElement leg71 = Data.getBestDistanceElement("ESTAR", "ITGOA", vesselClass);
		DistanceElement leg72 = Data.getBestDistanceElement("ITGOA", "ITGIT", vesselClass);
		DistanceElement leg73 = Data.getBestDistanceElement("ITGIT", "MAPTM", vesselClass);
		DistanceElement leg74 = Data.getBestDistanceElement("MAPTM", "ESALG", vesselClass);
		distances7.add(leg70);
		distances7.add(leg71);
		distances7.add(leg72);
		distances7.add(leg73);
		distances7.add(leg74);
		Rotation r7 = testGraph.createRotation(distances7, vesselClass);
		
		long time = System.currentTimeMillis();
		testGraph.runMcf();
		long timeUse = System.currentTimeMillis() - time;
		System.out.println("Running for " + timeUse + " ms");
		testGraph.getResult().saveODSol("ODSol.csv");
		testGraph.getResult().saveRotationSol("RotationSol.csv");
		System.out.println();
		System.out.println("Objective " + testGraph.getResult().getObjective());
		System.out.println("Flow profit " + testGraph.getResult().getFlowProfit(false));
		
		testGraph.getResult().saveOPLData("OPLData.dat");
	}

	
	public static void testMed() throws FileNotFoundException, InterruptedException{
		Data.initialize("fleet_Mediterranean.csv", "LinerLib_Data\\RandomNumbers.csv", 1, 1);
		Graph testGraph = new Graph("Demand_Mediterranean.csv");
		VesselClass vesselClass = Data.getVesselClasses().get(1);
		ArrayList<DistanceElement> distances = new ArrayList<DistanceElement>();
		DistanceElement leg1 = Data.getBestDistanceElement("MACAS", "MAAGA", vesselClass);
		DistanceElement leg2 = Data.getBestDistanceElement("MAAGA", "ESVGO", vesselClass);
		DistanceElement leg3 = Data.getBestDistanceElement("ESVGO", "PTLEI", vesselClass);
		DistanceElement leg4 = Data.getBestDistanceElement("PTLEI", "MAPTM", vesselClass);
		DistanceElement leg5 = Data.getBestDistanceElement("MAPTM", "ESALG", vesselClass);
		DistanceElement leg6 = Data.getBestDistanceElement("ESALG", "ESAGP", vesselClass);
		DistanceElement leg7 = Data.getBestDistanceElement("ESAGP", "DZBJA", vesselClass);
		DistanceElement leg8 = Data.getBestDistanceElement("DZBJA", "TNTUN", vesselClass);
		DistanceElement leg9 = Data.getBestDistanceElement("TNTUN", "ITGOA", vesselClass);
		DistanceElement leg10 = Data.getBestDistanceElement("ITGOA", "ESTAR", vesselClass);
		DistanceElement leg11 = Data.getBestDistanceElement("ESTAR", "ESAGP", vesselClass);
		DistanceElement leg12 = Data.getBestDistanceElement("ESAGP", "MACAS", vesselClass);
		distances.add(leg1);
		distances.add(leg2);
		distances.add(leg3);
		distances.add(leg4);
		distances.add(leg5);
		distances.add(leg6);
		distances.add(leg7);
		distances.add(leg8);
		distances.add(leg9);
		distances.add(leg10);
		distances.add(leg11);
		distances.add(leg12);
		Rotation r = testGraph.createRotation(distances, vesselClass);
		
		vesselClass = Data.getVesselClasses().get(1);
		ArrayList<DistanceElement> distances2 = new ArrayList<DistanceElement>();
		DistanceElement leg20 = Data.getBestDistanceElement("SYLTK", "ILHFA", vesselClass);
		DistanceElement leg21 = Data.getBestDistanceElement("ILHFA", "EGDAM", vesselClass);
		DistanceElement leg22 = Data.getBestDistanceElement("EGDAM", "EGPSD", vesselClass);
		DistanceElement leg23 = Data.getBestDistanceElement("EGPSD", "EGALY", vesselClass);
		DistanceElement leg24 = Data.getBestDistanceElement("EGALY", "EGPSD", vesselClass);
		DistanceElement leg25 = Data.getBestDistanceElement("EGPSD", "ILASH", vesselClass);
		DistanceElement leg26 = Data.getBestDistanceElement("ILASH", "TRMER", vesselClass);
		DistanceElement leg27 = Data.getBestDistanceElement("TRMER", "SYLTK", vesselClass);
		distances2.add(leg20);
		distances2.add(leg21);
		distances2.add(leg22);
		distances2.add(leg23);
		distances2.add(leg24);
		distances2.add(leg25);
		distances2.add(leg26);
		distances2.add(leg27);
		Rotation r2 = testGraph.createRotation(distances2, vesselClass);
		
		
		vesselClass = Data.getVesselClasses().get(0);
		ArrayList<DistanceElement> distances3 = new ArrayList<DistanceElement>();
		DistanceElement leg30 = Data.getBestDistanceElement("ESAGP", "DZALG", vesselClass);
		DistanceElement leg31 = Data.getBestDistanceElement("DZALG", "DZSKI", vesselClass);
		DistanceElement leg32 = Data.getBestDistanceElement("DZSKI", "TRIZM", vesselClass);
		DistanceElement leg33 = Data.getBestDistanceElement("TRIZM", "ITSAL", vesselClass);
		DistanceElement leg34 = Data.getBestDistanceElement("ITSAL", "ITGOA", vesselClass);
		DistanceElement leg35 = Data.getBestDistanceElement("ITGOA", "ESALG", vesselClass);
		DistanceElement leg36 = Data.getBestDistanceElement("ESALG", "ESAGP", vesselClass);
		distances3.add(leg30);
		distances3.add(leg31);
		distances3.add(leg32);
		distances3.add(leg33);
		distances3.add(leg34);
		distances3.add(leg35);
		distances3.add(leg36);
		Rotation r3 = testGraph.createRotation(distances3, vesselClass);
		
		vesselClass = Data.getVesselClasses().get(1);
		ArrayList<DistanceElement> distances4 = new ArrayList<DistanceElement>();
		DistanceElement leg40 = Data.getBestDistanceElement("TNTUN", "ITGIT", vesselClass);
		DistanceElement leg41 = Data.getBestDistanceElement("ITGIT", "ITTRS", vesselClass);
		DistanceElement leg42 = Data.getBestDistanceElement("ITTRS", "GRPIR", vesselClass);
		DistanceElement leg43 = Data.getBestDistanceElement("GRPIR", "TRAMB", vesselClass);
		DistanceElement leg44 = Data.getBestDistanceElement("TRAMB", "CYLMS", vesselClass);
		DistanceElement leg45 = Data.getBestDistanceElement("CYLMS", "EGPSD", vesselClass);
		DistanceElement leg46 = Data.getBestDistanceElement("EGPSD", "TNTUN", vesselClass);
		distances4.add(leg40);
		distances4.add(leg41);
		distances4.add(leg42);
		distances4.add(leg43);
		distances4.add(leg44);
		distances4.add(leg45);
		distances4.add(leg46);
		Rotation r4 = testGraph.createRotation(distances4, vesselClass);
		
		vesselClass = Data.getVesselClasses().get(2);
		ArrayList<DistanceElement> distances5 = new ArrayList<DistanceElement>();
		DistanceElement leg50 = Data.getBestDistanceElement("LBBEY", "EGPSD", vesselClass);
		DistanceElement leg51 = Data.getBestDistanceElement("EGPSD", "ITGIT", vesselClass);
		DistanceElement leg52 = Data.getBestDistanceElement("ITGIT", "ESVLC", vesselClass);
		DistanceElement leg53 = Data.getBestDistanceElement("ESVLC", "ESALG", vesselClass);
		DistanceElement leg54 = Data.getBestDistanceElement("ESALG", "ESAGP", vesselClass);
		DistanceElement leg55 = Data.getBestDistanceElement("ESAGP", "MAPTM", vesselClass);
		DistanceElement leg56 = Data.getBestDistanceElement("MAPTM", "ESLPA", vesselClass);
		DistanceElement leg57 = Data.getBestDistanceElement("ESLPA", "ESBCN", vesselClass);
		DistanceElement leg58 = Data.getBestDistanceElement("ESBCN", "ITGOA", vesselClass);
		DistanceElement leg59 = Data.getBestDistanceElement("ITGOA", "LBBEY", vesselClass);
		distances5.add(leg50);
		distances5.add(leg51);
		distances5.add(leg52);
		distances5.add(leg53);
		distances5.add(leg54);
		distances5.add(leg55);
		distances5.add(leg56);
		distances5.add(leg57);
		distances5.add(leg58);
		distances5.add(leg59);
		Rotation r5 = testGraph.createRotation(distances5, vesselClass);
		
		vesselClass = Data.getVesselClasses().get(0);
		ArrayList<DistanceElement> distances6 = new ArrayList<DistanceElement>();
		DistanceElement leg60 = Data.getBestDistanceElement("MACAS", "ESALG", vesselClass);
		DistanceElement leg61 = Data.getBestDistanceElement("ESALG", "DZORN", vesselClass);
		DistanceElement leg62 = Data.getBestDistanceElement("DZORN", "MACAS", vesselClass);
		distances6.add(leg60);
		distances6.add(leg61);
		distances6.add(leg62);
		Rotation r6 = testGraph.createRotation(distances6, vesselClass);
		
		vesselClass = Data.getVesselClasses().get(0);
		ArrayList<DistanceElement> distances7 = new ArrayList<DistanceElement>();
		DistanceElement leg70 = Data.getBestDistanceElement("EGPSD", "ILASH", vesselClass);
		DistanceElement leg71 = Data.getBestDistanceElement("ILASH", "ITGIT", vesselClass);
		DistanceElement leg72 = Data.getBestDistanceElement("ITGIT", "ITGOA", vesselClass);
		DistanceElement leg73 = Data.getBestDistanceElement("ITGOA", "GEPTI", vesselClass);
		DistanceElement leg74 = Data.getBestDistanceElement("GEPTI", "UAODS", vesselClass);
		DistanceElement leg75 = Data.getBestDistanceElement("UAODS", "BGVAR", vesselClass);
		DistanceElement leg76 = Data.getBestDistanceElement("BGVAR", "EGPSD", vesselClass);
		distances7.add(leg70);
		distances7.add(leg71);
		distances7.add(leg72);
		distances7.add(leg73);
		distances7.add(leg74);
		distances7.add(leg75);
		distances7.add(leg76);
		Rotation r7 = testGraph.createRotation(distances7, vesselClass);
		
		
		long time = System.currentTimeMillis();
		testGraph.runMcf();
		long timeUse = System.currentTimeMillis() - time;
		System.out.println("Running for " + timeUse + " ms");
		testGraph.getResult().saveODSol("ODSol.csv");
		testGraph.getResult().saveRotationSol("RotationSol.csv");
		System.out.println();
		System.out.println("Objective " + testGraph.getResult().getObjective());
		System.out.println("Flow profit " + testGraph.getResult().getFlowProfit(false));
		
		testGraph.getResult().saveOPLData("OPLData.dat");
	}
	
	/* Out-of-date method.
	public static void testWorldSmall1Auto()throws FileNotFoundException, InterruptedException{
		Data.initialize("fleet_WorldSmall.csv", "LinerLib_Data\\RandomNumbers.csv");
		Graph graph = new Graph("Demand_WorldSmall.csv");
		initialize(graph);
		ArrayList<AuxEdge> sortedEdges = AuxGraph.getSortedAuxEdges();
		VesselClass feeder450 = Data.getVesselClasses().get(0);
		VesselClass feeder800 = Data.getVesselClasses().get(1);
		VesselClass panamax1200 = Data.getVesselClasses().get(2);
		VesselClass panamax2400 = Data.getVesselClasses().get(3);
		VesselClass postPanamax = Data.getVesselClasses().get(4);
		VesselClass superPanamax = Data.getVesselClasses().get(5);
		
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, superPanamax);
		
		ComputeRotations.createAuxFlowRotation(9, sortedEdges, postPanamax);
		ComputeRotations.createAuxFlowRotation(9, sortedEdges, postPanamax);
		ComputeRotations.createAuxFlowRotation(9, sortedEdges, postPanamax);
		ComputeRotations.createAuxFlowRotation(9, sortedEdges, postPanamax);
		ComputeRotations.createAuxFlowRotation(9, sortedEdges, postPanamax);
		ComputeRotations.createAuxFlowRotation(7, sortedEdges, postPanamax);
		ComputeRotations.createAuxFlowRotation(6, sortedEdges, postPanamax);
		
		ComputeRotations.createAuxFlowRotation(9, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(9, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(9, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(9, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(9, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(8, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(8, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(7, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(6, sortedEdges, panamax2400);

		ComputeRotations.createAuxFlowRotation(9, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(9, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(9, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(9, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(9, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(9, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(7, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(7, sortedEdges, panamax1200);
		
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(4, sortedEdges, feeder800);
		
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder450);
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder450);
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder450);
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder450);
		ComputeRotations.createAuxFlowRotation(4, sortedEdges, feeder450);
		
		System.out.println("Rotations generated.");
		
//		ComputeRotations.addPorts();
		graph.runMcf();
//		ComputeRotations.addPorts();
//		ComputeRotations.removePorts();
//		MulticommodityFlow.run();
		System.out.println("Multicommodity run.");
		graph.getResult().saveODSol("ODSol.csv");
		graph.getResult().saveRotationSol("RotationSol.csv");
		System.out.println();
		System.out.println("Objective " + graph.getResult().getObjective());
		System.out.println("Flow profit " + graph.getResult().getFlowProfit(false));
		
		graph.saveOPLData("OPLData.dat");
	}
	*/

	/* Out-of-date method.
	public static void testWorldSmall2Auto()throws FileNotFoundException, InterruptedException{
		Data.initialize("fleet_WorldSmall.csv", "LinerLib_Data\\RandomNumbers.csv");
		Graph graph = new Graph("Demand_WorldSmall.csv");
		initialize(graph);
		ArrayList<AuxEdge> sortedEdges = AuxGraph.getSortedAuxEdges();
		VesselClass feeder450 = Data.getVesselClasses().get(0);
		VesselClass feeder800 = Data.getVesselClasses().get(1);
		VesselClass panamax1200 = Data.getVesselClasses().get(2);
		VesselClass panamax2400 = Data.getVesselClasses().get(3);
		VesselClass postPanamax = Data.getVesselClasses().get(4);
		VesselClass superPanamax = Data.getVesselClasses().get(5);
		
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, superPanamax);
		
		ComputeRotations.createAuxFlowRotation(11, sortedEdges, postPanamax);
		ComputeRotations.createAuxFlowRotation(11, sortedEdges, postPanamax);
		ComputeRotations.createAuxFlowRotation(11, sortedEdges, postPanamax);
		ComputeRotations.createAuxFlowRotation(9, sortedEdges, postPanamax);
		ComputeRotations.createAuxFlowRotation(9, sortedEdges, postPanamax);
		ComputeRotations.createAuxFlowRotation(7, sortedEdges, postPanamax);
		
		ComputeRotations.createAuxFlowRotation(11, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(11, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(11, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(9, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(8, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(8, sortedEdges, panamax2400);
		
		ComputeRotations.createAuxFlowRotation(8, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(8, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(8, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(8, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(8, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(8, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(7, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(7, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(6, sortedEdges, panamax1200);
		
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(4, sortedEdges, feeder800);
		
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder450);
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder450);
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder450);
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder450);
		ComputeRotations.createAuxFlowRotation(4, sortedEdges, feeder450);
		
		System.out.println("Rotations generated.");
		
//		ComputeRotations.addPorts();
		graph.runMcf();
//		ComputeRotations.addPorts();
//		ComputeRotations.removePorts();
//		MulticommodityFlow.run();
		System.out.println("Multicommodity run.");
		graph.getResult().saveODSol("ODSol.csv");
		graph.getResult().saveRotationSol("RotationSol.csv");
		System.out.println();
		System.out.println("Objective " + graph.getResult().getObjective());
		System.out.println("Flow profit " + graph.getResult().getFlowProfit(false));
		
		graph.saveOPLData("OPLData.dat");
		
	}
	*/

	
	/* Out-of-date method.
	public static void testWorldSmall3Auto()throws InterruptedException, IOException{
//		Data.initialize("fleet_WorldSmall.csv", "randomNumbers.csv");
//		Graph graph = new Graph("Demand_WorldSmall.csv");
//		initialize(graph);
//		ArrayList<AuxEdge> sortedEdges = AuxGraph.getSortedAuxEdges();
//		VesselClass feeder450 = Data.getVesselClasses().get(0);
//		VesselClass feeder800 = Data.getVesselClasses().get(1);
//		VesselClass panamax1200 = Data.getVesselClasses().get(2);
//		VesselClass panamax2400 = Data.getVesselClasses().get(3);
//		VesselClass postPanamax = Data.getVesselClasses().get(4);
//		VesselClass superPanamax = Data.getVesselClasses().get(5);
//		
//		ArrayList<Rotation> rotations = new ArrayList<Rotation>();
//		
//		rotations.add(ComputeRotations.createAuxFlowRotation(10, sortedEdges, superPanamax));
//		
//		rotations.add(ComputeRotations.createAuxFlowRotation(11, sortedEdges, postPanamax));
//		rotations.add(ComputeRotations.createAuxFlowRotation(11, sortedEdges, postPanamax));
//		rotations.add(ComputeRotations.createAuxFlowRotation(11, sortedEdges, postPanamax));
//		rotations.add(ComputeRotations.createAuxFlowRotation(9, sortedEdges, postPanamax));
//		rotations.add(ComputeRotations.createAuxFlowRotation(9, sortedEdges, postPanamax));
//		rotations.add(ComputeRotations.createAuxFlowRotation(7, sortedEdges, postPanamax));
//		
//		rotations.add(ComputeRotations.createAuxFlowRotation(9, sortedEdges, panamax2400));
//		rotations.add(ComputeRotations.createAuxFlowRotation(9, sortedEdges, panamax2400));
//		rotations.add(ComputeRotations.createAuxFlowRotation(9, sortedEdges, panamax2400));
//		rotations.add(ComputeRotations.createAuxFlowRotation(9, sortedEdges, panamax2400));
//		rotations.add(ComputeRotations.createAuxFlowRotation(9, sortedEdges, panamax2400));
//		rotations.add(ComputeRotations.createAuxFlowRotation(8, sortedEdges, panamax2400));
//		rotations.add(ComputeRotations.createAuxFlowRotation(8, sortedEdges, panamax2400));
//		rotations.add(ComputeRotations.createAuxFlowRotation(7, sortedEdges, panamax2400));
//		rotations.add(ComputeRotations.createAuxFlowRotation(6, sortedEdges, panamax2400));
//		
//		rotations.add(ComputeRotations.createAuxFlowRotation(8, sortedEdges, panamax1200));
//		rotations.add(ComputeRotations.createAuxFlowRotation(8, sortedEdges, panamax1200));
//		rotations.add(ComputeRotations.createAuxFlowRotation(8, sortedEdges, panamax1200));
//		rotations.add(ComputeRotations.createAuxFlowRotation(8, sortedEdges, panamax1200));
//		rotations.add(ComputeRotations.createAuxFlowRotation(8, sortedEdges, panamax1200));
//		rotations.add(ComputeRotations.createAuxFlowRotation(8, sortedEdges, panamax1200));
//		rotations.add(ComputeRotations.createAuxFlowRotation(7, sortedEdges, panamax1200));
//		rotations.add(ComputeRotations.createAuxFlowRotation(7, sortedEdges, panamax1200));
//		rotations.add(ComputeRotations.createAuxFlowRotation(6, sortedEdges, panamax1200));
//		
//		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder800);
//		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder800);
//		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder800);
//		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder800);
//		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder800);
//		ComputeRotations.createAuxFlowRotation(4, sortedEdges, feeder800);
//		
//		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder450);
//		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder450);
//		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder450);
//		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder450);
//		ComputeRotations.createAuxFlowRotation(4, sortedEdges, feeder450);
//		
//		System.out.println("Rotations generated.");
		
//		ComputeRotations.addPorts();
//		graph.runMcf();
		
//		int orgObj = graph.getResult().getObjective();
//		int orgFlow = graph.getResult().getFlowProfit(false);
//		System.out.println("Starting objective " + orgObj);
		
		
//		AuxGraph auxGraph = AuxGraph.deserialize();
		
		LNS lns = new LNS();
		
//		lns.run(300, 6, auxGraph);
		lns.run(1200, 20);

//		graph.getResult().getRotations().get(2).createRotationGraph();
//		graph.getResult().getRotations().get(2).findRotationFlow();
//		graph.getResult().getRotations().get(2).insertBestPort(1.1, 0.05);
//		graph.runMcf();
//		graph.getResult().getRotations().get(2).createRotationGraph();
//		graph.getResult().getRotations().get(2).findRotationFlow();
//		graph.getResult().getRotations().get(2).removeWorstPort();
		
//		for(Rotation r : graph.getResult().getRotations()){
//			r.createRotationGraph();
//		}
		
//		rotations.get(2).createRotationGraph();
//		rotations.get(2).findRotationFlow();
//		rotations.get(2).insertBestPort(1.1, 0.05);
//		
//		graph.runMcf();
//		
//		rotations.get(2).createRotationGraph();
//		rotations.get(2).findRotationFlow();
//		rotations.get(2).removeWorstPort();
		

//		long startTime = System.currentTimeMillis();

		
//		graph.runMcf();
		
//		graph.runMcf();
//		for(Rotation r : rotations){
//			r.createRotationGraph();
//			r.findRotationFlow();
//		}
		
		
//		Rotation newRotation = ComputeRotations.mergeRotations(r1, r2);
//		ArrayList<String> rotationPorts = new ArrayList<String>(r1.getPorts().size());
//		for(Port p : r1.getPorts()){
//			rotationPorts.add(p.getName());
//		}
//		RuneVisualization.makeVisualization(rotationPorts, "r1");
//		rotationPorts = new ArrayList<String>(r2.getPorts().size());
//		for(Port p : r2.getPorts()){
//			rotationPorts.add(p.getName());
//		}
//		RuneVisualization.makeVisualization(rotationPorts, "r2");
		
//		MulticommodityFlow.run();
//		ComputeRotations.addPorts();
//		ComputeRotations.removePorts();
//		MulticommodityFlow.run();
		
//		long endTime = System.currentTimeMillis();
//		
//		System.out.println("Multicommodity run.");
//		System.out.println("RunningTime " + (endTime-startTime));
////		graph.getMcf().saveODSol("ODSol.csv", graph.getDemands());
////		graph.getMcf().saveRotationSol("RotationSol.csv", graph.getResult().getRotations());
////		graph.getMcf().saveTransferSol("TransferSol.csv");
////		graph.getMcf().saveAllEdgesSol("AllEdgesSol.csv");
//		System.out.println();
//		System.out.println("Org objective " + orgObj);
//		System.out.println("Org flow profit " + orgFlow);
//		System.out.println();
//		System.out.println("Objective " + graph.getResult().getObjective());
//		System.out.println("Flow profit " + graph.getResult().getFlowProfit(false));
		
		
//		graph.saveOPLData("OPLData.dat");
		
	}
	*/
	
	/* Out-of-date method.
	public static void testWorldLargeAuto()throws FileNotFoundException, InterruptedException{
		Data.initialize("fleet_WorldLarge.csv", "LinerLib_Data\\RandomNumbers.csv");
		Graph graph = new Graph("Demand_WorldLarge.csv");
		initialize(graph);
		ArrayList<AuxEdge> sortedEdges = AuxGraph.getSortedAuxEdges();
		VesselClass feeder450 = Data.getVesselClasses().get(0);
		VesselClass feeder800 = Data.getVesselClasses().get(1);
		VesselClass panamax1200 = Data.getVesselClasses().get(2);
		VesselClass panamax2400 = Data.getVesselClasses().get(3);
		VesselClass postPanamax = Data.getVesselClasses().get(4);
		VesselClass superPanamax = Data.getVesselClasses().get(5);
		
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, superPanamax);
		
		ComputeRotations.createAuxFlowRotation(11, sortedEdges, postPanamax);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, postPanamax);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, postPanamax);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, postPanamax);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, postPanamax);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, postPanamax);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, postPanamax);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, postPanamax);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, postPanamax);
		
		Rotation r11 = ComputeRotations.createAuxFlowRotation(11, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax2400);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax2400);
		Rotation r10 = ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax2400);
		
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(10, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(7, sortedEdges, panamax1200);
		ComputeRotations.createAuxFlowRotation(7, sortedEdges, panamax1200);
		
		ComputeRotations.createAuxFlowRotation(7, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(7, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(7, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(7, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(7, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(7, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(7, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(7, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(7, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(7, sortedEdges, feeder800);
		ComputeRotations.createAuxFlowRotation(7, sortedEdges, feeder800);
		
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder450);
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder450);
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder450);
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder450);
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder450);
		ComputeRotations.createAuxFlowRotation(5, sortedEdges, feeder450);
		ComputeRotations.createAuxFlowRotation(4, sortedEdges, feeder450);
		ComputeRotations.createAuxFlowRotation(4, sortedEdges, feeder450);
	
		System.out.println("Rotations generated.");
		
//		ComputeRotations.addPorts();
		graph.runMcf();
		
//		Rotation newRotation = ComputeRotations.mergeRotations(r1, r2);
//		ArrayList<String> rotationPorts = new ArrayList<String>(r1.getPorts().size());
//		for(Port p : r1.getPorts()){
//			rotationPorts.add(p.getName());
//		}
//		RuneVisualization.makeVisualization(rotationPorts, "r1");
		
		
//		MulticommodityFlow.run();
//		ComputeRotations.addPorts();
//		ComputeRotations.removePorts();
//		MulticommodityFlow.run();
		System.out.println("Multicommodity run.");
		graph.getResult().saveODSol("ODSol.csv");
		graph.getResult().saveRotationSol("RotationSol.csv");
		System.out.println();
		System.out.println("Objective " + graph.getResult().getObjective());
		System.out.println("Flow profit " + graph.getResult().getFlowProfit(false));
		
		graph.saveOPLData("OPLData.dat");
	}
	*/
}
