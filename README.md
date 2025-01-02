# Pokémon MLS

## INTRODUCCIÓN

**Pokémon MLS** es una aplicación que permite **simular la captura de Pokémon**, facilitando el acceso a la información básica de todos tus Pokémon capturados. Está desarrollada para disposibivos Android, y presenta una interfaz muy visual, intuitiva y de ágil navegación.

La información del entrenador Pokémon se almacena en la nube, lo que le permite utilizar la aplicación en diferentes dispositivos sin perder sus avances.

Se trata de una aplicación de carácter académico desarrollada para el módulo ***"Programación Multimedia y Dispositivos Móviles"*** dentro del *CFGM Dessarrollo de Aplicaciones Multiplataforma* a distancia en el IES Aguadulce (Almería, España).


## CARACTERÍSTICAS PRINCIPALES

Las funcionalidades clave de la aplicación son las sigiuentes:

* **Autenticación**: La navegación de la APP arranca con la autenticación del ususario. Basada en la interfaz prediseñada que ofrece *Firebase Authentication*, facilita el registro y login de los entrenadores Pokémon de dos formas:
  * <ins>Email y contraseña</ins>, permitiendo además introducir un nombre o identificador personalizado.
  * <ins>Cuenta de Google</ins>, la forma más rápida de loguearse.
* **Pokédex**: Ofrece un listado de los Pokémon. Por defecto se muestra solamente la primera generación, si bien puede configurarse (en la pestaña *Configuración*, opción *Generaciones*) para mostrar **hasta la 9ª generación Pokémon**.

  La *captura de un Pokémon* se realiza con solo tocar sobre su nombre, apareciendo entonces los detalles del mismo. Los Pokémon que ya han sido capturados no pueden volver a capturarse (salvo que sean liberados previamente), y aparecen claramente marcados en la Pokédex mediante una Pokéball.
* **Pokémon capturados**: En esta pestaña aparecen todos los Pokémon capturados por el entrenador, ordenados por su índice. En la vista general aparecen su imagen y su tipo (o tipos). Pulsando sobre el Pokémon se puede acceder a más detalles del mismo (imagen más grande, peso y altura).

  El listado de Pokémon capturados, así como los detalles de cada Pokémon, son almacenados en la nube vinculados a la cuenta de usuario. Así, cuando el entrenador se loguee de nuevo más adelante (en este o en otro dispositivo), sus Pokémon capturados seguirán estando disponibles.
* **Liberar Pokémon**: Los Pokémon capturados pueden ser liberados de dos maneras:
  * Desde la lista de Pokémon capturados, arrastrándolo hacia la derecha.
  * Desde los detalles del Pokémon, pulsando el botón *Liberar Pokémon*.

  Para evitar accidentes, la posibilidad de liberar Pokémon puede habilitarse o deshabilitare desde la pestaña *Configuración*, opción *Liberar Pokémon*.

  Cuando un Pokémon es liberado, naturalmente deja de aparecer en la lista de Pokémon capturados y deja de estar marcado como "Capturado" en la Pokédex, volviendo a estar disponible para ser capturado.
* **Ajustes**: La ventana de ajustes ofrece las siguientes posibilidades, algunas de ellas ya mencionadas:
  * **Idioma**: La aplicación puede configurarse para ser utilizada en español y en inglés. Además de los propios textos de la APP, también se traduce el tipo (o tipos) del Pokémon, así como el formato numérico de los pesos y las alturas.
  * **Liberar Pokémon**: Como ya se ha indicado, habilita o deshabilita la posibilidad de que el entrenador pueda liberar individualmente algunos de sus Pokémon.
  * **Apariencia**: Esta opción permite entrenar con dos modos de color e iluminación: ***Diurno*** y ***Nocturno***, o simplemente utilizar el tema predefinido por el usuario en el dispositivo móvil.
  * **Generaciones**: Como ya se ha indicado, esta opción permite configurar la cantidad de Pokémon que aparecen en la Pokédex. Concretamente, las opciones son:
    | Opción                 | Pokémon disponibles |
    |------------------------|---------------------|
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
  * **Eliminar usuario**: Permite eliminar el usuario de la aplicación. Todos sus Pokémon son liberados, y la cuenta de entrenador deja de estar disponible en todos los dispositivos. Si posteriormente desea volver a entrenar, su lista de Pokémon estará inicialmente vacía.
  * **Acerca de...**: Informa sobre la versión de la APP y su desarrollador.
  


## TECNOLOGÍAS UTILIZADAS


## INSTRUCCIONES DE USO



## CONCLUSIONES DEL DESARROLLADOR


