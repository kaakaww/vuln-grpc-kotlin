#!/usr/bin/env bash

createCertificates() {
    echo 'Starting to create certificates...'
    openssl genrsa -des3 -out rootCA.key 2048
    openssl req -x509 -new -nodes -key rootCA.key -sha256 -days 1825 -addext "subjectAltName = DNS:localhost" -out rootCA.pem
    openssl genrsa -des3 -out server.key 2048
    openssl req -new -sha256 -key server.key -addext "subjectAltName = DNS:localhost" -out server.csr
    openssl x509 -req -in server.csr -CA rootCA.pem -CAkey rootCA.key -CAcreateserial -out server.pem -days 365 -sha256
    openssl genrsa -des3 -out client.key 2048
    openssl req -new -sha256 -key client.key -addext "subjectAltName = DNS:localhost" -out client.csr
    openssl x509 -req -in client.csr -CA rootCA.pem -CAkey rootCA.key -CAcreateserial -out client.pem -days 365 -sha256
    openssl pkcs12 -export -in server.pem -out keystore.p12 -name server -nodes -inkey server.key
    openssl pkcs12 -export -in client.pem -out client-keystore.p12 -name client -nodes -inkey client.key
    keytool -import -file rootCA.pem -alias rootCA -keystore truststore.p12
}


#Validate if provided argument is present
if [[ -z "$1" ]]; then
    echo "No common name is provided to create the Client Certificate"
else
    createCertificates "$1"
fi