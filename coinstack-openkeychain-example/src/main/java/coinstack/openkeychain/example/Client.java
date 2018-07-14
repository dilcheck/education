package coinstack.openkeychain.example;

import coinstack.openkeychain.env.OpenkeyChainClient;
import io.blocko.coinstack.CoinStackClient;
import io.blocko.coinstack.openkeychain.InMemoryKeyManager;
import io.blocko.coinstack.openkeychain.KeyManager;
import io.blocko.coinstack.openkeychain.client.LoginManager;
import io.blocko.coinstack.openkeychain.model.Challenge;
import io.blocko.coinstack.openkeychain.model.Response;

public class Client implements OpenkeyChainClient {
	private final KeyManager clientKeyManager;
	private final LoginManager loginManager;

	public Client(CoinStackClient client) {
		clientKeyManager = new InMemoryKeyManager();
		loginManager = new LoginManager(client, clientKeyManager);
	}

	public void registerKey(String clientAddress, String clientPrivateKey) {
		clientKeyManager.registerKey(clientAddress, clientPrivateKey.toCharArray());
	}

	public boolean checkChallenge(Challenge challenge, String serverAddress) {
		return loginManager.checkChallenge(challenge, serverAddress);
	}

	public Response createResponse(Challenge challenge) {
		return loginManager.createResponse(challenge);
	}

}
