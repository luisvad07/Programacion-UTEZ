package com.school.citas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.citas.models.Cita.Cita;
import com.school.citas.models.Cita.CitaRepository;
import com.school.citas.utils.CustomResponse;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Transactional
public class CitaService {
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Autowired
    private CitaRepository citaRepository;

    @Transactional(readOnly = true)
    public CustomResponse<List<Cita>> getAll(){
        return new CustomResponse<>(
                this.citaRepository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para las citas activas
    @Transactional(readOnly = true)
    public  CustomResponse<List<Cita>> getAllActive(){
        return new CustomResponse<>(
                this.citaRepository.findAllByAtendida(true),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para las citas inactivas
    @Transactional(readOnly = true)
    public  CustomResponse<List<Cita>> getAllInactive(){
        return new CustomResponse<>(
                this.citaRepository.findAllByAtendida(false),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para buscar una cita
    @Transactional (readOnly = true)
    public CustomResponse<Cita> getOne(Long id) {
        Optional<Cita> citaOptional = this.citaRepository.findById(id);
        if (citaOptional.isPresent()) {
            return new CustomResponse<>(
                    citaOptional.get(),
                    false,
                    200,
                    "Ok"
            );
        } else {
            return new CustomResponse<>(
                    null,
                    true,
                    404,
                    "Cita no encontrada"
            );
        }
    }

    ///Servicio para insertar una cita
    @Transactional (rollbackFor = {SQLException.class})
    public CustomResponse<Cita> insert(Cita cita) throws MessagingException{
    if (this.citaRepository.existsByFechaAndHora(cita.getFecha(), cita.getHora())) {
        return new CustomResponse<>(
                null,
                true,
                400,
                "La fecha y hora seleccionadas ya tienen una cita registrada"
            );
        }

         //envio de correos electronicos
         MimeMessage mimeMessage = javaMailSender.createMimeMessage();
         MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
         messageHelper.setTo(cita.getSolicitante().getCorreoElectronico());
         messageHelper.setFrom(cita.getVentanilla().getCorreoElectronico());
         messageHelper.setSubject("Agendación de la Cita");

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
                 "<h1 style=\"color: #ff605c;\">¡Hola! "+ cita.getSolicitante().getNombre() + cita.getSolicitante().getApePaterno() +" </h1>"+
                 "<h4 style=\"color: #264B99;\">Tienes programado una cita para realizar tu servicio</h4>" +
                 "<img src='cid:logoImage' />" +
                 "<h4 style=\"color: #58BEC4;\">El día " + cita.getFecha() +  " a las " + cita.getHora() + ", el tramite a realizar será " + cita.getServicio().getNomserv() + ", y te atenderá " + cita.getVentanilla().getNombreVent() + cita.getVentanilla().getApePaternoVent()+ "</h4>" +
                 "<h4 style=\"color: #BAB9BC;\">" +
                 "Costo: " + cita.getMontoPago()  +
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

        return new CustomResponse<>(
                this.citaRepository.saveAndFlush(cita),
                false,
                200,
                "Cita registrada correctamente"
        );
    }

    //Servicio actualizar
    @Transactional (rollbackFor = {SQLException.class})
    public  CustomResponse<Cita> update(Cita cita){
        if(!this.citaRepository.existsById(cita.getId())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "Cita no encontrada"
            );
        }
        return new CustomResponse<>(
                this.citaRepository.saveAndFlush(cita),
                false,
                200,
                "Cita actualizada correctamente"
        );
    }

    //Servicio para cambiar status
    @Transactional (rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> changeStatus(Cita cita){
        if (!this.citaRepository.existsById(cita.getId())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "Cita no encontrada"
            );
        }
        return new CustomResponse<>(
                this.citaRepository.updateAtendidaById(
                        cita.isAtendida(), cita.getId()
                )==1,
                false,
                200,
                "¡La cita fue atendida correctamente!"
        );
    }
}
