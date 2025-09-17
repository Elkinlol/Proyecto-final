package co.avanzada.dtos;

public record ResponseDTO<T>(Boolean success, T message) {
}
