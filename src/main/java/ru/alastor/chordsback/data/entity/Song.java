package ru.alastor.chordsback.data.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "song")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Song {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "name")
    private String name;

    @Column(name = "rate")
    private Long rate;

    @Column(name = "text", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String text;

    @Column(name = "create_at")
    private Instant createAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

}