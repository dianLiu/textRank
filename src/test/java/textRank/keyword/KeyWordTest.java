package textRank.keyword;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.io.IOUtil;

import model.keyword.HeadVertex;
import model.keyword.WordGraph;
import phrase.keyword.ConvergenceAlgorithm;
import phrase.keyword.GraphBuilder;
import phrase.keyword.TextRankAlgorithm;
import phrase.keyword.WordExtractAlgorithm;
import util.PropertiesReader;

public class KeyWordTest {

	public static void main(String[] args){
		String res = PropertiesReader.getValue("contentPath");
		String content = IOUtil.readTxt(res);
		Document doc = Jsoup.parse(content);
		
		GraphBuilder b = new GraphBuilder();
		WordGraph graph = b.buildGraph(doc);
		
        WordExtractAlgorithm conAlgorithm = new ConvergenceAlgorithm();
        WordExtractAlgorithm degAlgorithm = new TextRankAlgorithm();

        degAlgorithm.extractAlgorithm(graph);
        
		List<HeadVertex> list = graph.getNodeList();
		Collections.sort(list, new Comparator<HeadVertex>() {

			public int compare(HeadVertex o1, HeadVertex o2) {
				// TODO Auto-generated method stub
				return o1.getImpact()>=o2.getImpact()?-1:1;
			}
		});
		
		System.out.println(list.toString());
		System.out.println(HanLP.extractKeyword(doc.text(), 8));
	}
	

}
