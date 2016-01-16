mvn tomcat7:undeploy -Dtomcat.username=will -Dtomcat.password=tomcatWillServer
mvn tomcat7:deploy -Dmaven.test.skip=true -Dtomcat.username=will -Dtomcat.password=tomcatWillServer