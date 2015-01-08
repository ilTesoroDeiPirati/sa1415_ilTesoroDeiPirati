package utils;

import impl.Card;
import jason.asSyntax.Literal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Utils {
	
	public static String cardListToString(ArrayList<Card> cards){
		String str;
		if (cards.size() == 0){
			str = "[]";
			return str;
		}
		str = "[";
		int size = cards.size();
		for (int i = 0; i < size - 1; i++) {
			str += cards.get(i).getValue() + ",";
		}
		str += cards.get(size - 1).getValue() + "]";
		return str;
	}
	
	public static String turnToString(String[] turn){
		String str;
		if (turn.length == 0){
			str = "[]";
			return str;
		}
		str = "[";
		int size = turn.length;
		for (int i = 0; i < size - 1; i++) {
			str += turn[i] + ",";
		}
		str += turn[size - 1] + "]";
		return str;
	}
	
	public static int searchTrust(String name, String otherPlayer){
		int trust = 0;
		String[] res = null;
		String pathFile = name + ".txt";
		try{
			BufferedReader in = new BufferedReader(new FileReader(pathFile));
			String line = in.readLine();
			while(line!=null){
				if(line.contains(otherPlayer)){
					res = line.split(":");
					trust = Integer.parseInt(res[1]);
				}
				line = in.readLine();
			}
		}catch(IOException ex){ 
			ex.printStackTrace();
		}
		return trust;	
	}
	
	public static boolean savePlayer(String name, String otherPlayer,int newTrustValue){
		String[] res = null;
		boolean search = false;
		int newValue = 0;
		String pathFile = name + ".txt";
		BufferedWriter out = null;
		try{
			BufferedReader in = new BufferedReader(new FileReader(pathFile));
			String line = in.readLine();
			StringBuilder fileContent = new StringBuilder();
			while(line!=null){
				if(line.contains(otherPlayer)){
					res = line.split(":");
					newValue = (Integer.parseInt(res[1]) + newTrustValue)/2;
					fileContent.append(otherPlayer +":"+newValue+System.getProperty("line.separator"));
					search = true;
				}else{
					fileContent.append(line);
		            fileContent.append(System.getProperty("line.separator"));
				}
				line = in.readLine();
			}
			if(!search){
				FileWriter fw = new FileWriter (pathFile);
				BufferedWriter bw = new BufferedWriter (fw);
				PrintWriter outFile = new PrintWriter (bw);	
				outFile.println(otherPlayer+":"+newTrustValue); 
				outFile.close();
				return true;
			}else{
				FileWriter fstreamWrite = new FileWriter(pathFile);
		        out = new BufferedWriter(fstreamWrite);
		        out.write(fileContent.toString());
		        in.close();
				out.flush();
	            out.close();
	            return true;
			}
		}catch(IOException ex){ 
			ex.printStackTrace();
		}
		return false;
	}
	
	public static int convertAlpha(String alpha){
		
		if(alpha.equals("A")){
			return 1;
		}
		else if(alpha.equals("B")){
			return 2;
		}
		else if(alpha.equals("C")){
			return 3;
		}
		else if(alpha.equals("D")){
			return 4;
		}
		else if(alpha.equals("E")){
			return 5;
		}
		else if(alpha.equals("F")){
			return 6;
		}
		else if(alpha.equals("G")){
			return 7;
		}
		else if(alpha.equals("H")){
			return 8;
		}
		return 0;

	}
	
	public static int indexCards(ArrayList<Card> listCards, Card card){
		ArrayList<Card> listCardsCopy = (ArrayList<Card>) listCards.clone();
		int index = 0;
		boolean found = false;
		for(int i = 0; i < listCardsCopy.size() && !found; i++){
			if(listCardsCopy.get(i).getValue().equals(card.getValue())){
				index = i;
				found = true;
			}
		}
		return index;
		
	}
	
	
}
