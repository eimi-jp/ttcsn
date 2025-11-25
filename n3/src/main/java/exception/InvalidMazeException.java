package exception;

/**
 * Exception khi mê cung không hợp lệ
 */
public class InvalidMazeException extends RuntimeException {
    
    public InvalidMazeException(String message) {
        super(message);
    }
    
    public InvalidMazeException(String message, Throwable cause) {
        super(message, cause);
    }
}
