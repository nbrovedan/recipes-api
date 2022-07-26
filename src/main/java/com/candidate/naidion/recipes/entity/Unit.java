package com.candidate.naidion.recipes.entity;

import lombok.*;

import javax.persistence.*;

@With
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "unit")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String initials;
}
