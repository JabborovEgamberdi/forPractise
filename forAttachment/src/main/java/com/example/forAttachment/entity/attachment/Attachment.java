package com.example.forAttachment.entity.attachment;

import com.example.forAttachment.entity.template.IdClass;
import com.example.forAttachment.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Attachment extends IdClass {

    private String fileOriginalName;

    private long size;

    private String contentType;

    @OneToOne
    private User user;

}
