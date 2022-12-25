package com.example.forAttachment.entity.user;

import com.example.forAttachment.entity.template.IdClass;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class AttachmentUser extends IdClass {

    private String fileOriginalName;

    private long size;

    private String contentType;

    @ManyToOne
    private User user;

}
