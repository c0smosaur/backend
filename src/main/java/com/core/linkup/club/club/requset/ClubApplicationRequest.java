package com.core.linkup.club.club.requset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ClubApplicationRequest {

    private String introduction;
    private List<ClubAnswerRequest> clubAnswers;

}
