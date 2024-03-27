
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;/*
sdsds
*/import java.util.zip.ZipInputStream;


//TODO
/* code formatter
* recursive browse 
* Asterix
*/


public class Package {
	String PackageName;
	ArrayList<FileInfo> FileList = new ArrayList<FileInfo>();
	Package(String PackageName,ArrayList<FileInfo> FileList ){
		this.PackageName = PackageName;
		this.FileList=FileList;
	}
	
	
	static void PrintFileNumberLine(ArrayList<Package> List) {
		for(Package index : List) {
			System.out.println("Package "+index.PackageName);
			if(index.FileList.isEmpty()) {
				System.out.println("No Files");
			}
			   for (FileInfo file : index.FileList) {
		            System.out.println("File Name : " + file.FileName + ",Number Of Line : " + file.FileNumberLine);
		           if(file.FileImport.isEmpty()) {
		        	   System.out.println("No Imports");
		           }
		           else {
		            System.out.println(file.FileImport.size()+" Imports : ");
		            System.out.println("Used Import");
		            for(ImportStatus Import : file.FileImport) {
		            	if(Import.ImportStatus == 1) {
		            	System.out.println(Import.ImportName);
		            	}
		            }
		            System.out.println("Unused Import : ");
		            for(ImportStatus Import : file.FileImport) {
		            	if(Import.ImportStatus == 0) {
		            	System.out.println(Import.ImportName);
		            	}
		            }
		           }
		        }
		}
		
		
	}
	
	
	
	         /*ffffffffffffffffffffffff
	 * 
	 * 
	 */
	 
	 static boolean FinishedComment(String Line) {
		 Line = Line.trim();
		return Line.contains("/*") && Line.contains("*/") ;	 
	 }
	static boolean NotFinishedComment(String Line) {
		Line = Line.trim();
		return Line.contains("/*") && !Line.contains("*/") ;
	}
	
	static boolean ContainsOpeningComment(String Line) {
		return Line.startsWith("/*");
	}
	

	static boolean ContainsClosingComment(String Line) {
		String line = Line.replaceAll(" ", "");
		return line.endsWith("*/");
	}
	
	static String CodeOpeningComment(String Line) {
		return Line.substring(0,Line.indexOf("/*"));
	}
	
	static String CodeClosingComment(String Line) {
		return Line.substring(Line.indexOf("*/")+2);
	}
	
	
	static void JumpComment (String Line,ArrayList<String> List,BufferedReader reader) {
		if(!ContainsOpeningComment(Line)) {
			List.add(CodeOpeningComment(Line));
		}
		//System.out.println(Line);
		try {
			while ((Line = reader.readLine()) != null) { 
			Line = Line.trim();
			Line=RemoveQoute(Line);
			//System.out.println(Line);
			if(Line.contains("*/")) {
				break;
			}
			}
		}
		catch(IOException e) {
			
		}
		if(!ContainsClosingComment(Line)) {
			List.add(CodeClosingComment(Line));
		}
	}
	
	static boolean IsCommentOnlyCompleted(String Line) {
		Line = Line.trim();
	    String singleLineCommentPattern = "//.*"; 
	    String multiLineCommentPatternCompleted = "/\\*((?!(\\*/))[^\\n]|\\n)*(\\*/)";
	return Line.matches(multiLineCommentPatternCompleted)||Line.matches(singleLineCommentPattern);
	}
	
