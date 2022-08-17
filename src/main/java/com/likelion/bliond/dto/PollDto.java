package com.likelion.bliond.dto;

import com.likelion.bliond.domain.Poll;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class PollDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Poll toEntity(){
        Poll poll = Poll.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();
        return poll;
    }

    @Builder
    public PollDto(Long id, String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
