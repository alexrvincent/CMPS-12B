// $Id: linked_stack.java,v 1.1 2013-10-16 12:58:29-07 - - $
//
// NAME
//    class linked_stack - implementation of stack
//
//NAME: ALEX VINCENT, USER: AVINCENT@UCSC.EDU

import java.util.NoSuchElementException;

class linked_stack<item_t> {

   private class node {
      item_t value;
      node link;
   }

   private node top = null;

   public boolean empty() {
      return top == null;
   }
   //pop() - returns a tree from the stack
   public item_t pop() {
      if (empty()) {
         throw new NoSuchElementException ("linked_stack.pop");
      }
      node temp = top; 
      top = top.link; 
      return temp.value; 
   }
  //push - puts a tree onto the stack
   public void push (item_t value) {
     node n = new node(); 
     n.value = value; 
     n.link = top; 
     top = n; 
   }

}
