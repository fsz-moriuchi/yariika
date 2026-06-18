package model;

public class PasswordEdit {
	private boolean success;
	private String errorMsg;
	
	public PasswordEdit(boolean success,String errorMsg) {
		this.success = success;
		this.errorMsg = errorMsg;
		
	}
	public boolean isSuccess() {
		return success;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
}
