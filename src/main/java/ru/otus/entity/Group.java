package ru.otus.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.db.DBConnector;
import ru.otus.query.QueryConstructor;
import ru.otus.settings.Settings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Random;

@Data
@AllArgsConstructor
public class Group implements Entity {
    int id;
    int id_curator;
    String name;

    public Group() {
        Random random = new Random();
        try {
            Integer curatorCount = new Settings().getApplicationEntitySettings().get("Curator");
            this.id_curator = random.nextInt(1, curatorCount+1);
        } catch (Exception e) {
            this.id_curator = random.nextInt(1, 5);
        }
        this.name = "Group number " + random.nextInt(1, 100);
    }

    public Group(Map<String, String> fieldValue) {
        this.id = Integer.parseInt(fieldValue.get("id"));
        this.id_curator = Integer.parseInt(fieldValue.get("id_curator"));
        this.name = fieldValue.get("name");
    }

    @Override
    public String[] toArray() {
        String[] objectAsArray = { String.valueOf(id),
                                   String.valueOf(id_curator),
                                   name};
        return objectAsArray;
    }

    public String getGroupCurator(String groupName) {
        String query = new QueryConstructor().currentGroupCurator(groupName);
        ResultSet result = DBConnector.getDbConnector().executeQuery(query);
        String fio;
        try {
            result.next();
            fio = result.getString("fio");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fio;
    }
}
