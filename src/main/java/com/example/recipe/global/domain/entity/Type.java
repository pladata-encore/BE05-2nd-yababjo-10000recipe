package com.example.recipe.global.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name="TYPES")
public class Type {
    @Id
    @Column(name="TYPES_ID")
    private Long id;
    @Column(name="TYPES_TYPENAME")
    private String typeName;

    @JoinColumn(name="FOOD_RECIPE_ID")
    @ManyToOne
    private Recipe recipe;
}
