import java.util.*;

/**
 *  Une piece dans un jeu d'aventure. <p>
 *
 *  Cette classe fait partie du logiciel Zork, un jeu d'aventure simple en mode
 *  texte.</p> <p>
 *
 *  Une "Piece" represente un des lieux dans lesquels se deroule l'action du
 *  jeu. Elle est reliee a au plus quatre autres "Piece" par des sorties. Les
 *  sorties sont etiquettees "nord", "est", "sud", "ouest". Pour chaque
 *  direction, la "Piece" possede une reference sur la piece voisine ou null
 *  s'il n'y a pas de sortie dans cette direction.</p>
 *
 * @author     Guillaume Lepage
 * @author     Mike Coroas
 */

public class Piece
{
	public static final int DEHORS = 0;
	public static final int SALLE_TD = 1;
	public static final int TAVERNE	= 2;
	public static final int BATIMENT_C = 3;
	public static final int CROUS = 4;
	public static final int SALLEDEMUSCU = 5;

	private String description;

	// mémorise les sorties de cette piece.
	private Map<String, Piece> sorties= new HashMap<String, Piece>();;

	private List<ObjetZork> listeObjets = new ArrayList<ObjetZork>();
	
	private List<Personnage> listePersonnages = new ArrayList<Personnage>();

	
	/**
	 *  Cette methode indique quel objet et quel personnage se trouve dans
	 *  quelle piece. Et egalement les dialogues des personnages.
	 */
	public Piece(int type)
	{
	
		switch(type)
		{
		case DEHORS:
			description = "devant le batiment C";
			listeObjets.add(new ObjetZork(ObjetZork.VOITURE));
			break;
		case SALLE_TD:
			listeObjets.add(new ObjetZork(ObjetZork.TABLETTE));
			listeObjets.add(new ObjetZork(ObjetZork.PORTABLE));
			description = "salle td";
			listePersonnages.add(new Personnage("Cheikh", new OnSpeakListener()
			{
				@Override
				public void parler()
				{
					System.out.println("Hey, j'ai trouvé un portable dans la salle.");
					System.out.println("On dirait celui d'Anis !");
				}
			}));
			break;
		case SALLEDEMUSCU:
			listeObjets.add(new ObjetZork(ObjetZork.HALTERE));
			description = "salle de muscu";
			listePersonnages.add(new Personnage("Gabriel", new OnSpeakListener()
			{
				@Override
				public void parler()
				{
					System.out.println("Qu'est ce que tu fais là ?");
					System.out.println("Je pensais que tu aidais Anis !");
					System.out.println("Bon, profitons en pour faire quelques tractions ;)");
				}
			}));
			break;
		case TAVERNE:
			listeObjets.add(new ObjetZork(ObjetZork.EPEE));
			description = "taverne";
			listePersonnages.add(new Personnage("Anis", new OnSpeakListener()
			{
				@Override
				public void parler()
				{
					System.out.println("Je suis Anis, peux-tu m'aider ?");
					System.out.println("J'ai perdu mon portable, je ne pourrais pas jouer à Flappy Bird !");
					System.out.println("Trouve-le dans la fac et rapporte-le moi !");
					System.out.println("Tu seras récompensé comme il se doit...");
				}
			}));
			break;
		case BATIMENT_C:
			description = "batiment c";
			listePersonnages.add(new Personnage("Jeremy", new OnSpeakListener()
			{
				@Override
				public void parler()
				{
					System.out.println("Cheikh te cherche, il a quelque chose pour toi !");
					System.out.println("Il se trouve dans la salle de TD !");
				}
			}));
			break;
		case CROUS:
			description = "crous";
			listeObjets.add(new ObjetZork(ObjetZork.HAMBURGER));
			listePersonnages.add(new Personnage("Ayse", new OnSpeakListener()
			{
				@Override
				public void parler()
				{
					System.out.println("Bonjour je m'appelle Ayse ;D");
					System.out.println("Tu m'as l'air un peu maigrichon,");
					System.out.println("va à la salle de muscu ;D");
				}
			}));
			break;
		}
	}

