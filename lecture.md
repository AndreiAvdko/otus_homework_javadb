## Java + SQL
### JDBC ( Java Database Connectivity )
JDBC-driver - специфический для каждой базы данных драйвер, с помощью которого JDBC превращает вызовы уровня API в "родные" команды сервера баз данных.

## Создание подключения

java.sql.Connection - интерфейс, реализующие его объекты представляют собой установленное соединение


```java
String  CONNECTION_URL = "jdbc:postgresql://localhost:5432/postgres"; 
```

```java 
try (Connection connection = DriverManager.getConnection(CONNECTION_URL, 
                                                            USER, 
                                                            PASSWORD)) {
    // use connection here
} 
```

## Выполнение запросов.Statment
java.sql.Statement - интерфейс, предназначенный для формирования SQL-комманд, 
которые не содержат параметров, и для получения результата их выполнения.

__!!! Connection и Statement необходимо всегда закрывать, сначала statement, затем connection !!!__

```java
try (Statement stmt = connection.createStatement()) {
    stmt.execute(createTableSQL);
}
```

``` java
String insertSQL = "INSERT INTO employees(name, position, salary)" +
"VALUES ('simon', 'developer', 2000)", ('dave', 'tester', 2000)";

try (Statement stmt = connection.createStatement()) {
    int res = stmt.executeUpdate(insertSQL);
    System.out.println("INSERT RESULT: " + res);
}
 
```

### Получение результатов в ResultSet
```java
String selectSql  = "SELECT * FROM employees";

try (ResultSet resultSet = stmt.executeQuery(selectSQL)) {
    // use resultSet here
} 
```

### Prepared Statement
```java
String query = "select * from passenger where name = ?"

try (PreparedStatement statement = 
            connection.prepareStatement(query)) {
        statement.setString(1, "Igor Petrov");
        ResultSet resultSet = statement.executeQuery();
    }
```

### Получение результатов
```
String query = "select name, passport_number from passenger";

try (ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String rowData = String.format("Name: %s, Passport: %s",
                    resultSet.getString(),
                    resultSet.getString(2));
        System.out.println(rowData);
    }
```

### Работа с транзакциями

```java
boolean autoCommit = connection.getAutoCommit();
try {
    connection.setAutoCommit(false);
    pstmt.executeUpdate();
    pstmt2.executeUpdate();
    connection.commit();
} catch (SQLException exc) {
    exc.printStackTrace();
    connection.rollback();
} finally {
    connection.setAutoCommit(autoCommit);
}
```

### Работа с Properties

Все properties можно найти по запросу 'system properties java'

```
Properties properties = new Properties();

properties.load(new FileReader( System.getProperty("user.dir") 
+ "src/main/java/ru/otus/settings/db.properties"));

for(String key: properties.stringPropertyNames()) {
            result.put(key, properties.getProperty(key));
        }
```

### Проблема с регистром строк в Postgres
```
CREATE EXTENSION IF NOT EXISTS citext;
```