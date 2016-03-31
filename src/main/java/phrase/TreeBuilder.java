package phrase;

import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.ElementNode;
import model.StyleNode;

public class TreeBuilder {
	
	public ElementNode buildTree(ElementNode root,String html){
		Document doc = Jsoup.parse(html);
		Element body = getDomBody(doc);
		
		compareNode(root,body);
		return root;
	}
	
	public Element eliminate(ElementNode root,String html){
		Document doc = Jsoup.parse(html);
		Element body = getDomBody(doc);
		
		removeNoise(root,body);
		
		return body;
	}
	
	private void removeNoise(ElementNode node,Element dom){
		if(node.isNoise()){
			dom.remove();
			//System.out.println("remove:"+dom.text());
			return;
		}
		if(node.isMain()){
//			System.out.println("main:"+dom.text());
			return;
		}
		
		Elements children = dom.children();
		if(children.size() == 0 || node.getSList().isEmpty()){
			return;
		}
		
		String styleName = getDomStyle(children);
		StyleNode sn = node.getStyleByName(styleName);
		if(sn == null){
			System.out.println("removeNoise error,can't find styleNode");
			return;
		}
		int index = 0,len=children.size();
		while(index<len){
			removeNoise(sn.getElementNode(index),children.get(index));
			index++; 
		}
	}
	
	private Element getDomBody(Document doc){
		Element body = doc.select("body").first();
		body.getElementsByTag("link").remove();
		body.getElementsByTag("script").remove();
		body.getElementsByTag("style").remove();
		body.getElementsByTag("meta").remove();
		
		return body;
	}
	
	private void compareNode(ElementNode node,Element dom){
		if(dom.tagName().equals("p")){
			node.recordText(dom.text());
			return;
		}
		recordAttr(node,dom);
		
		Elements children = dom.children();
		if(children.size() == 0){
			return;
		}
		
		String styleName = getDomStyle(children);
		
		if(node.containStyle(styleName)){
			mergeStyleNode(node,children,styleName);
		}else{
			createStyleNode(node,children);
		}
	}
	
	private String getDomStyle(Elements children){
		StringBuilder styleName = new StringBuilder();
		Iterator<Element> it = children.iterator();
		while(it.hasNext()){
			Element t = it.next();
			styleName.append(t.tagName());
			styleName.append("-");
		}
		return styleName.toString();
	}
	
	private void createStyleNode(ElementNode node,Elements es){
		StyleNode sn = new StyleNode();
		
		int len = es.size();
		for(int i=0;i<len;i++){
			Element t = es.get(i);
			ElementNode en = new ElementNode(t.tagName());
			compareNode(en, t);
			sn.addElementNode(en);
		}
		node.addStyle(sn);
	}
	
	private void mergeStyleNode(ElementNode node,Elements children,String styleName){
		StyleNode sn = node.getStyleByName(styleName);
		sn.addCount();
		int index = 0,len=children.size();
		while(index<len){
			compareNode(sn.getElementNode(index),children.get(index));
			index++; 
		}
	}
	
	private void recordAttr(ElementNode node,Element dom){
		node.recordText(dom.ownText());
		
		if(dom.hasAttr("href")){
			node.addAttr("href", dom.attr("href"));
		}
		
		if(dom.hasAttr("src")){
			node.addAttr("src", dom.attr("src"));
		}
		
		if(dom.hasAttr("id")){
			node.addAttr("id", dom.attr("id"));
		}
		
		if(dom.hasAttr("class")&&"news_content".equals(dom.attr("class"))){
			node.addAttr("class", dom.attr("class"));
		}
	}
	
}
