-- ######### COMMENTI GENERICI AL CODICE #########

-- luca:
-- valutare meglio quando usare VARCHAR e quando usare TEXT

-- ale:
-- modificare opportunamente DATE con TIMESTAMP WITH TIMEZONE

-- ######### INIZIO CODICE #########

-- Domains

CREATE DOMAIN SEMESTRE AS SMALLINT
	CHECK(VALUE = 1 OR VALUE = 2 OR VALUE IS NULL);

CREATE DOMAIN ANNOACCADEMICO AS SMALLINT
	NOT NULL
	CHECK(VALUE >= 1 AND VALUE <= 6);

CREATE DOMAIN VALUTAZIONE AS SMALLINT
	NOT NULL
	CHECK(VALUE >= 1 AND VALUE <= 5);

CREATE DOMAIN EMAIL AS TEXT
	NOT NULL
    CHECK(
        VALUE ~* '^([A-Za-z0-9._-]+)@([A-Za-z0-9._-]+)[.]([a-z]{2,4})$'
    );
    
-- luca: creato dominio per il nome utente, così si può fare agilmente un controllo di validità
CREATE DOMAIN USERNAME AS TEXT
	NOT NULL
	CHECK(
	    LENGTH(VALUE) > 3 AND
   		LENGTH(VALUE) < 50 AND
    	VALUE ~* '^[A-Za-z][A-Za-z0-9._-]+$'
	);
	
-- Enums

CREATE TYPE TIPOLAUREA AS ENUM ('UNDERGRADUATE', 'GRADUATE', 'UNIQUE');

CREATE TYPE STATO AS ENUM ('DISABLED', 'VERIFIED', 'NOT VERIFIED', 'REPORTED');

-- Tables 

CREATE TABLE Area
(
Nome VARCHAR(60),
PRIMARY KEY (Nome)
);

CREATE TABLE Citta
(
Nome VARCHAR(30),
Stato VARCHAR(30),
PRIMARY KEY (Nome,Stato)
);

CREATE TABLE Estensione
(
IdArgomentoTesi INTEGER,
Area VARCHAR(60),
PRIMARY KEY (IdArgomentoTesi,Area)
);

CREATE TABLE Documentazione
(
IdFlusso VARCHAR(20),
NomeCertificato CHAR(3), 
LivelloCertificato CHAR(2),
PRIMARY KEY (IdFlusso,NomeCertificato,LivelloCertificato)
);

CREATE TABLE Lingua
(
Sigla CHAR(3),
Nome VARCHAR(40) NOT NULL,
PRIMARY KEY (Sigla)
);

CREATE TABLE LinguaTesi
(
SiglaLingua CHAR(3),
IdArgomentoTesi INTEGER,
PRIMARY KEY (SiglaLingua, IdArgomentoTesi)
);

CREATE TABLE LinguaCitta
(
SiglaLingua CHAR(3),
NomeCitta VARCHAR(30),
StatoCitta VARCHAR(30),
PRIMARY KEY (SiglaLingua,NomeCitta,StatoCitta)
);

CREATE TABLE CertificatiLinguistici
(
NomeLingua CHAR(3),  
Livello CHAR(2) NOT NULL,
PRIMARY KEY (NomeLingua,Livello)
);

CREATE TABLE Origine
(
IdFlusso VARCHAR(20),
IdCorso INTEGER,
PRIMARY KEY (IdFlusso,IdCorso)
);

CREATE TABLE Professore
(
Id SERIAL,
Nome VARCHAR(20) NOT NULL,
Cognome VARCHAR(40) NOT NULL,
PRIMARY KEY (Id)
);

CREATE TABLE Gestione
(
IdArgomentoTesi INTEGER,
IdProfessore INTEGER,
PRIMARY KEY (IdArgomentoTesi,IdProfessore)
);

CREATE TABLE Riconoscimento
(
IdInsegnamento INTEGER,
IdFlusso VARCHAR(20),
PRIMARY KEY (IdInsegnamento,IdFlusso)
);

