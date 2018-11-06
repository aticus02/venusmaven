package com.notilus.maven.parsecss;

import java.util.ArrayList;
import java.util.List;

import com.helger.css.decl.CascadingStyleSheet;
import com.helger.css.decl.visit.CSSVisitor;

/**
 * Service de filtrage des attributs CSS
 * @author Alexandre PRAT
 * @date 05/11/2018
 */
public class FilterCSS {
	/**
	 * Filtrage des attributs CSS en fonction du nom de la propriété
	 */
	public static List<Rule> filterByPropertyAndExpression(final CascadingStyleSheet css,final List<DeclarationCriteria> listeCriterias) {
		List<Rule> listeRules = new ArrayList<Rule>();

		//Récupération des propriétés
		CSSVisitor.visitCSS(css,new DeclarationCSSVisitor(listeRules,listeCriterias));

		return listeRules;
	}
}
