/*
 * Copyright 2023 Joern Muehlencord, https://muehlencord.de
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package de.muehlencord.epcqr;

import de.muehlencord.epcqr.model.Currency;
import de.muehlencord.epcqr.model.Encoding;
import de.muehlencord.epcqr.model.Version;
import lombok.Getter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * builder to setup Epc string to be converted to a QR code
 *
 * @author Joern Muehlencord, 2023-09-19
 * @since 1.0.0
 */

@Getter
public class EpcBuilder {

  private final DecimalFormat numberFormat;

  private Version version;
  private Encoding encoding;
  private String bic = null;
  private String recipient;
  private String iban;

  private Currency currency;
  private BigDecimal paymentAmount;

  private String purposeCode = null;
  private String structuredReference = null;
  private String purposeText;
  private String note;

  /**
   * create a new instance of the EpcBuilder. By default it uses Version 2 of the EPC spec with
   * UTF-8 encoding and currency Euro.
   */
  public EpcBuilder() {
    DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
    decimalFormatSymbols.setDecimalSeparator('.');

    this.numberFormat = new DecimalFormat("#.##", decimalFormatSymbols);

    this.version = Version.V002;
    this.encoding = Encoding.UTF_8;
    this.currency = Currency.EUR;
  }

  /* *** setter *** */

  /**
   * sets the version to be used.
   *
   * @param version the version to be used. See Version#label
   * @return the builder
   * @throws EpcException if no version with the defined version label is available.
   */
  public EpcBuilder withVersion(String version) throws EpcException {
    this.version = Version.getInstanceByLabel(version);
    return this;
  }

  /**
   * sets the version to be used.
   *
   * @param version the version to be used. See Version
   * @return the builder
   * @throws EpcException If an invalid version or null is given.
   */
  public EpcBuilder withVersion(Version version) throws EpcException {
    if (version == null) {
      throw new EpcException("Version must not be null");
    }
    this.version = version;
    return this;
  }

  /**
   * sets the encoding to be used.
   *
   * @param encoding the value of the encoding to be used. See Encoding#value
   * @return the builder
   * @throws EpcException if no Encoding with the given encoding value is avaibale.
   */
  public EpcBuilder withEncoding(int encoding) throws EpcException {
    this.encoding = Encoding.getInstanceByValue(encoding);
    return this;
  }

  /**
   * sets the encoding to be used.
   *
   * @param encoding the encoding to be used. See Encoding
   * @return the builder
   * @throws EpcException if no or invalid encoding is given
   */
  public EpcBuilder withEncoding(Encoding encoding) throws EpcException {
    if (encoding == null) {
      throw new EpcException("Encoding must not be null");
    }
    this.encoding = encoding;
    return this;
  }

  /**
   * sets the bic to be used.
   *
   * @param bic the value of the bic to be used. When using Version 2 of EPC, the value of the BIC is optional for SCT transfers.
   * @return the builder
   * @throws EpcException if bic is null
   */
  public EpcBuilder withBic(String bic) throws EpcException {
    if (bic == null) {
      throw new EpcException("BIC must not be null");
    }
    this.bic = bic;
    return this;
  }

  /**
   * sets the recipient to be used.
   *
   * @param recipient sets the name of the recipient
   * @return the builder
   * @throws EpcException if the recipient is null or exceeds a length of 70 characters
   */
  public EpcBuilder withRecipient(String recipient) throws EpcException {
    if (recipient == null) {
      throw new EpcException("Recipient must not be null");
    }
    assertLength("recipient", recipient, 70);
    this.recipient = recipient;
    return this;
  }

  /**
   * sets the iban to be used.
   *
   * @param iban sets the iban to be used. Note: there is NO validation of the iban specified.
   * @return the builder
   * @throws EpcException if the iban is null.
   */
  public EpcBuilder withIban(String iban) throws EpcException {
    if (iban == null) {
      throw new EpcException("IBAN must not be null");
    }
    try {
      this.iban = iban.trim().replace(" ", "");
      return this;
    } catch (Exception ex) {
      throw new EpcException(String.format("IBAN not valid. %s", ex.getMessage()));
    }
  }

  /**
   * sets the currency to be used.
   *
   * @param currency the currency to be used. See Currency#label.
   * @return the builder
   * @throws EpcException if the currency is null or no currency with the given label is available.
   */
  public EpcBuilder withCurrency(String currency) throws EpcException {
    this.currency = Currency.getInstanceByLabel(currency);
    return this;
  }

  /**
   * sets the currency to be used.
   *
   * @param currency the currency to be used. See Currency.
   * @return the builder
   * @throws EpcException if the currency is null
   */
  public EpcBuilder withCurrency(Currency currency) throws EpcException {
    if (currency == null) {
      throw new EpcException("Currency must not be null");
    }
    this.currency = currency;
    return this;
  }

