package co.avanzada.services.impl;

import co.avanzada.model.Reservations;
import lombok.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Getter
@Setter
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

    //Envío de confirmación al huésped
    public void sendReservationConfirmation(Reservations reservation) {
        String subject = "Confirmación de tu reserva";
        String body = """
                Hola %s,

                Tu reserva para el alojamiento "%s" se ha registrado correctamente.

                Fechas: %s - %s
                Huéspedes: %d
                Precio total: $%s
                Estado: %s

                ¡Gracias por reservar con nosotros!
                """.formatted(
                reservation.getUser().getFullName(),
                reservation.getListings().getTitle(),
                reservation.getCheckIn(),
                reservation.getCheckOut(),
                reservation.getGuestCount(),
                reservation.getPrice(),
                reservation.getReservationsStatus()
        );

        send(reservation.getUser().getEmail(), subject, body);
    }

    //Envío de notificación al anfitrión
    public void sendHostNotification(Reservations reservation) {
        String subject = "Nueva reserva en tu alojamiento";
        String body = """
                Hola %s,

                Has recibido una nueva reserva para tu alojamiento "%s".

                Fechas: %s - %s
                Huéspedes: %d
                Total: $%s
                Estado: %s
                """.formatted(
                reservation.getListings().getHost().getFullName(),
                reservation.getListings().getTitle(),
                reservation.getCheckIn(),
                reservation.getCheckOut(),
                reservation.getGuestCount(),
                reservation.getPrice(),
                reservation.getReservationsStatus()
        );

        send(reservation.getListings().getHost().getEmail(), subject, body);
    }

    public void sendHostReservationCancellation(Reservations reservation) {
        String subject = "Una reserva ha sido cancelada";
        String body = """
            Hola %s,

            El huésped %s ha cancelado su reserva en el alojamiento "%s".
            
            Fechas: %s - %s
            Estado actual: %s
            """.formatted(
                reservation.getListings().getHost().getFullName(),
                reservation.getUser().getFullName(),
                reservation.getListings().getTitle(),
                reservation.getCheckIn(),
                reservation.getCheckOut(),
                reservation.getReservationsStatus()
        );

        send(reservation.getListings().getHost().getEmail(), subject, body);
    }
}
