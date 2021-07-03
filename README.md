# Sistema per la configurazione di computer
## Operazioni preliminari
### Database
* Installare **MariaDB** per la gestione del server SQL
* Creare un'istanza chiamata "simpleapi"

### Back End
* Aprire la cartella *server* all'interno di un IDE (preferibilmente **Visual Studio Code**).
* *EVENTUALE*: Tramite il file "src/main/resources/application.properties", modificare:
  * nome utente
  * password
  * porta di accesso
* Eseguire il codice per creare le tabelle, che verranno create da Hibernate alla prima esecuzione.
* Successivamente, eseguire lo script per popolare le tabelle.

### Front End
Per eseguire il front end, è necessario installare il framework Angular Material. Seguire le seguenti instruzioni per l'installazione:
* Installare Node.JS. Per verificare la versione digitare da terminale `node -v`.
* Installare NPM Package Manager. Per verificare la versione digitare da terminale `npm -v`

  **NOTA: NPM Client viene installato insieme a Node.JS**
* A questo punto digitare a terminale `npm install -g @angular/cli`. Per maggiori informazioni leggere il README del client.

Una guida più completa è disponibile al seguente indirizzo web: https://angular.io/guide/setup-local.

## Esecuzione del codice
* Avviare il server SQL
* Avviare il codice server del back end
* Eseguire il comando `ng serve` per eseguire il front end

  **NOTA: Riferirsi al README del client per ulteriori informazioni**
* Aprire la pagina `localhost:4200`

## Note finali
Per la visualizzazione web, utilizzare il browser Google Chrome
