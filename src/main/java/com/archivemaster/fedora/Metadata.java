package com.archivemaster.fedora;

import java.util.regex.Pattern;

public class Metadata {

	public static String META_DATA_CONTENT_TYPE = "application/sparql-update";

	public static String INSERT = "INSERT";

	public static String DELETE = "DELETE";


	public static String metadataAPIStringBuilder (String metadataName, String metadataValue, String insertOrDelete) {
		return "PREFIX ebucore: <http://www.ebu.ch/metadata/ontologies/ebucore/ebucore#> " + insertOrDelete + " { <> ebucore:" + metadataName + " \"" + metadataValue + "\" } WHERE { }";
	}

	public static String metadataSearchStringBuilder (String metadataName) {
		return Pattern.quote("<span property=\"http://www.ebu.ch/metadata/ontologies/ebucore/ebucore#") + metadataName + "\">" + "(.*?)" + Pattern.quote("</span>");
	}
}
