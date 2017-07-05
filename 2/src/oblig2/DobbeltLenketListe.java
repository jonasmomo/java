package oblig2;

import java.util.*;

public class DobbeltLenketListe<T> implements Liste<T>
{

  private static final class Node<T>       // en indre nodeklasse
  {

    private T verdi;
    private Node<T> forrige, neste;

    private Node(T verdi, Node<T> forrige, Node<T> neste)   // konstruktør
    {
      this.verdi = verdi;
      this.forrige = forrige;
      this.neste = neste;
    }
  }
  private Node<T> hode;         // peker til den første i listen
  private Node<T> hale;         // peker til den siste i listen
  private int antall;           // antall noder i listen
  private int antallEndringer;  // antall endringer i listen

  private void indeksKontroll(int indeks)
  {
    if (indeks < 0)
    {
      throw new IndexOutOfBoundsException("Indeks "
              + indeks + " er negativ!");
    } else if (indeks >= antall)
    {
      throw new IndexOutOfBoundsException("Indeks "
              + indeks + " >= antall(" + antall + ") noder!");
    }
  }

  private static <T> void nullSjekk(T verdi) // Oppgave 2
  {
    if (verdi == null)
    {
      throw new NullPointerException("Kan ikke legge inn null-verdier.");
    }
  }

  private Node<T> finnNode(int indeks)
  {
    if (indeks < (antall / 2))
    {
      Node<T> p = hode;
      for (int i = 0; i < indeks; i++)
      {
        p = p.neste;
      }
      return p;
    } else
    {
      Node<T> p = hale;
      for (int i = antall - 1; i > indeks; i--)
      {
        p = p.forrige;
      }
      return p;
    }
  }

  public DobbeltLenketListe()  // konstruktør
  {
    hode = hale = null;
    antall = 0;
    antallEndringer = 0;
  }

  public boolean tom() // Oppgave 2
  {
    if (antall == 0)
    {
      return true;
    }
    return false;
  }

  public int antall() // Oppgave 2
  {
    return antall;
  }

  public void nullstill()
  {
    Node<T> p = hode, q;

    while (p != null)
    {
      q = p.neste;
      p.neste = null;
      p.verdi = null;
      p = q;
    }

    hode = hale = null;

    antallEndringer++;
    antall = 0;
  }

  public boolean inneholder(T verdi)
  {
    return indeksTil(verdi) != -1;
  }

  public void leggInn(int indeks, T verdi)
  {

    if (indeks < 0)
    {
      throw new IndexOutOfBoundsException("Indeks "
              + indeks + " er negativ!");
    } else if (indeks > antall)
    {
      throw new IndexOutOfBoundsException("Indeks "
              + indeks + " > antall(" + antall + ") noder!");
    }

    nullSjekk(verdi);


    if (antall == 0 && indeks == 0)
    {
      hale = hode = new Node<>(verdi, null, null);

    } else if (indeks == 0)
    {
      Node<T> ghode = hode; // temp
      hode = new Node<>(verdi, null, ghode);
      ghode.forrige = hode;

    } else if (indeks == antall)
    {
      Node<T> ghale = hale; // temp
      hale = new Node<>(verdi, ghale, null);
      ghale.neste = hale;


    } else
    {
      Node<T> p = hode;
      for (int i = 0; i < indeks - 1; i++)
      {
        p = p.neste;
      }
      Node<T> temp = p;
      p.neste = new Node<>(verdi, temp, p.neste);
      temp.neste = p.neste;
      p.neste.forrige = temp;
      p.neste.neste.forrige = p.neste;

    }

    antall++;
    antallEndringer++;

  }

  public boolean leggInn(T verdi) // Oppgave 2
  {
    nullSjekk(verdi);

    if (antall == 0)
    {
      hode = hale = new Node<>(verdi, null, null);
    } else
    {
      hale = hale.neste = new Node<>(verdi, hale, null);
    }
    antall++;
    antallEndringer++;
    return true;
  }

  public T hent(int indeks)
  {
    indeksKontroll(indeks);
    return finnNode(indeks).verdi;
  }

  public int indeksTil(T verdi)
  {
    if (verdi == null)
    {
      return -1;
    }

    Node<T> p = hode;

    for (int indeks = 0; indeks < antall; indeks++)
    {
      if (p.verdi.equals(verdi))
      {
        return indeks;
      }

      p = p.neste;
    }

    return -1;
  }

  public T oppdater(int indeks, T nyverdi)
  {
    nullSjekk(nyverdi);
    indeksKontroll(indeks);

    Node<T> p = finnNode(indeks);        // bruker finnNode

    T gammelVerdi = p.verdi;
    p.verdi = nyverdi;

    antallEndringer++;

    return gammelVerdi;
  }

  public T fjern(int indeks)
  {

    indeksKontroll(indeks);

    T temp;

    if (antall == 1)
    {
      temp = hode.verdi;
      hode = hode.neste;
      hale = null;
    } else if (indeks == 0)
    {
      temp = hode.verdi;
      hode = hode.neste;
      hode.forrige = null;
    } else if (indeks == antall - 1)
    {
      temp = hale.verdi;
      hale = hale.forrige;
      hale.neste = null;
    } else
    {
      Node<T> p = finnNode(indeks - 1);
      Node<T> q = p.neste;

      temp = q.verdi;

      if (q == hale)
      {
        hale = p;
      }

      p.neste = q.neste;
      q.neste.forrige = p;
      q.forrige = null;

    }

    antallEndringer++;
    antall--;

    return temp;
  }

