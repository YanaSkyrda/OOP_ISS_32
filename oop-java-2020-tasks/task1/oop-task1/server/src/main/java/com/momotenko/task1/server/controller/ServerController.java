package com.momotenko.task1.server.controller;

import com.momotenko.task1.server.Server;

public class ServerController {
    private Server server;
    private String hostname;
    private int port;

    public ServerController(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }

    public void run() {
        server = new Server(hostname, port);
        server.run();
    }
}
