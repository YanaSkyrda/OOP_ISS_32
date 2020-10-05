package com.momotenko.task1.client.view;

import com.momotenko.task1.client.entity.Delivery;

public class ClientView {
    public void printAllCommands(){
        System.out.println("Choose what to do:\n" +
                "1. Create delivery\n" +
                "2. View delivery\n" +
                "3. Edit delivery\n" +
                "4. Send delivery to the server\n" +
                "5. Exit");
    }

    public void printEditCommands(){
        System.out.println("Choose what to edit:\n" +
                "1. Delivery city\n" +
                "2. Sender name\n" +
                "3. Receiver name\n" +
                "4. Cost\n" +
                "5. Exit\n");
    }

    public void print(String toPrint){
        System.out.println(toPrint);
    }

    public void printDelivery(Delivery toPrint){
        if (toPrint == null){
            System.out.println("You haven't created a delivery");
            return;
        }

        System.out.println(toPrint.toString());
    }

    public void printInvalidCommand(){
        System.out.println("Invalid command");
    }
}
