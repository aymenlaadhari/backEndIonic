package de.fiduciagad.sharea.server.rest;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.neovisionaries.i18n.CurrencyCode;

import de.deltatree.tools.qrsct.QRSCT;
import de.deltatree.tools.qrsct.QRSCTCharacterSetEnum;
import de.deltatree.tools.qrsct.QRSCTPurposeEnum;
import de.deltatree.tools.qrsct.QRSCTServiceTagEnum;
import de.deltatree.tools.qrsct.QRSCTVersionEnum;

@RestController
public class GirocodeController {

	@RequestMapping("/girocode")
	public HttpEntity<byte[]> getCredentials(
			@RequestParam(value = "amount") double amount,
			@RequestParam(value = "currency") String currency)
			throws UnsupportedEncodingException {

		String build = new QRSCT().serviceTag(QRSCTServiceTagEnum.DEFAULT)
				.version(QRSCTVersionEnum.DEFAULT)
				.characterSet(QRSCTCharacterSetEnum.DEFAULT).bic("GENODE61ORH") //$NON-NLS-1$
				.name("DeltaTree - Alexander Widak") //$NON-NLS-1$
				.iban("DE21663916000034000832") //$NON-NLS-1$
				.amount(CurrencyCode.EUR, amount)
				.purpose(QRSCTPurposeEnum.DEFAULT).reference("Share.A :-)") //$NON-NLS-1$ //$NON-NLS-2$
				.build();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		QRCode.from(build).to(ImageType.PNG).withCharset("UTF-8") //$NON-NLS-1$
				.withErrorCorrection(ErrorCorrectionLevel.M).writeTo(baos);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		headers.setContentLength(baos.size());

		return new HttpEntity<byte[]>(baos.toByteArray(), headers);

	}
}
