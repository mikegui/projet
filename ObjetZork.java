/**
 *  
 *
 *  Cette classe fait partie du logiciel Zork, un jeu d'aventure simple en mode
 *  texte. <p>
 *
 *  Cette classe liste les objets du jeu. Elle contient leurs poids, leurs noms
 *  et si ils sont transportables ou non.
 *
 * @author     Guillaume Lepage
 * @author     Mike Coroas
 */

public class ObjetZork
{
	public static final int PORTABLE = 0;
	public static final int VOITURE = 1;
	public static final int EPEE = 2;
	public static final int HAMBURGER = 3;
	public static final int HALTERE = 4;
	public static final int TABLETTE = 5;
	
	
	private String description;
	private int poids;
	private boolean transportable;

	public ObjetZork(int type)
	{
		switch(type)
		{
		case PORTABLE:
			description = "portable";
			poids = 1;
			transportable = true;
			break;
		case VOITURE:
			description = "voiture";
			poids = 1000;
			transportable = false;
			break;
		case EPEE:
			description = "épée";
			poids = 5;
			transportable = true;
			break;
		case HAMBURGER:
			description = "hamburger";
			poids = 2;
			transportable = true;
			break;
		case HALTERE:
			description = "haltere";
			poids = 7;
			transportable = true;
			break;
		case TABLETTE:
			description = "tablette";
			poids = 3;
			transportable = true;
			break;
		}
	}

	public String getDescription() {
		return description;
	}
	public int getPoids() {
		return poids;
	}
	public boolean getTransportable() {
		return transportable;
	}
	public boolean Equals(Object o)
	{
		if( !((o instanceof ObjetZork)))
		{
			return false;
		}
		
		ObjetZork objet = (ObjetZork) o;
		
		if( !(this.poids == objet.getPoids())) {
			return false;
		}
		if( !(this.description == objet.getDescription())) {
			return false;
		}
		if( !(this.transportable == objet.getTransportable())) {
			return false;
		}
		else {
			return true;
		}
	}
}
