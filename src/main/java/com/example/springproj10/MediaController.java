package com.example.springproj10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MediaController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    ReferenceRepository referenceRepository;

    @Autowired
    PostWithReferenceRepository postWithReferenceRepository;

    @Autowired
    PostAndReferenceRepository postAndReferenceRepository;

    @PostMapping("/post")
    public ResponseEntity<Post> addPost(@RequestParam String title, @RequestParam String content) {
        try {
            Post post = new Post(title, content);
            Post savedPost = postRepository.save(post);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/postwithref")
    public ResponseEntity<PostWithReference> addPostWithReference(@RequestParam String title, @RequestParam String content, @RequestParam String referenceLinkURL) {
        try {
            PostWithReference post = new PostWithReference(title, content, referenceLinkURL);
            PostWithReference savedPost = postWithReferenceRepository.save(post);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/postandref")
    public ResponseEntity<PostAndReference> addPostAndReference(@RequestParam String title, @RequestParam String content, @RequestParam String referenceLinkURL) {
        try {
            Reference reference = new Reference(referenceLinkURL);
            Reference savedReference = referenceRepository.save(reference);
            PostAndReference post = new PostAndReference(title, content, savedReference);
            PostAndReference savedPost = postAndReferenceRepository.save(post);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}