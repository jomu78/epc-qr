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

import de.muehlencord.epcqr.EpcException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * supported currency
 *
 * @author Joern Muehlencord, 2023-09-19
 * @since 1.0.0
 */
@Getter
public enum Currency {

  /**
   * EURO
   */
  EUR("EUR");

  private String label;

  Currency(String label) {
    this.label = label;
  }

  private final static Map<String, Currency> labelMap = new HashMap<>();

  static {
    for (Currency currency : Currency.values()) {
      labelMap.put(currency.getLabel(), currency);
    }
  }

  /**
   * create a new Currency instance using the label of it.
   *
   * @param label the label to use to create the instance from
   * @return the currency with the given label.
   * @throws EpcException if no currency with the given label is available.
   */
  public static Currency getInstanceByLabel(String label) throws EpcException {
    if (!labelMap.containsKey(label)) {
      throw new EpcException(String.format("Currency %s not found", label));
    }
    return labelMap.get(label);
  }


}
