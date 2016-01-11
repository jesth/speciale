import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Graph {
	private ArrayList<Node> nodes;
	private ArrayList<Edge> edges;
	private Data data;
	
	public Graph() throws FileNotFoundException {
		data = new Data("Demand_Baltic.csv", "fleet_Baltic.csv");
		this.nodes = new ArrayList<Node>();
		this.edges = new ArrayList<Edge>();
		createCentroids(data);
		createOmissionEdges(data);
	}
	
	public void createCentroids(Data data){
		for(Port i : data.getPorts().values()){
			Node newCentroid = new Node(i);
			nodes.add(newCentroid);
			i.setCentroidNode(newCentroid);
		}
	}
	
	public void createOmissionEdges(Data data){
		for(Demand i : data.getDemands()){
			Node fromCentroid = i.getOrigin().getCentroidNode();
			Node toCentroid = i.getDestination().getCentroidNode();
			Edge newOmissionEdge = new Edge(fromCentroid, toCentroid);
			edges.add(newOmissionEdge);
		}
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public Data getData() {
		return data;
	}
}
