public String omvendtString() {
    Stakk<T> stakkInorden   = new TabellStakk<T>();
   
    StringBuilder s = new StringBuilder();
    s.append("[");
   
    Iterator iterator = new InordenIterator();
    while(iterator.hasNext()) {
      stakkInorden.leggInn((T) iterator.next());
    }  
 
    while(stakkInorden.antall() > 1)
      s.append(stakkInorden.taUt()).append(",").append(" ");
   
    if(!stakkInorden.tom())
      s.append(stakkInorden.taUt());  
    s.append("]");
   
    return s.toString();  
  }