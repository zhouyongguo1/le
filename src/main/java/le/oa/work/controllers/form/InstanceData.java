package le.oa.work.controllers.form;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import le.oa.core.models.User;
import le.oa.work.models.Form;
import le.oa.work.models.FormParameter;
import le.oa.work.models.WorkFlow;
import le.oa.work.models.WorkInstance;
import le.util.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InstanceData {
    private String title;
    private String workFlow;
    private Integer teamTemplateId;
    private String teamTemplateName;
    private String fields;
    private String parameters;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(String workFlow) {
        this.workFlow = workFlow;
    }

    public Integer getTeamTemplateId() {
        return teamTemplateId;
    }

    public void setTeamTemplateId(Integer teamTemplateId) {
        this.teamTemplateId = teamTemplateId;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getTeamTemplateName() {
        return teamTemplateName;
    }

    public void setTeamTemplateName(String teamTemplateName) {
        this.teamTemplateName = teamTemplateName;
    }

    public Form toForm() throws IOException {
        Form form = new Form();
        form.setTemplateId(teamTemplateId);
        form.setFields(fields);
        List<ParameterData> parameterDatas = JsonUtils.unMarshal(parameters, new TypeReference<List<ParameterData>>() {
        });
        List<FormParameter> formParameters = new ArrayList<>();
        for (ParameterData p : parameterDatas) {
            FormParameter formParameter = p.toFormParameter();
            formParameter.setForm(form);
            formParameters.add(formParameter);
        }
        form.setParameters(formParameters);
        return form;
    }

    public WorkInstance toWorkInstance(Form form, User createUser) throws IOException {
        WorkInstance instance = new WorkInstance();
        instance.setFormId(form.getId());
        instance.setTitle(title);
        instance.setTemplateName(teamTemplateName);
        WorkFlow wf = JsonUtils.unMarshal(workFlow, WorkFlow.class);
        instance.setWorkFlow(wf);
        instance.setUser(createUser);
        return instance;
    }

    public static InstanceData of(WorkInstance instance, Form form) throws JsonProcessingException {
        InstanceData data = new InstanceData();
        data.setTitle(instance.getTitle());
        data.setTeamTemplateId(form.getTemplateId());
        data.setTeamTemplateName(instance.getTemplateName());
        data.setFields(form.getFields());
        data.setParameters(JsonUtils.marshal(convertParameterDatas(form.getParameters())));
        data.setWorkFlow(JsonUtils.marshal(instance.getWorkFlow()));
        return data;
    }

    private static List<ParameterData> convertParameterDatas(List<FormParameter> parameters) {
        List<ParameterData> list=new ArrayList<>();
        for(FormParameter parameter:parameters){
            list.add(ParameterData.of(parameter));
        }
        return  list;
    }

}