CREATE TABLE Flusso
(
Id VARCHAR(20),
Destinazione VARCHAR(80) NOT NULL,
RespFlusso USERNAME NOT NULL,
PostiDisponibili SMALLINT NOT NULL,
Attivo BOOLEAN NOT NULL DEFAULT TRUE,
DataUltimaModifica DATE,
Durata SMALLINT NOT NULL CHECK (Durata > 0),
Dettagli TEXT DEFAULT NULL,
PRIMARY KEY (Id)
);

CREATE TABLE Specializzazione
(
NomeArea VARCHAR(60),
IdCorso INTEGER,
PRIMARY KEY (NomeArea,IdCorso)
);

CREATE TABLE Studente
(
NomeUtente USERNAME,
Email EMAIL,
DataRegistrazione DATE DEFAULT CURRENT_DATE,
Password VARCHAR(128) NOT NULL,
Salt VARCHAR(128) NOT NULL,
UltimoAccesso DATE DEFAULT NULL,
Attivo BOOLEAN NOT NULL DEFAULT TRUE,
PRIMARY KEY (NomeUtente),
UNIQUE (Email)              
);

CREATE TABLE Iscrizione
(
IdCorso INTEGER,
NomeUtenteStudente USERNAME,
AnnoInizio  DATE    NOT NULL,       -- TODO: DATE o INTERVAL YEAR?
AnnoFine    DATE    NOT NULL,       -- TODO: DATE o INTERVAL YEAR?
PRIMARY KEY (IdCorso,NomeUtenteStudente)
);

CREATE TABLE Interesse
(
NomeUtenteStudente USERNAME,
IdFlusso VARCHAR(20),
PRIMARY KEY (NomeUtenteStudente,IdFlusso)
);

CREATE TABLE Partecipazione
(
NomeUtenteStudente USERNAME,
IdFlusso VARCHAR(20),
Inizio DATE NOT NULL,
Fine DATE NOT NULL,
CHECK (Fine > Inizio),
PRIMARY KEY (NomeUtenteStudente,IdFlusso)
);

CREATE TABLE Svolgimento
(
IdInsegnamento INTEGER,
IdProfessore INTEGER,
PRIMARY KEY (IdInsegnamento,IdProfessore)
);

CREATE TABLE Universita
(
Nome VARCHAR(80),
Link TEXT DEFAULT NULL, 
PosizioneClassifica SMALLINT DEFAULT NULL CHECK (PosizioneClassifica > 0),
PresenzaAlloggi BOOLEAN DEFAULT FALSE,
nomeCitta VARCHAR(30),
statoCitta VARCHAR(30),
PRIMARY KEY (Nome)
);

CREATE TABLE ResponsabileFlusso
(
NomeUtente USERNAME,
Nome VARCHAR(30) NOT NULL,
Cognome VARCHAR(40) NOT NULL,
Email EMAIL,
DataRegistrazione DATE NOT NULL DEFAULT CURRENT_DATE,
Password VARCHAR(128) NOT NULL,
Salt VARCHAR(128) NOT NULL,
UltimoAccesso DATE DEFAULT NULL,
Attivo BOOLEAN NOT NULL DEFAULT FALSE,
NomeUniversita VARCHAR(80) NOT NULL,
PRIMARY KEY (NomeUtente),
UNIQUE(Email)
);

CREATE TABLE Insegnamento
(
Id SERIAL,
Nome VARCHAR(40) NOT NULL,
Crediti SMALLINT NOT NULL CHECK (Crediti > 0),
NomeUniversita VARCHAR(80) NOT NULL,
PeriodoErogazione SEMESTRE NOT NULL,
Stato STATO NOT NULL,
AnnoCorso ANNOACCADEMICO NOT NULL,
NomeArea VARCHAR(60) NOT NULL,
NomeLingua CHAR(3),
PRIMARY KEY (Id),
UNIQUE (Nome, Crediti, NomeUniversita)  -- luca: aggiunto
);

CREATE TABLE ArgomentoTesi
(
Id SERIAL,
Nome VARCHAR(40) NOT NULL,
NomeUniversita VARCHAR(80),
Triennale BOOLEAN NOT NULL,
Magistrale BOOLEAN NOT NULL,
Stato STATO NOT NULL,
PRIMARY KEY (Id),
UNIQUE (Nome, NomeUniversita) -- luca: aggiunto
);

