package com.cs.csaiagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CsAiAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsAiAgentApplication.class, args);
        System.out.println("" +
                "   ____ ____    _    ___    _                    _   \n" +
                "  / ___/ ___|  / \\  |_ _|  / \\   __ _  ___ _ __ | |_ \n" +
                " | |   \\___ \\ / _ \\  | |  / _ \\ / _` |/ _ \\ '_ \\| __|\n" +
                " | |___ ___) / ___ \\ | | / ___ \\ (_| |  __/ | | | |_ \n" +
                "  \\____|____/_/   \\_\\___/_/   \\_\\__, |\\___|_| |_|\\__|\n" +
                "                                |___/                ");
    }

}
