# spring-boot-transaction-transfer
Приложение предназначено для передачи большого количества транзакций из одной базы данных в другую.

####Конфигурирование и запуск

- Подготовливаем базы данных

```sh
$ sudo -u postgres psql
# CREATE DATABASE front;
# CREATE DATABASE back;
# CREATE USER tester WITH password 'qwerty';
# GRANT ALL PRIVILEGES ON DATABASE front TO tester;
# GRANT ALL PRIVILEGES ON DATABASE back TO tester;
```

- Перенесим файл с настройками приложения application.properties(находиться в корне проекта) в папку /opt(проверить права на запись)

- Убеждаемся что приложение запускается под Java 11:
update-alternatives --config java

- Собираем приложение:
./gradlew build

- Запускам приложение(для инициализации таблиц БД):
./gradlew bootRun

- Останавливаем приложение

- Инициализируем таблицу с историей миграций:
./gradlew flywayBaseLine

- Выполняем миграции:
./gradlew flywayMigrate

- Запускаем приложение: ./gradlew bootRun


> Логирование осуществляется в файл: /opt/spring.log