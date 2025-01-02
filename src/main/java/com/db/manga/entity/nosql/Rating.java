package com.db.manga.entity.nosql;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ratings")
public class Rating {

    private ObjectId id;
    private int ratingValue;
    private String ratingType;
    private String ratingDate;
    private ObjectId userId;
    private ObjectId mangaId;
    private ObjectId chapterId;

    public Rating(){}

    public Rating(int ratingValue, String ratingType, String rattingDate){
        this.ratingValue = ratingValue;
        this.ratingType = ratingType;
        this.ratingDate = rattingDate;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getRatingType() {
        return ratingType;
    }

    public void setRatingType(String ratingType) {
        this.ratingType = ratingType;
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

    public ObjectId getChapterId() {
        return chapterId;
    }

    public void setChapterId(ObjectId chapterId) {
        this.chapterId = chapterId;
    }

    public String getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(String ratingDate) {
        this.ratingDate = ratingDate;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", ratingValue=" + ratingValue +
                ", ratingType='" + ratingType + '\'' +
                ", ratingDate='" + ratingDate + '\'' +
                ", userId=" + userId +
                ", mangaId=" + mangaId +
                ", chapterId=" + chapterId +
                '}';
    }
}
