public class ExpEval {
	static boolean isOperand(Character c)  //returns true if character is an operand (uses Character.isLetterOrDigit)
	{
		if (Character.isLetterOrDigit(c) || c == '_' || c == '.')
			return true;
		else
			return false;
	}
	static boolean isOperator(Character c)  //return true if character is one of 5 pre-defined operators
	{
		if(c == '+' ||
				c == '-' ||
				c == '*' ||
				c == '/' ||
				c == '^')
				return true;
			else
				return false;
	}
	static int priLvl(Character c)  //returns priority level of operators, higher pri = higher number returned
	{
		int ret ;
		switch(c)
		{
			case '^':
				ret = 4;
				break;
			case '*':
			case '/':
				ret =  3;
				break;
			case '+':
			case '-':
				ret =  2;
				break;
			case '(':
				ret =  1;
				break;
			default:
				ret = -1;
		}
		return ret;
	}
	static int priLvl2(Character c)  //same as other priLvl method but excludes parenthesis
	{
		int ret ;
		switch(c)
		{
			case '^':
				ret = 3;
				break;
			case '*':
			case '/':
				ret =  2;
				break;
			case '+':
			case '-':
				ret =  1;
				break;
			default:
				ret = -1;
		}
		return ret;
	}
	static String infixToPostfix(String exp)  //converts expression from Infix to Postfix notation
	{ 
		String result = new String(""); //string for the resulting postfix expression
		Stack2<Character> stack = new Stack2<>(); //stack for algorithm
		
		for (int i = 0; i < exp.length(); i++) 
		{ 
			char c = exp.charAt(i);  //read from left to right. start @ charAt(i) which is string index 0
			
			if (c == ' ') //skip spaces
				continue;
			
			else if(isOperand(c))  //handles multi-digit integer creation and appends to result string
			{
				int n = 0;
				while(Character.isDigit(c))
				{
					n = n*10 + Character.getNumericValue(c);
					i++;
					if(i == exp.length())  //keeps program from reaching past string
						break;
					else
						c = exp.charAt(i);
				}
				i--;
				result += Integer.toString(n);
				result += ' ';  //adds a space when item is appended to result string
			}
			else if (c == '(')  //if c = '('
			{
				stack.push(c);  //push to stack
			}
			else if (c == ')')  //if c = ')'
			{ 
				while ( !(stack.getSize() == 0) && stack.getTop() != '(')  //pop and output until '(' is reached
				{
					result += stack.pop(); 
					result += ' ';  //adds a space when item is appended to result string
				}
				stack.pop();  //discards remaining parenthesis from stack
			} 
			else //c is an operator
			{ 
				while ( !(stack.getSize() == 0) && priLvl2(c) <= priLvl2(stack.getTop())) //while priLvl(c) <= priLvl(stack.peek)
				{
					result += stack.pop();  //pop stack and append to result string
					result += ' ';  //adds a space when item is appended to result string
				}
				stack.push(c); //if priLvl(c) > priLvl(stack.top), it gets pushed onto stack
			} 
		} 
		while ( !(stack.getSize() == 0)) //string has been fully read, while the stack has stuff in it...
		{
			result += stack.pop();  //pop and append those things to the result string
			result += ' ';  //adds a space when item is appended to result string
		}
		return result;  //returns string: expression in postfix notation (rpn)
	}
	static int evalPostfix(String exp)  //evaluates expression in postfix notation (RPN)
	{
		Stack2 stack = new Stack2();  //create stack for algorithm
		for(int i = 0; i < exp.length(); i++)
		{
			char c = exp.charAt(i);  //read from left to right, start @ charAt(i) which is string index 0
			
			if(c == ' ')	//skip spaces
				continue;
			
			else if(isOperand(c))	//handles multi-digit integer creation and pushes onto stack
			{
				int n = 0;
				while(Character.isDigit(c))
				{
					n = n*10 + Character.getNumericValue(c);
					i++;
					c = exp.charAt(i);
				}
				i--;
				stack.push(n);
			}
			else if(isOperator(c))	//handles arithmetic when operators are encountered
			{
				int tempA = (int) stack.pop();
				int tempB = (int) stack.pop();  //pop top 2 operators, perform operation c
				switch(c)
				{
					case '^':
						stack.push((int) Math.pow(tempB, tempA));
						break;
					case '+': 
						stack.push(tempA + tempB);
						break;
					case '-': 
						stack.push(tempB - tempA);
						break;
					case '*': 
						stack.push(tempA * tempB);
						break;
					case '/': 
						stack.push(tempB / tempA);
						break;
				}
			}
		}
		return (int) stack.pop();  //returns
	}
	public static void main(String args[])  //main/test/driver
	{
		String expression1 = "(5 + 2) * 3";
		System.out.println("expression 1: \n" + expression1);
		System.out.println("Infix to Postfix: \n" + infixToPostfix(expression1));
		System.out.println("Postfix evaluated: \n" + evalPostfix(infixToPostfix(expression1)));
		System.out.println();
		
		String expression2 = "((5 * 2) - 2) ^ 2";
		System.out.println("expression 2: \n" + expression2);
		System.out.println("Infix to Postfix: \n" + infixToPostfix(expression2));
		System.out.println("Postfix evaluated: \n" + evalPostfix(infixToPostfix(expression2)));
		System.out.println();
		
		String expression3 = "(3 * (4 + 2 * (6 - 4) + 1) + 2 ^ 3) / 5";
		System.out.println("expression 3: \n" + expression3);
		System.out.println("Infix to Postfix: \n" + infixToPostfix(expression3));
		System.out.println("Postfix evaluated: \n" + evalPostfix(infixToPostfix(expression3)));
		System.out.println();
		
		String expression4 = "3 * ( 42 + 200)";
		System.out.println("expression 4: \n" + expression4);
		System.out.println("Infix to Postfix: \n" + infixToPostfix(expression4));
		System.out.println("Postfix evaluated: \n" + evalPostfix(infixToPostfix(expression4)));
		System.out.println();
		
		
	}
}