package application;

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
import java.util.zip.ZipEntry;/*
sdsds
*/import java.util.zip.ZipInputStream;


//TODO
/* code formatter 3(most likley no)                    
* Asterix 2
* javafx 1
*/


public class Package {
	String PackageName;
	ArrayList<FileInfo> FileList = new ArrayList<FileInfo>();
	ArrayList<Package> SubPackges = new ArrayList<Package>();
	Package(String PackageName,ArrayList<FileInfo> FileList ){
		this.PackageName = PackageName;
		this.FileList=FileList;
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
	/*
	 * 
	 * 
	 * 
	 * 
	 */

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
		    String ThrowsPattern = "(\\s*throws\\s+\\w+)?"; // Making the throws clause optional
		    String MethodPattern = "(?!return\\s+)(?!else\\s+if\\s*\\()\\w*\\s*\\w*\\s*\\w*\\s*\\w+\\s+\\w+\\s*\\([^()]*\\) " + ThrowsPattern + "\\s*(;|\\{|\\{\\s*\\})?\\s*";
		    String ConstructorPattern = "(?!while\\s*\\()(?!catch\\s*\\()(?!for\\s*\\()(?!if\\s*\\()(?!return\\s+)(?!else\\s+if\\s*\\()\\w*\\s*\\w+\\s*\\([^()]*\\)" + ThrowsPattern + "\\s*(\\{|\\{\\s*\\})?\\s*";
		    return Line.matches(MethodPattern) || Line.matches(ConstructorPattern);
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
	//	System.out.println(Line);
		try {
			while ((Line = reader.readLine()) != null) { 
			Line = Line.trim();
			Line = RemoveQoute(Line);
			//System.out.println(Line);
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
	
	
	
	static boolean IsJavaPackageNotEmpty(File FileDir) {
		File[]FileDirList = FileDir.listFiles();
		
		for(File file : FileDirList) { 
			if(file.isFile()) {
				if(file.getName().endsWith(".java")) {
					return true;
				}
			}
			
		}
		for(File file : FileDirList) {
			 if (file.isDirectory() && file.listFiles()!=null) {
				return IsJavaPackageNotEmpty(file);
			}
		}
		return false;
	}
	
	static boolean IsDefaultPackage(File[] SrcFiles) {
	    boolean noNamedPackages = true;
	    for (File file : SrcFiles) {
	        if (file.isDirectory() && IsJavaPackageNotEmpty(file)) {
	            noNamedPackages = false;
	            break;
	        }
	    }
	    return noNamedPackages;
	}
	
	static void FetchJavaFile(File PackageDir,ArrayList<Package>ListPackage) {
		File[]FileList = PackageDir.listFiles();
		if(FileList.length!=0) {
		ArrayList<FileInfo> ListInfoFile=new ArrayList<FileInfo>();
		ArrayList<Package> SubPackages = new ArrayList<>(); // Store sub-packages
        //loop to fetch java file of package
		for(File file : FileList) {
        	if( file.isFile() && file.getName().endsWith(".java")) {
        		ListInfoFile.add(new FileInfo(file.getName(),0,null));
        	}
        	
        }
        ListPackage.add(new Package(PackageDir.getName(),ListInfoFile));
        //loop to fetch SubPackages That Arent Empty
        for(File file : FileList) { 
        	if(file.isDirectory() && file.listFiles()!=null) {
        		   FetchJavaFile(file, SubPackages); // Recursive call to fetch sub-packages
           	
        }
      }
        ListPackage.get(ListPackage.size()-1).SubPackges.addAll(SubPackages);
	}
		
 }
	
	static void FetchJavaFileNoPackage(File[]SrcFile,ArrayList<Package>ListPackage) {
		ArrayList<FileInfo>ListInfoFile=new ArrayList<FileInfo>();
		for(File file : SrcFile) {
			if(file.getName().endsWith(".java")) {
				ListInfoFile.add(new FileInfo(file.getName(),0,null));
			}
		}
		ListPackage.add(new Package("Default Package",ListInfoFile));
	}
	
	static void FetchSrcJavaFile(File[]SrcFile,ArrayList<Package>ListPackage) {
		if(!IsDefaultPackage(SrcFile)) {
		for(File file : SrcFile) {
			//System.out.println(file.getName());
				if(file.isDirectory() && file.listFiles()!=null &&IsJavaPackageNotEmpty(file)) {
					//System.out.println(file.getName());
					FetchJavaFile(file,ListPackage);
				}
			}
			}
		else {
			FetchJavaFileNoPackage(SrcFile,ListPackage);
		}
	}
	   
	

	private static void printTree(ArrayList<Package> packageList, int depth) {
	    for (Package pkg : packageList) {
	        // Print package name with appropriate indentation
	        for (int i = 0; i < depth; i++) {
	            System.out.print("|  ");
	        }
	        System.out.println("|-- " + pkg.PackageName);

	        // Print files within the package
	        for (FileInfo fileInfo : pkg.FileList) {
	            // Indent file representation appropriately
	            for (int i = 0; i <= depth; i++) {
	                System.out.print("|  ");
	            }
	            System.out.println("|-- " + fileInfo.FileName);
	        }

	        // Recursively print sub-packages
	        printTree(pkg.SubPackges, depth + 1);
	    }
	}

	static void PrintRecursive(ArrayList<Package>ListPackage) {
		for(Package pkg : ListPackage) {
			System.out.println(pkg.PackageName);
			for(FileInfo file : pkg.FileList) {
				System.out.println(" "+file.FileName);
			}
			if(pkg.SubPackges!=null) {
				PrintRecursive(pkg.SubPackges);
			}
	}
	}	
	static void BrowseFileMetric(String PathProject) {
		Scanner sc = new Scanner(System.in);
		if(!PathProject.endsWith("\\src") && !PathProject.endsWith("\\src\\")) {
			PathProject+="\\src";
		}
		//System.out.println(IsJavaProject(PathProject));
		if(IsJavaProject(PathProject) == 1) {
			//System.out.println("hehe");
			ArrayList<Package> ListPackage = new ArrayList<Package>();
			File ProjectFile = new File(PathProject);
			File[] SrcFile =ProjectFile.listFiles();
			FetchSrcJavaFile(SrcFile,ListPackage);
	        int choice;
			do {
			//	PrintRecursive(ListPackage);
				printTree(ListPackage,0);
	        System.out.println("0. TO EXIT");
	        System.out.println("1. TO CHOOSE FILE");
	        choice = sc.nextInt();
	        if(choice==1) {
	        	System.out.println("INPUT FILE NAME : ");
	        	String FileName = sc.next();
	        }
	        }while(choice!=0);
			
			    
		}
		
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
			    if(SubDir.length!=0) {
			    return RecursiveDir(SubDir);
			    }
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
    		System.out.println(line);
    		System.out.println(ListImportFromFile);
    	}
	}
	/*              */
	/*
	 *
	 */
	
	
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
	
	
	
	
	
	
	
}
