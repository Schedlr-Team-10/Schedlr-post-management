package com.schedlr.Schedlr_post_management;
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

    @Column(name = "id")
    private int postid;

    //private long userid;

    @Column(name = "descript")
    private String description;

    @Column(name = "instagrarm")
    private boolean instagram;
    private boolean facebook;
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
        return postid == post.postid && Objects.equals(description, post.description) && Objects.equals(instagram, post.instagram) && Objects.equals(facebook, post.facebook) && Objects.equals(linkedin, post.linkedin) && Objects.equals(twitter, post.twitter) && Arrays.equals(image, post.image) && Objects.equals(schedule,post.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postid, Arrays.hashCode(image), description, instagram, facebook, linkedin, twitter, schedule);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + postid +
                ", description='" + description + '\'' +
                ", instagram='" + instagram + '\'' +
                ", facebook= '" + facebook + '\'' +
                ", linkedin= '" + linkedin + '\'' +
                ", twitter= '" + twitter + '\'' +
                ", image=" + Arrays.toString(image) +
                ", schedule=" + schedule.toString() +
                '}';
    }
}
