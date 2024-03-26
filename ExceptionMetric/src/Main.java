
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = "int a=0; /* adkwldsad"
				+ "wakdaskda wasad*/ "
				+ "a=10;";
		boolean b= false;
		System.out.println(Methods.isComment(path, b));
		while(Methods.isComment(path,b)) {
			System.out.println(Methods.isComment(path,b));
		}

	}

}
