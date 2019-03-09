package com.archivemaster.validation;

public class Validation {

	private boolean valid;

	private Object object;

	private String result;

	public Validation () {

	}

	public Validation (boolean valid, Object object, String result) {
		this.valid = valid;
		this.object = object;
		this.result = result;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
