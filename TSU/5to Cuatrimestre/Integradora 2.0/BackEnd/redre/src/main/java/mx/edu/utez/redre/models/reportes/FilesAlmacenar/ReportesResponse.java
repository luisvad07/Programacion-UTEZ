package mx.edu.utez.redre.models.reportes.FilesAlmacenar;

public class ReportesResponse {
    private String message;

	public String getMessage() {
		return message;
	}

	public ReportesResponse(String message) {
		super();
		this.message = message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
