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
	public ArrayList<String> FileNameList = new ArrayList<String>();
	public ArrayList<Package> SubPackges = new ArrayList<Package>();
	Package(String PackageName,ArrayList<String> FileNameList ){
		this.PackageName = PackageName;
		this.FileNameList=FileNameList;
	}
	
	
		
	
		
	         /*ffffffffffffffffffffffff
	 * 
	 * 
	 */

	
	public static boolean classExists(String asterixImport, String className) {
	    
	        try {
	        	Class.forName(asterixImport.substring(0,asterixImport.lastIndexOf("*")) + className);
	            return true; 
	        } catch (ClassNotFoundException e) {
	        	 return false;
	            
	        }
	    
	   
	   
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
	static ArrayList<String> FetchMethodThrowable(String line){
		ArrayList<String> classNames = new ArrayList<String>();
		Pattern ThrowsPattern = Pattern.compile( "\\s*throws\\s+(\\w+)\\s*|(?:\\s*\\,\\s*(\\w+)\\s*)");
	    Matcher matcher = ThrowsPattern.matcher(line);
        while (matcher.find()) {
        	
        	String className = matcher.group(1);
	        if (className == null) { // If the first capturing group didn't match
	            className = matcher.group(2); // Use the second capturing group
	        }
	        classNames.add(className);	            
           
            
        }    
        return classNames;
	}
	
	static ArrayList<String> FetchMethodArgumentType(String line){
		ArrayList<String> classNames= new ArrayList<String>();  
		line = line.substring(line.indexOf("(")+1,line.indexOf(")")+1); 
	        Pattern pattern = Pattern.compile("(?<=<\\s*)\\b\\w+\\b|\\b\\w+\\b(?!\\s*,)(?!\\s*\\))"); // Regular expression to match class names

	        Matcher matcher = pattern.matcher(line);
	        while (matcher.find()) {
	            String className = matcher.group();
	            classNames.add(className);
	        }    
	    return classNames;
	}
	
	static String FetchMethodReturnType(String line) {
		if(!IsConstructor(line)) {
		 String PattrneAcessModfiers="(?:private\\s+|protected\\s+|public\\s+)?";
		 String PatterneNonAcessModifier="(?:static\\s+final\\s+|static\\s+|final\\s+|abstract\\s+)?";
		 Pattern MethodPattern =  Pattern.compile( PattrneAcessModfiers + PatterneNonAcessModifier + "(?!else)(\\w+)\\s*");
		 Matcher matcher = MethodPattern.matcher(line);
		 while(matcher.find())
		 {
			 return matcher.group(1);
		 }
		}
		
		 return null;
		
	}
	 static void extractClassNamesMethod(String line,ArrayList<String> classNames) { 
		 classNames.addAll(FetchMethodArgumentType(line));
		 classNames.addAll(FetchMethodThrowable(line));
		 if(FetchMethodReturnType(line)!=null) {
		 classNames.add(FetchMethodReturnType(line));
		 }
		 }
	 
	 static boolean IsConstructor(String line) {
		    String PattrneAcessModfiers="(?:private\\s+|protected\\s+|public\\s+)?";
			    String ThrowsPattern = "(\\s*throws\\s+\\w+\\s*(\\s*\\,\\s*\\w+\\s*)*)?"; // Making the throws clause optional
			    String ConstructorPattern = PattrneAcessModfiers+"(?!(return|catch|else|while|for|if))\\w+\\s*\\([^()]*\\)\\s*"+ ThrowsPattern +"\\s*(\\{|\\{\\s*\\})?\\s*";
			    return  line.matches(ConstructorPattern);	
		}
		
	 static boolean IsMethod(String line) {
			String PattrneAcessModfiers="(?:private\\s+|protected\\s+|public\\s+)?";
			String CollectionPatterne="(<[\\s\\S]+?>|(\\[\\s*\\]\\s*){1,2})?";
		    String PatterneNonAcessModifier="(?:static\\s+final\\s+|static\\s+|final\\s+|abstract\\s+)?";
		    String ThrowsPattern = "(\\s*throws\\s+\\w+\\s*(\\s*\\,\\s*\\w+\\s*)*)?"; // Making the throws clause optional
		    String MethodPattern = PattrneAcessModfiers + PatterneNonAcessModifier + "(?!else)\\b\\w+\\b\\s*" + CollectionPatterne+ "(\\s*|\\s+)(?!if)\\b\\w+\\b\\s*\\([^()]*\\)\\s*" + ThrowsPattern + "\\s*(;|\\{|\\{\\s*\\})?\\s*";
		    return line.matches(MethodPattern); 
		}
	
  //Method to know If Line Is A Method Prototype	
	 static boolean IsMethodPrototype(String Line) {
		    return IsConstructor(Line) || IsMethod(Line);
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
	
	
	//Method To Fetch Exception From Throw	
	static ArrayList<String>ThrowException(String line){
		ArrayList<String> classNames = new ArrayList<String>();
		Pattern ThrowsPattern = Pattern.compile("(?:\\s*|\\s+)new\\s+(\\w+)\\s*\\(");
	    Matcher matcher = ThrowsPattern.matcher(line);
        while (matcher.find()) {
        	
        	String className = matcher.group(1);
	        
	        classNames.add(className);	            
            
        }    
        return classNames;	
	}
	
	//Method To Fetch Exception From Catch
	static ArrayList<String> CatchException(String line) {
	    ArrayList<String>classNames = new ArrayList<String>();
		Pattern pattern = Pattern.compile("\\(\\s*(\\w+)|\\|\\s*(\\w+)");
	    Matcher matcher = pattern.matcher(line);
	    while (matcher.find()) {
	        String className = matcher.group(1);
	        if (className == null) { // If the first capturing group didn't match
	            className = matcher.group(2); // Use the second capturing group
	        }
	        classNames.add(className);
	    }
	    
		return classNames;
	}
	
	//Method To Know If Line Is Import
	static boolean IsImport(String Line) {
		return Line.startsWith("import ");
	}
	
	
	//Method To Know If Line Is Throw
	static boolean IsThrow(String line) {
		String MultipleThrowPattern="(,\\s*new\\s+\\w+\\s*\\(([^()]*)?\\)\\s*)*";
		String pattern = "throw\\s+new\\s+\\w+\\s*\\(([^()]*)?\\)"+MultipleThrowPattern+"\\s*;";
		
		return line.matches(pattern);
	}
	
	//Method To Know If Line Is Catch
	static boolean IsCatch(String line) {
	    line = line.trim();
	    String PipeCatch = "(\\s*\\|\\s*\\w+(\\s+\\w+)?\\s*)*";
	    String PatternCatch = "(\\})?\\s*catch\\s*\\(\\s*\\w+(\\s+\\w+)?"+PipeCatch+"\\s*\\)\\s*(\\{)?\\s*";
	    return line.matches(PatternCatch);
	}
		
	static void IsAll(ArrayList<String> ListImportFromFile , String line) {
		if(IsMethodPrototype(line)) {
    		System.out.println(line);
    		  extractClassNamesMethod(line, ListImportFromFile);
    		  System.out.println(ListImportFromFile);
    	}
    	else if(IsNew(line)) {
    		//System.out.println(line);
    		ExtractNewClassNames(line,ListImportFromFile);
    	//	System.out.println(ListImportFromFile);
    	}
    	else if(IsCatch(line)) {
    		//System.out.println(line);
    		ListImportFromFile.addAll(CatchException(line));
    		//System.out.println(CatchException(line));
    	}
    	else if(IsThrow(line)) {
    		ListImportFromFile.addAll(ThrowException(line));
    	}
    	else if (IsVariable(line)) {
    		ExtractVarClassNames(line,ListImportFromFile);
    	//	System.out.println(line);
    		//System.out.println(ListImportFromFile);
    	}
    	else if(IsAnnotation(line)) {
    		ListImportFromFile.add(FetchAnnotation(line));
    	}
    	else if (IsStaticCall(line)) {
    		FetchStaticCall(line,ListImportFromFile);
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
	            			if(!Import.ImportName.contains("*")) {
	            			if(Import.ImportName.substring(Import.ImportName.lastIndexOf(".")+1).equals(ImportFile)) {
	            			
	            				Import.ImportStatus = 1;
	            			}
	            			}
	            			else {
	            				classExists(Import.ImportName,ImportFile);
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
	 
	 static boolean IsStaticCall(String Line) {
		 return Line.contains(".");
	 }
	 
	 static void FetchStaticCall(String Line , ArrayList<String>ClassList) {
		 Pattern pattern = Pattern.compile("(\\w+)\\.");
		    Matcher matcher = pattern.matcher(Line);
		    while (matcher.find()) {
		        String className = matcher.group(1);
		        ClassList.add(className);
		    }
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
	            	            		ImportList.add(new ImportStatus(code.substring(7, index).trim(),0));
	            	            		
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
        	            		ImportList.add(new ImportStatus(Line.substring(7, index).trim(),0));
        	            		
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
