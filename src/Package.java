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
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


//TODO
/* Methode To Fetch ClassName
 * IsNew Update
 * IsVariable Update
 * 
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
	static void extractClassNamesMethode(String methodLine , ArrayList<String>classNames) {
        String line = methodLine.trim().substring(methodLine.indexOf("(")+1,methodLine.indexOf(")")+1);  		
        
		//System.out.println(line);
		Pattern pattern = Pattern.compile("\\b\\w+\\b(?!\\s*,)(?!\\s*\\))"); 

        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String className = matcher.group();
            classNames.add(className);
        }
        pattern = Pattern.compile("(?<=<\\s*)\\b\\w+\\b");
        Matcher matcher2 = pattern.matcher(line); 
        while (matcher2.find()) {
            String className = matcher2.group();
            classNames.add(className);
        }	        
        	
 }
	
	
	
	
	static boolean IsMethode(String Line) {
	    return Line.matches("(?!else\\s+if\\s*\\()\\w*\\s*\\w*\\s*\\w*\\s*\\w+\\s+\\w+\\s*\\([^()]*\\)\\s*(;|\\{)?\\s*");
	}
	
	
	static boolean IsBracket(String Line) {
		String line = Line.trim();
		line = line.replace(" ", "");
		return line.equals("{") || line.equals("}");
	}
	
	static int CountLines(File file) {
		int NbLine = 0;
		String Line;
		 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            while ((Line = reader.readLine() )!= null) {
	            	if(!Line.isEmpty() && !IsComments(Line)) {
	            	
	            		 RemoveQoute(Line);
	            	
	                RemoveComment(Line);
	            	
	                if(!IsBracket(Line)) {          	
	                	++NbLine;
	                }
	            	}
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
		return NbLine;
	}
	
	
	static ArrayList<String> SubFilesList(File file){
		ArrayList<String> SubList = new ArrayList<String>();
		File[] SubFiles = file.listFiles();
		if(SubFiles!=null) {
		for(File index : SubFiles) {
			if(index.isFile()) {
				SubList.add(index.getName().replace(".java", ""));
			}
		}
		}
		return SubList;	
	}
	
	static ArrayList<File> SubDirectoryList(File file){
		ArrayList<File> SubList = new ArrayList<File>();
		File[] SubFiles = file.listFiles();
		if(SubFiles!=null) {
		for(File index : SubFiles) {
			if(index.isDirectory()) {
				SubList.add(index);
			}
		}
		}
		return SubList;
	}
	
	
	static void RemoveComment(String line) {
		 if(ContainsComment(line)) {
	     int index = line.indexOf("\\\\");
	     line = line.substring(0,index);
		 }
			
	 } 
	
	 static boolean ContainsComment(String Line) {
		 boolean b = false;
		 String line = Line;
		 if(line.contains("\\\\") ) {
			 b = true;
		 }
		 return b;
	 }
	 
   static boolean IsComments(String Line) {
       
   	boolean b  = false;
   	if(Line.startsWith("//") || Line.startsWith("/*") || Line.startsWith("*")||Line.startsWith("*/")) {
   		b = true;
   	}
   	
   	return b;
   }
   
   static boolean IsQoute(String Line) {
   	boolean b = false;
   	if(Line.contains("\"") || !Line.trim().startsWith("if")) {
   		b = true;
   	}
   	
   	return b;
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
   
   
   static boolean IsNew(String Line) {
   	boolean b  = false;
   	String line = Line;	
   	if(line.replaceAll(" ","").contains("=new") || line.replaceAll(" ", "").contains("(new")) {
   		
   		b = true;
   	}
   	
   	return b;
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
		String line = Line.trim();
		int BI = line.indexOf("new")+3;
		line = line.substring(BI);
		line = line.trim();
		line = line.replaceAll(" ", "");
		int BS = line.indexOf("(");
		return line.substring(0,BS);
	}
	
	static String CatchException(String Line) {
		String line = Line;
		line = line.trim();
		int BI = line.indexOf("(")+1;
		line = line.substring(BI);
		line = line.trim();
		int BS = line.indexOf(" ");
		return line.substring(0,BS);
	}
	
	
	static boolean IsImport(String Line) {
		return Line.startsWith("import ");
	}
	
	
	
	
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

	
	static Boolean IsThrows(String Line) {
		String line = Line;
		line = line.replaceAll(" ","");
		if(line.contains(")")) {
		return line.substring(line.lastIndexOf(")")).contains("throws");
		}
		else {
			return false;
		}
	}
	
	static Boolean IsThrow(String Line) {
		String line = Line;
		line = line.replaceAll(" ","");
		line = line.trim();
		return line.startsWith("throw");
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
		
	
	static void NewClassNames(String line , ArrayList<String> List){
	      line = line.trim();
		//line = line.substring(line.indexOf("=")+1);
		line  = line.replaceAll(" ", "");
		int BI;
		int BS;
		while(line.contains("new")) {
			line = line.substring(line.indexOf("new"));
			BI = line.indexOf("new")+3;
			BS = line.indexOf("(");
			//System.out.println(line);
			//System.out.println(BI);
			//System.out.println(BS);
			List.add(line.substring(BI,BS));
			line = line.replaceAll(Pattern.quote(line.substring(BI-3,BS+1)), "");
		}
		
		
	}
	
	static void NewClassNamesCollection(String line , ArrayList<String> List){
		String Patterne;
		int BI = 0;
		int Case = 0;
		int BS = 0;
		line = line.trim();
		line = line.replaceAll(" ","");
		line = line.substring(0,line.indexOf("="));
		//System.out.println(line);
		while(line.contains("<") ||  line.contains(">") || line.contains("[") ||  line.contains("]")) {
			//System.out.println("Inside While");
			if(line.contains("<")) {
				BI= line.indexOf("<");
				Case =1;
			}
			else if(line.contains("[")) {
             BI = line.indexOf("[");
             Case= 2;
		}   
			//System.out.println(BI);
			List.add(line.substring(0,BI));
			//System.out.println(List);
			
		    if(Case ==1) {
		    	BS = line.lastIndexOf(">");
		    }
		    else if (Case == 2) {
		    	BS = line.lastIndexOf("]");
		    }
		    //System.out.println(BS);
		    Patterne = line.substring(BI+1,BS);
		    line = line.substring(0, BI) + line.substring(BS + 1);
		   // System.out.println(line);
		    
		    
		   // System.out.println(Patterne);
		    while( Patterne.contains("<") ||  Patterne.contains(">") || Patterne.contains("]")  || Patterne.contains("[") || Patterne.contains(",")) {
		    	if(Patterne.contains("<")) {
		    		BI= Patterne.indexOf("<");
					Case =1;
		    	}
		    	else if (Patterne.contains("[")) {
		    		BI= Patterne.indexOf("[");
					Case =2;
		    	}
		    	else if(Patterne.contains(",")) {
		    		BI = Patterne.indexOf(",");
		    		Case = 3;
		    	}
		    	if(Case == 3) {
		    		
		    	List.add(Patterne.substring(0,BI));
		    	List.add(Patterne.substring(BI+1));
		    	Patterne = Patterne.substring(0,BI) + Patterne.substring(BI+1);
		    	}
		    	else {
		    	 if(Case ==1) {
				    	BS = Patterne.indexOf(">");
				    }
				    else if (Case == 2) {
				    	BS = Patterne.indexOf("]");
				    }
		    	  List.add(Patterne.substring(BI+1,BS));
		    	  Patterne = Patterne.replaceAll(Pattern.quote(Patterne.substring(BI,BS+1)),"");
		    	}
		    	}
		    line = line.replaceAll(Pattern.quote(Patterne),"");
			
		}
		             
		
	}
	
	  
	 static ArrayList<ImportStatus> update(File file , ArrayList<ImportStatus> ImportList){
		 	 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	            	line = line.trim();
	                RemoveQoute(line);
	                RemoveComment(line);
	                ArrayList<String> ListImportFromFile = new ArrayList<String>();
	            	if(!line.isEmpty() && !IsComments(line) && !IsImport(line)) {
	            	if(IsMethode(line)) {
	            		//System.out.println(line);
	            		  extractClassNamesMethode(line, ListImportFromFile);
	            		  //System.out.println(ListImportFromFile);
	            	}
	            	else if(IsCatch(line)) {
	            		//System.out.println(line);
	            		ListImportFromFile.add(CatchException(line));
	            		//System.out.println(CatchException(line));
	            	}
	            	for(ImportStatus Import : ImportList) {
	            		for(String ImportFile :  ListImportFromFile) {
	            			if(Import.ImportName.equals(ImportFile)) {
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


	
	static ArrayList<ImportStatus> ImportFetch(File file){
		ArrayList<ImportStatus> ImportList = new ArrayList<ImportStatus>();
		String Line;
		 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            while ((Line = reader.readLine() )!= null) {
	            	Line = Line.trim();
	            	if(IsImport(Line)) {
	            		int index = Line.indexOf(';');
	            		ImportList.add(new ImportStatus(Line.substring(7, index),0));
	            		
	            	}
	            	else {
	            		break;
	            	}
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
		return ImportList;
	}
	
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
