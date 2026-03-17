package com.packd.server_api.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.packd.server_api.utils.Constants.Schema.SCHEMA_NAME;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trip", schema = SCHEMA_NAME)
@EqualsAndHashCode(callSuper = true)
public class Trip extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;

    // Relationships
    @ToString.Exclude
    @OneToOne
    private AppUser owner;

    @ToString.Exclude
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Destination> destinations;

    // Fields
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal totalBudget = BigDecimal.ZERO;
}
