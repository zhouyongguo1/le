package le.oa.work.controllers.form;

public class FormTemplateData {
    private Integer id;
    private String name;
    private boolean active = false;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

//    public static Collection<FormTemplateData> toDatas(List<FormUserTemplate> userTemplates
//            , List<FormTeamTemplate> teamTemplates) {
//        Map<Integer, FormTemplateData> map = new HashMap<>();
//        userTemplates.forEach(m -> {
//            FormTemplateData item = new FormTemplateData();
//            item.setId(m.getFormTeamTemplate().getId());
//            item.setName(m.getFormTeamTemplate().getName());
//            item.setActive(true);
//            map.put(item.getId(), item);
//        });
//        teamTemplates.forEach(m -> {
//            FormTemplateData item = new FormTemplateData();
//            item.setId(m.getId());
//            item.setName(m.getName());
//            item.setActive(false);
//            map.put(item.getId(), item);
//        });
//        return map.values();
//    }
}
