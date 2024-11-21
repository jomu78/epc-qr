/*
 * Copyright 2023 Joern Muehlencord, https://muehlencor.dde
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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import de.muehlencord.epcqr.model.ImageFormat;
import lombok.Getter;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * output QrCode has Bae64 encoded image.
 *
 * @author Joern Muehlencord, 2023-09-19
 * @since 1.0.0
 */

public class Base64ImageGenerator extends AbstractImageGenerator implements QrCodeGenerator<String> {

  /**
   * create a new image generator which return the rendered image base64 encoded string.
   */
  public Base64ImageGenerator() {
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
  }

  /* *** builder *** */

  /**
   * set the target width of the QR code image
   * @param width the width to set
   * @return the build object
   */
  public Base64ImageGenerator withWidth(int width) {
    this.width = width;
    return this;
  }

  /**
   * set the target height of the QR code image
   * @param height the width to set
   * @return the build object
   */
  public Base64ImageGenerator withHeight(int height) {
    this.height = height;
    return this;
  }


  /**
   * create the QR code
   * @param data the EPC data in the specified format. Use the EpcBuilder to generate it
   * @param hints the hints to use
   * @return the rendered QR code as base64 encoded string
   * @throws EpcException if the QR code cannot be rendered.
   */
  protected String createQR(
    String data, Map<EncodeHintType, ErrorCorrectionLevel> hints
  ) throws EpcException {

    try {
      BitMatrix matrix = new MultiFormatWriter()
        .encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);

      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      MatrixToImageWriter.writeToStream(matrix, format.getName(), outputStream);
      return new String(Base64.getEncoder().encode(outputStream.toByteArray()));
    } catch (Exception ex) {
      throw new EpcException(String.format("Failed to generate QR code. Reason: %s", ExceptionUtils.getRootCauseMessage(ex)), ex);
    }
  }
}
