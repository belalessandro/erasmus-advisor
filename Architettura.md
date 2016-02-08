# Architettura del codice Java #

## Package beans: ##
Assieme al package resources, rappresenta il MODEL dell’architettura MVC. Gli Entity Beans contenuti in questo package hanno una corrispondenza uno-ad-uno con lo schema del database: ad ogni relazione nel database corrisponde una classe bean, i cui attributi sono variabili private di istanza, accessibili solamente dai metodi getter / setter.
I tipi di dato sono stati mappati opportunamente per mantenere la compatibilità tra il modello relazionale e quello ad oggetti.

## Package database: ##
I metodi statici di ogni classe ricevono un’istanza di Connection ed eseguono la specifica query SQL. La connessione non viene mai chiusa, è responsabilità del chiamante (e.g. Servlet) chiudere correttamente la connessione e gestire eventuali transazioni.
Gli Statement, PrepearedStatement e ResultSet, al contrario, vengono sempre chiusi direttamente nel metodo dopo l’uso.
Nel passaggio di parametri ai metodi, al posto dei singoli campi, è possibile utilizzare gli appositi Bean, popolandoli preventivamente con i campi necessari. In questo modo, si ha maggiore flessibilità e omogeneità.
Si può inoltre contare sul supporto della libreria Apache Commons DbUtils e BeanUtils: esse mettono a disposizione dei metodi per eseguire molte operazioni ripetitive su JDBC, come la chiusura corretta delle Connection, popolamento dei Bean, query e parsing dei risultati. La libreria non vuol essere un ORM, ma si presenta molto leggera e mantiene la corrispondenza con le istruzioni manuali: ove possibile, si potrà scegliere di ricorrere ad essa per semplificare il codice.
Le operazioni svolte dai metodi sono di tipo: Create, Update, Delete, Read (sinonimi Get / Search / Filter / Find)

## Package resources: ##
Assieme al package beans, rappresenta il MODEL dell’architettura MVC. In questo package sono contenuti tutti i Data Model per il trasferimento di dati. Essi solitamente incapsulano diversi bean al loro interno, o liste di bean, o semplici messaggi. Le servlet possono utilizzarli per recuperare in una sola operazione tutti i risultati delle query e trasferirli alla specifica pagina JSP, per la successiva visualizzazione.

## Package servlets: ##
Rappresenta il CONTROLLER dell’architettura MVC. Ogni classe rappresenta una Servlet e si occupa di gestire un preciso macro-concetto dell’applicazione. Ad ognuna di esse sono associate una o più pagine JSP (VIEW) per la visualizzazione dei dati richiesti.
L’organizzazione dei metodi segue la maniera più naturale: GET per ricevere uno o più dati, POST per inviare dati o operazioni alla Servlet. Le varie operazioni consentite vengono poi eventualmente smistate tra metodi privati.
In risposta alle azioni richieste dall’utente, la Servlet tipicamente si occupa di ricevere eventuali parametri, creare e chiudere correttamente un’unica connessione al database(che viene utilizzata dai metodi statici del package database), eseguire le operazioni richieste, lavorare in modalità transazionale (commit, in caso di successo, o rollback) e gestire eventuali errori.
I risultati vengono poi inviati alle pagine JSP appropriate, oppure possono essere restituiti in formato JSON, se la richiesta è asincrona.
Una tale organizzazione predispone, in maniera naturale, sia all’uso della tecnica Ajax, sia all’eventuale interfacciarsi di applicazioni per dispositivi mobile (Android, iOs..)

### Package servlets.filters: ###
Contengono i filtri per l’aggiunta di funzioni supplementari, come il supporto alla codifica UTF-8, o filtri per il controllo degli accessi (sicurezza programmatica) permettendo il redirect automatico in caso l’utente non sia autorizzato ad accedere a determinate pagine.

## Package test: ##
Contiene tutte le classi, eseguibili standalone, utilizzate per il collaudo dei package beans, resources e database. Sebbene questo package non sia in alcun modo necessario per il fuzionamento dell’applicazione, rimane comunque essenziale in fase di sviluppo e debugging dei livelli sottostanti le Servlets. L’utilizzo del singolo DriverManager al posto del DataSource per le connessioni, evita infatti di dover eseguire l’intero webserver per il testing dei metodi che operano sul database.