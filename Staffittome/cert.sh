#!/bin/bash
export CLASSPATH=bcprov-jdk16-146.jar
CERTSTORE=mystore.bks
if [ -a $CERTSTORE ]; then
    rm $CERTSTORE || exit 1
fi
keytool \
      -import \
      -v \
      -trustcacerts \
      -alias 0 \
      -file &lt;(openssl x509 -in thawte_Primary_Root_CA.pem) \
      -keystore $CERTSTORE \
      -storetype BKS \
      -provider org.bouncycastle.jce.provider.BouncyCastleProvider \
      -providerpath /usr/share/java/bcprov.jar \
      -storepass ez24get