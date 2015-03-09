import java.util.*;

/**
 *  
 *
 *  Cette classe fait partie du logiciel Zork, un jeu d'aventure simple en mode
 *  texte. <p>
 *
 *  Cette classe permet d'associer les objets aux joueurs.
 *  Elle donne le poids maximum d'objets que le joueur peut transporter.
 *  Elle contient la methode ajouterObjet qui ajoute un objet de type ObjetZork 
 *  dans la liste d'objets que contient le joueur seulement si cela ne depasse
 *  pas la limite de poids.
 *
 * @author     Guillaume Lepage
 * @author     Mike Coroas
 */

public class Joueur
{
	public static final int POIDS_MAX = 15;

	private String nom;
	private List<ObjetZork> listeObjets;
	
	public Joueur(String nom)
	{
		this.nom = nom;
		listeObjets = new ArrayList<ObjetZork>();
	}
	
	public String getNom()
	{
		return nom;
	}
	
	public List<ObjetZork> getListeObjets()
	{
		return listeObjets;
	}
	
	public boolean ajouterObjet(ObjetZork o)
	{
		int poids = 0;
		for(ObjetZork objet : listeObjets)
			poids += objet.getPoids();
	
		if(o.getPoids() + poids < POIDS_MAX)
		{
			listeObjets.add(o);
			return true;
		}
		else
		{
			System.out.println("Plus de place dans l'inventaire");
			return false;
		}
	}
}
