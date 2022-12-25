package com.example.forAttachment.repository.attachment;

import com.example.forAttachment.entity.attachment.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
    Optional<Attachment> findByUserId(Integer userId);
}
