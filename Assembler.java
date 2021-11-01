import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

// converts Converter expression into assembly.
public class Assembler {
	// memory tracker
	static int temp_memory_num = 1;
	
	// infix -> conversion -> assembly ^_^
	public static String assemble(String expr) {
		String operands = "+-*/";
		LinkedListStack<String> stk = new LinkedListStack<String>();
		String post_expr = Converter.conversion(expr);
		// split by white spaces
		String [] tokens = post_expr.split(" ");
		for (int i=0; i < tokens.length; i++) {
			String t = tokens[i];

			if (!(operands.contains(t))) {
				stk.push(t);
			}
			else {
				String right = stk.pop();
				String left = stk.pop();
				stk.push(evaluate(left, t, right));
			}
		}
		// Top of stack has value
		return stk.peek();
	}


	// assigns memory space then assembles instructions
	public static String evaluate(String left, String operator, String right) {
		String temp_memory = "TMP" + temp_memory_num ;
		String solution = "";
		if (operator.equals("+")){
			try{    
				solution += "LD " + left + "\n";
				solution += "AD " + right + "\n";
				solution += "ST " + temp_memory;
				System.out.println(solution);
			}
			catch(Exception e){System.out.println(e);}    
		}
		else if (operator.equals("-")){
			try{    
				solution += "LD " + left + "\n";
				solution += "SB " + right + "\n";
				solution += "ST " + temp_memory;
				System.out.println(solution);   
			}
			catch(Exception e){System.out.println(e);}    
		}
		else if (operator.equals("*")){
			try{    
				solution += "LD " + left + "\n";
				solution += "ML " + right + "\n";
				solution += "ST " + temp_memory;
				System.out.println(solution); 
			}
			catch(Exception e){System.out.println(e);}    
		}
		else if (operator.equals("/")){
			try{    
				solution += "LD " + left + "\n";
				solution += "DV " + right + "\n";
				solution += "ST " + temp_memory;
				System.out.println(solution);
			}
			catch(Exception e){System.out.println(e);}    
		}
		temp_memory_num++;
		return temp_memory;
	}

	// file writer
	public static void writeToFile(String filename, String newfile) {
		try {
			FileReader reader = new FileReader(filename);
			BufferedReader buffered = new BufferedReader(reader);

			String expr = buffered.readLine();

			FileWriter writer = new FileWriter(newfile);            

			while(expr!=null) {
				String infixToWrite = "Infix Expression : " + expr + "\n";
				String postToWrite = "Converter Expression : " + Converter.conversion(expr) + " ;" + "\n";
				String assemblyToWrite = "Assembly Expression : " + assemble(postToWrite);
				writer.write(infixToWrite + postToWrite + assemblyToWrite);
				expr = buffered.readLine();
			}
			buffered.close();
			writer.close();
		}
			
		// error catching
		catch(FileNotFoundException e) {
			System.out.println("Unable to open file " + filename);
		}

		catch(IOException ex) {
			System.out.println("Error reading file " + filename);
		}
	}
	public static void main(String args[])throws IOException{
		String exp1 = "( ( AX + ( BY * C ) ) / ( D4 - E ) ) ;";
		String exp2 = "( ( A + ( B * C ) ) / ( D - E ) ) ;";
		System.out.println("infix: " + exp1);
		assemble(exp2);
		System.out.println("infix: " + exp2);
		assemble("Assembly : "  + "/n" + exp2);
	}
}