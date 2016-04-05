package phrase.keyword;

import java.util.List;

import org.jsoup.nodes.Document;

import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;

import model.keyword.HeadVertex;
import model.keyword.WordGraph;

public class GraphBuilder {

	private WordGraph graph;
	private final static int WORD_SPAN = 2;
	
	public GraphBuilder(){
		this.graph = new WordGraph();
	}
	
	public WordGraph buildGraph(Document doc){
		String title = doc.select("title").first().text();
		doc.select("title").remove();
		String content = doc.select("body").first().text();
//		System.out.println(content);
		List<Term> list = NLPTokenizer.segment(content);
		
		int offset,count,len = list.size();
		for(int i=0;i<len;i++){
			Term t = list.get(i);
			if(!isMeaning(t))
				continue;
			
			offset = 1;count=0;
			while(count<WORD_SPAN && i+offset<len){
				Term tmp = list.get(i+offset);
				if(!isMeaning(tmp)){
					offset++;
					continue;
				}else{
//					graph.addEdge(t.word, tmp.word, 1);
					graph.addEdge(t, tmp, 1);
					offset++;
					count++;
				}
			}
		}
		weightGraph(title);
		return this.graph;
	}

	public void weightGraph(String title){
	    weightLoaction(title);
	    graph.weightWordLen();
	}
	
	private void weightLoaction(String title){
		List<Term> list = NLPTokenizer.segment(title);
		for(Term t:list){
			if(!isMeaning(t))
				continue;
			this.graph.addWeight(t.word,1.2);
		}
	}
	
	private boolean isMeaning(Term t){
		if(t.nature == Nature.w || t.nature == Nature.t || t.nature == Nature.c )
			return false;
		if( t.nature == Nature.v ||t.nature ==Nature.k || t.nature == Nature.f)
			return false;
		if(t.nature == Nature.b || t.nature == Nature.r  ||t.nature ==Nature.ug)
			return false;
		if(t.nature == Nature.u || t.nature == Nature.p ||  t.nature == Nature.d )
			return false;
		if(t.nature == Nature.ul || t.nature == Nature.uv || t.nature == Nature.uj  )
			return false;
		if(t.nature == Nature.ug || t.nature == Nature.udeng || t.nature == Nature.uls )
			return false;
		if(t.nature == Nature.e || t.nature == Nature.Mg || t.nature == Nature.nx )
			return false;
		if(t.nature == Nature.m ||t.nature ==Nature.mq|| t.nature == Nature.q )
			return false;
		if(t.nature == Nature.uz ||t.nature ==Nature.cc ||t.nature ==Nature.ng)
			return false;
		if(t.nature == Nature.a)
			return false;
		return true;
	}
}
