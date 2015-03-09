//
//  zorkfr.java
//  zorkfr
//
//  Created by Marc on 04/03/06.
//  Copyright (c) 2006 __MyCompanyName__. All rights reserved.
//
import java.util.*;

/**
 *  
 *
 *  Cette classe fait partie du logiciel Zork, un jeu d'aventure simple en mode
 *  texte. <p>
 *
 *  Cette classe contient la methode main et cree une instance de jeu.
 *
 * @author     Guillaume Lepage
 * @author     Mike Coroas
 */

public class zorkfr
{

	public static void main (String args[])
	{
		Jeu leJeu;
		
		leJeu = new Jeu();
		leJeu.jouer();
	}
}
