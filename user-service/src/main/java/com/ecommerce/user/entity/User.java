package com.ecommerce.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // hashed password

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE}) // CascadeType.PERSIST
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;  // default true

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin = false;  // default false

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id", referencedColumnName = "id")
    private User createdBy;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_user_id", referencedColumnName = "id")
    private User updatedBy;

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public String getMiddleInitial() {
        return this.middleName != null && !this.middleName.isEmpty() ? this.middleName.charAt(0) + "." : "";
//        return this.middleName != null && !this.middleName.isEmpty() ? this.middleName.substring(0, 1) + "." : "";
    }

    public String getFullName() {
        return this.firstName + " " + getMiddleInitial() + " " + this.lastName;
    }
}