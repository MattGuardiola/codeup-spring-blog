package com.codeup.codeupspringblog.models;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1024, nullable = false)
    private String body;

    @ManyToOne
    @JoinColumn (name = "bloguser_id")
    private User user;



}
