

public class MyStack implements StackInterface {

    private Object []array = new Object[1];//initial array with length equal to one
    private int top = -1;//index of top element of stack

/**************************************************************************************METHOD-1*************************************************************************************************************** */
    public void push(Object o){
        if(o == null){
            return;
        }else{
        if(top==array.length-1)//to check if our array is fully filled
        {
            Object [] array_new = new Object[array.length*2];
            for(int i = 0;i<array.length;i++){
                array_new[i] = new Object();
                array_new[i]=array[i];//copying elements of smaller array to bigger one
            }
            array = array_new;//passing reference of new array to old one
        }
        ++top;//incrementing the index of top
        array[top] = new Object();
        array[top] = o;
        }
    }
/****************************************************************************************METHOD-2************************************************************************************************************* */

    public Object pop() throws EmptyStackException{
        if(isEmpty()){
            System.out.println("Stack is Empty!!!");
            throw new EmptyStackException();//exception defined in other class file
        }else{
            Object obj = array[top];
            array[top] = null;//to reduce wastage of memory reclaimed by garbage collector
            top--;
            return obj;//popped element
        }
    }
/******************************************************************************************METHOD-3*********************************************************************************************************** */

    public Object top() throws EmptyStackException{
        if(isEmpty()){
                System.out.println("Stack is Empty!!!");
                throw new EmptyStackException();//exception defined in other class file
        }else{
            return array[top];
        }
    }
/******************************************************************************************METHOD-4*********************************************************************************************************** */

    public boolean isEmpty(){
        if(top==-1){//initialised top with negative index
            return true;
        }
        return false;
    }
/******************************************************************************************METHOD-5*********************************************************************************************************** */

    public String toString(){
        String ans = "[";
        for(int i = top;i>=0;i--){
            ans+=array[i].toString();//converting object to string
            if(i!=0){
                ans+=", ";//to ensure proper space in output string
            }
        }
        ans+="]";
        return ans;//final string
    }
}
class EmptyStackException extends Exception {
    public EmptyStackException(){
    }
}