CREATE TABLE CorsoDiLaurea
(
Id SERIAL,
Nome VARCHAR(40) NOT NULL,
Livello TIPOLAUREA,
NomeUniversita VARCHAR(80),
PRIMARY KEY (Id),
UNIQUE (Nome, Livello, NomeUniversita)  -- luca: aggiunto
);

CREATE TABLE Coordinatore
(
NomeUtente USERNAME,
Email EMAIL,
DataRegistrazione DATE DEFAULT CURRENT_DATE,
Password VARCHAR(128) NOT NULL,
Salt VARCHAR(128) NOT NULL,
UltimoAccesso DATE DEFAULT NULL,
Attivo BOOLEAN NOT NULL,
NomeUniversita VARCHAR(80),
PRIMARY KEY (NomeUtente),
UNIQUE(Email)
);

CREATE TABLE ValutazioneCitta
(
NomeUtenteStudente USERNAME,
NomeCitta VARCHAR(30),
StatoCitta VARCHAR(30),
CostoDellaVita VALUTAZIONE,
DisponibilitaAlloggi VALUTAZIONE,
VivibilitaUrbana VALUTAZIONE,
VitaSociale VALUTAZIONE,
DataInserimento DATE DEFAULT CURRENT_DATE,
Commento TEXT DEFAULT NULL,
PRIMARY KEY (NomeUtenteStudente,NomeCitta,StatoCitta)
);

CREATE TABLE ValutazioneFlusso
(
NomeUtenteStudente USERNAME,
IdFlusso VARCHAR(20),
SoddEsperienza VALUTAZIONE,
SoddAccademica VALUTAZIONE,
Didattica VALUTAZIONE,
ValutazioneResponsabile VALUTAZIONE,
DataInserimento DATE DEFAULT CURRENT_DATE,
Commento TEXT DEFAULT NULL,
PRIMARY KEY (NomeUtenteStudente,IdFlusso)
);

CREATE TABLE ValutazioneInsegnamento
(
NomeUtenteStudente USERNAME,
IdInsegnamento INTEGER,
QtaInsegnamanto VALUTAZIONE,
Interesse VALUTAZIONE,
Difficolta VALUTAZIONE,
RispettoDelleOre VALUTAZIONE,
DataInserimento DATE DEFAULT CURRENT_DATE,
Commento TEXT DEFAULT NULL,
PRIMARY KEY (NomeUtenteStudente,IdInsegnamento)
);

CREATE TABLE ValutazioneTesi
(
NomeUtenteStudente USERNAME,
IdArgomentoTesi INTEGER,
ImpegnoNecessario VALUTAZIONE,
InteresseArgomento VALUTAZIONE,
DisponibilitaRelatore VALUTAZIONE,
Soddisfazione VALUTAZIONE,
DataInserimento DATE DEFAULT CURRENT_DATE,
Commento TEXT DEFAULT NULL,
PRIMARY KEY (NomeUtenteStudente,IdArgomentoTesi)
);

CREATE TABLE ValutazioneUniversita
(
NomeUtenteStudente USERNAME,
NomeUniversita VARCHAR(80),
CollocazioneUrbana VALUTAZIONE,
IniziativeErasmus VALUTAZIONE,
QtaInsegnamenti VALUTAZIONE,
QtaAule VALUTAZIONE,
DataInserimento DATE DEFAULT CURRENT_DATE,
Commento TEXT DEFAULT NULL,
PRIMARY KEY (NomeUtenteStudente,NomeUniversita)
);

