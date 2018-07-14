package coinstack.openkeychain.example;

import static coinstack.openkeychain.env.Configuration.AUTH_ADDRESSES;
import static coinstack.openkeychain.env.Configuration.SERVER_ADDRESS;
import static coinstack.openkeychain.env.Configuration.CLIENT_ADDRESS;
import static coinstack.openkeychain.env.Configuration.CLIENT_PRIVATEKEY;
import static coinstack.openkeychain.env.Configuration.SERVER_PRIVATEKEY;

import coinstack.openkeychain.env.CoinstackClientFactory;
import java.io.IOException;
import io.blocko.coinstack.CoinStackClient;
import io.blocko.coinstack.openkeychain.model.Challenge;
import io.blocko.coinstack.openkeychain.model.RegistrationMetadata;
import io.blocko.coinstack.openkeychain.model.Response;

public class Simulation {
  // 테스트 준비
  private static final CoinStackClient coinstackClient = new CoinstackClientFactory().create();
  private static final Server server = new Server(coinstackClient);
  private static final Client client = new Client(coinstackClient);

  // 서버 환경 변수 설정
  private static final String serverAddress = SERVER_ADDRESS;
  private static final String serverPrivateKey = SERVER_PRIVATEKEY;
  private static final String[] authAddresses = AUTH_ADDRESSES;

  // 클라이언트 환경 변수 설정
  private static final String clientAddress = CLIENT_ADDRESS;
  private static final String clientPrivateKey = CLIENT_PRIVATEKEY;
  
  public static void settingTest() {
    server.registerKey(serverAddress, serverPrivateKey);
    server.registerAllAddress(authAddresses);
    server.setTimeOut(Integer.MAX_VALUE);
  }

  public static void challengeAndResponse() {
    // server side
    System.out.println("Challenge & Response test\n");

    System.out.println("Server side");

    Challenge challenge = server.createChallenge("OpenkeyChain Education");
    System.out.println("#Create challenge");
    System.out.println("  context: " + challenge.getContext());
    System.out.println("  timeStamp: " + challenge.getTimestamp());
    System.out.println("  signature: " + challenge.getSignature());

    // client side
    System.out.println("\nClient side");
    client.registerKey(clientAddress, clientPrivateKey);

    System.out.println("#checkChallenge: " + client.checkChallenge(challenge, serverAddress));
    Response response = client.createResponse(challenge);
    System.out.println("  certificate: " + response.getCertificate());
    System.out.println("  challenge: " + response.getChallenge());
    System.out.println("  timeStamp: " + response.getTimestamp());
    System.out.println("  signature: " + response.getSignature());

    // server side
    System.out.println("\nServer side again");
    System.out.println("#checkResponse: " + server.checkResponse(response));
  }

  public static void blockchainRecord() {
    try {
      System.out.println("Blockchain record test\n");
      
      //set metadata(identity)
      RegistrationMetadata metadata = new RegistrationMetadata() {
        @Override
        public byte[] marshal() {
          return "identity".getBytes();
        }
      };
/*
      System.out.println("Regesteration record");
      System.out.println("Regestering new record for " + clientAddress);
      String registrationTxId = server.recordRegistration(clientAddress, metadata);
      System.out.println("Registration txId : " + registrationTxId);
      
      System.out.println("\nCheck record");
      byte[] beforeRecordedMetadata = server.checkRegistration(clientAddress);
      System.out.println("Recorded metadata: " + new String(beforeRecordedMetadata));
      */
      // 한번이라도 폐기시키면 사용 불가
      System.out.println("\nrevokation record");
      String revokationRecord = server.revokeRegistration(clientAddress);
      System.out.println("revokation txId: " + revokationRecord);
      
      System.out.println("\nCheck record");
      byte[] afterRecordedMetadata = server.checkRegistration(clientAddress);
      System.out.println("Recorded metadata: " + new String(afterRecordedMetadata));
      
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  };

  public static void main(String[] args) {
    settingTest();
    
    // CHAPTER1
    //challengeAndResponse();

    // CHAPTER2
    blockchainRecord();

  }
}
