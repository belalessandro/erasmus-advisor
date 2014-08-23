package it.unipd.dei.bding.erasmusadvisor.beans;

import java.io.Serializable;
import java.sql.Date;

/**
 * Represents the ...
 * 
 * @author Alessandro
 * @version 1.0
 *  
 */
public class ValutazioneTesiBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1569276918498251354L;

	/**
	 *
	 */
	private String nomeUtenteStudente;

	/**
	 *
	 */
	private int idArgomentoTesi;

	/**
	 *
	 */
	private int impegnoNecessario;

	/**
	 *
	 */
	private int interesseArgomento;

	/**
	 *
	 */
	private int disponibilitaRelatore;

	/**
	 *
	 */
	private int soddisfazione;

	/**
	 *
	 */
	private Date dataInserimento;

	/**
	 *
	 */
	private String commento;

	/**
	 * Empty constructor
	 */
	public ValutazioneTesiBean() {}

	/**
	 * Returns the field nomeUtenteStudente.
	 *
	 * @return the value of nomeUtenteStudente
	 */
	public String getNomeUtenteStudente() {
		return nomeUtenteStudente;
	}

	/**
	 * Sets the field nomeUtenteStudente
	 *
	 * @param nomeUtenteStudente The value to set
	 */
	public void setNomeUtenteStudente(String nomeUtenteStudente) {
		this.nomeUtenteStudente = nomeUtenteStudente;
	}

	/**
	 * Returns the field idArgomentoTesi.
	 *
	 * @return the value of idArgomentoTesi
	 */
	public int getIdArgomentoTesi() {
		return idArgomentoTesi;
	}

	/**
	 * Sets the field idArgomentoTesi
	 *
	 * @param idArgomentoTesi The value to set
	 */
	public void setIdArgomentoTesi(int idArgomentoTesi) {
		this.idArgomentoTesi = idArgomentoTesi;
	}

	/**
	 * Returns the field impegnoNecessario.
	 *
	 * @return the value of impegnoNecessario
	 */
	public int getImpegnoNecessario() {
		return impegnoNecessario;
	}

	/**
	 * Sets the field impegnoNecessario
	 *
	 * @param impegnoNecessario The value to set
	 */
	public void setImpegnoNecessario(int impegnoNecessario) {
		this.impegnoNecessario = impegnoNecessario;
	}

	/**
	 * Returns the field interesseArgomento.
	 *
	 * @return the value of interesseArgomento
	 */
	public int getInteresseArgomento() {
		return interesseArgomento;
	}

	/**
	 * Sets the field interesseArgomento
	 *
	 * @param interesseArgomento The value to set
	 */
	public void setInteresseArgomento(int interesseArgomento) {
		this.interesseArgomento = interesseArgomento;
	}

	/**
	 * Returns the field disponibilitaRelatore.
	 *
	 * @return the value of disponibilitaRelatore
	 */
	public int getDisponibilitaRelatore() {
		return disponibilitaRelatore;
	}

	/**
	 * Sets the field disponibilitaRelatore
	 *
	 * @param disponibilitaRelatore The value to set
	 */
	public void setDisponibilitaRelatore(int disponibilitaRelatore) {
		this.disponibilitaRelatore = disponibilitaRelatore;
	}

	/**
	 * Returns the field soddisfazione.
	 *
	 * @return the value of soddisfazione
	 */
	public int getSoddisfazione() {
		return soddisfazione;
	}

	/**
	 * Sets the field soddisfazione
	 *
	 * @param soddisfazione The value to set
	 */
	public void setSoddisfazione(int soddisfazione) {
		this.soddisfazione = soddisfazione;
	}

	/**
	 * Returns the field dataInserimento.
	 *
	 * @return the value of dataInserimento
	 */
	public Date getDataInserimento() {
		return dataInserimento;
	}

	/**
	 * Sets the field dataInserimento
	 *
	 * @param dataInserimento The value to set
	 */
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	/**
	 * Returns the field commento.
	 *
	 * @return the value of commento
	 */
	public String getCommento() {
		return commento;
	}

	/**
	 * Sets the field commento
	 *
	 * @param commento The value to set
	 */
	public void setCommento(String commento) {
		this.commento = commento;
	}
}

