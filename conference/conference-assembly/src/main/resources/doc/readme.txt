
Installation muss auf einem JBOSS > Version 7.1 erfolgen.

In einer MYSQL DB muss eine Datenbank/ein Schema conference angelegt werden. 
Siehe hierzu im Verzeichniss sql. Hier findet man ein entsprechendes SQL
Script zum anlegen der Tabelle.
 
Danach muss im JBOSS eine Datasource angelegt werden die auf die Datenbank/das Schema conference verweißt. 
Der JNDI Name muss lauten java:jboss/datasources/conferenceDS bzw. datasources/conferenceDS

Im Verzeichnis soapui findet mein ein SOAPUI Testprojekt für den Test der REST Webservice Schnittstellen.

Im Verzeichnis site findet man verschiedenste Infos zum Projekt wie auch die komplette JAVADOC

Im Verzeichnis artefact findet man alle Deploymentartefakte.
