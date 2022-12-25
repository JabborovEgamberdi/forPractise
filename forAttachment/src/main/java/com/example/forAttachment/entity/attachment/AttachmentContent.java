package com.example.forAttachment.entity.attachment;

import com.example.forAttachment.entity.template.IdClass;
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
public class AttachmentContent extends IdClass {

    private byte[] bytes;

    @OneToOne
    private Attachment attachment;
}
