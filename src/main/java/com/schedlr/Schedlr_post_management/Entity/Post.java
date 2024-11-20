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
}
