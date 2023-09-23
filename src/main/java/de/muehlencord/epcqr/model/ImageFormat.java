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

package de.muehlencord.epcqr.model;

import lombok.Getter;

/**
 * supported image output formats
 *
 * @author Joern Muehlencord, 2023-09-19
 * @since 1.0.0
 */
@Getter
public enum ImageFormat {

  /**
   * image format bmp
   */
  BMP("bmp"),
  /**
   * image format tif
   */
  TIF("tif"),
  /**
   * image format pnm
   */
  PNM("pnm"),
  /**
   * image format pcx
   */
  PCX("pcx"),
  /**
   * image format png
   */
  PNG("png"),
  /**
   * image format gif
   */
  GIF("gif"),
  /**
   * image format jpg
   */
  JPG("jpg");

  private String name;

  ImageFormat(String name) {
    this.name = name;
  }
}
