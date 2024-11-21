
REM set MVN_REPOSITORY=C:\ext\mvn-repository
REM set MVN_REPOSITORY=C:\Users\d2fde\.m2\repository
set MVN_REPOSITORY=C:\Users\Administrateur\.m2\repository
REM set MVN_REPOSITORY=C:\Users\formation\.m2\repository

set MY_H2_DB_URL_PRODUCT=jdbc:h2:~/productDb
set MY_H2_DB_URL_JOBREPOSITORY=jdbc:h2:~/jobRepositoryDb

set PATH="C:\Program Files\Java\jdk-21\bin"

set H2_VERSION=2.2.224
set H2_CLASSPATH=%MVN_REPOSITORY%\com\h2database\h2\%H2_VERSION%\h2-%H2_VERSION%.jar
