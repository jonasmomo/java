package oblig;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class Oblig1
{

//------------------------------------Oppgave 1------------------------------------
  public static int maks(int[] a)  //------Ferdig
  {
    if (a.length < 1)
    {
      throw new NoSuchElementException("Intervallet et tomt.");
    }

    for (int i = 0; i < a.length - 1; i++)
    {
      if (a[i] > a[i + 1])
      {
        int temp = a[i];
        a[i] = a[i + 1];
        a[i + 1] = temp;

      }
    }
    return a[a.length - 1];
  }

//------------------------------Oppgave 2------------------------------------
  public static void sortering(int[] a)  //------Ferdig
  {
    for (int j = 0; j < a.length - 1; j++)
    {
      for (int i = 0; i < a.length - 1; i++)
      {
        if (a[i] > a[i + 1])
        {
          int temp = a[i];
          a[i] = a[i + 1];
          a[i + 1] = temp;
        }
      }
    }
  }

//------------------------------------Oppgave 3------------------------------------
  public static int antallUlikeSortert(int[] a)   //------Ferdig
  {
    for (int i = 0; i < a.length - 1; i++)
    {
      if (a[i] > a[i + 1])
      {
        throw new IllegalStateException("Tabellen er ikke sortert.");
      }
    }

    if (a.length < 1)
    {
      return 0;
    } // Returnerer 0 hvis tabellen er tom

    int ant = 1;  // Startverdien er allerede tellt opp

    for (int j = 0; j < a.length - 1; j++)
    {
      if (a[j] < a[j + 1])
      {
        ant++;
      }
    }
    return ant;
  }

//------------------------------------Oppgave 4------------------------------------
  public static int antallUlikeUsortert(int[] a)//------Ferdig
  {
    if (a.length < 1)
    {
      return 0;
    }
    int max = a[0];
    int antU = 1;
    for (int i = 0; i < a.length; i++)
    {
      if (a[i] > max)
      {
        max = a[i];
      }
    }
    for (int i = 0; i < max; i++)
    {
      for (int j = 0; j < a.length; j++)
      {
        if (a[j] == i)
        {
          antU++;
          break;
        }
      }
    }
    return antU;
  }

//------------------------------------Oppgave 5------------------------------------
  public static void rotasjon(char[] a)   //Ferdig
  {

    if (a.length > 0)
    {
      char temp = a[a.length - 1];

      for (int i = a.length - 1; i > 0; i--)
      {
        a[i] = a[i - 1];
      }
      a[0] = temp;
    }
  }

//------------------------------------Oppgave 6------------------------------------
  public static void bytt(char[] a, int v, int h)
  {
    char temp = a[v];
    a[v] = a[h];
    a[h] = temp;
  }

  public static void rotasjon(char[] a, int k)
  {
    if (a.length > 0)
    {
      k = k % a.length;

      if (k < 0)
      {
        k += a.length;
      }

      int v = 0, h = a.length - 1;

      while (v < h)
      {
        bytt(a, v++, h--);
      }
      v = 0;
      h = k - 1;

      while (v < h)
      {
        bytt(a, v++, h--);
      }
      v = k;
      h = a.length - 1;

      while (v < h)
      {
        bytt(a, v++, h--);
      }
    }
  }
//------------------------------------Oppgave 7------------------------------------
  public static String toString(int[] a, char v, char h, String mellomrom)
  {
    if (a.length < 0)
    {
      return null;
    } else if (a.length == 0)
    {
      return v + mellomrom + h + "";
    }


    StringBuilder b = new StringBuilder();
    b.append(v);
    for (int i = 0;; i++)
    {
      b.append(a[i]);
      if (i == a.length - 1)
      {
        return b.append(h).toString();
      }
      b.append(mellomrom);
    }
  }

//------------------------------------Oppgave 8a------------------------------------
  public static int[] tredjeMinst(int[] a)
  {
    int n = a.length;

    if (n < 3)
    {
      throw new IllegalArgumentException("Tabellen inneholder minzre enn tre elementer.");
    }

    int mi = 0;
    int nmi = 1;
    int tmi = 2;

    if (a[2] < a[1] && a[2] < a[0]){mi = 2;tmi = 0;}
    if (a[2] < a[1]){nmi = 2;tmi = 1;}
    if (a[1] < a[0]){mi = 1;nmi = 0;}
    if (a[1] > a[2]){nmi = 2;tmi = 1;}

    int m = a[mi];
    int nm = a[nmi];
    int tm = a[tmi];

    for (int i = 3; i < n; i++)
    {
      if (a[i] < tm)
      {
        if (a[i] < nm)
        {
          if (a[i] < m)
          {
            tmi = nmi;
            tm = nm;

            nmi = mi;
            nm = m;

            mi = i;
            m = a[i];
          } else
          {
            tmi = nmi;
            tm = nm;

            nmi = i;
            nm = a[i];
          }
        } else
        {
          tmi = i;
          tm = a[i];
        }
      }
    }
    return new int[]
            {
              m, nm, tm
            };
  }
//------------------------------------Oppgave 8b------------------------------------
  
  public static int minst(int[] a)
  {
    if (a.length < 1)
    {
      throw new NoSuchElementException("Intervallet et tomt.");
    }

    for (int i = 0; i < a.length - 1; i++)
    {
      if (a[i] < a[i + 1])
      {
        int temp = a[i];
        a[i] = a[i + 1];
        a[i + 1] = temp;

      }
    }
    return a[a.length - 1];
  }


  public static void tredjeMinstTest()
  {
    int[] a = {8,3,5,7,9,6,10,2,1,4};  // maksverdien 1 er i posisjon 8, 
                                       // men etter tredjeminst() er kjørt er minste-pos lik 1.
                                       // Jaja
    if (minst(a) != 1){
      System.out.println("Gir feil posisjon for minsteverdi.");
    }

    a = new int[0];  // en tom tabell, lengde lik 0
    boolean unntak = false;

    try
    {
      minst(a);
    }
    catch (Exception e)
    {
      unntak = true;
      if (!(e instanceof java.util.NoSuchElementException))
      {
        System.out.println("Feil unntak for en tom tabell!");
      }
    }

    if (!unntak)
    {
      System.out.println("Det skal kastes unntak for en tom tabell!");
    }
  }

//------------------------------------Oppgave 9------------------------------------
  public static int[] kMinst(int[] a, int k)
  {
    int n = a.length;
    if (k < 1 || k > n)
    {
      throw new IllegalArgumentException("Ugyldig verdi av k.");
    }
    
    int[] verdier = null;
    
    System.arraycopy(a, 0, verdier, 0, k);
    
    sortering(verdier);
    
    //flettemetode av noe slag
    
    
    
    return a;
  }

//------------------------------------Oppgave 10------------------------------------
  public static int[] bokstavfrekvens(String url) throws IOException
  {
    InputStream inn =
            new BufferedInputStream((new URL(url)).openStream());

    // Her skal resten av koden komme


    return null;

  }
}