package application.BackEnd;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javafx.scene.control.TextField;
//TODO
/* code formatter 3(most likley no)                    
* Asterix 2(doing it)
* javafx (doing it)
* resolve issue with import of other subpackages (\\w+.)
*/


public class Package {
	public String PackageName;
	public ArrayList<FileInfo> FileList = new ArrayList<FileInfo>();
	public ArrayList<Package> SubPackges = new ArrayList<Package>();
	Package(String PackageName,ArrayList<FileInfo> FileList ){
		this.PackageName = PackageName;
		this.FileList=FileList;
	}
	
	
		
	
		
	         /*ffffffffffffffffffffffff
	 * 
	 * 
	 */

	
	public static boolean classExists(String asterixImport, String className) {
	    String[] arrayPackage = {"java.util.", "java.util.regex.", "java.math.","java.net.","java.io.","com.sun.crypto.provider.","com.sun.security.ntlm."};
	    
	    for (String pkg : arrayPackage) {
	        try {
	            Class.forName(pkg + className);
	            return true; // Return true if class is found
	        } catch (ClassNotFoundException e) {
	            // Class not found in this package, continue searching
	        }
	    }
	    
	    // None of the classes were found, return false
	    return false;
	}
	
	
	static boolean IsAnnotation(String Line) {
		return Line.startsWith("@") && !Line.equals("@Overload") && !Line.equals("@Override") ;
	}
	
	static String FetchAnnotation(String Line) {
		return Line.substring(Line.indexOf("@")+1);
		}
	
    //Detect If Line Is Variable
   static boolean IsVariable(String line) {
	   String PattrneAcessModfiers="(?:private\\s+|protected\\s+|public\\s+)?";
		String PattrneStatic="(?:static\\s+)?";
		String PatterneFinal="(?:final\\s+)?";
	    String variablePattern = PattrneAcessModfiers+PattrneStatic+PatterneFinal+"(?!return\\s+)\\w+\\s+\\w+\\s*(=\\s*.+)?;?";
	    String ArrayPattern= PattrneAcessModfiers+PattrneStatic+PatterneFinal+"\\w+\\s*(\\[\\s*\\]\\s*){1,2}\\w+\\s*(=\\s*.+)?;?";
	    String CollectionPattern=  PattrneAcessModfiers+PattrneStatic+PatterneFinal+"\\w+\\s*\\<[\\s\\S]+?>\\s*\\w+\\s*(=\\s*.+)?;?";
	    return line.matches(variablePattern) || line.matches(CollectionPattern) || line.matches(ArrayPattern);
	}
	
	
	//Fetch Class Name From Var Line
	static void ExtractVarClassNames(String VarLine , ArrayList<String> classNames) {
		String PattrneAcessModfiers="(?:private\\s+|protected\\s+|public\\s+)?";
		String PattrneStatic="(?:static\\s+)?";
		String PatterneFinal="(?:final\\s+)?";
		Pattern PatterneVar = Pattern.compile(PattrneAcessModfiers+PattrneStatic+PatterneFinal+"\\s*(\\w+)\\s*(?=[\\[<>])|(?<=[<])\\s*(\\w+)|"+PattrneAcessModfiers+PattrneStatic+PatterneFinal+"(\\w+)\\s+\\w+|(\\w+)\\.");
		Matcher matcher1 = PatterneVar.matcher(VarLine);
		
	    while (matcher1.find()) {
	    	 String className = matcher1.group(1);
		       if(className==null) {
		    	   className = matcher1.group(2);
		    	   if(className==null) {
		    		   className = matcher1.group(3);
		    	   }
		    	   if(className==null) {
		    		   className=matcher1.group(4);
		    	   }
		    	   
		    	   }

	        classNames.add(className);
	    }
	}
	
	
	
	
	