  /**
   * the payment amount to be used.
   *
   * @param paymentAmount the payment amount to be used
   * @return the builder
   */
  public EpcBuilder withPaymentAmount(double paymentAmount) {
    this.paymentAmount = BigDecimal.valueOf(paymentAmount);
    return this;
  }

  /**
   * the payment amount to be used.
   *
   * @param paymentAmount the payment amount to be used
   * @return the builder
   * @throws EpcException if the payment amount is null
   */
  public EpcBuilder withPaymentAmount(BigDecimal paymentAmount) throws EpcException {
    if (paymentAmount == null) {
      throw new EpcException("PaymentAmount must not be null");
    }
    this.paymentAmount = paymentAmount;
    return this;
  }

  /**
   * sets the purpose code - currently not yet supported
   * @param purposeCode the purpose code
   * @return the builder
   * @throws EpcException always, support of purpose codes not yet implemented.
   */
  public EpcBuilder withPurposeCode(String purposeCode) throws EpcException {
    throw new EpcException("purposeCode not yet supported");
  }

  /**
   * sets the reference code - currently not yet supported
   * @param reference the purpose code
   * @return the builder
   * @throws EpcException always, support of reference codes not yet implemented.
   */
  public EpcBuilder withReference(String reference) throws EpcException {
    throw new EpcException("structured reference not yet supported");
  }

  /**
   * sets the purpose text
   * @param text the text to use as purpose
   * @return the builder
   * @throws EpcException if the text cannot be set - e.g. because the text is longer thank 140 characters.
   */
  public EpcBuilder withPurposeText(String text) throws EpcException {
    assertLength("purposeText", text, 140);
    this.purposeText = text;
    return this;
  }

  /**
   * sets the note text
   * @param note the note to use
   * @return the builder
   * @throws EpcException if the note cannot be set - e.g. because the text is longer thank 70 characters.
   */
  public EpcBuilder withNote(String note) throws EpcException {
    assertLength("note", note, 70);
    this.note = note;
    return this;
  }



  /* *** validation *** */

  private void assertLength(String key, String value, int maxLength) throws EpcException {
    if (value == null || value.isEmpty() || value.length() > maxLength) {
      throw new EpcException(String.format("value=%s for %s does not match length 1-%s", value, key, maxLength));
    }
  }

  private void assertSet(String key, Object value) throws EpcException {
    if (value == null) {
      throw new EpcException(String.format("%s must not be null", key));
    }
  }

  private void validate() throws EpcException {
    if (Version.V001.equals(version) && bic == null) {
      // version 1 requires BIC to be set
      throw new EpcException("BIC must be set when using Version 001");
    }
    assertSet("recipient", recipient);
    assertSet("iban", iban);
    assertSet("currency", currency);
    assertSet("paymentAmount", paymentAmount);
    assertSet("purposeText", purposeText);
  }


  /* *** build *** */

  private String getValueString(String value) {
    if (value == null) {
      return "";
    } else {
      return value.trim();
    }
  }

  /**
   * returns the value as formatted string in EPC format.
   *
   * @param currency the currency to use
   * @param value    the value of to use
   * @return the value as EPC formatted string.
   * @throws EpcException if the string cannot be constructed.
   */
  private String getValueString(Currency currency, BigDecimal value) throws EpcException {
    if (currency == null || value == null) {
      throw new EpcException("Currency and value are required");
    }
    return String.format("%s%s", currency.getLabel(), numberFormat.format(value.doubleValue()));
  }


  /**
   * returns a EPC validated string - see <a href="https://en.wikipedia.org/wiki/EPC_QR_code">
   * https://en.wikipedia.org/wiki/EPC_QR_code</a>.
   *
   * @return an EPC validated string
   * @throws EpcException if the setup is incomplete - e.g. mandatory information like recipient name
   *                      are missing.
   */
  public String build() throws EpcException {
    validate();

    StringBuilder sb = new StringBuilder();
    // 1 - BCD
    sb.append("BCD").append(System.lineSeparator());
    // 2 - Version
    sb.append(getValueString(version.getLabel())).append(System.lineSeparator());
    // 3 - Encoding
    sb.append(encoding.getValue()).append(System.lineSeparator());
    // 4 - Identification
    sb.append("SCT").append(System.lineSeparator());
    // 5 - BIC
    sb.append(getValueString(bic)).append(System.lineSeparator());
    // 6 - recipient
    sb.append(getValueString(recipient)).append(System.lineSeparator());
    // 7 - IBAN
    sb.append(getValueString(iban)).append(System.lineSeparator());
    // 8 - value
    sb.append(getValueString(currency, paymentAmount)).append(System.lineSeparator());
    // 9 - purposeCode
    sb.append(getValueString(purposeCode)).append(System.lineSeparator());
    // 10 - structured reference
    sb.append(getValueString(structuredReference)).append(System.lineSeparator());
    // 11 - purposeText
    sb.append(getValueString(purposeText)).append(System.lineSeparator());
    // 12 - note
    sb.append(getValueString(note)).append(System.lineSeparator());

    return sb.toString();
  }


}
