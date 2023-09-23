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

/**
 * Exception thrown when builder is used with wrong data or build is called without required data set.
 *
 * @author Joern Muehlencord, 2023-09-19
 * @since 1.0.0
 */
public class EpcException extends Exception {

  public EpcException(String message) {
    super(message);
  }

  public EpcException(String message, Throwable cause) {
    super(message, cause);
  }
}
