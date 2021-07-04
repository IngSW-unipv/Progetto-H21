# Sistema per la configurazione di computer
## Operazioni preliminari
* Per la corretta esecuzione del progetto consigliamo l'utilizzo di **Visual Studio Code**.
* Verificare inoltre di avere installato correttamente la jdk (consigliata versione 11 o superiore).
* Per maggiori dettagli consultare il seguente link: `https://code.visualstudio.com/docs/java/java-tutorial`
* Clonare il progetto dove si preferisce: `git clone https://github.com/IngSW-unipv/Progetto-H21.git`
* Importare il progetto su visual studio code: `File -> Open Folder ->  YourPath/ProgettoH21`
* In questo modo sarà presente sia il client che il server in una sola cartella chiamat Progetto-H21.

### Database
* Installare **MariaDB** per la gestione del server SQL
* Creare un'istanza chiamata "simpleapi": `CREATE DATABASE simpleapi`;

### Back End
* Aprire la cartella *server*.
* *EVENTUALE*: Tramite il file "src/main/resources/application.properties", modificare:
  * nome utente
  * password
  * porta di accesso
* Lanciare il backend dal main per creare le tabelle del DB, che verranno create da Hibernate alla prima esecuzione.
* Successivamente, eseguire lo script su MariaDB per popolare le tabelle.
* Problemi comuni: 
  * Se ci sono warning relativi alla versione di java verificare nel pom.xml che la java.version coincida con la propria versione.

### Front End
Per eseguire il front end, è necessario installare il framework Angular. Seguire le seguenti instruzioni per l'installazione:
* Installare Node.JS. Per verificare la versione digitare da terminale `node -v`.
* Installare NPM Package Manager. Per verificare la versione digitare da terminale `npm -v`

Una guida più completa è disponibile al seguente indirizzo web: `https://angular.io/guide/setup-local`.

  **NOTA: NPM Client viene installato insieme a Node.JS**
* Entrare tramite terminale(Command prompt, NO powershell) di visual studio code nella cartella relativa al client: `cd Client`.
* Digitare a terminale `npm install -g @angular/cli` per installare il node_module(all'interno ci sono le librerie relative al client). Per maggiori informazioni leggere il README del client.

## Esecuzione del codice
* Avviare il server SQL
* Avviare il codice server del back end presente nel main
* Per eseguire il front end, eseguire il comando `ng serve` dal terminale di visual studio code dopo aver verificato di essere nella cartella Client 

* Aprire la pagina web `localhost:4200`

## Note finali
Per la visualizzazione web, consigliamo il browser Google Chrome
