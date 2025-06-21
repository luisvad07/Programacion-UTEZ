package mx.edu.utez.redre.upload.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import mx.edu.utez.redre.models.reportes.FilesAlmacenar.ReportesResponse;

import java.nio.file.FileAlreadyExistsException;


@ControllerAdvice
public class FileUploadExceptionAdvice {
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ReportesResponse> handleMaxSizeException(MaxUploadSizeExceededException ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ReportesResponse("Verifica el tamaño de los archivos"));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ReportesResponse> handleException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ReportesResponse("Error generico" + ex.getMessage()));
	}
	@ExceptionHandler(FileAlreadyExistsException.class)
	public ResponseEntity<ReportesResponse> handleFileAlreadyExistsException(FileAlreadyExistsException ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ReportesResponse("Nombre de archivo invalido"));
	}

}