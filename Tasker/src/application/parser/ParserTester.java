package application.parser;
//import application.storage.Storage;

import application.storage.Storage;

public class ParserTester {

    public static void main (String[] args){
        Storage storage = new Storage();
        Parser parser = new Parser();
        
        Command cmd = parser.interpretCommand("brush teeth by 7 am tomorrow at bathroom");
        System.out.println(cmd.execute(storage));
        
        
    }
}
