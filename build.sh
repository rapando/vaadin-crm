mvn clean package -Pproduction -DskipTests
scp target/vaadin-crm-1.0-SNAPSHOT.jar root@innv8.lib.co.ke:/apps/admin/app.jar
