package coinstack.document.service;

import java.io.IOException;

import coinstack.document.util.Json;
import io.blocko.coinstack.ECDSA;
import io.blocko.coinstack.exception.CoinStackException;
import io.blocko.json.JSONException;
import io.blocko.json.JSONObject;

public class VerifyService {
	public void isNotModified(String transactionId, String originHash)
			throws IOException, CoinStackException, JSONException {
		String data = BlockchainService.getDocumentInfo(transactionId);

		JSONObject result = Json.parse(data);
		String blockHash = (String) result.get("hash");

		System.out.println("블록체인에 등록된 해시값: " + blockHash);
		System.out.println("원본 해시값: " + originHash);

		if (originHash.equals(blockHash))
			System.out.println("일치");
		else
			System.out.println("불일치");
	}

	public void isValidSign(String sign, String signedAddress, String originHash) {
		System.out.println("서명값: " + sign);
		System.out.println("서명한 주소: " + signedAddress);
		System.out.println("원본 해시값:" + originHash);

		if (ECDSA.verifyMessageSignature(signedAddress, originHash, sign))
			System.out.println("유효한 서약입니다.");
		else
			System.out.println("잘못된 서약입니다.");
	}
}
