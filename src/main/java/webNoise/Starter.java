package webNoise;

import java.io.File;

import util.PropertiesReader;

public class Starter {


	public static void main(String[] args){
		String base = PropertiesReader.getValue("siteBase");
		
		NoiseHandler noise = new NoiseHandler(base);
		File webBase = new File(base);
		File[] files = webBase.listFiles();
		for(File f:files){
			noise.removeNoise(f.getAbsolutePath());
		}
	}
}
