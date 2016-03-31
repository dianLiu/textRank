package webNoise;

import java.io.File;
import java.util.HashMap;

import org.jsoup.nodes.Element;

import com.hankcs.hanlp.corpus.io.IOUtil;

import model.ElementNode;
import model.StyleNode;
import model.StyleTree;
import net.sf.json.JSONObject;

public class NoiseHandler {
	private String basePath;
	private StyleTree template;
	private final static String TEMPLATE_NAME = "template.txt";
	private final static int PAGE_COUNT = 15;
	
	public NoiseHandler(String path){
		this.basePath = path;
		this.template = loadStyleTree(basePath);
	}
	
	public void removeNoise(String webPath){
		String resDir = basePath +File.separator+"res"+File.separator;
		checkResDir(resDir);
		
		File webRoot = new File(webPath);
		File[] files = webRoot.listFiles();
		for(File f:files){
			if(!f.isDirectory()){
				String html = IOUtil.readTxt(f.getAbsolutePath());
			    Element body = template.eliminateNoise(html);
			    
			    String fileName = "res_"+webRoot.getName()+"_"+f.getName();
			    IOUtil.saveTxt(resDir+fileName, body.html());
			}
		}
	}

	private static StyleTree loadStyleTree(String path){
		String templatePath = path+File.separator+TEMPLATE_NAME;
		
		StyleTree tree = null;
		if(IOUtil.isFileExists(templatePath)){
			String str = IOUtil.readTxt(templatePath);
			
			HashMap<String,Class> map = new HashMap<String, Class>();
			map.put("EList", ElementNode.class);
			map.put("SList", StyleNode.class);
			
			JSONObject obj = JSONObject.fromObject(str);
			tree = (StyleTree) JSONObject.toBean(obj, StyleTree.class,map);
		}else{
			tree = createStyleTree(path);
		}
		return tree;
	}
	
	private static StyleTree createStyleTree(String path){
        StyleTree tree = new StyleTree();
		
		File base = new File(path);
		File[] files = base.listFiles();
		for(File file:files){
			if(file.isDirectory()){
				File[] temp = file.listFiles();
				if(temp == null || temp.length < PAGE_COUNT)
					continue;
				for(int i=0;i<PAGE_COUNT;i++){
					tree.buildStyleTree(IOUtil.readTxt(temp[i].getAbsolutePath()));
				}
				break;
			}
		}
		tree.buildTemplate();
		
		String savePath = path+File.separator+TEMPLATE_NAME;
		String content = JSONObject.fromObject(tree).toString();
		IOUtil.saveTxt(savePath, content);
		return tree;
	}
	
	private void checkResDir(String dir){
		File dirFile = new File(dir);
		if(dirFile.exists() && dirFile.isDirectory())
			return;
		dirFile.mkdir();
	}
}
