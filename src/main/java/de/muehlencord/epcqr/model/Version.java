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
 * EPC Version enum.
 *
 * @author Joern Muehlencord, 2023-09-19
 * @since 1.0.0
 */
@Getter
public enum Version {

  V001("001"),
  V002("002");

  private String label;

  Version(String label) {
    this.label = label;
  }

  private final static Map<String, Version> labelMap = new HashMap<>();

  static {
    for (Version version : Version.values()) {
      labelMap.put(version.label, version);
    }
  }

  public static Version getInstanceByLabel(String label) throws EpcException {
    if (label == null) {
      throw new EpcException("Version must not be null");
    }
    if (!labelMap.containsKey(label)) {
      throw new EpcException(String.format("Version %s not found", label));
    }
    return labelMap.get(label);
  }
}