    //Detect If Line Is Variable
	static boolean IsVariable(String line) {
	    String variablePattern = "\\b\\w+\\b\\s+\\w+\\s*(=\\s*.+)?;?";
	    String ArrayPattern="\\w+\\s*(\\[\\s*\\]\\s*){1,2}\\w+\\s*(=\\s*.+)?;?";
	    String CollectionPattern="\\w+\\s*\\<[\\s\\S]+?>\\s*\\w+\\s*(=\\s*.+)?;?";
	    return line.matches(variablePattern) || line.matches(CollectionPattern) || line.matches(ArrayPattern);
	}
	
	
	//Fetch Class Name From Var Line
	static void ExtractVarClassNames(String VarLine , ArrayList<String> classNames) {
	    Pattern pattern = Pattern.compile("\\s*(\\w+)\\s*(?=[\\[<>])|(?<=[<])\\s*(\\w+)|^(\\w+)");
	    Matcher matcher = pattern.matcher(VarLine);
	    while (matcher.find()) {
	        String className = matcher.group(1);
	       if(className==null) {
	    	   className = matcher.group(2);
	    	   if(className==null) {
	    		   className = matcher.group(3);
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
	    return Line.matches("(?!else\\s+if\\s*\\()\\w*\\s*\\w*\\s*\\w*\\s*\\w+\\s+\\w+\\s*\\([^()]*\\)\\s*(;|\\{)?\\s*");
	}
	
	//Method to Know If Line Is Bracket Only Line
	static boolean IsBracket(String Line) {
		String line = Line;
		line = line.replace(" ", "");
		return line.equals("{") || line.equals("}");
	}
	
	//CountLine Excluding Comment only Line , Empty Line , Bracket Only Line
	static int CountLines(File file) {
		int NbLine = 0;
		String Line;
		 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            while ((Line = reader.readLine() )!= null) {
	            	if(!Line.isBlank() && !IsCommentOnlyCompleted(Line)) {
                    Line = Line.trim();
                    Line =  RemoveQoute(Line);
	                if(!IsBracket(Line)) {
                    if(ContainsComment(Line) ) {
	                	++NbLine;
	                }
                    else if(FinishedComment(Line) ) {
                    	if(!ContainsClosingComment(Line) || !ContainsOpeningComment(Line)) {
                    		
                    		++NbLine;
                    	}
                    }
                    else if(NotFinishedComment(Line)) {
                    	NbLine+=JumpComment(Line,reader);
                    }
                    else {
                    	
                    
                    	++NbLine;
                    }
                    
	                }
	                
                    
	            	}
	        
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
		return NbLine;
	}
	
	static int JumpComment (String Line,BufferedReader reader) {
		int NbLine = 0;
		if(!ContainsOpeningComment(Line)) {
	    ++NbLine;
		}
		System.out.println(Line);
		try {
			while ((Line = reader.readLine()) != null) { 
			Line = Line.trim();
			Line = RemoveQoute(Line);
			System.out.println(Line);
			if(Line.contains("*/")) {
				break;
			}
			}
		}
		catch(IOException e) {
			
		}
		if(!ContainsClosingComment(Line)) {
	   ++NbLine;
		}
		return NbLine;
	}
	

	
	//Method To Remove Comment From Line
	static String RemoveComment(String line) {
	     int index = line.indexOf("//");
	     line = line.substring(0,index);
		 
			return line;
	 } 
	
	//Method To Know If Line Contains Comment
	 static boolean ContainsComment(String Line) {	
		 return Line.contains("//") ; 
	 }
	 
	 
	
   
   //Method That Detect Qoute In Line
   static boolean IsQoute(String Line) {
		Line = Line.trim();
		return Line.contains("\"");
	}
   
   //Method To RemoveQoute From Line
   static String RemoveQoute(String line) {
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
   	return line;
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
	
	
	
	//Method To Know If A Giving Path Is A Java Project
	static int IsJavaProject(String PathProject) {
		if(!(new File (PathProject).exists())) {
			System.out.println("Error Path Doesnt even Exist");
			return -1;
		}
		if(!PathProject.endsWith("\\src") && !PathProject.endsWith("\\src\\")) {
			PathProject+="\\src";
		}
		File SrcFile = new File(PathProject);
		if(!SrcFile.exists()) {
			System.out.println("Src Folder Doesn't Exist");
		return -2;
		}
		else {
		File [] ListFile = SrcFile.listFiles();
		if(ListFile.length == 0) {
			System.out.println("Src Folder Is Empty");
        return 0;
		}
		else {
		return RecursiveDir(ListFile);	
		}
	}
	}
		//Recursive Method To Browse The Src/ Directory
		static int RecursiveDir(File[] ListFile) {
			for(File FILE : ListFile) {
				if(FILE.isDirectory()) {
			    File[] SubDir = FILE.listFiles();
			    return RecursiveDir(SubDir);
				}
				else if(FILE.isFile()){
					if(FILE.getName().endsWith(".java")) {
						return 1;
					}
				}
			}
			
			return 2;
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
    		//System.out.println(line);
    		  extractClassNamesMethode(line, ListImportFromFile);
    		  //System.out.println(ListImportFromFile);
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
    	}
	}
	
	
	  //Method To Update Flags Of ImportStatus(used , not used)
	 static ArrayList<ImportStatus> update(File file , ArrayList<ImportStatus> ImportList){
		 	 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	            line = line.trim();
	            line =  RemoveQoute(line);
	                ArrayList<String> ListImportFromFile = new ArrayList<String>();
	                ArrayList<String> ListCode=new ArrayList<String>();
	                
	                if(!line.isBlank() && !line.isEmpty() && !IsCommentOnlyCompleted(line) && !IsImport(line)) {
	            	if(ContainsComment(line)) {
	            	//	System.out.println(line);
	            		line = RemoveComment(line);
	            	}
	            	else {
	            		
	            		if(FinishedComment(line)) {
	            			if(!ContainsOpeningComment(line)) {
	            				ListCode.add(CodeOpeningComment(line));
	            			}
	            			if(!ContainsClosingComment(line)) {
	            				ListCode.add(CodeClosingComment(line));
	            			}
	            		}
	            		else if (NotFinishedComment(line)) {
	            			JumpComment(line,ListCode,reader);
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
	static ArrayList<ImportStatus> ImportFetch(File file){
		ArrayList<ImportStatus> ImportList = new ArrayList<ImportStatus>();
		String Line;
		 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            while ((Line = reader.readLine() )!= null) {
	            	Line = Line.trim();
	            	Line = RemoveQoute(Line);
	            	ArrayList<String> ListCode=new ArrayList<String>();
	            	if(!Line.isBlank() && !Line.isEmpty() && !IsCommentOnlyCompleted(Line)) {
	            		if(ContainsComment(Line)) {
	    	            	//System.out.println(line);
	    	            		Line = RemoveComment(Line);
	    	            	}
	            		else {
	            			if(FinishedComment(Line)) {
		            			if(!ContainsOpeningComment(Line)) {
		            				ListCode.add(CodeOpeningComment(Line));
		            			}
		            			if(!ContainsClosingComment(Line)) {
		            				ListCode.add(CodeClosingComment(Line));
		            			}
		            		}
	            			else if (NotFinishedComment(Line)) {
		            			JumpComment(Line,ListCode,reader);
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
	
	//Browse src/ Dir And Call The Metrics (Will Change cause Browsing Isn't Recursive)
	static ArrayList<Package> NumberLineJavaProject(String ProjectPath) {
		String SrcPath = ProjectPath+"/src";
		File SrcDirectory = new File(SrcPath);
		File[] files = SrcDirectory.listFiles();
		ArrayList<Package> List = new ArrayList<Package>();
		ArrayList<FileInfo> defaultPackageFilesList = new ArrayList<FileInfo>();
		for(File index : files) {
			
			 if(index.isDirectory()) {
	                File[] subFiles = index.listFiles();
	                ArrayList<FileInfo> FileList = new ArrayList<FileInfo>();
	            
	                for (File subFile : subFiles) {
	                    if (subFile.isFile()) {
	                    	System.out.println("bhjd");
	                    	String FileName =subFile.getName();
	                    	int NumberLine = CountLines(subFile);
	                    	ArrayList<ImportStatus> ListImport = new ArrayList<ImportStatus>();
	                    	ListImport = ImportFetch(subFile);
	                    	update(subFile,ListImport);
	                    	FileList.add(new FileInfo(FileName,NumberLine,ListImport));
	               
	                    	
	                    }
	                }
	                List.add(new Package(index.getName(), FileList));
	                
	               
	            }
			 else if (index.isFile()) {
				 
				 defaultPackageFilesList.add(new FileInfo(index.getName(), CountLines(index),update(index,ImportFetch(index))));
				 
			 }
		}
		if(!defaultPackageFilesList.isEmpty()) {
			List.add(new Package("(Default Package)", defaultPackageFilesList));
		}
		return List;
		
	}
	
	
}
