package exception;

/**
 * Exception khi đồ thị không hợp lệ
 */
public class InvalidGraphException extends RuntimeException {
    
    public InvalidGraphException(String message) {
        super(message);
    }
    
    public InvalidGraphException(String message, Throwable cause) {
        super(message, cause);
    }
}
