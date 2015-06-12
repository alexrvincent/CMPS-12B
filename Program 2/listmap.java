// $Id: listmap.java,v 1.5 2013-10-16 17:10:32-07 - - $
//Final listmap

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import static java.lang.System.*;

class listmap implements Iterable<Entry<String,intqueue>> {

   private class node implements Entry<String,intqueue> {
     String key;                                         
      intqueue queue = new intqueue();             
      node link;
      
      public String getKey() {
         return key;
      }
      public intqueue getValue() {
         return queue;
      }
      public intqueue setValue (intqueue queue) {
         throw new UnsupportedOperationException();
      }
   }
   private node head = null;

   public listmap() {
      // Not needed, since head defaults to null anyway.
   }

   public void insert (String key, int linenr) {
     //misc.trace ("insert", key, linenr);
     node prev = null;
     if(head == null){ //If list is empty, create the first node
       node n = new node(); 
       n.key = key;
       n.queue.insert(linenr);
       head = n; 
       return;
     }
     for(node curr = head; curr!= null; curr=curr.link){
       if(key.compareTo(curr.getKey()) == 0){ //word is the same
         curr.queue.insert(linenr);
         break;
       }else if(key.compareTo(curr.getKey()) < 0){ //word is before
        node n = new node(); 
        n.key = key;
        n.link = curr;
        n.queue.insert(linenr);
        head = n; 
        break;
       }else if(key.compareTo(curr.getKey()) > 0){// word is after
         if(curr.link == null){//insert word at end of there is no more
           node n = new node();
           n.key = key;
           n.queue.insert(linenr);
           curr.link = n;
           break;
         }else if(key.compareTo(curr.link.getKey()) < 0){// insert word after curr and before
          node n = new node();
          n.key = key;
          n.link = curr.link;
          n.queue.insert(linenr);
          curr.link = n;
          break;
        }
       }
      prev = curr;
    }
  }


   public Iterator<Entry<String,intqueue>> iterator() {
      return new iterator();
   }
   private class iterator 
           implements Iterator<Entry<String,intqueue>> {
      node curr = head; 

      public boolean hasNext() {
         return curr != null;
      }

      public Entry<String,intqueue> next() {
         if (curr == null) throw new NoSuchElementException();
         node next = curr; 
         curr = curr.link; 
         return next;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

   }

}