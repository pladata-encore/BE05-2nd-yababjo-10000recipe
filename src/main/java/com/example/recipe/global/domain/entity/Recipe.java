package com.example.recipe.global.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Builder
@Table(name = "FOOD_RECIPE")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FOOD_RECIPE_ID")
    private Long id;

    @Column(name = "FOOD_RECIPE_FOOD_NAME", nullable = false)
    private String foodName;

    @Column(name = "FOOD_RECIPE_TITLE", nullable = false)
    private String recipeTitle;

    @Column(name = "FOOD_RECIPE_COOKING_TIP")
    private String cookingTip;

    @Column(name = "FOOD_RECIPE_SERVING_SIZE")
    private int servingSize;

    @Column(name = "FOOD_RECIPE_LEVEL")
    private int level;

    @Column(name = "FOOD_RECIPE_COOKING_TIME")
    private double cookingTime;

    @Column(name = "FOOD_RECIPE_CREATEAT")
    private LocalDateTime createAT;

    @Column(name = "FOOD_RECIPE_RATING_AVERAGE")
    private double ratingAverage;

    @Column(name = "FOOD_RECIPE_VIEWS")
    private int views;

    @JoinColumn(name="USER_ID")
    @ManyToOne
    private User user;

    @JoinColumn(name="TYPE_ID")
    @ManyToOne
    private Type type;

//    @JoinColumn(name="COOKING_METHOD_ID", nullable = false)
//    @ManyToOne
//    private CookingMethod cookingMethod;

    @JoinColumn(name="COOKING_METHOD_ID")
    @ManyToOne
    private CookingMethod cookingMethod;

    @OneToMany(mappedBy = "recipe")
    private List<SauceRecipeBridge> sauceRecipeBridges;

    @OneToMany(mappedBy = "recipe")
    private List<FoodIngredientsRecipeBridge> foodIngredientsRecipeBridges;

    @OneToMany(mappedBy = "recipe")
    private List<CookingOrder> cookingOrders;

    @OneToMany(mappedBy = "recipe")
    private List<Comment> comments;

    @OneToMany(mappedBy = "recipe")
    private List<TagRecipeBridge> tagRecipeBridges;

    @OneToMany(mappedBy = "recipe")
    private List<Rating> ratings;


}