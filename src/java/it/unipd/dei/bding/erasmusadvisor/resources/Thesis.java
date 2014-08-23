package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ArgomentoTesiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ProfessoreBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneTesiBean;





import java.util.ArrayList;
import java.util.List;


/**
 * Contains all the details of a Thesis needed by an end user.
 * 
 * @author Nicola, Luca
 *
 */

public class Thesis 
{
        private ArgomentoTesiBean arg;
        private List<ValutazioneTesiBean> listaValutazioni;
        private List<ProfessoreBean> professori;
        private List<LinguaBean> lingue;
        private List<AreaBean> aree;

        /**
         * Initialize the Thesis.
         * 
         * @param arg the Thesis
         * @param listaValutazioni the list of the thesis' evaluations 
         * @param professori the list of teachers that manage the thesis
         * @param lingue the list of languages in which the thesis could be done
         * @param aree the list of areas that the thesis extends
         */
        public Thesis(ArgomentoTesiBean arg, List<ValutazioneTesiBean> listaValutazioni,
                        List<ProfessoreBean> professori, List<LinguaBean> lingue, List<AreaBean> aree) 
        {
                this.arg = arg;
                this.listaValutazioni = listaValutazioni;
                this.professori = professori;
                this.lingue = lingue;
                this.aree = aree;
        }
        
        /**
         * @param arg
         * @param listaValutazioni
         */
        public Thesis(ArgomentoTesiBean arg, List<ValutazioneTesiBean> listaValutazioni) 
        {
                this.arg = arg;
                this.listaValutazioni = listaValutazioni;
        }
        
        /**
         * @param arg
         */
        public Thesis(ArgomentoTesiBean arg) 
        {
                this.arg = arg;
                listaValutazioni = null;
        }
        
        /**
         * Get the teachers that manage the thesis
         * @return a list of beans representing the teachers
         */
        public List<ProfessoreBean> getProfessori()
        {
                return professori;
        }
        
        /**
         * Set the teachers that manage the thesis
         * @param obj
         */
        public void setProfessori(List<ProfessoreBean> obj)
        {
                professori = obj;
        }
        
        /**
         * Get the thesis' languages
         * @return the list of languages
         */
        public List<LinguaBean> getLingue()
        {
                return lingue;
        }
        
        /**
         * Set the thesis' languages
         * @param obj a list of beans representing the languages
         */
        public void setLingue(List<LinguaBean> obj)
        {
                lingue = obj;
        }
        
        /**
         * Get the thesis' areas
         * @return a list of areas
         */
        public List<AreaBean> getAree()
        {
                return aree;
        }
        
        /**
         * Set the thesis' areas
         * @param obj a list of beans representing the areas
         */
        public void setAree(List<AreaBean> obj)
        {
                aree = obj;
        }

        /**
         * Returns Thesis.
         *
         * @return a bean representing the Thesis
         */
        public ArgomentoTesiBean getArgomentoTesi()
        {
                return arg;
        }

        /**
         * Sets the Thesis
         *
         * @param arg The thesis
         */
        public void setArgomentoTesi(ArgomentoTesiBean arg) 
        {
                this.arg = arg;
        }

        /**
         * Returns the thesis' evaluations.
         *
         * @return a list of beans representing the evaluations
         */
        public List<ValutazioneTesiBean> getListaValutazioni() 
        {
                return listaValutazioni;
        }

        /**
         * Sets the thesis' evaluations
         *
         * @param listaValutazioni The value to set
         */
        public void setListaValutazioni(
                        ArrayList<ValutazioneTesiBean> listaValutazioni) 
        {
                this.listaValutazioni = listaValutazioni;
        }
        
}