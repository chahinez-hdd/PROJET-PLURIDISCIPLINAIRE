package application.BackEnd;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.JarInputStream;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

//Fix IsMethode and its fetch for Multiple throws
//Fix fetchCatch for multiple exception
//remove isthrows and its fetch methode
//how to get classpath of javaproject



public class ExceptionStatus {
 public String ExceptionName;
 
public int CheckedStatus;
 public int DefaultStatus;
 ExceptionStatus(String ExceptionName,int CheckedStatus,int DefaultStatus){
	 this.ExceptionName = ExceptionName;
	 this.CheckedStatus = CheckedStatus;
	 this.DefaultStatus = DefaultStatus;
	
 }
 
 static void IsThrowable(ArrayList<String> List,String line){
	 if(RegularExpression.IsThrow(line)) {
		 List.addAll(RegularExpression.ThrowException(line));
	 }
	 else if(RegularExpression.IsMethod(line)) {
		 List.addAll(RegularExpression.FetchMethodThrowable(line));
	 }
	 else if(RegularExpression.IsCatch(line)) {
		 List.addAll(RegularExpression.CatchException(line));
	 }
	 
 }
 
 public static boolean isClassFromJRE(String className) {
	    try {
	        Class<?> clazz = Class.forName(className);
	        ClassLoader classLoader = clazz.getClassLoader();
	        
	        // Check if the class loader is null or if it's from the JRE
	        return classLoader == null || classLoader.getName().contains("java.base")
	        ||classLoader.getName().contains("java.desktop") 
	        ||classLoader.getName().contains("java.net")
	        ||classLoader.getName().contains("java.rmi")
	        ||classLoader.getName().contains("java.sql");
	    } catch (ClassNotFoundException e) {
	        // Handle ClassNotFoundException if the class is not found
	        return false;
	    }
 }
 
 
 static String ExceptionImport(String ExceptionName,File file) {
	 for(ImportStatus Import : ImportStatus.ImportFetch(file)) {
		 if(Import.ImportName.endsWith("."+ExceptionName)) {
			 return Import.ImportName;
		 }
	 }
	 return null;
 }
 
 
 static String IsSrcFile(String ExceptionName,File file) {
	// File file = new File(ProjectPath);
	 //FilePath = FilePath.substring(0,FilePath.indexOf("/src")+4);
	 File[] SrcFile = file.listFiles();
	 for(File Files : SrcFile) {
		 if( Files.isFile() && Files.getName().endsWith(".java")&& Files.getName().replace(".java", "").equals(ExceptionName)) {
		
			 return Files.getAbsolutePath();
		 }
		 else if(Files.isDirectory() && Files.listFiles()!=null) {
			 return IsSrcFile(ExceptionName,Files);
			 
		 }
	 }
	 return null;
 }
 
