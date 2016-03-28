package phrase;

import java.text.DecimalFormat;
import java.util.List;

import model.ElementNode;
import model.StyleNode;

public class TreeAnalyzer {

	/** 衰减因子 */
	private final static double q = 0.9;
	
	/**保留有效位*/
	private final DecimalFormat df = new DecimalFormat("#.0000");
	
	/**噪声节点阈值 */
	private final static double NOISE_TL = 0.2;
	
	public boolean markNoise(ElementNode node){
	    List<StyleNode> slist = node.getSList();
	    for(StyleNode s:slist){
	    	List<ElementNode> elist = s.getEList();
	    	for(ElementNode en:elist){
	    		if(markNoise(en) == false){
	    			node.setNoise(false);
	    			return false;
	    		}
	    	}
	    }
	    if(node.eImportance < NOISE_TL){
	    	node.setNoise(true);
	    }else{
	    	node.setNoise(false);
	    }
	    return node.isNoise();
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
		
		node.eImportance = format((1-factor)*nodeImp + factor*childrenImp);
	}
	
	private double calElementNodeImp(ElementNode node,int m){
		if(m == 1) return 1.0;
		double res = 0.0,p;
		List<StyleNode> list = node.getSList();
		for(StyleNode s:list){
			p = s.getCount()*1.0/m;
			if(p < 1){
				res -= Math.log(m)/Math.log(p);
			}
		}
		return format(res);
	}
	
	private double calChildrenImp(ElementNode node,int m){
		double childrenComp = 0.0,p=0.0;
		List<StyleNode> list = node.getSList();
		for(StyleNode sn:list){
			if(Double.isNaN(sn.sImportance)){
				calStyleNodeImp(sn);
			}
			p = sn.getCount()*1.0/m;
			childrenComp += sn.sImportance*p;
		}
		return format(childrenComp);
	}
	
	private void calStyleNodeImp(StyleNode sn){
		double temp= 0.0;
		List<ElementNode> eList = sn.getEList();
		for(ElementNode e:eList){
			if(Double.isNaN(e.eImportance))
				calNodeImp(e,sn.getCount());
			temp += e.eImportance;
		}
		temp = temp*1.0/eList.size();
		sn.sImportance = format(temp);
	}
	
	private void calLeafComp(ElementNode en,int m){
		double p = en.getTextCount()*1.0/m;
		if(p==1||m==1){
			en.eImportance = 1;
		}else{
			en.eImportance = format(1+ Math.log(m)/Math.log(p));
		}
	}
	
	private double format(double number){
		String tempValue = df.format(number);
		return Double.valueOf(tempValue);
	}
}
