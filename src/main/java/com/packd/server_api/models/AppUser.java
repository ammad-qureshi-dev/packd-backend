package com.packd.server_api.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

import static com.packd.server_api.utils.Constants.Schema.SCHEMA_NAME;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appUser", schema = SCHEMA_NAME)
@EqualsAndHashCode(callSuper = true)
public class AppUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean accountVerified;
}
