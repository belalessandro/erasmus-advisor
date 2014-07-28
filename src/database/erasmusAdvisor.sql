\c postgres
drop database "erasmusadvisor";
create database "erasmusadvisor";
\c erasmusadvisor;

CREATE TABLE Professore(
Id		SERIAL 			PRIMARY KEY,
Nome	VARCHAR(20),
Cognome	VARCHAR(20)		NOT NULL
);

CREATE TABLE Area(
Nome 		VARCHAR(40) 	PRIMARY KEY
);

CREATE TABLE Citta(
Nome 		VARCHAR(30),
Stato		VARCHAR(30),
PRIMARY KEY (Nome, Stato)
);

CREATE TABLE Universita(
Nome 		VARCHAR(40) 	PRIMARY KEY,
Link 		VARCHAR(100),
Posizione 	SMALLINT 		CHECK(Posizione > 0),
Alloggi 	BOOLEAN,
NomeCitta	VARCHAR(30)		NOT NUll,
StatoCitta	VARCHAR(30)		NOT NUll,
FOREIGN KEY (NomeCitta, StatoCitta) REFERENCES Citta(Nome, Stato)
);

CREATE DOMAIN TipoLaurea AS VARCHAR(20)
	CHECK(VALUE = 'triennale' OR VALUE = 'magistrale' OR VALUE = 'ciclounico');

CREATE TABLE CorsoDiLaurea(
Nome 		VARCHAR(40),
Livello 	TipoLaurea ,
Universita	VARCHAR(40),		
PRIMARY KEY (Nome, Livello, Universita),
FOREIGN KEY (Universita) REFERENCES Universita(Nome)
);

CREATE TABLE Specializzazione(
Corso 		VARCHAR(40),
Livello		TipoLaurea,
Universita	VARCHAR(40),
NomeArea	VARCHAR(40),
PRIMARY KEY (Corso, Livello, Universita, NomeArea),
FOREIGN KEY (Corso, Livello, Universita) REFERENCES CorsoDiLaurea(Nome, Livello, Universita),
FOREIGN KEY (NomeArea) 						 REFERENCES Area(Nome)
);

CREATE DOMAIN Semestre AS SMALLINT
	CHECK(VALUE = 1 OR VALUE = 2 OR VALUE IS NULL);


CREATE DOMAIN AnnoAccademico AS CHAR(9)
	CHECK(VALUE LIKE '____/____'); --  <- Da sostituire con una regexp?

CREATE TABLE ArgomentoTesi(
Nome				VARCHAR(60),
Universita			VARCHAR(40),
PeriodoErogazione	Semestre		DEFAULT NULL,
Stato				BOOLEAN			DEFAULT TRUE, -- True = Attivo, False = Disattivo
Anno				AnnoAccademico	NOT NULL,
Area				VARCHAR(40)		NOT NULL,
PRIMARY KEY (Nome, Universita),
FOREIGN KEY (Universita) REFERENCES Universita(Nome),
FOREIGN KEY (Area) 	   REFERENCES Area(Nome)
);

CREATE TABLE Insegnamento(
Nome				VARCHAR(30),
Crediti				SMALLINT,
Universita			VARCHAR(40),
PeriodoErogazione 	Semestre		NOT NULL,
Stato				BOOLEAN			DEFAULT TRUE,
Anno 				AnnoAccademico	NOT NULL,
Area				VARCHAR(40)		NOT NULL,
PRIMARY KEY (Nome, Crediti, Universita),
FOREIGN KEY (Universita) 	REFERENCES Universita(Nome),
FOREIGN KEY (Area) 		REFERENCES Area(Nome)
);

CREATE TABLE Gestione(
Professore				SERIAL,
NomeArgomentoTesi		VARCHAR(60),
UniversitaArgomentoTesi	VARCHAR(40),
PRIMARY KEY	(Professore, NomeArgomentoTesi, UniversitaArgomentoTesi),
FOREIGN KEY (NomeArgomentoTesi, UniversitaArgomentoTesi) REFERENCES ArgomentoTesi(Nome, Universita)
);

