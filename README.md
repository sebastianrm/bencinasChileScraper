# bencinasChileScraper
Web Scraper para http://www.bencinaenlinea.cl

## librerias utilizadas

* Spring Boot 2.4.8
* htmlUnit 2.44.0
* Super-cvs 2.40

## Flujo de operacion:

1. Se conecta la la pagina http://www.bencinaenlinea.cl.
2. Hace clik en la primera region.
3. Para cada tipo de combustibles, recupera los resultados y los almacena en una lista.
4. asi para cada region.
5. Al terminar crea un archivo del tipo cvs, con la fecha actual.

