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
    CHECK(
        VALUE ~* '^([A-Za-z0-9._-]+)@([A-Za-z0-9._-]+)[.]([a-z]{2,4})$'
    );
	
-- Enums

CREATE TYPE TIPOLAUREA AS ENUM ('triennale', 'magistrale', 'ciclounico');

CREATE TYPE STATO AS ENUM ('DISABLED', 'VERIFIED', 'NOT VERIFIED', 'SIGNALLED');

-- Tables 
CREATE TABLE Area
(
Nome VARCHAR(40),
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
Area VARCHAR(40),
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

CREATE TABLE LinguaTesi -- ale: corretto errore grammaticale LiguaTesi -> LinguaTesi 
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
RespFlusso VARCHAR(50) NOT NULL,
PostiDisponibili SMALLINT NOT NULL,
Attivo BOOLEAN NOT NULL DEFAULT TRUE,
DataUltimaModifica DATE,
Durata SMALLINT NOT NULL CHECK (Durata > 0),
PRIMARY KEY (Id)
);

CREATE TABLE Specializzazione
(
NomeArea VARCHAR(40),
IdCorso INTEGER,
PRIMARY KEY (NomeArea,IdCorso)
);

CREATE TABLE Studente
(
NomeUtente VARCHAR(50),
Email EMAIL NOT NULL,
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
NomeUtenteStudente VARCHAR(50),
AnnoInizio  DATE    NOT NULL,       -- TODO: DATE o INTERVAL YEAR?
AnnoFine    DATE    NOT NULL,       -- TODO: DATE o INTERVAL YEAR?
PRIMARY KEY (IdCorso,NomeUtenteStudente)
); -- Ale: mancano attributi!! Anno inizio.. ecc..

CREATE TABLE Interesse
(
NomeUtenteStudente VARCHAR(50),
IdFlusso VARCHAR(20),
PRIMARY KEY (NomeUtenteStudente,IdFlusso)
);

CREATE TABLE Partecipazione
(
NomeUtenteStudente VARCHAR(50),
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
NomeUtente VARCHAR(50),
Nome VARCHAR(30) NOT NULL,
Cognome VARCHAR(40) NOT NULL,
Email VARCHAR(50) NOT NULL,
DataRegistrazione DATE NOT NULL DEFAULT CURRENT_DATE,
Password VARCHAR(128) NOT NULL,
Salt VARCHAR(128) NOT NULL,
UltimoAccesso DATE DEFAULT NULL,
Attivo BOOLEAN NOT NULL DEFAULT FALSE,
NomeUniversita VARCHAR(80) NOT NULL,
PRIMARY KEY (NomeUtente)
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
NomeArea VARCHAR(40) NOT NULL,
NomeLingua CHAR(3),
PRIMARY KEY (Id)
);

CREATE TABLE ArgomentoTesi
(
Id SERIAL,
Nome VARCHAR(40) NOT NULL,
NomeUniversita VARCHAR(80),
Triennale BOOLEAN NOT NULL,
Magistrale BOOLEAN NOT NULL,
Stato STATO NOT NULL,
PRIMARY KEY (Id)
);

CREATE TABLE CorsoDiLaurea
(
Id SERIAL,
Nome VARCHAR(40) NOT NULL,
Livello TIPOLAUREA,
nomeUniversita VARCHAR(80),
PRIMARY KEY (Id)
);

CREATE TABLE Coordinatore
(
NomeUtente VARCHAR(50),
Email VARCHAR(50) NOT NULL,
DataRegistrazione DATE DEFAULT CURRENT_DATE,
Password VARCHAR(128) NOT NULL,
Salt VARCHAR(128) NOT NULL,
UltimoAccesso DATE DEFAULT NULL,
Attivo BOOLEAN NOT NULL,
NomeUniversita VARCHAR(80),
PRIMARY KEY (NomeUtente)
);

CREATE TABLE ValutazioneCitta
(
NomeUtenteStudente VARCHAR(50),
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
NomeUtenteStudente VARCHAR(50),
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
NomeUtenteStudente VARCHAR(50),
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
NomeUtenteStudente VARCHAR(50),
IdArgomentoTesi INTEGER,
ImpegnoNecessario VALUTAZIONE,
InteresseArgomento VALUTAZIONE,
DiponibilitaRelatore VALUTAZIONE,
Soddisfazione VALUTAZIONE,
DataInserimento DATE DEFAULT CURRENT_DATE,
Commento TEXT DEFAULT NULL,
PRIMARY KEY (NomeUtenteStudente,IdArgomentoTesi)
);

