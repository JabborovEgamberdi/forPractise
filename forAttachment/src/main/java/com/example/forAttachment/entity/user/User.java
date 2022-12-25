package com.example.forAttachment.entity.user;

import com.example.forAttachment.entity.attachment.Attachment;
import com.example.forAttachment.entity.template.IdClass;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "users")
public class User extends IdClass {

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    @Cascade(CascadeType.ALL)
    @OneToOne
    private AttachmentUser attachmentUser = new AttachmentUser();

    public void addAttachment(AttachmentUser attachmentUser) {

    }

    @OneToMany
    private List<Attachment> attachment;

    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }
}
