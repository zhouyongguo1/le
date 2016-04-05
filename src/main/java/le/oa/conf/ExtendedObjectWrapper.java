package le.oa.conf;

import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleDate;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class ExtendedObjectWrapper extends DefaultObjectWrapper {

    @Override
    public TemplateModel wrap(Object obj) throws TemplateModelException {
        if (obj == null) {
            return super.wrap(obj);
        }
        if (obj instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime) obj;
            Instant instant = localDateTime.atZone(ZoneOffset.UTC).toInstant();
            return new SimpleDate(Date.from(instant), TemplateDateModel.DATETIME);
        } else if (obj instanceof LocalDate) {
            LocalDate localDate = (LocalDate) obj;
            Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            return new SimpleDate(Date.from(instant), TemplateDateModel.DATE);
        }
        return super.wrap(obj);
    }
}
