/**
 *  Classe principale du jeu "Zork". <p>
 *
 *  Zork est un jeu d'aventure tres rudimentaire avec une interface en mode
 *  texte: les joueurs peuvent juste se déplacer parmi les differentes pieces.
 *  Ce jeu necessite vraiment d'etre enrichi pour devenir interessant!</p> <p>
 *
 *  Pour jouer a ce jeu, creer une instance de cette classe et appeler sa
 *  methode "jouer". </p> <p>
 *
 *  Cette classe cree et initialise des instances de toutes les autres classes:
 *  elle cree toutes les pieces, cree l'analyseur syntaxique et demarre le jeu.
 *  Elle se charge aussi d'executer les commandes que lui renvoie l'analyseur
 *  syntaxique.</p>
 *
 * @author     Guillaume Lepage
 * @author     Mike Coroas
 */

public class Jeu
{
	private AnalyseurSyntaxique analyseurSyntaxique;
	private Piece pieceCourante;
	private Piece piecePrecedente = null;
	
	private Joueur joueur = new Joueur("Guillaume & Mike");


	/**
	 *  Crée le jeu et initialise la carte du jeu (i.e. les pièces).
	 */
	public Jeu()
	{
		creerPieces();
		analyseurSyntaxique = new AnalyseurSyntaxique();
	}


	/**
	 *  Crée toutes les pieces et relie leurs sorties les unes aux autres.
	 */
	public void creerPieces()
	{
		Piece dehors;
		Piece salleTD;
		Piece taverne;
		Piece batimentC;
		Piece crous;
		Piece salleDeMuscu;

		// création des pieces
		dehors = new Piece(Piece.DEHORS);
		salleTD = new Piece(Piece.SALLE_TD);
		taverne = new Piece(Piece.TAVERNE);
		batimentC = new Piece(Piece.BATIMENT_C);
		crous = new Piece(Piece.CROUS);
		salleDeMuscu = new Piece(Piece.SALLEDEMUSCU);

		// initialise les sorties des pieces
		dehors.setSorties(salleDeMuscu, salleTD, batimentC, taverne);
		salleTD.setSorties(null, null, null, dehors);
		taverne.setSorties(null, dehors, null, null);
		batimentC.setSorties(dehors, crous, null, null);
		crous.setSorties(null, null, null, batimentC);
		salleDeMuscu.setSorties(null, null, null, dehors);

		// le jeu commence dehors
		pieceCourante = dehors;
	}


	/**
	 *  Pour lancer le jeu. Boucle jusqu'a la fin du jeu.
	 */
	public void jouer() {
		afficherMsgBienvennue();

		// Entrée dans la boucle principale du jeu. Cette boucle lit
		// et exécute les commandes entrées par l'utilisateur, jusqu'a
		// ce que la commande choisie soit la commande "quitter"
		boolean termine = false;
		while (!termine) {
			Commande commande = analyseurSyntaxique.getCommande();
			termine = traiterCommande(commande);
		}
		System.out.println("Merci d'avoir joué.  Au revoir.");
	}


	/**
	 *  Affiche le message d'accueil pour le joueur.
	 */
	public void afficherMsgBienvennue()
	{
		System.out.println();
		System.out.println("Bienvennue dans le monde de Zork !");
		System.out.println("Zork est un nouveau jeu d'aventure, terriblement enuyeux.");
		System.out.println("Tapez 'aide' si vous avez besoin d'aide.");
		System.out.println();
		System.out.println(pieceCourante.descriptionLongue());
	}


	/**
	 *  Exécute la commande spécifiée. Si cette commande termine le jeu, la valeur
	 *  true est renvoyée, sinon false est renvoyée
	 *
	 * @param  commande  La commande a exécuter
	 * @return           true si cette commande termine le jeu ; false sinon.
	 */
	public boolean traiterCommande(Commande commande) {
		if (commande.estInconnue()) {
			System.out.println("Je ne comprends pas ce que vous voulez...");
			return false;
		}

		String motCommande = commande.getMotCommande();
		if (motCommande.equals("aide"))
			afficherAide();
			
		else if (motCommande.equals("prendre"))
			prendreObjet(commande);
			
		else if (motCommande.equals("parler"))
			parler(commande);
			
		else if (motCommande.equals("retour"))
			retour(commande);
			
		else if (motCommande.equals("donner"))
			return donner(commande);

		else if (motCommande.equals("jeter"))
			jeter(commande);
			
		else if (motCommande.equals("aller"))
			deplacerVersAutrePiece(commande);
			
		else if (motCommande.equals("quitter"))
		{
			if (commande.aSecondMot())
				System.out.println("Quitter quoi ?");
				
			else
				return true;
		}
		return false;
	}


	// implementations des commandes utilisateur:

