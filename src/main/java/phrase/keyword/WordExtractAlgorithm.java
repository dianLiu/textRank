package phrase.keyword;

import model.keyword.WordGraph;

public abstract class WordExtractAlgorithm {

	public void extractAlgorithm(WordGraph graph){
		long start = System.currentTimeMillis();
		wordExtract(graph);
		long end = System.currentTimeMillis();
		System.out.println("run time:"+(end-start));
	}
	
	public  abstract void wordExtract(WordGraph graph) ;
}
