  public String[] grener()
  {
      Node<T> p = rot;
      TabellStakk<Node<T>> nodeStakk = new TabellStakk<>();     //stakk for nodene
      TabellStakk<String> stringStakk = new TabellStakk<>();    //stakk for string verdier

      StringBuilder s = new StringBuilder();
      String[] gren = new String[1];
      int i = 0;
      s.append('[');                                            //f�rste parantes blir satt
      
      while(!stringStakk.tom()|| p!=null)                       
      {
          if(p!=null)
          {          
            s.append(p.verdi);                                  //verdi blir lagt i stringbuilder
            nodeStakk.leggInn(p);                               //node blir lagt i stakk

            if(p.venstre !=null || p.h�yre != null)             
                s.append(',').append(' ');                      //legger til komma/mellomrom n�r tilfellet intreffer
            
            if(p.venstre != null && p.h�yre != null)            
                stringStakk.leggInn(s.toString());              //legger til stringen i stakken
            
            if (p.venstre != null)
                p = p.venstre;                                  //g�r til venstre om det er mulig
            else p = p.h�yre;                                   //ellers h�yre
          }
          else
          {
              s.append(']');            //legger til bakre parantes
              
              gren[i] = s.toString();       //flytter Stringbuilder inn i retur verdi
              String[] temp = new String[gren.length+1];        //lager ny array med dobbelt s� stor plass
              System.arraycopy(gren, 0, temp, 0, gren.length);  //kopierer returarray til den nye arrayen
              gren = temp;                                      //setter returarray som den nye array
              i++;                                              //flytter i til ledig plass i array
 
              s.setLength(0);                                   //stringBuilder blir nullet ut
              p = nodeStakk.taUt();                             //p blir flyttet til forrige node siden
                                                                // p har truffet null
              while(p.venstre == null || p.h�yre == null)       //hvis p ikke har verdi p� h�yre og venstre
                p = nodeStakk.taUt();                           //blir p flyttet oppover
              
              p=p.h�yre;                                        //p blir flyttet inn i h�yre gren
              s = s.append(stringStakk.taUt());                 //tar ut fra string stakken 
          }   
      }     
    return gren;   
  }