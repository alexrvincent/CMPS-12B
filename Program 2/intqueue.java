// $Id: intqueue.java,v 1.4 2013-10-16 17:10:32-07 - - $
//final intqueue

import java.util.Iterator;
import java.util.NoSuchElementException;

class intqueue implements Iterable<Integer> {

   private class node {
      int linenr; 
      node link;  
   }
   private int count = 0;
   private node front = null; 
   private node rear = null;

   public void insert (int number) { //number = linenr
      ++count;
      //misc.trace (count);
      //--CASE: The queue has no list --
      if(front == null){ 
        node n = new node();
        n.linenr = number;
        front = n;
        return;
      }
      //--CASE: The list has only one element--
      else if(rear == null){
        node n = new node();
        n.linenr = number;
        front.link = n;
        rear = n;
        return;
      }
      //CASE: 
      else { //add a new node to the end of the list in O(1)
        node n = new node();
        n.linenr = number;
        rear.link = n;
        rear = n;
      }
   }
 
   public void printLines(){
     for(node header = front; header != null; header = header.link){
       System.out.print(header.linenr + " ");
     }
   }
   
   public boolean empty() {
      return count == 0;
   }

   public int getcount() {
      return count;
   }

   public Iterator<Integer> iterator() {
      return new iterator();
   }

   private class iterator implements Iterator<Integer> {
      node curr = front;

      public boolean hasNext() {
         return curr != null;
      }

      public Integer next() {
         if (curr == null) throw new NoSuchElementException();
         Integer next = curr.linenr;
         curr = curr.link;
         return next;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

}
