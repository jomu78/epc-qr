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

import com.google.zxing.WriterException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * test builder
 *
 * @author Joern Muehlencord, 2023-09-19
 * @since 1.0.0
 */
class EpcBuilderTest {


  private static final Logger logger = LoggerFactory.getLogger(EpcBuilderTest.class);

  @Test
  void testIncompleteSetup() {
    EpcException ex = assertThrows(EpcException.class, () -> {
      new EpcBuilder().build();
    });
  }

  @Test
  void testCompleteSetup() throws EpcException, IOException, WriterException {
    var expectedString = "BCD\n" +
      "002\n" +
      "1\n" +
      "SCT\n" +
      "\n" +
      "Max Mustermann\n" +
      "GB33BUKB20201555555555\n" +
      "EUR48.81\n" +
      "\n" +
      "\n" +
      "Test\n" +
      "\n";

    var builder = new EpcBuilder()
      .withRecipient("Max Mustermann")
      .withIban("GB33BUKB20201555555555")
      .withPaymentAmount(48.81D)
      .withPurposeText("Test");

    assertThat(builder.build()).isEqualTo(expectedString);

    String base64 = new Base64ImageGenerator().generate(builder);
    logger.info("base64: {}", base64);
    assertThat(base64).isNotNull();

    String fileName = new ImageFileGenerator().generate(builder);
    logger.info ("file: {}", fileName);
    assertThat(fileName).isNotNull();
    assertTrue (Paths.get(fileName).toFile().isFile());
    assertTrue (Paths.get(fileName).toFile().exists());
  }

}
