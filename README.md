# HTTPS

Obtain a free SSL certificate from [here](sslforfree.com). To create the .jks file required for HTTPS, run the following commands:

`openssl pkcs12 -export -out georgi.pkcs12 -inkey private.key -in certificate.crt -certfile ca_bundle.crt`

and then

`openssl pkcs12 -in georgi.pkcs12 -out cert_key.pem -nodes`

Copy the resulting `PEM` file + private key to the page settings in GitLab.

# License

Copyright 2018 Georgi Christov

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
