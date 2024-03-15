import java.util.ArrayList;

public class FileInfo {
	String FileName;
	int FileNumberLine;
	ArrayList<ImportStatus> FileImport = new ArrayList<ImportStatus>();

	FileInfo(String FileName,int FileNumberLine,ArrayList<ImportStatus> FileImport){
		this.FileName =FileName;
		this.FileImport=FileImport;
		this.FileNumberLine=FileNumberLine;
	}


}
