package roseli.rabbitmq.sender;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Sender {

	private static final String QUEUE_NAME = "rabbitmq-queue";

	public static void main(String[] args) {

	

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		try {
			// Cria a conexão com o "Rabbit Server" e informa o canal
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection conecta = factory.newConnection();
			Channel canal = conecta.createChannel();

			// Informa a fila
			canal.queueDeclare(QUEUE_NAME, true, false, false, null);

			System.out.println("Digite a mensagem que será enviada (Digite 'sair' para encerrar): ");
			String message = in.readLine();

			// Envia a mensagem para a fila
			while (!message.equals("sair")) {
				if (!message.trim().isEmpty()) {
					canal.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
				}

				message = in.readLine();
			}

			// Fecha o canal e a conexão
			canal.close();
			conecta.close();
		} catch (Exception e) {
			System.out.println(String.format("Erro!!!", e.getMessage(), e));
		}
	}

}