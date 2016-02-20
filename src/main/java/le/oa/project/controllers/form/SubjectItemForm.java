package le.oa.project.controllers.form;

import le.oa.project.models.SubjectItem;

public class SubjectItemForm {
    private String name;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SubjectItem toSubjectItem() {
        SubjectItem subjectItem = new SubjectItem();
        subjectItem.setName(name);
        subjectItem.setContent(content);
        return subjectItem;
    }

}
