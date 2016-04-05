package phrase.keyword;

import java.util.List;

import model.keyword.HeadVertex;
import model.keyword.Vertex;
import model.keyword.WordGraph;

public class Floyd {
	
	public static int[][] floyd(WordGraph graph){
		List<HeadVertex> list = graph.getNodeList();
		HeadVertex temp ;
		List<Vertex> edgeList;
		int i,j,k,n = list.size();
		
		int[][] distance = new int[n][n];
		
		for(i=0;i<n;i++){
		    temp = list.get(i);
		    edgeList = temp.getNodes();
		    
		    for(j=0;j<n;j++){
		    	distance[i][j] = Integer.MAX_VALUE;
		    	if(i==j)
		    		distance[i][j] = 0;
		    }
		    for(Vertex v : edgeList){
		    	distance[i][v.getIndex()] = 1;
		    }
		}
		
		for(k=0;k<n;k++){
			for(i=0;i<n;i++){
				for(j=0;j<n;j++){
					if(distance[i][j]>(distance[i][k]+distance[k][j])){
						distance[i][j] = distance[i][k]+distance[k][j];
					}
				}
			}
		}
		return distance;
	}
	
	public static int[][] nodeContract(int[][] distance,HeadVertex node,int k){
		int i,j,n=distance.length;
		int[][] contract = new int[n][n];
		
		for(i=0;i<n;i++){
			for(j=0;j<n;j++){
				if(i==j && i==k){
					contract[i][j] = 0;
				}else if(i!=j && (i==k || j==k)){
					contract[i][j] = distance[i][j]-1;
				}else{
					if(distance[i][j] == distance[i][k]+distance[k][j]){
						contract[i][j] = distance[i][j]-2;
					}else if(distance[i][j]+1 == distance[i][k]+distance[k][j]){
						contract[i][j] = distance[i][j]-1;
					}else{
						contract[i][j] = distance[i][j];
					}
				}
			}
		}
		
		return contract;
	}
	
}
