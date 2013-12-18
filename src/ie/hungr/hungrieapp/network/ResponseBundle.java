package ie.hungr.hungrieapp.network;

public class ResponseBundle {

	private int responseStatus;
	private Object resource;
	private String errorMessage;

	public ResponseBundle() {
		resource = null;
		errorMessage = null;
	}

	public int getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}

	public Object getResource() {
		return resource;
	}

	public void setResource(Object resource) {
		this.resource = resource;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
