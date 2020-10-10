package com.momotenko.task1.server.controller;

import com.momotenko.task1.server.Server;

import java.io.IOException;

public class ServerController {
    private Server server;

    public void run() {
        try {
            server = Server.start();
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
