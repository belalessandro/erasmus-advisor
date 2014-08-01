-- ######### COMMENTI GENERICI AL CODICE #########

-- luca:
-- abbiamo usato serial come tipo di dato per i contatori. potrebe essere meglio, nel caso
-- bisognasse riferirsi a quei campi come chiave esterna, impostare il loro tipo di dato nelle tabelle
-- esterne ad INTEGER invece che SERIAL.
-- eg: CREATE TABLE Insegnamento (id SERIAL);
--     CREATE TABLE Valutazione (id INTEGER, FOREIGN KEY id REFERENCES Insegnamento(id));
-- pare non cambi niente farlo in un modo o nell'altro, in ogni caso è una cosa da tenere a mente

-- luca:
-- sarebbe bene definire un tipo di dato per le email, in modo da fare il controllo di validità
-- ma non capisco come si può fare

-- luca:
-- valutare meglio quando usare VARCHAR e quando usare TEXT

-- ######### INIZIO CODICE #########

-- luca: queste 4 righe a me non funzionano
\c postgres
drop database "erasmusadvisor";
create database "erasmusadvisor";
\c erasmusadvisor;

-- Domains
CREATE DOMAIN SEMESTRE AS SMALLINT
	CHECK(VALUE = 1 OR VALUE = 2 OR VALUE IS NULL);

-- luca: corretto, anno accademico non indica l'anno accademico in cui viene erogato (eg 2013/2014)
-- ma l'anno del corso di studi in cui viene erogato (eg quarto anno)
CREATE DOMAIN ANNOACCADEMICO AS SMALLINT
	NOT NULL
	CHECK(VALUE >= 1 AND VALUE <= 6);

-- luca: non può essere nulla, il voto numerico è obbligatorio
CREATE DOMAIN VALUTAZIONE AS SMALLINT
	NOT NULL
	CHECK(VALUE >= 1 AND VALUE <= 5);
	
-- Enums
-- luca: fatta come enum
CREATE TYPE TIPOLAUREA AS ENUM ('triennale', 'magistrale', 'ciclounico');

-- luca: lo stato di insegnamento e argomento tesi
CREATE TYPE STATO AS ENUM ('DISABLED', 'VERIFIED', 'NOT VERIFIED', 'SIGNALLED');


-- Tables (Created with SQLEditor)
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
IdArgomentoTesi SERIAL,
Area VARCHAR(40),
PRIMARY KEY (IdArgomentoTesi,Area)
);

CREATE TABLE Documentazione
(
IdFlusso SERIAL,
NomeCertificato CHAR(3),
LivelloCertificato CHAR(2), -- luca: il livello certificato è solo del tipo B1, C2 etc
PRIMARY KEY (IdFlusso,NomeCertificato,LivelloCertificato)
);

CREATE TABLE Lingua
(
Sigla CHAR(3),
Nome VARCHAR(40) NOT NULL,
PRIMARY KEY (Sigla)
);

CREATE TABLE LiguaTesi
(
SiglaLingua CHAR(3),
IdArgomentoTesi SERIAL,
PRIMARY KEY (SiglaLingua,IdArgomentoTesi)
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
Livello CHAR(2) NOT NULL, -- luca: il livello certificato è solo del tipo B1, C2 etc
PRIMARY KEY (NomeLingua,Livello)
);

CREATE TABLE Origine
(
IdFlusso SERIAL,
IdCorso SERIAL,
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
IdArgomentoTesi SERIAL,
IdProfessore SERIAL,
PRIMARY KEY (IdArgomentoTesi,IdProfessore)
);

CREATE TABLE Riconoscimento
(
IdInsegnamento SERIAL,
IdFlusso SERIAL,
PRIMARY KEY (IdInsegnamento,IdFlusso)
);

CREATE TABLE Flusso
(
Id SERIAL,
RespFlusso VARCHAR(50) NOT NULL,
PostiDisponibili SMALLINT NOT NULL,
Attivo BOOLEAN NOT NULL DEFAULT TRUE,
DataUltimaModifica DATE,
Durata SMALLINT NOT NULL CHECK (Durata > 0), -- luca: così dovrebbe andare
PRIMARY KEY (Id)
);

CREATE TABLE Specializzazione
(
NomeArea VARCHAR(40),
IdCorso SERIAL,
PRIMARY KEY (NomeArea,IdCorso)
);

CREATE TABLE Studente
(
NomeUtente VARCHAR(50),
Email VARCHAR(50) NOT NULL,
DataRegistrazione DATE DEFAULT CURRENT_DATE,
Password VARCHAR(80) NOT NULL,
Salt VARCHAR(80) NOT NULL,
UltimoAccesso DATE DEFAULT NULL,
Attivo BOOLEAN NOT NULL DEFAULT TRUE,
PRIMARY KEY (NomeUtente)
);