CREATE TABLE ValutazioneUniversita
(
NomeUtenteStudente VARCHAR(50),
NomeUniversita VARCHAR(80),
CollocazioneUrbana VALUTAZIONE,
IniziativeErasmus VALUTAZIONE,
QtaInsegnamenti VALUTAZIONE,
QtaAule VALUTAZIONE,
DataInserimento DATE DEFAULT CURRENT_DATE,
Commento TEXT DEFAULT NULL,
PRIMARY KEY (NomeUtenteStudente,NomeUniversita)
);





ALTER TABLE Estensione ADD FOREIGN KEY (IdArgomentoTesi) REFERENCES ArgomentoTesi (Id) ON DELETE CASCADE;

ALTER TABLE Estensione ADD  FOREIGN KEY (Area) REFERENCES Area (Nome) ON DELETE CASCADE
                                                                      ON UPDATE CASCADE;

ALTER TABLE Documentazione ADD  FOREIGN KEY (IdFlusso) REFERENCES Flusso (Id) ON DELETE CASCADE;

ALTER TABLE Documentazione ADD  FOREIGN KEY (NomeCertificato,LivelloCertificato) REFERENCES CertificatiLinguistici (NomeLingua,Livello);

ALTER TABLE LinguaTesi ADD  FOREIGN KEY (SiglaLingua) REFERENCES Lingua (Sigla);

ALTER TABLE LinguaTesi ADD  FOREIGN KEY (IdArgomentoTesi) REFERENCES ArgomentoTesi (Id) ON DELETE CASCADE;

ALTER TABLE LinguaCitta ADD  FOREIGN KEY (SiglaLingua) REFERENCES Lingua (Sigla);

ALTER TABLE LinguaCitta ADD  FOREIGN KEY (NomeCitta,StatoCitta) REFERENCES Citta (Nome,Stato);

ALTER TABLE Origine ADD FOREIGN KEY (IdFlusso) REFERENCES Flusso (Id) ON DELETE CASCADE;

ALTER TABLE Origine ADD FOREIGN KEY (IdCorso) REFERENCES CorsoDiLaurea (Id) ON DELETE CASCADE;

ALTER TABLE Gestione ADD FOREIGN KEY (IdArgomentoTesi) REFERENCES ArgomentoTesi (Id) ON DELETE CASCADE;

ALTER TABLE Gestione ADD FOREIGN KEY (IdProfessore) REFERENCES Professore (Id) ON DELETE CASCADE;

ALTER TABLE Riconoscimento ADD FOREIGN KEY (IdInsegnamento) REFERENCES Insegnamento (Id) ON DELETE CASCADE;

ALTER TABLE Riconoscimento ADD FOREIGN KEY (IdFlusso) REFERENCES Flusso (Id) ON DELETE CASCADE;

ALTER TABLE Flusso ADD FOREIGN KEY (RespFlusso) REFERENCES ResponsabileFlusso (NomeUtente) ON DELETE CASCADE
                                                                                           ON UPDATE CASCADE; 
     -- ale: inserire un trigger, ad es. se il responsabile ha almeno un flusso a lui assegnato, non viene eliminato ma
     --      viene semplicemente disabilitato l'account e tutti i flussi a lui associati, per evitare eliminazioni a catena
     --      Quindi per eliminarlo definitivamente e' necessario aver trovato dei responsabili sostituti ai flussi,
     --      oppure averli cancellati singolarmente (NO ACTION)
     
                                                                                           
ALTER TABLE Flusso ADD FOREIGN KEY (Destinazione) REFERENCES Universita (Nome) ON DELETE CASCADE;
	-- ale: anche qui impedirei di eliminare cosi' facilmente tutti i flussi, al massimo permetterei un ON UPDATE CASCADE,
	--      nel caso l'universita' cambiasse leggermente il nome o la dicitura iniziale

ALTER TABLE Specializzazione ADD FOREIGN KEY (NomeArea) REFERENCES Area (Nome);

ALTER TABLE Specializzazione ADD FOREIGN KEY (IdCorso) REFERENCES CorsoDiLaurea (Id) ON DELETE CASCADE;

ALTER TABLE Iscrizione ADD FOREIGN KEY (IdCorso) REFERENCES CorsoDiLaurea (Id) ON DELETE CASCADE;

ALTER TABLE Iscrizione ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente); 
	-- ale: mettere ON DELETE CASCADE, dato che voglio poter eliminare lo studente anche se e' iscritto a dei corsi

