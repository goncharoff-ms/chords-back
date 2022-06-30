package ru.alastor.chordsback.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "author")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "name")
    private String name;

    @Column(name = "rate")
    private Long rate;

}