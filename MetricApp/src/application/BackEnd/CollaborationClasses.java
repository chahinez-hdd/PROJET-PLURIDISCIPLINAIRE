package application.BackEnd;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

    public class CollaborationClasses  {
    
   public  String FileName;
     public int NbInstanciation;
    	
     CollaborationClasses(String FileName,int NbInstanciation){
    	 this.FileName= FileName;
    	 this.NbInstanciation=NbInstanciation;
     }
    	 
     
     
     public static void NbCollaborationClass(String ParentPkgName,ArrayList<Package>ListPackage , ArrayList<String>ClassName,ArrayList<CollaborationClasses>NbInstanciationList) {
    	 for (Package pack : ListPackage) {
    	        for (String fileName : pack.FileNameList) {
    	            int count = 0;
    	            for (String className : ClassName) {
    	                if (fileName.replace(".java", "").equals(className)) {
    	                    count++;
    	                }
    	            }
    	            String FileName="";
    	            if(ParentPkgName.equals("")) {
    	             FileName=pack.PackageName+"."+fileName;
    	            }
    	            else {
    	            	 FileName=ParentPkgName+fileName;	
    	            }
    	            CollaborationClasses collaboration = new CollaborationClasses(FileName, count);
    	            NbInstanciationList.add(collaboration);
    	        }
    	        
    	       if(pack.SubPackges.size()!=0) {
    	    	   ParentPkgName+=pack.PackageName+".";
    	    	   NbCollaborationClass(ParentPkgName, pack.SubPackges, ClassName, NbInstanciationList);
    	       }
    	    }
    	 
     }
     
     public static void IsInstanciation(ArrayList<String>ClassName,String line) {
    	 if(RegularExpression.IsMethodPrototype(line)) {
    			 RegularExpression.extractClassNamesMethod(line, ClassName);
    		}
    		else if(RegularExpression.IsNew(line)) {
    			RegularExpression.ExtractNewClassNames(line,ClassName);
    		}
    		
    		else if (RegularExpression.IsVariable(line)) {
    			RegularExpression.ExtractVarClassNames(line,ClassName);
    		}
    		
    		else if (RegularExpression.IsStaticCall(line)) {
    	     RegularExpression.FetchStaticCall(line,ClassName);
    		}
     }
     
     
    public static void FetchClassInstaciationName(File file,ArrayList<String>ClassName) {
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
             				IsInstanciation(ClassName, code);
             			}
             		}
             	}
             	
             	if(ListCode.size()==0) {
             		IsInstanciation(ClassName, line);
             	}
             	
                }	               
                 
             }
         } catch (IOException e) {
             e.printStackTrace(); // Handle any IO exceptions
         }
    }
 
}
