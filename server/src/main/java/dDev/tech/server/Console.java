package dDev.tech.server;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.tools.jdeprscan.scan.Scan;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Console extends Thread{
    private static  Scanner scanner= new Scanner(System.in);
    private static HashMap<String, Consumer<String[]>>commands = new HashMap<>();
    private volatile boolean logIn = true;
    public static void addCommand(String command, Consumer<String[]> function){
        commands.put(command,function);
    }
    @Override
    public void run() {
        while(logIn){

            String commandLine=scanner.nextLine();
            String[]splits = commandLine.split(" ");

            Consumer<String[]>function=commands.get(splits[0]);
            if(function!=null) {
                function.accept(splits);
            }else{
                logError("Command doesnt exist");
            }
        }

    }
    public static void log(String prefix,String message){

        System.out.println("\b\b["+prefix+"]: "+message);
        System.out.print("> ");
    }
    public static void logError(String message){
        log("ERROR",message);
    }
    public static void logWarning(String message){
        log("WARNING",message);
    }
    public static void logInfo(String message){
        log("INFO",message);
    }
}
