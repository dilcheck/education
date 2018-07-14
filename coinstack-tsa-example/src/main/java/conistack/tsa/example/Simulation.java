package conistack.tsa.example;

import static coinstack.tsa.env.Configuration.PRIVATE_KEY;

import coinstack.tsa.env.CoinstackClientFactory;
import io.blocko.coinstack.CoinStackClient;
import io.blocko.coinstack.exception.CoinStackException;
import io.blocko.coinstack.model.Stamp;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Simulation implements TimeStampingAuthority {
  private static final CoinStackClient client = new CoinstackClientFactory().create();

  public String registerDocument(String privateKey, String hash)
      throws IOException, CoinStackException {
    return client.stampDocument(privateKey, hash);
  }

  public Stamp getDocument(String stampId) throws IOException, CoinStackException {
    return client.getStamp(stampId);
  }

  public void printStampInfo(Stamp stamp) {
    System.out.println("\nStamp information");
    System.out.println("txId: " + stamp.getTxId());
    System.out.println("vout: " + stamp.getOutputIndex());
    System.out.println("confirmations: " + stamp.getConfirmations());
    System.out.println("timestamp: " + stamp.getTimestamp());
    System.out.println("hash: " + stamp.getHash());
  }

  // 블록체인에 등록되는 시간 비교하기 위한 메서드
  public void getServerTimeStamp() {
    long time = System.currentTimeMillis();
    SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    String formatTime = dayTime.format(new Date(time));
    System.out.println("\nServer time : " + formatTime);
  }

  public static void main(String[] args) throws IOException, CoinStackException {
    Simulation tsa = new Simulation();
    String privateKey = PRIVATE_KEY;
/*
    // 문서의 hash값 등록
    System.out.println("Document registration");
    String stampId = tsa.registerDocument(privateKey,
        "9F86D081884C7D659A2FEAA0C55AD015A3BF4F1B2B0B822CD15D6C15B0F00A08");
    System.out.println("stampId: " + stampId);
*/
    //서버 시간 조회
    tsa.getServerTimeStamp();

    // stampId를 통해 stamp 정보값 조회
    Stamp stampInfo = tsa.getDocument("fe51e18bb91bd14f262883730b0776fc21f4e5929c89c353b10ddb49c9642380-0");

    // stamp 정보값 출력
    tsa.printStampInfo(stampInfo);
  }
}
