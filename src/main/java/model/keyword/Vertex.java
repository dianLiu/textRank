package model.keyword;

public class Vertex {

	private int index;
	private double value;
	private String word;
	
	public Vertex(int index,double value){
		this.index = index;
		this.value = value;
	}
	
	public void addValue(){
		this.value += 1;
	}
	
	public void setWord(String w){
	    this.word = w;
	}
	
	public String getWord() {
		return word;
	}

	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
}
