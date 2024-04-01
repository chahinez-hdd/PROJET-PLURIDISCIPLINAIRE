package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Java {
		
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

	
	
	
	//To Know If A Folder Is A Non Empty Java Package
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
	
	
	//To Know If There Are No Java Package
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
	
	//Fetch Java File From A Java Package 
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
	//Fetch Java File In Case Of Default Package
	static void FetchJavaFileNoPackage(File[]SrcFile,ArrayList<Package>ListPackage) {
		ArrayList<FileInfo>ListInfoFile=new ArrayList<FileInfo>();
		for(File file : SrcFile) {
			if(file.getName().endsWith(".java")) {
				ListInfoFile.add(new FileInfo(file.getName(),0,null));
			}
		}
		ListPackage.add(new Package("Default Package",ListInfoFile));
	}
	
	//Fetch Java File From Src Folder
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
  	


}
