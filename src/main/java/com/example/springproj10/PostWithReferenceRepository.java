package com.example.springproj10;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostWithReferenceRepository extends JpaRepository<PostWithReference, Long> {}