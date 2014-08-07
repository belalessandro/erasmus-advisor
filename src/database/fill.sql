-- Author: Mauro Piazza
-- Last Modified: 2014.08.06 12.04
--
-- Description: SQL file containing all statements to populate the database

--INSERT INTO Studente VALUES('mauro.piazza', 'mr.charlie90@gmail.com','2013-04-01', 'aldskj2o38428dqjkhdkqj1832','alskdjlkj23948209wdh', NULL, TRUE);
--INSERT INTO Studente VALUES('mauro.piazza1', 'mr.charle90gmail.com','2013-04-01', 'aldskj2o38428dqjkhdkqj1832','alskdjlkj23948209wdh', NULL, TRUE);

-- Foreign key test
INSERT INTO citta VALUES('Paris', 'France');
INSERT INTO lingua VALUES('fra', 'French');
INSERT INTO linguacitta VALUES('fra','Paris','France');
--delete from citta where Nome='Paris';

INSERT INTO Universita VALUES ('Ecole normale supérieure','www.ens.fr', 28, TRUE, 'Paris', 'France');

INSERT INTO ResponsabileFlusso VALUES (
    'georgua.en',
    'Georguà', 
    'Enformage', 
    'georgua.enformage@ens.fr', 
    CURRENT_DATE, 
    'asldubvdkwjbkwrw98374', 
    'slakj238940293fh', 
    NULL, 
    TRUE, 
    'Ecole normale supérieure');

INSERT INTO CorsoDiLaurea VALUES (1, 'Économie', 'triennale', 'Ecole normale supérieure');

INSERT INTO Flusso VALUES ('FRA001', 'Ecole normale supérieure', 'georgua.en', 10, TRUE, CURRENT_DATE, 5);

INSERT INTO Origine VALUES ('FRA001', 1)

--CREATE TABLE Flusso
--(
--Id SERIAL,
--Destinazione VARCHAR(80) NOT NULL, -- mauro: aggiunta campo dell'universita di destinazione
--RespFlusso VARCHAR(50) NOT NULL,
--PostiDisponibili SMALLINT NOT NULL,
--Attivo BOOLEAN NOT NULL DEFAULT TRUE,
--DataUltimaModifica DATE,
--Durata SMALLINT NOT NULL CHECK (Durata > 0), -- luca: così dovrebbe andare
--PRIMARY KEY (Id)
--);

--CREATE TABLE CorsoDiLaurea
--(
--Id SERIAL,
--Nome VARCHAR(40) NOT NULL,
--Livello TIPOLAUREA,
--nomeUniversita VARCHAR(80),
--PRIMARY KEY (Id)
--);

--CREATE TABLE Origine
--(
--IdFlusso INTEGER,
--IdCorso INTEGER,
--PRIMARY KEY (IdFlusso,IdCorso)
--);

--CREATE TABLE ResponsabileFlusso
--(
--NomeUtente VARCHAR(50),
--Nome VARCHAR(30) NOT NULL,
--Cognome VARCHAR(40) NOT NULL,
--Email VARCHAR(50) NOT NULL,
--DataRegistrazione DATE NOT NULL DEFAULT CURRENT_DATE,
--Password VARCHAR(128) NOT NULL,
--Salt VARCHAR(128) NOT NULL,
--UltimoAccesso DATE DEFAULT NULL,
--Attivo BOOLEAN NOT NULL DEFAULT FALSE,
--NomeUniversita VARCHAR(80) NOT NULL,
--PRIMARY KEY (NomeUtente)
--);

    



--CREATE TABLE Universita
--(
--Nome VARCHAR(80),
--Link TEXT DEFAULT NULL, -- luca: messo TEXT come tipo di dato
--PosizioneClassifica SMALLINT DEFAULT NULL CHECK (PosizioneClassifica > 0), -- luca: aggiunto check
--PresenzaAlloggi BOOLEAN DEFAULT FALSE,
--nomeCitta VARCHAR(30),
--statoCitta VARCHAR(30),
--PRIMARY KEY (Nome)
--);



--INSERT INTO CordoDiLaurea()

