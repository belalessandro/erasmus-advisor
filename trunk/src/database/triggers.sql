-- Author: Mauro Piazza
--
-- Description: File containing all functions for managing database's domains and triggers

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
    
    
    
    