CREATE TABLE Svolgimento(
Professore				SERIAL,
NomeInsegnamento		VARCHAR(30),
CreditiInsegnamento 	SMALLINT,
UniversitaInsegnamento	VARCHAR(40),
PRIMARY KEY (Professore, NomeInsegnamento, CreditiInsegnamento, UniversitaInsegnamento),
FOREIGN KEY ( NomeInsegnamento, CreditiInsegnamento, UniversitaInsegnamento)
			REFERENCES Insegnamento(Nome, Crediti, Universita)

);

CREATE TABLE Estensione(
ArgomentoTesi 		VARCHAR(60),
Universita 			VARCHAR(40),
Area 				VARCHAR(40),
PRIMARY KEY	(ArgomentoTesi, Universita, Area),
FOREIGN KEY (ArgomentoTesi, Universita) REFERENCES ArgomentoTesi(Nome, Universita),
FOREIGN KEY	(Area)						REFERENCES Area(Nome)
);

CREATE TABLE Lingua(
Nome 	CHAR(3) PRIMARY KEY
);

CREATE TABLE CertificatiLiguistici(
NomeLingua		CHAR(3),
Livello		VARCHAR(10),
PRIMARY KEY	(NomeLingua, Livello),
FOREIGN KEY (NomeLingua) REFERENCES Lingua(Nome)
);

CREATE TABLE LinguaInsegnamento(
Lingua					CHAR(3),
NomeInsegnamento		VARCHAR(30),
CreditiInsegnamento 	SMALLINT,
UniversitaInsegnamento	VARCHAR(40),
PRIMARY KEY (Lingua, NomeInsegnamento, CreditiInsegnamento, UniversitaInsegnamento),
FOREIGN KEY (Lingua) REFERENCES Lingua(Nome),
FOREIGN KEY ( NomeInsegnamento, CreditiInsegnamento, UniversitaInsegnamento)
			REFERENCES Insegnamento(Nome, Crediti, Universita)
);

CREATE TABLE LinguaTesi(
Lingua					CHAR(3),
NomeArgomentoTesi		VARCHAR(60),
UniversitaArgomentoTesi	VARCHAR(40),
PRIMARY KEY (Lingua, NomeArgomentoTesi, UniversitaArgomentoTesi),
FOREIGN KEY (NomeArgomentoTesi, UniversitaArgomentoTesi) REFERENCES ArgomentoTesi(Nome, Universita)
);

CREATE TABLE LinguaCitta(
Lingua					CHAR(3),
Citta				VARCHAR(30),
Stato				VARCHAR(30),
PRIMARY KEY (Lingua, Citta, Stato),
FOREIGN KEY (Lingua) REFERENCES Lingua(Nome),
FOREIGN KEY (Citta, Stato) REFERENCES Citta(Nome, Stato)
);


CREATE TABLE ResponsabileFlusso(
NomeUtente			VARCHAR(30)		PRIMARY KEY,
Nome 				VARCHAR(20)		NOT NULL,
Cognome 			VARCHAR(20) 	NOT NULL,
Email				VARCHAR(30)		DEFAULT NULL,
DataRegistrazione 	DATE			NOT NULL,
HashedPassword		VARCHAR(30)		NOT NULL,
Salt 				VARCHAR(30)		NOT NULL,
UltimoAccesso		DATE,
Attivo				BOOLEAN			DEFAULT FALSE,
Universita 			VARCHAR(40)		NOT NULL,
FOREIGN KEY (Universita) REFERENCES Universita(Nome)
);


CREATE TABLE Studente(
NomeUtente			VARCHAR(30)		PRIMARY KEY,
Email				VARCHAR(30)		DEFAULT NULL,
DataRegistrazione 	DATE 			NOT NULL,
HashedPassword		VARCHAR(30)		NOT NULL,
Salt 				VARCHAR(30)		NOT NULL,
UltimoAccesso		DATE,
Attivo				BOOLEAN			DEFAULT FALSE,
Universita 			VARCHAR(40)		NOT NULL,
FOREIGN KEY (Universita) REFERENCES Universita(Nome)
);

