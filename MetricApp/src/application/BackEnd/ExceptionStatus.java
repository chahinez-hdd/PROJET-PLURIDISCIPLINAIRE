package application.BackEnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ExceptionStatus {
 String ExceptionName;
 int CheckedStatus;
 int DefaultStatus;
 ExceptionStatus(String ExceptionName,int CheckedStatus,int DefaultStatus){
	 this.ExceptionName = ExceptionName;
	 this.CheckedStatus = CheckedStatus;
	 this.DefaultStatus = DefaultStatus;
 }
 
 static void IsThrowable(ArrayList<ExceptionStatus> List,String line){
	 if(Package.IsThrow(line)) {
		 List.add(new ExceptionStatus(Package.ThrowException(line),0,0));
	 }
	 else if(Package.IsThrows(line)) {
		 List.add( new ExceptionStatus(Package.ThrowsException(line),0,0));
	 }
	 else if(Package.IsCatch(line)) {
		 List.add(new ExceptionStatus(Package.CatchException(line),0,0));
	 }
	 
 }
 
 static ArrayList<ExceptionStatus> UpdateCheckedStatus(ArrayList<ExceptionStatus> List , File file){
	 
	 return List;
 }
 
  static ArrayList<ExceptionStatus> FetchThrowable(File file){
	  ArrayList<ExceptionStatus> ThrowableList = new ArrayList<ExceptionStatus>();
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
