package textRank.keyword;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.io.IOUtil;

import model.keyword.WordGraph;
import phrase.keyword.GraphBuilder;
import util.PropertiesReader;

public class KeyWordTest {

	public static void main(String[] args){
		String res = PropertiesReader.getValue("contentPath");
		String content = IOUtil.readTxt(res);
		Document doc = Jsoup.parse(content);
		
		GraphBuilder b = new GraphBuilder();
		WordGraph graph = b.buildGraph(doc);
		
		
		System.out.println(graph.toString());
		System.out.println(HanLP.extractKeyword(doc.text(), 8));
	}
}
