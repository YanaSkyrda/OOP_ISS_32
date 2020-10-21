package com.momotenko.task1.client;

import com.momotenko.task1.api.entity.Delivery;
import com.momotenko.task1.client.controller.ClientController;
import com.momotenko.task1.server.Server;
import com.momotenko.task1.server.controller.ServerController;
import org.jgroups.nio.MockSocketChannel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.*;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ClientTest {
    static private MockedStatic<Client> clientMock;

    @BeforeAll
    static void beforeMethod() throws IOException {
        InetSocketAddress address = new InetSocketAddress("localhost", 1024);

        clientMock = Mockito.mockStatic(Client.class);

//        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
//        serverSocketChannel.bind(address);
//
//        when(clientMock.openSocketChannel(address)).thenReturn(serverSocketChannel.accept());
//        clientMock = mock(Client.start().getClass());
//        MockSocketChannel mockSocketChannel = new MockSocketChannel();
//        address = new InetSocketAddress("localhost", 1024);
//
//        selectorMock = Selector.open();
//        serverSocketChannelMock = ServerSocketChannel.open();
//        serverSocketChannelMock.bind(address);
//        serverSocketChannelMock.configureBlocking(false);
//        serverSocketChannelMock.register(selectorMock,SelectionKey.OP_ACCEPT);
//        mockSocketChannel.connect(address);
//
//        selectorMock.select();
//        Set<SelectionKey> selectedKeys = selectorMock.selectedKeys();
//        Iterator<SelectionKey> iterator = selectedKeys.iterator();
//
//        while (iterator.hasNext()) {
//            SelectionKey key = iterator.next();
//            if (key.isAcceptable()){
//                serverSocketChannelMock.accept();
//                break;
//            }
//        }
//
//        mockSocketChannel.finishConnect();

        //when(client.openSocketChannel(address)).thenReturn()
//        clientSocketChannelMock = mock(SocketChannel.class);
//        serverSocketChannelMock = ServerSocketChannel.open();
//        selectorMock = Selector.open();
//
//        serverSocketChannelMock.bind(address);
//
//        serverSocketChannelMock.configureBlocking(false);
//
//
//        serverSocketChannelMock.register(selectorMock, SelectionKey.OP_ACCEPT);
//
//        serverSocketChannelMock.accept();
//        //when(SocketChannel.open(address)).thenReturn(clientSocketChannelMock);
//        client = Client.start();
//
//        selectorMock.select();
    }

    @AfterAll
    static void afterMethod() {
        clientMock.stop();
    }

    @Test
    @DisplayName("test")
    void sendMessageVerifyIfSuccess() throws IOException, ClassNotFoundException {
        Delivery delivery = new Delivery();
        delivery.setCity("Kyiv");
        delivery.setCost(BigDecimal.valueOf(435));
        delivery.setSender("Vlad");
        delivery.setReceiver("Yura");


//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
//        outputStream.writeObject(delivery);
//        outputStream.flush();
//
//        ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());


        ArgumentCaptor<ByteBuffer> byteBufferArgumentCaptor = ArgumentCaptor.forClass(ByteBuffer.class);

        clientMock.sendMessage(delivery);
        // verify(clientSocketChannelMock).write(byteBufferArgumentCaptor.capture());


        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBufferArgumentCaptor.getValue().array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object object = objectInputStream.readObject();
        Delivery newDelivery = new Delivery((Delivery) object);


        assertTrue(delivery.equals(newDelivery));
    }
}
