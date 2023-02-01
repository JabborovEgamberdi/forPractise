package com.example.forAttachment.entity.user;

import com.example.forAttachment.entity.template.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "attachment_content_user")
public class AttachmentContentUser extends IdClass {

    private byte[] bytes;

    @OneToOne
    private AttachmentUser attachmentUser;

}