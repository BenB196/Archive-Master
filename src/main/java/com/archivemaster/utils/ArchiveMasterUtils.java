package com.archivemaster.utils;

public class ArchiveMasterUtils {

	/**
	 * isEmptyString - check if string passed is null or empty and returns bool
	 * @param string - string you want to check
	 * @return
	 */
	public static boolean isEmptyString(String string) {
		return (string == null || string.isEmpty());
	}
}
