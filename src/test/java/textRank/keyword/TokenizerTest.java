package textRank.keyword;

import java.util.List;

import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

public class TokenizerTest {

	public static void main(String[] args){
		String content = "这是件很幸福的事。";
		List<Term> list = NLPTokenizer.segment(content);
		for(Term t:list){
			if(t.nature.compareTo(Nature.w)>=0 && t.nature.compareTo(Nature.wh)<=0 ){
				System.out.println(t.word);
			}
		}
		System.out.println(list);
	}
}
