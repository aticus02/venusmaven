package com.notilus.maven.parsecss;

import com.helger.css.decl.CSSDeclaration;

/**
 * Classe représentant la valeur d'une règle
 * @author Alexandre PRAT
 * @date 25/10/2018
 */
public class Declaration {
	/** Données **/
	private CSSDeclaration cssDeclaration;
	private DeclarationCriteria critere;

	/**
	 * Accesseurs
	 */
	public CSSDeclaration getCssDeclaration() { return cssDeclaration; }
	public DeclarationCriteria getCritere() { return critere; }
	public void setCssDeclaration(CSSDeclaration cssDeclaration) { this.cssDeclaration = cssDeclaration; }
	public void setCritere(DeclarationCriteria critere) { this.critere = critere; }

	/**
	 * Constructeur
	 */
	public Declaration() {
		//Héritage
		super();
	}

	/**
	 * Constructeur par arguments
	 */
	public Declaration(CSSDeclaration cssDeclaration,DeclarationCriteria critere) {
		//Héritage
		this();
		
		//Définition des données
		setCssDeclaration(cssDeclaration);
		setCritere(critere);
	}
}
