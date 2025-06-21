package mx.edu.utez.redre.models.reportes.FilesAlmacenar;

import javax.persistence.Column;

public class File {
	@Column(name = "name", nullable = false, unique = true)
    private String name;
	@Column(name = "url", nullable = false)
	private String url;
	
	public File(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
