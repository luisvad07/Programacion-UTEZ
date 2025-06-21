package mx.edu.utez.redre.upload.services;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileServiceAPI {
    public String save(MultipartFile file, String fileName) throws Exception;

	String saveFinal(String fileName) throws Exception;
	
	public Resource load(String name) throws Exception;
	
	public void save(List<MultipartFile> files) throws Exception;
	
	public Stream<Path> loadAll() throws Exception;
}
