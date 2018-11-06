package com.notilus.maven.parsecss;

import java.util.List;

import com.helger.css.decl.CSSStyleRule;

/**
 * Classe représentant les informations d'une règle CSS
 * @author Alexandre PRAT
 * @date 25/10/2018
 */
public class Rule {
	/** Données **/
	private CSSStyleRule rule;
	private List<Declaration> listeDeclarations;

	/**
	 * Accesseurs
	 */
	public CSSStyleRule getRule() { return rule; }
	public List<Declaration> getListeDeclarations() { return listeDeclarations; }
	public void setRule(CSSStyleRule rule) { this.rule = rule; }
	public void setListeDeclarations(List<Declaration> listeDeclarations) { this.listeDeclarations = listeDeclarations; }

	/**
	 * Constructeur
	 */
	public Rule() {
		//Héritage
		super();
	}
	
	/**
	 * Constructeur par arguments
	 */
	public Rule(CSSStyleRule rule,List<Declaration> listeDeclarations) {
		//Héritage
		this();
		
		//Définition des données
		setRule(rule);
		setListeDeclarations(listeDeclarations);
	}
}