package com.reviews.model;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.*;
import org.hibernate.annotations.ListIndexBase;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Document(collection = "reviews")
public class Review {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Please provide a title")
    private String title;

    @NotEmpty(message = "Please provide a text")
    private String text;

    @ManyToOne
    private Product product;

    @Transient
    private List<Comment> commentList=new ArrayList<>();


    public Review addComment(Comment comment){
        this.commentList.add(comment);
        return this;
    }
}