 static boolean IsClassException(String PathSrcFile) throws IOException, ClassNotFoundException {
	 String PathBinFile = PathSrcFile.replace(".java", ".class");
	 PathBinFile = PathBinFile.replace("/src/","/bin/");
     // Create a custom class loader
     URLClassLoader classLoader;
	try {
		classLoader = new URLClassLoader(new URL[]{new File(PathBinFile.substring(0,PathBinFile.indexOf("/bin")+4)).toURI().toURL()});
	     Class<?> loadedClass = classLoader.loadClass(PathBinFile);

	     // Now you have the loaded class and can work with it
	     System.out.println("Loaded class: " + loadedClass.getName());
	     Class<?> superClass = loadedClass;
	     // You can also get the superclass
	     while (superClass != null) {
             superClass = superClass.getSuperclass();
                  if (superClass != null)
                	  if(superClass==Exception.class) {
                		  return true;
                	  }
                	  else if(superClass==Error.class) {
                		  return false;
                	  }
              System.out.println("Superclass of " + loadedClass.getName() + ": " + superClass.getName());
         }
	     
	     // Close the class loader when done
	     classLoader.close();
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

     

	 return false;
 }
 
 
 
 
 
 static int UpdateCheckedStatus(String ExceptionName, String ExceptionPath, int flag) throws IOException {
	   
	 try {
	        if (flag == 0) { // Load using system class loader for JRE classes
	            try {
	                Class<?> loadedClass = Class.forName(ExceptionPath);
	             System.out.println(loadedClass.getName());
	                if (loadedClass != null) {
	                    // Check the superclass of the loaded class
	                    Class<?> superClass = loadedClass;
	                    while (superClass != null) {
	                    	
	                        superClass = superClass.getSuperclass();
	                        System.out.println(superClass.getName());
	                        if (superClass != null) {
	                            if (superClass == RuntimeException.class) {
	                                return 1;
	                            } else if (superClass == Error.class) {
	                                return -1;
	                            }
	                            else if(superClass == Exception.class) {
	                            	break;
	                            }
	                        }
	                    }
	                }
	            } catch (ClassNotFoundException e) {
	                // Class not found by the system class loader
	            }
	        } else {
	        	// Load using custom class loader for project classes
	        	System.out.println(ExceptionPath);
	        	ExceptionPath = ExceptionPath.replace("\\src\\", "\\bin\\");
	        	System.out.println(ExceptionPath);
	        	ExceptionPath = ExceptionPath.substring(0,ExceptionPath.indexOf("\\bin\\")+4);
	            System.out.println(ExceptionPath);
	            URLClassLoader classLoader = new URLClassLoader(new URL[]{new File(ExceptionPath).toURI().toURL()});
	            Class<?> loadedClass = classLoader.loadClass(ExceptionName);
	            System.out.println(ExceptionPath);
	           // System.out.println(ExceptionPath);
	            System.out.println(loadedClass.getName());
	            classLoader.close();

	            if (loadedClass != null) {
	                // Check the superclass of the loaded class
	                Class<?> superClass = loadedClass;
	                while (superClass != null) {
	                    superClass = superClass.getSuperclass();
	                    System.out.println(superClass.getName());
	                    if (superClass != null) {
	                        if (superClass == RuntimeException.class) {
	                            return 1; // UnChecked exception
	                        } else if (superClass == Error.class) {
	                            return -1; // Error
	                        }
	                        else if(superClass == Exception.class) {
	                        	break;
	                        }
	                    }
	                }
	            }
	        }
	    } catch (ClassNotFoundException | MalformedURLException e) {
	        // Handle exceptions
	        e.printStackTrace();
	    }

	    return 0; // Default
	}

  public static ArrayList<ExceptionStatus> FetchThrowable(File file,String ProjectPath) throws ClassNotFoundException, IOException{
	  ArrayList<String> ThrowableList = new ArrayList<String>();
		String Line;
		 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            while ((Line = reader.readLine() )!= null) {
	            	Line = Line.trim();
	            	Line = Qoute.RemoveQoute(Line);
	            	ArrayList<String> ListCode=new ArrayList<String>();
	            	if(!Line.isBlank() && !Line.isEmpty() && !Comment.IsCommentOnlyCompleted(Line) && !RegularExpression.IsPackage(Line) && !RegularExpression.IsImport(Line)) {
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
	            					IsThrowable(ThrowableList,code);
	            				}
	            			}
	            		}
	            		if(ListCode.isEmpty()) {
	            			IsThrowable(ThrowableList,Line);
      	            	}
	            			
	            		}
	            	}
	            }
	         catch (IOException e) {
	            e.printStackTrace();
	        }
		System.out.println(ThrowableList);
	ArrayList<ExceptionStatus> ListException = new ArrayList<>();
	File FileSrc = new File(ProjectPath);
	for(String Exc : ThrowableList ) {
		String ExceptionPath="";
		int Flag = 0;
		if(IsSrcFile(Exc,FileSrc )!=null) {
     	 ExceptionPath = IsSrcFile(Exc, FileSrc); 
     	 Flag = 1;
		}
		else {
			 ExceptionPath = ExceptionImport(Exc,file);
			// DefaultFlag = 0;

		}
     	int CheckedFlag = UpdateCheckedStatus(Exc, ExceptionPath,Flag);
     	if(CheckedFlag!=-1) {
     		int Default = 0;
     
     		if(!isClassFromJRE(ExceptionPath)) {
     			Default = 1;
     		}
     		ListException.add(new ExceptionStatus(Exc, CheckedFlag, Default));
     	}
	}
	return ListException;
	  
  }
}
