# Pokémon MLS

## INTRODUCCIÓN

**Pokémon MLS** es una aplicación que permite **simular la captura de Pokémon**, facilitando el acceso a la información básica de todos tus Pokémon capturados. Está desarrollada en Java para disposibivos Android, y presenta una interfaz muy visual, intuitiva y de ágil navegación.

La información del entrenador Pokémon se almacena en la nube, lo que le permite utilizar la aplicación en diferentes dispositivos sin perder sus avances.

Se trata de una aplicación de carácter académico desarrollada para el módulo ***"Programación Multimedia y Dispositivos Móviles"*** dentro del *CFGM Dessarrollo de Aplicaciones Multiplataforma* a distancia en el IES Aguadulce (Almería, España).

* SDK mínimo: 24
* SDK target: 31
* Versión: 1.0.


## CARACTERÍSTICAS PRINCIPALES

Las funcionalidades clave de la aplicación son las sigiuentes:

* **Autenticación**: La navegación de la APP arranca con la autenticación del ususario. Basada en la interfaz prediseñada que ofrece *Firebase Authentication*, facilita el registro y login de los entrenadores Pokémon de dos formas:
  * <ins>Email y contraseña</ins>, permitiendo además introducir un nombre o identificador personalizado.
  * <ins>Cuenta de Google</ins>, la forma más rápida de loguearse.
