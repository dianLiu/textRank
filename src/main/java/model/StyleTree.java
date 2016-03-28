package model;

import phrase.TreeAnalyzer;
import phrase.TreeBuilder;

public class StyleTree {

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
	
	public void summaryTreeNode(){
		this.analyzer.calNodeImp(root, this.fileCount);
	}

	public void markNoise(){
		this.analyzer.markNoise(root);
	}
	
	public ElementNode getRoot() {
		return root;
	}

	public void setRoot(ElementNode root) {
		this.root = root;
	}
}
