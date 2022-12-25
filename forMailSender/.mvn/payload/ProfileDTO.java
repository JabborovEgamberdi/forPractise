package com.example.forcookie.payload;

import java.util.List;

public class ProfileDTO {
    private UserSummaryDTO userDto;
    private List<UserSummaryDTO> friends;
    private List<MessageDTO> messages;
    private List<ImageDTO> images;

    public ProfileDTO() {
        super();
    }

    public ProfileDTO(UserSummaryDTO userDto, List<UserSummaryDTO> friends, List<MessageDTO> messages, List<ImageDTO> images) {
        this.userDto = userDto;
        this.friends = friends;
        this.messages = messages;
        this.images = images;
    }

    public UserSummaryDTO getUserDto() {
        return userDto;
    }

    public void setUserDto(UserSummaryDTO userDto) {
        this.userDto = userDto;
    }

    public List<UserSummaryDTO> getFriends() {
        return friends;
    }

    public void setFriends(List<UserSummaryDTO> friends) {
        this.friends = friends;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }

    public List<ImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
    }
}
