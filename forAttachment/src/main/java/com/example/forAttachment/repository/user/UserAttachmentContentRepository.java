package com.example.forAttachment.repository.user;

import com.example.forAttachment.entity.user.AttachmentContentUser;
import com.example.forAttachment.entity.user.AttachmentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAttachmentContentRepository extends JpaRepository<AttachmentContentUser, Integer> {

    Optional<AttachmentContentUser> findByAttachmentUser_Id(AttachmentUser attachmentUserId);
}
