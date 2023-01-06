package org.example.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;

@Entity
@Table(name = "boats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Boat {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @NonNull
    private String name;

    private String description;

    private String manufacturer;

    private Integer year;
    
    @NonNull
    private OffsetDateTime createdAt;

    @NonNull
    private OffsetDateTime updatedAt;
}
