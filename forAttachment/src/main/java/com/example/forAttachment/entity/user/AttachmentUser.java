package com.example.forAttachment.entity.user;

import com.example.forAttachment.entity.template.IdClass;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "attachment_user")
public class AttachmentUser extends IdClass {

    private String fileOriginalName;

    private long size;

    private String contentType;

    @ManyToOne
    private User user;

}
