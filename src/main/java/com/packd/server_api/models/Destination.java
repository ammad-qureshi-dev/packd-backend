package com.packd.server_api.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.packd.server_api.utils.Constants.Schema.SCHEMA_NAME;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "destination", schema = SCHEMA_NAME)
@EqualsAndHashCode(callSuper = true)
public class Destination extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;

    // Relationships
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "trip")
    private Trip trip;

    @ToString.Exclude
    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = false)
    private List<Activity> activities;

    // Fields
    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private LocalDate fromDate;

    @Column(nullable = false)
    private LocalDate toDate;

}
