package de.fiduciagad.sharea.server.data.repository.dto;

import java.security.GeneralSecurityException;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

import com.google.common.base.Preconditions;

public class AccessToken extends CouchDbDocument {

	private static final long serialVersionUID = 1L;

	private static final int KEY_LENGTH = 192;

	public static AccessToken createRandom(String deviceName, String deviceIdentifier) throws GeneralSecurityException {
		Preconditions.checkNotNull(deviceName, "A device name has to be set.");
		Preconditions.checkNotNull(deviceIdentifier, "A device identifier has to be set.");

		AccessToken token = new AccessToken(generate());
		token.deviceName = deviceName;
		token.devideIdentifier = deviceIdentifier;
		token.created = new Date();
		token.lastUsed = new Date();
		return token;
	}

	private static String generate() throws GeneralSecurityException {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(KEY_LENGTH);
			SecretKey secretKey = keyGen.generateKey();
			byte[] encoded = secretKey.getEncoded();
			return DatatypeConverter.printHexBinary(encoded).toLowerCase();
		} catch (Exception e) {
			throw new GeneralSecurityException("Cannot generate new Token.", e);
		}
	}

	@JsonProperty("docType")
	@TypeDiscriminator(value = "doc.docType === 'AccessToken'")
	private final String docType = "AccessToken";

	private Date created;

	private String deviceName;

	private String devideIdentifier;

	private Date lastUsed;

	private String tokenText;

	private String owningAccountId;

	private AccessToken() {
	}

	private AccessToken(String tokenText) {
		this.tokenText = tokenText;
	}

	public Date getCreated() {
		return created;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public String getDevideIdentifier() {
		return devideIdentifier;
	}

	public Date getLastUsed() {
		return lastUsed;
	}

	public String getOwningAccountId() {
		return owningAccountId;
	}

	public String getTokenText() {
		return tokenText;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public void setDevideIdentifier(String devideIdentifier) {
		this.devideIdentifier = devideIdentifier;
	}

	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}

	public void setOwningAccountId(String owningAccountId) {
		this.owningAccountId = owningAccountId;
	}

	public void setTokenText(String token) {
		tokenText = token;
	}

}