import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

public class Converter{

	private FileWriter writer;
	private String name;

	public Converter(FileWriter write){
		
		this.writer = write;
		
	}

	

	// method to ensure acceptable operands are being used
	static boolean opcheck (char ch){

		if(ch == '+' || ch == '-' || ch == '/' || ch == '*' || ch == '^'){

			return true;
		}

		System.out.println("Invalid operand!");
		return false;
	}

	// handles PEMDAS component of operation
	static int opsorter(char ch){

		switch(ch){

			// case based priority set
			case '^':
				return 3;
			
			case '*':
			case'/':
				return 2;

			case '-':
			case'+':
				return 1; 
		}
		return -1;
	}

	// converts infix expression to postfix
	public static String conversion(String expr) {
		// uses stack and goes through to sort operands as well as convert instructions, leaving postfix op on top of stack
		LinkedListStack<String> output = new LinkedListStack<String>();
		String [] tokens = expr.split(" ");
		
		for (int i=0; i < tokens.length-1; i++) {	
			if (tokens[i].equals(")")) {
				String right = output.pop();
				String oper = output.pop();
				String left = output.pop();
				output.push( left + " " + right + " " + oper);
			}
			else {
				if (!(tokens[i].equals("("))) {
					output.push(tokens[i]);
				}
			}
		}
		String xs = output.peek();
		System.out.println("list:" + xs);
		return output.peek();
	}

	// makes file from converter
	public static void writeToFile(String filename, String newfile) {
		try {
			// read file
			FileReader read = new FileReader(filename);
			BufferedReader buff = new BufferedReader(read);

			String expr = buff.readLine();

			// file writing 
			FileWriter writer = new FileWriter(newfile);            

			while(expr!=null) {
				String infixToWrite = "Infix Expression : " + expr + "\n";
				String postToWrite = "Postfix Expression : " + conversion(expr) + " ;" + "\n";
				writer.write(infixToWrite + postToWrite);
				expr = buff.readLine();
			}
			buff.close();
			writer.close();
		}
			
		catch(FileNotFoundException e) {
			System.out.println("File not found :( ");
		}

		catch(IOException ex) {
			System.out.println("Can't open " + filename);
		}
	}

	
	public static void main(String[] args){
		String input = "( AX + ( B * C ) ) ;";
		String ex = "( ( A + ( B * C ) ) / ( D - E ) ) ;";
		String infix = "( ( H * ( ( ( ( A + ( ( B + C ) * D ) ) * F ) * G ) * E ) ) + J ) ;";
		String ex1 = "( ( AX + ( BY * C ) ) / ( D4 - E ) ) ;";
		
		System.out.println(conversion(input));
		System.out.println(conversion(infix));
		System.out.println(conversion(ex));
		System.out.println(conversion(ex1));

 
		writeToFile("test1.txt", "testres1.txt");
		writeToFile("test2.txt", "testres2.txt");
		
	}
}


