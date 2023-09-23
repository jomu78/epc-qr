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
 * Add a short description of the class
 *
 * @author Joern Muehlencord, 2023-09-23
 * @since TODO - add versiom
 */
public abstract class AbstractImageGenerator implements QrCodeGenerator<String>{
  protected String charset = "UTF-8";

  protected final Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();

  @Getter
  protected int width = 300;

  @Getter
  protected int height = 300;

  @Getter
  protected ImageFormat format = ImageFormat.PNG;

  abstract protected String createQR(String data, Map<EncodeHintType, ErrorCorrectionLevel> map) throws EpcException;


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
