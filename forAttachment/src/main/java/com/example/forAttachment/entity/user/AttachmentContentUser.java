package com.example.forAttachment.entity.user;

import com.example.forAttachment.entity.template.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class AttachmentContentUser extends IdClass {

    private byte[] bytes;

    @OneToOne
    private AttachmentUser attachmentUser;
}
