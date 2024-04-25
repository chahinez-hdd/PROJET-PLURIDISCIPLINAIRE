package application.BackEnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ImportStatus {
public String ImportName;
public int ImportStatus;
ImportStatus(String ImportName,int ImportStatus){
	this.ImportName=ImportName;
	this.ImportStatus=ImportStatus;
}



static void IsAll(ArrayList<String> ListImportFromFile , String line) {
	if(RegularExpression.IsMethodPrototype(line)) {
		System.out.println(line);
		 RegularExpression.extractClassNamesMethod(line, ListImportFromFile);
		  System.out.println(ListImportFromFile);
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
	 	 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
          String line;
          while ((line = reader.readLine()) != null) {
          line = line.trim();
          line =  Qoute.RemoveQoute(line);
              ArrayList<String> ListImportFromFile = new ArrayList<String>();
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
          	for(ImportStatus Import : ImportList) {
          		for(String ImportFile :  ListImportFromFile) {
          			if(!Import.ImportName.contains("*")) {
          			if(Import.ImportName.substring(Import.ImportName.lastIndexOf(".")+1).equals(ImportFile)) {
          			
          				Import.ImportStatus = 1;
          			}
          			}
          			else {
       Java.classExists(Import.ImportName,ImportFile);
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
