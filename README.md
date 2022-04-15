# Anleitung zum Setuppen von Wildfly mit Hilfe dieses basic EJB Programms

## Installation Wildfly

- https://www.wildfly.org/downloads/
- 26.1.0.Final -> JakartaEE 8.0 -> Zip
- JAVA_HOME und Java in Path hinzufügen
- Serververzeichnis aussuchen
- Den `bin` Folder vom Server öffnen
- dort `standalone.bat` ausführen (als admin)

## User für WildFly Anwenden

- File `add-user-properties` öffnen (weiterhin im `bin` Folder), `password.restriction`: 
  - minLength -> 0
  - minAlpha -> 0
  - minDigit -> 0
  - minSymbol -> 0
  - mustNotMatchUserName -> FALSE
  - forbiddenValue: `none`
  - strength -> VERY_WEAK
  - (speichern und schließen)
- `add-user.bat` ausführen
  - `a` für Management User
  - Username = `admin`
  - `b` für enable existing user
  - (setup vorbei)
- `add-user.bat` nochmal ausführen
  - `a` für Management USer
  - Username = `admin`
  - `a` für Update the existing...
  - Passwort: `admin`
  - bei Gruppen `PowerUser,BillingAdmin`
  - bei nächsten `yes`
  - und den "secret value" brauch wir nicht
  - (setup vorbei)

## Admin Konsole

- Auf `localhost:9990`ist die Adminconsole
- Mit `admin` `admin` anmelden
- Und wir sind gelandet
- Hier kann man auch händisch Dinge deployen oder nachsehen, welche EJB oder REST(JAX-RS) endpunkte verfügbar sind (in Tab Runtime und eigenes Gerät auswählen)

## Deployment über IntelliJ

- Projekt `https://github.com/Noah-Sutter/TestForEJB` pullen, wer keine Einladung bekommen hat, bei Noah melden
- Run-Config erstellen:
  - WildFly - Remote (Server wird außerhalb von IntelliJ gestartet)
  - Application-Server -> Configure button und WildFlyServer Verzeichnis auswählen, z.B. `C:\Program Files\wildfly-26.1.0.Final`
  - ManagementPort: `9990`
  - Username: `admin`
  - Passwort `admin`
  - Remote-Staging bei beidem "Same als File System"
  - Ganz unten bei before-launch aufs Plus -> Build Artifacts -> `TheRealEJB-1.0.war` (nicht exploded) angeben
  - Ganz rauf scrollen, Deployment und wieder aufs Plus und die `TheRealEJB-1.0.war` angeben
  - Apply und okay (Name für RunConfig nicht vergessen ;) 
- Run Config laufen lassen
- Auf URL `localhost:8080/TheRealEJB-1.0/api/hello-world` sollte jetzt `heyoo asdf!` gezeigt werden.
  - Das heißt => Deployment war erfolgreich und EJB funktioniert auf dem Server auch (da zeigt eine Rest API rauf)
  - Wenn man (in Intellij) auf die Klasse `ServerRestController` geht und dort statt `asdf` etwas anderes eingibt und dann erneut deployed, dann sollte sich, wenn man im Browser die URL neu laden lässt, sichtbar machen. 

## Verbindung eines Clients über IntelliJ

- Im gleichen Projekt ist auc him package `client` die Klasse `Client`. Dort solte die Main Funktion ausführbar sein und ein `heyoo Wow` sollte direkt unter `> Task :Client.main()` im Log erscheinen. 
- Wenn das geht, dann klappt alles.
- Hinweis: im `build.gradle` gibt es 3 Dependencies, die der Client benötigt, um auf den Server zuzugreifen:
  - `wildfly-ejb-client-bom`
  - `wildfly-niming-client`
  - `jboss-ejb-client`
