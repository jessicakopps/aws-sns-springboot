package br.mso.awssns;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AwssnsApplication {
	
	@Autowired
	private AmazonSNSClient snsClient;
	
	String TOPIC_ARN = "";
	
	@GetMapping("/addSubscription/{email}")
	public String addSubscription(@PathVariable String email) {
		SubscribeRequest request = new SubscribeRequest(TOPIC_ARN, "email", email);
		snsClient.subscribe(request);
		return "Subscription request está pendente. Para confirmar, verifique seu email:" + email;
	}
	
	@GetMapping("/sendNotification")
	public String publishMessageToTopic(){
		PublishRequest publishRequest = new PublishRequest(TOPIC_ARN, buildEmailBody(), "Notification: Network connectivity issue");
		snsClient.publish(publishRequest);
		return "Notificação enviada com sucesso!";
	}
	
	private String buildEmailBody(){
		return "Querido cliente,\n" +
				"\n" +
				"\n" +
				"Pague o que você deve, do contrário o serasa vai te pegar";
	}

	public static void main(String[] args) {
		SpringApplication.run(AwssnsApplication.class, args);
	}

}