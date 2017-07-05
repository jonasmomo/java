// Jonas Myren Mo - s188089 - AlgDat Oblig3

package oblig3;

import java.util.*;
import hjelpeklasser.*;

public class SBinTre2<T> implements Beholder<T>
{

    private static final class Node<T> // en indre nodeklasse
    {

        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn

        private Node(T verdi, Node<T> v, Node<T> h) // konstruktør
        {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
        }

        private Node(T verdi) // konstruktør
        {
            this(verdi, null, null);
        }

        public String toString()
        {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                  // peker til rotnoden
    private int antall;                   // antall noder
    private int endringer;                // antall endringer
    private int høyde;                    // treets høyde
    private int antallIngenBarn;          // antall bladnoder
    private int antallToBarn;             // antall noder med to barn
    private int antallEttBarn;            // antall noder med kun ett barn

    private final Comparator<? super T> comp;  // komparator

    public SBinTre2(Comparator<? super T> c) // konstruktør
    {
        rot = null;
        antall = 0;
        endringer = 0;
        høyde = -1;
        antallIngenBarn = 0;
        antallEttBarn = 0;
        antallToBarn = 0;
        comp = c;
    }

    public static <T extends Comparable<? super T>> SBinTre2<T> lagTre()
    {
        return new SBinTre2<>(Komparator.<T>naturlig());
    }

    public static <T> SBinTre2<T> lagTre(Comparator<? super T> c)
    {
        return new SBinTre2<>(c);
    }

    public int antall() // antall verdier i treet
    {
        return antall;
    }

    public boolean tom() // er treet tomt?
    {
        return antall == 0;
    }

    public int høyde()
    {
        return høyde;
    }

    public int antallIngenBarn()
    {
        return antallIngenBarn;
    }

    public int antallEttBarn()
    {
        return antallEttBarn;
    }

    public int antallToBarn()
    {
        return antallToBarn;
    }

    public void nullstill()
    {
        rot = null;
        antall = 0;
        høyde = -1;
        antallIngenBarn = 0;
        antallEttBarn = 0;
        antallToBarn = 0;
    }

    //       Oppgave 2
    public boolean leggInn(T verdi)
    {
        if (verdi == null)
            throw new NullPointerException("Ulovlig nullverdi!");

        Node<T> p = rot, q = null;               // p starter i roten
        int cmp = 0;                             // hjelpevariabel
        int level = 0;

        while (p != null) // fortsetter til p er ute av treet
        {
            q = p;                                 // q forelder til p
            cmp = comp.compare(verdi, p.verdi);      // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
            level++;
        }

        p = new Node<>(verdi);                   // oppretter en ny node

        if (q == null)
        {
            rot = p;                  // rotnoden
            antallIngenBarn++;
        } else if (cmp < 0)
        {
            q.venstre = p;                      // til venstre for q
            if (q.høyre == null)
            {
                antallEttBarn++;
            } else
            {
                antallEttBarn--;
                antallToBarn++;
                antallIngenBarn++;
            }
        } else
        {
            q.høyre = p;                        // til høyre for q
            if (q.venstre == null)
            {
                antallEttBarn++;
            } else
            {
                antallEttBarn--;
                antallToBarn++;
                antallIngenBarn++;
            }
        }

        høyde = level;
        endringer++;                             // en endring
        antall++;                                // en ny verdi i treet

        return true;
    }

    public boolean inneholder(T verdi)
    {
        if (verdi == null)
            return false;

        Node<T> p = rot;                                // starter i roten
        while (p != null) // sjekker p
        {
            int cmp = comp.compare(verdi, p.verdi);     // sammenligner
            if (cmp < 0)
                p = p.venstre;                          // går til venstre
            else if (cmp > 0)
                p = p.høyre;                            // går til høyre
            else
                return true;                            // cmp == 0, funnet
        }
        return false;                                   // ikke funnet
    }

    //       Oppgave 10 (ikke gjort)
    public boolean fjern(T verdi)
    {
        if (verdi == null)
            return false;

        Node<T> p = rot, q = null;                      // q skal være forelder til p

        while (p != null)                               // leter etter verdi
        {
            int cmp = comp.compare(verdi, p.verdi);     // sammenligner
            if (cmp < 0)
            {
                q = p;
                p = p.venstre;
            } // går til venstre
            else if (cmp > 0)
            {
                q = p;
                p = p.høyre;
            } // går til høyre
            else
                break;                                  // den søkte verdien ligger i p
        }

        if (p == null)
            return false;                               // finner ikke verdi

        if (p.venstre == null || p.høyre == null)       // Tilfelle 1) og 2)
        {
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;  // b for barn
            if (p == rot)
                rot = b;
            else if (p == q.venstre)
                q.venstre = b;
            else
                q.høyre = b;
        } else // Tilfelle 3)
        {
            Node<T> s = p, r = p.høyre;                 // finner neste i inorden
            while (r.venstre != null)
            {
                s = r;                                  // s er forelder til r
                r = r.venstre;
            }

            p.verdi = r.verdi;                          // kopierer verdien i r til p

            if (s != p)
                s.venstre = r.høyre;
            else
                s.høyre = r.høyre;
        }

        endringer++;
        antall--;                                       // det er nå én node mindre i treet
        return true;
    }

    private class InordenIterator implements Iterator<T>
    {

        private Stakk<Node<T>> s = new TabellStakk<>();
        private Node<T> p = null;
        private int iteratorendringer;

        private Node<T> først(Node<T> q)
        {
            while (q.venstre != null)
            {
                s.leggInn(q);
                q = q.venstre;
            }
            return q;
        }

        public InordenIterator() // konstruktør
        {
            if (rot == null)
                return;
            p = først(rot);
            iteratorendringer = endringer;
        }

        public T next()
        {
            if (iteratorendringer != endringer)
                throw new ConcurrentModificationException();

            if (!hasNext())
                throw new NoSuchElementException();

            T verdi = p.verdi;

            if (p.høyre != null)
                p = først(p.høyre);
            else if (!s.tom())
                p = s.taUt();
            else
                p = null;

            return verdi;
        }

        public boolean hasNext()
        {
            return p != null;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<T> iterator()
    {
        return new InordenIterator();
    }

    public String toString()
    {
        StringBuilder s = new StringBuilder();   // StringBuilder
        s.append('[');                           // starter med [
        if (!tom())
            toString(rot, s);                    // den rekursive metoden
        s.append(']');                           // avslutter med ]
        return s.toString();                     // returnerer
    }

    private static <T> void toString(Node<T> p, StringBuilder s)
    {
        if (p.venstre != null)                   // p har et venstre subtre
        {
            toString(p.venstre, s);              // komma og mellomrom etter
            s.append(',').append(' ');           // den siste i det venstre
        }                                        // subtreet til p

        s.append(p.verdi);                       // verdien i p

        if (p.høyre != null)                     // p har et høyre subtre
        {
            s.append(',').append(' ');           // komma og mellomrom etter
            toString(p.høyre, s);                // p siden p ikke er den
        }                                        // siste noden i inorden
    }

    //       Oppgave 1
    public int antall(T verdi)
    {
        int antallVerdi = 0;

        Iterator iterator = new InordenIterator();
        while (iterator.hasNext())
        {
            if (iterator.next().equals(verdi))
                antallVerdi++;
        }
        return antallVerdi;
    }

    //       Oppgave 3
    public T min()
    {
        if (tom())
            throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot;
        while (p.venstre != null)
            p = p.venstre;
        return p.verdi;
    }

    public T nestMin()
    {
        if (antall < 2)
            throw new NoSuchElementException("Må ha minst to verdier for å finne nest minste.");

        Node<T> p = rot, q = null;

        while (p.venstre != null)
        {
            q = p;
            p = p.venstre;
        }

        if (p.høyre != null)
        {
            p = p.høyre;
            while (p.venstre != null)
                p = p.venstre;
        } else
            p = q;

        return p.verdi;
    }

    //       Oppgave 4
    public T minFjern()
    {
        if (tom())
            throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot, q = null;
        T fjern = null;
        if (p.venstre != null)
        {
            while (p.venstre != null)
            {
                q = p;
                p = p.venstre;
            }

            fjern = p.verdi;

            if (p.høyre != null)
            {
                q.venstre = p = p.høyre;
                antallEttBarn--;
            } else
            {
                q.venstre = null;
                if (q.høyre != null)
                {
                    antallToBarn--;
                    antallIngenBarn--;
                    antallEttBarn++;
                } else
                {
                    antallEttBarn--;
                }
            }
        } else
        {
            fjern = rot.verdi;
            rot = rot.høyre;
            antallEttBarn--;
            if (rot == null)
                antallEttBarn = antallToBarn = antallIngenBarn = 0;
        }

        endringer++;
        antall--;

        return fjern;
    }

    //       Oppgave 3
    public T maks()
    {
        if (tom())
            throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot;

        while (p.høyre != null)
            p = p.høyre;
        return p.verdi;
    }

    public T nestMaks()
    {
        if (antall < 2)
            throw new NoSuchElementException("Må ha minst to verdier for å finne nest minste.");

        Node<T> p = rot, q = null;

        while (p.høyre != null)
        {
            q = p;
            p = p.høyre;
        }

        if (p.venstre != null)
        {
            p = p.venstre;
            while (p.høyre != null)
                p = p.høyre;
        } else
            p = q;

        return p.verdi;
    }

    //       Oppgave 5
    public int maksFjernAlle()
    {
        if (tom())
            return 0;

        Node<T> p = rot, maks = p, q = null;
        int fjern = 1;
        while (p.høyre != null)
        {
            int cmp = comp.compare(p.høyre.verdi, maks.verdi);

            if (cmp > 0)
            {
                q = p;
                maks = p.høyre;
                fjern = 1;
            } else if (cmp == 0)
            {
                fjern++;
            }
            p = p.høyre;
        }

        if (maks.venstre != null)
        {
            if (maks == rot)
            {
                rot = rot.venstre;
            } else
            {
                q.høyre = maks.venstre;
            }

            if (fjern == 1)
                antallEttBarn--;
            else
            {
                antallIngenBarn--;
                antallEttBarn -= fjern - 2;
                antallToBarn--;
            }
        } else
        {
            if (maks == rot)
            {
                rot = null;
                antallEttBarn = antallToBarn = antallIngenBarn = 0;
            } else
            {
                q.høyre = null;
                antallEttBarn -= fjern;
            }

        }

        antall -= fjern;
        endringer++;

        return fjern;  // foreløpig kode
    }

    //       Oppgave 6
    public String høyreGren()
    {
        StringBuilder s = new StringBuilder();
        s.append("[");

        Node<T> p = rot;
        if (p != null)
        {
            while (p.høyre != null || p.venstre != null)
            {
                s.append(p).append(",").append(" ");
                p = (p.høyre != null) ? p.høyre : p.venstre;
            }
            s.append(p.verdi);
        }
        s.append("]");

        return s.toString();  // foreløpig kode
    }

    //       Oppgave 7
    public String omvendtString()
    {
        Stakk<T> stakkInorden = new TabellStakk<T>();

        StringBuilder s = new StringBuilder();
        s.append("[");

        Iterator iterator = new InordenIterator();
        while (iterator.hasNext())
        {
            stakkInorden.leggInn((T) iterator.next());
        }

        while (stakkInorden.antall() > 1)
            s.append(stakkInorden.taUt()).append(",").append(" ");

        if (!stakkInorden.tom())
            s.append(stakkInorden.taUt());
        s.append("]");

        return s.toString();  // foreløpig kode    
    }

    //       Oppgave 8
    public String[] grener()
    {
        if (tom())
        {
            return new String[0];
        }
        Node<T> p = rot, q = null, r = null;
        Stakk<Node<T>> s = new TabellStakk<>();
        Stakk<Node<T>> t = new TabellStakk<>();
        Stakk<String> ut = new TabellStakk<>();
        Stakk<Node<T>> end = new TabellStakk<>();

        p = rot;
        end.leggInn(p);
        if (p.høyre != null)
        {
            p = p.høyre;
            end.leggInn(p);
            while (true)
            {
                if (p.høyre != null)
                    p = p.høyre;
                else if (p.venstre != null)
                    p = p.venstre;
                else
                    break;
                end.leggInn(p);
            }
        }

        p = rot;
        s.leggInn(p);
        if (p.venstre != null)
        {
            p = p.venstre;
            s.leggInn(p);
            while (true)
            {
                if (p.venstre != null)
                    p = p.venstre;
                else if (p.høyre != null)
                    p = p.høyre;
                else
                    break;
                s.leggInn(p);
            }
        }

        int lengde = s.antall();
        for (int i = 0; i < lengde; i++)
            t.leggInn(s.taUt());

        ut.leggInn(t.toString());

        lengde = t.antall();
        for (int i = 0; i < lengde; i++)
            s.leggInn(t.taUt());

        r = s.taUt();

        if (!s.tom())
            q = s.kikk();
        else
        {
            String[] retur = new String[1];
            retur[0] = "[" + r.toString() + "]";
            return retur;
        }
        while (!s.toString().equals(end.toString()))
        {
            if (q.høyre != null && q.høyre != r)
            {
                while ((q.venstre != null && q.venstre != r) || (q.høyre != null && q.høyre != r))
                {
                    if (q.venstre != null && q.venstre != r)
                    {
                        q = q.venstre;
                        s.leggInn(q);
                    } else if (q.høyre != null && q.høyre != r)
                    {
                        q = q.høyre;
                        s.leggInn(q);
                    }
                    r = q;

                }

                lengde = s.antall();
                for (int i = 0; i < lengde; i++)
                    t.leggInn(s.taUt());
                ut.leggInn(t.toString());
                lengde = t.antall();
                for (int i = 0; i < lengde; i++)
                    s.leggInn(t.taUt());
                if (!s.toString().equals(end.toString()))
                    r = s.taUt();
            } else
            {
                if (!s.toString().equals(end.toString()))
                    r = s.taUt();
            }
            q = s.kikk();
        }

        String[] retur = new String[ut.antall()];
        int teller = retur.length - 1;
        while (!ut.tom())
        {
            retur[teller--] = ut.taUt();
        }
        return retur;
    }

    //       Oppgave 9
    private class BladnodeIterator implements Iterator<T>
    {
        // Instansvariabler, konstruktør og eventuelle
        // hjelpemetoder skal inn her

        private Stakk<Node<T>> s = new TabellStakk<>();  // for traversering
        private Node<T> p = null;                        // nodepeker
        private int iteratorendringer = endringer;       // iteratorendringer

        private Node<T> bladnode(Node<T> p, Stakk<Node<T>> s)  // hjelpemetode
        {
            while (true)  // må flytte p til første bladnode
            {
                if (p.venstre != null)
                {
                    if (p.høyre != null)
                        s.leggInn(p.høyre);
                    p = p.venstre;
                } else if (p.høyre != null)
                    p = p.høyre;
                else
                    return p;
            }
        }

        private BladnodeIterator()
        {
            if (!tom())
                p = bladnode(rot, s);
        }

        @Override
        public boolean hasNext()
        {
            return p != null;
        }

        @Override
        public T next()
        {
            if (iteratorendringer != endringer)
                throw new ConcurrentModificationException("Det er gjort endringer i treet!");

            if (!hasNext())
                throw new NoSuchElementException("Ingen flere verdier!");

            T temp = p.verdi;

            if (!s.tom())
                p = bladnode(s.taUt(), s);
            else
                p = null;

            return temp;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

    }  // BlodnodeIterator

    public Iterator<T> bladnodeiterator()
    {
        return new BladnodeIterator();
    }

} // SBinTre2