package coinstack.document.service;

import java.io.IOException;

import coinstack.document.util.CoinstackClientFactory;
import io.blocko.coinstack.CoinStackClient;
import io.blocko.coinstack.TransactionBuilder;
import io.blocko.coinstack.TransactionUtil;
import io.blocko.coinstack.exception.CoinStackException;
import io.blocko.coinstack.model.Output;
import io.blocko.coinstack.model.Transaction;

public class BlockchainService {
	private static final CoinStackClient client = new CoinstackClientFactory().create();

	public static String insertDocumentInfo(String privateKey, String data) throws IOException, CoinStackException {
		TransactionBuilder txBuilder = new TransactionBuilder();
		txBuilder.allowLargePayload(true);
		txBuilder.setData(data.getBytes());
		String rawTransaction;

		rawTransaction = client.createSignedTransaction(txBuilder, privateKey);
		client.sendTransaction(rawTransaction);

		return TransactionUtil.getTransactionHash(rawTransaction);
	}

	public static String getDocumentInfo(String transactionId) throws IOException, CoinStackException {
		String result = null;

		Transaction docuemntInfo = client.getTransaction(transactionId);
		Output[] outputs = docuemntInfo.getOutputs();

		for (Output output : outputs) {
			if (output.getData()!=null) {
				result = new String(output.getData());
				break;
			}
		}
		return result;
	}
}
