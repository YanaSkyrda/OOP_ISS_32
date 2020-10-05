package com.momotenko.client.controller;

import com.momotenko.client.model.entity.Delivery;
import com.momotenko.client.view.ClientView;

import java.math.BigDecimal;
import java.util.Scanner;

public class ClientController {
    private ClientView view = new ClientView();
    private Delivery delivery;

    public void run() {
        Scanner input = new Scanner(System.in);
        int command;
        while (true) {
            view.printAllCommands();
            command = input.nextInt();
            switch (command) {
                case 1:
                    createDelivery();
                    break;
                case 2:
                    view.printDelivery(delivery);
                    break;
                case 3:
                    editDelivery();
                    break;
                case 4:
                    sendDelivery();
                    break;
                case 5:
                    return;
                default:
                    view.printInvalidCommand();
                    break;
            }
        }
    }

    private void createDelivery() {
        Scanner input = new Scanner(System.in);
        String city;
        String sender;
        String receiver;
        BigDecimal cost;

        view.print("Enter the city of the delivery: ");
        city = input.nextLine();
        view.print("Enter the sender: ");
        sender = input.nextLine();
        view.print("Enter receiver: ");
        receiver = input.nextLine();
        view.print("Enter the cost: ");
        cost = input.nextBigDecimal();

        delivery = new Delivery(city, sender, receiver, cost);
    }

    private void editDelivery() {
        int command;
        Scanner scanner = new Scanner(System.in);
        while(true){
            view.printEditCommands();
            command = scanner.nextInt();
            switch (command){
                case 1:
                    view.print("Enter new city name: ");
                    String city = scanner.nextLine();
                    delivery.setCity(city);
                    break;
                case 2:
                    view.print("Enter new sender name: ");
                    String senderName = scanner.nextLine();
                    delivery.setSender(senderName);
                    break;
                case 3:
                    view.print("Enter new receiver name: ");
                    String receiverName = scanner.nextLine();
                    delivery.setReceiver(receiverName);
                    break;
                case 4:
                    view.print("Enter new cost: ");
                    BigDecimal cost = scanner.nextBigDecimal();
                    delivery.setCost(cost);
                    break;
                case 5:
                    return;
                case 6:
                    view.printInvalidCommand();
                    break;
            }
        }
    }

    private void sendDelivery() {

    }
}
