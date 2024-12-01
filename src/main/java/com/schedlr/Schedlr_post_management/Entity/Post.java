package com.schedlr.Schedlr_post_management.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @SequenceGenerator(name = "post_id_sequence", sequenceName = "post_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_sequence")

    private int postId;
    private int userId;
    //private long userid;

    private String description;
    private boolean pinterest;
    private boolean fb;
    private boolean linkedin;
    private boolean twitter;

    @Column(name = "scheduled_time")
    private LocalDateTime schedule;
    private byte[] image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return postId == post.postId && Objects.equals(description, post.description) && Objects.equals(pinterest, post.pinterest) && Objects.equals(fb, post.fb) && Objects.equals(linkedin, post.linkedin) && Objects.equals(twitter, post.twitter) && Arrays.equals(image, post.image) && Objects.equals(schedule,post.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, Arrays.hashCode(image), description, pinterest, fb, linkedin, twitter, schedule);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + postId +
                ", description='" + description + '\'' +
                ", pinterest='" + pinterest + '\'' +
                ", fb= '" + fb + '\'' +
                ", linkedin= '" + linkedin + '\'' +
                ", twitter= '" + twitter + '\'' +
                ", image=" + Arrays.toString(image) +
                ", schedule=" + schedule.toString() +
                '}';
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPinterest() {
        return pinterest;
    }

    public void setPinterest(boolean pinterest) {
        this.pinterest = pinterest;
    }

    public boolean isFb() {
        return fb;
    }

    public void setFb(boolean fb) {
        this.fb = fb;
    }

    public boolean isLinkedin() {
        return linkedin;
    }

    public void setLinkedin(boolean linkedin) {
        this.linkedin = linkedin;
    }

    public boolean isTwitter() {
        return twitter;
    }

    public void setTwitter(boolean twitter) {
        this.twitter = twitter;
    }

    public LocalDateTime getSchedule() {
        return schedule;
    }

    public void setSchedule(LocalDateTime schedule) {
        this.schedule = schedule;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
