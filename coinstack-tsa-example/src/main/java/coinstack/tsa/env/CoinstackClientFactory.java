package coinstack.tsa.env;

import static coinstack.tsa.env.Configuration.ENDPOINT;

import io.blocko.coinstack.*;
import java.security.PublicKey;

public class CoinstackClientFactory {
  private static CoinStackClient coinstackClient = new CoinStackClient(null, new AbstractEndpoint() {
    public boolean mainnet() {
      return true;
    }

    public PublicKey getPublicKey() {
      return null;
    }

    public String endpoint() {
      return ENDPOINT;
    }
  });

  public CoinstackClientFactory() {
    System.out.println("Welcome to BLOCKO's education\n");
  }

  public CoinStackClient create() {
    return coinstackClient;
  }
}
