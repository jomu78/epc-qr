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

import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import de.muehlencord.epcqr.model.ImageFormat;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * abstract image generator with some default implementations.
 *
 * @author Joern Muehlencord, 2023-09-23
 * @since 1.0.0
 */
public abstract class AbstractImageGenerator implements QrCodeGenerator<String>{
  /**
   * the charset to use inside the image when generating it. Defaults to UTF-8.
   */
  protected String charset = "UTF-8";

  /**
   * the encoding hints to be used when rendering the QR code.
   */
  protected final Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();

  /**
   * the width of the image to be generated.  Defaults to 300 pixel.
   */
  @Getter
  protected int width = 300;

  /**
   * the height of the image to be generated. Defaults to 300 pixel.
   */
  @Getter
  protected int height = 300;

  /**
   * the output format of the image to be used when rendering the image. Defaults to PNG.
   */
  @Getter
  protected ImageFormat format = ImageFormat.PNG;

  /**
   * create the QR code
   * @param data the EPC data in the specified format. Use the EpcBuilder to generate it
   * @param hints the hints to use
   * @return depends on the implementation of the image generator.
   * @throws EpcException if the QR code cannot be rendered.
   */
  abstract protected String createQR(String data, Map<EncodeHintType, ErrorCorrectionLevel> hints) throws EpcException;


  /**
   * generate based on the give builder a QR code
   *
   * @param builder the builder to use
   * @return the returned output image as bae64 encoded string
   * @throws EpcException if the generation failed .
   */
  @Override
  public String generate(EpcBuilder builder) throws EpcException {
    String data = builder.build();
    this.charset = builder.getEncoding().getCharset().name();
    return createQR(data, hints);
  }

}
