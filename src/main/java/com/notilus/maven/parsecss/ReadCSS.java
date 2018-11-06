package com.notilus.maven.parsecss;

import java.io.File;
import java.nio.charset.StandardCharsets;

import com.helger.css.ECSSVersion;
import com.helger.css.decl.CascadingStyleSheet;
import com.helger.css.reader.CSSReader;

/**
 * Service de lecture d'un CSS
 * @author Alexandre PRAT
 * @date 05/11/2018
 */
public class ReadCSS {
	/**
	 * Lecture d'un fichier CSS
	 */
	public static CascadingStyleSheet read(final File styleFile,final ECSSVersion cssVersion) {
		//Lecture du fichier css
		CascadingStyleSheet stylesheet;
		
		//Lecture du fichier
		stylesheet = CSSReader.readFromFile(styleFile,StandardCharsets.UTF_8,cssVersion);

		return stylesheet;
	}
}
