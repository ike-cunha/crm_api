package ikecrm.api;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Main {
    public static void main(String[] args) {
        System.setProperty("org.apache.commons.logging.Log","org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime","true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http","DEBUG");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.wire","DEBUG");
        Quarkus.run(args);
    }
}
