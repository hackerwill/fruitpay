test -d src/main/webapp/build/ || mkdir src/main/webapp/build/
cp -a ${JENKINS_HOME}/workspace/FruitpayFrontend/build/. src/main/webapp/build/.
test -d AllPayAioSDK || git clone https://github.com/allpay/AllPayAioSDK.git
mvn install:install-file -Dfile=AllPayAioSDK/AllPay.Payment.Integration.jar -DgroupId=com.allpay -DartifactId=allpay -Dversion=1.0.0 -Dpackaging=jar
rm -f src/main/java/META-INF/persistence.xml
test -d src/main/resources/META-INF/ || mkdir src/main/resources/META-INF/
cp ~/Downloads/persistence_jenkins_test.xml src/main/resources/META-INF/persistence.xml