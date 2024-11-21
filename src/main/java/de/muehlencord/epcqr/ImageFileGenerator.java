
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
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Create an QR Code image and write it to a file.
 *
 * @author Joern Muehlencord, 2023-09-20
 * @since 1.0.0
 */
public class ImageFileGenerator extends AbstractImageGenerator implements QrCodeGenerator<String> {

  private Path outputFile = Paths.get(System.getProperty("java.io.tmpdir"), "test.png");

  /* *** builder *** */

  /**
   * sets the output file to store the created QR code to.
   *
   * @param outputFile the full file name to use.
   * @return the builder.
   */
  public ImageFileGenerator withOutputFile(String outputFile) {
    this.outputFile = Paths.get(outputFile);
    return this;
  }

  /**
   * sets the width of the image to create
   *
   * @param width the width of the image to create.
   * @return the builder.
   */
  public ImageFileGenerator withWidth(int width) {
    this.width = width;
    return this;
  }

  /**
   * sets the height of the image to create
   *
   * @param height the height of the image to create.
   * @return the builder.
   */
  public ImageFileGenerator withHeight(int height) {
    this.height = height;
    return this;
  }


  /**
   * create the QR code
   *
   * @param data  the EPC data in the specified format. Use the EpcBuilder to generate it
   * @param hints the hints to use
   * @return the filename the image has been stored under.
   * @throws EpcException if the QR code cannot be rendered.
   */
  protected String createQR(
    String data, Map<EncodeHintType, ErrorCorrectionLevel> hints
  ) throws EpcException {

    try {
      BitMatrix matrix = new MultiFormatWriter()
        .encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);

      MatrixToImageWriter.writeToPath(matrix, this.format.getName(), outputFile);
      return outputFile.toString();
    } catch (Exception ex) {
      throw new EpcException(String.format("Failed to generate QR code. Reason: %s", ExceptionUtils.getRootCauseMessage(ex)), ex);
    }
  }
}
