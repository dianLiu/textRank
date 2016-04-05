package phrase.keyword;

import model.keyword.HeadVertex;
import model.keyword.Vertex;
import model.keyword.WordGraph;

public class ConvergenceAlgorithm extends WordExtractAlgorithm{
	
	public void wordExtract(WordGraph graph){
		int i=0,n =graph.getNodeSize();
		double[] d = new double[n],c = new double[n];
		double cSum = 0,a=0.5f;
		for(HeadVertex head:graph.getNodeList()){
			for(Vertex v:head.getNodes()){
				d[i] = d[i]+v.getValue();
				for(int j=v.getIndex()+1;j<n;j++){
					if(head.findVertex(j)!=null && graph.getNodeList().get(v.getIndex()).findVertex(j)!=null)
						c[i] = c[i]+1;
				}
			}
			c[i] = 2*c[i]/(d[i]*(d[i]-1));
			cSum += c[i];
			i++;
		}
		
		i=0;
		for(HeadVertex head:graph.getNodeList()){
			double tmp = a * c[i]/cSum +(1-a)*d[i]/n;
			head.setImpact(tmp);
			i++;
		}
	}

}
