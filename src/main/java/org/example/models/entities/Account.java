package org.example.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;


@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Account {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @NonNull
    @Column(unique = true)
    private String userName;

    @NonNull
    private String password;

    @NonNull
    private OffsetDateTime createdAt;

    @NonNull
    private OffsetDateTime updatedAt;

}
