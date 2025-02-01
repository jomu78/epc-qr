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

package de.muehlencord.epcqr.model;

import de.muehlencord.epcqr.EpcException;
import lombok.Getter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * encodings defined for EPC QR codes.
 *
 * @author Joern Muehlencord, 2023-09-19
 * @since 1.0.0
 */
@Getter
public enum Encoding {

  /**
   * Encoding UTF-8
   */
  UTF_8(1, StandardCharsets.UTF_8),
  /**
   * Encoding ISO-8859-1
   */
  ISO_8859_1(2, StandardCharsets.ISO_8859_1),
  /**
   * Encoding ISO-8859-2
   */
  ISO_8859_2(3, Charset.forName("ISO-8859-2")),
  /**
   * Encoding ISO-8859-4
   */
  ISO_8859_4(4, Charset.forName("ISO-8859-4")),
  /**
   * Encoding ISO-8859-5
   */
  ISO_8859_5(5, Charset.forName("ISO-8859-5")),
  /**
   * Encoding ISO-8859-7
   */
  ISO_8859_7(6, Charset.forName("ISO-8859-7")),
  // Unsupported
  // ISO_8859_10 (7, Charset.forName("ISO-8859-10")),
  /**
   * Encoding ISO-8859-15
   */
  ISO_8859_15(8, Charset.forName("ISO-8859-15"));

  private int value;
  private Charset charset;

  Encoding(int value, Charset charset) {
    this.value = value;
    this.charset = charset;
  }

  private final static Map<Integer, Encoding> valueMap = new HashMap<>();

  static {
    for (Encoding encoding : Encoding.values()) {
      valueMap.put(encoding.getValue(), encoding);
    }
  }

  /**
   * create a new Encoding using the value defined in the EPC spec
   *
   * @param value the value to use to create the instance from
   * @return the encoding with the given value
   * @throws EpcException if no encoding with the given value is available.
   */
  public static Encoding getInstanceByValue(int value) throws EpcException {
    if (!valueMap.containsKey(value)) {
      throw new EpcException(String.format("Version %s not found", value));
    }
    return valueMap.get(value);
  }
}
