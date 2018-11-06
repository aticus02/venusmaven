package com.notilus.maven.parsecss;

import java.io.File;
import java.util.List;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.helger.css.ECSSVersion;
import com.helger.css.decl.CSSSelector;
import com.helger.css.decl.CSSStyleRule;
import com.helger.css.decl.CascadingStyleSheet;

/**
 * Plugin Maven de conversion de fichier CSS avec extraction de propriété
 * @author Alexandre PRAT
 * @date 05/11/2018
 */
@Mojo(name = "parsecss",defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class ParseCSS extends AbstractMojo {
	/**
	 * Localisation du fichier d'entrée
	 */
	@Parameter(defaultValue = "${project.build.directory}/venus/css/app.min.css",required = false)
	private File fileIn;

	/**
	 * Localisation du fichier cible
	 */
	@Parameter(defaultValue = "${project.build.directory}/venus/css/default_theme.css",required = false)
	private String pathOut;

	/**
	 * Version CSS
	 */
	@Parameter(defaultValue = "CSS30",required = false)
	private ECSSVersion cssVersion;

	/**
	 * Liste des critères à extraire
	 */
	@Parameter(required = true,alias = "criterias")
	private List<DeclarationCriteria> listeCriterias;

	/**
	 * Exécution du plugin
	 */
	public void execute() throws MojoExecutionException {
		CascadingStyleSheet stylesheet;
		CascadingStyleSheet newStylesheet;
		CSSStyleRule cssRule;

		//Récupération des données du fichier CSS
		if (fileIn.exists()) {
			//Lecture du fichier
			stylesheet = ReadCSS.read(fileIn,cssVersion);

			//Log
			getLog().info("ParseCSS - Lecture du fichier " + fileIn.getAbsolutePath());

			//Vérification de la liste des critères
			if (listeCriterias != null) {
				//Log du nombre de critères
				getLog().info("Nombre de critères : " + listeCriterias.size());

				//Log des critères définis
				listeCriterias.stream().forEach(d -> getLog().info(d.toString()));
			}
		} else {
			//Log d'erreur
			getLog().error("ParseCSS - Le fichier n'existe pas" + fileIn.getAbsolutePath());
			
			//Ne pas continuer
			return;
		}

		//Vérification des données
		if (stylesheet != null) {
			//Création d'une nouvelle feuille de style
			newStylesheet = new CascadingStyleSheet();

			//Parcours des données
			for (Rule rule : FilterCSS.filterByPropertyAndExpression(stylesheet,listeCriterias)) {
				//Création d'une nouvelle règle
				cssRule = new CSSStyleRule();

				//Parcours des déclarations CSS
				for (Declaration declaration : rule.getListeDeclarations()) {
					//Modification des données
					declaration.getCssDeclaration().setExpression(declaration.getCritere().getValue());

					//Récupération de la nouvelle déclaration
					cssRule.addDeclaration(declaration.getCssDeclaration());
				}

				//Parcours des selecteurs
				for (CSSSelector selector: rule.getRule().getAllSelectors())
					//Récupération des sélecteur
					cssRule.addSelector(selector);

				//Récupération des nouvelles règles
				newStylesheet.addRule(cssRule);
			}

			//Ecriture du nouveau fichier CSS
			WriterCSS.write(pathOut,newStylesheet,cssVersion);

			//Log de fin du traitement
			getLog().info("Traitement terminé. Fichier enregistré dans : " + pathOut);
		} else {
			//Avertissement
			getLog().warn("ParseCSS - Le fichier " + fileIn.getAbsolutePath() + " ne contient aucune donnée");
		}
	}
}
