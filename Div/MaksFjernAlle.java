public int maksFjernAlle()
  {
      if(tom()) return 0;
      
      Node<T> p = rot, maks = p, q = null;
      int fjern = 1;
      while(p.høyre != null)
      {
          int cmp = comp.compare(p.høyre.verdi, maks.verdi);
          
          if(cmp > 0)
          {
              q = p;
              maks = p.høyre;
              fjern = 1;
          }
          else if(cmp == 0)
          {
              fjern++;
          }
          p = p.høyre;
      }
      
      if(maks.venstre != null)  // maks har barn til venstre
      {
          if(maks == rot)   // maks er roten
          {
              rot = rot.venstre;
          }
          
          else  // maks er ikke roten
          {
              q.høyre = maks.venstre;
          }
          
          if(fjern == 1)    // maks har ikke noen barn til høyre
                  antallEttBarn--;
          else  // maks har barn til høyre
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
              q.høyre = null;
              antallEttBarn -= fjern;
          }
            
      }
     
      antall -= fjern;
      endringer++;
      
      return fjern;
  }