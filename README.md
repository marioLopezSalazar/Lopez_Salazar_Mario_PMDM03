# Pokémon MLS

## INTRODUCCIÓN

**Pokémon MLS** es una aplicación que permite **simular la captura de Pokémon**, facilitando el acceso a la información básica de todos tus Pokémon capturados. Está desarrollada en Java para disposibivos Android, y presenta una interfaz muy visual, intuitiva y de ágil navegación.

La información del entrenador Pokémon se almacena en la nube, lo que le permite utilizar la aplicación en diferentes dispositivos sin perder sus avances.

Se trata de una aplicación de carácter académico desarrollada para el módulo ***"Programación Multimedia y Dispositivos Móviles"*** dentro del *CFGM Dessarrollo de Aplicaciones Multiplataforma* a distancia en el IES Aguadulce (Almería, España).

** SDK mínimo: 24
** SDK target: 31
** Versión: 1.0.


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



## CONCLUSIONES DEL DESARROLLADOR


