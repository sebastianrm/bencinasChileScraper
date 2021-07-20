# bencinasChileScraper
Web Scraper para http://www.bencinaenlinea.cl

## librerias utilizadas

1.- Spring Boot 2.4.8
2.- htmlUnit 2.44.0
3.- Super-cvs 2.40

## Flujo de operacion:

Se conecta la la pagina http://www.bencinaenlinea.cl.
hace clik en la primera region.
Para cada tipo de combustibles, recupera los resultados y los almacena en una lista.
asi para cada region.
Al terminar crea un archivo del tipo cvs, con la fecha actual.

