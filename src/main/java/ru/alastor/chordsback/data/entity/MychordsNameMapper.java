package ru.alastor.chordsback.data.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "mychords_name_mapper")
public class MychordsNameMapper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "real_name", nullable = false)
    private String realName;

    @Column(name = "site_name", nullable = false)
    private String siteName;

    @Column(name = "rate")
    private Long rate;

}