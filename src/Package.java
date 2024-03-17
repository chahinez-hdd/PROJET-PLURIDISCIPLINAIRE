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
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


//TODO
/* Methode To Fetch ClassName
 * Methode To Fetch Comment
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
	
	static int CountLines(File file) {
		int NbLine = 0;
		String Line;
		 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            while ((Line = reader.readLine() )!= null) {
	            	if(!Line.isEmpty()) {
	            	NbLine++;
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
	
	
	static String RemoveComment(String Line) {
		 String line = Line;
	     int index = line.indexOf("\\\\");
	     line = line.substring(0,index);
			return line;
	 } 
	
	 static boolean ContainsComment(String Line) {
		 boolean b = false;
		 String line = Line;
		 if(IsQoute(line)) {
			 line = RemoveQoute(line);
		 }
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
   	if(Line.contains("\"")) {
   		b = true;
   	}
   	
   	return b;
   }
   
   static String RemoveQoute(String Line) {
   	String line = Line;
   	String qoute;
   	while(line.contains("\"")) {
    
   		int BI = line.indexOf("\"");
   		 int BS = line.indexOf("\"",BI+1);
   		 qoute = line.substring(BI,BS+1);
   		
   		 line = line.replaceAll(Pattern.quote(qoute), "");
   		
   		
   	}
   	
   		return line;
   	
   }
   
   static boolean IsNew(String Line) {
   	boolean b  = false;
   	String line = Line;
   	if(IsQoute(Line))
   	{
   	
   		 line = RemoveQoute(Line);
   		 
   		 
   	}	
   	if(line.replaceAll(" ","").contains("=new")) {
   		
   		b = true;
   	}
   	
   	
   	
   	return b;
   }

	
	
   static ArrayList<String> NewClassNames(String Line){
		ArrayList<String> List  =new ArrayList<String>();
		String Patterne;
		String line = Line;
		int BI = 0;
		int Case = 0;
		int BS = 0;
		line = line.trim();
		line = line.replaceAll(" ","");
		line = line.substring(0,line.indexOf("="));
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
			List.add(Line.substring(0,BI));
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
		    System.out.println(line);
		    
		    
		    System.out.println(Patterne);
		    while(Patterne.contains("<") ||  Patterne.contains(">") || Patterne.contains("]")  || Patterne.contains("[") || Patterne.contains(",")) {
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
				    	BS = Patterne.lastIndexOf(">");
				    }
				    else if (Case == 2) {
				    	BS = Patterne.lastIndexOf("]");
				    }
		    	  List.add(Patterne.substring(BI+1,BS));
		    	  Patterne = Patterne.replaceAll(Pattern.quote(Patterne.substring(BI,BS+1)),"");
		    	}
		    	}
		    line = line.replaceAll(Pattern.quote(Patterne),"");
			
		}
		             
		
		return List;
	}
	
	static ArrayList<ImportStatus> ImportStatusUpdate(File file, ArrayList<ImportStatus> ListImport) {
	    String Line;
	    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	        while ((Line = reader.readLine()) != null) {
	            if (!Line.trim().contains("import")) {
	                for (ImportStatus index : ListImport) {
	                    if (!index.ImportName.contains("*")) {
	                        int LastIndex = index.ImportName.lastIndexOf(".");
	                        if (Line.contains(index.ImportName.substring(LastIndex + 1))) {
	                 
	                            index.ImportStatus = 1;
	                        }
	                    } else {
	                        String JdkPath = "C:\\Users\\DELL\\AppData\\Local\\Programs\\Eclipse Adoptium\\jdk-17.0.8.101-hotspot\\lib\\src.zip";
	                        String PathExtracted = "C:\\Users\\DELL\\Metrics";
	                      //  unzipFolder(JdkPath,PathExtracted);
	                        if (index.ImportName.startsWith("java") && (!index.ImportName.contains("awt") || !index.ImportName.contains("rmi") || !index.ImportName.contains("sql") || !index.ImportName.contains("applet"))) {
	                        	PathExtracted = PathExtracted + "\\java.base\\java\\";
	                            int BI = index.ImportName.indexOf(".") + 1;
	                            int BS = index.ImportName.lastIndexOf(".");
	                            String PackageName = index.ImportName.substring(BI, BS);
	                            String PackageName1;
	                            if (PackageName.contains(".")) {
	                                PackageName1 = PackageName.replace(".", "\\");
	                            } else {
	                                PackageName1 = PackageName;
	                            }
	                            PathExtracted = PathExtracted+PackageName1;
	                            
	                           File extractedDirectory = new File(PathExtracted );
	                            ArrayList<File> SubDir = new ArrayList<>();
	                            SubDir = SubDirectoryList(extractedDirectory); // Use extracted directory File object
	                            ArrayList<String> SubFile = new ArrayList<>();
	                            SubFile = SubFilesList(extractedDirectory); // Use extracted directory File object
	                            if (!SubFile.isEmpty()) {
	                                for (String javaClass : SubFile) {
	                                    if (Line.contains(javaClass)) {
	                                    	//System.out.println(index.ImportName);
	                                    	//System.out.println(javaClass);
	                                        index.ImportStatus = 1;
	                                    }
	                                }
	                            }

	                            if (!SubDir.isEmpty()) {
	                                for (File idk : SubDir) {
	                                    File[] filename = idk.listFiles();
	                                    for (File idk2 : filename) {
	                                        if (Line.contains(idk2.getName().replace(".java", ""))) {
	                                        //	System.out.println(index.ImportName);
	                                        	//System.out.println(idk2.getName().replace(".java", ""));
	                                            index.ImportStatus = 1;
	                                        }
	                                    }

	                                }
	                            }
	                        }
	                    }
	                }
	            }

	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return ListImport;
	}

	
	static ArrayList<ImportStatus> ImportFetch(File file){
		ArrayList<ImportStatus> ImportList = new ArrayList<ImportStatus>();
		String Line;
		 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            while ((Line = reader.readLine() )!= null) {
	            	if(Line.trim().startsWith("import")) {
	            		Line = Line.trim();
	            		int index = Line.indexOf(';');
	            		ImportList.add(new ImportStatus(Line.substring(7, index),0));
	            		
	            	}
	            	if(Line.contains("class")) {
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
	                    	FileList.add(new FileInfo(subFile.getName(),CountLines(subFile),ImportStatusUpdate(subFile,ImportFetch(subFile))));
	               
	                    	
	                    }
	                }
	                List.add(new Package(index.getName(), FileList));
	                
	               
	            }
			 else if (index.isFile()) {
				 
				 defaultPackageFilesList.add(new FileInfo(index.getName(), CountLines(index),ImportStatusUpdate(index,ImportFetch(index))));
				 
			 }
		}
		if(!defaultPackageFilesList.isEmpty()) {
			List.add(new Package("(Default Package)", defaultPackageFilesList));
		}
		return List;
		
	}
	
	
}
