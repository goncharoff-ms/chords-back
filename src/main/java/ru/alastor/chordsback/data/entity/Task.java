package ru.alastor.chordsback.data.entity;

import lombok.*;
import org.hibernate.Hibernate;
import ru.alastor.chordsback.loader.parsing.ParsingSiteType;
import ru.alastor.chordsback.loader.parsing.TaskType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TaskType type;

    @Column(name = "link")
    private String link;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "is_completed", nullable = false, columnDefinition = "boolean default false")
    private boolean isCompleted;

    @Column(name = "completed_at", nullable = false, columnDefinition = "now()")
    private LocalDateTime completedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "site", nullable = false)
    private ParsingSiteType site;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Task task = (Task) o;
        return id != null && Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}