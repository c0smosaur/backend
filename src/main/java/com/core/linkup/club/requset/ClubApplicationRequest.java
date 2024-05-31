package com.core.linkup.club.requset;

import com.core.linkup.club.entity.ClubAnswer;
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
//    private List<ClubAnswer> answerList;
}
