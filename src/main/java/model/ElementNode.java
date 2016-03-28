package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.helper.StringUtil;

public class ElementNode {

	private String tagName;
	private HashMap<String, String> attrMap;
	private List<StyleNode> SList;
	private StringBuilder content;
	private int textCount = 0;
	public double eImportance = Double.NaN;
	private boolean isNoise;
	
	public ElementNode(String tag){
		this.tagName = tag;
		this.attrMap = new HashMap<String, String>();
		this.SList = new ArrayList<StyleNode>();
		this.content = new StringBuilder();
	}

	
	public boolean containStyle(String styleName){
		for(StyleNode s:SList){
			if(s.isEqual(styleName))
				return true;
		}
		return false;
	}
	
	public StyleNode getStyle(String styleName){
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
		
		if(content.indexOf(text)<0){
			this.textCount++;
			content.append("@");
			content.append(text);
		}
	}
	
	public String toString(){
	    StringBuilder str = new StringBuilder();
	    str.append(tagName);
	    str.append(":");
	    str.append(eImportance);
	    str.append("\n");
	    for(StyleNode s:SList){
	    	str.append(s.toString());
	    }
	    return str.toString();
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


	public int getTextCount() {
		return textCount;
	}


	public void setTextCount(int textCount) {
		this.textCount = textCount;
	}


	public StringBuilder getContent() {
		return content;
	}


	public void setContent(StringBuilder content) {
		this.content = content;
	}


	public boolean isNoise() {
		return isNoise;
	}


	public void setNoise(boolean isNoise) {
		this.isNoise = isNoise;
	}
}
