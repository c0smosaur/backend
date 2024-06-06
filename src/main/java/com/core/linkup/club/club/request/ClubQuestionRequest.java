package com.core.linkup.club.club.request;

import com.core.linkup.common.entity.BaseEntity;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ClubQuestionRequest extends BaseEntity {

    private String question;
    private Integer qorders;
}
