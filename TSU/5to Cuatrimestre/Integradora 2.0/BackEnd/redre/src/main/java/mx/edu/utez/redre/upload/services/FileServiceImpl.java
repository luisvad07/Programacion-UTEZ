package mx.edu.utez.redre.upload.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileServiceAPI {

	private final Path rootFolder = Paths.get("uploads");

	@Override
	public String save(MultipartFile file, String fileName) throws Exception{
		String url = String.valueOf(fileName);
		Files.deleteIfExists(this.rootFolder.resolve(fileName));
		Files.copy(file.getInputStream(), this.rootFolder.resolve(fileName));

		return url;
	}

	@Override
	public String saveFinal(String fileName) throws Exception{
		Files.copy(this.rootFolder.resolve(fileName + ".pdf"), this.rootFolder.resolve(fileName + "Final.pdf"));
		Files.deleteIfExists(this.rootFolder.resolve(fileName + ".pdf"));
		return fileName + "Final.pdf";
	}

	@Override
	public Resource load(String name) throws Exception {
		Path file = rootFolder.resolve(name);
		Resource resource = new UrlResource(file.toUri());
		return resource;
	}

	@Override
	public void save(List<MultipartFile> files) throws Exception {
		for (MultipartFile file : files) {
			this.save(file, null);
		}
	}

	@Override
	public Stream<Path> loadAll() throws Exception {
		return Files.walk(rootFolder, 1).filter(path -> !path.equals(rootFolder)).map(rootFolder::relativize);
	}

}