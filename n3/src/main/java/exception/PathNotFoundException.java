package exception;

/**
 * Exception khi không tìm thấy đường đi
 */
public class PathNotFoundException extends RuntimeException {
    
    public PathNotFoundException(String message) {
        super(message);
    }
    
    public PathNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
