package com.example.forAttachment.repository.user;

import com.example.forAttachment.entity.attachment.Attachment;
import com.example.forAttachment.entity.user.AttachmentContentUser;
import com.example.forAttachment.entity.user.User;
import com.example.forAttachment.payload.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //    SELECT * FROM users as u join attachment_user au on u.id = au.user_id
    @Query(
            nativeQuery = true,
            value = "SELECT new com.example.forpractise.payload.UserDTO (u.id, u.first_name, u.last_name, u.username, u.password, au.file_original_name, au.content_type)" +
                    "FROM Users as u join Attachment_User as au ON a.id = au.user_id"
    )
    List<UserDTO> takeUser();

    Optional<Attachment> findByAttachmentId(Integer attachmentId);

    @Query(value = "select au.user.id, acu.bytes, acu.id\n" +
            "from attachment_user as au \n" +
            "         join attachment_content_user as acu on au.id = acu.attachmentUser.id\n" +
            "where au.user.id in (select id from users where id = ?1)")
    List<AttachmentContentUser> findByAttachmentUserId(Integer id);

//    @Query(value = "select au.user_id, acu.bytes, acu.id\n" +
//            "from attachment_user as au\n" +
//            "         join attachment_content_user acu on attachment_user.id = acu.attachment_user_id\n" +
//            "where user_id in (select id from users where id = ?1)")
    List<AttachmentContentUser> findAttachmentByUserId(Integer userId);


}
