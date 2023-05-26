package com.ecommerce.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.internal.build.AllowPrintStacktrace;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllowPrintStacktrace
@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")

    private Long id;
    private String name;
}

