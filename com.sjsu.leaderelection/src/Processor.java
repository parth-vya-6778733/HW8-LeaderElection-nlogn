import javafx.css.StyleableStringProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by tphadke on 8/29/17.
 * processor has its own message buffer, id, children if applicable,
 * a parent process if itself is not root, and a neighbor pj which may
 * or may not be the parent, but sends the message to this processor.
 */
public class Processor extends Thread implements Observer {
    //Each processsor has a message Buffer to store messages
    Buffer messageBuffer ;
    Integer id ;
    Integer currentLeader;
    public Processor right;
    public Processor left;
    Integer totalProcessors = 6;
    boolean phaseWinnerFlag = false;
   //private static final Object lockObject = new Object();


    /**
     * initialize processor's params
     */
    public Processor(Integer id) {
        messageBuffer = new Buffer();
        this.id = id; //This is an invalid value. Since only +ve values are acceptable as processor Ids.
        //Each processor is observing itself;
        messageBuffer.addObserver(this);
        this.currentLeader = -1;

    }

    public void setRight(Processor right) {
        this.right = right;
    }

    public void setLeft(Processor left) {
        this.left = left;
    }

    /**
     * send message to this processor with reference to sender as pj
     * @param message
     */
    //This method will add a message to this processors buffer.
    //Other processors will invoke this method to send a message to this Processor
    public void sendMessgeToMyBuffer(Message message){
        messageBuffer.setMessage(message);
    }


    /**
     * this method is called when the message buffer is updated with a new message
     * @param observable
     * @param arg
     */
    //This is analogous to recieve method.Whenever a message is dropped in its buffer this Pocesssor will respond
    //TODO: implement the logic of receive method here
    //      Hint: Add switch case for each of the conditions given in receive
    public void update(Observable observable, Object arg) {
            Message m = (Message) arg;
            System.out.println("This Processor id: " + this.id);
            System.out.println("Message Received: " + m.getId());
            System.out.println("Message Type: " + m.getMessage());
            String message = m.getMessage();
            int mId = m.getId();
            int hops = m.getHops();
            int phase = m.getPhase();


            switch(message) {
                case "probe":
                    if(mId == this.id)
                    {
                        this.left.sendMessgeToMyBuffer(new Message(this.id,"terminate",phase,hops));
                        this.right.sendMessgeToMyBuffer(new Message(this.id,"terminate",phase,hops));
                        //send terminate message, last phase
                    }
                    if(mId == this.left.id)
                    {
                        if((mId > this.id) && (hops < Math.pow(2,phase)))
                        {
                            left.sendMessgeToMyBuffer(new Message(mId,"probe",phase,hops+1));
                        }
                        else if((mId > this.id) && (hops >= Math.pow(2,phase)))
                        {
                            right.sendMessgeToMyBuffer(new Message(mId,"reply",phase,hops));
                        }
                    }
                    if(mId == this.right.id)
                    {
                        if((mId > this.id) && (hops < Math.pow(2,phase)))
                        {
                            right.sendMessgeToMyBuffer(new Message(mId,"probe",phase,hops+1));
                        }
                        else if((mId > this.id) && (hops >= Math.pow(2,phase)))
                        {
                            left.sendMessgeToMyBuffer(new Message(mId,"reply",phase,hops));
                        }
                    }

                    break;
                case "reply":
                    if (mId != this.id)
                    {
                        if(mId == this.left.id)
                        {
                            this.left.sendMessgeToMyBuffer(new Message(mId,"reply",phase,hops));
                        }
                        else if(mId == this.right.id)
                        {
                            this.right.sendMessgeToMyBuffer(new Message(mId,"reply",phase,hops));
                        }

                    }
                    else
                    {
                        if(mId == this.right.id || mId == this.left.id)
                        {
                            this.phaseWinnerFlag = true;
                        }
                        if(phaseWinnerFlag)
                        {
                            if(mId == this.left.id)
                            {
                                this.left.sendMessgeToMyBuffer(new Message(mId,"probe",phase+1,hops));
                            }
                            else if(mId == this.right.id)
                            {
                                this.right.sendMessgeToMyBuffer(new Message(mId,"probe",phase+1,hops));
                            }
                            this.phaseWinnerFlag = false;
                        }
                    }

                    break;
                case "terminate":
                    if (this.id == mId) {
                        System.out.println("Terminate Message Received by Leader: " + this.id);
                    } else if(mId == this.right.id){
                        right.sendMessgeToMyBuffer(m);
                    }else if(mId == this.left.id){
                        left.sendMessgeToMyBuffer(m);
                    }


            }


    }

    @Override
    public void run() {
        Message probe = new Message(this.id,"probe",0,1);
        System.out.println("Running Thread: " + this.id);

            this.right.sendMessgeToMyBuffer(probe);
            this.left.sendMessgeToMyBuffer(probe);





    }


}