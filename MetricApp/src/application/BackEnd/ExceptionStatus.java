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
 String ExceptionName;
 int CheckedStatus;
 int DefaultStatus;
 ExceptionStatus(String ExceptionName,int CheckedStatus,int DefaultStatus){
	 this.ExceptionName = ExceptionName;
	 this.CheckedStatus = CheckedStatus;
	 this.DefaultStatus = DefaultStatus;
 }
 
 static void IsThrowable(ArrayList<String> List,String line){
	 if(Package.IsThrow(line)) {
		 List.add(Package.ThrowException(line));
	 }
	 else if(Package.IsThrows(line)) {
		 List.add(Package.ThrowsException(line));
	 }
	 else if(Package.IsCatch(line)) {
		 List.add(Package.CatchException(line));
	 }
	 
 }
 
 
 static boolean IsDefault(String ExceptionName,String FilePath) {
	 return true;
 }
 

 private static Set<String> listPackages(String ExceptionName) {
     String classpath = System.getProperty("java.class.path");
     //String classpath="C:\\Users\\DELL\\AppData\\Local\\Programs\\Eclipse Adoptium\\jdk-17.0.8.101-hotspot\\lib\\jrt-fs.jar";
     
     return Arrays.stream(classpath.split(File.pathSeparator))
             .filter(entry -> entry.endsWith(".jar") || entry.endsWith(".zip"))
             .flatMap(entry -> {
					try {
						return listJar(new File(entry).toURI().toURL(), ExceptionName).stream();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				})
             .collect(Collectors.toCollection(TreeSet::new));
 }

 private static Set<String> listJar(URL url, String ExceptionName) {
     Set<String> packages = new LinkedHashSet<>();
     try {
         File file = new File(url.toURI());
         if (file.exists()) {
             try (JarInputStream in = new JarInputStream(file.toURI().toURL().openStream())) {
                 for (ZipEntry entry; (entry = in.getNextEntry()) != null; ) {
                     if (entry.isDirectory()) {
                     	String PackageName=entry.getName();
                     	if(entry.getName().contains("/")) {
                     		PackageName=entry.getName().replaceAll("/",".");
                     		//System.out.println(PackageName);
                     		String className = PackageName+ExceptionName;
                     	}
                         packages.add(PackageName);
                     }
                 }
             }
         }
     } catch (Exception e) {
         e.printStackTrace();
     }
     return packages;
 }
 
 static boolean IsSrcFile(String ExceptionName,File file) {
	 //FilePath = FilePath.substring(0,FilePath.indexOf("/src")+4);
	 File[] SrcFile = file.listFiles();
	 for(File Files : SrcFile) {
		 if( Files.isFile() && Files.getName().endsWith(".java")&& Files.getName().replace(".java", "").equals(ExceptionName)) {
			 return true;
		 }
		 else if(Files.isDirectory() && Files.listFiles()!=null) {
			if (IsSrcFile(ExceptionName,Files)){
				return true;
			 }
		 }
	 }
	 return false;
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
 
 
 static ArrayList<ExceptionStatus> UpdateFlagException(ArrayList<String> ListThrowable,String FilePath) {
	 ArrayList<ExceptionStatus> ListException = new ArrayList<ExceptionStatus>();
	 try {
		if(IsClassException(FilePath)) {
			 
		 }
	} catch (ClassNotFoundException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 return ListException;
 }
 
 
 
 static ArrayList<ExceptionStatus> UpdateCheckedStatus(ArrayList<ExceptionStatus> List , File file){
	 
	 return List;
 }
 
  static ArrayList<ExceptionStatus> FetchThrowable(File file){
	  ArrayList<String> ThrowableList = new ArrayList<String>();
		String Line;
		 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            while ((Line = reader.readLine() )!= null) {
	            	Line = Line.trim();
	            	Line = Qoute.RemoveQoute(Line);
	            	ArrayList<String> ListCode=new ArrayList<String>();
	            	if(!Line.isBlank() && !Line.isEmpty() && !Comment.IsCommentOnlyCompleted(Line) && !Package.IsPackage(Line) && !Package.IsImport(Line)) {
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
		
	return ThrowableList;
	  
  }
}
