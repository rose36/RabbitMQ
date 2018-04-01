package roseli.rabbitmq.receiver;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Receiver {

	private static final String QUEUE_NAME = "rabbitmq-queue";

	public static void main(String[] args) {

		System.out.println("Consumindo as mensagens...");

		try {
			// Cria conexão 
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection conecta = factory.newConnection();

			// Cria o canal e a fila
			Channel canal = conecta.createChannel();
			canal.queueDeclare(QUEUE_NAME, true, false, false, null);

			// Cria o consumidor
			QueueingConsumer consumidor = new QueueingConsumer(canal);
			canal.basicConsume(QUEUE_NAME, true, consumidor);

			while(true) {
				// Pega a próxima mensagem
				QueueingConsumer.Delivery delivery = consumidor.nextDelivery();

				// Mostra a mensagem consumida
				System.out.println(new String(delivery.getBody()));
			}
		} catch (Exception e) {
			System.out.println(String.format("Erro!!!", e.getMessage(), e));
		}
	}

}