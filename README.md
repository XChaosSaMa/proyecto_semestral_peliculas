# proyecto_semestral_peliculas
Proyecto Semestral Programación II

Definición del Problema: 
1. Se necesita automatizar la gestión de un local de venta de Películas.
2. Las Películas están organizados por géneros, cada género puede incluir varias películas pero una película solo puede pertenecer a un
género.
3. De las películas se requiere tener un código que sea un identificador único, el nombre de la película, el precio unitario, el formato y el
stock existente en el local.
4. El dueño de la tienda adicionalmente necesita llevar el Kardex de las películas, el cual consiste en registrar las ventas (Egresos) y
compras (Ingresos) que se ha realizado de una determinada película. En el kardex se registra la fecha de la transacción, el número de
películas adquiridas o vendidas, el precio al que se compró o vendió y el tipo de transacción (EGRESO o INGRESO).
5. De los clientes se conoce su identificación, nombre, dirección, teléfono y correo electrónico.
6. De los proveedores se conoce su identificación, nombre, dirección, teléfono y correo electrónico.
7. El local de Películas tiene varios empleados que son los que pueden registrar las ventas a los clientes y las compras que se hacen a los
proveedores. De los empleados se conoce un código identificador único, la identificación, nombre, dirección, teléfono  y sueldo.
8. Para la venta de las películas se requiere asignar un número secuencial de factura, la fecha de venta, el cliente y el detalle de películas
vendidas donde se incluye la pelicula, la cantidad, el precio unitario y se calcula el subtotal. Al final del proceso se debe calcular el total
de la factura con base al subtotal de los productos vendidos.
9. El cliente puede pagar la cuenta en efectivo o con tarjeta de crédito. En el caso del pago con tarjeta de crédito se debe registrar el tipo
de tarjeta y el número de autorización del operador.
10. Cuando se realice la venta de las películas se debe actualizar el stock de las películasy registrar la transacción en el Kardex.
11. Para la compra de películas se requiere asignar un número secuencial de orden de compra, la fecha de compra, el proveedor   y el
detalle de películas adquiridas donde se incluye la película, la cantidad, el precio unitario y se calcula el subtotal. Al final del proceso se
debe calcular el total de la factura con base al subtotal de los productos comprados.
12. Cuando se realice la compra de las películas se debe actualizar el stock de las películas y registrar la transacción en el Kardex.
Adicionalmente se requiere actualizar el precio unitario de las películas el cual será el de la última compra.
13. Los empleados pueden ingresar, actualizar y consultar los datos de las películas y clientes
14. El dueño del local de Películas puede ingresar, actualizar, consultar y eliminar  los datos de los proveedores, los empleados y las
películas. 
15. El cliente también puede consultar el precio y formato de una película, pero no otros datos de la misma.
16. En el sistema se deben generar listados de reportes con información de clientes los cuales se pueden ordenar por identificación en
forma descendente y por nombre en orden alfabético
17. En el sistema se deben generar listados de reportes con información de las películas los cuales se pueden ordenar por código en forma
descendente, por nombre en orden alfabético, por precio en orden descendente, por stock en orden ascendente y por género.
18. En el sistema se deben generar listados de reportes con información de empleados los cuales se pueden ordenar por identificación en
forma descendente, por nombre en orden alfabético y por sueldo en orden descendente
19. En el sistema se deben generar listados de reportes con información del kardex de películas los cuales se pueden ordenar fecha de
ingreso en orden ascendente y descendente.

Requisitos de implementación: 

1. Se deben generar los diagramas de casos de uso y de clases en Power Designer. La notación de los modelos empezará por el nombre
del proyecto (peliculas) seguido del tipo de diagrama (Clases o casos de Uso). La notación para el archivo del workspace será el
nombre del proyecto (peliculas)
2. Los diferentes elementos de los diagramas UML deben tener los lineamientos, nomenclatura y sintaxis sugerida o recomendada para su
implementación. Tomar en cuenta el diseño en base a las responsabilidades de cada uno de los objetos para ubicar cada uno de los
elementos en el lugar más apropiado.
3. Los constructores de clase deben exigir el ingreso de todos los atributos para inicializar el estado del objeto. No se permite la definición
de constructores por defecto.
4. Todos los atributos deben tener implementado los métodos que comunican y modifican el estado.
5. Todos los datos de los objetos deben persistir en un archivo de texto. Para esto se deben crear las operaciones necesarias (CRUD), que
permitan crear, buscar, eliminar y actualizar el estado de un objeto en el  archivo.
6. En los métodos de funcionalidad que van a trabajar con la información de varios objetos a la vez para cálculos o reportes deben utilizar
exclusivamente arreglos unidimensionales del tipo correspondiente. No se permite el uso de colecciones de datos de lenguaje Java.
Para esto debe considerar la creación de métodos auxiliares que permitan agregar, eliminar y buscar elementos en un arreglo.
7. No pueden existir objetos con información duplicada, para lo cual se validará la igualdad de objetos a partir de los identificadores
definidos para cada clase. Esta validación se realizará antes de crear los datos del objeto en el archivo.
8. El sistema debe realizar los controles pertinentes para controlar la  obligatoriedad de datos, la duplicidad de información y cualquier otro
control funcional que considere adecuado sobre la información mediante la implementación adecuada del manejo de excepciones. 
9. El sistema tendrá una interfaz gráfica basada en Swing. 
10. El proyecto del sistema debe ser generado utilizando el IDE de desarrollo NetBeans. La notación del proyecto será el nombre del
proyecto (peliculas). El proyecto se dividirá en capas para lo cual se generarán paquetes dentro del proyecto. Al menos deben existir los
paquetes para la interfaz gráfica y los datos.
