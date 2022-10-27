package no.oslomet.cs.algdat.Oblig3;


import java.util.*;

public class SBinTre<T> {


    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, hoyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            hoyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.hoyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot,            // Setter p lik roten rot
                q = null;            // sette q som er over p lik null
        int cmp = 0;                  // Lager hjelpevariabel cmp

        while (p != null)       // fortsetter helt til treet er ferdig og p er lik null
        {
            q = p;                                 // q er forelder til p
            cmp = comp.compare(verdi,p.verdi);     // bruker komparatoren
            if (cmp < 0){
                p = p.venstre; // flytter p
            }
            else {
               p = p.hoyre;            // Setter p på venstre siden dersom
            }
        }

        p = new Node<>(verdi, q);                   // oppretter en ny node

        if (q == null) rot = p;                  // p blir rotnode
        else if (cmp < 0) q.venstre = p;         // venstre barn til q
        else q.hoyre = p;                        // høyre barn til q

        antall++;                                // én verdi mer i treet
        return true;                             // vellykket innlegging
    }

    public boolean fjern(T verdi) {
        if (verdi == null) return false;  // Treet har ingen nullverdier

        Node<T> p = rot, q = null;   // q skal være forelder til p

        while (p != null)            // Leter etter verdi
        {
            int cmp = comp.compare(verdi,p.verdi);      // Sammenligner
            if (cmp < 0) { q = p; p = p.venstre; }      // Går til venstre
            else if (cmp > 0) { q = p; p = p.hoyre; }   // Går til høyre
            else break;                                 // Den søkte verdien ligger i p
        }
        if (p == null) return false;   // Finner ikke verdi

        if (p.venstre == null || p.hoyre == null)  // Hvis noden som skal fjernes har 0 eller 1 barn
        {
            Node<T> b = p.venstre != null ? p.venstre : p.hoyre;  // b er barnet til p noden
            if (p == rot) {
                rot = b;
            }
            else if (p == q.venstre){
                q.venstre = b;
                if(b != null){
                    b.forelder = q;
                }
            }
            else {
                q.hoyre = b;
                if(b != null){
                    b.forelder = q;
                }
            }
        }
        else  // Hvis noden som skal fjernes har 2 barn
        {
            Node<T> s = p, r = p.hoyre;   // Finner neste i inorden
            while (r.venstre != null)
            {
                s = r;                      // s er forelder til r
                r = r.venstre;
            }

            p.verdi = r.verdi;   // Kopierer verdien i r til p

            if (s != p) {
                s.venstre = r.hoyre;
                if(r.hoyre != null){
                    r.hoyre.forelder = s;
                }

            }
            else {
                s.hoyre = r.hoyre;
                if(r.hoyre != null){
                    r.hoyre.forelder = s;
                }
            }
        }

        antall--;      // Det er nå én node mindre i treet
        endringer++;   // Det er gjort en ny endring på treet
        return true;
    }

    public int fjernAlle(T verdi) {
        if(tom()) { return 0; }        //
        int antallFjernet = 0;
        while(fjern(verdi)) { antallFjernet++; }

        return antallFjernet;    }

    public int antall(T verdi) {

        Node<T> p = rot;
        int antallDuplikater = 0;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi); // Sammenligner mellom den gitt verdien og p verdien
            if (cmp < 0) p = p.venstre; // Umulig at like eller større verdier kommer til venstre i binæretrær
            else {
                p = p.hoyre; // Verdien på høyre siden kan alltid være større eller lik.
                if (cmp == 0)       // Ellers hvis cmp lik 0 legger vi til at verdien har duplikater i treet
                    antallDuplikater++;
            }

        }
        return antallDuplikater; // returnerer antall forekomster av verdier i treet

    }


    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
       Objects.requireNonNull(p);

        while (true)
        {
            if (p.venstre != null) p = p.venstre;
            else if (p.hoyre != null) p = p.hoyre;
            else return p;
        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        if (p.forelder == null) return null;

        if (p == p.forelder.hoyre)
            return p.forelder;
        else if (p.forelder.hoyre == null)
            return p.forelder;
        else return førstePostorden(p.forelder.hoyre);

    }

    public void postorden(Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
