pour préparer base productDB:
* dans src/script/h2/setEnv.bat modifier MVN_REPOSITORY (ex: set MVN_REPOSITORY=C:\Users\Administrateur\.m2\repository)
* dans src/script/h2/lancer_console_h2_productdb.bat
puis copier/coller et exécuter dans l'ordre
les scripts suivants:
   * src/main/resources/sql/batch-schema.sql
   * src/main/resources/sql/insert-product.sql
   * src/main/resources/sql/select-product.sql
* bien se déconnecter de la console h2 pour ne pas vérouiller la base