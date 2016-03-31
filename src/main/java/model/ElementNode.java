package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.helper.StringUtil;

public class ElementNode {

	private String tagName;
	private HashMap<String, String> attrMap;
	private List<StyleNode> SList;
	private HashMap<String, Integer> contentMap;
	private double eImportance = Double.NaN;
	private boolean isNoise = true;
	private boolean isMain = false;
	
	public ElementNode(){
		
	}
	
	public ElementNode(String tag){
		this.tagName = tag;
		this.attrMap = new HashMap<String, String>();
		this.SList = new ArrayList<StyleNode>();
		this.contentMap = new HashMap<String, Integer>();
	}

	
	public boolean containStyle(String styleName){
		for(StyleNode s:SList){
			if(s.isEqual(styleName))
				return true;
		}
		return false;
	}
	
	public StyleNode getStyleByName(String styleName){
		for(StyleNode s:SList){
			if(s.isEqual(styleName))
				return s;
		}
		return null;
	}
	
	public void addStyle(StyleNode sn){
		if(sn == null)
			return ;
		SList.add(sn);
	}
	
	public void addAttr(String key,String value){
		this.attrMap.put(key, value);
	}

	public void recordText(String text){
		if(StringUtil.isBlank(text))
			return;
		if(contentMap.containsKey(text)){
			int oldCount = contentMap.get(text);
			contentMap.put(text, oldCount+1);
		}else{
			contentMap.put(text,1);
		}
	}
	
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public HashMap<String, String> getAttrMap() {
		return attrMap;
	}

	public void setAttrMap(HashMap<String, String> attrMap) {
		this.attrMap = attrMap;
	}

	public List<StyleNode> getSList() {
		return SList;
	}

	public void setSList(List<StyleNode> sList) {
		SList = sList;
	}

	public boolean isNoise() {
		return isNoise;
	}


	public void setNoise(boolean isNoise) {
		this.isNoise = isNoise;
	}
	
	public double geteImportance() {
		return eImportance;
	}


	public void seteImportance(double eImportance) {
		this.eImportance = eImportance;
	}


	public HashMap<String, Integer> getContentMap() {
		return contentMap;
	}


	public void setContentMap(HashMap<String, Integer> contentMap) {
		this.contentMap = contentMap;
	}


	public boolean isMain() {
		return isMain;
	}


	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}
}
