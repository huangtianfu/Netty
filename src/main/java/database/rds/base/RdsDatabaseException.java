package database.rds.base;

@SuppressWarnings("serial")
public class RdsDatabaseException extends Exception {
    private String msg = null;

    public RdsDatabaseException() {
        super();
        msg = "";
    }

    public RdsDatabaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMessage() {
        return this.msg;
    }
}
