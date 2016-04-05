
package model.keyword;

import java.util.ArrayList;
import java.util.List;

import com.hankcs.hanlp.corpus.tag.Nature;

public class HeadVertex {

	private List<Vertex> nodes;
	private String word;
	private double impact;
	private Nature nature;
	
	public HeadVertex(String word,Nature nature){
		this.word = word;
		this.nodes = new ArrayList<Vertex>();
		this.nature = nature;
	}

	public void addVertex(String word,int index,double value){
	    Vertex vertex = findVertex(index);
	    if(vertex == null){
	    	vertex = new Vertex(index,value);
	    	vertex.setWord(word);
	    	nodes.add(vertex);
	    }else{
	    	vertex.addValue();
	    }
	}
	
	public double getNodePower() {
		double nodePower =0.0;
		for(Vertex v : this.nodes){
			nodePower += v.getValue();
		}
		return nodePower;
	}
	
	public String toString(){
	    StringBuilder str = new StringBuilder();
	    str.append(word);
	    str.append(nature);
	    str.append(impact);
	    str.append("\n");
	    
	    /*str.append(":(");
	    for(Vertex v:nodes){
	    	str.append(v.getWord());
	    	str.append("=");
	    	str.append(v.getValue());
	    	str.append(", ");
	    }
	    str.append(")");*/
	    return str.toString();
	}
	
	public Vertex findVertex(int index){
	    for(Vertex v:nodes){
	    	if(index == v.getIndex())
	    		return v;
	    }
	    return null;
	}
	
	public List<Vertex> getNodes() {
		return nodes;
	}

	public void setNodes(List<Vertex> nodes) {
		this.nodes = nodes;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Nature getNature() {
		return nature;
	}

	public void setNature(Nature nature) {
		this.nature = nature;
	}

	public double getImpact() {
		return impact;
	}

	public void setImpact(double impact) {
		this.impact = impact;
	}
	
}
