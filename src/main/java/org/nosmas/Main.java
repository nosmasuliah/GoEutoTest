package org.nosmas;


import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.codehaus.jettison.json.JSONException;



public class Main {

	public static void main(String[] args)  {
		if(args.length !=1){
			usage();
			System.exit(1);
		}
		
		String input = args[0].trim().toLowerCase();
		
		 File file=new File(input+".csv"); 
		 
		 if(file.exists()){ // 
			 try {
				String finalOutput = FileUtils.readFileToString(file);
				System.out.println(finalOutput);
				System.exit(0);
			} catch (IOException e) {
//				System.out.println("Error reading local File \n" + e.getMessage() );
//				System.exit(1);
			}
		 }
		
		RequestReader reader = new RequestReader();
		String result = reader.read(input);
		try{
		Places places = new Places(result);
		
		
		System.out.println(places.toCSV());
		 
		 
		FileUtils.writeStringToFile(file, places.toCSV());
		reader.close();
		
		}catch(PlaceNotFoundException PNF){ // this happens if an empty JSONArray was returned
			System.err.println("no results were found for the plcace " + input);	
			
		}catch(JSONException JE){ //this happens if an error message (which is a JSONObject instead of JSONArray)
			System.err.println("");
		} catch (IOException e) {
//			System.out.println("Error reading local File \n" + e.getMessage() );
//			System.exit(1);
		}
		
		

	}
	
	
	public static void usage(){
		
		System.out.println("please use GoEuroTest place-to-be-searched");
		
	}

}
