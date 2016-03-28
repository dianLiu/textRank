package phrase;

import model.HtmlType;

import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

public class WebTypeHandler {

	/** 目录型页面阈值  */
	private final static double HUB_TL = 0.4;
	
	private final static int A_TITLE_TL = 5;
	
	public WebTypeHandler(){
		
	}
	
	public  double parseWebType(String html){
		Document doc = Jsoup.parse(html);
		Element body = doc.select("body").first();
		int contentSize = body.text().length();
		
		int aSize = 0,acount=0;
		List<Element> aList = body.select("a[href]");
		for(Element e :aList){
			int len = e.text().length();
			if(len<A_TITLE_TL){
				contentSize -= len;
				continue;
			}
			aSize += len;
			acount++;
		}
		
		int pContent = 0;
		List<Element> pList = body.select("p");
		for(Element p:pList){
			pContent += p.text().length();
		}
		//System.out.print(aSize*1.0/(aSize+pContent));
		System.out.println("    "+contentSize+":"+pContent+":"+aSize+"("+acount+"):"+aSize*1.0/contentSize);
		
		return aSize*1.0/contentSize;
	/*	if(aSize*1.0/contentSize>HUB_TL){
			return HtmlType.HUB;
		}
		return HtmlType.CONTENT;*/
	}
	
}
