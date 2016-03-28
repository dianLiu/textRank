
package textRank.html;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import model.ElementNode;
import model.StyleTree;
import net.sf.json.JSONObject;
import phrase.TreeBuilder;
import phrase.WebTypeHandler;
import util.PropertiesReader;

public class Main {

	public static void main(String[] args){
		WebTypeHandler handler = new WebTypeHandler();
		
		String path = PropertiesReader.getValue("docPath");
		File root = new File(path);
		File[] files = root.listFiles();
		/*double max = 0;
		for(File file:files){
			if(!file.isDirectory()){
				System.out.println(file.getName()+"---");
				String html = readFromFile(file);
				max = Math.max(max, handler.parseWebType(html));
			}
		}
		System.out.println(max);*/
		
		StyleTree tree = new StyleTree();
		for(File file:files){
			if(!file.isDirectory()){
				tree.buildStyleTree(readFromFile(file));
			}
		}
		
		/*String html = "<html><body><div><p>p标签的内容</p>div标签特有的内容</div><div></div></body></html>";
		ElementNode e = builder.buildTree(html);*/
		tree.summaryTreeNode();
		//System.out.println(JSONObject.fromObject(e).toString());
		System.out.println(tree.getRoot().toString());
	}
	
	private static String readFromFile(File f){
		StringBuilder str = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(f));
			String temp = null;
			while((temp = reader.readLine())!=null){
				str.append(temp);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return str.toString();
	}
}
