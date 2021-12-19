package com.example.springproj10;
import com.sun.istack.NotNull;
import javax.persistence.*;

@Entity
@Table(name = "posts_table_3")
public class PostAndReference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "content")
    @NotNull
    private String content;

    @OneToOne
    @JoinColumn(name = "reference_id", referencedColumnName = "id")
    private Reference reference;


    public PostAndReference(String title, String content, Reference reference) {
        this.title = title;
        this.content = content;
        this.reference = reference;
    }

    public PostAndReference() {}
}