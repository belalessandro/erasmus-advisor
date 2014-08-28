package it.unipd.dei.bding.erasmusadvisor.beans;

/**
 * @author Nicola
 *
 */
public class SearchThesisBean
{
	private String nomeTesi;
	private String aree;
	private String livello;
	private String professori;
	private String lingue;
	
	public SearchThesisBean(){}

	/**
	 * @return
	 */
	public String getNomeTesi() {
		return nomeTesi;
	}

	/**
	 * @param nomeTesi
	 */
	public void setNomeTesi(String nomeTesi) {
		this.nomeTesi = nomeTesi;
	}

	public String getAree() {
		return aree;
	}

	public void setAree(String aree) {
		this.aree = aree;
	}

	public String getLivello() {
		return livello;
	}

	public void setLivello(String livello) {
		this.livello = livello;
	}

	public String getProfessori() {
		return professori;
	}

	public void setProfessori(String professori) {
		this.professori = professori;
	}

	public String getLingue() {
		return lingue;
	}

	public void setLingue(String lingue) {
		this.lingue = lingue;
	}
	
}