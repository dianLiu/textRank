package phrase.keyword;

import java.util.HashMap;
import java.util.List;

import model.keyword.HeadVertex;
import model.keyword.Vertex;
import model.keyword.WordGraph;

public class DegreeAlgorithm extends WordExtractAlgorithm{


	public void wordExtract(WordGraph graph) {
		int[][] distance = Floyd.floyd(graph);
		double graphLen = calGLen(distance);
		double power = calGPower(graph);
		double initDeg = power/graphLen;
		
		List<HeadVertex> list = graph.getNodeList();
		int n = list.size();
		for(int i=0;i<n;i++){
			int[][] newDistance = Floyd.nodeContract(distance, list.get(i), i);
			double len = calGLen(newDistance, getExcepts(list.get(i),i));
			double p = calGPower(graph, i);
			double deg = p/len;
			list.get(i).setImpact(1-initDeg/deg);
		}
		
	}
	
	private  HashMap<Integer, Boolean> getExcepts(HeadVertex head,int i){
		HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
		map.put(i, true);
		
		List<Vertex> list = head.getNodes();
		for(Vertex v:list){
			map.put(v.getIndex(), true);
		}
		return map;
	}
	
	private  double calGLen(int[][] distance){
		int i,j,n = distance.length;
		double len = 0.0;
		for(i=0;i<n;i++){
			for(j=0;j<n;j++){
				len += distance[i][j];
			}
		}
		return len/(n*(n-1));
	}
	
	private  double calGLen(int[][] distance,HashMap<Integer,Boolean> excepts){
		int i,j,n = distance.length,k=0;
		double len = 0.0;
		for(i=0;i<n;i++){
			if(excepts.containsKey(i)){
                k++;
				continue;
			}
			for(j=0;j<n;j++){
				if(excepts.containsKey(j))
					continue;
				len += distance[i][j];
			}
		}
		n = n-k;
		return len/(n*(n-1));
	}
	
	private  double calGPower(WordGraph G){
		List<HeadVertex> list = G.getNodeList();
		double power = 0.0;
		for(HeadVertex h:list){
			power += h.getNodePower();
		}
		G.setPower(power);
		return power/list.size();
	}
	
	private  double calGPower(WordGraph G,int nodeIndex){
	    int n = G.getNodeSize();
	    int k = G.getNodeList().get(nodeIndex).getNodes().size()+1;
	    double nodePower = G.getNodeList().get(nodeIndex).getNodePower();
	    double res = (G.getPower()-2*nodePower)/(n-k);
	    return res;
	}

}
