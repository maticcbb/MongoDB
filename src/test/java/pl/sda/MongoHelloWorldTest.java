package pl.sda;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.assertj.core.api.Assertions;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MongoHelloWorldTest {

    @Test
    @DisplayName("Show how to connect to MongoDB using Java Driver")
    void connectTest() {
        MongoClient mongoClient = MongoClients.create(codecSettings());
        MongoDatabase testDb = mongoClient.getDatabase("testDb");
        MongoCollection<User> userCollection = testDb.getCollection("User", User.class);
        User userToAdd = new User("Rafal" , "Lewandowski");
        userCollection.insertOne(userToAdd);
        User firstUserInDb = userCollection.find().first();
        assertThat(firstUserInDb.getFirstName()).isEqualTo("Rafal");
        assertThat(firstUserInDb.getLastName()).isEqualTo("Lewandowski");
    }

    private MongoClientSettings codecSettings() {
        return MongoClientSettings.builder().codecRegistry(CodecRegistries.fromRegistries(com.mongodb
                .MongoClient.getDefaultCodecRegistry(), CodecRegistries.fromProviders(PojoCodecProvider
                .builder().automatic(true).build()))).build();
    }
}
