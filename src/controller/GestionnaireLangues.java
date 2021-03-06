package controller;

import java.util.Locale;
import java.util.ResourceBundle;
 
/**
 * Gestionnaire permettant le multilinguisme
 * @author gregoryangeloz & Yann LERJEN
 */
public class GestionnaireLangues {
        /** Pour singleton **/
        private static GestionnaireLangues gestionnaireLangues = null;
        
	public static final Locale ENGLISH_LANGUAGE=new Locale("en","EN");				
	public static final Locale FRENCH_LANGUAGE=new Locale("fr","FR");
 
	private static final String[] languagesList=new String[] {
                                                                    "Francais",
                                                                    "English",                                                                    
                                                                 };
        /**
         * Langue chargée par défaut (ANGLAIS)
         */
        private static Locale defaultLanguage=ENGLISH_LANGUAGE;
	private static Locale currentLanguage;
 
	private static ResourceBundle messages;
 
	private static String currentLanguageString;
 
	/**
	 * Constructeur privé pour n'avoir qu'une instance de GestionnaireLangue possible via Singleton
	 **/	
	private GestionnaireLangues() {

		currentLanguage=defaultLanguage;
 
		messages=ResourceBundle.getBundle("ressources.TextesLangues",currentLanguage);                                 
		currentLanguageString = "English";           
	}
        
        /**
         * Permet d'accéder au gestionnaireLangues unique
         * Pattern Singleton
         * @return une instance unique du GestionnaireLangues
         */
        public static GestionnaireLangues rendGestionnaireLangues() {
            if(gestionnaireLangues == null)
                GestionnaireLangues.gestionnaireLangues = new GestionnaireLangues();
            return GestionnaireLangues.gestionnaireLangues;
        }
        
	/**
	 * Retourne la liste de langues supportées
	 * @returns a Un tableau de langues
	 **/
	public static String[] getLanguagesList()
	{
		return languagesList;
	}
 
	/**
	 * Retourne un champ du fichier properties courant
         * @param text le champ de texte à retourner
	 **/
	public String getLocalizedText(String text)
	{		
		return messages.getString(text);
	}
 
	/**
	 * Permet de changer dynamiquement de langue
	 * @param newLanguage La nouvelle langue
	 **/
	public static void setCurrentLanguage(String newLanguage)
	{
		boolean change=false;
		if(newLanguage.equalsIgnoreCase("Francais"))
		{
			currentLanguage=FRENCH_LANGUAGE;
			currentLanguageString="Francais";
			change=true;
		}
		else if (newLanguage.equalsIgnoreCase("English"))
		{
			currentLanguage=ENGLISH_LANGUAGE;
			currentLanguageString="English";
			change=true;
		}
 
		if(change)
		{
			messages=ResourceBundle.getBundle("ressources.TextesLangues",currentLanguage);
		}
	}
 
	/**
	 *Retourne la langue actuelle au format Locale
	 *@return la langue actuelle
	 **/
	public static Locale getCurrentLanguage()
	{
		return currentLanguage;
	}
        
        /**
         * Retourne la langue actuelle au format String
         * @return la langue actuelle
         */
	public static String getCurrentLanguageString()
	{
		return currentLanguageString;
	}
 
}