public String høyreGren()
  {
    StringBuilder s = new StringBuilder();
    s.append("[");
   
    Node<T> p = rot;  
    if (p != null) {
      while(p.høyre != null || p.venstre != null) {
        s.append(p).append(",").append(" ");
        p = (p.høyre != null) ? p.høyre : p.venstre;
      }
      s.append(p.verdi);
    }
    s.append("]");
   
    return s.toString();
  }