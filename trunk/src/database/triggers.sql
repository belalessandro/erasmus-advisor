-- Author: Mauro Piazza, Fede Chiariotti
--
-- Description: File containing all functions for managing database's domains and triggers

--Possibile che manchi il trigger per i professori (un prof deve avere almeno un insegnamento o tesi per restare nel db)

-- Checking foreach flow that the departure university is not the same as the arriving one
CREATE FUNCTION CheckFlowOriginAndDestination() RETURNS TRIGGER AS $$
    BEGIN
	    PERFORM C.NomeUniversita, F.Destinazione
	            FROM CorsoDiLaurea AS C, Flusso AS F
	            WHERE NEW.idCorso = C.id AND NEW.idFlusso = F.id AND C.nomeUniversita =  F.destinazione;
	    
	    IF FOUND THEN
	       RAISE EXCEPTION 'EA ERROR: Origin and destination universities are equal.' USING ERRCODE = 'EA001'; 
	    END IF;
	    
	    RETURN NEW;
    END;
$$ LANGUAGE PLPGSQL;

-- Trigger CheckingFlowUniversities
CREATE TRIGGER CheckingFlowUniversities BEFORE UPDATE OR INSERT 
    ON origine
    FOR EACH ROW 
    EXECUTE PROCEDURE CheckFlowOriginAndDestination();
    
-- View showing the flow id and the relative origin universities
CREATE VIEW UniversitaOriginePerFlusso AS 
    SELECT DISTINCT F.id AS idFlusso, C.nomeUniversita AS NomeUniversitaOrigine
    FROM   Flusso AS F, Origine AS O, CorsoDiLaurea AS C
    WHERE F.id = O.idFlusso AND C.id = O.idCorso;

-- Checking foreach flow that all courses refering to it belong to the same university
CREATE FUNCTION CheckFlowCoursesOrigin() RETURNS TRIGGER AS $$
    BEGIN
        PERFORM * 
                FROM CorsoDiLaurea AS C, UniversitaOriginePerFlusso AS U  
                WHERE NEW.idCorso = C.id AND NEW.idFlusso = U.idFlusso AND C.nomeUniversita <> U.nomeUniversitaOrigine; 
        
        IF FOUND THEN
           RAISE EXCEPTION 'EA ERROR: Not all courses for the flow: % are from the same university.', NEW.idFlusso  
            USING ERRCODE = 'EA002'; 
        END IF;
        
        RETURN NEW;
    END;
$$ LANGUAGE PLPGSQL;   

-- Trigger CheckingFlowCourses
CREATE TRIGGER CheckingFlowCourses BEFORE UPDATE OR INSERT
    ON Origine
    FOR EACH ROW
    EXECUTE PROCEDURE CheckFlowCoursesOrigin();
    
    
    

--Checking that a student does not have multiple simultaneous subscriptions
CREATE FUNCTION CheckSubscriptionOverlap() RETURNS TRIGGER AS $$
    BEGIN
	    PERFORM I.nomeUtenteStudente
	            FROM iscrizione AS I
	            WHERE NEW.AnnoInizio<I.AnnoFine OR NEW.AnnoInizio<I.AnnoFine AND NEW.nomeUtenteStudente=I.nomeUtenteStudente;

	    IF FOUND THEN
	       RAISE EXCEPTION 'EA ERROR: Overlap in program subscription.' USING ERRCODE = 'EA003'; 
	    END IF;
	    
	    RETURN NEW;
    END;
$$ LANGUAGE PLPGSQL;


CREATE TRIGGER CheckingSubscriptionOverlap BEFORE UPDATE OR INSERT 
    ON iscrizione
    FOR EACH ROW 
    EXECUTE PROCEDURE CheckSubscriptionOverlap();
    
    
    
    
    
    
    
--Checking that a student does not have multiple simultaneous Erasmus programs
CREATE FUNCTION CheckErasmusOverlap() RETURNS TRIGGER AS $$
    BEGIN
	    PERFORM P.NomeUtenteStudente
	            FROM partecipazione AS P
	            WHERE NEW.Inizio<P.Fine OR P.Inizio<NEW.Fine AND NEW.nomeUtenteStudente=P.NomeUtenteStudente;
	    RAISE NOTICE 'YO mother fucker!';
	    
	    IF FOUND THEN
	       RAISE EXCEPTION 'EA ERROR: Overlap in Erasmus subscription.' USING ERRCODE = 'EA004'; 
	    END IF;
	    
	    RETURN NEW;
    END;
