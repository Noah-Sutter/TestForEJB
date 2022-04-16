# Anleitung zum Setuppen von Wildfly mit Hilfe dieses basic EJB Programms

**Anleitung für Docker gibt es im Branch `dockerize`!**

Warum überhaupt WildFly? 
Ich habe zuvor versucht, über Payara EJB Anwendungen in IntelliJ zu deployen, nur mit bedingten Erfolg. Der Payaraserver hat sich (zumindest auf meinem System) als sehr unzuverlässig herausgestellt und nach vielen Stunden arbeit hab ich das weiterhin nicht hinbekommen.

Als alternative habe ich es hier mit Wildfly probiert (in meine endlosen Rechrechen bin ich zu EJB Zeug hauptsächlich auf Wildfly Server gestoßen) und das hat relativ einwandfrei geklappt. Das initiale Setup ist etwas aufwändiger (Hauptsächlich dem geschuldet, dass wir selbst einen User anelgen müssen und ich jegliche Sicherheitsmaßnahmen für die Passwörter entfernt habe), dafür ist er schneller und IntelliJ kann auch viel besser damit. Außerdem hat man bei der Weboberfläche des Wildfly Servers das Gefühl, dass man auch im 21. Jahrhundert angekommen ist ;)

## Installation Wildfly

- https://www.wildfly.org/downloads/
- 26.1.0.Final -> JakartaEE 8.0 -> Zip
- JAVA_HOME und Java in Path hinzufügen
- Achtung, das ist der Schwierigste Teil der Anleitung: Ihr müsst euch jetzt entscheiden, in welchem Verzeichnis ihr den Server installiert haben wollt. Hier seid ihr selbst gefordert! Anyway, dort einfach die `.zip` Datei entpacken
- Den `bin` Folder vom Server öffnen
- dort `standalone.bat` ausführen (als admin)

## User für WildFly Anwenden

- File `add-user.properties` öffnen (weiterhin im `bin` Folder), `password.restriction`: 
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

## Automatisches Deployen über Deploy-Folder

`.war` Files lassen sich neben der händischen Deployment und IntelliJ auch über einen Folder deployen. Einfach die zu deployende `.war` Datei in den Folder im Serververzeichnis `standalone/deployments` ziehen und die Datei wird automatisch deployed. Wenn die Deployment abgeschlossen ist, dann erscheint einmal eine File mit dem gleichen Namen `.war.deployed`. Zum Undeployen einfach die originale `.war` File entfernen. Die `war.deployed` File wird gegen eine `.war.undeployed` File ausgetausch, die gelöscht werden muss, bevor man eine neue `.war` mit dem gleichen Namen deployed. 

