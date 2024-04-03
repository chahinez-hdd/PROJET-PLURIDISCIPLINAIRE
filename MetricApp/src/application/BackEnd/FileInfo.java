package application.BackEnd;
import java.io.File;
import java.util.ArrayList;

public class FileInfo {
	public File file;
	int FileNumberLine;
	ArrayList<ImportStatus> FileImport = new ArrayList<ImportStatus>();

	FileInfo(File file,int FileNumberLine,ArrayList<ImportStatus> FileImport)
	{	this.file =file;
		this.FileImport=FileImport;
		this.FileNumberLine=FileNumberLine;
	}


}
