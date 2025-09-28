package co.avanzada.services.impl;

import lombok.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Getter @Setter
@AllArgsConstructor
@Builder
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public void send(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void send(String to, String text) {
        send(to, "Código de recuperación de contraseña", text);
    }
}
