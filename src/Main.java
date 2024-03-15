import java.util.ArrayList;

public class Main {
	public static void main(String args[]) {
		String path = "C:\\Users\\DELL\\eclipse-workspace\\Metrics";
		ArrayList<Package>list = Package.NumberLineJavaProject(path);
		Package.PrintFileNumberLine(list);
	}
}