	//Method To Extract Class Names From Method Prototype Line
	 static void extractClassNamesMethode(String methodLine,ArrayList<String> classNames) { 
		 String line = methodLine.substring(methodLine.indexOf("(")+1,methodLine.indexOf(")")+1); 
	        Pattern pattern = Pattern.compile("(?<=<\\s*)\\b\\w+\\b|\\b\\w+\\b(?!\\s*,)(?!\\s*\\))"); // Regular expression to match class names

	        Matcher matcher = pattern.matcher(line);
	        while (matcher.find()) {
	            String className = matcher.group();
	            classNames.add(className);
	        }
	        
	        
	 }	
	
	
  //Method to know If Line Is A Method Prototype	
	 static boolean IsMethode(String Line) {
		    String PattrneAcessModfiers="(?:private\\s+|protected\\s+|public\\s+)?";
			String CollectionPatterne="(<[\\s\\S]+?>|(\\[\\s*\\]\\s*){1,2})?";
		    String PatterneNonAcessModifier="(?:static\\s+final\\s+|static\\s+|final\\s+|abstract\\s+)?";
		    String ThrowsPattern = "(\\s*throws\\s+\\w+)?"; // Making the throws clause optional
		    String MethodPattern = PattrneAcessModfiers + PatterneNonAcessModifier + "(?!else)\\w+\\s*" + CollectionPatterne+ "\\s+(?!if)\\w+\\s*\\([^()]*\\)\\s*" + ThrowsPattern + "\\s*(;|\\{|\\{\\s*\\})?\\s*";
		    String ConstructorPattern = PattrneAcessModfiers+"(?!(return|catch|if|while|for))\\w+\\s*\\([^()]*\\)\\s*"+ ThrowsPattern +"\\s*(\\{|\\{\\s*\\})?\\s*";
		    return Line.matches(MethodPattern) || Line.matches(ConstructorPattern);
		}
	//Method to Know If Line Is Bracket Only Line
	static boolean IsBracket(String Line) {
		String line = Line;
		line = line.replace(" ", "");
		return line.equals("{") || line.equals("}");
	}
	
	
	    //Method To Know If Line Is New Line Instantiation
	static boolean IsNew(String Line) {
		String trimmedLine = Line.trim();
		String pattern = "(\\(|\\=)\\s*new\\s+";

	    return trimmedLine.matches(".*"+pattern+".*");
	}
	
    //Method To Extract ClassNames From NewLine	
	static void ExtractNewClassNames(String NewLine , ArrayList<String> classNames) {
	    Pattern pattern = Pattern.compile("new\\s+(\\w+)|(?:<|,)\\s*(\\w+)");
	    Matcher matcher = pattern.matcher(NewLine);
	    while (matcher.find()) {
	        String className = matcher.group(1);
	        if (className == null) { // If the first capturing group didn't match
	            className = matcher.group(2); // Use the second capturing group
	        }
	        classNames.add(className);
	    }
	}
	
 //Method To Fetch Exception From Throws
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
	
	//Method To Fetch Exception From Throw	
	static String ThrowException(String Line) {
		String line = Line;
		int BI = line.indexOf("new")+3;
		line = line.substring(BI);
		line = line.trim();
		line = line.replaceAll(" ", "");
		int BS = line.indexOf("(");
		return line.substring(0,BS);
	}
	
	//Method To Fetch Exception From Catch
	static String CatchException(String Line) {
		String line = Line;
		int BI = line.indexOf("(")+1;
		line = line.substring(BI);
		line = line.trim();
		int BS = line.indexOf(" ");
		return line.substring(0,BS);
	}
	
	//Method To Know If Line Is Import
	static boolean IsImport(String Line) {
		return Line.startsWith("import ");
	}
	
	
	//Method To Know If Line Is Throws
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
	//Method To Know If Line Is Throw
	static Boolean IsThrow(String Line) {
		return Line.startsWith("throw ");
	}
	
	//Method To Know If Line Is Catch
	 static Boolean IsCatch(String Line) {
		   String line = Line;
		   line = line.replaceAll(" ","");
			line = line.trim();
			if(line.startsWith("}")) {
				line = line.replaceAll("}","");
			}
			return line.startsWith("catch");
	   }
		
