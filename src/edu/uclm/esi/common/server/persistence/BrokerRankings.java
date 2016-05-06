package edu.uclm.esi.common.server.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import org.omg.Messaging.SyncScopeHelper;

import com.maco.juegosEnGrupo.server.dominio.Match;

public class BrokerRankings {

	private static BrokerRankings yo;
	private final String sudokuDir = "C:\\Users\\pedroma\\workspace\\web_eclipse\\JuegosEnGrupo\\data\\rankings\\sudokuRanking.csv";
	private Hashtable<String, Integer> rankings; //email, #wins

	private BrokerRankings() {
		this.rankings = new Hashtable<String, Integer>();
		this.loadRankings();

	}

	public static BrokerRankings get() {
		if (yo==null)
			yo=new BrokerRankings();
		return yo;
	}

	private void loadRankings(){
		try{
			FileReader fr = new FileReader (new File(sudokuDir));
			BufferedReader br = new BufferedReader(fr);

			String line = null;
			while((line = br.readLine()) != null) {
				String [] player = line.split(";");
				this.rankings.put(player[0], Integer.valueOf(player[1]));
			}
			br.close();

		} catch (IOException ioe){
			//System.out.println("loadAllGames - " + ioe);
		}

	}

	public String getRankings() {
		this.loadRankings();
		String msg = "";
		Enumeration<String> enumer = this.rankings.keys();
		
		String email;
		while (enumer.hasMoreElements()) {
			email = enumer.nextElement(); 
			msg += email +" " + this.rankings.get(email) + ";";
		}
		return msg;
	}

	public void addAVictory(String email){
		int victories = this.rankings.get(email);
		this.rankings.put(email, victories+1);
		this.saveRankings();
	}

	public void saveRankings(){
		String txtRankings = this.getRankings();
		try{
			File file = new File(sudokuDir);
			FileWriter fw = new FileWriter (file, false);
			fw.write(txtRankings);
			fw.close();
		}catch(IOException e){
			//System.out.println(e);
		}

	}

}