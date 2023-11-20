FROM debian:bookworm-20210816-slim

# Установка необходимых пакетов
RUN apt-get update \
    && apt-get install -y gnupg2 curl dos2unix openjdk-17-jre-headless \
    && apt-get clean

# Установка PostgreSQL из официального репозитория
RUN curl https://www.postgresql.org/media/keys/ACCC4CF8.asc | apt-key add - \
    && echo "deb http://apt.postgresql.org/pub/repos/apt/ bookworm-pgdg main" > /etc/apt/sources.list.d/pgdg.list \
    && apt-get update \
    && apt-get install -y postgresql postgresql-contrib \
    && apt-get clean

# Копирование приложения в образ
COPY . /app
WORKDIR /app

# Настройка PostgreSQL и создание базы данных
USER postgres
RUN /etc/init.d/postgresql start \
    && psql -c "ALTER USER postgres WITH PASSWORD 'test';" \
    && psql -c "CREATE DATABASE realtalk;"

# Возвращение к пользователю root
USER root

# Настройка переменных среды JAVA_HOME и PATH для Java
ENV JAVA_HOME /usr/lib/jvm/java-17-openjdk-amd64
ENV PATH $PATH:$JAVA_HOME/bin

# Настройка приложения и запуск
EXPOSE 9000
RUN dos2unix ./gradlew
CMD service postgresql start && ./gradlew bootRun