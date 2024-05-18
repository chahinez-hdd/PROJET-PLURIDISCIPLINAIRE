package application.BackEnd;

import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpression {

	
	 
	//Method to Know If Line Is Bracket Only Line
	static boolean IsBracket(String Line) {
		String line = Line;
		line = line.replace(" ", "");
		return line.equals("{") || line.equals("}");
	}
	
		
	
	
	//Method To Know If Line Is Import
	static boolean IsImport(String Line) {
		return Line.startsWith("import ");
	}
	
	public static boolean IsPackage(String Line) {
		return Line.startsWith("package ");
	}
	
	

	
	static boolean IsAnnotation(String Line) {
		return Line.startsWith("@") && !Line.equals("@Overload") && !Line.equals("@Override") ;
	}
	
	static String FetchAnnotation(String Line) {
		return Line.substring(Line.indexOf("@")+1);
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
			if(!RegularExpression.IsConstructor(line)) {
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

			//Method To Know If Line Is Catch
			static boolean IsCatch(String line) {
			    line = line.trim();
			    String PipeCatch = "(\\s*\\|\\s*\\w+(\\s+\\w+)?\\s*)*";
			    String PatternCatch = "(\\})?\\s*catch\\s*\\(\\s*\\w+(\\s+\\w+)?"+PipeCatch+"\\s*\\)\\s*(\\{)?\\s*";
			    return line.matches(PatternCatch);
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
			//Method To Know If Line Is Throw
	static boolean IsThrow(String line) {
		String MultipleThrowPattern="(,\\s*new\\s+\\w+\\s*\\(([^()]*)?\\)\\s*)*";
		String pattern = "throw\\s+new\\s+\\w+\\s*\\(([^()]*)?\\)"+MultipleThrowPattern+"\\s*;";
				
			return line.matches(pattern);
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

	
}
