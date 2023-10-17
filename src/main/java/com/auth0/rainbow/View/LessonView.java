package com.auth0.rainbow.View;

import com.auth0.rainbow.service.dto.AppLessonInfoDTO;
import com.auth0.rainbow.service.dto.AppQuestionDTO;
import java.util.Objects;
import java.util.Set;

public class LessonView {

    private Long id;

    private Set<AppQuestionDTO> appQuestion; // sử dụng tên 'appQuestion' thay vì 'appQuestionDTO'

    private Set<AppLessonInfoDTO> appLesonInf;

    //...

    public Set<AppQuestionDTO> getappQuestion() { // sử dụng tên 'getAppQuestion' thay vì 'getAppQuestionDTO'
        return appQuestion;
    }

    public void setAppQuestion(Set<AppQuestionDTO> appQuestion) { // sử dụng tên 'setAppQuestion' thay vì 'setAppAvailableCourses'
        this.appQuestion = appQuestion;
    }

    public Set<AppLessonInfoDTO> getappLesonInf() {
        return appLesonInf;
    }

    public void setappLesonInf(Set<AppLessonInfoDTO> appLesonInf) {
        this.appLesonInf = appLesonInf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppLessonDTO{" +
            "id=" + getId() +
            // ", course=" + getCourse() +
            "Info=" + getappLesonInf() +
            "Question=" + getappQuestion() +
            "}";
    }
}
