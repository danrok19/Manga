package com.db.manga.entity.nosql;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "subscriptions")
public class Subscription {

    @Id
    private ObjectId id;

    private String subscriptionDate;
    private ObjectId userId;
    private ObjectId mangaId;

    public Subscription() {}

    public Subscription(String subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(String subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public ObjectId getMangaId() {
        return mangaId;
    }

    public void setMangaId(ObjectId mangaId) {
        this.mangaId = mangaId;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", subscriptionDate='" + subscriptionDate + '\'' +
                ", userId=" + userId +
                ", mangaId=" + mangaId +
                '}';
    }
}
