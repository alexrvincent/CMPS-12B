// $Id: bitree.java,v 1.3 2013-10-17 18:34:13-07 - - $
//
// NAME
//    class bitree - starter class for bitree implementation.
//
//NAME: ALEX VINCENT, USER: AVINCENT@UCSC.EDU

class bitree {
   char symbol;
   bitree left; 
   bitree right; 
   double total = 0;

   //Constructor -- Used when creating operators
   bitree (char symbol_, bitree left_, bitree right_) { 
      symbol = symbol_;
      left = left_;
      right = right_;
   }
  //Constructor -- Used when creating varibles
   bitree (char symbol_) {
      this (symbol_, null, null);
   }
   //
   //toString() -- Uses recursive In-Order Traversal to print tree. 
   //
   public String toString () {
     if(this.left == null && this.right == null){
       System.out.print(symbol);
       return null;//Leaf node
     }
      System.out.print("(");
      this.left.toString();
      System.out.print(symbol);
      this.right.toString();
      System.out.print(")");
      return "";
   }
   //
   //eval() -- Uses recursive Post-Order Traversal to evaluate tree.
   //
     public double eval(symbol_table st){
       if(this == null) return 0;
       if(this.left == null && this.right == null){
         this.total = st.get_value(this.symbol);
         return 0;
       }
       this.left.eval(st);
       this.right.eval(st);
       if(Character.isLetter(this.symbol))return 0;
       else{
       switch(this.symbol){
        case '+': total = (this.left.total + this.right.total); break;
        case '-': total = (this.left.total - this.right.total); break;
        case '*': total = (this.left.total * this.right.total); break;
        case '/': if (this.right.total == 0) return Double.POSITIVE_INFINITY;
                  else { total = (this.left.total / this.right.total);
                    break;
                  }
             }
       }
       return this.total;
     }
}
   
