package application.BackEnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
        
public class Encapsulation {
	int Total;
	int CompteurNone;
	int CompteurPublic;
	int CompteurPrivate;
	int CompteurProtected;
	double TauxEncapsulation;
	
	Encapsulation(int Total  ,int CompteurNone,int CompteurPublic , int CompteurPrivate,int CompteurProtected){
		this.Total = Total;
		this.CompteurNone =CompteurNone;
		this.CompteurPublic = CompteurPublic;
		this.CompteurPrivate = CompteurPrivate;
		this.CompteurProtected = CompteurProtected;
		TauxEncapsulation = 0;
	}
	
	
	public static void CalculTauxEncapsulation(Encapsulation encapsulation) {
	 encapsulation.TauxEncapsulation  = (double) (encapsulation.CompteurPrivate  + encapsulation.CompteurProtected )*100 / encapsulation.Total;
	}
	
	public static void UpdateEncapsulationFlag(Encapsulation encapsulation , String Line) {
		if(Line.startsWith("public")) {
			encapsulation.CompteurPublic++;
		}
		else if(Line.startsWith("private")) {
			encapsulation.CompteurPrivate++;
		}
		else if(Line.startsWith("protected")) {
			encapsulation.CompteurProtected++;
		}
		else {
			encapsulation.CompteurNone++;
		}
		
		encapsulation.Total++;
	}
	
	public static Encapsulation EncapsulationFetch(File file){
		Encapsulation encapsulation = new Encapsulation(0,0,0,0,0);
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
	          					if(RegularExpression.IsVariable(code) || RegularExpression.IsMethodPrototype(code)) {
	          	            		UpdateEncapsulationFlag(encapsulation, code);
	          	            	}
	          					
	          				}
	          			}
	          		}
	          		if(ListCode.isEmpty()) {
	          			if(RegularExpression.IsVariable(Line) || RegularExpression.IsMethodPrototype(Line)) {
	  	            		UpdateEncapsulationFlag(encapsulation, Line);
	  	            	}
	          			
	          		}
	          	}
	          }
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
		CalculTauxEncapsulation(encapsulation);
		return encapsulation;
	}

	public static void main(String[] args) {
		String Path="C:\\Users\\DELL\\eclipse-workspace\\MetricApp\\src\\application\\BackEnd\\Test.java";
		
		File file = new File(Path);
		Encapsulation encapsulation = EncapsulationFetch(file);
       System.out.println(encapsulation.Total);
       System.out.println(encapsulation.CompteurPublic);
       System.out.println(encapsulation.CompteurNone);
       System.out.println(encapsulation.CompteurPrivate);
       System.out.println(encapsulation.CompteurProtected);
       System.out.println(encapsulation.TauxEncapsulation);
	}

    
}
        