package com.notilus.maven.parsecss;

import java.io.File;
import java.nio.charset.StandardCharsets;

import com.helger.commons.io.file.SimpleFileIO;
import com.helger.css.ECSSVersion;
import com.helger.css.decl.CascadingStyleSheet;
import com.helger.css.writer.CSSWriter;

/**
 * Service d'ecriture d'un fichier CSS
 * @author Alexandre PRAT
 * @date 05/11/2018
 */
public class WriterCSS {
	/**
	 * Ecriture du fichier CSS
	 */
	public static void write(String path,CascadingStyleSheet stylesheet,ECSSVersion cssVersion) {
		File newFile;
		String cssCode;
		CSSWriter writer;

		//Initialisation du nouveau fichier
		newFile = new File(path);

		//Initialisation de l'objet WriterCSS
		writer = new CSSWriter(cssVersion,false);

		//Conversion des données en chaîne de caractères
		cssCode = writer.getCSSAsString(stylesheet);

		//Création du nouveau fichier
		SimpleFileIO.writeFile(newFile,cssCode,StandardCharsets.UTF_8);
	}
}
