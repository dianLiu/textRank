package textRank.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class JsoupTest {

	public static void main(String[] args){
		String html = "<html><body><div><p>p标签的内容</p>div标签特有的内容</div><div></div></body></html>";
		Document doc = Jsoup.parse(html);
		Element body = doc.select("body").first();
		
		double p = 0.1;
		double m = 10;
		double t = -p*Math.log(p)/Math.log(m);
		System.out.println(t);
		//System.out.println(body.children().size());
	}
}
