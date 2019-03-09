package com.archivemaster.fedora;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fedora {
	public static String BASEURL = "http://localhost:8080/";

	public static String RESTURL = BASEURL + "rest/";

	public static String TOMBSTONEURL = "/fcr:tombstone";

	public static boolean apiResponseCheck (int responseCode) {
		System.out.println(responseCode);
		Pattern p = Pattern.compile("2[0-9]{2}+");
		Matcher m = p.matcher(String.valueOf(responseCode));
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}
}
