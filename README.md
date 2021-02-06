#  APP RECOMENDAUTOS
## MANUAL DE INSTALCIÓN Y CONFIGURACIÓN

###### *Recomendautos v1.0.2 / febrero 2021* 
___
## Cóntenido:
- [Inicio](#app-recomendautos)
- [1. Requisitos](#1-requisitos)
- [2. Instalación del proyecto](#2-instalacion-del-proyecto)
- [3. Configuración del proyecto](#3-configuracion-del-proyecto)
- [4. Compilación del APK](#4-compilacion-del-apk)
- [5. Instalación del apk](#5-instalacion-del-apk)
- [Anexo 1 Configuración del modo desarrollador](#anexo-1)
- [Contacto](#contacto-del-programador)
___
&nbsp;

>Nota: Para poder hacer uso de la aplicación móvil debera haber instalado y configurado el servidor. si no lo ha hecho la aplicación no se podrá conectar.

## 1. Requisitos
Para la instalción y configuración del proyecto, se requiere el siguiente software: 

- Android Studio 4.1+  (obligatorio)
- smartphone con S.O. android marshmellow 6.0+ (obligatorio)
- 
librerias:
- gson
- retrofit

## 2. Instalacion del proyecto
Medinte su terminal dirigase a la ruta de su preferencia donde instalará el proyecto. Si ha instalado **git**, puede clonarlo directamente del repositorio de github como se explicac a continuación.

En caso de que ya cuente cuetne con el Zip del proyecto, descomprimalo en al ruta seleccionada y omita el resto del paso 3 y valla al paso 4.
2.1. En su terminal, ejecute el siguiente comando:

```cmd
C:\dir> git clone https://github.com/bitmarc/Recommender.git
```
2.2.A Una vez descargado, deberá abrir el proyecto en android studio.

## 3. Configuracion del proyecto
Antes de iniciar la aplicación deberá establecer la dirección ip del servidor. A continuación siga los siguientes pasos:
1. Abra el archivo `ConnectionManager.java` el cual se encuentra en el paquete **com.example.recomender** dentro de la carpeta "connection".
2. deberá editar la variable que contiene la url base del servidor:
```java
private String base_url="http://192.168.0.104:5000/"; //dirección ip del servidor
```
Es importante que incluya la diagonal `/` al final de la direccion, ya que si no se incluye, no podrá accede.

## 4. Compilacion del APK

Para generar el archivo APK (archivo de instalación) siga los siguientes pasos:
1. En la barra de herramientas abra el menú **Build**
2. Seleccióne la opcion **Build bundle(s) / APK(s)**
3. Seleccióne **Build APK(s)**

A continuación espere a que termine la compilación.
El directorio de salida por defecto es: `..\Recommender\app\build\outputs\apk\debug\app-debug.apk`


## 5. Instalacion del APK
>Nota:
Si lo desea, puede compilar e instalar directamente la aplicación a su dispositivo mediante las opciónes de desarrollador. para esto revise el [Anexo 1](#anexo-1)

Para instalar el apk deberá transferir el archivo apk a su dispositivo. y ejecutarlo mediante el administrador de archivos.
Si envias el apk desde alguna aplicación, deberás habilitar las fuentes desconocidas mediante el siguiente procedimiento:
1. valla a al menu de **configuración**
2. seleccione **aplicaciónes y notificaciónes**
3. valla a **acceso especial de apps**
4. seleccione la opción **instalar apps desconosidas**
5. Seleccione la aplicación que sará y habilite la opcion **"confiar en esta fuente"**
6. Descargue y instale el apk

&nbsp;
______

##### En este punto debería poder utilizar la aplicación sin ningún problema
______


&nbsp;
## ANEXO 1
### Configurar coneción del dispositivo a android studio
Para poder conectar el dispositivo android con Android studio, deberá habilitar primero la **depuración usb** en su dispositivo, para esto, deberña tener activado el **modo desarrollador** android mediante los siguientes pasos:
1. Dirijase al menú **ajustes** de su android
2. seleccione **sistema**
3. seleccióne **acerca del telefono**
4. debera tocar 5 veces seguidas la opción **número de compilación**

Al hacer esto se le habilitrá la opción **modo desarrollador** o **programador** en el menú sistema. Ahora debera habilitar la depuración USB siguiendo estos pasos:

1. dirijase al menú **desarrollador** o **programador**
2. busque la opción **depuración por usb** y activela.

Tras hacer esto, ahora puede conectar su dispositivo mediante usb a su computadora.
Le deberá salir un mensaje preguntando si confia en la fuente y deberá aceptar.
> Nota:
En caso de que no pueda detectar su dispositivo, debera instalar los drivers del usb que emite el fabricante de su dispositivo.

Desde Android studio deberá aparecerle el nombre de su dispositivo en la lista de sipositivos. seleccionelo y presione el botñon **Run app**.
Automáticamente deberá compilarse  e instalarse la aplicación en su dispositivo móvil.


## Licencia

*Open source*

**Free Software**


### Contacto del programador:

[@hermuslife](https://twitter.com/hermuslife)

marcoarojas.95@gmail.com

&nbsp;