CREATE TABLE Coordinatore(
NomeUtente			VARCHAR(30)		PRIMARY KEY,
Email				VARCHAR(30)		DEFAULT NULL,
DataRegistrazione 	DATE 			NOT NULL,
HashedPassword		VARCHAR(30)		NOT NULL,
Salt 				VARCHAR(30)		NOT NULL,
UltimoAccesso		DATE,
Attivo				BOOLEAN			DEFAULT FALSE,
Universita 			VARCHAR(40)		NOT NULL,
FOREIGN KEY (Universita) REFERENCES Universita(Nome)
);

CREATE TABLE Flusso(
Id 						SERIAL 			PRIMARY KEY,
ResponsabileFlusso		VARCHAR(30)		NOT NULL,
PostiDisponibili		SMALLINT		NOT NULL,
Attivo					BOOLEAN			DEFAULT FALSE,
DataUltimaModifica		DATE,
Durata					SMALLINT		NOT NUll,
FOREIGN KEY (ResponsabileFlusso) REFERENCES ResponsabileFlusso(NomeUtente) 			
);

CREATE TABLE Destinazione(
Flusso 		SERIAL 			REFERENCES	Flusso(Id),
Universita 	VARCHAR(40)		REFERENCES	Universita(Nome),
PRIMARY KEY (Flusso, Universita)
);

CREATE TABLE Iscrizione(
Studente 			VARCHAR(30),
NomeCorso			VARCHAR(40),
LivelloCorso		TipoLaurea,
UniversitaCorso		VARCHAR(40),
AnnoInizio			SMALLINT NOT NUll,
AnnoFine			SMALLINT NOT NUll
					CHECK(AnnoFine > AnnoInizio),
PRIMARY KEY (Studente, NomeCorso, LivelloCorso, UniversitaCorso),
FOREIGN KEY (Studente) REFERENCES Studente(NomeUtente),
FOREIGN KEY	(NomeCorso, LivelloCorso, UniversitaCorso) REFERENCES CorsoDiLaurea(Nome, Livello, Universita)
);

CREATE TABLE Orgine(
Flusso 				SERIAL,
NomeCorso			VARCHAR(40),
LivelloCorso		TipoLaurea,
UniversitaCorso		VARCHAR(40),
PRIMARY KEY (Flusso, NomeCorso, LivelloCorso, UniversitaCorso),
FOREIGN KEY (Flusso) REFERENCES Flusso(Id),
FOREIGN KEY	(NomeCorso, LivelloCorso, UniversitaCorso) REFERENCES CorsoDiLaurea(Nome, Livello, Universita)
);

CREATE TABLE Riconoscimento(
Flusso 					SERIAL,
NomeInsegnamento		VARCHAR(30),
CreditiInsegnamento 	SMALLINT,
UniversitaInsegnamento	VARCHAR(40),
PRIMARY KEY (Flusso, NomeInsegnamento, CreditiInsegnamento, UniversitaInsegnamento),
FOREIGN KEY (Flusso) REFERENCES Flusso(Id),
FOREIGN KEY ( NomeInsegnamento, CreditiInsegnamento, UniversitaInsegnamento)
			REFERENCES Insegnamento(Nome, Crediti, Universita)

);


CREATE TABLE Documentazione(
Flusso 				SERIAL,
LinguaCertificato	CHAR(3),
LivelloCertificato	VARCHAR(10),
PRIMARY KEY (Flusso, LinguaCertificato, LivelloCertificato),
FOREIGN KEY	(Flusso) REFERENCES Flusso(Id),
FOREIGN KEY (LinguaCertificato, LivelloCertificato) REFERENCES CertificatiLiguistici(NomeLingua, Livello)
);

CREATE TABLE Partecipazione(
Studente			VARCHAR(30),
Flusso 				SERIAL,
Inizio 				DATE 			NOT NULL,
Fine 				DATE 			NOT NUll,
PRIMARY KEY (Studente, Flusso),
FOREIGN KEY (Studente) 		REFERENCES Studente(NomeUtente),
FOREIGN KEY (Flusso) 			REFERENCES Flusso(Id)
);