$$ LANGUAGE PLPGSQL;

CREATE TRIGGER CheckingErasmusOverlap BEFORE UPDATE OR INSERT 
    ON partecipazione
    FOR EACH ROW 
    EXECUTE PROCEDURE CheckErasmusOverlap();
    
    
    
    
    
    
--Checking that a student does not evaluate Erasmus flows he did not participate in
CREATE FUNCTION CheckUniEval() RETURNS TRIGGER AS $$
    BEGIN
	    PERFORM P.NomeUtenteStudente
	            FROM partecipazione AS P, Flusso as F
	            WHERE NEW.idFlusso=P.idFlusso AND P.idFlusso=F.idFlusso AND F.Destinazione=NEW.NomeUniversita AND 
	            	NEW.NomeUtenteStudente=P.NomeUtenteStudente;
	    RAISE NOTICE 'YO mother fucker!';
	    
	    IF NOT FOUND THEN
	       RAISE EXCEPTION 'EA ERROR: Invalid evaluation.' USING ERRCODE = 'EA005'; 
	    END IF;
	    
	    RETURN NEW;
    END;
$$ LANGUAGE PLPGSQL;


CREATE TRIGGER CheckingUniEval BEFORE UPDATE OR INSERT 
    ON ValutazioneUniversita
    FOR EACH ROW 
    EXECUTE PROCEDURE CheckUniEval();
    
    
    
    
    
    
--Checking that a student does not evaluate Erasmus flows he did not participate in
CREATE FUNCTION CheckFlowEval() RETURNS TRIGGER AS $$
    BEGIN
	    PERFORM P.nomeUtenteStudente
	            FROM partecipazione AS P
	            WHERE P.idFlusso=NEW.IdFlusso AND NEW.NomeUtenteStudente=P.NomeUtenteStudente;
	    RAISE NOTICE 'YO mother fucker!';
	    
	    IF NOT FOUND THEN
	       RAISE EXCEPTION 'EA ERROR: Invalid evaluation.' USING ERRCODE = 'EA005'; 
	    END IF;
	    
	    RETURN NEW;
    END;
$$ LANGUAGE PLPGSQL;


CREATE TRIGGER CheckingFlowEval BEFORE UPDATE OR INSERT 
    ON ValutazioneFlusso
    FOR EACH ROW 
    EXECUTE PROCEDURE CheckFlowEval();
    
    
    
    
    
    
    
--Checking that a student does not evaluate Erasmus flows he did not participate in
CREATE FUNCTION CheckCityEval() RETURNS TRIGGER AS $$
    BEGIN
	    PERFORM P.nomeUtenteStudente
	            FROM partecipazione AS P, Flusso as F, Universita as U
	            WHERE NEW.idFlusso=P.idFlusso AND P.idFlusso=F.idFlusso AND F.Destinazione=U.NomeUniversita AND 
	            	NEW.NomeUtenteStudente=P.NomeUtenteStudente AND U.nomeCitta=NEW.nomeCitta;
	    RAISE NOTICE 'YO mother fucker!';
	    
	    IF NOT FOUND THEN
	       RAISE EXCEPTION 'EA ERROR: Invalid evaluation.' USING ERRCODE = 'EA005'; 
	    END IF;
	    
	    RETURN NEW;
    END;
$$ LANGUAGE PLPGSQL;


CREATE TRIGGER CheckingCityEval BEFORE UPDATE OR INSERT 
    ON ValutazioneCitta
    FOR EACH ROW 
    EXECUTE PROCEDURE CheckCityEval();
    
    
    
    
    
    
