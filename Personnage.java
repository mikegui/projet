/**
 *  Les personnages dans un jeu d'aventure. <p>
 *
 *  Cette classe fait partie du logiciel Zork, un jeu d'aventure simple en mode
 *  texte.</p> <p>
 *
 *  Cette classe contient les differents noms des personnages.
 *  Elle utilise la classe OnSpeakListener pour faire "parler" les personnages
 *  dans le jeu.
 *
 * @author     Guillaume Lepage
 * @author     Mike Coroas
 */

public class Personnage
{
	public static final int AYSE = 0;
	public static final int ANIS = 1;
	public static final int JEREMY = 2;
	public static final int CHEIKH = 3;
	public static final int GABRIEL = 4;
	
	private String nom;
	private OnSpeakListener listener;

	public Personnage(String nom, OnSpeakListener listener)
	{
		this.nom = nom;
		this.listener = listener;
	}
	
	
	public String getNom()
	{
		return nom;
	}
	
	public OnSpeakListener getListener()
	{
		return listener;
	}
}
