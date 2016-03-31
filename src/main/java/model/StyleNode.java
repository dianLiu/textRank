package model;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

public class StyleNode {

	private int count;
	private String styleName;
	private List<ElementNode> EList;
	private double sImportance = Double.NaN;
	
	public StyleNode(){
		this.count = 1;
		this.EList = new ArrayList<ElementNode>();
		this.styleName = "";
	}
	
	public StyleNode(String styleName){
		this.count = 1;
		this.EList = new ArrayList<ElementNode>();
		this.styleName = styleName+"-";
	}
	
	/**
	 * 样式节点使用节点名称StyleName来匹配
	 * @param name
	 * @return
	 */
	public boolean isEqual(String name){
	    return this.styleName.equals(name);	
	}
	
	/**
	 * 添加样式节点频率
	 */
	public void addCount(){
		this.count++;
	}
	
	/**
	 * 根据下标访问元素节点
	 * @param index
	 * @return
	 */
	public ElementNode getElementNode(int index){
		if(index >= this.EList.size())
			return null;
		return this.EList.get(index);
	}
	
	/**
	 * 增加ElementNode，并更新样式节点名称
	 * @param en
	 */
	public void addElementNode(ElementNode en){
		this.EList.add(en);
		this.styleName += en.getTagName()+"-";
	}
	
	public String toString(){
		StringBuilder str = new StringBuilder();
		
		for(ElementNode e:EList){
			str.append("--");
			str.append(e.getTagName());
			str.append("=");
			str.append(e.geteImportance());
			str.append(e.isNoise());
			 str.append("---");
			 str.append(e.isMain());
			    
			if(e.getTagName().equals("form")){
				str.append("\njson");
				str.append(JSONArray.fromObject(e.getSList()).toString());
			}
			str.append("\n");
		}
		return str.toString();
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public List<ElementNode> getEList() {
		return EList;
	}

	public void setEList(List<ElementNode> eList) {
		EList = eList;
	}

	public double getsImportance() {
		return sImportance;
	}

	public void setsImportance(double sImportance) {
		this.sImportance = sImportance;
	}
}
