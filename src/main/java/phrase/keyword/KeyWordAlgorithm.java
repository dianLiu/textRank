package phrase.keyword;

import java.util.HashMap;
import java.util.List;

import model.keyword.HeadVertex;
import model.keyword.Vertex;
import model.keyword.WordGraph;

public class KeyWordAlgorithm extends WordExtractAlgorithm {

	private static final double a = 0.5;
	
	@Override
	public void wordExtract(WordGraph graph) {
		// TODO Auto-generated method stub
		double power = calGPower(graph);
		double conver = calGCon(graph);
		double initDeg = a*power+(1-a)*conver;
		
		List<HeadVertex> list = graph.getNodeList();
		int n = list.size();
		for(int i=0;i<n;i++){
			double p = calGPower(graph, i);
			double con = calGCon(graph,getExcepts(list.get(i),i));
			double deg = a*p+(1-a)*con;
			list.get(i).setImpact(1-initDeg/deg);
		}
	}
	
	private double calGCon(WordGraph graph){
		int i=0,n =graph.getNodeSize();
		float[] d = new float[n],c = new float[n];
		float cSum = 0,a=0.5f;
		for(HeadVertex head:graph.getNodeList()){
			for(Vertex v:head.getNodes()){
				d[i] = d[i]+1;
				for(int j=v.getIndex()+1;j<n;j++){
					if(head.findVertex(j)!=null && graph.getNodeList().get(v.getIndex()).findVertex(j)!=null)
						c[i] = c[i]+1;
				}
			}
			c[i] = 2*c[i]/(d[i]*(d[i]-1));
			cSum += c[i];
			i++;
		}
		return cSum/n;
	}
	
	private double calGCon(WordGraph graph,HashMap<Integer,Boolean> excepts){
		int i=0,n =graph.getNodeSize(),k=0;
		float[] d = new float[n],c = new float[n];
		float cSum = 0,a=0.5f;
		for(HeadVertex head:graph.getNodeList()){
			if(excepts.containsKey(i)){
				k++;
				continue;
			}
			for(Vertex v:head.getNodes()){
				if(excepts.containsKey(v.getIndex())){
					continue;
				}
				d[i] = d[i]+1;
				for(int j=v.getIndex()+1;j<n;j++){
					if(excepts.containsKey(j)){
						continue;
					}
					if(head.findVertex(j)!=null && graph.getNodeList().get(v.getIndex()).findVertex(j)!=null)
						c[i] = c[i]+1;
				}
			}
			c[i] = 2*c[i]/(d[i]*(d[i]-1));
			cSum += c[i];
			i++;
		}
		return cSum/(n-k);
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
	
	private  HashMap<Integer, Boolean> getExcepts(HeadVertex head,int i){
		HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
		map.put(i, true);
		
		List<Vertex> list = head.getNodes();
		for(Vertex v:list){
			map.put(v.getIndex(), true);
		}
		return map;
	}

}