--Checking that a student does not evaluate Erasmus flows he did not participate in
CREATE FUNCTION CheckExamEval() RETURNS TRIGGER AS $$
    BEGIN
	    PERFORM P.nomeUtenteStudente
	            FROM partecipazione AS P, Flusso as F, Insegnamento as I
	            WHERE NEW.idFlusso=P.idFlusso AND P.idFlusso=F.idFlusso AND F.Destinazione=I.NomeUniversita AND 
	            	NEW.NomeUtenteStudente=P.NomeUtenteStudente AND NEW.IdInsegnamento=I.Id;
	    RAISE NOTICE 'YO mother fucker!';
	    
	    IF NOT FOUND THEN
	       RAISE EXCEPTION 'EA ERROR: Invalid evaluation.' USING ERRCODE = 'EA005'; 
	    END IF;
	    
	    RETURN NEW;
    END;
$$ LANGUAGE PLPGSQL;


CREATE TRIGGER CheckingExamEval BEFORE UPDATE OR INSERT 
    ON ValutazioneInsegnamento
    FOR EACH ROW 
    EXECUTE PROCEDURE CheckExamEval();
    
    
    
    
    
    
--Checking that a student does not evaluate Erasmus flows he did not participate in
CREATE FUNCTION CheckThesisEval() RETURNS TRIGGER AS $$
    BEGIN
	    PERFORM P.nomeUtenteStudente
	            FROM partecipazione AS P, Flusso as F, ArgomentoTesi as T
	            WHERE NEW.idFlusso=P.idFlusso AND P.idFlusso=F.idFlusso AND F.Destinazione=T.NomeUniversita AND 
	            	NEW.NomeUtenteStudente=P.NomeUtenteStudente AND NEW.IdArgomentoTesi=T.Id
	    RAISE NOTICE 'YO mother fucker!';
	    
	    IF NOT FOUND THEN
	       RAISE EXCEPTION 'EA ERROR: Invalid evaluation.' USING ERRCODE = 'EA005'; 
	    END IF;
	    
	    RETURN NEW;
    END;
$$ LANGUAGE PLPGSQL;


CREATE TRIGGER CheckingThesisEval BEFORE UPDATE OR INSERT 
    ON ValutazioneTesi
    FOR EACH ROW 
    EXECUTE PROCEDURE CheckThesisEval();
    
    
    
    
    
    
--Checking that a new user does not have the same username as an existing user
CREATE FUNCTION CheckCoordinator() RETURNS TRIGGER AS $$
    BEGIN
	    PERFORM C.NomeUtente
	            FROM Coordinatore AS C
	            WHERE NEW.NomeUtente=C.NomeUtente;
	    RAISE NOTICE 'YO mother fucker!';
	    
	    IF FOUND THEN
	       RAISE EXCEPTION 'EA ERROR: Username already present.' USING ERRCODE = 'EA006'; 
	    END IF;
	    
	    RETURN NEW;
    END;
$$ LANGUAGE PLPGSQL;


CREATE TRIGGER CheckingNewStudentC BEFORE UPDATE OR INSERT 
    ON Studente
    FOR EACH ROW 
    EXECUTE PROCEDURE CheckCoordinator();
    
CREATE TRIGGER CheckingNewRespC BEFORE UPDATE OR INSERT 
    ON ResponsabileFlusso
    FOR EACH ROW 
    EXECUTE PROCEDURE CheckCoordinator();
    
    
    
    
    
--Checking that a new user does not have the same username as an existing user
CREATE FUNCTION CheckResp() RETURNS TRIGGER AS $$
    BEGIN
	    PERFORM R.NomeUtente
	            FROM ResponsabileFlusso AS R
	            WHERE NEW.NomeUtente=R.NomeUtente;
	    RAISE NOTICE 'YO mother fucker!';
	    
	    IF FOUND THEN
	       RAISE EXCEPTION 'EA ERROR: Username already present.' USING ERRCODE = 'EA006'; 
	    END IF;
	    
	    RETURN NEW;
    END;
$$ LANGUAGE PLPGSQL;


CREATE TRIGGER CheckingNewStudentR BEFORE UPDATE OR INSERT 
    ON Studente
    FOR EACH ROW 
    EXECUTE PROCEDURE CheckResp();
    