CREATE TABLE Interesse(
Studente 			VARCHAR(30),
Flusso 				SERIAL,
PRIMARY KEY (Studente, Flusso),
FOREIGN KEY (Studente) 	REFERENCES Studente(NomeUtente),
FOREIGN KEY (Flusso) 		REFERENCES Flusso(Id)
);

CREATE DOMAIN Valutazione AS SMALLINT 
	DEFAULT NULL
	CHECK((VALUE >= 1 AND VALUE <= 5) OR VALUE IS NULL);

CREATE TABLE ValFlusso(
Studente 			VARCHAR(30),
Flusso 				SERIAL,
SoddEsperienza 		Valutazione,
SoddAccademica		Valutazione,
Didattica			Valutazione,
ValResponsabile		Valutazione,
DataInserimento		DATE 			DEFAULT CURRENT_DATE,
Commento			VARCHAR(120),
PRIMARY KEY	(Studente, Flusso),
FOREIGN KEY (Studente) 	REFERENCES Studente(NomeUtente),
FOREIGN KEY (Flusso) 		REFERENCES Flusso(Id)
);

CREATE TABLE ValUniversita(
Studente 			VARCHAR(30),
Universita 			VARCHAR(40),
CollocazioneUrbana	Valutazione,
IniziativeErasmus	Valutazione,
QtaInsegnamenti		Valutazione,
QtaAule				Valutazione,
DataInserimento 	DATE 			DEFAULT CURRENT_DATE,
Commento 			VARCHAR(120),
PRIMARY KEY (Studente, Universita),
FOREIGN KEY (Studente) 	REFERENCES Studente(NomeUtente),
FOREIGN KEY (Universita) 	REFERENCES Universita(Nome)
);

CREATE TABLE ValCitta(
Studente				VARCHAR(30),
NomeCitta 				VARCHAR(30),
StatoCitta				VARCHAR(30),
CostoDellaVita			Valutazione,
DisponibilitaAlloggi	Valutazione,
VivibilitaUrbana		Valutazione,
VitaSociale				Valutazione,
DataInserimento			DATE 			DEFAULT CURRENT_DATE,
Commento				CHAR(120),
PRIMARY KEY (Studente, NomeCitta, StatoCitta),
FOREIGN KEY (Studente) 					REFERENCES Studente(NomeUtente),
FOREIGN KEY	(NomeCitta, StatoCitta)	 	REFERENCES Citta(Nome, Stato)
);

CREATE TABLE ValTesi(
Studente 				VARCHAR(30),
NomeArgomentoTesi		VARCHAR(60),
UniversitaArgomentoTesi	VARCHAR(40),
ImpegnoNecessario		Valutazione,
InteresseArgomento		Valutazione,
DisponibilitaRelatore	Valutazione,
Soddisfazione			Valutazione,
DataInserimento			DATE 			DEFAULT CURRENT_DATE,
Commento				VARCHAR(120),
PRIMARY KEY (Studente, NomeArgomentoTesi, UniversitaArgomentoTesi),
FOREIGN KEY (Studente) 	REFERENCES Studente(NomeUtente),
FOREIGN KEY (NomeArgomentoTesi, UniversitaArgomentoTesi) REFERENCES ArgomentoTesi(Nome, Universita)
);

CREATE TABLE ValInsegnamento(
Studente 				VARCHAR(30),
NomeInsegnamento 		VARCHAR(30),
CreditiInsegnamento		SMALLINT,
UniversitaInsegnamento 	VARCHAR(40),
QtaInsegnamnto			Valutazione,
Interesse 				Valutazione,
Difficolta				Valutazione,
RispettoDelleOre		Valutazione,
DataInserimento			DATE 			DEFAULT CURRENT_DATE,
Commento				VARCHAR(120),
PRIMARY KEY (Studente, NomeInsegnamento, CreditiInsegnamento, UniversitaInsegnamento),
FOREIGN KEY (Studente) 	REFERENCES Studente(NomeUtente),
FOREIGN KEY (NomeInsegnamento, CreditiInsegnamento, UniversitaInsegnamento) REFERENCES Insegnamento(Nome, Crediti, Universita)
);


