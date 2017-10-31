import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Created by tphadke on 8/29/17.
 */
public class Main {


    /**
     * asks user for selecting a root value to initiate the program
     * @param args
     */
    public static void main ( String args[]){
        Processor p0 = new Processor(1);
        Processor p1 = new Processor(44);
        Processor p2 = new Processor(12);
        Processor p3 = new Processor(10);
        Processor p4 = new Processor(50);
        Processor p5 = new Processor(33);

        p0.setRight(p1);
        p0.setLeft(p5);

        p1.setRight(p2);
        p1.setLeft(p0);

        p2.setRight(p3);
        p2.setLeft(p1);

        p3.setRight(p4);
        p3.setLeft(p2);

        p4.setRight(p5);
        p4.setLeft(p3);

        p5.setRight(p0);
        p5.setLeft(p4);

        Executor e0 = new Executor(p0);
        Executor e1 = new Executor(p1);
        Executor e2 = new Executor(p2);
        Executor e3 = new Executor(p3);
        Executor e4 = new Executor(p4);
        Executor e5 = new Executor(p5);

        e0.start();
        e1.start();
        e2.start();
        e3.start();
        e4.start();
        e5.start();



    }


}

class Executor extends Thread
{
    Processor p;

    public Executor(Processor p)
    {
        this.p = p;
    }

    @Override
    public void run() {
        Message probe = new Message(p.id,"probe",0,1);
        System.out.println("Running Thread: " + p.id);
        try {
        Thread.sleep(2000);
        this.p.right.sendMessgeToMyBuffer(probe);
        this.p.left.sendMessgeToMyBuffer(probe);




            //System.out.println("Running Thread: " + p.id);
            System.out.println("--------------------------------------------------------");
        }catch(InterruptedException e)
        {
            System.out.println(e);
        }
    }
}
