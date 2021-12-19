package com.example.springproj10;

import com.sun.istack.NotNull;
import javax.persistence.*;

@Entity
@Table(name = "posts_table_2")
public class PostWithReference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "content")
    @NotNull
    private String content;

    @Column(name = "reference_linkurl")
    @NotNull
    private String referenceLinkURL;

    public PostWithReference(String title, String content, String referenceLinkURL) {
        this.title = title;
        this.content = content;
        this.referenceLinkURL = referenceLinkURL;
    }

    public PostWithReference() {}
}