package application.BackEnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import application.FrontEnd.MetricController;

public class ImportStatus {
public String ImportName;
public int ImportStatus;
public int ConflictStatus=0;
ImportStatus(String ImportName,int ImportStatus){
	this.ImportName=ImportName;
	this.ImportStatus=ImportStatus;
}

//method to see if package from src
//method to see if from jre
//method fetch src class , jre class
//method to see if conflict double wild card

static boolean ConlictClassLoad(String AsterixImport , String ClassName) {
String FilePath;
	if(MetricController.PathProject.endsWith("\\")) {
		FilePath =MetricController.PathProject+AsterixImport.replace(".", "\\").replace("*",""); 
		}
		else {
			FilePath =MetricController.PathProject+"\\"+AsterixImport.replace(".", "\\").replace("*","");
		}	
	 Path path = Paths.get(FilePath);
	  if(Files.exists(path)) {
          return ConflictClassLoadPackageSrc(FilePath, ClassName);
      } else {
        return  ConflictClassLoadingPackageJre(AsterixImport, ClassName);
      }
}


static boolean ConflictClassLoadingPackageJre(String AsterixImport , String ClassName) {
	
    try {
    	 Class.forName(AsterixImport.replace("*","") + ClassName);
        return true; 
    } catch (ClassNotFoundException e) {
    	 return false;
        
    }
}

static boolean ConflictClassLoadPackageSrc(String Path , String ClassName) {

Path = Path.replace("\\src\\", "\\bin\\");
String fileName = Path.substring(Path.indexOf("\\bin\\")+5).replace(".java", "").replace("\\", ".");
Path = Path.replace("\\"+ClassName+".java","");

URLClassLoader classLoader = null;
try {
	classLoader = new URLClassLoader(new URL[]{new File(Path).toURI().toURL()});
} catch (MalformedURLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	return false;
} 
try {
	Class<?> loadedClass = classLoader.loadClass(fileName);
	return true;
} catch (ClassNotFoundException e) {
	e.printStackTrace();
	return true;
	
}
}


static boolean IsDoubleWildCardConflictImport(String ImportPackageName1,String ImportPackageName2,ArrayList<ImportStatus> ImportList) {
	for(ImportStatus Import : ImportList) {
		return (ConlictClassLoad(ImportPackageName1, Import.ImportName) && ConlictClassLoad(ImportPackageName2, Import.ImportName));
	}
	return false;
}

static boolean IsClassImportConflict(String ImportPackageName1,String ImportPackageName2 , ArrayList<ImportStatus>ImportList) {
	String ImportClassName1 = FetchImportClassName(ImportPackageName1);
	String ImportClassName2 = FetchImportClassName(ImportPackageName2);
	if(!ImportClassName1.equals("*") && !ImportClassName2.equals("*")&&ImportClassName2.equals(ImportClassName1)) {
		return true;
	}
	else if(ImportClassName1.equals("*") && !ImportClassName2.equals("*")&&ConlictClassLoad(ImportPackageName1, ImportClassName2)) {
		return true;
	}
	else if(!ImportClassName1.equals("*") && ImportClassName2.equals("*")&&ConlictClassLoad(ImportPackageName2, ImportClassName1)) {
		return true;
	}
	else if(ImportClassName1.equals("*") && ImportClassName2.equals("*")) {
		return IsDoubleWildCardConflictImport(ImportPackageName1, ImportPackageName2, ImportList);
	}
	
	return false;
}

static String  FetchImportClassName(String ImportName) {
	return ImportName.substring(ImportName.lastIndexOf(".")+1);
}

public static void UpdateConflictFlag(ArrayList<ImportStatus> ImportList) {
	
	for(int i = 0;i<ImportList.size();i++) {
		String ImportPackageName1 = ImportList.get(i).ImportName;
		
		for(int j = i+1;j<ImportList.size();j++) {
			String ImportPackageName2 = ImportList.get(j).ImportName; 
			 if(IsClassImportConflict(ImportPackageName1, ImportPackageName2,ImportList)) {
				ImportList.get(i).ConflictStatus = 1;
				ImportList.get(j).ConflictStatus = 1;
			
		}
	}
}
}


static void IsAll(ArrayList<String> ListImportFromFile , String line) {
	if(RegularExpression.IsMethodPrototype(line)) {
		
		 RegularExpression.extractClassNamesMethod(line, ListImportFromFile);
		 
	}
	else if(RegularExpression.IsNew(line)) {
		//System.out.println(line);
		RegularExpression.ExtractNewClassNames(line,ListImportFromFile);
	//	System.out.println(ListImportFromFile);
	}
	else if(RegularExpression.IsCatch(line)) {
		//System.out.println(line);
		ListImportFromFile.addAll(RegularExpression.CatchException(line));
		//System.out.println(CatchException(line));
	}
	else if(RegularExpression.IsThrow(line)) {
		ListImportFromFile.addAll(RegularExpression.ThrowException(line));
	}
	else if (RegularExpression.IsVariable(line)) {
		RegularExpression.ExtractVarClassNames(line,ListImportFromFile);
	//	System.out.println(line);
		//System.out.println(ListImportFromFile);
	}
	else if(RegularExpression.IsAnnotation(line)) {
		ListImportFromFile.add(RegularExpression.FetchAnnotation(line));
	}
	else if (RegularExpression.IsStaticCall(line)) {
     RegularExpression.FetchStaticCall(line,ListImportFromFile);
	}
}



//Method To Update Flags Of ImportStatus(used , not used)
public static ArrayList<ImportStatus> update(File file , ArrayList<ImportStatus> ImportList){
	 ArrayList<String> ListImportFromFile = new ArrayList<String>(); 	 
	try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
          String line;
          while ((line = reader.readLine()) != null) {
          line = line.trim();
          line =  Qoute.RemoveQoute(line);
             
              ArrayList<String> ListCode=new ArrayList<String>();
              
              if(!line.isBlank() && !line.isEmpty() && !RegularExpression.IsBracket(line)&& !Comment.IsCommentOnlyCompleted(line) && !RegularExpression.IsImport(line) &&!RegularExpression.IsPackage(line)) {
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
              }
          }
	}catch (IOException e) {
              e.printStackTrace(); // Handle any IO exceptions
          }
    System.out.println(ListImportFromFile);      	
	for(ImportStatus Import : ImportList) {
          		for(String ImportFile :  ListImportFromFile) {
          			if(!Import.ImportName.contains("*")) {
          			if(Import.ImportName.substring(Import.ImportName.lastIndexOf(".")+1).equals(ImportFile)) {
          			
          				Import.ImportStatus = 1;
          			}
          			}
          			else {
        if (ConlictClassLoad(Import.ImportName,ImportFile)) {
        	Import.ImportStatus = 1;
        }
          			}
          		}
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
          	if(!Line.isBlank() && !Line.isEmpty() && !Comment.IsCommentOnlyCompleted(Line) && ! RegularExpression.IsPackage(Line)) {
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
          					if(RegularExpression.IsImport(code)) {
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
          			if(RegularExpression.IsImport(Line)) {
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