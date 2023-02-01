package com.example.forAttachment.repository.user;

import com.example.forAttachment.entity.user.AttachmentContentUser;
import com.example.forAttachment.entity.user.AttachmentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAttachmentContentRepository extends JpaRepository<AttachmentContentUser, Integer> {

//    Optional<AttachmentContentUser> findByAttachmentUser_Id(AttachmentUser attachmentUserId);
    List<AttachmentContentUser> findByAttachmentUserId(AttachmentUser attachmentUserId);

//    @Query(value = "select au.user.id, acu.bytes, acu.id\n" +
//            "from attachment_user as au \n" +
//            "         join attachment_content_user as acu on au.id = acu.attachmentUser.id\n" +
//            "where au.user.id in (select id from users where id = ?1)")
//    List<AttachmentContentUser> findByAttachmentUser_Id(Integer userId);
//
//    @Query(nativeQuery = true, value = "select user_id, acu.bytes, acu.id\n" +
//            "from attachment_user\n" +
//            "         join attachment_content_user acu on attachment_user.id = acu.attachment_user_id\n" +
//            "where user_id in (select id from users where id = ?1)")
//    List<AttachmentContentUser> findByAttachmentUserId1(Integer userId);

}