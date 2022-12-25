package com.example.forAttachment.repository.user;

import com.example.forAttachment.entity.user.AttachmentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAttachmentRepository extends JpaRepository<AttachmentUser, Integer> {

    Optional<AttachmentUser> findByUserId(Integer userId);
}