-- luca: note su ON DELETE e ON UPDATE
-- notare che alcune tabelle (Area, Lingua, Certificati Linguistici) sono a dominio fisso
-- sono cioè create dal SysAdmin e non possono essere modificate durante le operazioni normali
-- per le relazioni che si riferiscono a loro direi che va bene ON UPDATE CASCADE
-- rendendo l'eliminazione di quanto già inserito problematico
--
-- l'eliminazione di alcuni dei concetti centrali inoltre (città, università e flusso)
-- dovrebbe essere allo stesso modo impossibile se non al SysAdmin
--
-- per quanto riguarda gli utenti se eliminano l'account essi non sono eliminati dal database
-- ma si pone la flag attivo su false. l'eliminazione dal db avviene solo ad opera dal SysAdmin
-- e sono operazioni straordinarie fatte per ripulire il database. quindi mettere
-- ON DELETE CASCADE ON UPDATE CASCADE direi che va bene
-- in alternativa ale proponeva un sistema più articolato:
--      Si puo' invece impedire di eliminare uno studente se e' marcato come attivo tramite una trigger procedure:
-- 		in caso di eliminazione, impedisce l'azione e piuttosto trasforma lo studente da ATTIVO ad INATTIVO.
--		Piuttosto consente l'effettiva eliminazione solo se e' inattivo 
-- 		(e quindi deve poter eliminare in cascata le rispettive valutazioni o iscrizioni.. ecc.., si pensi ad es.
-- 		all'eliminazione di account fake/spam)
--
-- notare che Idflusso, nonostante il nome, non è un ID Numerico ma l'ID nel sistema erasmus
-- quindi ho messo ON UPDATE CASCADE in quanto il sistema con cui gli ID sono generati potrebbe cambiare
-- negli ID di tipo SERIAL invece l'ON UPDATE non serve in quanto questo non può cambiare
-- lo stesso trattamento è stato applicato a tutte le tabelle che hanno identificatore non SERIAL
-- (eg: Università, Studente, Città)


ALTER TABLE Estensione ADD FOREIGN KEY (IdArgomentoTesi) REFERENCES ArgomentoTesi (Id) ON DELETE CASCADE;

ALTER TABLE Estensione ADD FOREIGN KEY (Area) REFERENCES Area (Nome) ON UPDATE CASCADE;

ALTER TABLE Documentazione ADD FOREIGN KEY (IdFlusso) REFERENCES Flusso (Id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Documentazione ADD FOREIGN KEY (NomeCertificato,LivelloCertificato) REFERENCES CertificatiLinguistici (NomeLingua,Livello)
																	ON UPDATE CASCADE;

ALTER TABLE LinguaTesi ADD FOREIGN KEY (SiglaLingua) REFERENCES Lingua (Sigla) ON UPDATE CASCADE;

ALTER TABLE LinguaTesi ADD FOREIGN KEY (IdArgomentoTesi) REFERENCES ArgomentoTesi (Id) ON DELETE CASCADE;

ALTER TABLE LinguaCitta ADD FOREIGN KEY (SiglaLingua) REFERENCES Lingua (Sigla) ON UPDATE CASCADE;

ALTER TABLE LinguaCitta ADD FOREIGN KEY (NomeCitta,StatoCitta) REFERENCES Citta (Nome,Stato) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Origine ADD FOREIGN KEY (IdFlusso) REFERENCES Flusso (Id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Origine ADD FOREIGN KEY (IdCorso) REFERENCES CorsoDiLaurea (Id) ON DELETE CASCADE;

ALTER TABLE Gestione ADD FOREIGN KEY (IdArgomentoTesi) REFERENCES ArgomentoTesi (Id) ON DELETE CASCADE;

ALTER TABLE Gestione ADD FOREIGN KEY (IdProfessore) REFERENCES Professore (Id) ON DELETE CASCADE;

ALTER TABLE Riconoscimento ADD FOREIGN KEY (IdInsegnamento) REFERENCES Insegnamento (Id) ON DELETE CASCADE;

ALTER TABLE Riconoscimento ADD FOREIGN KEY (IdFlusso) REFERENCES Flusso (Id) ON DELETE CASCADE ON UPDATE CASCADE;

     -- ale: inserire un trigger, ad es. se il responsabile ha almeno un flusso a lui assegnato, non viene eliminato ma
     --      viene semplicemente disabilitato l'account e tutti i flussi a lui associati, per evitare eliminazioni a catena
     --      Quindi per eliminarlo definitivamente e' necessario aver trovato dei responsabili sostituti ai flussi,
     --      oppure averli cancellati singolarmente (NO ACTION)
     -- luca: per me non serve un trigger apposta, come detto sopra l'eliminazione di un account dal DB viene fatta
     --		  apposta per ripulire il database 
ALTER TABLE Flusso ADD FOREIGN KEY (RespFlusso) REFERENCES ResponsabileFlusso (NomeUtente) ON DELETE CASCADE ON UPDATE CASCADE;     
                                                                                           
	-- ale: anche qui impedirei di eliminare cosi' facilmente tutti i flussi
	-- luca: non capisco
ALTER TABLE Flusso ADD FOREIGN KEY (Destinazione) REFERENCES Universita (Nome) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Specializzazione ADD FOREIGN KEY (NomeArea) REFERENCES Area (Nome) ON UPDATE CASCADE;

ALTER TABLE Specializzazione ADD FOREIGN KEY (IdCorso) REFERENCES CorsoDiLaurea (Id) ON DELETE CASCADE;

ALTER TABLE Iscrizione ADD FOREIGN KEY (IdCorso) REFERENCES CorsoDiLaurea (Id) ON DELETE CASCADE;

ALTER TABLE Iscrizione ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente) ON DELETE CASCADE ON UPDATE CASCADE; 

ALTER TABLE Interesse ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Interesse ADD FOREIGN KEY (IdFlusso) REFERENCES Flusso (Id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Partecipazione ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Partecipazione ADD FOREIGN KEY (IdFlusso) REFERENCES Flusso (Id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Svolgimento ADD FOREIGN KEY (IdInsegnamento) REFERENCES Insegnamento (Id) ON DELETE CASCADE;

ALTER TABLE Svolgimento ADD FOREIGN KEY (IdProfessore) REFERENCES Professore (Id) ON DELETE CASCADE;

-- luca: eliminando una città si genera un errore
ALTER TABLE Universita ADD FOREIGN KEY (nomeCitta,statoCitta) REFERENCES Citta (Nome,Stato) ON UPDATE CASCADE;

ALTER TABLE ResponsabileFlusso ADD FOREIGN KEY (NomeUniversita) REFERENCES Universita (Nome) ON UPDATE CASCADE;

-- Ale: per l'ON DELETE si potrebbe tenere il RESTRICT di default, così per eliminare l'università
-- bisogna prima togliere tutti gli insegnamenti 
-- luca: io credo che un CASCADE sarebbe meglio
ALTER TABLE Insegnamento ADD FOREIGN KEY (NomeUniversita) REFERENCES Universita (Nome) ON UPDATE CASCADE;

ALTER TABLE Insegnamento ADD FOREIGN KEY (NomeArea) REFERENCES Area (Nome) ON UPDATE CASCADE;

ALTER TABLE Insegnamento ADD FOREIGN KEY (NomeLingua) REFERENCES Lingua (Sigla) ON UPDATE CASCADE;

ALTER TABLE ArgomentoTesi ADD FOREIGN KEY (NomeUniversita) REFERENCES Universita (Nome) ON UPDATE CASCADE;

ALTER TABLE CorsoDiLaurea ADD FOREIGN KEY (NomeUniversita) REFERENCES Universita (Nome) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE Coordinatore ADD FOREIGN KEY (NomeUniversita) REFERENCES Universita (Nome) ON UPDATE CASCADE;

ALTER TABLE ValutazioneCitta ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ValutazioneCitta ADD FOREIGN KEY (NomeCitta,StatoCitta) REFERENCES Citta (Nome,Stato) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ValutazioneFlusso ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ValutazioneFlusso ADD FOREIGN KEY (IdFlusso) REFERENCES Flusso (Id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ValutazioneInsegnamento ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ValutazioneInsegnamento ADD FOREIGN KEY (IdInsegnamento) REFERENCES Insegnamento (Id) ON DELETE CASCADE;

ALTER TABLE ValutazioneTesi ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ValutazioneTesi ADD FOREIGN KEY (IdArgomentoTesi) REFERENCES ArgomentoTesi (Id) ON DELETE CASCADE;

ALTER TABLE ValutazioneUniversita ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente) ON UPDATE CASCADE ON DELETE CASCADE;
 
ALTER TABLE ValutazioneUniversita ADD FOREIGN KEY (NomeUniversita) REFERENCES Universita (Nome) ON UPDATE CASCADE;


