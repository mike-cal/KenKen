# ProgettoKenKen
Progetto realizzato per il corso di "Ingegneria del software"

---



## **Obiettivo:**
Occorre sviluppare un’applicazione completa dotata di GUI (Swing o web-based) che consenta di:
-	specificare la configurazione  del gioco (porre numeri nelle caselle, stabilire relazioni di precedenza tra caselle contigue etc.) e di poterla salvare/caricare su/da un repository (su file system o su db) in un formato opportuno
-	consentire a un utente di giocare inserendo i numeri nelle caselle. L’utente può abilitare il controllo del soddisfacimento dei vincoli man mano che si inseriscono i numeri. 
-	calcolare la soluzione (le soluzioni) del gioco (ad esempio  impiegando la tecnica backtracking). L’utente può specificare il numero massimo desiderato di soluzioni e navigare (con tasti tipo next/previous) sullo spazio delle soluzioni trovate, mostrando a  le varie soluzioni.

Nello sviluppo del progetto si devono utilizzare i Design Pattern ritenuti più adeguati motivandone opportunamente la scelta. Le fasi del processo di sviluppo devono essere documentate ricorrendo, ove necessario, all'uso di diagrammi UML.
Si richiede inoltre di effettuare il testing di uno o più moduli significativi impiegando un opportuno criterio e sfruttando le funzionalità offerte dal framework JUnit.



## **Descrizione del gioco**
Il KenKen è un gioco di logica, ispirato al Sudoku che consiste in una griglia di dimensioni variabili (i più comuni vanno dalle griglie 3x3 fino alle più difficili 6x6), nelle quali bisogna disporre le cifre da 1 a n senza che ci siano ripetizioni né nelle righe né nelle colonne (come per il Sudoku). La griglia inizialmente è totalmente vuota, e divisa in blocchi di diverse forme da linee più spesse; in ogni blocco viene riportato un numero, seguito da un operatore aritmetico (+, -, x o ÷), che indica l'operazione da effettuare tra le varie cifre del blocco.

La griglia va completata in modo che, effettuando l'operazione riportata in ciascun blocco tra le sue cifre, si ottenga esattamente il risultato richiesto (sempre un numero intero positivo). Le cifre si possono ripetere all'interno dei blocchi, a condizione però che non si trovino sulla stessa riga o colonna.
