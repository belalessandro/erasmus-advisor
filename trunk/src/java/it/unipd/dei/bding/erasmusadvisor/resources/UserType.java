package it.unipd.dei.bding.erasmusadvisor.resources;

/**
 * Represent the classification of a logged user. 
 * 
 * It may be:
 * - a Student (STUDENTE).
 * - a Flow Manager (RESPONSABILE).
 * - a Erasmus Coordinator (COORDINATORE).
 */
public enum UserType {
	STUDENTE, RESPONSABILE, COORDINATORE;
}