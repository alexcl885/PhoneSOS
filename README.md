# PhoneSOS 

En el manifest tengo los permisos para poder realizar la llamada, para poner una alarma y además he puesto el permiso de conexión a internet.

``` xml
  <uses-permission android:name="android.permission.INTERNET" />
  <uses-permission android:name="android.permission.CALL_PHONE"/>
  <uses-permission android:name="com.android.alarm.permission.SET_ALARM"/>

````

En esta actividad he realizado una aplicación en Android Studio que mediante la primera activity nos muestra 1 imagen grande y 4 imágenes en los cuales:
1º Nos manda a realizar una llamada de emergencia.
2º Nos manda a poner una alarma.
3ºNos manda a google maps.
4ºNos manda a la página web de GitHub
En la lógica del primer activity en las imágenes he utilizado renombrar cada unas de las imágenes para asociarlas luego a la imagen del primer activity para luego en la segunda activity utilizar preferencias compartidas con el binding para la llamada SOS.

En la segunda activity realizó la llamada SOS, en la cual programó con preferencias compartidas ya que al inicializar esto, se hace más fácil y no tengo que crear 50 botones para referenciarlos luego, sino que directamente con el binding busco ese botón o texto.
Entonces en la segunda activity lo que hace es tener que poner el usuario un numero de telefono valido para poder tener un teléfono de emergencia y también le he añadido un botón para volver atrás al primer activity.

En la tercera activity es donde está la crema del pastel, donde está toda la lógica donde al pulsar a llamar, si no tienes los permisos de llamada te obligará a ponerlos mediante una pantallita y si no las aceptas te mandara directamente a los ajustes para que las aceptes.
Y también habrá una imagen que al pulsarla podrás volver al segundo activity para poner otro número de teléfono.
