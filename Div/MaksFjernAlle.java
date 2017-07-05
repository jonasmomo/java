public int maksFjernAlle()
  {
      if(tom()) return 0;
      
      Node<T> p = rot, maks = p, q = null;
      int fjern = 1;
      while(p.h�yre != null)
      {
          int cmp = comp.compare(p.h�yre.verdi, maks.verdi);
          
          if(cmp > 0)
          {
              q = p;
              maks = p.h�yre;
              fjern = 1;
          }
          else if(cmp == 0)
          {
              fjern++;
          }
          p = p.h�yre;
      }
      
      if(maks.venstre != null)  // maks har barn til venstre
      {
          if(maks == rot)   // maks er roten
          {
              rot = rot.venstre;
          }
          
          else  // maks er ikke roten
          {
              q.h�yre = maks.venstre;
          }
          
          if(fjern == 1)    // maks har ikke noen barn til h�yre
                  antallEttBarn--;
          else  // maks har barn til h�yre
          {
              antallIngenBarn--;
              antallEttBarn -= fjern-2;
              antallToBarn--;
          }
      }
      else // maks har IKKE barn til venstre
      {
          if(maks == rot)
          {
              rot = null;
              antallEttBarn = antallToBarn = antallIngenBarn = 0;
          }
          else // maks er ikke rot
          {
              q.h�yre = null;
              antallEttBarn -= fjern;
          }
            
      }
     
      antall -= fjern;
      endringer++;
      
      return fjern;
  }