ALTER TABLE Interesse ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente);
	-- ale: qui metterei ON DELETE CASCADE, perché vogliamo poter eliminare lo studente anche se ha inserito degli interessi
	--      Si puo' invece impedire di eliminare uno studente se e' marcato come attivo tramite una trigger procedure:
	-- 		in caso di eliminazione, impedisce l'azione e piuttosto trasforma lo studente da ATTIVO ad INATTIVO.
	--		Piuttosto consente l'effettiva eliminazione solo se e' inattivo 
	-- 		(e quindi deve poter eliminare in cascata le rispettive valutazioni o iscrizioni.. ecc.., si pensi ad es.
	-- 		all'eliminazione di account fake/spam)

ALTER TABLE Interesse ADD FOREIGN KEY (IdFlusso) REFERENCES Flusso (Id) ON DELETE CASCADE;

ALTER TABLE Partecipazione ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente);
	-- 		Ale: Idem di quanto scritto sopra per Interesse

ALTER TABLE Partecipazione ADD FOREIGN KEY (IdFlusso) REFERENCES Flusso (Id) ON DELETE CASCADE;

ALTER TABLE Svolgimento ADD FOREIGN KEY (IdInsegnamento) REFERENCES Insegnamento (Id) ON DELETE CASCADE;

ALTER TABLE Svolgimento ADD FOREIGN KEY (IdProfessore) REFERENCES Professore (Id) ON DELETE CASCADE;

ALTER TABLE Universita ADD FOREIGN KEY (nomeCitta,statoCitta) REFERENCES Citta (Nome,Stato);

ALTER TABLE ResponsabileFlusso ADD FOREIGN KEY (NomeUniversita) REFERENCES Universita (Nome);
	--	Ale: ci vuole sicuramente ON UPDATE CASCADE nel caso l'universita' cambi leggermente nome

ALTER TABLE Insegnamento ADD FOREIGN KEY (NomeUniversita) REFERENCES Universita (Nome);
	--	Ale: ci vuole sicuramente ON UPDATE CASCADE nel caso l'universita' cambi leggermente nome
	--  per l'ON DELETE si potrebbe tenere il RESTRICT di default, così per eliminare l'università
	--	bisogna prima togliere tutti gli insegnamenti 

ALTER TABLE Insegnamento ADD FOREIGN KEY (NomeArea) REFERENCES Area (Nome);

ALTER TABLE Insegnamento ADD FOREIGN KEY (NomeLingua) REFERENCES Lingua (Sigla);

ALTER TABLE ArgomentoTesi ADD FOREIGN KEY (NomeUniversita) REFERENCES Universita (Nome);
	--	Ale: ci vuole sicuramente ON UPDATE CASCADE nel caso l'universita' cambi leggermente nome

ALTER TABLE CorsoDiLaurea ADD FOREIGN KEY (nomeUniversita) REFERENCES Universita (Nome);
	--	Ale: ci vuole sicuramente ON UPDATE CASCADE nel caso l'universita' cambi leggermente nome

ALTER TABLE Coordinatore ADD FOREIGN KEY (NomeUniversita) REFERENCES Universita (Nome);
	--	Ale: ci vuole sicuramente ON UPDATE CASCADE nel caso l'universita' cambi leggermente nome

-- Ale: per tutte le valutazioni dovremmo mettere l'ON DELETE CASCADE, altrimenti per cancellare 
--		anche un semplice insegnamento bisogna eliminare tutte le singole valutazioni associate 

ALTER TABLE ValutazioneCitta ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente);

ALTER TABLE ValutazioneCitta ADD FOREIGN KEY (NomeCitta,StatoCitta) REFERENCES Citta (Nome,Stato);

ALTER TABLE ValutazioneFlusso ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente);

ALTER TABLE ValutazioneFlusso ADD FOREIGN KEY (IdFlusso) REFERENCES Flusso (Id);

ALTER TABLE ValutazioneInsegnamento ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente);

ALTER TABLE ValutazioneInsegnamento ADD FOREIGN KEY (IdInsegnamento) REFERENCES Insegnamento (Id);

ALTER TABLE ValutazioneTesi ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente);

ALTER TABLE ValutazioneTesi ADD FOREIGN KEY (IdArgomentoTesi) REFERENCES ArgomentoTesi (Id) ON DELETE CASCADE;

ALTER TABLE ValutazioneUniversita ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente);

ALTER TABLE ValutazioneUniversita ADD FOREIGN KEY (NomeUniversita) REFERENCES Universita (Nome);
	--	Ale: ci vuole sicuramente ON UPDATE CASCADE nel caso l'universita' cambi leggermente nome

