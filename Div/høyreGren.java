public String h�yreGren()
  {
    StringBuilder s = new StringBuilder();
    s.append("[");
   
    Node<T> p = rot;  
    if (p != null) {
      while(p.h�yre != null || p.venstre != null) {
        s.append(p).append(",").append(" ");
        p = (p.h�yre != null) ? p.h�yre : p.venstre;
      }
      s.append(p.verdi);
    }
    s.append("]");
   
    return s.toString();
  }