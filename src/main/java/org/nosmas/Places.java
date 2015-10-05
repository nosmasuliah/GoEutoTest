package org.nosmas;

//import org.codehaus.jettison.json.CDL;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

/*
 * A class representing the results returned
 * contains the list of results as a JSONArray
 * */

public class Places {

	private JSONArray listOfElements;

	/*Class Constructor 
	 * @param jsonArray - the returned Json result as a string
	 * @throws JSONException if the string is not in the form of JSONArray
	 * @throws PlaceNotFoundException if the JSONArrray was empty
	 * it also removes un-necessary nodes from the objects
	 * */
	public Places(String jsonArray)throws JSONException,PlaceNotFoundException {
		this.listOfElements = new JSONArray(jsonArray);
		if(listOfElements.length() < 1) throw new PlaceNotFoundException(jsonArray);
		clean();
	}

	/*
	 * returns all the JSONObjects in the array as a String
	 * */
	@Override
	public String toString() {
		return listOfElements.toString();
		}
	
	public String toCSV(){
		
		StringBuilder buffer = new StringBuilder();
		try {
			
			JSONArray header = listOfElements.getJSONObject(0).names();
			for(int i=0; i < header.length(); i++){
				
				if(i>0 )	buffer.append(",");
				buffer.append(header.getString(i));
				
			}
			
			buffer.append("\n");
			
			for(int i=0; i<listOfElements.length(); i++){ //iterate over the rows
				for(int j=0; j<header.length();j++){	//iterate over the columns
					if(j>0) buffer.append(",");
					buffer.append(listOfElements.getJSONObject(i).getString(header.getString(j)));
					
				}
				buffer.append("\n");
			}
			
			return buffer.toString();
			
		} catch (JSONException e) {
			return null;
		}
		
			
		
		//return null;
		
	}
	
	
	/*removes un-necessary details, the keys to be removed are hard coded*/
	public void clean(){
		try{
		for(int i=0; i < listOfElements.length(); i++){
			listOfElements.getJSONObject(i).remove("key");
			listOfElements.getJSONObject(i).remove("fullName");
			listOfElements.getJSONObject(i).remove("iata_airport_code");
			listOfElements.getJSONObject(i).remove("country");
			listOfElements.getJSONObject(i).remove("locationId");
			listOfElements.getJSONObject(i).remove("inEurope");
			listOfElements.getJSONObject(i).remove("countryCode");
			listOfElements.getJSONObject(i).remove("coreCountry");
			listOfElements.getJSONObject(i).remove("distance");
			listOfElements.getJSONObject(i).put("latitude", listOfElements.getJSONObject(i).getJSONObject("geo_position").getDouble("latitude"));
			listOfElements.getJSONObject(i).put("longitude", listOfElements.getJSONObject(i).getJSONObject("geo_position").getDouble("longitude"));
			listOfElements.getJSONObject(i).remove("geo_position");
			
		}
		}catch(JSONException JE){}
	}
	
	public void display(){
		System.out.println(toString());
	}
	
	
	
	
}
