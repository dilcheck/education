package coinstack.openkeychain.env;

import io.blocko.coinstack.openkeychain.model.Challenge;
import io.blocko.coinstack.openkeychain.model.Response;

public interface OpenkeyChainClient {
  /**
   * client의 주소 & 개인키 등록.
   * 
   * @param clientAddress
   * @param clientPrivateKey
   */
  public void registerKey(String clientAddress, String clientPrivateKey);

  /**
   * 서버로부터 전달받은 challenge 검증.
   * 
   * @param challenge
   * @param serverAddress
   * @return
   */
  public boolean checkChallenge(Challenge challenge, String serverAddress);

  /**
   * challenge에 대한 회신 생성.
   * 
   * @param challenge
   * @return
   */
  public Response createResponse(Challenge challenge);
}
