# Translator Application

## Описание
Приложение для перевода текста, использующее API Lecto.ai.

## Требования
- Java 11+
- Maven 3.6.3+
- PostgreSQL 12+

## Установка и запуск

### Настройка базы данных
1. Создание БД PostgreSQL:
    ```sql
    CREATE DATABASE translator;
    ```

2. Создание таблицы `translation_requests`:
    ```sql
    CREATE TABLE translation_requests (
        id SERIAL PRIMARY KEY,
        ip_address VARCHAR(255),
        input_text TEXT,
        translated_text TEXT
    );
    ```

### Настройка приложения
1. Клонирование репозитория:
    ```sh
    git clone https://github.com/your-username/your-repository-name.git
    cd your-repository-name
    ```

2. Настройка параметров подключения к БД `src/main/resources/application.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/translator
    spring.datasource.username=psql
    spring.datasource.password=123
    spring.datasource.driver-class-name=org.postgresql.Driver
    ```

3. Запуск проекта


### Использование API
Для перевода текста используйте следующий cURL-запрос:
```sh
curl -X POST "https://api.lecto.ai/v1/translate/text" -H "X-API-Key: BFMMVYP-XP74TFY-KZD7JGZ-VXEDRT4" -H "Content-Type: application/json" -H "Accept: application/json" --data-raw "{\"texts\":[\"Hello world, this is my first program\"],\"to\":[\"ru\"],\"from\":\"en\"}" --compressed
# Translator