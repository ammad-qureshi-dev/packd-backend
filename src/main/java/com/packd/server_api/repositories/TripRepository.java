package com.packd.server_api.repositories;

import com.packd.server_api.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TripRepository extends JpaRepository<Trip, UUID> {

    @Query("""
            SELECT
                CASE WHEN COUNT(t) > 0 THEN true ELSE false END
            FROM Trip t
            WHERE t.owner.id = :app_user_id
            AND LOWER(t.title) = LOWER(:title)
            """)
    boolean existsByName(@Param("app_user_id") UUID appUserId, @Param("title") String title);
}
