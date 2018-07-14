package coinstack.document.util;

public class DataBase {
	// Just Concept
	private String originHash;
	private String clientAddress;
	private String filePath;
	private String transactionId;

	public void setOriginHash(String originHash) {
		this.originHash = originHash;
	}

	public String getOriginHash() {
		return this.originHash;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public String getClientAddress() {
		return this.clientAddress;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionId() {
		return this.transactionId;
	}
}
