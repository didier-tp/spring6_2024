dépendances générales (grandes lignes)
======================================
web.api ---> core ---> persistence


dépendances précises en mode direct (par défaut)
================================================
core.service.direct --> persistence.repository

(core.hex.spi  , persistence.hex.adapter et core.service.hex pas utilisés)


dépendances adaptées en mode hexagonal
 (à activer via @Qualifier("hex") sur @Autoriwed de XyzService)
=============================================================
core.service.hex --> core.hex.spi
                     core.hex.spi <---- persistence.hex.adapter ---> persistence.repositoty
(et core.service.direct pas utilisé)