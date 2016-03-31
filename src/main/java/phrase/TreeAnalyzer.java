package phrase;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import model.ElementNode;
import model.StyleNode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TreeAnalyzer {

	/** 衰减因子 */
	private final static double q = 0.9;
	
	/**保留有效位*/
	private final DecimalFormat df = new DecimalFormat("#.0000");
	
	/**噪声节点阈值 */
	private final static double NOISE_TL = 0.3;
	
	public boolean markNoise(ElementNode node){
	    List<StyleNode> slist = node.getSList();
	    int nodeNum=0,noiseNum=0;
	    
	    for(StyleNode s:slist){
	    	List<ElementNode> elist = s.getEList();
	    	for(ElementNode en:elist){
	    		if(markNoise(en))
	    			noiseNum++;
	    		nodeNum++;
	    	}
	    }
	    if(noiseNum == nodeNum){
	    	node.getSList().clear();
		    if(node.geteImportance() < NOISE_TL){
		    	node.setNoise(true);
		    }else{
		    	node.setNoise(false);
		    }
	    }else if(noiseNum<nodeNum){
	    	node.setNoise(false);
	    }

	    return node.isNoise();
	}
	
	public boolean markMain(ElementNode node){
		 List<StyleNode> slist = node.getSList();
		 int nodeNum=0,mainNum=0;
		    
		 for(StyleNode s:slist){
		    List<ElementNode> elist = s.getEList();
		    for(ElementNode en:elist){
		    	if(markMain(en))
		    		mainNum++;
		   		nodeNum++;
		    }
		 }
		 if(nodeNum == 0){
			 node.setMain(!node.isNoise());
		 }else if(nodeNum == mainNum){
			 node.setMain(true);
			 node.getSList().clear();
		 }else{
			 node.setMain(false);
		 }
		 return node.isMain();
	}
	
	public void calNodeImp(ElementNode node,int m){
		if(node.getSList().isEmpty()){
			calLeafComp(node,m);
			return;
		}
		
		int len = node.getSList().size();
		double factor = Math.pow(q,len);
		
		double nodeImp = calElementNodeImp(node,m);
		double childrenImp = calChildrenImp(node,m);
		
		node.seteImportance(format((1-factor)*nodeImp + factor*childrenImp));
	}
	
	private double calElementNodeImp(ElementNode node,int m){
		if(m == 1) return 1.0;
		double res = 0.0,p;
		List<StyleNode> list = node.getSList();
		for(StyleNode s:list){
			p = s.getCount()*1.0/m;
			res -= p*Math.log(p)/Math.log(m);
		}
		return format(res);
	}
	
	private double calChildrenImp(ElementNode node,int m){
		double childrenComp = 0.0,p=0.0;
		List<StyleNode> list = node.getSList();
		for(StyleNode sn:list){
			if(Double.isNaN(sn.getsImportance())){
				calStyleNodeImp(sn);
			}
			p = sn.getCount()*1.0/m;
			childrenComp += sn.getsImportance()*p;
		}
		return format(childrenComp);
	}
	
	private void calStyleNodeImp(StyleNode sn){
		double temp= 0.0;
		List<ElementNode> eList = sn.getEList();
		for(ElementNode e:eList){
			if(Double.isNaN(e.geteImportance()))
				calNodeImp(e,sn.getCount());
			temp += e.geteImportance();
		}
		temp = temp*1.0/eList.size();
		sn.setsImportance(format(temp));
	}
	
	private void calLeafComp(ElementNode en,int m){
		if(m == 1){
			en.seteImportance(1);
			return;
		}
		
		HashMap<String, Integer> map = en.getContentMap();
		double leafComp = 0.0,p;
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()){
			int count = map.get(it.next());
			p = count*1.0/m ;
			leafComp -= p*Math.log(p)/Math.log(m);
		}
		en.seteImportance(leafComp);
//		System.out.println(en.getTagName()+":"+en.geteImportance()+":"+en.getContentMap().toString());
	}
	
	private double format(double number){
		String tempValue = df.format(number);
		return Double.valueOf(tempValue);
	}
}
