import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Methods {
	//fonction tedi fichier w te9rah w t3amer arraylist ta3 ExceptionInfo
	//fonction tedi arraylist ta3 mo9bil w tbedel les flag 
	static ArrayList<ExceptionInfo> fetchException (File fichier){
		ArrayList<ExceptionInfo> list = new ArrayList<>();
		
		
		try (BufferedReader reader = new BufferedReader(new FileReader(fichier))){
			
			String line;
			while ((line = reader.readLine())!= null) {
				line =line.trim();
				RemoveQoute(line);
				if (!line.isEmpty() && !IsComments(line)) {
					if(line.contains("/*")) {
						ArrayList<String> com = new ArrayList<>();
						com.add(line.substring(0,line.indexOf("/*")));
						if (!line.contains("*/")) {
							 if (JumpComment(reader)!=null) {
								 com.add(JumpComment(reader)); 
							 }
							
						}else {
							line.replace(" ", "");
							 if(line.indexOf("*/")+2!=-1) {
								 com.add(line.substring(line.indexOf("*/")+2));
							 }
							
						}
						
					}
					
				}
				
			}
		} catch (IOException e) {
	
			e.printStackTrace();
		}
				
		return list;
	}
	static Boolean IsThrows(String Line) {
		String line = Line;
		line = line.replaceAll(" ","");
		if(line.contains(")")) {
		return line.substring(line.lastIndexOf(")")).contains("throws ");
		}
		else {
			return false;
		}
	}
	static Boolean IsCatch(String Line) {
		   String line = Line;
		   line = line.replaceAll(" ","");
			line = line.trim();
			if(line.startsWith("}")) {
				line = line.replaceAll("}","");
			}
			return line.startsWith("catch");
	   }
	
	static Boolean IsThrow(String Line) {
		return Line.startsWith("throw ");
	}
	
	static String ThrowsException(String Line) {
		String line = Line.trim();
		int BI = line.indexOf("throws")+6;
		line = line.substring(BI);
		line = line.trim();
		line = line.replaceAll(" ", "");
		int BS = 0;
		if(line.contains("{")) {
		 BS = line.indexOf("{");
		 return line.substring(0,BS).replaceAll(" ", "");
		}
		else {
			return line.replaceAll(" ", "");
		}
		
	}
	
	static String ThrowException(String Line) {
		String line = Line;
		int BI = line.indexOf("new")+3;
		line = line.substring(BI);
		line = line.trim();
		line = line.replaceAll(" ", "");
		int BS = line.indexOf("(");
		return line.substring(0,BS);
	}
	
	static String CatchException(String Line) {
		String line = Line;
		int BI = line.indexOf("(")+1;
		line = line.substring(BI);
		line = line.trim();
		int BS = line.indexOf(" ");
		return line.substring(0,BS);
	}
	 static boolean ContainsComment(String Line) {
		 boolean b = false;
		 String line = Line;
		 if(line.contains("\\\\") ) {
			 b = true;
		 }
		 return b;
		 
	 }
	 
	static String JumpComment(BufferedReader read) {
	     String line=null;
	     try {
			while((line=read.readLine())!=null ) {
				
				line = line.trim();
				RemoveQoute(line);
				if(line.contains("*/")) {
					break;
				}
				
				 
			 }
			
		} catch(IOException e) {
			
			e.printStackTrace();
		}
	     line.trim();
	     line.replace(" ","");
	     line.indexOf("*/");
	     int n= line.indexOf("*/")+2;
	     if (n==-1) {
	    	 return null;
	     }else {
	     return line.substring(line.indexOf("*/")+2);
	     }	
	 } 
	 static boolean IsComments(String Line) {
	       
		   	boolean b  = false;
		   	if(Line.startsWith("//")  ) {
		   		b = true;
		   	}
		   	
		   	return b;
		   }
	 
	 static boolean IsQoute(String Line) {
			Line = Line.trim();
			return Line.contains("\"");
		}
	 
	 static void RemoveQoute(String line) {
		   	String qoute;
		   	if(IsQoute(line)) {
		   	while(line.contains("\"")) {
		             
		   		int BI = line.indexOf("\"");
		   		 int BS = line.indexOf("\"",BI+1);
		   		  if (BS == -1) {
		   			  line = line.replace("\"", "");// Check if the closing quote was found
		   	            break; // Exit the loop if not found to avoid StringIndexOutOfBoundsException
		   	        }
		   		  qoute = line.substring(BI,BS+1);
		   		 line = line.replaceAll(Pattern.quote(qoute), "");
		   		
		   		
		   	}
		   	}
		   	
		   }
	 static void RemoveComment(String line) {
		 if(ContainsComment(line)) {
	     int index = line.indexOf("\\\\");
	     line = line.substring(0,index);
		 }
			
	 } 

}
