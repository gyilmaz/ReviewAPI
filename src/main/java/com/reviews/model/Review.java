package com.reviews.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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
}
