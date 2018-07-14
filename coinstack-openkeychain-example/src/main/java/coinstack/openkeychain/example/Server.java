package coinstack.openkeychain.example;

import coinstack.openkeychain.env.OpenkeyChainServer;
import io.blocko.coinstack.CoinStackClient;
import io.blocko.coinstack.openkeychain.InMemoryKeyManager;
import io.blocko.coinstack.openkeychain.KeyManager;
import io.blocko.coinstack.openkeychain.model.Challenge;
import io.blocko.coinstack.openkeychain.model.RegistrationMetadata;
import io.blocko.coinstack.openkeychain.model.Response;
import io.blocko.coinstack.openkeychain.server.AuthorizationManager;
import io.blocko.coinstack.openkeychain.server.ChallengeResponseManager;
import io.blocko.coinstack.openkeychain.server.RegistrationManager;
import java.io.IOException;

public class Server implements OpenkeyChainServer {
  private final KeyManager keyManager;
  private final ChallengeResponseManager shakeManager;
  private final RegistrationManager regManager;
  private final AuthorizationManager authManager;

  public Server(CoinStackClient client) {
    keyManager = new InMemoryKeyManager();
    shakeManager = new RegistrationManager(client, keyManager);
    authManager = new AuthorizationManager(client, keyManager);
    regManager = new RegistrationManager(client, keyManager);
  }

  public void registerKey(String serverAddress, String privateKey) {
    keyManager.registerKey(serverAddress, privateKey.toCharArray());
  }

  public void registerAllAddress(String[] authAddresses) {
    keyManager.registerAllAddress(authAddresses);
  }

  public void setTimeOut(int value) {
    shakeManager.setThresholdMinutes(value);
  }

  public Challenge createChallenge(String message) {
    return shakeManager.createChallenge(message);
  }

  public boolean checkResponse(Response response) {
    return shakeManager.checkResponse(response);
  }

  public String recordRegistration(String clientAddress, RegistrationMetadata metadata)
      throws IOException {
    return regManager.recordRegistration(clientAddress, metadata);
  }

  public String revokeRegistration(String clientAddress) throws IOException {
    return regManager.revokeRegistration(clientAddress);
  }

  public byte[] checkRegistration(String clientAddress) throws IOException {
    return authManager.checkRegistration(clientAddress);
  }


}
