package co.avanzada.dtos.extras;

public record ResponseUserDTO <T>(boolean succes, String message, T data) {
}
