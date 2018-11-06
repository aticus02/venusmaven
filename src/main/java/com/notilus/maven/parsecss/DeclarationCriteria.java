package com.notilus.maven.parsecss;

import com.helger.css.decl.CSSExpression;

/**
 * Classe représentant un critère de recherche pour la classe CSSVisitor
 * @author Alexandre PRAT
 * @date 25/10/2018
 */
public class DeclarationCriteria {
	/** Données **/
	private String propertyName;
	private String expressionValue;
	private CSSExpression value;

	/**
	 * Accesseurs
	 */
	public String getPropertyName() { return propertyName; }
	public String getExpressionValue() { return expressionValue; }
	public CSSExpression getValue() { return value; }
	public void setPropertyName(String propertyName) { this.propertyName = propertyName; }
	public void setExpressionValue(String expressionValue) { this.expressionValue = expressionValue; }
	public void setValue(CSSExpression value) { this.value = value; }

	/**
	 * Constructeur
	 */
	public DeclarationCriteria() {
		//Héritage
		super();
	}

	/**
	 * Constructeur par arguments
	 */
	public DeclarationCriteria(String propertyName,String expressionValue,String value) {
		//Héritage
		this();
		
		CSSExpression cssExpression = new CSSExpression();
		
		//Définition des propriétés
		setPropertyName(propertyName);
		setExpressionValue(expressionValue);
		
		//Initialisation de l'expression
		cssExpression.addTermSimple(value);

		//Définition de l'expression
		setValue(cssExpression); 
	}
	
	/**
	 * Récupération d'une description de l'objet
	 */
	public String toString() {
		//Formattage
		return "PropertyName " + (getPropertyName() != null ? getPropertyName() : "") + ", ExpressionValue " + (getExpressionValue() != null ? getExpressionValue() : "") + ", newValue " + (getValue() != null ? getValue() : "");
	}
}
