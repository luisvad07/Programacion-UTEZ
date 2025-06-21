package com.school.citas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.school.citas.dtos.SolicitanteDto;
import com.school.citas.models.Solicitante.Solicitante;
import com.school.citas.services.SolicitanteService;
import com.school.citas.utils.CustomResponse;

import java.io.File;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/solicitantes/")
@CrossOrigin(origins = {"*"})
public class SolicitanteController {

    @Autowired
    private JavaMailSender javaMailSender;     
    @Autowired
    private SolicitanteService solicitanteService;

     //@PreAuthorize("hasRole('SOLICITANTE')")
     @GetMapping("/")
     public ResponseEntity<CustomResponse<List<Solicitante>>> getAll(){
         return new ResponseEntity<>(
                 this.solicitanteService.getAll(),
                 HttpStatus.OK
         );
     }

    @GetMapping("/getActive")
    public ResponseEntity<CustomResponse<List<Solicitante>>>
    getAllActive(){
        return new ResponseEntity<>(
                this.solicitanteService.getAllActive(),
                HttpStatus.OK
        );
    }

     @GetMapping("/getAllInactive")
     public ResponseEntity<CustomResponse<List<Solicitante>>>
     getAllInactive(){
         return new ResponseEntity<>(
                 this.solicitanteService.getAllInactive(),
                 HttpStatus.OK
         );
     }

     @GetMapping("/{id}")
     public ResponseEntity<CustomResponse<Solicitante>> getOne(@PathVariable("id") Long id) {
         return new ResponseEntity<>(
                 this.solicitanteService.getOne(id),
                 HttpStatus.OK
         );
     }

     @PostMapping("/")
     public ResponseEntity<CustomResponse<Solicitante>> insert(@RequestBody @Valid SolicitanteDto solicitanteDto, @Valid BindingResult result) throws MessagingException{
         if (result.hasErrors()) {
             return new ResponseEntity<>(
                     null,
                     HttpStatus.BAD_REQUEST
             );
         }
         return new ResponseEntity<>(
                 this.solicitanteService.insert(solicitanteDto.getSolicitante()),
                 HttpStatus.CREATED
         );
     }

     @PutMapping("/{id}")
     public ResponseEntity<CustomResponse<Solicitante>> update(
             @RequestBody SolicitanteDto solicitanteDto,
             @Valid BindingResult result
     ){
         if (result.hasErrors()) {
             return new ResponseEntity<>(
                     null,
                     HttpStatus.BAD_REQUEST
             );
         }
         return new ResponseEntity<>(
                 this.solicitanteService.update(solicitanteDto.getSolicitante()),
                 HttpStatus.CREATED
         );
     }

     @PatchMapping("/{id}")
     public ResponseEntity<CustomResponse<Boolean>> enableOrDisable(@RequestBody SolicitanteDto solicitanteDto){
         return new ResponseEntity<>(
                 this.solicitanteService.changeStatus(solicitanteDto.getSolicitante()),
                 HttpStatus.OK
         );
     }

      //modificar contraseña
    @PatchMapping("/changePassword")
    public ResponseEntity<CustomResponse<Integer>> changePassword(@RequestBody SolicitanteDto solicitanteDto, @Valid BindingResult result) throws MessagingException
    {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }

        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper= new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setTo(solicitanteDto.getCorreoElectronico());
        messageHelper.setFrom("20213tn002@utez.edu.mx");
        messageHelper.setSubject("Solicitud de Recuperación de Contraseña");
        
        // Configurar el contenido del mensaje con diseño personalizado
        String htmlContent = "<html>" +
        "<head>" +
        "<style>" +
        ".card {" +
        "width: 500px;" +
        "height: 500px;" +
        "margin: 0 auto;" +
        "background-color: #f8fbfe;" +
        "border-radius: 8px;" +
        "border: 1px solid #ccc;" +
        "}" +
        ".tools {" +
        "display: flex;" +
        "align-items: center;" +
        "padding: 9px;" +
        "}" +
        ".circle {" +
        "padding: 0 4px;" +
        "}" +
        ".box {" +
        "display: inline-block;" +
        "align-items: center;" +
        "width: 10px;" +
        "height: 10px;" +
        "padding: 1px;" +
        "border-radius: 50%;" +
        "}" +
        ".red {" +
        "background-color: #ff605c;" +
        "}" +
        ".yellow {" +
        "background-color: #ffbd44;" +
        "}" +
        ".green {" +
        "background-color: #00ca4e;" +
        "}" +
        ".card__content {" +
        "display: flex;" +
        "justify-content: center;" +
        "align-items: center;" +
        "height: calc(100% - 36px);" +
        "}" +
        ".card__content > div {" +
        "padding-left: 10px;" +
        "}" +
        "h1 {" +
        "font-size: 24px;" +
        "color: #ff605c;" +
        "font-family: 'Courier New', monospace;" +
        "margin-bottom: 8px;" +
        "}" +
        "h4 {" +
        "font-size: 18px;" +
        "color: #264b99;" +
        "font-family: 'Courier New', monospace;" +
        "margin-bottom: 4px;" +
        "}" +
        "img {" +
        "max-width: 100%;" +
        "height: auto;" +
        "margin-bottom: 12px;" +
        "}" +
        "</style>" +
        "</head>" +
        "<body>" +
        "<div class=\"card\">" +
        "<div class=\"tools\">" +
        "<div class=\"circle\">" +
        "<span class=\"red box\"></span>" +
        "</div>" +
        "<div class=\"circle\">" +
        "<span class=\"yellow box\"></span>" +
        "</div>" +
        "<div class=\"circle\">" +
        "<span class=\"green box\"></span>" +
        "</div>" +
        "</div>" +
        "<div class=\"card__content\">" +
        "<div>" +
        "<h1 style=\"color: #ff605c;\">¡Hola! "+ solicitanteDto.getNombre() + solicitanteDto.getApePaterno() +" </h1>" +
        "<h4 style=\"color: #58BEC4;\">Usuario Administrador</h4>" +
        "<img src='cid:logoImage' />" +
        "<h4 style=\"color: #BAB9BC;\">" +
        "Su contraseña has sido modificada correctamente: " + solicitanteDto.getPass() +
        "</h4>" +
        "<br>" +
        "<div>" +
        "<center><img src=\"cid:logoImage\" alt=\"Logo\"> width=\"50px\" height=\"50px\"></center>"+
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</body>" +
        "</html>";
        messageHelper.setText(htmlContent, true);
        
        // Adjuntar la imagen como recurso en línea
        FileSystemResource imageResource = new FileSystemResource(new File("src/main/resources/static/images/citat.jpeg"));
        messageHelper.addInline("logoImage", imageResource);

        this.javaMailSender.send(mimeMessage);
        return new ResponseEntity<>(
                this.solicitanteService.changePassword(solicitanteDto.getSolicitante()),
                HttpStatus.OK
        );
    }
}
