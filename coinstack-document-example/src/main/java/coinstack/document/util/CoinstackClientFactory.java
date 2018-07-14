package coinstack.document.util;

import static coinstack.document.env.Configuration.ENDPOINT;

import io.blocko.coinstack.AbstractEndpoint;
import io.blocko.coinstack.CoinStackClient;
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

	  public CoinStackClient create() {
	    return coinstackClient;
	  }
	}