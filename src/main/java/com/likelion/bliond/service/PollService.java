package com.likelion.bliond.service;

import com.likelion.bliond.domain.Poll;
import com.likelion.bliond.dto.PollDto;
import com.likelion.bliond.repository.PollRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PollService {

    private PollRepository pollRepository;

    private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 4; // 한 페이지에 존재하는 게시글 수

    private PollDto convertEntityToDto(Poll poll) {
        return PollDto.builder()
                .id(poll.getId())
                .title(poll.getTitle())
                .content(poll.getContent())
                .writer(poll.getWriter())
                .createdDate(poll.getCreatedDate())
                .modifiedDate(poll.getModifiedDate())
                .build();
    }


    @Transactional
    public List<PollDto> getPolllist(Integer pageNum) {
        Page<Poll> page = pollRepository.findAll(PageRequest.of(
                pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "createdDate")));

        List<Poll> pollEntities = page.getContent();
        List<PollDto> pollDtoList = new ArrayList<>();

        for (Poll poll : pollEntities) {
            pollDtoList.add(this.convertEntityToDto(poll));
        }

        return pollDtoList;
    }

    @Transactional
    public PollDto getPost(Long id) {
        Optional<Poll> pollWrapper = pollRepository.findById(id);
        Poll poll = pollWrapper.get();

        return PollDto.builder()
                .id(poll.getId())
                .title(poll.getTitle())
                .content(poll.getContent())
                .writer(poll.getWriter())
                .createdDate(poll.getCreatedDate())
                .modifiedDate(poll.getModifiedDate())
                .build();
    }

    @Transactional
    public Long savePost(PollDto pollDto) {
        return pollRepository.save(pollDto.toEntity()).getId();
    }

    @Transactional
    public void deletePost(Long id) {
        pollRepository.deleteById(id);
    }

    // 검색 API
    @Transactional
    public List<PollDto> searchPosts(String keyword) {
        List<Poll> pollEntities = pollRepository.findByTitleContaining(keyword);
        List<PollDto> pollDtoList = new ArrayList<>();

        if (pollEntities.isEmpty()) return pollDtoList;

        for (Poll poll : pollEntities) {
            pollDtoList.add(this.convertEntityToDto(poll));
        }

        return pollDtoList;
    }


    // 페이징
    @Transactional
    public Long getPollCount() {
        return pollRepository.count();
    }

    public Integer[] getPageList(Integer curPageNum) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getPollCount());

        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

        // 페이지 번호 할당
        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }

        return pageList;
    }

}