package model.responses;

public record UserResponse(String username, String authToken, boolean success, String message) {
}
