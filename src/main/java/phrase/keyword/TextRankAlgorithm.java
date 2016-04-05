package phrase.keyword;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.keyword.HeadVertex;
import model.keyword.Vertex;
import model.keyword.WordGraph;

public class TextRankAlgorithm extends WordExtractAlgorithm{
	
	public void wordExtract(WordGraph graph){
		Map<String, Double> score = new HashMap<String, Double>();
		int max_iter = 20;
		double d = 0.9f,min_diff=0.001f;
		
		 for (int i = 0; i < max_iter; ++i)
	        {
	            Map<String, Double> m = new HashMap<String, Double>();
	            double max_diff = 0.0;
	            List<HeadVertex> list = graph.getNodeList();
	            for(HeadVertex head:list){
	            	String key = head.getWord();
	            	m.put(key, (1.0-d));
	            	for(Vertex v:head.getNodes()){
	            		HeadVertex other = list.get(v.getIndex());
	            		int size = other.getNodes().size();
	            		if(size == 0) continue;
	            		m.put(head.getWord(),  (m.get(head.getWord()) + d / size * (score.get(other.getWord()) == null ? 0 : score.get(other.getWord()))));
	            	}
	            	max_diff = Math.max(max_diff, Math.abs(m.get(key) - (score.get(key) == null ? 0 : score.get(key))));
	            }
	            score = m;
	            if (max_diff <= min_diff) break;
	        }
		 
		 for(HeadVertex head:graph.getNodeList()){
			 head.setImpact(score.get(head.getWord()));
		 } 
	}
	

}