	/**
	 *  Affichage de l'aide. Affiche notament la liste des commandes utilisables.
	 */
	public void afficherAide() {
		System.out.println("Vous etes perdu. Vous etes seul. Vous errez");
		System.out.println("sur le campus de l'Université Paris 13.");
		System.out.println();
		System.out.println("Les commandes reconnues sont:");
		analyseurSyntaxique.afficherToutesLesCommandes();
	}


	/**
	 *  Tente d'aller dans la direction spécifiée par la commande. Si la piece
	 *  courante possède une sortie dans cette direction, la piece correspondant a
	 *  cette sortie devient la piece courante, dans les autres cas affiche un
	 *  message d'erreur.
	 *
	 * @param  commande  Commande dont le second mot spécifie la direction a suivre
	 */
	public void deplacerVersAutrePiece(Commande commande) {
		if (!commande.aSecondMot()) {
			// si la commande ne contient pas de second mot, nous ne
			// savons pas ou aller..
			System.out.println("Aller où ?");
			return;
		}

		String direction = commande.getSecondMot();

		// Tentative d'aller dans la direction indiquée.
		Piece pieceSuivante = pieceCourante.pieceSuivante(direction);

		if (pieceSuivante == null) {
			System.out.println("Il n'y a pas de porte dans cette direction!");
		} else {
			piecePrecedente = pieceCourante;
			pieceCourante = pieceSuivante;
			System.out.println(pieceCourante.descriptionLongue());
		}
	}
	
	/**
	 * Prend l'objet si transportable.
	 * Supprime l'objet de la piece une fois pris.
	 */
	public void prendreObjet(Commande commande) {
		if (!commande.aSecondMot()) {
			System.out.println("Prendre quoi ?");
			return;
		}
		
		ObjetZork objetTampon = null;
		String nom = commande.getSecondMot();
		
		for(ObjetZork objet : pieceCourante.getObjets())
			if(objet.getDescription().equals(nom))
				objetTampon = objet;
		
		if(objetTampon != null)
		{
			if(objetTampon.getTransportable() == false) {
				System.out.println("L'objet n'est pas transportable !");
			}
			else {
				if(joueur.ajouterObjet(objetTampon))
				{
					pieceCourante.getObjets().remove(objetTampon);
					System.out.println("Vous avez pris l'objet");
				}
			}
		}
		else
			System.out.println("Il n'y a pas d'objet nommé " + nom + ".");
	}
	
	/**
	 * Donne l'objet portable seulement si le joueur respecte les conditions suivantes :
	 * etre dans la taverne
	 * avoir l'objet portable
	 * ecrire le mot "portable" en second mot
	 */
	public boolean donner(Commande commande)
	{
		String nomObj = commande.getSecondMot();
		int i = 0;
		
		if (!commande.aSecondMot()) {
			System.out.println("Donner quoi ?");
			return false;
		}
		
		if(pieceCourante.getDescription().equals("taverne") && nomObj.equals("portable"))
		{
			boolean portable = false;
			for(ObjetZork objet : joueur.getListeObjets())
				if(objet.getDescription().equals("portable"))
					portable = true;
			
			if(portable)
			{
				System.out.println("Vous avez donné le portable à Anis :D");
				System.out.println("Quête terminée");
				return true;
			}
			System.out.println("Vous n'avez pas le portable d'Anis");
			return false;
		}
		else
			System.out.println("Vous ne pouvez pas donner l'objet \"" + nomObj + "\".");
		return false;
	}
	
	/**
	 * Cette methode permet de retourner dans la piece ou l'on se trouvait 
	 * precedemment.
	 */
	public void retour(Commande commande) 
	{
		// On echange les pieces
		if(piecePrecedente == null)
		{
			System.out.println("Il n'y a pas de pièce précédente.");
		}
		else
		{
			Piece pieceTampon = pieceCourante;
			pieceCourante = piecePrecedente;
			piecePrecedente = pieceTampon;
			System.out.println(pieceCourante.descriptionLongue());
		}
	}
	public void jeter(Commande commande) 
	{
		if (!commande.aSecondMot())
		{
			System.out.println("Jeter quoi ?");
			return;
		}
		
		String nom = commande.getSecondMot();
		ObjetZork obj = null;
		boolean supp = false;
		
		for(ObjetZork objet : joueur.getListeObjets())
		{
			if(objet.getDescription().equals(nom))
			{
				supp = true;
				obj = objet;
			}
		}
		if(!supp) 
			System.out.println("Il n'y a pas d'objet nommé " + nom + ".");
		else
		{
			joueur.getListeObjets().remove(obj);
			System.out.println("Objet \"" + nom + "\" jeté.");
		}
	}
	
	public void parler(Commande commande)
	{
		if (!commande.aSecondMot())
		{
			System.out.println("Parler à qui ?");
			return;
		}
		
		String nom = commande.getSecondMot();
		
		for(Personnage p : pieceCourante.getPersonnages())
		{
			if(p.getNom().equals(nom))
				p.getListener().parler();
		}
	}
}


