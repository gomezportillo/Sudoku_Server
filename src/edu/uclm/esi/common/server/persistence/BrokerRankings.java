package edu.uclm.esi.common.server.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class BrokerRankings {
	
	private static BrokerRankings yo;
	private final String rankingsDir = "data/rankings/sudokuRanking.csv";
	private File file;
	private Hashtable<String, Integer> rankings; //email, #wins
	
	private BrokerRankings() {
		rankings = new Hashtable<String, Integer>();
		file = new File(rankingsDir);
		
	}
	
	public static BrokerRankings get() {
		if (yo==null)
			yo=new BrokerRankings();
		return yo;
	}
	
	private void loadRankings(){
		try{
			FileReader fr = new FileReader (file.getAbsolutePath());
			BufferedReader br = new BufferedReader(fr);

			String player = null;
			while((player = br.readLine()) != null) {
				
			}
				

			br.close();
		} catch (IOException ioe){
			System.out.println(ioe); 
		}
	}
	
	private void saveRankings(){
		
	}
	
	private String getRankings() {
		return null;
	}
}