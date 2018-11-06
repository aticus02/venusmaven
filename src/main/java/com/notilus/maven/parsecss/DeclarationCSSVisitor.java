package com.notilus.maven.parsecss;

import java.util.ArrayList;
import java.util.List;

import com.helger.css.decl.CSSDeclaration;
import com.helger.css.decl.CSSStyleRule;
import com.helger.css.decl.shorthand.CSSShortHandDescriptor;
import com.helger.css.decl.shorthand.CSSShortHandRegistry;
import com.helger.css.decl.visit.DefaultCSSVisitor;
import com.helger.css.property.ECSSProperty;

/**
 * Classe permettant la récupération de valeurs CSS
 * @author Alexandre PRAT
 * @date 24/10/2018
 */
public class DeclarationCSSVisitor extends DefaultCSSVisitor {
	/** Données **/
	private CSSStyleRule currentRule;
	private List<Declaration> listeDeclarations;
	private List<Rule> listeRules;
	private List<DeclarationCriteria> listeCriterias;
	
	/**
	 * Accesseurs
	 */
	public CSSStyleRule getCurrentRule() { return currentRule; }
	public List<Declaration> getListeDeclarations() { return listeDeclarations; }
	public List<Rule> getListeRules() { return listeRules; }
	public List<DeclarationCriteria> getListeCriterias() { return listeCriterias; }
	public void setCurrentRule(CSSStyleRule currentRule) { this.currentRule = currentRule; }
	public void setListeDeclarations(List<Declaration> listeDeclarations) { this.listeDeclarations = listeDeclarations; }
	public void setListeRules(List<Rule> listeRules) { this.listeRules = listeRules; }
	public void setListeCriterias(List<DeclarationCriteria> listeCriterias) { this.listeCriterias = listeCriterias; }
	
	/**
	 * Constructeur
	 */
	public DeclarationCSSVisitor(List<Rule> listeRules,List<DeclarationCriteria> listeCriterias) {
		//Héritage
		super();
		
		//Définition des données
		setListeRules(listeRules);
		setListeCriterias(listeCriterias);
	}

	/**
	 * Lecture d'une déclaration
	 */
	@Override
	public void onDeclaration(CSSDeclaration declaration) {
		ECSSProperty eCSSProperty;
		CSSShortHandDescriptor cssDescriptor;
		List<CSSDeclaration> listeDeclarations;
		CSSDeclaration shortHandDeclaration;

		//Parcours des crit1eres
		for (DeclarationCriteria criteria : getListeCriterias()) {
			//Vérification de l'attribut CSS
			if ((criteria.getPropertyName() != null || criteria.getExpressionValue() != null) 
					&& (criteria.getPropertyName() == null || declaration.getProperty().equalsIgnoreCase(criteria.getPropertyName())) 
					&& (criteria.getExpressionValue() == null || declaration.getExpressionAsCSSString().toLowerCase().contains(criteria.getExpressionValue().toLowerCase()))) {
				//Récupération de la propriété CSS
				eCSSProperty = ECSSProperty.getFromNameOrNull(declaration.getProperty());

				//Vérification de la propriété CSS
				if (CSSShortHandRegistry.isShortHandProperty(eCSSProperty)) {
					//Récupération du descripteur
					cssDescriptor = CSSShortHandRegistry.getShortHandDescriptor(eCSSProperty);

					//Découpage des propriétés
					listeDeclarations = cssDescriptor.getSplitIntoPieces(declaration);

					//Récupération de la déclaration contenant la couleur
					shortHandDeclaration = listeDeclarations.stream().filter(d -> d.getExpressionAsCSSString().equalsIgnoreCase(criteria.getExpressionValue())).findFirst().orElse(null);

					//Récupération de la déclaration
					getListeDeclarations().add(new Declaration(shortHandDeclaration,criteria));
				} else
					//Récupération de la déclaration
					getListeDeclarations().add(new Declaration(declaration,criteria));
			}
		}

		//Héritage
		super.onDeclaration(declaration);
	}

	/**
	 * Lecture d'un style
	 */
	@Override
	public void onBeginStyleRule(CSSStyleRule styleRule) {
		//Récupération de la règle
		setCurrentRule(styleRule);

		//Création d'une nouvelle liste
		setListeDeclarations(new ArrayList<>());

		//Héritage
		super.onBeginStyleRule(styleRule);
	}

	/**
	 * Fin de lecture d'un style
	 */
	@Override
	public void onEndStyleRule(CSSStyleRule styleRule) {
		//Vérification de la liste des déclarations
		if (!getListeDeclarations().isEmpty())
			//Récupération des déclarations de la règle
			getListeRules().add(new Rule(getCurrentRule(),getListeDeclarations()));

		//Héritage
		super.onEndStyleRule(styleRule);
	}
}
