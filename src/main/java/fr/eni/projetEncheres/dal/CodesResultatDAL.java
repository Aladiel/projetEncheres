package fr.eni.projetEncheres.dal;

/**
 * @author Daphné
 * 
 * Les codes disponibles sont entre 10000 et 19999
 */
public abstract class CodesResultatDAL {
	/**
	 * Echec général quand tentative d'ajouter un objet null
	 */
	public static final int INSERT_OBJET_NULL = 10000;

	/**
	 * Echec général quand erreur non gérée à l'insertion
	 */
	public static final int INSERT_OBJET_ECHEC = 10001;

	/**
	 * Echec de la selection des objets
	 */
	public static final int SELECT_OBJET_NULL = 10003;

}



