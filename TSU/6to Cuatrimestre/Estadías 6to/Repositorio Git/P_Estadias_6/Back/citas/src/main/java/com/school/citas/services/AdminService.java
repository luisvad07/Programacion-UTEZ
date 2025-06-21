package com.school.citas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.citas.models.Administrador.Administrador;
import com.school.citas.models.Administrador.AdministradorRepository;
import com.school.citas.utils.CustomResponse;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Transactional
public class AdminService {

    @Value("spring.mail.username")
    private String mail;
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private AdministradorRepository adminRepository;

    @Transactional(readOnly = true)
    public CustomResponse<List<Administrador>> getAll(){
        return new CustomResponse<>(
                this.adminRepository.findAll(),
                false,
                200,
                "ok"
        );
    }

    ///Servicio para los activos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Administrador>> getAllActive(){
        return new CustomResponse<>(
                this.adminRepository.findAllByStatus(true),
                false,
                200,
                "ok"
        );
    }

    ///Servicio para los inactivos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Administrador>> getAllInactive(){
        return new CustomResponse<>(
                this.adminRepository.findAllByStatus(false),
                false,
                200,
                "ok"
        );
    }

    ///Servicio para buscar uno
    @Transactional(readOnly = true)
    public CustomResponse<Administrador> getOne(Long id) {
        Optional<Administrador> adminOptional = this.adminRepository.findById(id);
        if (adminOptional.isPresent()) {
            return new CustomResponse<>(
                    adminOptional.get(),
                    false,
                    200,
                    "ok"
            );
        } else {
            return new CustomResponse<>(
                    null,
                    true,
                    404,
                    "Usuario Administrador no encontrado"
            );
        }
    }

    //Servicio para insertar
    @Transactional (rollbackFor = {SQLException.class})
    public CustomResponse<Administrador> insert(Administrador admin) throws MessagingException {
        if (this.adminRepository.existsByCorreoAdmin(admin.getCorreoAdmin())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "¡El correo de este Administrador ya existe!"
            );
        }
        //envio de correos electronicos
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setTo(admin.getCorreoAdmin());
        messageHelper.setFrom(mail);
        messageHelper.setSubject("Mensaje de Confirmación de Creación de Cuenta");

        // Configurar el contenido del mensaje con diseño personalizado
        String htmlContent = "<html>" +
        "<head>" +
        "<style>" +
        ".card {" +
        "width: 500px;" +
        "height: 700px;" +
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
        "<h1 style=\"color: #ff605c;\">¡Hola! "+ admin.getNombreAdmin() + " " + admin.getApePaternoAdmin() +" </h1>" +
        "<h4 style=\"color: #264B99;\">Bienvenido al Sistema CITAT</h4>" +
        "<h4 style=\"color: #58BEC4;\">Usuario Administrador</h4>" +
        "<h4 style=\"color: #BAB9BC;\">" +
        "Este es tu correo para ingresar sesión " + admin.getCorreoAdmin() +  ", con tu contraseña " + admin.getPass() +
        "</h4>" +
        "<br>" +
        "<div>" +
        "<center><img src=\"cid:logoImage\" alt=\"Logo\"> width=\"1px\" height=\"1px\"></center>"+
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

        // Enviar el correo electrónico
        this.javaMailSender.send(mimeMessage);
        return new CustomResponse<>(
                this.adminRepository.saveAndFlush(admin),
                false,
                200,
                "Administrador registrado correctamente"
        );
    }

    //Servicio para actualizar
    @Transactional (rollbackFor = {SQLException.class})
    public  CustomResponse<Administrador> update(Administrador admin){
        if (!this.adminRepository.existsById(admin.getId())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "¡El administrador que quieres buscar no existe!"
            );
        }
        return new CustomResponse<>(
                this.adminRepository.saveAndFlush(admin),
                false,
                200,
                "Administrador actualizado correctamente"
        );
    }

    //Servicio para cambiar el status
    @Transactional (rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> changeStatus(Administrador admin){
        if (!this.adminRepository.existsById(admin.getId())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "¡El administrador que quieres buscar no existe!"
            );
        }
        return new CustomResponse<>(
                this.adminRepository.updateStatusById(admin.isStatus(), admin.getId()) == 1,
                false,
                200,
                "¡Se ha cambiado el status del Administrador correctamente!"
        );
    }

    //cambia la contraseña por la que el usuario establezca
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Integer> changePassword(Administrador admin) {
        if (!this.adminRepository.existsByCorreoAdmin(admin.getCorreoAdmin()))
            return new CustomResponse<>(
                    null, true, 400,
                    "¡El administrador que quieres buscar no existe!"
            );
        admin.setPass(admin.getPass());
        System.out.println("New password->" + admin.getPass());

        return new CustomResponse<>(
                this.adminRepository.changePassword(
                    admin.getPass(), admin.getCorreoAdmin()),
                false, 200,
                "¡Contraseña modificada correctamente!"
        );
    }

    public Administrador findByCorreoAdmin(String correoAdmin){
        return this.adminRepository.findByCorreoAdmin(correoAdmin);
    }
}
