\c postgres
drop database "erasmusadvisor";
create database "erasmusadvisor";
\c erasmusadvisor;


-- Domains
CREATE DOMAIN TIPOLAUREA AS VARCHAR(10)
	CHECK(VALUE = 'triennale' OR VALUE = 'magistrale' OR VALUE = 'ciclounico');

CREATE DOMAIN SEMESTRE AS SMALLINT
	CHECK(VALUE = 1 OR VALUE = 2 OR VALUE IS NULL);

CREATE DOMAIN ANNOACCADEMICO AS CHAR(9)
	CHECK(VALUE LIKE '____/____'); --  <- Da sostituire con una regexp?

CREATE DOMAIN VALUTAZIONE AS SMALLINT 
	DEFAULT NULL
	CHECK((VALUE >= 1 AND VALUE <= 5) OR VALUE IS NULL);

	
-- Tables (Created with SQLEditor)
CREATE TABLE Area
(
Nome VARCHAR(40),
PRIMARY KEY (Nome)
);

CREATE TABLE Citta
(
Nome VARCHAR(20),
Stato VARCHAR(20),
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
LivelloCertificato VARCHAR(10),
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
NomeCitta VARCHAR(20),
StatoCitta VARCHAR(20),
PRIMARY KEY (SiglaLingua,NomeCitta,StatoCitta)
);

CREATE TABLE CertificatiLinguistici
(
NomeLingua CHAR(3),
Livello VARCHAR(10) NOT NULL,
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
Cognome VARCHAR(20) NOT NULL,
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
Attivo BOOLEAN,
DataUltimaModifica DATE,
/*Numero di mesi?*/
Durata SMALLINT NOT NULL,
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
/*Aggiungere vincolo inizio < fine*/
Inizio DATE NOT NULL,
/*Aggiungere vincolo inizio < fine*/
Fine DATE NOT NULL,
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
Nome VARCHAR(40),
Link VARCHAR(50) DEFAULT NULL,
PosizioneClassifica SMALLINT DEFAULT NULL,
PresenzaAlloggi BOOLEAN DEFAULT FALSE,
nomeCitta VARCHAR(20),
statoCitta VARCHAR(20),
PRIMARY KEY (Nome)
);

CREATE TABLE Destinazione
(
NomeUniversita VARCHAR(40),
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
NomeUniversita VARCHAR(40) NOT NULL,
PRIMARY KEY (NomeUtente)
);

CREATE TABLE Insegnamento
(
Id SERIAL,
Nome VARCHAR(40) NOT NULL,
Crediti SMALLINT NOT NULL,
NomeUniversita VARCHAR(40),
PeriodoErogazione SEMESTRE NOT NULL,
Stato BOOLEAN NOT NULL DEFAULT TRUE,
AnnoCorso ANNOACCADEMICO NOT NULL,
NomeArea VARCHAR(40),
NomeLingua CHAR(3),
PRIMARY KEY (Id)
);

CREATE TABLE ArgomentoTesi
(
Id SERIAL,
Nome VARCHAR(40) NOT NULL,
NomeUniversita VARCHAR(40),
Triennale BOOLEAN NOT NULL,
Magistrale BOOLEAN NOT NULL,
Stato BOOLEAN NOT NULL DEFAULT true,
NomeArea VARCHAR(40),
PRIMARY KEY (Id)
);

CREATE TABLE CorsoDiLaurea
(
Id SERIAL,
Nome VARCHAR(40) NOT NULL,
Livello TIPOLAUREA,
nomeUniversita VARCHAR(40),
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
NomeUniversita VARCHAR(40),
PRIMARY KEY (NomeUtente)
);

CREATE TABLE ValCitta
(
NomeUtenteStudente VARCHAR(50),
NomeCitta VARCHAR(20),
StatoCitta VARCHAR(20),
CostoDellaVita VALUTAZIONE DEFAULT NULL,
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
SoddEsperienza VALUTAZIONE DEFAULT NULL,
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
QtaInsegnamanto VALUTAZIONE DEFAULT NULL,
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
ImpegnoNecessario VALUTAZIONE DEFAULT NULL,
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
NomeUniversita VARCHAR(40),
CollocazioneUrbana VALUTAZIONE DEFAULT NULL,
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

ALTER TABLE ArgomentoTesi ADD FOREIGN KEY (NomeArea) REFERENCES Area (Nome);

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

