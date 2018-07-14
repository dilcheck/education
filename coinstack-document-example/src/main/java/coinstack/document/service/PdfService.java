package coinstack.document.service;

import io.blocko.coinstack.util.PDFController;
import java.io.File;
import java.io.IOException;

public class PdfService {
	private final PDFController pdfController;
	
	public PdfService(File pdf) {
		pdfController = new PDFController(pdf);
	}
	
	public String getHash(){
		return pdfController.calculateHash();
	}
	
	public void signPdf(File pdf, String sign) throws IOException {
		pdfController.writeStampId(pdf, sign.replaceAll("/", ":")); //해결방법 찾아보기
	}
	
	public String getSign() {
		return pdfController.getStampId().replaceAll(":", "/");
	}
}
	
	
