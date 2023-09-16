package ru.otus.utils;

import ru.otus.entity.Entity;
import ru.otus.tables.ITable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


public class MetaClassData {
    public static String getClassName(Class parameterClass) {
        String tableNameArr[] = parameterClass.getName().split("\\.");
        String tableName = tableNameArr[tableNameArr.length-1];
        return tableName;
    }

    public static String getClassName(Entity entity) {
        Class parameterClass = entity.getClass();
        String tableNameArr[] = parameterClass.getName().split("\\.");
        String tableName = tableNameArr[tableNameArr.length-1];
        return tableName;
    }
    public static Map<String,String> getFields(Class parameterClass, ITable table) {
        Map<String,String> tableFields = new HashMap<>();
        Field[] fieldsOfClass = parameterClass.getDeclaredFields();
        for (Field field: fieldsOfClass) {
            String fieldName = field.getName();
            String[] typeFieldArr = field.getType().toString().split("\\.");
            String fieldJavaType = typeFieldArr[typeFieldArr.length-1];
            tableFields.put(fieldName, table.getTypeForConcreteDb(fieldJavaType));
        }
        return tableFields;
    }



    public static Map<String, String> getFieldsAndValues(Entity entity) {
        Map<String, String> fieldsAndValues = new HashMap<>();

        Field[] fieldsOfClass = entity.getClass().getDeclaredFields();
        try {
            for (Field field : fieldsOfClass) {
                field.setAccessible(true);
                if (field.getType().toString().equals("int")) {
                    fieldsAndValues.put(field.getName(), String.valueOf(field.getInt(entity)));
                }
                if (field.getType().toString().equals("class java.lang.String")) {
                    fieldsAndValues.put(field.getName(), (String)field.get(entity));
                }
            }
        } catch (IllegalAccessException e) {
            System.out.println("Не удалось получить значение поля объекта");
            e.printStackTrace();
        }
        return fieldsAndValues;
    }

}
