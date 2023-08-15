package defaultpackage;

import java.util.*;
 public class JSONParser 
 { 
	 public static Map parseJson(String jsonString)
	 {
		 Map resultMap = new HashMap<>(); 
	 // Remove curly braces and split JSON into key-value pairs
	 jsonString = jsonString.replace("{", "").replace("}", "");
	 String[] keyValuePairs = jsonString.split(","); 
	 for (String pair : keyValuePairs)
	 { 
		 String[] keyValue = pair.split(":"); 
		 String key = keyValue[0].trim();
		 String value = keyValue[1].trim();
		 // Check if the value is a string, number, or nested JSON 
		 if (value.startsWith("\"") && value.endsWith("\""))
		 { 
			 resultMap.put(key, value.substring(1, value.length() - 1));
			 }
		 else if (value.matches("\\d+"))
			 { 
				 resultMap.put(key, Integer.parseInt(value));
				 }
		 else if (value.startsWith("{") && value.endsWith("}")) 
		 { 
			 resultMap.put(key, parseJson(value)); 
			 } } 
	 return resultMap; } 
	 public static void main(String[] args)
	 {
		 String jsonString = "{\"name\": \"Manju\", \"age\": 20, \"address\": {\"city\": \"New York\", \"zip\": \"10001\"}}";
	 Map resultMap = parseJson(jsonString); 
	 System.out.println(resultMap); }
	
	
	 }
 
