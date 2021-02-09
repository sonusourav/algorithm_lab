package assignment_5;

public class Stack{

    int top;
    int stack[];
    int maxStackSize = 0;
    char name;

    //Constructor with one parameter i.e. size
    Stack(int size, char name){
        this.stack = new int[size];
        this.top = -1;
        this.maxStackSize = size;
        this.name = name;
    }

    //push function
    //add a new value to the top of the stack
    boolean push(int val){
        if(top >= maxStackSize){
            return false;
        }else{
            stack[++top] = val;
            return true;
        }
    }

    //pop function 
    //return the value at the top and remove it from the stack
    int pop(){
        if(top < 0){
            return -100;
        }else{
            return stack[top--];
        }
    }

    //function to check if the stack is empty
    boolean isEmpty(){
        return (top<0);
    }

    //function to get the value at the top
    int peek(){
        if(this.top < 0){
            return -100;
        }else{
            return stack[top];
        }
    }
}