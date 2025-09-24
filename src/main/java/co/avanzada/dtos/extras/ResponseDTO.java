package co.avanzada.dtos.extras;

public record ResponseDTO<T>(Boolean success, T message) {
}
