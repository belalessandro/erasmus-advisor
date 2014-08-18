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
public class FlussoBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7311535251244471827L;

	/**
	 *
	 */
	private String id;

	/**
	 *
	 */
	private String destinazione;

	/**
	 *
	 */
	private String respFlusso;

	/**
	 *
	 */
	private int postiDisponibili;

	/**
	 *
	 */
	private boolean attivo;

	/**
	 *
	 */
	private Date dataUltimaModifica;

	/**
	 *
	 */
	private int durata;

	/**
	 *
	 */
	private String dettagli;

	/**
	 * Empty constructor
	 */
	public FlussoBean() {}

	/**
	 * Returns the field id.
	 *
	 * @return the value of id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the field id
	 *
	 * @param id The value to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the field destinazione.
	 *
	 * @return the value of destinazione
	 */
	public String getDestinazione() {
		return destinazione;
	}

	/**
	 * Sets the field destinazione
	 *
	 * @param destinazione The value to set
	 */
	public void setDestinazione(String destinazione) {
		this.destinazione = destinazione;
	}

	/**
	 * Returns the field respFlusso.
	 *
	 * @return the value of respFlusso
	 */
	public String getRespFlusso() {
		return respFlusso;
	}

	/**
	 * Sets the field respFlusso
	 *
	 * @param respFlusso The value to set
	 */
	public void setRespFlusso(String respFlusso) {
		this.respFlusso = respFlusso;
	}

	/**
	 * Returns the field postiDisponibili.
	 *
	 * @return the value of postiDisponibili
	 */
	public int getPostiDisponibili() {
		return postiDisponibili;
	}

	/**
	 * Sets the field postiDisponibili
	 *
	 * @param postiDisponibili The value to set
	 */
	public void setPostiDisponibili(int postiDisponibili) {
		this.postiDisponibili = postiDisponibili;
	}

	/**
	 * Returns the field attivo.
	 *
	 * @return the value of attivo
	 */
	public boolean isAttivo() {
		return attivo;
	}

	/**
	 * Sets the field attivo
	 *
	 * @param attivo The value to set
	 */
	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

	/**
	 * Returns the field dataUltimaModifica.
	 *
	 * @return the value of dataUltimaModifica
	 */
	public Date getDataUltimaModifica() {
		return dataUltimaModifica;
	}

	/**
	 * Sets the field dataUltimaModifica
	 *
	 * @param dataUltimaModifica The value to set
	 */
	public void setDataUltimaModifica(Date dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}

	/**
	 * Returns the field durata.
	 *
	 * @return the value of durata
	 */
	public int getDurata() {
		return durata;
	}

	/**
	 * Sets the field durata
	 *
	 * @param durata The value to set
	 */
	public void setDurata(int durata) {
		this.durata = durata;
	}

	/**
	 * Returns the field dettagli.
	 *
	 * @return the value of dettagli
	 */
	public String getDettagli() {
		return dettagli;
	}

	/**
	 * Sets the field dettagli
	 *
	 * @param dettagli The value to set
	 */
	public void setDettagli(String dettagli) {
		this.dettagli = dettagli;
	}
}

