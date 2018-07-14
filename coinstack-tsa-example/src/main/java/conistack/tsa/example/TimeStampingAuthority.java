package conistack.tsa.example;

import io.blocko.coinstack.exception.CoinStackException;
import io.blocko.coinstack.model.Stamp;
import java.io.IOException;

public interface TimeStampingAuthority {

  /**
   * 문서의 hash값을 등록해주는 메소드.
   * 
   * @param privateKey 개인키
   * @param hash 문서의 hash
   * @return stampId
   * @throws IOException
   * @throws CoinStackException
   */
  public String registerDocument(String privateKey, String hash)
      throws IOException, CoinStackException;

  /**
   * StampId를 통해 stamp 정보를 조회해주는 메소드.
   * 
   * @param stampId stampId
   * @return stampInfo
   * @throws IOException
   * @throws CoinStackException
   */
  public Stamp getDocument(String stampId) throws IOException, CoinStackException;
}
