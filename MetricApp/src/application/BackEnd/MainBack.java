package application.BackEnd;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainBack {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
	 File file = new File("C://Users//DELL//eclipse-workspace//EXO1.Exception//src//Main.java");
    ArrayList<ExceptionStatus> List=ExceptionStatus.FetchThrowable(file,"C://Users//DELL//eclipse-workspace//EXO1.Exception//src//");
    for(ExceptionStatus exc : List) {	
    	System.out.println(exc.ExceptionName);
    	System.out.println(exc.DefaultStatus);
    	System.out.println(exc.CheckedStatus);
    }
    
	}

}
