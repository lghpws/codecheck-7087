import java.io.*;
package codecheck;

public class App {
	public static void main(String[] args) {
		System.out.println("tttt");
		String path = new File(".").getAbsoluteFile().getParent();
		for (int i = 0, l = args.length; i < l; i++) {
			System.out.println(args[i]);
		}
	}
}
