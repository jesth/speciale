package AuxFlow;

import java.util.ArrayList;

import Data.Data;
import Data.Demand;

public class AuxDijkstra {
	private AuxGraph graph;
	private AuxHeap heap;
	
	public AuxDijkstra(AuxGraph graph){
		this.graph = graph;
		heap = new AuxHeap(graph);
		reset();
	}

	public void run(int rand){
		ArrayList<Demand> demandsList = graph.getDemands();
		Demand[] demands = new Demand[demandsList.size()];
		int[] remainingDemand = new int[demandsList.size()];
		int totRemainingDemand = 0;
		for(Demand d : demandsList){
			demands[d.getId()] = d;
			remainingDemand[d.getId()] = d.getDemand();
			totRemainingDemand += d.getDemand();
		}
		while(totRemainingDemand > 0){
			int index = chooseIndex(remainingDemand, totRemainingDemand, rand);
			Demand demand = demands[index];
			int sourcePortId = demand.getOrigin().getPortId();
			int sinkPortId = demand.getDestination().getPortId();
			AuxNode source = graph.getNode(sourcePortId);
			AuxNode sink = graph.getNode(sinkPortId);
			dijkstraSingle(source, sink);
			remainingDemand[index]--;
			totRemainingDemand--;
			if(remainingDemand[index] < 0){
				throw new RuntimeException("Negative remaining demand");
			}
		}
//				for(AuxEdge e : graph.getEdges()){
//					if(e.getLoad() > 0){
//						System.out.println("Edge from " + e.getFromNode().getPort().getUNLocode() + " to " + e.getToNode().getPort().getUNLocode() + " has expected load " + e.getLoad());
//					}
//				}
	}

	public int chooseIndex(int[] remainingDemand, int totRemainingDemand, int rand){
		int indexDemand = (int) (Data.getRandomNumber(totRemainingDemand + 13*rand) * totRemainingDemand);
		int index = -1;
		int cumDemand = 0;
		while(cumDemand <= indexDemand){
			index++;
			cumDemand += remainingDemand[index];
		}
		return index;
	}

	private void reset(){
		for(AuxNode i : graph.getNodes()){
			i.setDistance(Integer.MAX_VALUE);
			i.setPredecessor(null);
		}
		heap.reset();
	}

	public void convert(int iterations){
		for(AuxEdge e : graph.getEdges()){
			e.convertLoad(iterations);
		}
	}

	private void dijkstraSingle (AuxNode source, AuxNode sink){
		reset();
		heap.setSource(source);
		while (heap.getSize() > 0 && heap.getMin().getDistance() != Integer.MAX_VALUE){
			AuxNode currentNode = heap.extractMin();
			if (!currentNode.equals(sink)){
				for (int i = 0; i < currentNode.getOutgoingEdges().size(); i++){
					AuxEdge currentEdge = currentNode.getOutgoingEdges().get(i);
					if(!currentEdge.isFull()){
						if(currentEdge.getToNode().getHeapIndex() < heap.getSize()){
							relax(currentNode, currentEdge.getToNode(), currentEdge);
							heap.bubbleUp(currentEdge.getToNode().getHeapIndex());
						}
					}
				}
			} else {
				AuxEdge predecessorEdge = sink.getPredecessor();
				AuxNode predecessorNode = predecessorEdge.getFromNode();
				predecessorEdge.addFFE();
				while(!predecessorNode.equals(source)){
					predecessorEdge = predecessorNode.getPredecessor();
					predecessorNode = predecessorEdge.getFromNode();
					predecessorEdge.addFFE();
				}
				return;
			}
		}
	}

	private void relax(AuxNode fromNode, AuxNode toNode, AuxEdge edge){
		if (toNode.getDistance() > (fromNode.getDistance() + edge.getCost())){
			toNode.setDistance(fromNode.getDistance() + edge.getCost());
			toNode.setPredecessor(edge);
		}
	}
}
