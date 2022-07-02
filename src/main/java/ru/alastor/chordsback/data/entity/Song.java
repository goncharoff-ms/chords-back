package ru.alastor.chordsback.data.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "song")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "rate")
    private Long rate;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "author_many_name")
    private String authorManyName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    @ToString.Exclude
    private Author author;

}