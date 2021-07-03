# Sistema per la configurazione di computer

## Preliminari

* È necessario installare un server SQL per il DB. La versione di SQL da utilizzare è MariaDB.
* Creare un istanza chiamata "simpleapi".

## Back End

* Per eseguire il back end aprire la cartella server all'interno di un IDE(preferibilmente Visual Studio Code).
* Modificare eventualmente la porta di accesso e le voci utente e password per il DB tramite il file application.properties.
* Runnare il codice per creare le tabelle che verranno create da Hibernate alla prima esecuzione.
* Successivamente eseguire lo script per popolare le tabelle.

## Front End

* Per eseguire il front end è necessario installare il framework Angular Material. Seguire le seguenti instruzioni per l'installazione https://angular.io/guide/setup-local.
* Installare Node.JS, per verificare la versione digitare da terminale node -v.
* Installare Npm Package Manager, per verificare la versione digitare da terminale npm -v. NOTA: Npm Client è installato insieme a Node.JS
* A questo punto digitare a terminale npm install -g @angular/cli. Per maggiori informazioni leggere il README del client.

## Note finali

Eseguire il Back End prima del Front End. Per visualizzare il progetto aprire Chrome alla pagina localhost:4200/
