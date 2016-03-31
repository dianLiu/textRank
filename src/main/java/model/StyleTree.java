package model;

import java.io.Serializable;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import phrase.TreeAnalyzer;
import phrase.TreeBuilder;

public class StyleTree implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ElementNode root;
	private TreeBuilder builder;
	private TreeAnalyzer analyzer;
	private  int fileCount;
	
	public StyleTree(){
		this.root = new ElementNode("body");
		this.builder = new TreeBuilder();
		this.analyzer = new TreeAnalyzer();
		this.fileCount = 0;
	}
	
	public void buildStyleTree(String html){
		this.builder.buildTree(this.root,html);
		this.fileCount ++;
	}
	
	public void buildTemplate(){
		analyzer.calNodeImp(root, this.fileCount);
		analyzer.markNoise(root);
		analyzer.markMain(root);
//		System.out.println(toString());
	}

	public Element eliminateNoise(String html){
		return builder.eliminate(root,html);
	}
	
	public String toString(){
	    StringBuilder str = new StringBuilder();
	    str.append(root.getTagName());
	    str.append(":");
	    str.append(root.geteImportance());
	    str.append("\n");
	    for(StyleNode s:root.getSList()){
	    	str.append(s.toString());
	    }
	    return str.toString();
	}
	
	public ElementNode getRoot() {
		return root;
	}

	public void setRoot(ElementNode root) {
		this.root = root;
	}
}
