/**
 * Created by tphadke on 8/29/17.
 */
public class  Message {
    private int id;
    private int phase;
    private int hops;
    private String message;

    public Message() {
        this.message = "";
    }

    public Message(int id, String message, int phase, int hops)
    {
        this.id = id;
        this.message = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String terminate) {
        this.message = message;
    }

    public int getPhase() {
        return phase;
    }

    public int getHops() {
        return hops;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public void setHops(int hops) {
        this.hops = hops;
    }

}
