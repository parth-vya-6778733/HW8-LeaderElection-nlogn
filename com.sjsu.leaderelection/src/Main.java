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

    Processor p0,p1,p2,p3,p4,p5;
    /**
     * asks user for selecting a root value to initiate the program
     * @param args
     */
    public static void main ( String args[])throws InterruptedException{
    Main m = new Main();
    m.init();
    m.execute();
    }

    public void init()
    {
        p0 = new Processor(1);
        p1 = new Processor(44);
        p2 = new Processor(12);
        p3 = new Processor(10);
        p4 = new Processor(50);
        p5 = new Processor(33);

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
    }

    public void execute() throws InterruptedException
    {
        p0.start();
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();

        p0.join();
        p1.join();
        p2.join();
        p3.join();
        p4.join();
        p5.join();
    }



}

