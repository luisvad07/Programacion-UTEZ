package mx.edu.utez.redre.upload.controller;

import java.util.List;
import java.util.stream.Collectors;

import mx.edu.utez.redre.models.estudiantes.Estudiantes;
import mx.edu.utez.redre.models.estudiantes.EstudiantesRespository;
import mx.edu.utez.redre.models.mensajes.services.MAServices;
import mx.edu.utez.redre.models.mensajes.services.MDServices;
import mx.edu.utez.redre.models.mensajes.services.MRServices;
import mx.edu.utez.redre.models.reportesFinales.ReportesFinales;
import mx.edu.utez.redre.services.EstudiantesServices;
import mx.edu.utez.redre.services.ReportesFinalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import mx.edu.utez.redre.models.reportes.FilesAlmacenar.File;
import mx.edu.utez.redre.models.reportes.FilesAlmacenar.ReportesResponse;
import mx.edu.utez.redre.upload.services.FileServiceAPI;

@RestController
@RequestMapping("/redre/files")
@CrossOrigin({"*"})
public class FileController {
    @Autowired
	private FileServiceAPI fileServiceAPI;
	@Autowired
	private EstudiantesRespository estudiantesRespository;

	@Autowired
	private EstudiantesServices estudiantesServices;

	@Autowired
	private MAServices maServices;

	@Autowired
	private MRServices mrServices;

	@Autowired
	private MDServices mdServices;

	@Autowired
	ReportesFinalesService reportesFinalesService;

	@PostMapping("/upload")
	public ResponseEntity<ReportesResponse> uploadFiles(
			@RequestParam("files") MultipartFile files,
			@RequestParam("matricula") String matricula,
			@RequestParam("type") String type
	) throws Exception {
		String filePath = fileServiceAPI.save(files, matricula + "Reporte" + type + ".pdf");
		String url = MvcUriComponentsBuilder.fromMethodName(FileController.class, "getFile", filePath).build().toString();

		Estudiantes estudiante;

		estudiante = estudiantesRespository.getEstudiantesByMatricula(matricula);

		estudiante.setUrlReporte(url);

		estudiante.setReportStatus("esperaRespuestaDeAsesor");

		estudiantesServices.update(estudiante);

		maServices.deleteByNombre(matricula);

		mrServices.deleteByNombre(matricula);
		mdServices.deleteByNombre(matricula);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ReportesResponse("El archivo fue cargado correctamente"));
	}

	@PostMapping("/uploadFinal")
	public ResponseEntity<ReportesResponse> uploadFinalReport(
			@RequestParam("matricula") String matricula,
			@RequestParam("division") String division,
			@RequestParam("type") String type
	) throws Exception {
		String filePath = fileServiceAPI.saveFinal(matricula + "Reporte" + type);
		String url = MvcUriComponentsBuilder.fromMethodName(FileController.class, "getFile", filePath).build().toString();

		ReportesFinales reportesFinales = new ReportesFinales();

		reportesFinales.setDivisionAcademica(division);
		reportesFinales.setMatricula(matricula);
		if (type.equals("TSU")){
			reportesFinales.setUrlReporteTSU(url);
		} else reportesFinales.setUrlReporteING(url);

		reportesFinalesService.insert(reportesFinales);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ReportesResponse("El archivo fue cargado correctamente"));
	}

	@GetMapping("/{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename) throws Exception {
		Resource resource = fileServiceAPI.load(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<File>> getAllFiles() throws Exception {
		List<File> files = fileServiceAPI.loadAll().map(path -> {
			String filename = path.getFileName().toString();
			String url = MvcUriComponentsBuilder.fromMethodName(FileController.class, "getFile", path.getFileName().toString()).build().toString();
			return new File(filename, url);
		}).collect(Collectors.toList());
		
		return ResponseEntity.status(HttpStatus.OK).body(files);
	}
}