	/**
	 *  Initialise une piece décrite par la chaine de caractères spécifiée.
	 *  Initialement, cette piece ne possède aucune sortie. La description fournie
	 *  est une courte phrase comme "la bibliothèque" ou "la salle de TP".
	 *
	 * @param  description  Description de la piece.
	 */
	


	/**
	 *  Définie les sorties de cette piece. A chaque direction correspond ou bien
	 *  une piece ou bien la valeur null signifiant qu'il n'y a pas de sortie dans
	 *  cette direction.
	 *
	 * @param  nord   La sortie nord
	 * @param  est    La sortie est
	 * @param  sud    La sortie sud
	 * @param  ouest  La sortie ouest
	 */
	public void setSorties(Piece nord, Piece est, Piece sud, Piece ouest)
	{
		if (nord != null)
			sorties.put("nord", nord);
		if (est != null)
			sorties.put("est", est);
		if (sud != null)
			sorties.put("sud", sud);
		if (ouest != null)
			sorties.put("ouest", ouest);
	}


	/**
	 *  Renvoie la description de cette piece (i.e. la description spécifiée lors
	 *  de la création de cette instance).
	 *
	 * @return    Description de cette piece
	 */
	public String descriptionCourte()
	{
		String desc = description;
		if(listeObjets.size() > 0)
		{
			desc += "Les objets présents dans la pièce sont :";
			for(int i=0 ; i<listeObjets.size() ; i++)
				desc += listeObjets.get(i).getDescription() + "\n";
		}
		
		return desc;
	}


	/**
	 *  Renvoie une description de cette piece mentionant ses sorties et
	 *  directement formatée pour affichage, de la forme: <pre>
	 *  Vous etes dans la bibliothèque.
	 *  Sorties: nord ouest</pre> Cette description utilise la chaine de caractères
	 *  renvoyée par la méthode descriptionSorties pour décrire les sorties de
	 *  cette piece.
	 *
	 * @return    Description affichable de cette piece
	 */
	public String descriptionLongue()
	{
		String desc = "Vous etes dans " + description + ".\n" + descriptionSorties() + "\n";
		
		if(listeObjets.size() > 0)
		{
			desc += "Les objets présents dans la pièce sont :";
			for(ObjetZork o : listeObjets)
				desc += o.getDescription() + "\n";
				
		}
			
		if(listePersonnages.size() > 0) {
				
			desc += "Les personnages présents dans la pièce sont :";
			for(Personnage p : listePersonnages)
				desc += p.getNom() + "\n";
		}
		
		
		return desc;
	}
	

	/**
	 *  Renvoie une description des sorties de cette piece, de la forme: <pre>
	 *  Sorties: nord ouest</pre> Cette description est utilisée dans la
	 *  description longue d'une piece.
	 *
	 * @return    Une description des sorties de cette pièce.
	 */
	public String descriptionSorties()
	{
		String resulString = "Sorties:";
		Set keys = sorties.keySet();

		for (Iterator iter = keys.iterator(); iter.hasNext(); )
			resulString += " " + iter.next();

		return resulString;
	}


	/**
	 *  Renvoie la piece atteinte lorsque l'on se déplace a partir de cette piece
	 *  dans la direction spécifiée. Si cette piece ne possède aucune sortie dans cette direction,
	 *  renvoie null.
	 *
	 * @param  direction  La direction dans laquelle on souhaite se déplacer
	 * @return            Piece atteinte lorsque l'on se déplace dans la direction
	 *      spécifiée ou null.
	 */
	public Piece pieceSuivante(String direction)
	{
		return sorties.get(direction);
	}
	
	public List<ObjetZork> getObjets()
	{
		return listeObjets;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public List<Personnage> getPersonnages()
	{
		return listePersonnages;
	}
}


