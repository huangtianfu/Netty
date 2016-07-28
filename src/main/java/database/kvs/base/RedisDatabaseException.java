package database.kvs.base;

@SuppressWarnings("serial")
public class RedisDatabaseException extends Exception {
    private String msg = null;

    public RedisDatabaseException() {
        super();
        this.msg = "";
    }

    public RedisDatabaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMessage() {
        return this.msg;
    }
}
