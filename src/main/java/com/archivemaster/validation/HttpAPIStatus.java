package com.archivemaster.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpAPIStatus {

	private boolean success;

	private int responseCode;

	private String responseMessage;

	public HttpAPIStatus () {

	}

	public HttpAPIStatus (boolean success, int responseCode, String responseMessage) {
		this.success = success;
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public static boolean getSuccessFromCode(int responseCode) {
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
