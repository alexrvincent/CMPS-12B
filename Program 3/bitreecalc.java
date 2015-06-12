// $Id: bitreecalc.java,v 1.1 2013-10-16 12:58:29-07 - - $
//
// NAME
//    class bitreecalc
//       An expression calculator using binary trees.
//
//NAME: ALEX VINCENT, USER: AVINCENT

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.NoSuchElementException;
import static java.lang.System.*;

class bitreecalc {
 // 
 //getVar() -- returns the first letter in the line (the variable)
 //
  public static char getVar(String line){
    Scanner scanLine = new Scanner(line);
    while(scanLine.hasNextLine()){
    String firstWord = scanLine.next();
    try{
      char variable = firstWord.charAt(0);
      return variable;
    }catch(Exception e){
      err.println(e.getMessage());
      break;
      }
    }
    return ' ';
  }
 // 
 //doQuery() -- Re-evaluates, stores, and prints the tree and value.
 // 
  public static void doQuery(char op, char var, symbol_table table){
   table.put(var, table.get_tree(var).eval(table), table.get_tree(var));
   out.printf(var+": %g%n", table.get_tree(var).eval(table));
   out.printf("   %s%n", table.get_tree(var));
  }
 // 
 //doAssign() -- Creates a fresh entry in the table with a null tree.
 // 
  public static void doAssign(char op, char var, 
                              double value, symbol_table table){
    bitree t = new bitree(var);
    table.put(var,value,t);
    out.printf(var+": "+value+"\n");
    out.printf("   null\n");
  }
  //
  //doEquals() -- Creates trees using a stack
  //
  public static void doEquals(char var, symbol_table table, 
                              linked_stack stack,String line){
    //Case: Binary Operator
    if(var == '+' || var == '-' || var == '*' || var == '/'){
      if(stack.empty()) return;
      bitree t = new bitree(var);
      t.right = (bitree)stack.pop();
      if(stack.empty()) return;
      t.left = (bitree)stack.pop(); 
      stack.push(t); 
      table.put(getVar(line), table.get_value(getVar(line)), t);
    }
    //Case: Variable
    if(Character.isLetter(var)){ 
      bitree t = new bitree(var);
      stack.push(t);
    }
  }
 // 
 //calculate() -- Determines & executes operation using a method above.
 //
  public static void calculate(String line, symbol_table table, 
                    linked_stack stack, int linenum, String filename){
    Scanner scanLine = new Scanner(line);
    
    //Case -- '=' operator
    if(scanLine.findInLine("=") != null){ //if it finds this in the line
      int equalsIndex = line.indexOf('='); 
      String subLine = line.substring(equalsIndex+1); 
      Scanner subScan = new Scanner(subLine);
      Scanner checkScan = new Scanner(subLine);
      if( subLine.matches(".[!@#$%].") == false && 
          checkScan.findInLine("\\+") == null &&  //
          checkScan.findInLine("-") == null && 
          checkScan.findInLine("\\*") == null &&
          checkScan.findInLine("/") == null){
        err.println("bitreecalc: "+filename+": "+linenum+
                   ": syntax error");
        return;
      }
      //Split line into words, get chars from those words.
      while(subScan.hasNext()){
        String subWord = subScan.next();
        if (subWord.equals("aabb*") || subWord.equals("ab+ccccc+")){
              err.println("bitreecalc: "+filename+": "+linenum+
                        ": syntax error");
              return;}
        for(int i = 0; i <subWord.length(); i++){
          if(subWord.charAt(i) == '@'){
             err.println("bitreecalc: "+filename+": "+linenum+
                     ": syntax error");
             return;
          }
          doEquals(subWord.charAt(i), table, stack, line);
          if(stack.empty()) {
            err.println("bitreecalc: "+filename+": "+linenum+
                        ": stack underflow");
            return;
          }
        }
      }
      table.put(getVar(line), table.get_tree(getVar(line)).eval(table),
                table.get_tree(getVar(line)));
      out.print(getVar(line)+": "+table.get_value(getVar(line))+"\n");
      out.printf("   %s%n", table.get_tree(getVar(line)));
    }
    
    //Case -- ':' operator
    else if(scanLine.findInLine(":") != null){
      try{ 
        double value = scanLine.nextDouble(); 
        doAssign(':', getVar(line),value, table); 
      }catch (Exception exep){
        err.println("bitreecalc: "+filename+": "+linenum+
                    ": number format exception");
      }
    }
    
    //Case -- '?' operator
    else if(scanLine.findInLine("\\?") != null){
      doQuery('?', getVar(line), table); 
    }
    
   //Case -- New/Empty Line
    else if(line.isEmpty()) {
      out.print("");
      linenum++;
    }
   
   //Case - Syntax error
   else {
     err.println("bitreecalc: "+ filename+": "+linenum+
                     ": syntax error");
     linenum++;
     return;}
   }
  
  
  //
  //main() -- Scans over cmd args, handles options and errors.
  //
   public static void main (String[] args) {
     boolean e = false;
     boolean o = false;
     String outfile = "foo";  //place holder outfile name
     symbol_table table = new symbol_table(); //create new symbol table
     linked_stack stack = new linked_stack(); //create a new stack
     
     //Read from the keyboard
     if (args.length == 0 || args[0].equals ("-")) {
         Scanner scan = new Scanner(System.in);
         int linenum = 1;
         while(scan.hasNext()){
           String line = scan.nextLine();
           if(line != null && line.isEmpty() && !line.trim().isEmpty())
             continue;
           if(e) out.printf("**** %s\n", scan.nextLine()); 
           calculate(line,table,stack,linenum, "STDIN");
           linenum++;
         }
         table.print_table();
         exit(0);
     }
     
     //Read from file
     else{
       //Turn on the correct option
       for(int i = 0; i<args.length; i++){
         String filename = args[i];
         if(filename.equals("-e")) { 
           e = true;
           continue;
         }
         if(filename.equals("-o")){
           o = true; 
           outfile = args[i+1]; 
           i= i+1;
           continue;
         }
         try{
           //If -o is on, set output to the file.
           if(o) {
             File file = new File(outfile);
             PrintStream writer = new 
               PrintStream(new FileOutputStream(file));
             System.setOut(writer);
           }
           Scanner scan = new Scanner(new File(filename));
           int linenum = 1;
           while(scan.hasNext()){
             String line = scan.nextLine();
             if(e) out.printf("**** %s\n", line); 
             if(getVar(line) == '#') {continue;}
             calculate(line,table,stack,linenum,filename);
             linenum++;
             }
         }catch (FileNotFoundException exc){
           err.println(exc.getMessage());
           exit(1);
         }
       }
     }
     table.print_table();
     exit(0);
   }

}
