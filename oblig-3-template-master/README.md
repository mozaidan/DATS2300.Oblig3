# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Mouaz Zaidan, S366611, s366611@oslomet.no

Hele obligen er løst ved hjelp fra kompendiet, og etter samarbeid
med andre. Jeg har også brukt vidoene som ligger ut i andre sin nettside.
De var veldig nyttige å se på. Ukesoppgaver var også en veldig god kilde.


# Warnings:
- Linje 13, intellij foreslår om gjør om gjøre noden (forelder) til final.
- Linje 37, intellij foreslår om å fjerne telleren endringer, det blir ikke brukt før vi kjører koden.
- Linje 48, intellij foreslår om å fjerne metoden inneholder(), dette er fordi vi brukte ikke den koden
- Linje 85, legginn() returner ingenting enn true, og derfor foreslår intellij om å endre til void..
- Siste to i linje 268, c og data er parametere som blir aldri brukt siden oppgave 5 mangler.

# Oppgavebeskrivelse

I oppgave 1 var det bare å kopiere koden fra kompendiet,
¨Det jeg gjorde var å erstatte ternary if med vanlig if og else setning
slik at koden skal være litt mer forståelig.
For at koden skulle fungere, måtte jeg legge til q i new Node<> slik at
ny noden bli verdien til q.

I oppgave 2 fikk jeg litt hjelp fra kompendiet, vi oppretter en teller
som legger til 1 hver gang vi oppdager en gjentatt eller duplikat av en gitt verdi.
Vi bruker while-løkke som fortsetter helt til vi kommer til null, 
I denne løkken er det best å bruke comperator slik at vi bruker retur verdiene fra comparatoren til å skille alle tilfellene.
Hver gang vi får -1, betyr det at verdien er mindre enn p, og dermed legges den til venstre.
om den er lik p får vi 0 så legges den til høyre.

I oppgave 3, jobbet jeg med å opprette førstePostorden(Node<T> p) og nestePostorden(Node<T> p).
I den første sikrer vi oss for å ikke motta nullverdier. Dersom det ikke skjer fungerer koden fint.
Her bruker jeg while-løkke for å sikre den nevnete tillfelle.
Den første i Postorden blir funnet når:
- ventre barnet er ikke lik null.
- eller dersom venstre barnet lik null, er høyrebarnet den første i Postorden.

I neste delen brukte jeg litt tid men det var fordi jeg leste det i kompendiet og prøvde litt med penn og pappir.
Først har vi if-setningen som dersom vi kommer til rot noden blir det returnert null.
Videre tester vi det forskjellige tillfellene slik at vi kommer til neste noden i Postorden etter noden i førstePostorden.

I oppgave 4, har vi sat p som rot,
videre bruker vi metoden førstePostorden() rekursivt for å begynne med å sette opp treet.
videre bruker vi nestePostorden() til å sette opp neste og den som kommer neste osv.
til slutt sjekker vi om at verdien ikke er null i hver node.
Private metoden har oppgave og kalle rekursivt for p slik at venstre og høyre barn settes riktig.


I oppgave 6 prøvde jeg først med egen kode som ikke fuket i det hele tatt, videre prøvde
jeg å endre litt med inpirasjon fra kompendiet, og det var når jeg satt opp de første to metodene.
Etter å ha lagt til nullstill, sluttet metodene å funke og passerte ikke testen. 
derfor måtte jeg se på noen gamle obliger og noen venner sine obliger, men fant ikke ut
hvorfor jeg fikk (ignored) hver gang jeg skulle kjøre testen.

