package application.BackEnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Complexity {
public String labelCom;
public Complexity(int complexity) {
    if (complexity <= 10) {
        this.labelCom = "Low";
    } else if (complexity <= 20) {
        this.labelCom = "Moderate";
    } else if (complexity <= 50) {
        this.labelCom ="High";
    } else {
    	this.labelCom ="Very High";
    }
}

public static boolean IsCom(String Line) {
	Line = Line.replace(" ", "");
	return Line.startsWith("for(")||Line.startsWith("While(")||
	Line.startsWith("case(")||Line.startsWith("elseif(")||Line.startsWith("else")||
	Line.startsWith("catch(");
}

public static int FetchComplexity(File file){
	int Cmp = 0;
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
          					if(IsCom(code)) {
          	            		++Cmp;
          	            	}
          					
          				}
          			}
          		}
          		if(ListCode.isEmpty()) {
          			if(IsCom(Line)) {
  	            		++Cmp;
  	            	}
          			
          		}
          	}
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
	
	return Cmp;
}

}
