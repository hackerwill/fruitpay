test -d src/main/webapp/build/ || mkdir src/main/webapp/build/
cp -a ${JENKINS_HOME}/workspace/FruitpayFrontend/build/. src/main/webapp/build/.
test -d src/main/webapp/build/admin/ || mkdir src/main/webapp/build/admin/
cp -a ${JENKINS_HOME}/workspace/FruitpayBackend/build/. src/main/webapp/build/admin/.
test -d AllPayAioSDK || git clone https://github.com/allpay/AllPayAioSDK.git
mvn install:install-file -Dfile=AllPayAioSDK/AllPay.Payment.Integration.jar -DgroupId=com.allpay -DartifactId=allpay -Dversion=1.0.0 -Dpackaging=jar
rm -f src/main/resources/db.properties
test -d src/main/resources/ || mkdir src/main/resources/
cp ~/Downloads/db_test.properties src/main/resources/db.properties