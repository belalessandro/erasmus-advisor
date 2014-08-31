package it.unipd.dei.bding.erasmusadvisor.resources;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ArgomentoTesiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ProfessoreBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneTesiBean;





import java.util.ArrayList;
import java.util.List;


/**
 * Contains all the details of a Thesis displayed to an end user.
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
         * Initializes the Thesis.
         * 
         * @param arg The Thesis.
         * @param listaValutazioni The list of the thesis' evaluations.
         * @param professori The list of teachers that manage the thesis.
         * @param lingue The list of languages in which the thesis could be done.
         * @param aree The list of areas that the thesis extends.
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
         * Initializes the thesis.
         * @param arg The thesis.
         * @param listaValutazioni A list of evaluations.
         */
        public Thesis(ArgomentoTesiBean arg, List<ValutazioneTesiBean> listaValutazioni) 
        {
                this.arg = arg;
                this.listaValutazioni = listaValutazioni;
        }
        
        /**
         * Initializes the thesis.
         * @param arg The thesis.
         */
        public Thesis(ArgomentoTesiBean arg) 
        {
                this.arg = arg;
                listaValutazioni = null;
        }
        
        /**
         * Returns the teachers that manage the thesis.
         * @return A list of beans representing the teachers.
         */
        public List<ProfessoreBean> getProfessori()
        {
                return professori;
        }
        
        /**
         * Sets the teachers that manage the thesis.
         * @param obj A list of teachers.
         */
        public void setProfessori(List<ProfessoreBean> obj)
        {
                professori = obj;
        }
        
        /**
         * Returns the thesis' languages.
         * @return The list of languages.
         */
        public List<LinguaBean> getLingue()
        {
                return lingue;
        }
        
        /**
         * Sets the thesis' languages.
         * @param obj A list of beans representing the languages.
         */
        public void setLingue(List<LinguaBean> obj)
        {
                lingue = obj;
        }
        
        /**
         * Returns the thesis' areas.
         * @return A list of areas.
         */
        public List<AreaBean> getAree()
        {
                return aree;
        }
        
        /**
         * Sets the thesis' areas.
         * @param obj A list of beans representing the areas.
         */
        public void setAree(List<AreaBean> obj)
        {
                aree = obj;
        }

        /**
         * Returns Thesis.
         *
         * @return A bean representing the Thesis.
         */
        public ArgomentoTesiBean getArgomentoTesi()
        {
                return arg;
        }

        /**
         * Sets the Thesis.
         *
         * @param arg The thesis.
         */
        public void setArgomentoTesi(ArgomentoTesiBean arg) 
        {
                this.arg = arg;
        }

        /**
         * Returns the thesis' evaluations.
         *
         * @return A list of beans representing the evaluations.
         */
        public List<ValutazioneTesiBean> getListaValutazioni() 
        {
                return listaValutazioni;
        }

        /**
         * Sets the thesis' evaluations.
         *
         * @param listaValutazioni The value to set.
         */
        public void setListaValutazioni(
                        ArrayList<ValutazioneTesiBean> listaValutazioni) 
        {
                this.listaValutazioni = listaValutazioni;
        }
        
        
}