CREATE TABLE Iscrizione
(
IdCorso SERIAL,
NomeUtenteStudente VARCHAR(50),
PRIMARY KEY (IdCorso,NomeUtenteStudente)
);

CREATE TABLE Interesse
(
NomeUtenteStudente VARCHAR(50),
IdFlusso SERIAL,
PRIMARY KEY (NomeUtenteStudente,IdFlusso)
);

CREATE TABLE Partecipazione
(
NomeUtenteStudente VARCHAR(50),
IdFlusso SERIAL,
Inizio DATE NOT NULL,
Fine DATE NOT NULL,
CHECK (Fine > Inizio), -- luca: aggiunto vincolo
PRIMARY KEY (NomeUtenteStudente,IdFlusso)
);

CREATE TABLE Svolgimento
(
IdInsegnamento SERIAL,
IdProfessore SERIAL,
PRIMARY KEY (IdInsegnamento,IdProfessore)
);

CREATE TABLE Universita
(
Nome VARCHAR(80),
Link TEXT DEFAULT NULL, -- luca: messo TEXT come tipo di dato
PosizioneClassifica SMALLINT DEFAULT NULL CHECK (PosizioneClassifica > 0), -- luca: aggiunto check
PresenzaAlloggi BOOLEAN DEFAULT FALSE,
nomeCitta VARCHAR(30),
statoCitta VARCHAR(30),
PRIMARY KEY (Nome)
);

CREATE TABLE Destinazione
(
NomeUniversita VARCHAR(80),
IdFlusso SERIAL,
PRIMARY KEY (NomeUniversita,IdFlusso)
);

CREATE TABLE ResponsabileFlusso
(
NomeUtente VARCHAR(50),
Nome VARCHAR(30) NOT NULL,
Cognome VARCHAR(40) NOT NULL,
Email VARCHAR(50) NOT NULL,
DataRegistrazione DATE NOT NULL DEFAULT CURRENT_DATE,
Password VARCHAR(80) NOT NULL,
Salt VARCHAR(80) NOT NULL,
UltimoAccesso DATE DEFAULT NULL,
Attivo BOOLEAN NOT NULL DEFAULT FALSE,
NomeUniversita VARCHAR(80) NOT NULL,
PRIMARY KEY (NomeUtente)
);

CREATE TABLE Insegnamento
(
Id SERIAL,
Nome VARCHAR(40) NOT NULL,
Crediti SMALLINT NOT NULL CHECK (Crediti > 0), -- luca: aggiunto check
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
Password VARCHAR(80) NOT NULL,
Salt VARCHAR(80) NOT NULL,
UltimoAccesso DATE DEFAULT NULL,
Attivo BOOLEAN NOT NULL,
NomeUniversita VARCHAR(80),
PRIMARY KEY (NomeUtente)
);

CREATE TABLE ValCitta
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

CREATE TABLE ValFlusso
(
NomeUtenteStudente VARCHAR(50),
IdFlusso SERIAL,
SoddEsperienza VALUTAZIONE,
SoddAccademica VALUTAZIONE,
Didattica VALUTAZIONE,
ValResponsabile VALUTAZIONE,
DataInserimento DATE DEFAULT CURRENT_DATE,
Commento TEXT DEFAULT NULL,
PRIMARY KEY (NomeUtenteStudente,IdFlusso)
);

CREATE TABLE ValInsegnamento
(
NomeUtenteStudente VARCHAR(50),
IdInsegnamento SERIAL,
QtaInsegnamanto VALUTAZIONE,
Interesse VALUTAZIONE,
Difficolta VALUTAZIONE,
RispettoDelleOre VALUTAZIONE,
DataInserimento DATE DEFAULT CURRENT_DATE,
Commento TEXT DEFAULT NULL,
PRIMARY KEY (NomeUtenteStudente,IdInsegnamento)
);

CREATE TABLE ValTesi
(
NomeUtenteStudente VARCHAR(50),
IdArgomentoTesi SERIAL,
ImpegnoNecessario VALUTAZIONE,
InteresseArgomento VALUTAZIONE,
DiponibilitaRelatore VALUTAZIONE,
Soddisfazione VALUTAZIONE,
DataInserimento DATE DEFAULT CURRENT_DATE,
Commento TEXT DEFAULT NULL,
PRIMARY KEY (NomeUtenteStudente,IdArgomentoTesi)
);

CREATE TABLE ValUniversita
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

ALTER TABLE Estensione ADD FOREIGN KEY (IdArgomentoTesi) REFERENCES ArgomentoTesi (Id);

ALTER TABLE Estensione ADD FOREIGN KEY (Area) REFERENCES Area (Nome);

ALTER TABLE Documentazione ADD FOREIGN KEY (IdFlusso) REFERENCES Flusso (Id);

ALTER TABLE Documentazione ADD FOREIGN KEY (NomeCertificato,LivelloCertificato) REFERENCES CertificatiLinguistici (NomeLingua,Livello);

