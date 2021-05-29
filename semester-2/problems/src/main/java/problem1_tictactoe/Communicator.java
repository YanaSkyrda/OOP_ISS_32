package problem1_tictactoe;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Communicator {
    public static final String XtoO = "XtoO";
    public static final String OtoX = "OtoX";

    private Connection connection;
    private Session session;
    private MessageProducer producer;
    private MessageConsumer consumer;

    public Communicator(int p) {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        try {
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination queueSend;
            Destination queueRec;
            if (p == GameManager.X) {
                queueSend = session.createQueue(XtoO);
                queueRec = session.createQueue(OtoX);
            } else {
                queueSend = session.createQueue(OtoX);
                queueRec = session.createQueue(XtoO);
            }

            producer = session.createProducer(queueSend);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            consumer = session.createConsumer(queueRec);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendMove(int i, int j) {
        try {
            TextMessage message = session.createTextMessage("Move#" + i + "#" + j);
            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendWin(int i, int j) {
        try {
            TextMessage message = session.createTextMessage("Win#" + i + "#" + j);
            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendTie(int i, int j) {
        try {
            TextMessage message = session.createTextMessage("Tie#" + i + "#" + j);
            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public int[] receiveMessage() {
        try {
            Message message = consumer.receive(500);

            if (message == null) {
                return null;
            }

            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String buffer = textMessage.getText();
                var fields = buffer.split("#");

                int[] move = {0, -1, -1};
                if ("Win".equals(fields[0]))
                    move[0] = -1;
                if ("Tie".equals(fields[0]))
                    move[0] = -2;

                move[1] = Integer.parseInt(fields[1]);
                move[2] = Integer.parseInt(fields[2]);
                return move;
            }
        } catch (JMSException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    public void close() {
        try {
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
