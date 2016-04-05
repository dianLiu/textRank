package model.keyword;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hankcs.hanlp.seg.common.Term;

public class WordGraph {

	private List<HeadVertex> nodeList;
	private HashMap<String, Integer> map;
	private double power;
	
	public WordGraph(){
		this.nodeList = new ArrayList<HeadVertex>();
		this.map = new HashMap<String, Integer>();
	}
	
	public void addEdge(Term startWord,Term endWord,double value){
		int startIndex = getHeadVertex(startWord);
		int endIndex = getHeadVertex(endWord);
		
		nodeList.get(startIndex).addVertex(endWord.word,endIndex,value);
		nodeList.get(endIndex).addVertex(startWord.word,startIndex,value);
	}
	
	public void addWeight(String word,double w){
		if(!map.containsKey(word))
			return;
		int nodeIndex = map.get(word);
		HeadVertex start = nodeList.get(nodeIndex);
		List<Vertex> list = start.getNodes();
		for(Vertex v:list){
			v.setValue(w*v.getValue());
			//updateWeight(v,nodeIndex);
		}
	}
	
	public void weightWordLen(){
		double w1;
		for(HeadVertex h:nodeList){
			w1 = calWeight(h.getWord().length());
			weightOne(h,w1);
		}
	}
	
	public int getNodeSize(){
		return this.nodeList.size();
	}
	
	private void weightOne(HeadVertex h,double w){
		List<Vertex> list = h.getNodes();
		
		for(Vertex t:list){
			String word = nodeList.get(t.getIndex()).getWord();
			t.setValue(t.getValue()*w*calWeight(word.length()));
		}
	}
	
	private double calWeight(int len){
		double w = 1.0;
	     if(len>=4&&len<=7)
	    	 w = 1.2;
	     if(len>7)
	    	 w=1.3;
	     return w;
	}
	
	public String toString(){
	    StringBuilder str = new StringBuilder();
	    for(HeadVertex h:nodeList){
	    	str.append(h.toString());
	    	str.append("\n");
	    }
	    str.append("节点总数n=");
	    str.append(nodeList.size());
	    return str.toString();
	}
	
	private int getHeadVertex(Term word){
		if(map.containsKey(word.word))
			return map.get(word.word);
		
		int index = nodeList.size();
		map.put(word.word, index);
		
		HeadVertex v = new HeadVertex(word.word,word.nature);
		nodeList.add(v);
		
		return index;
	}
	
	public List<HeadVertex> getNodeList() {
		return nodeList;
	}
	public void setNodeList(List<HeadVertex> nodeList) {
		this.nodeList = nodeList;
	}
	public HashMap<String, Integer> getMap() {
		return map;
	}
	public void setMap(HashMap<String, Integer> map) {
		this.map = map;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
	}
	
	
}
