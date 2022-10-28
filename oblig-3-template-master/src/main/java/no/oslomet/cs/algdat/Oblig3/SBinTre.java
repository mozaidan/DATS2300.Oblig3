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

        while (p != null) { //Dersom p ikke er lik null
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
               p = p.hoyre;      // Setter p på venstre siden dersom verdien er større
                                    // enn forelder noden q
            }
        }

        p = new Node<>(verdi, q);           // oppretter en ny node og setter verdien til q

        if (q == null) rot = p;                  // p blir rotnode
        else if (cmp < 0) q.venstre = p;         // venstre barn til q
        else q.hoyre = p;                        // høyre barn til q

        antall++; // én verdi mer i treet

        return true;                             // vellykket innlegging
    }

    public boolean fjern(T verdi) {
        if (verdi == null) return false;  // treet har ingen nullverdier

        Node<T> p = rot, q = null;   // q skal være forelder til p

        while (p != null)            // leter etter verdi
        {
            int cmp = comp.compare(verdi,p.verdi);      // sammenligner
            if (cmp < 0) { q = p; p = p.venstre; }      // går til venstre
            else if (cmp > 0) { q = p; p = p.hoyre; }   // går til høyre
            else break;    // den søkte verdien ligger i p
        }
        if (p == null) return false;   // finner ikke verdi

        if (p.venstre == null || p.hoyre == null)  // Tilfelle 1) og 2)
        {
            Node<T> b = p.venstre != null ? p.venstre : p.hoyre;  // b for barn
            if (p == rot) rot = b;
            else if (p == q.venstre) q.venstre = b;
            else q.hoyre = b;
        }
        else  // Tilfelle 3)
        {
            Node<T> s = p, r = p.hoyre;   // finner neste i inorden
            while (r.venstre != null)
            {
                s = r;    // s er forelder til r
                r = r.venstre;
            }

            p.verdi = r.verdi;   // kopierer verdien i r til p

            if (s != p) s.venstre = r.hoyre;
            else s.hoyre = r.hoyre;
        }

        endringer++;   // treet er endret
        antall--;      // det er nå én node mindre i treet
        return true;    }

    public int fjernAlle(T verdi) {
        if (tom()) return 0;

        int verdiAntall = 0;

        while (fjern(verdi)) {
            verdiAntall++;
        }
        return verdiAntall;
    }


    public int antall(T verdi) {
        Node<T> p = rot;
        int antallDuplikater = 0; //Teller

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
        if (antall == 0) { //Returnerer ingenting dersom antall er null
            return;
        }
        Node<T> p = rot; //Initialiserer p som rot.

        int antallOppdatert = antall; //Hjelpevariabel som skal oppdatere antall verdier.

        p= førstePostorden(p); //Initialiserer den første verdien av p i postorden. Gjør slik at vi kan bruke nestePostorden.

        while (antallOppdatert != 0){
            if (p != null) {
                fjern(p.verdi); //bruker fjern() metoden og nullstiller for hver iterasjon der stopper ikke er null.
            }
            if (p != null) {
                p.verdi = null; //Oppdaterer p.verdi.
            }
            if (p != null) {
                p = nestePostorden(p); //Bruker nestePostorden() for å gå videre i treet.
            }
            antallOppdatert--; //Antall verdier oppdateres.
        }
    }


    private static <T> Node<T> førstePostorden(Node<T> p) {
       Objects.requireNonNull(p); // sikre at det ikke går inn en nullreferanse

        while (true) // Så langt det er true
        {
            if (p.venstre != null) p = p.venstre;
            else if (p.hoyre != null) p = p.hoyre;
            else return p;
        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        if (p.forelder == null) return null; // Dersom vi kommer til rot noden

        if (p == p.forelder.hoyre)    //hvis p er høyre barnet til sin sin foreldre
            return p.forelder; //   foreldren til p er den neste
        else if (p.forelder.hoyre == null) // ellers hvis høyrebarnet er null
            return p.forelder; // da er p.venstre det ene barnet til p. foreldre og dermed er den neste
        else return førstePostorden(p.forelder.hoyre); // Ellers er høyrebarnet til p de neste

    }

    public void postorden(Oppgave<? super T> oppgave) {
        Node<T> p = rot; //Initialiserer p som rot.

        Node<T> forste = førstePostorden(p); //Finner første node av metoden førstePostorden av p.
        oppgave.utførOppgave(forste.verdi);

        while (forste.forelder != null) { //While-løkke som looper gjennom treet og oppdaterer neste verdi i postorden.

            forste = nestePostorden(forste);
            oppgave.utførOppgave(Objects.requireNonNull(forste).verdi);         	}

    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {

        if (!tom()) {
            //Dersom treet ikke er tomt, så kaller vi rekursivt på metoden, start ved rot.
            postordenRecursive(rot, oppgave);
        }

    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {

        if (p == null) { //Returnerer dersom p er null.
            return;
        }
            postordenRecursive(p.venstre, oppgave); //Kaller rekursivt for p sitt venstrebarn
            postordenRecursive(p.hoyre, oppgave);  //Kaller rekursivt for p sitt høyrebarn
            oppgave.utførOppgave(p.verdi); //Kjører oppgaven
        }

    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
