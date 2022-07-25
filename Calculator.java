public class Calculator {

    public String[] remove_spaces(String s) {//method to remove spaces from input array and storing integers , parenthesis and operators in separate indices
        String[] s_new = s.split(" ");//array of string consisting of elements without spaces
        int size=0;
        for(int i =0;i<s_new.length;i++){//loop to calculate length of array
            if(s_new[i].equals("")){
                continue;
            }
            size++;
        }
        String[] exp_string = new String[size];
        int index=0;
        for(int i =0;i<s_new.length;i++){//loop for inserting non-null values in return array
            if(!s_new[i].equals("")&&s_new[i]!=null){
                exp_string[index] = s_new[i];
                index++;
            }
        }
        return exp_string;
    }

    /************************************************************************METHOD-1*********************************************************/

    public void check_exp_postfix(String s) throws InvalidPostfixException {//method to check if the input string has some characters apart from those required for postfix expression
        for(int i = 0;i<s.length();i++){
            if(!(s.charAt(i)=='+'||s.charAt(i)=='-'||s.charAt(i)==' '||s.charAt(i)=='*'||Character.isDigit(s.charAt(i)))){
                System.out.println("Invalid Expression!!");
                throw new InvalidPostfixException();
            }
        }
    }
    public int evaluatePostFix( String s) throws InvalidPostfixException{
        check_exp_postfix(s);//checking if expression is valid or not
        String [] s_new = remove_spaces(s);
        int index = 0;
        MyStack stack = new MyStack();
        int pop_elements[] = new int[2];
        while(index<s_new.length){
            if(s_new[index].equals("+")||s_new[index].equals("-")||s_new[index].equals("*")){//if element is an operator
                int pop_index = 1;
                while(pop_index<=2){//loop to pop 2 elements or return error if stack is empty
                    if(!stack.isEmpty()){
                        try {
                            pop_elements[pop_index-1] = (int)stack.pop();
                        } catch (EmptyStackException e) {
                            throw  new InvalidPostfixException();
                        }
                        pop_index++;
                    }else{//if stack is empty
                        throw  new InvalidPostfixException();
                    }
                }
                int ans = 0;
                switch(s_new[index]){//to calculate value of expression having 2 operands and an operator
                    case "+":
                        ans =  pop_elements[0]+pop_elements[1];
                        break;
                    case "-":
                        ans = pop_elements[1]-pop_elements[0];
                        break;
                    case "*":
                        ans = pop_elements[0]*pop_elements[1];
                        break;
                }
                stack.push(ans);//push the result into the stack
            }else{//integers and null strings
                String push_element = s_new[index];
                if(!push_element.equals("")){//getting rid of empty strings
                    int a = Integer.parseInt(push_element);
                    stack.push(a);//push integer into the stack
                }
            }
            index++;//increment the index of array of strings
        }
        int ans_final = 0;
        if(!stack.isEmpty())
        {
            try {
                ans_final = (int) stack.pop();//final element present in stack is our answer
            } catch (EmptyStackException e) {
                System.out.println("Invalid Expression!!");
                throw  new InvalidPostfixException();
            }
        }
        if(!stack.isEmpty()){//if expression is invalid then stack remains filled even after removal of last element
            System.out.println("Invalid Expression!!");
            throw new InvalidPostfixException();//defined in other class
        }else{
            return ans_final;//final answer
        }
    }

    /*************************************************************************************************METHOD-2***************************************************************************************************************/

    public void check_exp_infix(String s) throws InvalidExpException {//method to check if the input string has some characters apart from those required for infix expression
        for(int i = 0;i<s.length();i++){
            if(!(s.charAt(i)=='+'||s.charAt(i)=='-'||s.charAt(i)==' '||s.charAt(i)=='*'||s.charAt(i)=='('||s.charAt(i)==')'||Character.isDigit(s.charAt(i)))){
                System.out.println("Invalid Expression!!");
                throw new InvalidExpException();
            }
        }
    }
    public String create_exp(String s){//method to create equi-spaced expression then using remove_spaces method to separate integers , operators and parenthesis
        String s_exp = "";
        int repeat_digit = 0;
        int repeat_space = 0;
        int index = 0;
        while(index<s.length()){
            if(s.charAt(index)==' '&&repeat_space==0){
                repeat_space = 1;
                repeat_digit = 0;
            }else if(Character.isDigit(s.charAt(index))){
                if(repeat_digit==0){
                    s_exp=s_exp+" "+s.charAt(index);
                }else{
                    s_exp=s_exp+s.charAt(index);
                }
                repeat_digit = 1;
                repeat_space=0;
            } else{
                if(s.charAt(index)!=' '){
                    s_exp=s_exp+" "+s.charAt(index);
                    repeat_space=0;
                    repeat_digit=0;
                }
            }
            index++;
        }
        return s_exp;
    }

    public boolean check_validity(String[] s) throws InvalidExpException {//method to check if the expression is valid or not
        if(s[0].equals("+")||s[0].equals("-")||s[0].equals("*")||s[s.length-1].equals("(")||s[0].equals(")")){
            System.out.println("Invalid Expression!!");
            throw new InvalidExpException();
        }
        for(int i = 1;i<s.length-1;i++){
            if(s[i].equals("+")||s[i].equals("-")||s[i].equals("*")){//checks if the operators lies between correct parenthesis or integers
                if(!((s[i-1].equals(")")&&((s[i+1].equals("("))||Character.isDigit(s[i+1].charAt(0))))||(Character.isDigit(s[i-1].charAt(0))&&(Character.isDigit(s[i+1].charAt(0))||s[i+1].equals("("))))){
                    System.out.println("Invalid Expression!!");
                    throw new InvalidExpException();
                }
            }else if(Character.isDigit(s[i].charAt(0))){//check if the integer is present consecutively with parenthesis ")" or with integers
                if(s[i-1].equals(")")||s[i+1].equals("(")||Character.isDigit(s[i-1].charAt(0))||Character.isDigit(s[i+1].charAt(0))){
                    System.out.println("Invalid Expression!!");
                    throw new InvalidExpException();
                }
            }
        }
        return true;
    }

    public int Prec(String s)//method for setting the precedence of operators
    {
        switch (s)
        {
            case "+":
            case "-":
                return 1;

            case "*":
                return 2;
        }
        return -1;
    }


    public String convertExpression(String exp) throws InvalidExpException {
        check_exp_infix(exp);
        String exp_new = create_exp(exp);
        String[] exps = remove_spaces(exp_new);
       check_validity(exps);
            // initializing empty String for result
            String result = "";
            // initializing empty stack
            MyStack stack = new MyStack();

            for (int i = 0; i < exps.length; ++i) {
                String c = exps[i];

                // If the scanned character is an operand, add it to output.
                if (Character.isDigit(exps[i].charAt(0))) {
                    if(i!=0){
                        result = result + " " + c;
                    }else{
                        result=result+c;
                    }
                }

                // If the scanned character is an "(" push it to the stack.
                else if (c.equals("("))
                    stack.push(c);

                    //  If the scanned character is an ')',
                    // pop and output from the stack until an '(' is encountered.
                else if (c.equals(")")) {
                    try{
                    while (!stack.isEmpty() &&
                            !stack.top().equals("(")) {
                        result = result+" "+stack.pop();
                    }
                    if(stack.top().equals("(")){
                        stack.pop();
                    }else if(stack.isEmpty()){
                        System.out.println("Invalid Expression!!");
                        throw new InvalidExpException();
                    }
                    }catch(EmptyStackException e){
                        System.out.println("Invalid Expression!!");
                        throw new InvalidExpException();
                    }
                } else // an operator is encountered
                {
                    try{
                    while (!stack.isEmpty() && Prec(c)
                            <= Prec((String) stack.top())) {
                        result = result + " " + stack.pop();
                    }
                    }catch (EmptyStackException e){
                        System.out.println("Invalid Expression!!");
                        throw new InvalidExpException();
                    }
                    stack.push(c);
                }

            }

            // pop all the operators from the stack
            while (!stack.isEmpty()) {
            try {
                if (stack.top().equals("("))
                {
                    System.out.println("Invalid Expression!!");
                    throw new InvalidExpException();
                }
                result = result + " " + stack.pop();
            }catch(EmptyStackException e){
                System.out.println("Invalid Expression!!");
                throw new InvalidExpException();
            }
            }
            if(result.charAt(0)==' '){
                result=result.substring(1);
            }
            return result;
        }

    }



class InvalidExpException extends Exception {
    public InvalidExpException(){

    }
}
class InvalidPostfixException extends Exception {
    public InvalidPostfixException(){
    }
}