CREATE TRIGGER CheckingNewCoordR BEFORE UPDATE OR INSERT 
    ON Coordinatore
    FOR EACH ROW 
    EXECUTE PROCEDURE CheckResp();    
    
 

 
 --Checking that a new user does not have the same username as an existing user
CREATE FUNCTION CheckStudent() RETURNS TRIGGER AS $$
    BEGIN
	    PERFORM S.NomeUtente
	            FROM Studente AS S
	            WHERE NEW.NomeUtente=S.NomeUtente
	    RAISE NOTICE 'YO mother fucker!';
	    
	    IF FOUND THEN
	       RAISE EXCEPTION 'EA ERROR: Username already present.' USING ERRCODE = 'EA006'; 
	    END IF;
	    
	    RETURN NEW;
    END;
$$ LANGUAGE PLPGSQL;


CREATE TRIGGER CheckingNewRespS BEFORE UPDATE OR INSERT 
    ON ResponsabileFlusso
    FOR EACH ROW 
    EXECUTE PROCEDURE CheckStudent();
    
CREATE TRIGGER CheckingNewCoordS BEFORE UPDATE OR INSERT 
    ON Coordinatore
    FOR EACH ROW 
    EXECUTE PROCEDURE CheckStudent();
    
    
--Checking that a new user does not have the same email address as an existing user
CREATE FUNCTION CheckCoordinatorEmail() RETURNS TRIGGER AS $$
    BEGIN
	    PERFORM C.NomeUtente
	            FROM Coordinatore AS C
	            WHERE NEW.Email=C.Email;
	    
	    IF FOUND THEN
	       RAISE EXCEPTION 'EA ERROR: Email already present.' USING ERRCODE = 'EA007'; 
	    END IF;
	    
	    RETURN NEW;
    END;
$$ LANGUAGE PLPGSQL;


CREATE TRIGGER CheckingEmailStudentC BEFORE UPDATE OR INSERT 
    ON Studente
    FOR EACH ROW 
    EXECUTE PROCEDURE CheckCoordinatorEmail();
    
CREATE TRIGGER CheckingEmailRespC BEFORE UPDATE OR INSERT 
    ON ResponsabileFlusso
    FOR EACH ROW 
    EXECUTE PROCEDURE CheckCoordinatorEmail();
    
    
    
    
    
--Checking that a new user does not have the same email address as an existing user
CREATE FUNCTION CheckRespEmail() RETURNS TRIGGER AS $$
    BEGIN
	    PERFORM R.NomeUtente
	            FROM ResponsabileFlusso AS R
	            WHERE NEW.Email=R.Email;
	    
	    IF FOUND THEN
	       RAISE EXCEPTION 'EA ERROR: Email already present.' USING ERRCODE = 'EA007'; 
	    END IF;
	    
	    RETURN NEW;
    END;
$$ LANGUAGE PLPGSQL;


CREATE TRIGGER CheckingEmailStudentR BEFORE UPDATE OR INSERT 
    ON Studente
    FOR EACH ROW 
    EXECUTE PROCEDURE CheckRespEmail();
    
CREATE TRIGGER CheckingEmailCoordR BEFORE UPDATE OR INSERT 
    ON Coordinatore
    FOR EACH ROW 
    EXECUTE PROCEDURE CheckRespEmail();    
    
 

 
 --Checking that a new user does not have the same email address as an existing user
CREATE FUNCTION CheckStudentEmail() RETURNS TRIGGER AS $$
    BEGIN
	    PERFORM S.Email
	            FROM Studente AS S
	            WHERE NEW.Email=S.Email
	    
	    IF FOUND THEN
	       RAISE EXCEPTION 'EA ERROR: Email already present.' USING ERRCODE = 'EA007'; 
	    END IF;
	    
	    RETURN NEW;
    END;
$$ LANGUAGE PLPGSQL;


CREATE TRIGGER CheckingEmailRespS BEFORE UPDATE OR INSERT 
    ON ResponsabileFlusso
    FOR EACH ROW 
    EXECUTE PROCEDURE CheckStudentEmail();
    
CREATE TRIGGER CheckingEmailCoordS BEFORE UPDATE OR INSERT 
    ON Coordinatore
    FOR EACH ROW 
    EXECUTE PROCEDURE CheckStudentEmail();