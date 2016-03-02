package application.parser;
//import application.storage.Storage;

import application.storage.Storage;

public class ParserTester {

    public static void main (String[] args){
        Storage storage = new Storage();
        Parser parser = new Parser();
        
        Command cmd = parser.interpretCommand("just testing by now at then");
        System.out.println(cmd.execute(storage));
        
        
    }
}
