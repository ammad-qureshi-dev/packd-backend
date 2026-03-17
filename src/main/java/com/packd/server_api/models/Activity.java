package com.packd.server_api.models;

import com.packd.server_api.models.enums.ActivityCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.packd.server_api.utils.Constants.Schema.SCHEMA_NAME;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activity", schema = SCHEMA_NAME)
@EqualsAndHashCode(callSuper = true)
public class Activity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;

    // Relationships
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "destination")
    private Destination destination;

    // Fields
    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private ActivityCategory activityCategory;

    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
