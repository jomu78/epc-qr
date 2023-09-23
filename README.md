epc-qr
======

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://raw.githubusercontent.com/jomu78/epc-qr/master/LICENSE.txt)
[![Build Status](https://github.com/jomu78/epc-qr/actions/workflows/maven.yml/badge.svg)](https://github.com/jomu78/epc-qr/actions/workflows/maven.yml)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/de.muehlencord.epcqr/epc-qr/badge.svg)](https://maven-badges.herokuapp.com/maven-central/de.muehlencord.epcqr/epc-qr)
[![JavaDoc](https://javadoc.io/badge/de.muehlencord.epcqr/epc-qr.svg)](https://www.javadoc.io/doc/de.muehlencord.epcqr/epc-qr)
[![Java Doc](https://snyk.io/test/github/jomu78/epc-qr/badge.svg?style=flat)](https://snyk.io/test/github/jomu78/epc-qr)





A Java library for generation of EPC QR Code.

The European Payments Council Quick Response Code guidelines define the content of a QR code that can be used to initiate 
<a href="https://en.wikipedia.org/wiki/Single_Euro_Payments_Area" taget="_blank">SEPA</a> credit transfer (SCT).


#### epc-qr quick examples:

```java
    // specify data of the epc code
    var builder = new EpcBuilder()
      .withRecipient("Max Mustermann")
      .withIban("GB33BUKB20201555555555")
      .withPaymentAmount(48.81D)
      .withPurposeText("Test");

    // get the epc-qr code as hase64 encoded image
    String base64 = new Base64ImageGenerator().generate(builder);
```

![example output](example.png)


#### Maven dependency:

```xml

<dependency>
  <groupId>de.muehlencord.epcqr</groupId>
  <artifactId>epc-qr</artifactId>
  <version>1.0.0</version>
</dependency>
```

![Compatibility Badge](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)

#### References

- https://en.wikipedia.org/wiki/EPC_QR_code
- https://www.iban.com/testibans

## License
Copyright 2023 Jörn Mühlencord

Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
