package com.example.recipe.comment.service;

import com.example.recipe.comment.dto.request.CommentRequest;
import com.example.recipe.comment.dto.response.CommentResponse;
import com.example.recipe.global.domain.entity.*;
import com.example.recipe.global.domain.repository.CommentRepository;
import com.example.recipe.global.domain.repository.RecipeRepository;
import com.example.recipe.global.domain.repository.TypeRepository;
import com.example.recipe.global.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CommentServiceImplTest {
    Comment comment;

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TypeRepository typeRepository;
    @Test
    void save() {
        init();
        CommentRequest req = new CommentRequest(
                comment.getContent(),
                comment.getUser().getId(),
                comment.getRecipe().getId()
        );
        commentService.save(req);
    }

    @Test
    void update_성공() {
//        given
//        init();
//        save();
        comment = commentRepository.getOne(1L);
        CommentRequest req = new CommentRequest(
                "test9",
                comment.getUser().getId(),
                comment.getRecipe().getId()
        );
        Long id = comment.getId();
//        when
        commentService.update(req,id);
//        then
        Comment comment1 = commentRepository.findById(id).get();

    }

    @Test
    void update_실패_아이디_없음(){
        init();
        save();
        CommentRequest req = new CommentRequest(
                "test2",
                comment.getUser().getId(),
                comment.getRecipe().getId()
        );
        Long id = 10000000L;


        assertThrows(
                EntityNotFoundException.class,
                ()-> commentService.update(req,id)
        );

    }

    @Test
    @Transactional
    void findAllByRecipeId(){
//        given
        init();
//        when
        Recipe byId = recipeRepository.findById(1L).get();
        Comment byRecipeId = commentRepository.findByRecipeId(byId.getId()).get(3);
//        then
        assertEquals("test3",byRecipeId.getContent());
    }

    @Test
    @Transactional
    void findAllByUserId(){
//        given
        init();
//        when
        Recipe byId = recipeRepository.findById(1L).get();
        Comment byUserId = commentRepository.findByUserId(byId.getId()).get(3);
//        then
        assertEquals("test3",byUserId.getContent());
    }

    @BeforeEach
    void init(){
        User user = User.builder().id(1L).build();
        userRepository.save(user);
        Type type = Type.builder().id(1L).build();
        typeRepository.save(type);
        Recipe recipe = Recipe.builder()
                .id(1L)
                .foodName("test")
                .recipeTitle("test")
                .user(user)
                .cookingMethod(CookingMethod.builder().id(1L).build())
                .type(type).build();
        // UserRepository와 RecipeRepository를 사용하여 데이터베이스에 저장
        recipeRepository.save(recipe);

        for(Long i=0L;i<10;i++){
            comment = Comment.builder()
                    .id(i)
                    .user(user)
                    .content("test"+i)
                    .recipe(recipe)
                    .createdAt(LocalDateTime.now()) // createdAt도 초기화
                    .build();
            CommentRequest req = new CommentRequest(
                    comment.getContent(),
                    comment.getUser().getId(),
                    comment.getRecipe().getId()
            );
            commentService.save(req);
        }
    }
}