  public boolean fjern(T verdi)
  {
    nullSjekk(verdi);

    Node<T> q = hode, p = null;

    while (q != null)
    {
      if (q.verdi.equals(verdi))
      {
        break;
      }
      p = q;
      q = q.neste;
    }

    if (q == null)
    {
      return false;
    } else if (q == hode && antall == 1)
    {
      hode = hale = null;
    } else if (q == hode)
    {
      q = q.neste;
      hode = q;
      hode.forrige = null;
    } else if (q == hale)
    {
      hale = hale.forrige;
      hale.neste = null;
    } else
    {
      p.neste = q.neste;
      q.neste.forrige = p;
    }
    antallEndringer++;
    antall--;

    return true;
  }

  private class DobbeltLenketListeIterator implements Iterator<T>
  {

    private Node<T> p;
    private boolean fjernOK;
    private int forventetAntallEndringer;

    private DobbeltLenketListeIterator()
    {
      p = hode;         // p starter på den første i listen
      fjernOK = false;  // blir sann når next() kalles
      forventetAntallEndringer = antallEndringer;  // teller endringer
    }

    private DobbeltLenketListeIterator(int indeks)
    {
      {
        p = hode;         // p starter på den første i listen
        for (int i = 0; i < indeks; i++)
        {
          p = p.neste;
        }
        fjernOK = false;  // blir sann når next() kalles
        forventetAntallEndringer = antallEndringer;  // teller endringer
      }
    }

    public boolean hasNext()
    {
      return p != null;
    }

    public T next()
    {
      if (antallEndringer != forventetAntallEndringer)
      {
        throw new ConcurrentModificationException("Listen er endret!");
      }

      if (!hasNext())
      {
        throw new NoSuchElementException("Tomt eller ingen flere verdier!");
      }

      fjernOK = true;            // nå kan remove() kalles

      T denneVerdi = p.verdi;    // tar vare på verdien i p

      p = p.neste;               // flytter p til den nestenoden

      return denneVerdi;         // returnerer verdien
    }

    public void remove()
    {
      if (antallEndringer != forventetAntallEndringer)
      {
        throw new ConcurrentModificationException("Listen er endret!");
      }

      if (!fjernOK)
      {
        throw new IllegalStateException("Ulovlig tilstand!");
      }

      fjernOK = false;               // remove() kan ikke kalles på nytt
      Node<T> q = hode;              // hjelpepeker
      
      if(p == null && antall == 1)
      {
        hode = hale = null;
      }else if (hode.neste == p)           // skal den første fjernes?
      {
        hode = hode.neste;           // den første fjernes
        hode.forrige = null;
      }else if (p == null)
      {
        hale = hale.forrige;
        hale.neste = null;
      }else
      {
        // må finne forgjengeren til forgjengeren til p

        Node<T> r = hode;
        while (r.neste.neste != p)
        {
          r = r.neste;               // flytter r
        }

        q = r.neste;                 // det er q som skal fjernes
        r.neste = p;                 // "hopper" over q
        p.forrige = r;
        
        q.forrige = null;
        q.neste = null;
        
      }
      antallEndringer++;             // en ny endring i treet
      forventetAntallEndringer++;    // en lokal endring
      antall--;                      // en node mindre i listen 
    }
  } // class DobbeltLenketListeIterator  

  public Iterator<T> iterator()
  {
    return new DobbeltLenketListeIterator();
  }

  public Iterator<T> iterator(int indeks)
  {
    indeksKontroll(indeks);
    return new DobbeltLenketListeIterator(indeks);
  }

  public String toString() // Oppgave 3
  {
    StringBuilder s = new StringBuilder();

    s.append("[");

    if (!tom())
    {
      Node<T> p = hode;
      s.append(p.verdi);

      p = p.neste;

      while (p != null)
      {
        s.append(',').append(' ').append(p.verdi);
        p = p.neste;
      }
    }

    s.append(']');

    return s.toString();
  }

  public String omvendtString() //Oppgave 3
  {
    StringBuilder s = new StringBuilder();

    s.append("[");

    if (!tom())
    {
      Node<T> p = hale;
      s.append(p.verdi);

      p = p.forrige;

      while (p != null)
      {
        s.append(',').append(' ').append(p.verdi);
        p = p.forrige;
      }
    }

    s.append(']');

    return s.toString();
  }

  public static <T> int maks(Liste<T> liste, Comparator<? super T> c)
  {
    if (liste.tom())
    {
      throw new NoSuchElementException("listen er tom");
    }

    Iterator<T> it = liste.iterator();
    int m = 0;
    T maksverdi = it.next();  // finnes siden listen ikke er tom

    for (int i = 1; it.hasNext(); i++)
    {
      T verdi = it.next();
      if (c.compare(verdi, maksverdi) > 0)
      {
        m = i;
        maksverdi = verdi;
      }
    }
    return m;
  }
} // class DobbeltLenketListe