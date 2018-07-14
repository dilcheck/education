package coinstack.openkeychain.env;

import io.blocko.coinstack.openkeychain.model.Challenge;
import io.blocko.coinstack.openkeychain.model.RegistrationMetadata;
import io.blocko.coinstack.openkeychain.model.Response;
import java.io.IOException;

public interface OpenkeyChainServer {
  /**
   * server의 주소 & 개인키 등록.
   * @param serverAddress
   * @param privateKey
   */
  public void registerKey(String serverAddress, String privateKey);

  /**
   * 인증을 공유할 서버목록 등록.
   * @param authAddresses
   */
  public void registerAllAddress(String[] authAddresses);

  /**
   * 탐색 시간 제한 설정.
   * @param value
   */
  public void setTimeOut(int value);

  /**
   * client 검증을 위한 nonce 생성.
   * @param message
   * @return
   */
  public Challenge createChallenge(String message);

  /**
   * client의 회신 검증.
   * @param response
   * @return
   */
  public boolean checkResponse(Response response);

  /**
   * 인증서 등록.
   * @param clientAddress
   * @param metadata
   * @return
   * @throws IOException
   */
  public String recordRegistration(String clientAddress, RegistrationMetadata metadata)
      throws IOException;

  /**
   * 인증서 폐기.
   * @param clientAddress
   * @return
   * @throws IOException
   */
  public String revokeRegistration(String clientAddress) throws IOException;

  /**
   * 인증서 확인.
   * @param clientAddress
   * @return
   * @throws IOException
   */
  public byte[] checkRegistration(String clientAddress) throws IOException;
}