	static void IsAll(ArrayList<String> ListImportFromFile , String line) {
		if(IsMethode(line)) {
    		System.out.println(line);
    		  extractClassNamesMethode(line, ListImportFromFile);
    		  System.out.println(ListImportFromFile);
    	}
    	else if(IsNew(line)) {
    		//System.out.println(line);
    		ExtractNewClassNames(line,ListImportFromFile);
    	//	System.out.println(ListImportFromFile);
    	}
    	else if(IsCatch(line)) {
    		//System.out.println(line);
    		ListImportFromFile.add(CatchException(line));
    		//System.out.println(CatchException(line));
    	}
    	else if(IsThrow(line)) {
    		ListImportFromFile.add(ThrowException(line));
    	}
    	else if(IsThrows(line)) {
    		ListImportFromFile.add(ThrowsException(line));
    	}
    	else if (IsVariable(line)) {
    		ExtractVarClassNames(line,ListImportFromFile);
    	//	System.out.println(line);
    		//System.out.println(ListImportFromFile);
    	}
    	else if(IsAnnotation(line)) {
    		ListImportFromFile.add(FetchAnnotation(line));
    	}
	}
	/*              */
	/*
	 *
	 */
	public static boolean IsPackage(String Line) {
		return Line.startsWith("package ");
	}
	
	
	  //Method To Update Flags Of ImportStatus(used , not used)
	 public static ArrayList<ImportStatus> update(File file , ArrayList<ImportStatus> ImportList){
		 	 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	            line = line.trim();
	            line =  Qoute.RemoveQoute(line);
	                ArrayList<String> ListImportFromFile = new ArrayList<String>();
	                ArrayList<String> ListCode=new ArrayList<String>();
	                
	                if(!line.isBlank() && !line.isEmpty() && !IsBracket(line)&& !Comment.IsCommentOnlyCompleted(line) && !IsImport(line) &&!IsPackage(line)) {
	            	//System.out.println(line);
	                	if(Comment.ContainsComment(line)) {
	            	//	System.out.println(line);
	            		line = Comment.RemoveComment(line);
	            	}
	            	else {
	            		
	            		if(Comment.FinishedComment(line)) {
	            			if(!Comment.ContainsOpeningComment(line)) {
	            				ListCode.add(Comment.CodeOpeningComment(line));
	            			}
	            			if(!Comment.ContainsClosingComment(line)) {
	            				ListCode.add(Comment.CodeClosingComment(line));
	            			}
	            		}
	            		else if (Comment.NotFinishedComment(line)) {
	            			Comment.JumpComment(line,ListCode,reader);
	            		}
	            		if(!ListCode.isEmpty()) {
	            			for(String code : ListCode) {
	            				IsAll(ListImportFromFile,code);
	            			}
	            		}
	            	}
	            	
	            	if(ListCode.size()==0) {
	            		IsAll(ListImportFromFile,line);
	            	}
	            	for(ImportStatus Import : ImportList) {
	            		for(String ImportFile :  ListImportFromFile) {
	            			if(Import.ImportName.substring(Import.ImportName.lastIndexOf(".")+1).equals(ImportFile)) {
	            			
	            				Import.ImportStatus = 1;
	            			}
	            		}
	            	}
	               }	               
	                
	            }
	        } catch (IOException e) {
	            e.printStackTrace(); // Handle any IO exceptions
	        }
		 return ImportList;
	 }


	//Method To Fetch Import From File
	public static ArrayList<ImportStatus> ImportFetch(File file){
		ArrayList<ImportStatus> ImportList = new ArrayList<ImportStatus>();
		String Line;
		 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            while ((Line = reader.readLine() )!= null) {
	            	Line = Line.trim();
	            	Line = Qoute.RemoveQoute(Line);
	            	ArrayList<String> ListCode=new ArrayList<String>();
	            	if(!Line.isBlank() && !Line.isEmpty() && !Comment.IsCommentOnlyCompleted(Line) && !IsPackage(Line)) {
	            		//System.out.println(Line);
	            		if(Comment.ContainsComment(Line)) {
	    	            	//System.out.println(line);
	    	            		Line = Comment.RemoveComment(Line);
	    	            	}
	            		else {
	            			if(Comment.FinishedComment(Line)) {
		            			if(!Comment.ContainsOpeningComment(Line)) {
		            				ListCode.add(Comment.CodeOpeningComment(Line));
		            			}
		            			if(!Comment.ContainsClosingComment(Line)) {
		            				ListCode.add(Comment.CodeClosingComment(Line));
		            			}
		            		}
	            			else if (Comment.NotFinishedComment(Line)) {
		            			Comment.JumpComment(Line,ListCode,reader);
		            		}

	            			if(!ListCode.isEmpty()) {
	            				for(String code : ListCode) {
	            					if(IsImport(code)) {
	            	            		int index = code.indexOf(';');
	            	            		ImportList.add(new ImportStatus(code.substring(7, index),0));
	            	            		
	            	            	}
	            					else {
	            						break;
	            					}
	            				}
	            			}
	            		}
	            		if(ListCode.isEmpty()) {
	            			if(IsImport(Line)) {
        	            		int index = Line.indexOf(';');
        	            		ImportList.add(new ImportStatus(Line.substring(7, index),0));
        	            		
        	            	}
	            			else {
	            				break;
	            			}
	            		}
	            	}
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
		return ImportList;
	}
	
	
	
	
	
	
	
}
