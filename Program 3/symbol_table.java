// $Id: symbol_table.java,v 1.3 2013-10-17 18:33:53-07 - - $
//
// NAME
//    class symbol_table
//       Symbol table with letter indices and double and tree values.
//
//NAME: ALEX VINCENT, USER: AVINCENT@UCSC.EDU

import static java.lang.System.*;
import static java.lang.String.*;

class symbol_table {

   //
   // Constants for use within this class.
   //
   private static final char LO_LETTER = 'a';
   private static final char HI_LETTER = 'z';
   private static final int ARRAYLEN = HI_LETTER - LO_LETTER + 1;
   private static double[] values = new double[ARRAYLEN];
   private static bitree[] trees = new bitree[ARRAYLEN]; 

   //
   // Convert letter into array index.
   //
   private int aindex (char varname) {
      int index = Character.toLowerCase (varname) - LO_LETTER;
      if (index < 0 || index >= ARRAYLEN) {
         throw new IndexOutOfBoundsException (
                   format ("'%c' is out of bounds: '%c'..'%c'",
                           varname, LO_LETTER, HI_LETTER));
      }
      return index;
   }

   //
   // Constructor.  Defaults all values to NaN.
   //
   public symbol_table() {
      for (int index = 0; index < values.length; ++index) {
         values[index] = Double.NaN;
      }
   }

 
   //
   // Accessors.  Get the value or the tree from the symbol table.
   //
   public double get_value (char varname) {
      return values[aindex (varname)];
   }

   public bitree get_tree (char varname) {
      return trees[aindex (varname)];
   }
   //
   // print_table() - prints each value for non-NaN variables.
   //
   public void print_table(){
     char var = 'a';
     System.out.println("**** FINAL SYMBOL TABLE ****");
     for(int i = 0; i < 26 ; i++){
       if((Double.isNaN(get_value(var)))){
         var++;
         continue;
       }
       System.out.printf("%c: %g%n",var, get_value(var));
       if(get_tree(var).left ==null && get_tree(var).right== null){
         var++;
         System.out.printf("   null%n");
       }else{    
       System.out.printf("   %s%n",get_tree(var));
       var++;
       }
     }
   
   }

   //
   // Mutators.  Change the value and the tree in the table.
   //
   public void put (char varname, double value, bitree tree) {
      int index = aindex (varname);
      values[index] = value;
      trees[index] = tree;
   }

}