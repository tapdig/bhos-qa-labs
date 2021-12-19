package com.example.springproj10;

import com.sun.istack.NotNull;
import javax.persistence.*;

@Entity
@Table(name = "reference_table_3")
public class Reference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "reference_linkurl")
    @NotNull
    private String referenceLinkURL;


    public Reference(String referenceLinkURL) {
        this.referenceLinkURL = referenceLinkURL;
    }

    public Reference() {}
}