package com.archivemaster.fedora;

public class Metadata {

	public static String META_DATA_CONTENT_TYPE = "application/sparql-update";

	public static String INSERT = "INSERT";

	public static String DELETE = "DELETE";


	public static String metadataAPIStringBuilder (String metadataName, String metadataValue, String insertOrDelete) {
		return "PREFIX ebucore: <http://www.ebu.ch/metadata/ontologies/ebucore/ebucore#> " + insertOrDelete + " { <> ebucore:" + metadataName + " \"" + metadataValue + "\" } WHERE { }";
	}
}
