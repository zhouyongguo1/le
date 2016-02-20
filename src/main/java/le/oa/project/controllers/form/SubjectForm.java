package le.oa.project.controllers.form;

import le.oa.project.models.Subject;

public class SubjectForm {

    private Integer id;
    private String name;
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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


    public Subject toSubject(Subject subject) {

        if (subject == null) {
            subject = new Subject();
        }
        subject.setId(id);
        subject.setName(name);
        subject.setContent(content);
        return subject;
    }


}
