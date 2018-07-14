package coinstack.document.example;

import static coinstack.document.env.Configuration.CLIENT_ADDRESS;
import static coinstack.document.env.Configuration.CLIENT_PRIVATEKEY;
import static coinstack.document.env.Configuration.SERVER_PRIVATEKEY;

import coinstack.document.service.BlockchainService;
import coinstack.document.service.PdfService;
import coinstack.document.service.VerifyService;
import coinstack.document.util.DataBase;
import coinstack.document.util.Json;
import io.blocko.coinstack.ECDSA;
import io.blocko.coinstack.exception.CoinStackException;
import io.blocko.json.JSONException;
import io.blocko.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class Main {
	private static final DataBase database = new DataBase();

	public static void main(String[] args) {
		try {
			 insertDocument();
			
			 System.out.println(
			 "\n============================================================================================");
			
			 verifyDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void insertDocument() throws IOException, CoinStackException, JSONException {
		System.out.println("서약서 등록 로직");

		// client 에서 전송한 파일
		File pdf = new File("origin/paper.pdf");

		// server (서약서 해시 추출)
		PdfService pdfService = new PdfService(pdf);
		String originHash = pdfService.getHash();
		System.out.println("PDF 해시: " + originHash);

		// client (서약서 해시 서명)
		String signature = ECDSA.signMessage(CLIENT_PRIVATEKEY, originHash);
		System.out.println("서명값: " + signature);

		// server (서명값 PDF 등록)
		String filePath = "storage/paper1.pdf";
		File signedPdf = new File(filePath);
		pdfService.signPdf(signedPdf, signature);

		// server (서명값이 포함된 PDF 해시 추출)
		PdfService pdfService2 = new PdfService(signedPdf);
		String signedHash = pdfService2.getHash();
		System.out.println("서명된 PDF의 해시값: " + signedHash);

		// server (블록체인에 서약서 정보 등록)
		JSONObject blockchainData = new JSONObject();
		blockchainData.put("address", CLIENT_ADDRESS);
		blockchainData.put("hash", signedHash);

		String transactionId = BlockchainService.insertDocumentInfo(SERVER_PRIVATEKEY, Json.stringify(blockchainData));
		System.out.println("트랜젝션 ID: " + transactionId);

		// server (관련 정보 데이터 베이스 저장)
		database.setFilePath(filePath);
		database.setOriginHash(originHash);
		database.setClientAddress(CLIENT_ADDRESS);
		database.setTransactionId(transactionId);
	}

	private static void verifyDocument() throws IOException, CoinStackException, JSONException {
		System.out.println("서약서 검증 로직");

		// 데이터 베이스에서 필요한 값 조회
		String originHash = database.getOriginHash();
		String clientAddress = database.getClientAddress();
		String filePath = database.getFilePath();
		String transactionId = database.getTransactionId();

		File pdf = new File(filePath);
		PdfService pdfService = new PdfService(pdf);
		VerifyService verifyService = new VerifyService();

		System.out.println("* 위변조 검증");
		verifyService.isNotModified(transactionId, pdfService.getHash());

		System.out.println("\n* 개인 서명 검증");
		String sign = pdfService.getSign();
		verifyService.isValidSign(sign, clientAddress, originHash);
	}
}
