package application.BackEnd;

import java.io.File;
import java.util.ArrayList;

public class MainBack {

	public static void main(String[] args) {
	 File file = new File("C://Users//DELL//eclipse-workspace//EXO1.Exception//src//DividedByZeroException.java");
    ArrayList<ExceptionStatus> List=ExceptionStatus.FetchThrowable(file);
    for(ExceptionStatus exc : List) {	
    	System.out.println(exc.ExceptionName);
    }
    
	}

}
