## SQL
1. Запускаем контейнеры: docker-compose up -d
2. Подключение к контейнеру: docker-compose exec mysqldb mysql -u app -p app
3. Запускаем jar: java -jar app-deadline.jar -P:jdbc.url=jdbc:mysql://192.168.99.100:3306/app -P:jdbc.user=app -P:jdbc.password=pass