ALTER TABLE LiguaTesi ADD FOREIGN KEY (SiglaLingua) REFERENCES Lingua (Sigla);

ALTER TABLE LiguaTesi ADD FOREIGN KEY (IdArgomentoTesi) REFERENCES ArgomentoTesi (Id);

ALTER TABLE LinguaCitta ADD FOREIGN KEY (SiglaLingua) REFERENCES Lingua (Sigla);

ALTER TABLE LinguaCitta ADD FOREIGN KEY (NomeCitta,StatoCitta) REFERENCES Citta (Nome,Stato);

ALTER TABLE CertificatiLinguistici ADD FOREIGN KEY (NomeLingua) REFERENCES Lingua (Sigla);

ALTER TABLE Origine ADD FOREIGN KEY (IdFlusso) REFERENCES Flusso (Id);

ALTER TABLE Origine ADD FOREIGN KEY (IdCorso) REFERENCES CorsoDiLaurea (Id);

ALTER TABLE Gestione ADD FOREIGN KEY (IdArgomentoTesi) REFERENCES ArgomentoTesi (Id);

ALTER TABLE Gestione ADD FOREIGN KEY (IdProfessore) REFERENCES Professore (Id);

ALTER TABLE Riconoscimento ADD FOREIGN KEY (IdInsegnamento) REFERENCES Insegnamento (Id);

ALTER TABLE Riconoscimento ADD FOREIGN KEY (IdFlusso) REFERENCES Flusso (Id);

ALTER TABLE Flusso ADD FOREIGN KEY (RespFlusso) REFERENCES ResponsabileFlusso (NomeUtente);

ALTER TABLE Specializzazione ADD FOREIGN KEY (NomeArea) REFERENCES Area (Nome);

ALTER TABLE Specializzazione ADD FOREIGN KEY (IdCorso) REFERENCES CorsoDiLaurea (Id);

ALTER TABLE Iscrizione ADD FOREIGN KEY (IdCorso) REFERENCES CorsoDiLaurea (Id);

ALTER TABLE Iscrizione ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente);

ALTER TABLE Interesse ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente);

ALTER TABLE Interesse ADD FOREIGN KEY (IdFlusso) REFERENCES Flusso (Id);

ALTER TABLE Partecipazione ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente);

ALTER TABLE Partecipazione ADD FOREIGN KEY (IdFlusso) REFERENCES Flusso (Id);

ALTER TABLE Svolgimento ADD FOREIGN KEY (IdInsegnamento) REFERENCES Insegnamento (Id);

ALTER TABLE Svolgimento ADD FOREIGN KEY (IdProfessore) REFERENCES Professore (Id);

ALTER TABLE Universita ADD FOREIGN KEY (nomeCitta,statoCitta) REFERENCES Citta (Nome,Stato);

ALTER TABLE Destinazione ADD FOREIGN KEY (NomeUniversita) REFERENCES Universita (Nome);

ALTER TABLE Destinazione ADD FOREIGN KEY (IdFlusso) REFERENCES Flusso (Id);

ALTER TABLE ResponsabileFlusso ADD FOREIGN KEY (NomeUniversita) REFERENCES Universita (Nome);

ALTER TABLE Insegnamento ADD FOREIGN KEY (NomeUniversita) REFERENCES Universita (Nome);

ALTER TABLE Insegnamento ADD FOREIGN KEY (NomeArea) REFERENCES Area (Nome);

ALTER TABLE Insegnamento ADD FOREIGN KEY (NomeLingua) REFERENCES Lingua (Sigla);

ALTER TABLE ArgomentoTesi ADD FOREIGN KEY (NomeUniversita) REFERENCES Universita (Nome);

ALTER TABLE CorsoDiLaurea ADD FOREIGN KEY (nomeUniversita) REFERENCES Universita (Nome);

ALTER TABLE Coordinatore ADD FOREIGN KEY (NomeUniversita) REFERENCES Universita (Nome);

ALTER TABLE ValCitta ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente);

ALTER TABLE ValCitta ADD FOREIGN KEY (NomeCitta,StatoCitta) REFERENCES Citta (Nome,Stato);

ALTER TABLE ValFlusso ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente);

ALTER TABLE ValFlusso ADD FOREIGN KEY (IdFlusso) REFERENCES Flusso (Id);

ALTER TABLE ValInsegnamento ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente);

ALTER TABLE ValInsegnamento ADD FOREIGN KEY (IdInsegnamento) REFERENCES Insegnamento (Id);

ALTER TABLE ValTesi ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente);

ALTER TABLE ValTesi ADD FOREIGN KEY (IdArgomentoTesi) REFERENCES ArgomentoTesi (Id);

ALTER TABLE ValUniversita ADD FOREIGN KEY (NomeUtenteStudente) REFERENCES Studente (NomeUtente);

ALTER TABLE ValUniversita ADD FOREIGN KEY (NomeUniversita) REFERENCES Universita (Nome);

