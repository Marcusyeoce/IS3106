package util.exception;

/**
 *
 * @author oimun
 */
public class ArticleNotFoundException extends Exception{

    public ArticleNotFoundException() {
    }

    public ArticleNotFoundException(String message) {
        super(message);
    }
    
}