* **Pokédex**: Ofrece un listado de los Pokémon, obtenido en tiempo real desde la [API Pokémon](https://pokeapi.co/docs/v2). Por defecto se muestra solamente la primera generación de Pokémon, si bien puede configurarse (en la pestaña *Configuración*, opción *Generaciones*) para mostrar **hasta la 9ª generación Pokémon**.

  La *captura de un Pokémon* se realiza con solo tocar sobre su nombre, apareciendo entonces los detalles del mismo. Los Pokémon que ya han sido capturados no pueden volver a capturarse (salvo que sean liberados previamente), y aparecen claramente marcados en la Pokédex mediante una Pokéball.
* **Pokémon capturados**: En esta pestaña aparecen todos los Pokémon capturados por el entrenador, ordenados por su índice. En la vista general aparecen su imagen y su tipo (o tipos). Pulsando sobre el Pokémon se puede acceder a más detalles del mismo (imagen más grande, peso y altura).

  El listado de Pokémon capturados, así como los detalles de cada Pokémon, son almacenados en la nube vinculados a la cuenta de usuario (en la base de datos *Firebase Firestore*). Así, cuando el entrenador se loguee de nuevo más adelante (en este o en otro dispositivo), sus Pokémon capturados seguirán estando disponibles.
* **Liberar Pokémon**: Los Pokémon capturados pueden ser liberados de dos maneras:
  * Desde la lista de Pokémon capturados, arrastrándolo hacia la derecha.
  * Desde los detalles del Pokémon, pulsando el botón *Liberar Pokémon*.

  Para evitar accidentes, la posibilidad de liberar Pokémon puede habilitarse o deshabilitare desde la pestaña *Configuración*, opción *Liberar Pokémon*.

  Cuando un Pokémon es liberado, naturalmente es eliminado de la lista de Pokémon capturados y deja de estar marcado como "Capturado" en la Pokédex, volviendo a estar disponible para ser capturado.
* **Ajustes**: La ventana de ajustes ofrece las siguientes posibilidades, algunas de ellas ya mencionadas:
  * **Idioma**: La aplicación puede configurarse para ser utilizada en español y en inglés. Además de los propios textos de la APP, también se traduce el tipo (o tipos) del Pokémon, así como el formato numérico de los pesos y las alturas.
  * **Liberar Pokémon**: Como ya se ha indicado, habilita o deshabilita la posibilidad de que el entrenador pueda liberar individualmente algunos de sus Pokémon.
  * **Apariencia**: Esta opción permite entrenar con dos modos de color e iluminación: ***Diurno*** y ***Nocturno***, o simplemente utilizar el tema predefinido por el usuario en el dispositivo móvil.
  * **Generaciones**: Como ya se ha indicado, esta opción permite configurar la cantidad de Pokémon que aparecen en la Pokédex. Concretamente, las opciones son:
    
    | Opción                 | Pokémon disponibles |
    |------------------------|:-------------------:|
    | Hasta la 1ª generación |  1-151              |
    | Hasta la 2ª generación |  1-251              |
    | Hasta la 3ª generación |  1-386              |
    | Hasta la 4ª generación |  1-493              |
    | Hasta la 5ª generación |  1-649              |
    | Hasta la 6ª generación |  1-721              |
    | Hasta la 7ª generación |  1-809              |
    | Hasta la 8ª generación |  1-905              |
    | Hasta la 1ª generación |  1-1025             |
  * **Cerrar sesión**: Opción de logout. Facilita, por ejemplo, que varios entrenadores puedan compartir el mismo dispositivo sin interferir.
  * **Eliminar usuario**: Permite eliminar el usuario de la aplicación.
  
    *<ins>Nota:</ins>* Para los usuarios logueados mediante *email y contraseña*, se requiere la reautentificación manual mediante contraseña.

    Todos sus Pokémon son liberados, y la cuenta de entrenador deja de estar disponible en todos los dispositivos. Si posteriormente desea volver a entrenar, su lista de Pokémon estará inicialmente vacía.
  * **Acerca de...**: Informa sobre la versión de la APP y su desarrollador.
  

## TECNOLOGÍAS UTILIZADAS

* **Retrofit**: Biblioteca de cliente HTTP para Java, desarrollada por Square, que facilita la realización de llamadas HTTP a APIs. En esta aplicación se utiliza para hacer llamadas HTTP GET a la [**API Pokémon**](https://pokeapi.co/), pudiendo disponer de información del mundo Pokémon en formato JSON.
* **Gson (Google's JSON)**: Biblioteca de Java desarrollada por Google que se utiliza para convertir objetos JSON a objetos Java y viceversa. En esta aplicación se utiliza para convertir los objetos JSON descargados de la [**API Pokémon**](https://pokeapi.co/) a objetos Java empleados por la aplicación.
* [**Firebase**](https://firebase.google.com/): Plataforma de almacenamiento en la nube que facilita el desarrollo de aplicaciones. Esta aplicación utiliza:
    * <ins>Authentication</ins> para la autenticación y gestión de usuarios. Concretamente se utiliza la inferfaz preconstruida de logueo provista por Firebase.
    * <ins>Firestore database</ins>, base de datos No-SQL empleada para almacenar la colección de Pokémon de cada entrenador, así como el listado de *tipos de Pokémon* localizados al idioma español.
* **SharedPreferences**: API Android que permite almacenar y recuperar datos clave-valor de forma persistente, ideal para guardar configuraciones o preferencias de usuario, de forma privada y persistente.
* **Picasso**: Biblioteca de código abierto desarrollada por Square que facilita la carga y almacenamiento en caché de imágenes en aplicaciones Android. Empleada para descargar y manejar las imágenes de los Pokémon.
* **Android Navigation**: Componente que facilita la implementación de la navegación en aplicaciones Android. Permite mover a los usuarios entre pantallas (en este caso, *fragments*) y gestionar elementos relacionados con la navegación, como el manejo de la pila de retroceso.
* **BottomNavigationView**: Componente de interfaz de usuario que facilita la navegación entre diferentes secciones utilizando una barra de navegación en la parte inferior de la pantalla. Proporciona un acceso rápido y consistente a cada sección. Este componente se integra fácilmente con Navigation Component.
* **RecyclerView**: Componente de interfaz de usuario utilizado para mostrar grandes conjuntos de datos de forma eficiente, en este caso en forma de lista. Ofrece flexibilidad y rendimiento al reutilizar vistas mediante un patrón llamado "ViewHolder". RecyclerView permite personalizar fácilmente la presentación de los elementos mediante LayoutManagers (en este caso CardViews). Su diseño modular lo hace ideal para listas complejas y dinámicas, con soporte nativo para arrastrar y soltar, y también para deslizamiento, tal y como se emplea en esta aplicación.


## INSTRUCCIONES DE USO

1. En la [página principal del repositorio](https://github.com/marioLopezSalazar/Lopez_Salazar_Mario_PMDM03/), hacer clic en el botón verde `<> code` y copiar la dirección HTTPS que aparece (`https://github.com/marioLopezSalazar/Lopez_Salazar_Mario_PMDM03.git`).
2. En Android Studio, utilizamos el menú: *File -> New... -> Project from Version Control...*.
3. En el diálogo emergente, seleccionaremos:
    * *Repository URL*
    * *Version control: Git*
    * *URL:* En este campo pegaremos la dirección previamente copiada.
    * *Directory:* Directorio donde se descargará el proyecto.
    * Haremos clic en el botón *Clone*.
4. En unos segundos, tendremos el proyecto descargado en nuestro equipo.

Los archivos `build.gradle.kts` (principalmente el de nivel de App) ya contienen referencias a las librerías o dependencias que necesita la aplicación para funcionar. Así, en el proceso de construcción (*Build*) del proyecto, el propio Android Studio obtendrá de manera transparente las librerías necesarias. No obstante, también puede lanzarse este proceso mediante la opción *File -> Sync Project with Gradle Files*.

Para la ejecución de la aplicación es necesario disponer de un dispositivo emulador (puede usarse el *Device Manager* que provee Android Studio) con API mínima 24. Basta seleccionar el emulador deseado y utilizar la opción *Run -> Run 'App'*.


## CONCLUSIONES DEL DESARROLLADOR

#### MainActivity y navegación 
La construcción de la aplicación comenzó con la implementación de la **navegación**, creando el `navGraph` y los tres `fragments` de primer nivel de navegación (Pokédex, Pokémon capturados y Configuración `SharedPreferences`) y configurando lo anterior con el `BottomNavigationView`. He aprendido que un uso diestro de los atributos del `navGrap` facilita la configuración enormemente.

El diseño de esta **vista principal** ha sido modificado a lo largo del proyecto, pues inicialmente se hacía uso de la ActionBar por defecto pero posteriormente se hizo necesario emplear una ToolBar personalizada y un cambio en los layouts para conseguir un diseño visual óptimo en los cambios de fragmento.


#### Autenticación
El arranque de **Firebase** y la implementación de la **autenticación** (login y logout) resultó sencilla, siguiendo las indicaciones de la documentación y de la [profesora](https://github.com/lbarmar).

Decidí después implementar la opción de **eliminar usuario**, que ha supuesto un auténtico desafío:
* Descubrí que era necesaria la reautentificación del usuario para que la llamada de borrado de usuario tuviese efecto. Este aspecto no aparece en la documentación básica de Firebase.
* La reautentificación se realiza de forma diferente según el método de autentificación.
* Ha sido necesario implementar un diálogo personalizado para requerir al usuario su contraseña. Esto ha sido una oportunidad para investigar sobre `AlertDialog` personalizados y la personalización del `EditText`.

#### Diseño de los RecyclerView
Esta parte se desarrolló utilizando datos de ejemplo, creando objetos de modelo directamente en código.
Cada uno de los dos RecyclerView funciona con una `List` subyacente, una sobre objetos de la clase `PokémonId` y otra sobre objetos de la clase `Pokémon` que extiende a la anterior. Esto ha facilitado el chequeo ágil sobre si un Pokémon está o no capturado, lo que facilita las operaciones de actualización en la Pokédex.
En fases posteriores se ha añadido la funcionalidad de liberar Pokémon mediante deslizamiento de la CardView, para ello he investigado sobre la clase `ItemTouchHelper`. Aquí lo más difícil ha sido integrar esta clase de forma limpia con el resto de clases que componen la funcionalidad del RecyclerView.

#### Descarga de la API Pokémon
El diseño de la intefaz de llamadas y el *adaper* ha resultado sencillo. Más complicado ha resultado el diseño de una estructura de clases que encaje con la estructura JSON de la API, siendo necesarios numerosos reajustes y pruebas hasta obtener los resultados deseados.
En este momento surgió la necesidad de comenzar a trabajar con rutinas asíncronas (callbacks, `onCompleteListener`, objetos `Task`...) y la comunicación asíncrona entre clases y métodos, aspecto que se iría complicando al implementar otras funcionalidades.

#### Guardado y descarga de Pokémon capturados
Aquí el asunto se complicó, pues previamente fue necesario entender la estructura de datos que maneja Firestore, y cómo funciona el sistema de referencias, documentos y colecciones. Por otra parte, la necesidad de hacer robustas las funcionalidades lleva a tener que anidar las callbacks, para que las tareas que dependen una de otra se realicen de forma consecutiva (no paralela) y se controlen los posibles errores.

Una vez implementada esta parte, se hizo necesario coordinar el flujo completo de carga de la aplicación (Pokédex y Pokémon capturados, e integración de ambas).

Se consideró conviente mostrar siempre los Pokémon ordenados mediante su índice, que se ha conseguido de manera simple haciendo que `PokemonId implements Comparable` y haciendo uso del método `List.sort()`.

#### Detalle de los Pokémon
Esta sección no ha supuesto demasiadas complicaciones, más allá de la implementación de métodos auxiliares para mostrar los datos numéricos en unidades habituales en nuestro contexto.

#### Captura de Pokémon
En esta acción ya se ven involucradas muchas clases de la aplicación. Como aspectos a reseñar:
   * El bloqueo de los eventos para evitar *doble captura*, tanto momentánea (por doble toque) como sobre Pokémon previamente capturados.
   * La necesidad de coordinar varios procesos asíncronos que dependen uno del otro, y que deben ser revertidos si una de las acciones falla.
   * La actualización de las vistas de forma coherente.

#### Liberación de Pokémon
De manera similar a lo anterior, están involucrados varios procesos asíncronos que dependen uno del otro. Se ha cuidado que no ocurran situaciones inconsistentes en caso de error.
Ha sido necesario investigar un poco sobre cómo actualizar el RecyclerView tras la eliminación del objeto pues, al contrario de lo que sucede durante la captura, en la liberación no se produce un cambio de vista (y por tanto el reinflado automático).
La configuración de la *SharedPreference* no ha supuesto dificultad.

#### Tamaño de la Pokédex
Una funcionalidad adicional que me ha permitido profundizar en el diseño de las `SharedPreferences` mediante el uso del fichero `arrays.xml`.
Realmente no ha supuesto demasiadas modificaciones sobre lo ya implementado, simplemente introducir algunas modificaciones para el caso de desear liberar un Pokémon que ya no está presente en la Pokédex.

#### Estilo visual
La elección de la paleta de colores, las tipografías y los iconos ha resultado un proceso creativo.

Lo más complicado de esta parte (y posiblemente de toda la aplicación) ha sido descubrir los atributos concretos a emplear para personalizar las propiedades de los elementos visuales, especialmente de la `ToolBar` y de la `BottomNavigationView`.

También ha resultado especialmente complicado personalizar la interfaz preconstruida de *Login*, pues el estilo base empleado por Firebase no es el mismo del resto de mi aplicación. Ha sido necesario, por tanto, diseñar un nuevo `<theme>` para esta actividad.

#### Idioma
Más allá de la traducción de los ficheros `strings.xml` y `arrays.xml`, disponer de los *tipos de Pokémon* en ambos idomas ha requerido una toma de decisión. Con el objetivo de continuar el espíritu intencional de este proyecto, que maneja datos localizados fuera de la propia aplicación, se ha decidido utilizar otra colección de Firestore para almacenar el diccionario de traducción de *tipos de Pokémon*. La descarga se realiza de manera análoga a la descarga de Pokémon capturados.

Cuando el usuario cambia la preferencia, se realiza la descarga del correspondiente diccionario (en este caso, el diccionario *types-ES*). Al mostrar los datos al usuario, un método auxiliar efectúa la traducción.

#### Documentación
Finalmente, el proceso de documentación JavaDoc de todas las clases implementadas ha permitido una revisión general del código, refinando aspectos de modularidad e implementación, así como una profundización en las llamadas y métodos de las librerías empleadas.

### Valoración general y conclusiones
La realización de este proyecto ha resultado un reto realmente estimulante, por la cantidad de aspectos variados del desarrollo de aplicaciones que se involucran en él. Me ha permitido poner en práctica muchos tópicos que previamente había aprendido en otros módulos, pero sobre todo, aprender nuevos aspectos y rincones del mundo Android.








